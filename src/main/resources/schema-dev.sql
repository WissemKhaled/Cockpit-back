CREATE SCHEMA IF NOT EXISTS schema_dev;

CREATE TABLE IF NOT EXISTS schema_dev.gst_status (
    st_id SERIAL PRIMARY KEY,
    st_name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS schema_dev.gst_subcontractor (
    s_id SERIAL PRIMARY KEY,
    s_name VARCHAR(250) NOT NULL,
    s_email VARCHAR(45) NOT NULL,
    s_creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    s_lastupdate_date TIMESTAMP,
    s_fk_status_id smallint NOT NULL
);

CREATE TABLE IF NOT EXISTS schema_dev.gst_service_provider (
    sp_id SERIAL PRIMARY KEY,
    sp_first_name VARCHAR(250) NOT NULL,
    sp_name VARCHAR(250) NOT NULL,
    sp_email VARCHAR(45) NOT NULL,
    sp_creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    sp_lastupdate_date TIMESTAMP,
    sp_fk_subcontractor_id SMALLINT NOT NULL,
    sp_fk_status_id SMALLINT NOT NULL
);

CREATE TABLE IF NOT EXISTS schema_dev.gst_user (
    u_id SERIAL PRIMARY KEY,
    u_email VARCHAR(45) NOT NULL,
    u_password VARCHAR(255) NOT NULL,
    u_first_name VARCHAR(45) DEFAULT NULL,
    u_last_name VARCHAR(45) DEFAULT NULL,
    u_status BOOLEAN DEFAULT false,
    u_insertion_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    u_last_update TIMESTAMP
);

CREATE TABLE IF NOT EXISTS schema_dev.refresh_token (
    rt_id SERIAL PRIMARY KEY,
    rt_token VARCHAR(255) NOT NULL,
    rt_expiry_date TIMESTAMP NOT NULL,
    rt_fk_user_id SMALLINT NOT NULL
);

CREATE TABLE IF NOT EXISTS schema_dev.gst_log (
    log_id SERIAL PRIMARY KEY,
    log_type VARCHAR(45) NOT NULL,
    log_email VARCHAR(255) NOT NULL,
    log_password VARCHAR(255) NOT NULL,
    log_value VARCHAR(45),
    log_creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    log_last_update TIMESTAMP
);

CREATE TABLE IF NOT EXISTS schema_dev.gst_category (
    cat_id SERIAL PRIMARY KEY,
    cat_name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS schema_dev.gst_message_model (
    mm_id SERIAL PRIMARY KEY,
    mm_link integer NOT NULL,
    mm_subject VARCHAR(255) NOT NULL,
    mm_body TEXT NOT NULL,
    mm_has_email BOOLEAN,
    mm_is_relaunch BOOLEAN,
    mm_creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    mm_last_update TIMESTAMP,
    mm_fk_category_id SMALLINT NOT NULL
);

CREATE TABLE IF NOT EXISTS schema_dev.gst_message_send (
    ms_id SERIAL PRIMARY KEY,
    ms_to VARCHAR(55) DEFAULT NULL,
    ms_cc TEXT DEFAULT NULL,
    ms_subject VARCHAR(255) NOT NULL,
    ms_body TEXT NOT NULL,
    ms_send_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ms_fk_user_id SMALLINT NOT NULL
);

CREATE TABLE IF NOT EXISTS schema_dev.gst_contract (
    c_id SERIAL PRIMARY KEY,
    c_contract_number VARCHAR(255) NOT NULL,
    c_fk_subcontractor_id SMALLINT NOT NULL,
    c_fk_service_provider_id SMALLINT NOT NULL
);

CREATE TABLE IF NOT EXISTS schema_dev.gst_model_tracking (
    mt_id SERIAL PRIMARY KEY,
	mt_send_date TIMESTAMP,
    mt_validation_date TIMESTAMP,
    mt_fk_contract_id SMALLINT NOT NULL,
    mt_fk_message_model_id SMALLINT NOT NULL,
    mt_fk_status_id SMALLINT NOT NULL,
    mt_fk_category_id SMALLINT NOT NULL
);

ALTER TABLE schema_dev.gst_subcontractor
ADD CONSTRAINT fk_subcontractor_status
FOREIGN KEY (s_fk_status_id) REFERENCES schema_dev.gst_status(st_id);

ALTER TABLE schema_dev.gst_service_provider
ADD CONSTRAINT fk_sp_subcontractor
FOREIGN KEY (sp_fk_subcontractor_id) REFERENCES schema_dev.gst_subcontractor(s_id);

ALTER TABLE schema_dev.gst_service_provider
ADD CONSTRAINT fk_sp_status
FOREIGN KEY (sp_fk_status_id) REFERENCES schema_dev.gst_status(st_id);

ALTER TABLE schema_dev.refresh_token
ADD CONSTRAINT fk_refresh_token_user
FOREIGN KEY (rt_fk_user_id) REFERENCES schema_dev.gst_user(u_id);

ALTER TABLE schema_dev.gst_model_tracking
ADD CONSTRAINT fk_mt_contract
FOREIGN KEY (mt_fk_contract_id) REFERENCES schema_dev.gst_contract(c_id);

ALTER TABLE schema_dev.gst_model_tracking
ADD CONSTRAINT fk_mt_message_model
FOREIGN KEY (mt_fk_message_model_id) REFERENCES schema_dev.gst_message_model(mm_id);

ALTER TABLE schema_dev.gst_model_tracking
ADD CONSTRAINT fk_mt_status
FOREIGN KEY (mt_fk_status_id) REFERENCES schema_dev.gst_status(st_id);

ALTER TABLE schema_dev.gst_model_tracking
ADD CONSTRAINT fk_mt_category
FOREIGN KEY (mt_fk_category_id) REFERENCES schema_dev.gst_category(cat_id);