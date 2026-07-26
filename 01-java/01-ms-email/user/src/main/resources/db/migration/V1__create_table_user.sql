CREATE TABLE tb_user (
    id UUID PRIMARY KEY NOT NULL DEFAULT gen_random_uuid(),
    name VARCHAR(50) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL
);
