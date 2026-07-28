CREATE TABLE plans (
    id BIGSERIAL PRIMARY KEY,
    modality_id BIGINT NOT NULL REFERENCES modalities(id),
    name VARCHAR(100) NOT NULL,
    monthly_value NUMERIC(10,2) NOT NULL CHECK(monthly_value >= 0),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    UNIQUE (modality_id, name)
);
