CREATE TABLE students (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    birth_date DATE,
    gender VARCHAR(1) CHECK (gender IN ('M', 'F')),
    phone VARCHAR(30),
    cell_phone VARCHAR(30),
    email VARCHAR(150) UNIQUE,
    observation TEXT,
    address VARCHAR(150),
    number VARCHAR(20),
    complement VARCHAR(100),
    neighborhood VARCHAR(100),
    city VARCHAR(100),
    state VARCHAR(2),
    zip_code VARCHAR(20),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);
