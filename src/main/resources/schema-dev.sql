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

CREATE TABLE IF NOT EXISTS gst_log (
    log_id SERIAL PRIMARY KEY,
    log_type VARCHAR(45) NOT NULL,
    log_email VARCHAR(255) NOT NULL,
    log_password VARCHAR(255) DEFAULT NULL,
    log_value VARCHAR(45),
    log_creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    log_last_update TIMESTAMP
);

CREATE TABLE IF NOT EXISTS gst_message_model (
    mm_id SERIAL PRIMARY KEY,
    mm_category VARCHAR(45) NOT NULL,
    mm_type VARCHAR(45) NOT NULL,
    mm_subject VARCHAR(255) NOT NULL,
    mm_body TEXT NOT NULL,
    mm_creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    mm_last_update TIMESTAMP,
    mm_fk_status_id SMALLINT NOT NULL,
    FOREIGN KEY (mm_fk_status_id) REFERENCES status(st_id)
);

CREATE TABLE IF NOT EXISTS message_send (
    ms_id SERIAL PRIMARY KEY,
    ms_sender VARCHAR(55) NOT NULL,
    ms_to VARCHAR(55) DEFAULT NULL,
    ms_cc TEXT DEFAULT NULL,
    ms_subject VARCHAR(255) NOT NULL,
    ms_body TEXT NOT NULL,
    ms_error varchar(250),
    ms_status SMALLINT,
    ms_creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS gst_status_model_service_provider (
    status_msp_id SERIAL PRIMARY KEY,
    status_msp_fk_service_provider_id SERIAL NOT NULL,
    status_msp_fk_message_model_id SERIAL NOT NULL,
    status_msp_fk_status_id SERIAL NOT NULL,
    
    FOREIGN KEY (status_msp_fk_service_provider_id) REFERENCES service_provider(sp_id),
    FOREIGN KEY (status_msp_fk_message_model_id) REFERENCES gst_message_model(mm_id),
    FOREIGN KEY (status_msp_fk_status_id) REFERENCES status(st_id)
);

CREATE TABLE IF NOT EXISTS gst_status_model_subcontractor (
    status_ms_id SERIAL PRIMARY KEY,
    status_ms_fk_subcontractor_id SERIAL NOT NULL,
    status_ms_fk_message_model_id SERIAL NOT NULL,
    status_ms_fk_status_id SERIAL NOT NULL,
    
    FOREIGN KEY (status_ms_fk_subcontractor_id) REFERENCES subcontractor(s_id),
    FOREIGN KEY (status_ms_fk_message_model_id) REFERENCES gst_message_model(mm_id),
    FOREIGN KEY (status_ms_fk_status_id) REFERENCES status(st_id)
);