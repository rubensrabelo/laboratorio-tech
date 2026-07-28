-- 1. Inserir Alunos (Students)
INSERT INTO students (name, birth_date, gender, phone, cell_phone, email, observation, address, number, complement, neighborhood, city, state, zip_code)
VALUES 
('John Doe', '1995-05-15', 'M', '1122223333', '11999998888', 'john.doe@email.com', 'Nenhuma observação', 'Main Street', '123', 'Apt 42', 'Downtown', 'Springfield', 'SP', '01000000'),
('Jane Smith', '1998-08-22', 'F', '1144445555', '11988887777', 'jane.smith@email.com', 'Alergia a poeira', 'Oak Avenue', '456', NULL, 'Green Valley', 'Metropolis', 'RJ', '20000000');

-- 2. Inserir Modalidades (Modalities)
INSERT INTO modalities (name, active)
VALUES 
('Brazilian Jiu-Jitsu', TRUE),
('Muay Thai', TRUE),
('Natação', FALSE);

-- 3. Inserir Graduações (Graduations)
INSERT INTO graduations (modality_id, name)
VALUES 
(1, 'Faixa Branca'),
(1, 'Faixa Azul'),
(2, 'Grau Branco'),
(2, 'Grau Vermelho');

-- 4. Inserir Planos (Plans)
INSERT INTO plans (modality_id, name, monthly_value, active)
VALUES 
(1, 'Anual BJJ', 150.00, TRUE),
(1, 'Mensal BJJ', 180.00, TRUE),
(2, 'Mensal Muay Thai', 160.00, TRUE);

-- 5. Inserir Matrículas (Enrollments)
INSERT INTO enrollments (student_id, enrollment_date, due_day, end_date, status)
VALUES 
(1, CURRENT_DATE - INTERVAL '30 days', 10, NULL, 'ACTIVE'),
(2, CURRENT_DATE - INTERVAL '15 days', 15, NULL, 'ACTIVE');

-- 6. Relacionar Matrículas com as Modalidades (Enrollment Modalities)
INSERT INTO enrollment_modalities (enrollment_id, modality_id, graduation_id, plan_id, start_date, end_date)
VALUES 
(1, 1, 1, 1, CURRENT_DATE - INTERVAL '30 days', NULL),
(2, 2, 3, 3, CURRENT_DATE - INTERVAL '15 days', NULL);

-- 7. Inserir Faturas (Enrollment Invoices)
INSERT INTO enrollment_invoices (enrollment_id, due_date, amount, payment_date, cancellation_date, status)
VALUES 
(1, CURRENT_DATE - INTERVAL '10 days', 150.00, CURRENT_TIMESTAMP - INTERVAL '10 days', NULL, 'PAID'),
(1, CURRENT_DATE + INTERVAL '20 days', 150.00, NULL, NULL, 'OPEN'),
(2, CURRENT_DATE + INTERVAL '15 days', 160.00, NULL, NULL, 'OPEN');

-- 8. Inserir Assiduidade/Presença (Attendance)
INSERT INTO attendance (enrollment_id, check_in_time, check_out_time)
VALUES 
(1, CURRENT_TIMESTAMP - INTERVAL '2 days' - INTERVAL '1 hour', CURRENT_TIMESTAMP - INTERVAL '2 days'),
(1, CURRENT_TIMESTAMP - INTERVAL '1 day' - INTERVAL '1 hour', CURRENT_TIMESTAMP - INTERVAL '1 day'),
(2, CURRENT_TIMESTAMP - INTERVAL '5 hours', CURRENT_TIMESTAMP - INTERVAL '4 hours');
