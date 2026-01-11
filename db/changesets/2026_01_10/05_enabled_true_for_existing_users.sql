UPDATE user_credentials
SET enabled = true
WHERE enabled IS NULL;

ALTER TABLE user_credentials
ALTER COLUMN enabled SET NOT NULL;

ALTER TABLE user_credentials
ALTER COLUMN enabled SET DEFAULT true;
