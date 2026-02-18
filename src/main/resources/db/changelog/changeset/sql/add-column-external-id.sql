ALTER TABLE IF EXISTS goodEntity
    ADD COLUMN external_id varchar(100) NOT NULL UNIQUE;