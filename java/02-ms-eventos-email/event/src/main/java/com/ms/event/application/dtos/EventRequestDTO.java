package com.ms.event.application.dtos;

import java.time.LocalDateTime;

public record EventRequestDTO(
        int maxParticipants,
        int registeredParticipants,
        LocalDateTime date,
        String title, String description) {
}
