INSERT INTO homes (name)
VALUES ('Dom');

UPDATE budget_users
SET home_id = (SELECT id FROM homes WHERE user_name in ('admin', 'user'));

INSERT INTO accounts (name, home_id)
VALUES (
    'Domowe',
    (SELECT id FROM homes WHERE name = 'Dom')
);

UPDATE expenses
SET account_id = (SELECT id FROM accounts WHERE name = 'Domowe');

UPDATE incomes
SET account_id = (SELECT id FROM accounts WHERE name = 'Domowe');
