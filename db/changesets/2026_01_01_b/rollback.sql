ALTER TABLE expenses ADD COLUMN budget_user_id BIGINT;
ALTER TABLE expenses
    ADD CONSTRAINT fk_expense_user
    FOREIGN KEY (budget_user_id) REFERENCES budget_users(id);
CREATE INDEX idx_expenses_user ON expenses(budget_user_id);

-- Incomes rollback
ALTER TABLE incomes ADD COLUMN budget_user_id BIGINT;
ALTER TABLE incomes
    ADD CONSTRAINT fk_income_user
    FOREIGN KEY (budget_user_id) REFERENCES budget_users(id);
CREATE INDEX idx_incomes_user ON incomes(budget_user_id);