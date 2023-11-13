
INSERT INTO status (st_id, st_name, st_description)
VALUES (1, 'En cours', 'AAAA');

INSERT INTO status (st_id, st_name, st_description)
VALUES (2, 'En validation', 'BBBB');

INSERT INTO status (st_id, st_name, st_description)
VALUES (3, 'Validé', 'CCCC');

INSERT INTO status (st_id, st_name, st_description)
VALUES (4, 'Archivé', 'DDDD');

INSERT INTO subcontractor (s_name, s_email, s_fk_status_id )
VALUES ('Orange', 'Orange@email.com', 1);

INSERT INTO subcontractor (s_name, s_email, s_fk_status_id )
VALUES ('BPCE', 'BPCE@email.com', 2);

INSERT INTO subcontractor (s_name, s_email, s_fk_status_id )
VALUES ('EDF', 'EDF@email.com', 3);

INSERT INTO subcontractor (s_name, s_email, s_fk_status_id )
VALUES ('ENEDIE', 'ENDIS@email.com', 4);

INSERT INTO subcontractor (s_name, s_email, s_fk_status_id )
VALUES ('LYON', 'LYON@email.com', 1);

INSERT INTO subcontractor (s_name, s_email, s_fk_status_id )
VALUES ('Orange', 'Orange@email.com', 2);

INSERT INTO subcontractor (s_name, s_email, s_fk_status_id )
VALUES ('BPCE', 'BPCE@email.com', 3);

INSERT INTO subcontractor (s_name, s_email, s_fk_status_id )
VALUES ('EDF', 'EDF@email.com', 4);

INSERT INTO subcontractor (s_name, s_email, s_fk_status_id )
VALUES ('ENEDIE', 'ENDIS@email.com', 1);

INSERT INTO subcontractor (s_name, s_email, s_fk_status_id )
VALUES ('LYON', 'LYON@email.com', 2);
