INSERT INTO users (username, password, email, full_name, role)
VALUES
    ('admin1', 'adminpass', 'admin1@example.com', 'Admin User', 'admin'),
    ('manager1', 'managerpass', 'manager1@example.com', 'Manager User', 'manager'),
    ('user1', 'userpass', 'user1@example.com', 'Regular User', 'user');

INSERT INTO products (name, description, price, quantity)
VALUES
    ('Widget A', 'High-quality widget A', 19.99, 100),
    ('Gadget B', 'Reliable gadget B', 29.99, 50),
    ('Device C', 'Advanced device C', 49.99, 20);

INSERT INTO inventory_operations (product_id, user_id, quantity, comment)
VALUES
    ((SELECT id FROM products WHERE name = 'Widget A'), (SELECT id FROM users WHERE username = 'admin1'), 10, 'Initial stock'),
    ((SELECT id FROM products WHERE name = 'Gadget B'), (SELECT id FROM users WHERE username = 'manager1'), -5, 'Sold 5 units'),
    ((SELECT id FROM products WHERE name = 'Device C'), (SELECT id FROM users WHERE username = 'user1'), 15, 'Restocked by user');
