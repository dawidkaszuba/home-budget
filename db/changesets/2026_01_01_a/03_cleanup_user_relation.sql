ALTER TABLE categories
DROP CONSTRAINT IF EXISTS uq_category_user_type_name;

ALTER TABLE categories
DROP CONSTRAINT IF EXISTS fk_category_user;

ALTER TABLE categories
DROP COLUMN IF EXISTS budget_user_id;

ALTER TABLE categories
ALTER COLUMN home_id SET NOT NULL;

ALTER TABLE categories
ADD CONSTRAINT uq_category_home_type_name
UNIQUE (home_id, category_type, name);
