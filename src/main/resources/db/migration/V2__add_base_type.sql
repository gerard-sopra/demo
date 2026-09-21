ALTER TABLE bases
    ADD COLUMN type VARCHAR(50);

UPDATE bases
SET type = 'FOB';

ALTER TABLE bases
    ALTER COLUMN type SET NOT NULL;