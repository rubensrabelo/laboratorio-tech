package dev.project.mastersys.application.dtos;

import dev.project.mastersys.domain.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record StudentRequestDTO(
    @NotBlank(message = "Name is required")
    @Size(max = 150, message = "Name cannot exceed 150 characters")
    String name,

    LocalDate birthDate,

    Gender gender,

    @Size(max = 30, message = "Phone cannot exceed 30 characters")
    String phone,

    @Size(max = 30, message = "Cell phone cannot exceed 30 characters")
    String cellPhone,

    @Email(message = "Invalid email address format")
    @Size(max = 150, message = "Email cannot exceed 150 characters")
    String email,

    String observation,

    @Size(max = 150, message = "Address cannot exceed 150 characters")
    String address,

    @Size(max = 20, message = "Number cannot exceed 20 characters")
    String number,

    @Size(max = 100, message = "Complement cannot exceed 100 characters")
    String complement,

    @Size(max = 100, message = "Neighborhood cannot exceed 100 characters")
    String neighborhood,

    @Size(max = 100, message = "City cannot exceed 100 characters")
    String city,

    @Size(max = 2, message = "State must be exactly 2 characters")
    String state,

    @Size(max = 20, message = "Zip code cannot exceed 20 characters")
    String zipCode
) {}
