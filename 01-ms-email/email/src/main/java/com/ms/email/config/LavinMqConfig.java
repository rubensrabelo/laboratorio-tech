package com.ms.email.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.StringLayout;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LavinMqConfig {

    @Value("${app.lavinmq.email.queue}")
    private String queueName;

    @Value("${app.lavinmq.email.exchange}")
    private String exchangeName;

    @Value("${app.lavinmq.email.routing-key}")
    private String routingKey;

    @Value("${app.logging.exchange}")
    private String logExchangeName;

    @Value("${app.logging.routing-key}")
    private String logRoutingKey;

    @Bean
    Queue queue() {
        return new Queue(queueName, true);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(exchangeName);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    @Bean
    TopicExchange logsExchange() {
        return new TopicExchange(logExchangeName, true, false);
    }

    @Bean
    Queue centralLogsQueue() {
        return new Queue("central-logs-queue", true);
    }

    @Bean
    Binding logsBinding(Queue centralLogsQueue, TopicExchange logsExchange) {
        return BindingBuilder.bind(centralLogsQueue).to(logsExchange).with("microsservico.*.logs");
    }

    @Bean
    JacksonJsonMessageConverter jacksonJsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    CommandLineRunner setupLogBridge(RabbitTemplate rabbitTemplate) {
        return args -> {
            try {
                LoggerContext context = (LoggerContext) LogManager.getContext(false);
                org.apache.logging.log4j.core.config.Configuration config = context.getConfiguration();
                Appender consoleAppender = config.getAppender("Console");

                if (rabbitTemplate != null && consoleAppender != null) {
                    StringLayout stringLayout = (StringLayout) consoleAppender.getLayout();
                    
                    AbstractAppender amqpBridge = new AbstractAppender("LavinMQBridge", null, stringLayout, false, null) {
                        @Override
                        public void append(LogEvent event) {
                            if (event != null && event.getMessage() != null) {
                                try {
                                    String logJson = new String(getLayout().toByteArray(event)).trim();
                                    if (!logJson.isEmpty() && logJson.startsWith("{") && !logJson.contains(logExchangeName)) {
                                        rabbitTemplate.convertAndSend(logExchangeName, logRoutingKey, logJson);
                                    }
                                } catch (Exception ignored) {}
                            }
                        }
                    };
                    
                    amqpBridge.start();
                    config.addAppender(amqpBridge);
                    
                    LoggerConfig loggerConfig = config.getLoggerConfig("com.ms.email");
                    if (loggerConfig != null) {
                        loggerConfig.addAppender(amqpBridge, null, null);
                    }
                    config.getRootLogger().addAppender(amqpBridge, null, null);
                    
                    context.updateLoggers();
                }
            } catch (Exception ignored) {}
        };
    }
}
