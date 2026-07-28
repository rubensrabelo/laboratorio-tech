CREATE TABLE graduations (
    id BIGSERIAL PRIMARY KEY,
    modality_id BIGINT NOT NULL REFERENCES modalities(id),
    name VARCHAR(100) NOT NULL,
    UNIQUE (modality_id, name)
);
