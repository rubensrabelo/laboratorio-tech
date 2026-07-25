package com.ms.user.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
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
    org.springframework.boot.CommandLineRunner setupLogBridge(org.springframework.amqp.rabbit.core.RabbitTemplate rabbitTemplate) {
        return args -> {
            try {
                org.apache.logging.log4j.core.LoggerContext context = (org.apache.logging.log4j.core.LoggerContext) org.apache.logging.log4j.LogManager.getContext(false);
                org.apache.logging.log4j.core.config.Configuration config = context.getConfiguration();
                org.apache.logging.log4j.core.Appender consoleAppender = config.getAppender("Console");

                if (rabbitTemplate != null && consoleAppender != null) {
                    org.apache.logging.log4j.core.StringLayout stringLayout = (org.apache.logging.log4j.core.StringLayout) consoleAppender.getLayout();
                    
                    org.apache.logging.log4j.core.appender.AbstractAppender amqpBridge = new org.apache.logging.log4j.core.appender.AbstractAppender("LavinMQBridge", null, stringLayout, false, null) {
                        @Override
                        public void append(org.apache.logging.log4j.core.LogEvent event) {
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
