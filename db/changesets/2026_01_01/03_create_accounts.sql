CREATE TABLE accounts (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    home_id BIGINT NOT NULL,
    owner_id BIGINT,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,

    CONSTRAINT fk_account_home
        FOREIGN KEY (home_id)
        REFERENCES homes(id),

    CONSTRAINT fk_account_owner
        FOREIGN KEY (owner_id)
        REFERENCES budget_users(id)
);
