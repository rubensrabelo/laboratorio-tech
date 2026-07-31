-- 1. Populating 'tables' (10 tables)
INSERT INTO tables (number, description, capacity, status) VALUES
(1, 'Window seat', 2, 'AVAILABLE'),
(2, 'Window seat', 2, 'AVAILABLE'),
(3, 'Standard booth', 4, 'OCCUPIED'),
(4, 'Standard booth', 4, 'AVAILABLE'),
(5, 'Family table', 6, 'RESERVED'),
(6, 'Family table', 8, 'AVAILABLE'),
(7, 'Bar counter seat A', 1, 'OCCUPIED'),
(8, 'Bar counter seat B', 1, 'AVAILABLE'),
(9, 'Outdoor terrace', 4, 'AVAILABLE'),
(10, 'VIP Lounge', 6, 'INACTIVE');

-- 2. Populating 'product_categories' (6 categories)
INSERT INTO product_categories (name, active) VALUES
('Starters', true),
('Main Courses', true),
('Desserts', true),
('Soft Drinks', true),
('Beers & Wine', true),
('Special Offers', false);

-- 3. Populating 'products' (10 products)
INSERT INTO products (category_id, name, description, price, available, preparation_time_minutes) VALUES
(1, 'Garlic Bread', 'Toasted baguette with garlic butter and herbs', 5.50, true, 8),
(1, 'Buffalo Wings', 'Spicy chicken wings served with blue cheese dip', 9.90, true, 12),
(2, 'Classic Cheeseburger', 'Angus beef patty, cheddar, lettuce, tomato, special sauce', 14.50, true, 15),
(2, 'Ribeye Steak', '300g grilled ribeye with rustic fries', 28.00, true, 20),
(2, 'Margherita Pizza', 'Tomato sauce, fresh mozzarella, and basil', 12.00, true, 14),
(3, 'Chocolate Brownie', 'Warm brownie served with vanilla ice cream', 6.50, true, 5),
(3, 'Cheesecake', 'Classic New York style with red berry coulis', 7.00, true, 5),
(4, 'Coca-Cola Can', '350ml chilled can', 3.00, true, 2),
(4, 'Fresh Orange Juice', '400ml 100% natural juice', 4.50, true, 4),
(5, 'Craft IPA Beer', '500ml local artisanal beer', 7.50, true, 3);

-- 4. Populating 'orders' (6 orders)
INSERT INTO orders (table_id, opened_at, closed_at, status, notes) VALUES
(3, CURRENT_TIMESTAMP - INTERVAL '1 hour', NULL, 'PREPARING', 'Allergy to nuts'),
(7, CURRENT_TIMESTAMP - INTERVAL '30 minutes', NULL, 'OPEN', 'No ice in drinks'),
(1, CURRENT_TIMESTAMP - INTERVAL '2 hours', CURRENT_TIMESTAMP - INTERVAL '1 hour', 'CLOSED', NULL),
(2, CURRENT_TIMESTAMP - INTERVAL '3 hours', CURRENT_TIMESTAMP - INTERVAL '2 hours', 'CLOSED', 'Birthday celebration'),
(4, CURRENT_TIMESTAMP - INTERVAL '40 minutes', NULL, 'READY', NULL),
(9, CURRENT_TIMESTAMP - INTERVAL '15 minutes', NULL, 'CANCELLED', 'Customer left before ordering');

-- 5. Populating 'order_items' (8 items)
INSERT INTO order_items (order_id, product_id, quantity, unit_price, status, notes) VALUES
(1, 3, 2, 14.50, 'PREPARING', 'Medium rare'),
(1, 8, 2, 3.00, 'DELIVERED', NULL),
(2, 10, 2, 7.50, 'DELIVERED', NULL),
(3, 4, 1, 28.00, 'DELIVERED', 'Well done'),
(3, 9, 1, 4.50, 'DELIVERED', NULL),
(3, 6, 1, 6.50, 'DELIVERED', NULL),
(4, 5, 1, 12.00, 'DELIVERED', NULL),
(5, 2, 1, 9.90, 'READY', 'Extra spicy sauce');

-- 6. Populating 'payments' (5 records)
INSERT INTO payments (order_id, amount, payment_method, status, external_transaction_code, paid_at) VALUES
(3, 42.90, 'CREDIT_CARD', 'APPROVED', 'TX_99823145', CURRENT_TIMESTAMP - INTERVAL '1 hour'),
(4, 13.20, 'PIX', 'APPROVED', 'PIX_7721903', CURRENT_TIMESTAMP - INTERVAL '2 hours'),
(1, 35.00, 'CASH', 'PENDING', NULL, NULL),
(2, 15.00, 'DEBIT_CARD', 'APPROVED', 'TX_11029348', CURRENT_TIMESTAMP - INTERVAL '2 hours'),
(5, 9.90, 'CREDIT_CARD', 'DECLINED', 'TX_88321044', NULL);
