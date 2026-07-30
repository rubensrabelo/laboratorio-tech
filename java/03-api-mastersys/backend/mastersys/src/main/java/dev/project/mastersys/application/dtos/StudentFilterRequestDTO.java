package dev.project.mastersys.application.dtos;

public record StudentFilterRequestDTO(
        String name,
        String email,
        String phone,
        String city,
        String state) {

}
