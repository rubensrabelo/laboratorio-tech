package dev.project.mastersys.infra.repositories;

import dev.project.mastersys.domain.EnrollmentInvoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentInvoiceRepository extends JpaRepository<EnrollmentInvoice, Long> {
}
