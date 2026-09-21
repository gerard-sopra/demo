CREATE TABLE shipments
(
    id                  UUID PRIMARY KEY,
    supply_request_id   UUID        NOT NULL UNIQUE,
    source_base_id      UUID        NOT NULL,
    destination_base_id UUID        NOT NULL,
    supply_type         VARCHAR(50) NOT NULL,
    quantity            INTEGER     NOT NULL,
    status              VARCHAR(50) NOT NULL,
    created_at          TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT fk_shipment_request
        FOREIGN KEY (supply_request_id)
            REFERENCES supply_requests (id),

    CONSTRAINT fk_shipment_source
        FOREIGN KEY (source_base_id)
            REFERENCES bases (id),

    CONSTRAINT fk_shipment_destination
        FOREIGN KEY (destination_base_id)
            REFERENCES bases (id)
);