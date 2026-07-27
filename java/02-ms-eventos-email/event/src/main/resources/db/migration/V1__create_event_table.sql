CREATE TABLE event (
    id VARCHAR(36) NOT NULL,
    max_participants INTEGER NOT NULL,
    registered_participants INTEGER NOT NULL,
    date TIMESTAMP NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    
    CONSTRAINT pk_event PRIMARY KEY (id)
);
