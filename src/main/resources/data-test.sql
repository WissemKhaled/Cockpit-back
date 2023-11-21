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

CREATE TABLE IF NOT EXISTS u_user (
    u_id SERIAL PRIMARY KEY,
    u_email VARCHAR(45) NOT NULL,
    u_password VARCHAR(255) NOT NULL,
    u_first_name VARCHAR(45) DEFAULT NULL,
    u_last_name VARCHAR(45) DEFAULT NULL,
    u_status BOOLEAN DEFAULT FALSE,
    u_insertion_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    u_last_update TIMESTAMP
);

CREATE TABLE IF NOT EXISTS refresh_token (
    rt_id SERIAL PRIMARY KEY,
    rt_token VARCHAR(255) NOT NULL,
    rt_expiry_date TIMESTAMP NOT NULL,
    rt_fk_user_id SMALLINT NOT NULL,
    FOREIGN KEY (rt_fk_user_id) REFERENCES u_user(u_id)
);

CREATE TABLE IF NOT EXISTS service_provider (
    sp_id SERIAL PRIMARY KEY,
    sp_first_name VARCHAR(250) NOT NULL,
    sp_name VARCHAR(250) NOT NULL,
    sp_email VARCHAR(45) NOT NULL,
    sp_creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    sp_lastUpdate_date TIMESTAMP,
    sp_fk_subcontractor_id SMALLINT NOT NULL, 
    FOREIGN KEY (sp_fk_subcontractor_id) REFERENCES subcontractor(s_id),
    sp_fk_status_id SMALLINT NOT NULL, 
    FOREIGN KEY (sp_fk_status_id) REFERENCES status(st_id)
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

-- Prestataire 1
INSERT INTO service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id)
VALUES ('sp1', 'sp1_name' ,'ENDIS@email.fr', '2023-01-01 12:00:00', NULL,1 , 3);
