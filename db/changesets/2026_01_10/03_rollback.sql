ALTER TABLE budget_users ADD COLUMN username varchar(255);
ALTER TABLE budget_users ADD COLUMN password_hash varchar(255);
ALTER TABLE budget_users ADD COLUMN auth_provider varchar(255);