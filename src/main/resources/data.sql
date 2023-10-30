INSERT INTO status (st_id, st_name, st_description)
VALUES (1, 'EN_COURS', 'aaaaaaaaa');

INSERT INTO status (st_id, st_name, st_description)
VALUES (2, 'EN_VALIDATION', 'bbbb');

INSERT INTO status (st_id, st_name, st_description)
VALUES (3, 'VALIDE', 'CCCC');

INSERT INTO status (st_id, st_name, st_description)
VALUES (4, 'ARCHIVE', 'DDDD');

INSERT INTO subcontractor (s_name, s_email, s_fk_status_id )
VALUES ('BPCE', 'BBCE@email.com', 1);
