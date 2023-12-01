-- Status 1
INSERT INTO status (st_id, st_name, st_description)
VALUES (1, 'En cours', NULL);
 
-- Status 2
INSERT INTO status (st_id, st_name, st_description)
VALUES (2, 'En validation', NULL);
 
-- Status 3
INSERT INTO status (st_id, st_name, st_description)
VALUES (3, 'Validé', NULL);
 
-- Status 4
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

-- Prestataire 01
INSERT INTO service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Lea', 'DUBOIS' ,'LeaDubois-1@email.com', '2023-01-01 12:00:00', NULL, 1 , 1);

-- Prestataire 02
INSERT INTO service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Louis', 'LEFEVRE' ,'LouisLefevre-1@email.com', '2023-01-01 12:00:00', NULL, 1 , 2);

-- Prestataire 03
INSERT INTO service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Emma', 'MARTIN' ,'EmmaMartin-1@email.com', '2023-01-01 12:00:00', NULL, 1 , 3);

-- Prestataire 04
INSERT INTO service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Lucas', 'BERNARD' ,'LucasBernard-1@email.com', '2023-01-01 12:00:00', NULL, 1 , 1);

-- Prestataire 05
INSERT INTO service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Chloe', 'DURAND' ,'ChloeDurand-1@email.com', '2023-01-01 12:00:00', NULL, 1 , 2);

-- Prestataire 06
INSERT INTO service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Gabriel', 'LEROY' ,'GabrielLeroy-1@email.com', '2023-01-01 12:00:00', NULL, 1 , 3);

-- Prestataire 07
INSERT INTO service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Manon', 'MOREAU' ,'ManonMoreau-1@email.com', '2023-01-01 12:00:00', NULL, 1 , 1);

-- Prestataire 08
INSERT INTO service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Jules', 'FOURNIER' ,'JulesFournier-1@email.com', '2023-01-01 12:00:00', NULL, 1 , 2);

-- Prestataire 09
INSERT INTO service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Charlotte', 'GIRARD' ,'CharlotteGirard-1@email.com', '2023-01-01 12:00:00', NULL, 1 , 3);

-- Prestataire 11
INSERT INTO service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Lea', 'DUBOIS' ,'LeaDubois-2@email.com', '2023-01-01 12:00:00', NULL, 2 , 1);

-- Prestataire 12
INSERT INTO service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Louis', 'LEFEVRE' ,'LouisLefevre-2@email.com', '2023-01-01 12:00:00', NULL, 2 , 2);

-- Prestataire 13
INSERT INTO service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Emma', 'MARTIN' ,'EmmaMartin-2@email.com', '2023-01-01 12:00:00', NULL, 2 , 3);

-- Prestataire 14
INSERT INTO service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Lucas', 'BERNARD' ,'LucasBernard-2@email.com', '2023-01-01 12:00:00', NULL, 2 , 1);

-- Prestataire 15
INSERT INTO service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Chloe', 'DURAND' ,'ChloeDurand-2@email.com', '2023-01-01 12:00:00', NULL, 2 , 2);

-- Prestataire 16
INSERT INTO service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Gabriel', 'LEROY' ,'GabrielLeroy-2@email.com', '2023-01-01 12:00:00', NULL, 2 , 3);

-- Prestataire 17
INSERT INTO service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Manon', 'MOREAU' ,'ManonMoreau-2@email.com', '2023-01-01 12:00:00', NULL, 2 , 1);

-- Prestataire 18
INSERT INTO service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Jules', 'FOURNIER' ,'JulesFournier-2@email.com', '2023-01-01 12:00:00', NULL, 2 , 2);

-- Prestataire 19
INSERT INTO service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Charlotte', 'GIRARD' ,'CharlotteGirard-2@email.com', '2023-01-01 12:00:00', NULL, 2 , 3);

-- Prestataire 21
INSERT INTO service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Lea', 'DUBOIS' ,'LeaDubois-3@email.com', '2023-01-01 12:00:00', NULL, 3 , 1);

-- Prestataire 22
INSERT INTO service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Louis', 'LEFEVRE' ,'LouisLefevre-3@email.com', '2023-01-01 12:00:00', NULL, 3 , 2);

-- Prestataire 23
INSERT INTO service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Emma', 'MARTIN' ,'EmmaMartin-3@email.com', '2023-01-01 12:00:00', NULL, 3 , 3);

-- Prestataire 24
INSERT INTO service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Lucas', 'BERNARD' ,'LucasBernard-3@email.com', '2023-01-01 12:00:00', NULL, 3 , 1);

-- Prestataire 25
INSERT INTO service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Chloe', 'DURAND' ,'ChloeDurand-3@email.com', '2023-01-01 12:00:00', NULL, 3 , 2);

-- Prestataire 26
INSERT INTO service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Gabriel', 'LEROY' ,'GabrielLeroy-3@email.com', '2023-01-01 12:00:00', NULL, 3 , 3);

-- Prestataire 27
INSERT INTO service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Manon', 'MOREAU' ,'ManonMoreau-3@email.com', '2023-01-01 12:00:00', NULL, 3 , 1);

-- Prestataire 28
INSERT INTO service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Jules', 'FOURNIER' ,'JulesFournier-3@email.com', '2023-01-01 12:00:00', NULL, 3 , 2);

-- Prestataire 29
INSERT INTO service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Charlotte', 'GIRARD' ,'CharlotteGirard-3@email.com', '2023-01-01 12:00:00', NULL, 3 , 3);

-- Prestataire 31
INSERT INTO service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Lea', 'DUBOIS' ,'LeaDubois-4@email.com', '2023-01-01 12:00:00', NULL, 4 , 1);

-- Prestataire 32
INSERT INTO service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Louis', 'LEFEVRE' ,'LouisLefevre-4@email.com', '2023-01-01 12:00:00', NULL, 4 , 2);

-- Prestataire 33
INSERT INTO service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Emma', 'MARTIN' ,'EmmaMartin-4@email.com', '2023-01-01 12:00:00', NULL, 4 , 3);

-- Prestataire 34
INSERT INTO service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Lucas', 'BERNARD' ,'LucasBernard-4@email.com', '2023-01-01 12:00:00', NULL, 4 , 1);

-- Prestataire 35
INSERT INTO service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Chloe', 'DURAND' ,'ChloeDurand-4@email.com', '2023-01-01 12:00:00', NULL, 4 , 2);

-- Prestataire 36
INSERT INTO service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Gabriel', 'LEROY' ,'GabrielLeroy-4@email.com', '2023-01-01 12:00:00', NULL, 4 , 3);

-- Prestataire 37
INSERT INTO service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Manon', 'MOREAU' ,'ManonMoreau-4@email.com', '2023-01-01 12:00:00', NULL, 4 , 1);

-- Prestataire 38
INSERT INTO service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Jules', 'FOURNIER' ,'JulesFournier-4@email.com', '2023-01-01 12:00:00', NULL, 4 , 2);

-- Prestataire 39
INSERT INTO service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Charlotte', 'GIRARD' ,'CharlotteGirard-4@email.com', '2023-01-01 12:00:00', NULL, 4 , 3);

-- Prestataire 41
INSERT INTO service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Lea', 'DUBOIS' ,'LeaDubois-5@email.com', '2023-01-01 12:00:00', NULL, 5 , 1);

-- Prestataire 42
INSERT INTO service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Louis', 'LEFEVRE' ,'LouisLefevre-5@email.com', '2023-01-01 12:00:00', NULL, 5 , 2);

-- Prestataire 43
INSERT INTO service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Emma', 'MARTIN' ,'EmmaMartin-5@email.com', '2023-01-01 12:00:00', NULL, 5 , 3);

-- Prestataire 44
INSERT INTO service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Lucas', 'BERNARD' ,'LucasBernard-5@email.com', '2023-01-01 12:00:00', NULL, 5 , 1);

-- Prestataire 45
INSERT INTO service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Chloe', 'DURAND' ,'ChloeDurand-5@email.com', '2023-01-01 12:00:00', NULL, 5 , 2);

-- Prestataire 46
INSERT INTO service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Gabriel', 'LEROY' ,'GabrielLeroy-5@email.com', '2023-01-01 12:00:00', NULL, 5 , 3);

-- Prestataire 47
INSERT INTO service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Manon', 'MOREAU' ,'ManonMoreau-5@email.com', '2023-01-01 12:00:00', NULL, 5 , 1);

-- Prestataire 48
INSERT INTO service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Jules', 'FOURNIER' ,'JulesFournier-5@email.com', '2023-01-01 12:00:00', NULL, 5 , 2);

-- Prestataire 49
INSERT INTO service_provider (sp_first_name, sp_name, sp_email, sp_creation_date, sp_lastUpdate_date,sp_fk_subcontractor_id, sp_fk_status_id) VALUES ('Charlotte', 'GIRARD' ,'CharlotteGirard-5@email.com', '2023-01-01 12:00:00', NULL, 5 , 3);
