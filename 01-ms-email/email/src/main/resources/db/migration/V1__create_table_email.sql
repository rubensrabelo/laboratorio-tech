CREATE TABLE tb_email (
    id UUID PRIMARY KEY NOT NULL DEFAULT gen_random_uuid(),
    email_to VARCHAR(255) NOT NULL,
    email_from VARCHAR(255) NOT NULL,
    subject VARCHAR(255) NOT NULL,
    text TEXT NOT NULL,
    status_email VARCHAR(10) NOT NULL CHECK (status_email IN ('SENT', 'ERROR')),
    user_id UUID NOT NULL
);
