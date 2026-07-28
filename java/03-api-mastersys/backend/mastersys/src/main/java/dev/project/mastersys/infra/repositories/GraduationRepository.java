package dev.project.mastersys.infra.repositories;

import dev.project.mastersys.domain.Graduation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GraduationRepository extends JpaRepository<Graduation, Long> {
}
