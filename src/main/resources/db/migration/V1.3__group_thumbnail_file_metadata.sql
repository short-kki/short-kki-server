ALTER TABLE member_group ADD COLUMN thumbnail_img_file_id BIGINT;
ALTER TABLE member_group ADD CONSTRAINT fk_group_thumbnail_file
    FOREIGN KEY (thumbnail_img_file_id) REFERENCES file_metadata(id);
ALTER TABLE member_group DROP COLUMN thumbnail_img_url;
