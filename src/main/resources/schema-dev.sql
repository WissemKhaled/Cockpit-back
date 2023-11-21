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

CREATE TABLE IF NOT EXISTS email_model (
    em_id SERIAL PRIMARY KEY,
    em_name VARCHAR(45) NOT NULL,
    em_model TEXT NOT NULL,
    em_creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS email_send (
    es_id SERIAL PRIMARY KEY,
    es_mail_sender VARCHAR(45) DEFAULT NULL,
    es_mail_recipient VARCHAR(45) DEFAULT NULL,
    es_creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    es_fk_model_email_id SMALLINT NOT NULL,
    FOREIGN KEY (es_fk_model_email_id) REFERENCES email_model(em_id)
);