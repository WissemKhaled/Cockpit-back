INSERT INTO status (st_id, st_name, st_description)
VALUES (1, 'En cours', NULL);

INSERT INTO status (st_id, st_name, st_description)
VALUES (2, 'En validation', NULL);

INSERT INTO status (st_id, st_name, st_description)
VALUES (3, 'Validé', NULL);

INSERT INTO status (st_id, st_name, st_description)
VALUES (4, 'Archivé', NULL);

-- Subcontractor 1
INSERT INTO subcontractor (s_name, s_email, s_fk_status_id)
VALUES ('Orange', 'Orange@email.fr', 1);

-- Subcontractor 2
INSERT INTO subcontractor (s_name, s_email, s_fk_status_id)
VALUES ('BPCE', 'BPCE@email.com', 2);

-- Subcontractor 3
INSERT INTO subcontractor (s_name, s_email, s_creation_date, s_fk_status_id)
VALUES ('EDF', 'EDF@email.fr', '2023-01-01 12:00:00', 1);

-- Subcontractor 4
INSERT INTO subcontractor (s_name, s_email, s_creation_date, s_lastUpdate_date, s_fk_status_id)
VALUES ('ENEDIS', 'ENDIS@email.fr', '2023-01-01 12:00:00', NULL, 3);

-- Subcontractor 5
INSERT INTO subcontractor (s_name, s_email, s_fk_status_id)
VALUES ('LYON', 'LYON@email.fr', 2);

-- gst_message_model
INSERT INTO gst_message_model (mm_type, mm_subject, mm_body, mm_creation_date, mm_last_update)
VALUES (
    'RESET_PASSWORD',
    'Réinitialisation du mot de passe',
    'Bonjour [[firstName]] [[lastName]],\n\n' ||
    'Vous avez souhaité renouveler votre mot de passe pour accéder à l''application GST.\n' ||
    'Pour cela, veuillez cliquer sur le lien ci-dessous :\n' ||
    ' [[resetPwdLink]]' ||
    '\n\n En vous remerciant de votre confiance,\n\n' ||
    'L''équipe Open',
    NOW(),
    NULL
);