
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
VALUES ('Subcontractor 1', 'subcontractor1@example.com', 1);

-- Subcontractor 2
INSERT INTO subcontractor (s_name, s_email, s_fk_status_id)
VALUES ('Subcontractor 2', 'subcontractor2@example.com', 2);

-- Subcontractor 3
INSERT INTO subcontractor (s_name, s_email, s_creation_date, s_fk_status_id)
VALUES ('Subcontractor 3', 'subcontractor3@example.com', '2023-01-01 12:00:00', 1);

-- Subcontractor 4
INSERT INTO subcontractor (s_name, s_email, s_creation_date, s_lastUpdate_date, s_fk_status_id)
VALUES ('Subcontractor 4', 'subcontractor4@example.com', '2023-01-01 12:00:00', NULL, 3);

-- Subcontractor 5
INSERT INTO subcontractor (s_name, s_email, s_fk_status_id)
VALUES ('Subcontractor 5', 'subcontractor5@example.com', 2);

