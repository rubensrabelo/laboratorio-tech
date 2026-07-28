CREATE TABLE enrollment_invoices (
    id BIGSERIAL PRIMARY KEY,
    enrollment_id BIGINT NOT NULL REFERENCES enrollments(id),
    due_date DATE NOT NULL,
    amount NUMERIC(10, 2) NOT NULL CHECK ( amount >= 0 ),
    payment_date TIMESTAMP,
    cancellation_date DATE,
    status VARCHAR(20) NOT NULL DEFAULT 'OPEN',
    CHECK ( status IN ('OPEN', 'PAID', 'CANCELED', 'OVERDUE') ),
    UNIQUE (enrollment_id, due_date)
);
