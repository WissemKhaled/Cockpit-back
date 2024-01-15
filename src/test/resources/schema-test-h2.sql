
-- Dropping tables if they exist
DROP TABLE IF EXISTS gst_model_tracking;
DROP TABLE IF EXISTS gst_contract;
DROP TABLE IF EXISTS gst_log;
DROP TABLE IF EXISTS refresh_token;
DROP TABLE IF EXISTS gst_user;
DROP TABLE IF EXISTS gst_service_provider;
DROP TABLE IF EXISTS gst_subcontractor;
DROP TABLE IF EXISTS gst_message_model;
DROP TABLE IF EXISTS gst_category;
DROP TABLE IF EXISTS gst_status;

CREATE SCHEMA IF NOT EXISTS schema_dev;
SET SCHEMA schema_dev;

-- gst_status
CREATE TABLE IF NOT EXISTS gst_status
(
    st_id   INT AUTO_INCREMENT PRIMARY KEY,
    st_name VARCHAR(50) NOT NULL
);

-- gst_category
CREATE TABLE IF NOT EXISTS gst_category
(
    cat_id   INT AUTO_INCREMENT PRIMARY KEY,
    cat_name VARCHAR(50) NOT NULL
);

-- gst_message_model
CREATE TABLE IF NOT EXISTS gst_message_model
(
    mm_id             INT AUTO_INCREMENT PRIMARY KEY,
    mm_link           INT          NOT NULL,
    mm_subject        VARCHAR(255) NOT NULL,
    mm_body           TEXT         NOT NULL,
    mm_has_email      BOOLEAN,
    mm_creation_date  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    mm_last_update    TIMESTAMP,
    mm_fk_category_id INT          NOT NULL
);

-- gst_subcontractor
CREATE TABLE IF NOT EXISTS gst_subcontractor
(
    s_id              INT AUTO_INCREMENT PRIMARY KEY,
    s_name            VARCHAR(250) NOT NULL,
    s_email           VARCHAR(45)  NOT NULL,
    s_creation_date   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    s_lastupdate_date TIMESTAMP,
    s_fk_status_id    INT          NOT NULL
);

-- gst_service_provider
CREATE TABLE IF NOT EXISTS gst_service_provider
(
    sp_id                  INT AUTO_INCREMENT PRIMARY KEY,
    sp_first_name          VARCHAR(250) NOT NULL,
    sp_name                VARCHAR(250) NOT NULL,
    sp_email               VARCHAR(45)  NOT NULL,
    sp_creation_date       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    sp_lastupdate_date     TIMESTAMP,
    sp_fk_subcontractor_id INT          NOT NULL,
    sp_fk_status_id        INT          NOT NULL
);

-- gst_user
CREATE TABLE IF NOT EXISTS gst_user
(
    u_id             INT AUTO_INCREMENT PRIMARY KEY,
    u_email          VARCHAR(45)  NOT NULL,
    u_password       VARCHAR(255) NOT NULL,
    u_first_name     VARCHAR(45) DEFAULT NULL,
    u_last_name      VARCHAR(45) DEFAULT NULL,
    u_status         BOOLEAN     DEFAULT false,
    u_insertion_date TIMESTAMP   DEFAULT CURRENT_TIMESTAMP,
    u_last_update    TIMESTAMP
);

-- refresh_token
CREATE TABLE IF NOT EXISTS refresh_token
(
    rt_id          INT AUTO_INCREMENT PRIMARY KEY,
    rt_token       VARCHAR(255) NOT NULL,
    rt_expiry_date TIMESTAMP    NOT NULL,
    rt_fk_user_id  INT          NOT NULL
);

-- gst_log
CREATE TABLE IF NOT EXISTS gst_log
(
    log_id            INT AUTO_INCREMENT PRIMARY KEY,
    log_type          VARCHAR(45)  NOT NULL,
    log_email         VARCHAR(255) NOT NULL,
    log_password      VARCHAR(255) NOT NULL,
    log_value         VARCHAR(45),
    log_creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    log_last_update   TIMESTAMP
);

-- gst_contract
CREATE TABLE IF NOT EXISTS gst_contract
(
    c_id                     INT AUTO_INCREMENT PRIMARY KEY,
    c_contract_number        VARCHAR(255) NOT NULL,
    c_fk_subcontractor_id    INT          NOT NULL,
    c_fk_service_provider_id INT          NOT NULL
);

-- gst_model_tracking
CREATE TABLE IF NOT EXISTS gst_model_tracking
(
    mt_id                  INT AUTO_INCREMENT PRIMARY KEY,
    mt_send_date           TIMESTAMP,
    mt_validation_date     TIMESTAMP,
    mt_fk_contract_id      INT NOT NULL,
    mt_fk_message_model_id INT NOT NULL,
    mt_fk_status_id        INT NOT NULL,
    mt_fk_category_id      INT NOT NULL
);

-- Foreign keys
ALTER TABLE gst_subcontractor
    ADD CONSTRAINT fk_subcontractor_status
        FOREIGN KEY (s_fk_status_id) REFERENCES gst_status (st_id);

ALTER TABLE gst_service_provider
    ADD CONSTRAINT fk_sp_subcontractor
        FOREIGN KEY (sp_fk_subcontractor_id) REFERENCES gst_subcontractor (s_id);

ALTER TABLE gst_service_provider
    ADD CONSTRAINT fk_sp_status
        FOREIGN KEY (sp_fk_status_id) REFERENCES gst_status (st_id);

ALTER TABLE refresh_token
    ADD CONSTRAINT fk_refresh_token_user
        FOREIGN KEY (rt_fk_user_id) REFERENCES gst_user (u_id);

ALTER TABLE gst_model_tracking
    ADD CONSTRAINT fk_mt_contract
        FOREIGN KEY (mt_fk_contract_id) REFERENCES gst_contract (c_id);

ALTER TABLE gst_model_tracking
    ADD CONSTRAINT fk_mt_message_model
        FOREIGN KEY (mt_fk_message_model_id) REFERENCES gst_message_model (mm_id);

ALTER TABLE gst_model_tracking
    ADD CONSTRAINT fk_mt_status
        FOREIGN KEY (mt_fk_status_id) REFERENCES gst_status (st_id);

ALTER TABLE gst_model_tracking
    ADD CONSTRAINT fk_mt_category
        FOREIGN KEY (mt_fk_category_id) REFERENCES gst_category (cat_id);