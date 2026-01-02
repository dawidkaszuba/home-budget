UPDATE categories c
SET home_id = u.home_id
FROM budget_users u
WHERE c.budget_user_id = u.id;
