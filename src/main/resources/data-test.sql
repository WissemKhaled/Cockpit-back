-- Status 1
INSERT INTO gst_status (st_id, st_name)
VALUES (1, 'En cours');
 
-- Status 2
INSERT INTO gst_status (st_id, st_name)
VALUES (2, 'En validation');
 
-- Status 3
INSERT INTO gst_status (st_id, st_name)
VALUES (3, 'Validé');
 
-- Status 4
INSERT INTO gst_status (st_id, st_name)
VALUES (4, 'Archivé');

-- Subcontractor 1
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id)
VALUES ('Orange', 'Orange@email.fr', 1);

-- Subcontractor 2
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id)
VALUES ('BPCE', 'BPCE@email.com', 1);

-- Subcontractor 3
INSERT INTO gst_subcontractor (s_name, s_email, s_creation_date, s_fk_status_id)
VALUES ('EDF', 'EDF@email.fr', '2023-01-01 12:00:00', 1);

-- Subcontractor 4
INSERT INTO gst_subcontractor (s_name, s_email, s_creation_date, s_lastUpdate_date, s_fk_status_id)
VALUES ('ENEDIS', 'ENDIS@email.fr', '2023-01-01 12:00:00', NULL, 1);

-- Subcontractor 5
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id)
VALUES ('LYON', 'LYON@email.fr', 1);

-- Prestataire 01
INSERT INTO gst_service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Lea', 'DUBOIS' ,'LeaDubois-1@email.com', '2023-01-01 12:00:00', NULL, 1, 1);

-- Prestataire 02
INSERT INTO gst_service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Louis', 'LEFEVRE' ,'LouisLefevre-1@email.com', '2023-01-01 12:00:00', NULL, 1, 1);

-- Prestataire 03
INSERT INTO gst_service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Emma', 'MARTIN' ,'EmmaMartin-1@email.com', '2023-01-01 12:00:00', NULL, 1, 1);

-- Prestataire 04
INSERT INTO gst_service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Lucas', 'BERNARD' ,'LucasBernard-1@email.com', '2023-01-01 12:00:00', NULL, 1, 1);

-- Prestataire 05
INSERT INTO gst_service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Chloe', 'DURAND' ,'ChloeDurand-1@email.com', '2023-01-01 12:00:00', NULL, 1, 1);

-- Prestataire 06
INSERT INTO gst_service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Gabriel', 'LEROY' ,'GabrielLeroy-1@email.com', '2023-01-01 12:00:00', NULL, 1, 1);

-- Prestataire 07
INSERT INTO gst_service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Manon', 'MOREAU' ,'ManonMoreau-1@email.com', '2023-01-01 12:00:00', NULL, 1, 1);

-- Prestataire 08
INSERT INTO gst_service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Jules', 'FOURNIER' ,'JulesFournier-1@email.com', '2023-01-01 12:00:00', NULL, 1, 1);

-- Prestataire 09
INSERT INTO gst_service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Charlotte', 'GIRARD' ,'CharlotteGirard-1@email.com', '2023-01-01 12:00:00', NULL, 1, 1);

-- Prestataire 10
INSERT INTO gst_service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Luna', 'MARTIN' ,'LunaMartin-1@email.com', '2023-01-01 12:00:00', NULL, 2, 1);

-- Prestataire 11
INSERT INTO gst_service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Lea', 'DUBOIS' ,'LeaDubois-2@email.com', '2023-01-01 12:00:00', NULL, 2, 1);

-- Prestataire 12
INSERT INTO gst_service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Louis', 'LEFEVRE' ,'LouisLefevre-2@email.com', '2023-01-01 12:00:00', NULL, 2, 1);

-- Prestataire 13
INSERT INTO gst_service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Emma', 'MARTIN' ,'EmmaMartin-2@email.com', '2023-01-01 12:00:00', NULL, 2, 1);

-- Prestataire 14
INSERT INTO gst_service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Lucas', 'BERNARD' ,'LucasBernard-2@email.com', '2023-01-01 12:00:00', NULL, 2, 1);

-- Prestataire 15
INSERT INTO gst_service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Chloe', 'DURAND' ,'ChloeDurand-2@email.com', '2023-01-01 12:00:00', NULL, 2, 1);

-- Prestataire 16
INSERT INTO gst_service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Gabriel', 'LEROY' ,'GabrielLeroy-2@email.com', '2023-01-01 12:00:00', NULL, 2, 1);

-- Prestataire 17
INSERT INTO gst_service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Manon', 'MOREAU' ,'ManonMoreau-2@email.com', '2023-01-01 12:00:00', NULL, 2, 1);

-- Prestataire 18
INSERT INTO gst_service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Jules', 'FOURNIER' ,'JulesFournier-2@email.com', '2023-01-01 12:00:00', NULL, 2, 1);

-- Prestataire 19
INSERT INTO gst_service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Charlotte', 'GIRARD' ,'CharlotteGirard-2@email.com', '2023-01-01 12:00:00', NULL, 2, 1);

-- Prestataire 20
INSERT INTO gst_service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Luna', 'GIRARD' ,'LunaGirard-2@email.com', '2023-01-01 12:00:00', NULL, 2, 1);

-- Prestataire 21
INSERT INTO gst_service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Lea', 'DUBOIS' ,'LeaDubois-3@email.com', '2023-01-01 12:00:00', NULL, 3, 1);

-- Prestataire 22
INSERT INTO gst_service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Louis', 'LEFEVRE' ,'LouisLefevre-3@email.com', '2023-01-01 12:00:00', NULL, 3, 1);

-- Prestataire 23
INSERT INTO gst_service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Emma', 'MARTIN' ,'EmmaMartin-3@email.com', '2023-01-01 12:00:00', NULL, 3, 1);

-- Prestataire 24
INSERT INTO gst_service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Lucas', 'BERNARD' ,'LucasBernard-3@email.com', '2023-01-01 12:00:00', NULL, 3, 1);

-- Prestataire 25
INSERT INTO gst_service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Chloe', 'DURAND' ,'ChloeDurand-3@email.com', '2023-01-01 12:00:00', NULL, 3, 1);

-- Prestataire 26
INSERT INTO gst_service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Gabriel', 'LEROY' ,'GabrielLeroy-3@email.com', '2023-01-01 12:00:00', NULL, 3, 1);

-- Prestataire 27
INSERT INTO gst_service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Manon', 'MOREAU' ,'ManonMoreau-3@email.com', '2023-01-01 12:00:00', NULL, 3, 1);

-- Prestataire 28
INSERT INTO gst_service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Jules', 'FOURNIER' ,'JulesFournier-3@email.com', '2023-01-01 12:00:00', NULL, 3, 1);

-- Prestataire 29
INSERT INTO gst_service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Charlotte', 'GIRARD' ,'CharlotteGirard-3@email.com', '2023-01-01 12:00:00', NULL, 3, 1);

-- Prestataire 30
INSERT INTO gst_service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Lea', 'LEFEVRE' ,'LeaLEFEVRE-4@email.com', '2023-01-01 12:00:00', NULL, 4, 1);

-- Prestataire 31
INSERT INTO gst_service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Lea', 'DUBOIS' ,'LeaDubois-4@email.com', '2023-01-01 12:00:00', NULL, 4, 1);

-- Prestataire 32
INSERT INTO gst_service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Louis', 'LEFEVRE' ,'LouisLefevre-4@email.com', '2023-01-01 12:00:00', NULL, 4, 1);

-- Prestataire 33
INSERT INTO gst_service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Emma', 'MARTIN' ,'EmmaMartin-4@email.com', '2023-01-01 12:00:00', NULL, 4, 1);

-- Prestataire 34
INSERT INTO gst_service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Lucas', 'BERNARD' ,'LucasBernard-4@email.com', '2023-01-01 12:00:00', NULL, 4, 1);

-- Prestataire 35
INSERT INTO gst_service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Chloe', 'DURAND' ,'ChloeDurand-4@email.com', '2023-01-01 12:00:00', NULL, 4, 1);
-- Prestataire 36
INSERT INTO gst_service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Gabriel', 'LEROY' ,'GabrielLeroy-4@email.com', '2023-01-01 12:00:00', NULL, 4 , 1);

-- Prestataire 37
INSERT INTO gst_service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Manon', 'MOREAU' ,'ManonMoreau-4@email.com', '2023-01-01 12:00:00', NULL, 4 , 1);

-- Prestataire 38
INSERT INTO gst_service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Jules', 'FOURNIER' ,'JulesFournier-4@email.com', '2023-01-01 12:00:00', NULL, 4 , 1);

-- Prestataire 39
INSERT INTO gst_service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Charlotte', 'GIRARD' ,'CharlotteGirard-4@email.com', '2023-01-01 12:00:00', NULL, 4 , 1);

-- Prestataire 40
INSERT INTO gst_service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Chloe', 'DUBOIS' ,'ChloeDubois-4@email.com', '2023-01-01 12:00:00', NULL, 5 , 1);

-- Prestataire 41
INSERT INTO gst_service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Lea', 'DUBOIS' ,'LeaDubois-5@email.com', '2023-01-01 12:00:00', NULL, 5 , 1);

-- Prestataire 42
INSERT INTO gst_service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Louis', 'LEFEVRE' ,'LouisLefevre-5@email.com', '2023-01-01 12:00:00', NULL, 5 , 1);

-- Prestataire 43
INSERT INTO gst_service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Emma', 'MARTIN' ,'EmmaMartin-5@email.com', '2023-01-01 12:00:00', NULL, 5 , 1);

-- Prestataire 44
INSERT INTO gst_service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Lucas', 'BERNARD' ,'LucasBernard-5@email.com', '2023-01-01 12:00:00', NULL, 5 , 1);

-- Prestataire 45
INSERT INTO gst_service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Chloe', 'DURAND' ,'ChloeDurand-5@email.com', '2023-01-01 12:00:00', NULL, 5 , 1);

-- Prestataire 46
INSERT INTO gst_service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Gabriel', 'LEROY' ,'GabrielLeroy-5@email.com', '2023-01-01 12:00:00', NULL, 5 , 1);

-- Prestataire 47
INSERT INTO gst_service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Manon', 'MOREAU' ,'ManonMoreau-5@email.com', '2023-01-01 12:00:00', NULL, 5 , 1);

-- Prestataire 48
INSERT INTO gst_service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Jules', 'FOURNIER' ,'JulesFournier-5@email.com', '2023-01-01 12:00:00', NULL, 5 , 1);

-- Prestataire 49
INSERT INTO gst_service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Charlotte', 'GIRARD' ,'CharlotteGirard-5@email.com', '2023-01-01 12:00:00', NULL, 5 , 1);

-- Prestataire 50
INSERT INTO gst_service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Gabriel', 'LEROY' ,'GabrielLeroy-5@email.com', '2023-01-01 12:00:00', NULL, 2, 1);

-- -- For the table gst_status_model_service_provider
-- INSERT INTO gst_status_model_service_provider (status_msp_fk_service_provider_id, status_msp_fk_message_model_id, status_msp_fk_status_id)
-- SELECT sp.sp_id, mm.mm_id, sp.sp_fk_status_id
-- FROM gst_service_provider sp
-- JOIN gst_message_model mm ON mm.mm_category = 'SP'
-- WHERE sp.sp_fk_status_id IS NOT NULL
-- ORDER BY sp.sp_id, mm.mm_id;

-- -- For the table gst_status_model_subcontractor
-- INSERT INTO gst_status_model_subcontractor (status_ms_fk_subcontractor_id, status_ms_fk_message_model_id, status_ms_fk_status_id)
-- SELECT sub.s_id, mm.mm_id, sub.s_fk_status_id
-- FROM gst_subcontractor sub
-- JOIN gst_message_model mm ON mm.mm_category = 'SC'
-- WHERE sub.s_fk_status_id IS NOT NULL
-- ORDER BY sub.s_id, mm.mm_id;