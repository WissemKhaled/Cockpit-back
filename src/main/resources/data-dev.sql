-- Status
INSERT INTO gst_status (st_id, st_name)
VALUES
    (1, 'En cours'),
    (2, 'En validation'),
    (3, 'Validé'),
    (4, 'Archivé'),
    (5, 'En cours - relance');
 
-- Message Category
INSERT INTO gst_category (cat_id, cat_name)
VALUES (1, 'SP'), (2, 'SC'), (3, 'KBIS'), (4, 'REG');
 
--model de message modification
INSERT INTO gst_message_model (mm_link, mm_subject, mm_body, mm_has_email, mm_is_relaunch, mm_creation_date, mm_last_update, mm_fk_category_id) VALUES (1, 'Demande des documents administratifs du prestataire', 'je suis le body du mail', 't', 'f', CURRENT_TIMESTAMP, NULL, 1);
INSERT INTO gst_message_model (mm_link, mm_subject, mm_body, mm_has_email, mm_is_relaunch, mm_creation_date, mm_last_update, mm_fk_category_id) VALUES (1, 'Relance des documents administratifs du prestataire', 'je suis le body du mail', 't', 't', CURRENT_TIMESTAMP, NULL, 1);
INSERT INTO gst_message_model (mm_link, mm_subject, mm_body, mm_has_email, mm_is_relaunch, mm_creation_date, mm_last_update, mm_fk_category_id) VALUES (2, 'Demande création à la comptabilité du prestataire', 'je suis le body du mail', 'f', 'f', CURRENT_TIMESTAMP, NULL, 1);
INSERT INTO gst_message_model (mm_link, mm_subject, mm_body, mm_has_email, mm_is_relaunch, mm_creation_date, mm_last_update, mm_fk_category_id) VALUES (2, 'Relance création à la comptabilité du prestataire', 'je suis le body du mail', 'f', 't', CURRENT_TIMESTAMP, NULL, 1);
INSERT INTO gst_message_model (mm_link, mm_subject, mm_body, mm_has_email, mm_is_relaunch, mm_creation_date, mm_last_update, mm_fk_category_id) VALUES (3, 'Demande de signatures d''annexes du prestataire', 'je suis le body du mail', 't', 'f', CURRENT_TIMESTAMP, NULL, 1);
INSERT INTO gst_message_model (mm_link, mm_subject, mm_body, mm_has_email, mm_is_relaunch, mm_creation_date, mm_last_update, mm_fk_category_id) VALUES (3, 'Relance des signatures d''annexe du prestataire', 'je suis le body du mail', 'f', 't', CURRENT_TIMESTAMP, NULL, 1);
INSERT INTO gst_message_model (mm_link, mm_subject, mm_body, mm_has_email, mm_is_relaunch, mm_creation_date, mm_last_update, mm_fk_category_id) VALUES (4, 'Demande renouvellement des documents administratifs du prestataire', 'je suis le body du mail', 't', 'f', CURRENT_TIMESTAMP, NULL, 1);
INSERT INTO gst_message_model (mm_link, mm_subject, mm_body, mm_has_email, mm_is_relaunch, mm_creation_date, mm_last_update, mm_fk_category_id) VALUES (4, 'Relance renouvellement des documents administratif du prestataire', 'je suis le body du mail', 't', 't', CURRENT_TIMESTAMP, NULL, 1);

INSERT INTO gst_message_model (mm_link, mm_subject, mm_body, mm_has_email, mm_is_relaunch, mm_creation_date, mm_last_update, mm_fk_category_id) VALUES (5, 'Demande des documents de régularisation du sous-traitant', '<p class="h6">Bonjour <span class="text-danger">{{nom}},</span>  <br/>

<br/>

Conformément à la demande de <span class="text-danger">{{NomCommercial}} *</span>, je vous sollicite pour la partie administrative concernant votre référencement fournisseur.<br/>

<br/>

Nous avons besoin des éléments listés ci-dessous et des pièces jointes :<br/>

<br/>

{{NAF}}<br/>

{{SIREN}}<br/>

{{SIRET}}<br/>

{{TVA}} <br/>

<br/>

{{PJ}}<br/>

<br/>

{{vigilance}}<br/>

{{Kbis}}<br/>

{{RIB}}<br/>

{{assurance}}<br/>

<br/>

Par ailleurs, concernant votre collaborateur, nous avons également besoin du tableau de nationalité joint ainsi que des éléments suivants :<br/>

<br/>

{{nomPrenom}}<br/>

{{mail}}<br/>

{{tel}}<br/>

<br/>

{{volet Administartif}}<br/>

<br/>

En vous remerciant par avance et restant à votre disposition.<br/>

<br/>

Cordialement.</p>', 't', 'f', CURRENT_TIMESTAMP, NULL, 2);
INSERT INTO gst_message_model (mm_link, mm_subject, mm_body, mm_has_email, mm_is_relaunch, mm_creation_date, mm_last_update, mm_fk_category_id) VALUES (5, 'Relance des documents de régularisation du sous-traitant', 'je suis le body du mail', 't', 't', CURRENT_TIMESTAMP, NULL, 2);
INSERT INTO gst_message_model (mm_link, mm_subject, mm_body, mm_has_email, mm_is_relaunch, mm_creation_date, mm_last_update, mm_fk_category_id) VALUES (6, 'Demande création à la comptabilité du sous-traitant', '

Bonjour,<br/>
<br/>
Vous trouverez, en PJ et ci-dessous, les éléments nécessaires à la création du sous-traitant <span class="text-danger">{{nom sous-traitant}},</span> .<br/>
Restant à votre disposition si besoin.<br/>
Bonne journée.', 'f', 'f', CURRENT_TIMESTAMP, NULL, 2);
INSERT INTO gst_message_model (mm_link, mm_subject, mm_body, mm_has_email, mm_is_relaunch, mm_creation_date, mm_last_update, mm_fk_category_id) VALUES (6, 'Relance de création à la comptabilité du sous-traitant', 'je suis le body du mail', 'f', 't', CURRENT_TIMESTAMP, NULL, 2);
INSERT INTO gst_message_model (mm_link, mm_subject, mm_body, mm_has_email, mm_is_relaunch, mm_creation_date, mm_last_update, mm_fk_category_id) VALUES (7, 'Demande de signatures de contrat du sous-traitant', 'je suis le body du mail', 't', 'f', CURRENT_TIMESTAMP, NULL, 2);
INSERT INTO gst_message_model (mm_link, mm_subject, mm_body, mm_has_email, mm_is_relaunch, mm_creation_date, mm_last_update, mm_fk_category_id) VALUES (7, 'Relance des signatures de contrat du sous-traitant', 'je suis le body du mail', 't', 't', CURRENT_TIMESTAMP, NULL, 2);
INSERT INTO gst_message_model (mm_link, mm_subject, mm_body, mm_has_email, mm_is_relaunch, mm_creation_date, mm_last_update, mm_fk_category_id) VALUES (8, 'Demande de renouvellment du KBIS du sous-traitant', 'je suis le body du mail', 't', 'f', CURRENT_TIMESTAMP, NULL, 3);
INSERT INTO gst_message_model (mm_link, mm_subject, mm_body, mm_has_email, mm_is_relaunch, mm_creation_date, mm_last_update, mm_fk_category_id) VALUES (8, 'Relance de renouvellment du KBIS du sous-traitant', 'je suis le body du mail', 't', 't', CURRENT_TIMESTAMP, NULL, 3);
 
-- Subcontractor 1
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id)
VALUES ('Orange', 'Orange@email.fr', 1);
 
-- Subcontractor 2
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id)
VALUES ('Bpce', 'Bpce@email.com', 1);
 
-- Subcontractor 3
INSERT INTO gst_subcontractor (s_name, s_email, s_creation_date, s_fk_status_id)
VALUES ('Edf', 'Edf@email.fr', '2023-01-01 12:00:00', 1);
 
-- Subcontractor 4
INSERT INTO gst_subcontractor (s_name, s_email, s_creation_date, s_lastUpdate_date, s_fk_status_id)
VALUES ('Enedis', 'Enedis@email.fr', '2023-01-01 12:00:00', NULL, 1);
 
-- Subcontractor 5
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id)
VALUES ('Lyon', 'Lyon@email.fr', 1);

-- Subcontractor 6
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('TotalEnergies', 'TotalEnergies@email.fr', 1); 

-- Subcontractor 7
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('Sanofi', 'Sanofi@email.fr', 1); 

-- Subcontractor 8
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('Engie', 'Engie@email.fr', 1); 

-- Subcontractor 9
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('Vinci', 'Vinci@email.fr', 1); 

-- Subcontractor 10
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('Alstom', 'Alstom@email.fr', 1); 

-- Subcontractor 11
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('Sncf', 'Sncf@email.fr', 1); 

-- Subcontractor 12
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('Carrefour', 'Carrefour@email.fr', 1); 

-- Subcontractor 13
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('Valeo', 'Valeo@email.fr', 1); 

 
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
 
-- Contracts
-- 1 contrat par prestataire
INSERT INTO gst_contract (c_contract_number, c_fk_subcontractor_id, c_fk_service_provider_id)
SELECT
    'AFR546CVGF' || sp.sp_id AS c_contract_number,
    sp.sp_fk_subcontractor_id AS c_fk_subcontractor_id,
    sp.sp_id AS c_fk_service_provider_id
FROM gst_service_provider sp;
 
-- gst_model_tracking
INSERT INTO gst_model_tracking (mt_send_date, mt_validation_date, mt_fk_contract_id, mt_fk_message_model_id, mt_fk_status_id, mt_fk_category_id)
SELECT
    NULL AS mt_send_date,
    NULL AS mt_validation_date,
    c.c_id AS mt_fk_contract_id,
    mm.mm_id AS mt_fk_message_model_id,
    CASE WHEN mm.mm_is_relaunch
         THEN 5
         ELSE s.sp_fk_status_id
    END AS mt_fk_status_id,
    mm.mm_fk_category_id AS mt_fk_category_id
FROM gst_service_provider s
JOIN gst_contract c ON s.sp_id = c.c_fk_service_provider_id
JOIN gst_message_model mm ON s.sp_fk_status_id IS NOT NULL
WHERE mm.mm_fk_category_id IN (1,2,3)
ORDER BY s.sp_id, mm.mm_id;