INSERT INTO status (st_id, st_name)
VALUES (1, 'En cours');
INSERT INTO status (st_id, st_name)
VALUES (2, 'En validation');
INSERT INTO status (st_id, st_name)
VALUES (3, 'Validé');
INSERT INTO status (st_id, st_name)
VALUES (4, 'Archivé');

INSERT INTO subcontractor (s_name, s_email, s_fk_status_id)
VALUES ('BPCE', 'BBCE@email.com', 1);

-- SELECT setval('subcontrator_id_seq', 2, false);
