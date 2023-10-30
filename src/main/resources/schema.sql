CREATE TABLE IF NOT EXISTS status (
    st_id SERIAL PRIMARY KEY,
    st_name VARCHAR(50) NOT NULL,
    st_description VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS subcontractor (
    s_id SERIAL PRIMARY KEY,
    s_name VARCHAR(250) NOT NULL,
    s_email VARCHAR(45) NOT NULL,
    s_fk_status_id SMALLINT NOT NULL, 
    FOREIGN KEY (s_fk_status_id) REFERENCES status(st_id)
);
