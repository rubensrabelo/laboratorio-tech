package com.ms.email.infra.queue.consumers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import com.ms.email.application.dtos.EmailPayloadDTO;
import com.ms.email.application.services.EmailService;

@Component
public class EmailConsumer {

    private final EmailService emailService;

    public EmailConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "${app.lavinmq.email.queue}")
    public void consumeEmailMessage(@Payload EmailPayloadDTO payload) {
        emailService.sendEmail(payload);
    }
}
