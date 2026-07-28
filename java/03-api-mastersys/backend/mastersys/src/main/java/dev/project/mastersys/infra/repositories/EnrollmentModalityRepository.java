package dev.project.mastersys.infra.repositories;

import dev.project.mastersys.domain.EnrollmentModality;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentModalityRepository extends JpaRepository<EnrollmentModality, Long> {
}
