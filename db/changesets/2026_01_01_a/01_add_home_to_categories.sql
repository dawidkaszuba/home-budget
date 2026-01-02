ALTER TABLE categories
ADD COLUMN home_id BIGINT;

ALTER TABLE categories
ADD CONSTRAINT fk_category_home
FOREIGN KEY (home_id)
REFERENCES homes(id);
