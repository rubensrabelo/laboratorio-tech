CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    table_id BIGINT NOT NULL REFERENCES tables(id),
    opened_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    closed_at TIMESTAMP,
    status VARCHAR(30) NOT NULL DEFAULT 'OPEN',
    notes TEXT,
    CHECK (status IN ('OPEN', 'PREPARING', 'READY', 'DELIVERED', 'CLOSED', 'CANCELLED'))
);

CREATE INDEX idx_orders_table ON orders(table_id);
CREATE INDEX idx_orders_status ON orders(status);
CREATE INDEX idx_orders_opened_at ON orders(opened_at);
