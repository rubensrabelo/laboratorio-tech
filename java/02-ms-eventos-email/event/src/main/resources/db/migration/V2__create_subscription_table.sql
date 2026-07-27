CREATE TABLE subscription (
    id SERIAL,
    event_id VARCHAR(36) NOT NULL,
    participant_email VARCHAR(255) NOT NULL,

    CONSTRAINT pk_subscription PRIMARY KEY (id),
    
    CONSTRAINT fk_subscription_event
        FOREIGN KEY (event_id)
        REFERENCES event(id)
        ON DELETE CASCADE
);
