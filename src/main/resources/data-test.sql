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
VALUES (1, 'EN_COURS', 'AAAA');

INSERT INTO status (st_id, st_name, st_description)
VALUES (2, 'EN_VALIDATION', 'BBBB');

INSERT INTO status (st_id, st_name, st_description)
VALUES (3, 'VALIDE', 'CCCC');

INSERT INTO status (st_id, st_name, st_description)
VALUES (4, 'ARCHIVE', 'DDDD');

INSERT INTO subcontractor (s_name, s_email, s_fk_status_id )
VALUES ('Test', 'Test@email.com', 1);

