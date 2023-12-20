INSERT INTO public.gst_log (log_type, log_email, log_password, log_value, log_last_update)
VALUES
    ('Type1', 'email1@example.com', 'password1', 'Value1', CURRENT_TIMESTAMP),
    ('Type2', 'email2@example.com', 'password2', 'Value2', CURRENT_TIMESTAMP),
    ('Type3', 'email3@example.com', 'password3', 'Value3', CURRENT_TIMESTAMP),
    ('Type4', 'email4@example.com', 'password4', 'Value4', CURRENT_TIMESTAMP);

INSERT INTO status (type, st_description)
VALUES
    ('En cours', 'Description 1'),
    ('En validation', 'Description 2'),
    ('Validé', 'Description 3'),
    ('Archivé', 'Description 4');


INSERT INTO service_provider (sp_first_name, sp_name, sp_email, sp_lastupdate_date)
VALUES
    ('FirstName1', 'LastName1', 'provider1@example.com', CURRENT_TIMESTAMP),
    ('FirstName2', 'LastName2', 'provider2@example.com', CURRENT_TIMESTAMP),
    ('FirstName3', 'LastName3', 'provider3@example.com', CURRENT_TIMESTAMP),
    ('FirstName4', 'LastName4', 'provider4@example.com', CURRENT_TIMESTAMP);


INSERT INTO subcontractor (s_name, s_email, service_provider_id, s_lastupdate_date)
VALUES
    ('Subcontractor1', 'sub1@example.com', 1, CURRENT_TIMESTAMP),
    ('Subcontractor2', 'sub2@example.com', 2, CURRENT_TIMESTAMP),
    ('Subcontractor3', 'sub3@example.com', 3, CURRENT_TIMESTAMP),
    ('Subcontractor4', 'sub4@example.com', 4, CURRENT_TIMESTAMP);


INSERT INTO gst_message_model (mm_subject, mm_body, status_fk, service_provider_fk, subcontractor_fk, mm_last_update)
VALUES
    ('Subject1', 'Body1', 1, 1, 1, CURRENT_TIMESTAMP),
    ('Subject2', 'Body2', 2, 2, 2, CURRENT_TIMESTAMP),
    ('Subject3', 'Body3', 3, 3, 3, CURRENT_TIMESTAMP),
    ('Subject4', 'Body4', 4, 4, 4, CURRENT_TIMESTAMP);

INSERT INTO gst_message_model (mm_subject, mm_body, status_fk, service_provider_fk, subcontractor_fk, mm_last_update)
VALUES
    ('Subject1', 'Body1', 1, 1, 1, CURRENT_TIMESTAMP),
    ('Subject2', 'Body2', 2, 2, 2, CURRENT_TIMESTAMP),
    ('Subject3', 'Body3', 3, 3, 3, CURRENT_TIMESTAMP),
    ('Subject4', 'Body4', 4, 4, 4, CURRENT_TIMESTAMP);

INSERT INTO message_send (ms_sender, ms_subject, ms_body, gst_message_model_fk, ms_status)
VALUES
    ('Sender1', 'Subject1', 'Body1', 1, 1),
    ('Sender2', 'Subject2', 'Body2', 2, 2),
    ('Sender3', 'Subject3', 'Body3', 3, 3),
    ('Sender4', 'Subject4', 'Body4', 4, 4);


INSERT INTO public.u_user (u_email, u_password, u_first_name, u_last_name, u_status, u_last_update)
VALUES
    ('user1@example.com', 'pass1', 'FirstName1', 'LastName1', true, CURRENT_TIMESTAMP),
    ('user2@example.com', 'pass2', 'FirstName2', 'LastName2', false, CURRENT_TIMESTAMP),
    ('user3@example.com', 'pass3', 'FirstName3', 'LastName3', true, CURRENT_TIMESTAMP),
    ('user4@example.com', 'pass4', 'FirstName4', 'LastName4', false, CURRENT_TIMESTAMP);


INSERT INTO refresh_token (rt_token, rt_expiry_date, rt_fk_user_id)
VALUES
    ('token1', CURRENT_TIMESTAMP + INTERVAL '1 day', 1),
    ('token2', CURRENT_TIMESTAMP + INTERVAL '1 day', 2),
    ('token3', CURRENT_TIMESTAMP + INTERVAL '1 day', 3),
    ('token4', CURRENT_TIMESTAMP + INTERVAL '1 day', 4);


INSERT INTO gst_status_model_service_provider (status_msp_fk_service_provider_id, status_msp_fk_sp_message_model_id, status_msp_fk_status_id)
VALUES
    (1, 1, 1),
    (2, 2, 2),
    (3, 3, 3),
    (4, 4, 4);


INSERT INTO gst_status_model_subcontractor (status_ms_fk_subcontractor_id, status_ms_fk_sc_message_model_id, status_ms_fk_status_id)
VALUES
    (1, 1, 1),
    (2, 2, 2),
    (3, 3, 3),
    (4, 4, 4);
