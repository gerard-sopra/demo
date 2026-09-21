ALTER TABLE supply_requests
    ADD COLUMN source_depot_id UUID;

ALTER TABLE supply_requests
    ADD CONSTRAINT fk_request_source_depot
        FOREIGN KEY (source_depot_id)
            REFERENCES bases (id);