package com.ms.user.application.dtos;

import java.util.UUID;

public record EmailPayloadDTO(
    UUID id,
    String emailTo,
    String subject,
    String text
) {

}

