CREATE TABLE bill_closings (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL UNIQUE REFERENCES orders(id),
    subtotal NUMERIC(10,2) NOT NULL CHECK (subtotal >= 0),
    service_fee NUMERIC(10,2) NOT NULL DEFAULT 0 CHECK (service_fee >= 0),
    discount NUMERIC(10,2) NOT NULL DEFAULT 0 CHECK (discount >= 0),
    total NUMERIC(10,2) NOT NULL CHECK (total >= 0),
    closed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
