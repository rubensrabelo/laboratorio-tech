package dev.project.mastersys.application.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

public record OpenInvoicesResponseDTO(
    Long enrollmentId,
    String studentName,
    LocalDate dueDate,
    BigDecimal amount
) {}
