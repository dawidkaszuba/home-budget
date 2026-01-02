ALTER TABLE budget_users
ADD COLUMN home_id BIGINT;

ALTER TABLE budget_users
ADD CONSTRAINT fk_user_home
FOREIGN KEY (home_id)
REFERENCES homes(id);
