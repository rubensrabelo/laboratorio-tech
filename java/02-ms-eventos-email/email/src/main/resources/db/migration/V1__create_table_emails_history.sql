CREATE TABLE emails_history (
    id VARCHAR(36) NOT NULL,
    email_to VARCHAR(255) NOT NULL,
    subject VARCHAR(255) NOT NULL,
    body TEXT NOT NULL,
    sent_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    status VARCHAR(20) NOT NULL,

    CONSTRAINT pk_emails_history PRIMARY KEY (id)
);
