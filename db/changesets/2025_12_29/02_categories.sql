CREATE TABLE categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    category_type VARCHAR(50) NOT NULL,
    budget_user_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,

    CONSTRAINT chk_category_type
            CHECK (category_type IN ('INCOME', 'EXPENSE')),

    CONSTRAINT fk_category_user
        FOREIGN KEY (budget_user_id)
        REFERENCES budget_users(id),

    CONSTRAINT uq_category_user_type_name
        UNIQUE (budget_user_id, category_type, name)
);
