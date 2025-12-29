CREATE TABLE expenses (
    id BIGSERIAL PRIMARY KEY,
    value NUMERIC(15,2) NOT NULL,
    budget_user_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,

    CONSTRAINT fk_expense_user
        FOREIGN KEY (budget_user_id)
        REFERENCES budget_users(id),

    CONSTRAINT fk_expense_category
        FOREIGN KEY (category_id)
        REFERENCES categories(id)
);

CREATE INDEX idx_expenses_user ON expenses(budget_user_id);
CREATE INDEX idx_expenses_category ON expenses(category_id);
