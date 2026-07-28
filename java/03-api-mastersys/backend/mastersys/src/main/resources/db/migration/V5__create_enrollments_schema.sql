CREATE TABLE enrollments (
    id BIGSERIAL PRIMARY KEY,
    student_id BIGINT NOT NULL REFERENCES students(id),
    enrollment_date DATE NOT NULL DEFAULT CURRENT_DATE,
    due_day INTEGER NOT NULL CHECK (due_day BETWEEN 1 AND 31),
    end_date DATE,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    CHECK (status IN ('ACTIVE', 'ENDED', 'CANCELED'))
);
