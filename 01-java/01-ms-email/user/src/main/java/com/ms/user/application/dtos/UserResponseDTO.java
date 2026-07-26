package com.ms.user.application.dtos;

import java.util.UUID;

public record UserResponseDTO(
    UUID id,
    String name,
    String email
) {

}
