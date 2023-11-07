-- CREATE TABLE IF NOT EXISTS status (
--     st_id SERIAL PRIMARY KEY,
--     st_name VARCHAR(50) NOT NULL,
--     st_description VARCHAR(255) DEFAULT NULL
-- );

-- CREATE TABLE IF NOT EXISTS subcontractor (
--     s_id SERIAL PRIMARY KEY,
--     s_name VARCHAR(250) NOT NULL,
--     s_email VARCHAR(45) NOT NULL,
--     s_fk_status_id SMALLINT NOT NULL, 
--     FOREIGN KEY (s_fk_status_id) REFERENCES status(st_id)
-- );

-- INSERT INTO status (st_id, st_name, st_description)
-- VALUES (1, 'EN_COURS', 'AAAA');

-- INSERT INTO status (st_id, st_name, st_description)
-- VALUES (2, 'EN_VALIDATION', 'BBBB');

-- INSERT INTO status (st_id, st_name, st_description)
-- VALUES (3, 'VALIDE', 'CCCC');

-- INSERT INTO status (st_id, st_name, st_description)
-- VALUES (4, 'ARCHIVE', 'DDDD');

-- INSERT INTO subcontractor (s_name, s_email, s_fk_status_id )
-- VALUES ('Test', 'Test@email.com', 1);

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

-- INSERT INTO u_user (u_email, u_password, u_first_name, u_last_name, u_status, u_insertion_date, u_last_update)
-- VALUES ('user@example.com', 'password123', 'John', 'Doe', 't', CURRENT_TIMESTAMP, null);

-- INSERT INTO refresh_token (rt_token, rt_expiry_date, rt_fk_user_id) 
-- VALUES ('dgdfg51f1h56f1h5fg', TIMESTAMP '2023-12-25 00:00:00', 1);

-- INSERT INTO u_user (u_email, u_password, u_first_name, u_last_name, u_status, u_insertion_date, u_last_update)
-- VALUES ('user@example2.com', 'password123', 'John', 'Doe', 't', CURRENT_TIMESTAMP, null);

-- INSERT INTO refresh_token (rt_token, rt_expiry_date, rt_fk_user_id) 
-- VALUES ('dgdfg51f1h56f1h5fg222', TIMESTAMP '2023-12-25 00:00:00', 2);