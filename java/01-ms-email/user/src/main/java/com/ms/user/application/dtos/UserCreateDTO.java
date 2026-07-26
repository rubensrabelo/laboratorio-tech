package com.ms.user.application.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserCreateDTO(
    @NotBlank String name,
    @NotBlank @Email String email
) {

}
