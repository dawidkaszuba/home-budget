ALTER TABLE user_credentials ADD COLUMN enabled boolean;
ALTER TABLE user_credentials ADD COLUMN activation_token varchar(255);