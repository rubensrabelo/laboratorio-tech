package dev.project.mastersys.infra.repositories;

import dev.project.mastersys.domain.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
}
