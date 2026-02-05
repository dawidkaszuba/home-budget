ALTER TABLE user_credentials
ALTER COLUMN enabled DROP NOT NULL;

ALTER TABLE user_credentials
ALTER COLUMN enabled DROP DEFAULT;