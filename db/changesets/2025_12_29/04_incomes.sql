CREATE TABLE incomes (
    id BIGSERIAL PRIMARY KEY,
    value NUMERIC(15,2) NOT NULL,
    budget_user_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,

    CONSTRAINT fk_income_user
        FOREIGN KEY (budget_user_id)
        REFERENCES budget_users(id),

    CONSTRAINT fk_income_category
        FOREIGN KEY (category_id)
        REFERENCES categories(id)
);

CREATE INDEX idx_incomes_user ON incomes(budget_user_id);
CREATE INDEX idx_incomes_category ON incomes(category_id);
