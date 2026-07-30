package dev.project.mastersys.infra.projections;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface OpenInvoicesProjection {

    Long getEnrollmentId();
    String getStudentName();
    LocalDate getDueDate();
    BigDecimal getAmount();
}
