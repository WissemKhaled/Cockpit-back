INSERT INTO status (st_id, st_name, st_description)
VALUES (1, 'En cours', NULL);

INSERT INTO status (st_id, st_name, st_description)
VALUES (2, 'En validation', NULL);

INSERT INTO status (st_id, st_name, st_description)
VALUES (3, 'Validé', NULL);

INSERT INTO status (st_id, st_name, st_description)
VALUES (4, 'Archivé', NULL);

-- Subcontractor 1
INSERT INTO subcontractor (s_name, s_email, s_fk_status_id)
VALUES ('Orange', 'Orange@email.fr', 1);

-- Subcontractor 2
INSERT INTO subcontractor (s_name, s_email, s_fk_status_id)
VALUES ('BPCE', 'BPCE@email.com', 2);

-- Subcontractor 3
INSERT INTO subcontractor (s_name, s_email, s_creation_date, s_fk_status_id)
VALUES ('EDF', 'EDF@email.fr', '2023-01-01 12:00:00', 1);

-- Subcontractor 4
INSERT INTO subcontractor (s_name, s_email, s_creation_date, s_lastUpdate_date, s_fk_status_id)
VALUES ('ENEDIS', 'ENDIS@email.fr', '2023-01-01 12:00:00', NULL, 3);

-- Subcontractor 5
INSERT INTO subcontractor (s_name, s_email, s_fk_status_id)
VALUES ('LYON', 'LYON@email.fr', 2);

-- Prestataire 1
INSERT INTO service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id)
VALUES ('sp-1-first_name', 'sp-1-name' ,'sp1@email.com', '2023-01-01 12:00:00', NULL,1 , 3);

-- Prestataire 2
INSERT INTO service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id)
VALUES ('sp-2-first_name', 'sp-2-name' ,'sp2@email.com', '2023-01-01 12:00:00', NULL,1 , 1);

-- Prestataire 3
INSERT INTO service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id)
VALUES ('sp-3-first-name', 'sp-3-name' ,'sp3@email.com', '2023-01-01 12:00:00', NULL,2 , 3);

-- Prestataire 4
INSERT INTO service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id)
VALUES ('sp-4-first-name', 'sp-4-name' ,'sp4@email.com', '2023-01-01 12:00:00', NULL,2 , 4);