package dev.project.restaurant.exceptions.dtos;

public record FieldErrorDTO(
    String field,
    String message
) {}
