CREATE TABLE transfers (
    id                BIGSERIAL     PRIMARY KEY,
    home_id           BIGINT        NOT NULL REFERENCES homes(id),
    source_account_id BIGINT        NOT NULL REFERENCES accounts(id),
    target_account_id BIGINT        NOT NULL REFERENCES accounts(id),
    value             NUMERIC(15,2) NOT NULL,
    note              VARCHAR(255),
    created_at        TIMESTAMP,
    updated_at        TIMESTAMP,
    deleted_at        TIMESTAMP
);
