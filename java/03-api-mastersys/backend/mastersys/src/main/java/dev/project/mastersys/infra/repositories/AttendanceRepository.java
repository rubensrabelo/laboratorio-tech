package dev.project.mastersys.infra.repositories;

import dev.project.mastersys.domain.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
}
