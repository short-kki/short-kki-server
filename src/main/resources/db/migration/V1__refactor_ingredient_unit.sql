-- Add unit column to recipe_ingredient table (nullable first for safe migration)
ALTER TABLE recipe_ingredient ADD COLUMN unit VARCHAR(50);

-- Migrate existing unit data from ingredient to recipe_ingredient
UPDATE recipe_ingredient ri
    INNER JOIN ingredient i ON ri.ingredient_id = i.id
SET ri.unit = i.unit;

-- Make unit column NOT NULL after data migration
ALTER TABLE recipe_ingredient MODIFY COLUMN unit VARCHAR(50) NOT NULL;

-- Remove unit column from ingredient table
ALTER TABLE ingredient DROP COLUMN unit;
