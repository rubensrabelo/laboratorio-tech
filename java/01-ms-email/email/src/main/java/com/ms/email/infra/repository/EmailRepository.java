package com.ms.email.infra.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ms.email.domain.Email;

public interface EmailRepository extends JpaRepository<Email, UUID> {
}
