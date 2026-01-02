INSERT INTO budget_users
(first_name, last_name, user_name, password, roles)
VALUES
('Admin', 'Admin', 'admin',
 '$2a$12$qgWzpbD2l1HFbpJA2M72leJe8tno1BvckdSpErzMPzoO1hMEPczeu',
 'ROLE_ADMIN,ROLE_USER'),

('User', 'User', 'user',
 '$2a$12$qgWzpbD2l1HFbpJA2M72leJe8tno1BvckdSpErzMPzoO1hMEPczeu',
 'ROLE_USER');
