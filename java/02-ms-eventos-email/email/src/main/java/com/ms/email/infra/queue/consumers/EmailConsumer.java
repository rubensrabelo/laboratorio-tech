package com.ms.email.infra.queue.consumers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.ms.email.application.dtos.EmailRequestDTO;
import com.ms.email.application.services.EmailService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class EmailConsumer {

    private final EmailService emailService;

    @RabbitListener(queues = "${app.lavinmq.email.queue}")
    public void consumeEmailMessage(@Payload EmailRequestDTO payload) {
        emailService.sendEmail(payload);
    }
}
