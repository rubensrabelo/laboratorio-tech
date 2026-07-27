package com.ms.event.application.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Adicionado para controle de transação

import com.ms.event.application.dtos.EmailRequestDTO;
import com.ms.event.application.dtos.EventRequestDTO;
import com.ms.event.domain.Event;
import com.ms.event.domain.Subscription;
import com.ms.event.exceptions.domain.EventFullException;
import com.ms.event.exceptions.domain.EventNotFoundException;
import com.ms.event.infra.queue.producers.EmailProducer;
import com.ms.event.infra.repositories.EventRepository;
import com.ms.event.infra.repositories.SubscriptionRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EventService {
    
    private final EventRepository eventRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final EmailProducer emailProducer;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public List<Event> getUpcomingEvents() {
        return eventRepository.findByDateAfter(LocalDateTime.now());
    }

    public Event createEvent(EventRequestDTO eventRequest) {
        Event newEvent = new Event(eventRequest);
        return eventRepository.save(newEvent);
    }

    private Boolean isEventFull(Event event){
        return event.getRegisteredParticipants() >= event.getMaxParticipants();
    }

    @Transactional
    public void registerParticipant(String eventId, String participantEmail) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event not found with the provided ID."));

        if(isEventFull(event)) {
            throw new EventFullException("The event has already reached the maximum number of participants.");
        }

        Subscription subscription = new Subscription(event, participantEmail);
        subscriptionRepository.save(subscription);

        event.setRegisteredParticipants(event.getRegisteredParticipants() + 1);
        eventRepository.save(event);

        EmailRequestDTO emailRequest = new EmailRequestDTO(
                participantEmail, 
                "Confirmação de Inscrição", 
                "Você foi inscrito no evento " + event.getTitle() + " com sucesso!"
        );

        emailProducer.publishEmailMessage(emailRequest);
    }
}
