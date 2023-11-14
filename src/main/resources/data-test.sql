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

CREATE TABLE IF NOT EXISTS status (
    st_id SERIAL PRIMARY KEY,
    st_name VARCHAR(50) NOT NULL,
    st_description VARCHAR(255) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS subcontractor (
    s_id SERIAL PRIMARY KEY,
    s_name VARCHAR(250) NOT NULL,
    s_email VARCHAR(45) NOT NULL,
    s_fk_status_id SMALLINT NOT NULL, 
    FOREIGN KEY (s_fk_status_id) REFERENCES status(st_id)
);

INSERT INTO status (st_id, st_name, st_description)
VALUES (1, 'En cours', 'AAAA');

INSERT INTO status (st_id, st_name, st_description)
VALUES (2, 'En validation', 'BBBB');

INSERT INTO status (st_id, st_name, st_description)
VALUES (3, 'Validé', 'CCCC');

INSERT INTO status (st_id, st_name, st_description)
VALUES (4, 'Archivé', 'DDDD');

INSERT INTO subcontractor (s_name, s_email, s_fk_status_id )
VALUES ('Test', 'Test@email.com', 1);
