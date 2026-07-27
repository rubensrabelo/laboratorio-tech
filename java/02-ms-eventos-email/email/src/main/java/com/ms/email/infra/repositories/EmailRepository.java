package com.ms.email.infra.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ms.email.domain.Email;

public interface EmailRepository extends JpaRepository<Email, String> {
}

