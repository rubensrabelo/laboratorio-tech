package dev.project.mastersys.infra.repositories;

import dev.project.mastersys.domain.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Long> {
}
