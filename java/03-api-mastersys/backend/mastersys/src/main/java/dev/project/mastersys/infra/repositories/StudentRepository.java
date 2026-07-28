package dev.project.mastersys.infra.repositories;

import dev.project.mastersys.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
