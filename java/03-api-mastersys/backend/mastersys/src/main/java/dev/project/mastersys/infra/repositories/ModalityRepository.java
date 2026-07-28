package dev.project.mastersys.infra.repositories;

import dev.project.mastersys.domain.Modality;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModalityRepository extends JpaRepository<Modality, Long> {
}
