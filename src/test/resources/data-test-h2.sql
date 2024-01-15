-- Inserting data into the gst_status table
INSERT INTO gst_status (st_id, st_name)
VALUES (1, 'En cours'),
       (2, 'En validation'),
       (3, 'Validé'),
       (4, 'Archivé');

-- Inserting data into the gst_category table
INSERT INTO gst_category (cat_id, cat_name)
VALUES (1, 'SP'),
       (2, 'SC'),
       (3, 'KBIS'),
       (4, 'REG');

-- Inserting data into the gst_message_model table
INSERT INTO gst_message_model (mm_link, mm_subject, mm_body, mm_has_email, mm_creation_date, mm_last_update,
                               mm_fk_category_id)
VALUES (1, 'Demande des documents administratifs du prestataire', 'je suis le body du mail', TRUE, CURRENT_TIMESTAMP,
        NULL, 1),
       (1, 'Relance des documents administratifs du prestataire', 'je suis le body du mail', TRUE, CURRENT_TIMESTAMP,
        NULL, 1),
       (2, 'Demande création à la comptabilité du prestataire', 'je suis le body du mail', FALSE, CURRENT_TIMESTAMP,
        NULL, 1),
       (2, 'Relance création à la comptabilité du prestataire', 'je suis le body du mail', FALSE, CURRENT_TIMESTAMP,
        NULL, 1),
       (3, 'Demande de signatures d''annexes du prestataire', 'je suis le body du mail', TRUE, CURRENT_TIMESTAMP, NULL,
        1);

-- Inserting data into the gst_subcontractor table
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id)
VALUES ('Orange', 'orange@email.fr', 1),
       ('BPCE', 'bpce@email.com', 1),
       ('EDF', 'edf@email.fr', 1),
       ('ENEDIS', 'enedis@email.fr', 1),
       ('LYON', 'lyon@email.fr', 1);

-- Inserting data into the gst_service_provider table
INSERT INTO gst_service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,
                                  sp_fk_subcontractor_id, sp_fk_status_id)
VALUES ('Lea', 'DUBOIS', 'leadubois@email.com', '2023-01-01 12:00:00', NULL, 1, 1),
       ('Louis', 'LEFEVRE', 'louislefevre@email.com', '2023-01-01 12:00:00', NULL, 1, 1),
       ('Emma', 'MARTIN', 'emmamartin@email.com', '2023-01-01 12:00:00', NULL, 1, 1),
       ('Lucas', 'BERNARD', 'lucasbernard@email.com', '2023-01-01 12:00:00', NULL, 1, 1),
       ('Chloe', 'DURAND', 'chloedurand@email.com', '2023-01-01 12:00:00', NULL, 1, 1);

-- Inserting data into the gst_contract table
--
INSERT INTO gst_contract (c_contract_number, c_fk_subcontractor_id, c_fk_service_provider_id)
SELECT 'AFR546CVGF' || sp.sp_id  AS c_contract_number,
       sp.sp_fk_subcontractor_id AS c_fk_subcontractor_id,
       sp.sp_id                  AS c_fk_service_provider_id
FROM gst_service_provider sp
LIMIT 5;

-- Inserting data into the gst_model_tracking table
INSERT INTO gst_model_tracking (mt_send_date, mt_validation_date, mt_fk_contract_id, mt_fk_message_model_id,
                                mt_fk_status_id, mt_fk_category_id)
SELECT NULL                 AS mt_send_date,
       NULL                 AS mt_validation_date,
       c.c_id               AS mt_fk_contract_id,
       mm.mm_id             AS mt_fk_message_model_id,
       s.sp_fk_status_id    AS mt_fk_status_id,
       mm.mm_fk_category_id AS mt_fk_category_id
FROM gst_service_provider s
         JOIN gst_contract c ON s.sp_id = c.c_fk_service_provider_id
         JOIN gst_message_model mm ON s.sp_fk_status_id IS NOT NULL
LIMIT 5;

INSERT INTO gst_contract (c_contract_number, c_fk_subcontractor_id, c_fk_service_provider_id)
SELECT 'AFR546CVGF' || sp.sp_id  AS c_contract_number,
       sp.sp_fk_subcontractor_id AS c_fk_subcontractor_id,
       sp.sp_id                  AS c_fk_service_provider_id
FROM gst_service_provider sp
LIMIT 5;

-- Inserting data into the gst_model_tracking table
INSERT INTO gst_model_tracking (mt_send_date, mt_validation_date, mt_fk_contract_id, mt_fk_message_model_id,
                                mt_fk_status_id, mt_fk_category_id)
SELECT NULL                 AS mt_send_date,
       NULL                 AS mt_validation_date,
       c.c_id               AS mt_fk_contract_id,
       mm.mm_id             AS mt_fk_message_model_id,
       s.sp_fk_status_id    AS mt_fk_status_id,
       mm.mm_fk_category_id AS mt_fk_category_id
FROM gst_service_provider s
         JOIN gst_contract c ON s.sp_id = c.c_fk_service_provider_id
         JOIN gst_message_model mm ON s.sp_fk_status_id IS NOT NULL
LIMIT 5;
