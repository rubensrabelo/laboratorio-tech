package com.ms.user.infra.queue.producers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.ms.user.application.dtos.EmailPayloadDTO;

@Component
public class EmailProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${app.lavinmq.email.exchange}")
    private String exchangeName;

    @Value("${app.lavinmq.email.routing-key}")
    private String routingKey;

    public EmailProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishEmailMessage(EmailPayloadDTO payload) {
        rabbitTemplate.convertAndSend(exchangeName, routingKey, payload);
    }
}
