CREATE TABLE user_credentials (
    id BIGSERIAL PRIMARY KEY,

    provider VARCHAR(30) NOT NULL,
    provider_user_id VARCHAR(255),

    email VARCHAR(255),
    password_hash VARCHAR(255),

    user_id BIGINT NOT NULL,

    created_at TIMESTAMP,
    updated_at TIMESTAMP,

    CONSTRAINT fk_user_credentials_user
        FOREIGN KEY (user_id)
        REFERENCES budget_users(id)
);

CREATE UNIQUE INDEX ux_user_credentials_provider_email
    ON user_credentials (provider, email)
    WHERE email IS NOT NULL;

CREATE UNIQUE INDEX ux_user_credentials_provider_provider_user_id
    ON user_credentials (provider, provider_user_id)
    WHERE provider_user_id IS NOT NULL;
