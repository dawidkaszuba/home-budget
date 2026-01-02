ALTER TABLE expenses DROP CONSTRAINT fk_expense_account;
ALTER TABLE incomes DROP CONSTRAINT fk_income_account;

ALTER TABLE expenses DROP COLUMN account_id;
ALTER TABLE incomes DROP COLUMN account_id;

DROP TABLE accounts;

ALTER TABLE budget_users DROP CONSTRAINT fk_user_home;
ALTER TABLE budget_users DROP COLUMN home_id;

DROP TABLE homes;
