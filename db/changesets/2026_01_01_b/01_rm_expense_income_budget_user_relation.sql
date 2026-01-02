-- Expenses
ALTER TABLE expenses DROP CONSTRAINT IF EXISTS fk_expense_user;
DROP INDEX IF EXISTS idx_expenses_user;
ALTER TABLE expenses DROP COLUMN IF EXISTS budget_user_id;

-- Incomes
ALTER TABLE incomes DROP CONSTRAINT IF EXISTS fk_income_user;
DROP INDEX IF EXISTS idx_incomes_user;
ALTER TABLE incomes DROP COLUMN IF EXISTS budget_user_id;