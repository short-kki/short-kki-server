ALTER TABLE source_content
    ADD COLUMN content_status VARCHAR(30) NOT NULL DEFAULT 'AVAILABLE',
    ADD COLUMN playable BOOLEAN NOT NULL DEFAULT TRUE;

UPDATE source_content
SET content_status = CASE WHEN is_active = true THEN 'AVAILABLE' ELSE 'UNAVAILABLE' END,
    playable = true;

ALTER TABLE source_content
    DROP COLUMN is_active;
