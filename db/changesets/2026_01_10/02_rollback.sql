UPDATE budget_users u
SET
    username = c.email,
    password_hash = c.password_hash,
    auth_provider = 'LOCAL'
FROM user_credentials c
WHERE c.user_id = u.id
  AND c.provider = 'LOCAL';