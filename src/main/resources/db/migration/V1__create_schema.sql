CREATE TABLE bases
(
    id       UUID PRIMARY KEY,
    name     VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL
);

CREATE TABLE inventory
(
    id          UUID PRIMARY KEY,
    base_id     UUID        NOT NULL,
    supply_type VARCHAR(50) NOT NULL,
    quantity    INTEGER     NOT NULL,

    CONSTRAINT fk_inventory_base
        FOREIGN KEY (base_id)
            REFERENCES bases (id)
);

CREATE TABLE supply_requests
(
    id          UUID PRIMARY KEY,
    base_id     UUID                     NOT NULL,
    supply_type VARCHAR(50)              NOT NULL,
    quantity    INTEGER                  NOT NULL,
    priority    VARCHAR(50)              NOT NULL,
    status      VARCHAR(50)              NOT NULL,
    created_at  TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT fk_request_base
        FOREIGN KEY (base_id)
            REFERENCES bases (id)
);