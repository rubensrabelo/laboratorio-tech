package com.ms.user.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.StringLayout;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LavinMqConfig {

    @Value("${app.logging.exchange}")
    private String logExchangeName;

    @Value("${app.logging.routing-key}")
    private String logRoutingKey;

    @Bean
    TopicExchange logsExchange() {
        return new TopicExchange(logExchangeName, true, false);
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
                    config.getRootLogger().addAppender(amqpBridge, null, null);
                    context.updateLoggers();
                }
            } catch (Exception ignored) {}
        };
    }
}
