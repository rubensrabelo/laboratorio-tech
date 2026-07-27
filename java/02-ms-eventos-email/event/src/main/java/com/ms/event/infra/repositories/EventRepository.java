package com.ms.event.infra.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ms.event.domain.Event;

public interface EventRepository extends JpaRepository<Event, String> {

    List<Event> findByDateAfter(LocalDateTime currentDate);
}
