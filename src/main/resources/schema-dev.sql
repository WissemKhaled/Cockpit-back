CREATE TABLE IF NOT EXISTS gst_status (
    st_id SERIAL PRIMARY KEY,
    st_name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS gst_subcontractor (
    s_id SERIAL PRIMARY KEY,
    s_name VARCHAR(250) NOT NULL,
    s_email VARCHAR(45) NOT NULL,
    s_creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    s_lastupdate_date TIMESTAMP,
    s_fk_status_id smallint NOT NULL,
	FOREIGN KEY (s_fk_status_id) REFERENCES gst_status(st_id)
);

CREATE TABLE IF NOT EXISTS gst_service_provider (
    sp_id SERIAL PRIMARY KEY,
    sp_first_name VARCHAR(250) NOT NULL,
    sp_name VARCHAR(250) NOT NULL,
    sp_email VARCHAR(45) NOT NULL,
    sp_creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    sp_lastupdate_date TIMESTAMP,
    sp_fk_subcontractor_id SMALLINT NOT NULL,
	FOREIGN KEY (sp_fk_subcontractor_id) REFERENCES gst_subcontractor(s_id),
    sp_fk_status_id SMALLINT NOT NULL,
	FOREIGN KEY (sp_fk_status_id) REFERENCES gst_status(st_id)
);

CREATE TABLE IF NOT EXISTS gst_user (
    u_id SERIAL PRIMARY KEY,
    u_email VARCHAR(45) NOT NULL,
    u_password VARCHAR(255) NOT NULL,
    u_first_name VARCHAR(45) DEFAULT NULL,
    u_last_name VARCHAR(45) DEFAULT NULL,
    u_status BOOLEAN DEFAULT false,
    u_insertion_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    u_last_update TIMESTAMP
);

CREATE TABLE IF NOT EXISTS refresh_token (
    rt_id SERIAL PRIMARY KEY,
    rt_token VARCHAR(255) NOT NULL,
    rt_expiry_date TIMESTAMP NOT NULL,
    rt_fk_user_id SMALLINT NOT NULL,
	FOREIGN KEY (rt_fk_user_id) REFERENCES gst_user(u_id)
);

CREATE TABLE IF NOT EXISTS gst_log (
    log_id SERIAL PRIMARY KEY,
    log_type VARCHAR(45) NOT NULL,
    log_email VARCHAR(255) NOT NULL,
    log_password VARCHAR(255) NOT NULL,
    log_value VARCHAR(45),
    log_creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    log_last_update TIMESTAMP
);

CREATE TABLE IF NOT EXISTS gst_category (
    cat_id SERIAL PRIMARY KEY,
    cat_name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS gst_message_model (
    mm_id SERIAL PRIMARY KEY,
    mm_link integer NOT NULL,
    mm_subject VARCHAR(255) NOT NULL,
    mm_body TEXT NOT NULL,
    mm_has_email BOOLEAN,
    mm_creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    mm_last_update TIMESTAMP,
    mm_fk_category_id SMALLINT NOT NULL,
	FOREIGN KEY (mm_fk_category_id) REFERENCES gst_category(cat_id)
);

CREATE TABLE IF NOT EXISTS gst_message_send (
    ms_id SERIAL PRIMARY KEY,
    ms_to VARCHAR(55) DEFAULT NULL,
    ms_cc TEXT DEFAULT NULL,
    ms_subject VARCHAR(255) NOT NULL,
    ms_body TEXT NOT NULL,
    ms_send_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ms_fk_user_id SMALLINT NOT NULL,
	FOREIGN KEY (ms_fk_user_id) REFERENCES gst_user(u_id)
);

CREATE TABLE IF NOT EXISTS gst_contract (
    c_id SERIAL PRIMARY KEY,
    c_contract_number VARCHAR(255) NOT NULL,
    c_fk_subcontractor_id SMALLINT NOT NULL,
	FOREIGN KEY (c_fk_subcontractor_id) REFERENCES gst_subcontractor(s_id),
    c_fk_service_provider_id SMALLINT NOT NULL,
	FOREIGN KEY (c_fk_service_provider_id) REFERENCES gst_service_provider(sp_id)
);

CREATE TABLE IF NOT EXISTS gst_model_tracking (
    mt_id SERIAL PRIMARY KEY,
	mt_send_date TIMESTAMP,
    mt_validation_date TIMESTAMP,
    mt_fk_contract_id SMALLINT NOT NULL,
	FOREIGN KEY (mt_fk_contract_id) REFERENCES gst_contract(c_id),
    mt_fk_message_model_id SMALLINT NOT NULL,
	FOREIGN KEY (mt_fk_message_model_id) REFERENCES gst_message_model(mm_id),
    mt_fk_status_id SMALLINT NOT NULL,
	FOREIGN KEY (mt_fk_status_id) REFERENCES gst_status(st_id),
    mt_fk_category_id SMALLINT NOT NULL,
	FOREIGN KEY (mt_fk_category_id) REFERENCES gst_category(cat_id)
);