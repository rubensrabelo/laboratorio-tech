package dev.project.mastersys.application.dtos;

import dev.project.mastersys.domain.enums.Gender;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record StudentResponseDTO(
    Long id,
    String name,
    LocalDate birthDate,
    Gender gender,
    String phone,
    String cellPhone,
    String email,
    String observation,
    String address,
    String number,
    String complement,
    String neighborhood,
    String city,
    String state,
    String zipCode,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}
