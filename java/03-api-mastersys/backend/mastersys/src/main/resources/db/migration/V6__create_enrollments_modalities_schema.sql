CREATE TABLE enrollment_modalities (
    id BIGSERIAL PRIMARY KEY,
    enrollment_id BIGINT NOT NULL REFERENCES enrollments(id),
    modality_id BIGINT NOT NULL REFERENCES modalities(id),
    graduation_id BIGINT NOT NULL REFERENCES graduations(id),
    plan_id BIGINT NOT NULL REFERENCES plans(id),
    start_date DATE NOT NULL DEFAULT CURRENT_DATE,
    end_date DATE,
    UNIQUE (enrollment_id, modality_id)
);
