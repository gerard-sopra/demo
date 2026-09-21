INSERT INTO bases (id, name, location, type)
VALUES
    ('11111111-1111-1111-1111-111111111111',
     'FSB Hammer',
     'Pleiku',
     'FOB'),

    ('22222222-2222-2222-2222-222222222222',
     'FSB Cobra',
     'Dak To',
     'FOB'),

    ('33333333-3333-3333-3333-333333333333',
     'MACV-SOG FOB',
     'Da Nang',
     'FOB'),

    ('44444444-4444-4444-4444-444444444444',
     'Pleiku Logistics Depot',
     'Pleiku',
     'SUPPLY_DEPOT');


-- =====================================================
-- FSB Hammer inventory
-- =====================================================

INSERT INTO inventory (id, base_id, supply_type, quantity)
VALUES
    ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa1',
     '11111111-1111-1111-1111-111111111111',
     'AMMUNITION',
     5000),

    ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa2',
     '11111111-1111-1111-1111-111111111111',
     'FUEL',
     2000),

    ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa3',
     '11111111-1111-1111-1111-111111111111',
     'FOOD',
     1500),

    ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa4',
     '11111111-1111-1111-1111-111111111111',
     'MEDICAL',
     400);


-- =====================================================
-- FSB Cobra inventory
-- =====================================================

INSERT INTO inventory (id, base_id, supply_type, quantity)
VALUES
    ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbb1',
     '22222222-2222-2222-2222-222222222222',
     'AMMUNITION',
     3000),

    ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbb2',
     '22222222-2222-2222-2222-222222222222',
     'FUEL',
     1200),

    ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbb3',
     '22222222-2222-2222-2222-222222222222',
     'FOOD',
     1000),

    ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbb4',
     '22222222-2222-2222-2222-222222222222',
     'MEDICAL',
     250);


-- =====================================================
-- MACV-SOG FOB inventory
-- =====================================================

INSERT INTO inventory (id, base_id, supply_type, quantity)
VALUES
    ('cccccccc-cccc-cccc-cccc-ccccccccccc1',
     '33333333-3333-3333-3333-333333333333',
     'AMMUNITION',
     4000),

    ('cccccccc-cccc-cccc-cccc-ccccccccccc2',
     '33333333-3333-3333-3333-333333333333',
     'FUEL',
     1800),

    ('cccccccc-cccc-cccc-cccc-ccccccccccc3',
     '33333333-3333-3333-3333-333333333333',
     'FOOD',
     1200),

    ('cccccccc-cccc-cccc-cccc-ccccccccccc4',
     '33333333-3333-3333-3333-333333333333',
     'MEDICAL',
     350);


-- =====================================================
-- Pleiku Logistics Depot inventory
-- =====================================================

INSERT INTO inventory (id, base_id, supply_type, quantity)
VALUES
    ('dddddddd-dddd-dddd-dddd-ddddddddddd1',
     '44444444-4444-4444-4444-444444444444',
     'AMMUNITION',
     50000),

    ('dddddddd-dddd-dddd-dddd-ddddddddddd2',
     '44444444-4444-4444-4444-444444444444',
     'FUEL',
     30000),

    ('dddddddd-dddd-dddd-dddd-ddddddddddd3',
     '44444444-4444-4444-4444-444444444444',
     'FOOD',
     20000),

    ('dddddddd-dddd-dddd-dddd-ddddddddddd4',
     '44444444-4444-4444-4444-444444444444',
     'MEDICAL',
     5000);