package com.ms.email.application.dtos;

public record EmailRequestDTO(
        String to,
        String subject,
        String body) {
}

