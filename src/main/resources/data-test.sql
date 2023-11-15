CREATE TABLE IF NOT EXISTS status (
    st_id SERIAL PRIMARY KEY,
    st_name VARCHAR(50) NOT NULL,
    st_description VARCHAR(255) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS subcontractor (
    s_id SERIAL PRIMARY KEY,
    s_name VARCHAR(250) NOT NULL,
    s_email VARCHAR(45) NOT NULL,
    s_creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    s_lastUpdate_date TIMESTAMP,
    s_fk_status_id SMALLINT NOT NULL, 
    FOREIGN KEY (s_fk_status_id) REFERENCES status(st_id)
);

INSERT INTO status (st_id, st_name, st_description)
VALUES (1, 'En cours', NULL);

INSERT INTO status (st_id, st_name, st_description)
VALUES (2, 'En validation', NULL);

INSERT INTO status (st_id, st_name, st_description)
VALUES (3, 'Validé', NULL);

INSERT INTO status (st_id, st_name, st_description)
VALUES (4, 'Archivé', NULL);

-- Subcontractor for testing the get methode
INSERT INTO subcontractor (s_name, s_email, s_creation_date, s_lastUpdate_date, s_fk_status_id)
VALUES ('Subcontractor 1', 'subcontractor1@example.com', '2023-01-01 12:00:00', NULL, 1);

-- Subcontractor for testing the archive methode (Already archived behaviour)
INSERT INTO subcontractor (s_name, s_email, s_creation_date, s_lastUpdate_date, s_fk_status_id)
VALUES ('ArchivedSubcontractor', 'ArchivedSubcontractor@example.com', '2023-01-01 12:00:00', NULL, 4);
