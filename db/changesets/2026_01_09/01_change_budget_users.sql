ALTER TABLE budget_users RENAME COLUMN user_name TO username;
ALTER TABLE budget_users ADD COLUMN email VARCHAR(255);
ALTER TABLE budget_users RENAME COLUMN roles TO role;
UPDATE budget_users SET role = 'ADMIN' WHERE role LIKE '%ADMIN%';
UPDATE budget_users SET role = 'USER' WHERE role LIKE '%USER%';
UPDATE budget_users SET role = 'USER' WHERE role IS NULL;
ALTER TABLE budget_users ALTER COLUMN username SET NOT NULL, ALTER COLUMN role SET NOT NULL;
CREATE UNIQUE INDEX ux_budget_users_username ON budget_users(username);
CREATE UNIQUE INDEX ux_budget_users_email ON budget_users(email);

ALTER TABLE budget_users RENAME COLUMN password TO password_hash;
ALTER TABLE budget_users ADD COLUMN auth_provider VARCHAR(30) DEFAULT 'LOCAL' NOT NULL;
UPDATE budget_users SET auth_provider = 'LOCAL' WHERE auth_provider IS NULL;