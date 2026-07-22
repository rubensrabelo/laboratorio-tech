package com.ms.email.infra.queue.consumers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.ms.email.application.dtos.EmailPayloadDTO;


@Component
public class EmailConsumer {

    @RabbitListener(queues = "${app.lavinmq.email.queue}")
    public void consumeEmailMessage(@Payload EmailPayloadDTO payload) {
        System.out.println(payload.emailTo());
    }
}

