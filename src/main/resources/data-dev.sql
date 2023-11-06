INSERT INTO status (st_id, st_name, st_description)
VALUES (1, 'EN_COURS', 'AAAA');

INSERT INTO status (st_id, st_name, st_description)
VALUES (2, 'EN_VALIDATION', 'BBBB');

INSERT INTO status (st_id, st_name, st_description)
VALUES (3, 'VALIDE', 'CCCC');

INSERT INTO status (st_id, st_name, st_description)
VALUES (4, 'ARCHIVE', 'DDDD');

INSERT INTO subcontractor (s_name, s_email, s_fk_status_id )
VALUES ('Orange', 'Orange@email.com', 1);

-- INSERT INTO u_user (u_email, u_password, u_first_name, u_last_name, u_status, u_insertion_date, u_last_update)
-- VALUES ('user@example.com', 'password123', 'John', 'Doe', 't', CURRENT_TIMESTAMP, null);

-- INSERT INTO refresh_token (rt_token, rt_expiry_date, rt_fk_user_id) 
-- VALUES ('dgdfg51f1h56f1h5fg', TIMESTAMP '2023-12-25 00:00:00', 1);

-- INSERT INTO u_user (u_email, u_password, u_first_name, u_last_name, u_status, u_insertion_date, u_last_update)
-- VALUES ('user@example2.com', 'password123', 'John', 'Doe', 't', CURRENT_TIMESTAMP, null);

-- INSERT INTO refresh_token (rt_token, rt_expiry_date, rt_fk_user_id) 
-- VALUES ('dgdfg51f1h56f1h5fg222', TIMESTAMP '2023-12-25 00:00:00', 2);