ALTER TABLE expenses
ADD COLUMN account_id BIGINT;

ALTER TABLE incomes
ADD COLUMN account_id BIGINT;

ALTER TABLE expenses
ADD CONSTRAINT fk_expense_account
FOREIGN KEY (account_id)
REFERENCES accounts(id);

ALTER TABLE incomes
ADD CONSTRAINT fk_income_account
FOREIGN KEY (account_id)
REFERENCES accounts(id);
