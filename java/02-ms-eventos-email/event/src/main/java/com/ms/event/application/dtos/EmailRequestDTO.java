package com.ms.event.application.dtos;

public record EmailRequestDTO(
        String to,
        String subject,
        String body) {
}
