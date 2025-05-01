ALTER TABLE book
    ADD COLUMN created_by varchar(255);

ALTER TABLE book
    ADD COLUMN last_modified_by varchar(255);

UPDATE book
    SET created_by = 'unknown', last_modified_by = 'unknown';