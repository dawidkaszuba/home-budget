ALTER TABLE categories
DROP CONSTRAINT uq_category_home_type_name;

ALTER TABLE categories
ADD COLUMN budget_user_id BIGINT;

ALTER TABLE categories
ADD CONSTRAINT fk_category_user
FOREIGN KEY (budget_user_id)
REFERENCES budget_users(id);

UPDATE categories c
SET budget_user_id = (
    SELECT u.id
    FROM budget_users u
    WHERE u.home_id = c.home_id
    ORDER BY u.id
    LIMIT 1
);

ALTER TABLE categories
ALTER COLUMN budget_user_id SET NOT NULL;

ALTER TABLE categories DROP CONSTRAINT fk_category_home;
ALTER TABLE categories DROP COLUMN home_id;
