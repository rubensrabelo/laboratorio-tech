package com.ms.event.exceptions.domain;

public class EventFullException extends RuntimeException {
    public EventFullException() {
        super("The event is fully booked.");
    }

    public EventFullException(String message) {
        super(message);
    }
}
