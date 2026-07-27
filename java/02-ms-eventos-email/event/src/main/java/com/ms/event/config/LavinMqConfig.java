package com.ms.event.config;

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
}
