-- Add parsed_content column to source_import_history table for storing AI-parsed recipe data
ALTER TABLE source_import_history ADD COLUMN parsed_content TEXT;
