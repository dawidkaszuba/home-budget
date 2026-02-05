INSERT INTO user_credentials (
    provider,
    email,
    password_hash,
    user_id,
    created_at,
    updated_at
)
SELECT
    'LOCAL',
    username,
    password_hash,
    id,
    created_at,
    updated_at
FROM budget_users u
WHERE NOT EXISTS (
    SELECT 1
    FROM user_credentials c
    WHERE c.user_id = u.id
      AND c.provider = 'LOCAL'
);
