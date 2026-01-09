DROP INDEX IF EXISTS ux_budget_users_username;
DROP INDEX IF EXISTS ux_budget_users_email;

ALTER TABLE budget_users
    ALTER COLUMN username DROP NOT NULL,
    ALTER COLUMN role DROP NOT NULL;

ALTER TABLE budget_users
    RENAME COLUMN username TO user_name;

ALTER TABLE budget_users
    RENAME COLUMN role TO roles;

ALTER TABLE budget_users
    DROP COLUMN email;


ALTER TABLE budget_users
    DROP COLUMN auth_provider;

ALTER TABLE budget_users
    RENAME COLUMN password_hash TO password;
