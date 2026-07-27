package com.ms.event.infra.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ms.event.domain.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

}
