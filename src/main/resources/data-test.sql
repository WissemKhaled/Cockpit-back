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

INSERT INTO gst_message_model (mm_link, mm_subject, mm_body, mm_has_email, mm_is_relaunch, mm_creation_date, mm_last_update, mm_fk_category_id) VALUES (1, 'Demande des documents administratifs du prestataire', 'je suis le body du mail', 't', 'f', CURRENT_TIMESTAMP, NULL, 1);
INSERT INTO gst_message_model (mm_link, mm_subject, mm_body, mm_has_email, mm_is_relaunch, mm_creation_date, mm_last_update, mm_fk_category_id) VALUES (1, 'Relance des documents administratifs du prestataire', 'je suis le body du mail', 't', 't', CURRENT_TIMESTAMP, NULL, 1);
INSERT INTO gst_message_model (mm_link, mm_subject, mm_body, mm_has_email, mm_is_relaunch, mm_creation_date, mm_last_update, mm_fk_category_id) VALUES (2, 'Demande création à la comptabilité du prestataire', 'je suis le body du mail', 'f', 'f', CURRENT_TIMESTAMP, NULL, 1);
INSERT INTO gst_message_model (mm_link, mm_subject, mm_body, mm_has_email, mm_is_relaunch, mm_creation_date, mm_last_update, mm_fk_category_id) VALUES (2, 'Relance création à la comptabilité du prestataire', 'je suis le body du mail', 'f', 't', CURRENT_TIMESTAMP, NULL, 1);
INSERT INTO gst_message_model (mm_link, mm_subject, mm_body, mm_has_email, mm_is_relaunch, mm_creation_date, mm_last_update, mm_fk_category_id) VALUES (3, 'Demande de signatures d''annexes du prestataire', 'je suis le body du mail', 't', 'f', CURRENT_TIMESTAMP, NULL, 1);
INSERT INTO gst_message_model (mm_link, mm_subject, mm_body, mm_has_email, mm_is_relaunch, mm_creation_date, mm_last_update, mm_fk_category_id) VALUES (3, 'Relance des signatures d''annexe du prestataire', 'je suis le body du mail', 'f', 't', CURRENT_TIMESTAMP, NULL, 1);
INSERT INTO gst_message_model (mm_link, mm_subject, mm_body, mm_has_email, mm_is_relaunch, mm_creation_date, mm_last_update, mm_fk_category_id) VALUES (4, 'Demande renouvellement des documents administratifs du prestataire', 'je suis le body du mail', 't', 'f', CURRENT_TIMESTAMP, NULL, 1);
INSERT INTO gst_message_model (mm_link, mm_subject, mm_body, mm_has_email, mm_is_relaunch, mm_creation_date, mm_last_update, mm_fk_category_id) VALUES (4, 'Relance renouvellement des documents administratif du prestataire', 'je suis le body du mail', 't', 't', CURRENT_TIMESTAMP, NULL, 1);

INSERT INTO gst_message_model (mm_link, mm_subject, mm_body, mm_has_email, mm_is_relaunch, mm_creation_date, mm_last_update, mm_fk_category_id) VALUES (5, 'Demande des documents de régularisation du sous-traitant', '<p class="h6">Bonjour   <br/>

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

{{création de fournisseur}}<br/>

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
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('Orange', 'Orange@email.fr', 1); 

-- Subcontractor 2
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('Enedis', 'Enedis@email.fr', 1); 

-- Subcontractor 3
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('TotalEnergies', 'TotalEnergies@email.fr', 1); 

-- Subcontractor 4
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('Sanofi', 'Sanofi@email.fr', 1); 

-- Subcontractor 5
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('Engie', 'Engie@email.fr', 1); 

-- Subcontractor 6
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('Vinci', 'Vinci@email.fr', 1); 

-- Subcontractor 7
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('Alstom', 'Alstom@email.fr', 1); 

-- Subcontractor 8
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('Sncf', 'Sncf@email.fr', 1); 

-- Subcontractor 9
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('Carrefour', 'Carrefour@email.fr', 1); 

-- Subcontractor 10
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('Valeo', 'Valeo@email.fr', 1); 

-- Subcontractor 11
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('TechGuru Solutions', 'TechGuruSolutions@email.fr', 1); 

-- Subcontractor 12
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('InnovateTech Labs', 'InnovateTechLabs@email.fr', 1); 

-- Subcontractor 13
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('Quantum Innovations', 'QuantumInnovations@email.fr', 1); 

-- Subcontractor 14
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('CyberDynasty Systems', 'CyberDynastySystems@email.fr', 1); 

-- Subcontractor 15
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('DataNex Corporation', 'DataNexCorporation@email.fr', 1); 

-- Subcontractor 16
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('ByteBurst Technologies', 'ByteBurstTechnologies@email.fr', 1); 

-- Subcontractor 17
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('VirtuosoSoft Solutions', 'VirtuosoSoftSolutions@email.fr', 1); 

-- Subcontractor 18
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('SpectraMind Innovations', 'SpectraMindInnovations@email.fr', 1); 

-- Subcontractor 19
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('CodeCrafters Inc.', 'CodeCraftersInc.@email.fr', 1); 

-- Subcontractor 20
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('LogicPulse Technologies', 'LogicPulseTechnologies@email.fr', 1); 

-- Subcontractor 21
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('NexGen Dynamics', 'NexGenDynamics@email.fr', 1); 

-- Subcontractor 22
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('StriveSync Solutions', 'StriveSyncSolutions@email.fr', 1); 

-- Subcontractor 23
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('IntelliNet Innovators', 'IntelliNetInnovators@email.fr', 1); 

-- Subcontractor 24
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('DigitalVision Systems', 'DigitalVisionSystems@email.fr', 1); 

-- Subcontractor 25
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('TechnoLogic Innovations', 'TechnoLogicInnovations@email.fr', 1); 

-- Subcontractor 26
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('Futurize Technologies', 'FuturizeTechnologies@email.fr', 1); 

-- Subcontractor 27
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('CyberCore Labs', 'CyberCoreLabs@email.fr', 1); 

-- Subcontractor 28
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('CloudVista Solutions', 'CloudVistaSolutions@email.fr', 1); 

-- Subcontractor 29
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('SwiftStream Tech', 'SwiftStreamTech@email.fr', 1); 

-- Subcontractor 30
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('QuantumCode Innovations', 'QuantumCodeInnovations@email.fr', 1); 

-- Subcontractor 31
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('EpicSoft Solutions', 'EpicSoftSolutions@email.fr', 2); 

-- Subcontractor 32
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('InnoByte Dynamics', 'InnoByteDynamics@email.fr', 2); 

-- Subcontractor 33
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('ByteBlitz Technologies', 'ByteBlitzTechnologies@email.fr', 2); 

-- Subcontractor 34
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('VisionaryTech Inc.', 'VisionaryTechInc.@email.fr', 2); 

-- Subcontractor 35
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('PulseCrafters Innovations', 'PulseCraftersInnovations@email.fr', 2); 

-- Subcontractor 36
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('SwiftLogic Labs', 'SwiftLogicLabs@email.fr', 2); 

-- Subcontractor 37
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('EcoInnovate Systems', 'EcoInnovateSystems@email.fr', 2); 

-- Subcontractor 38
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('SkySprint Technologies', 'SkySprintTechnologies@email.fr', 2); 

-- Subcontractor 39
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('GlobalNex Dynamics', 'GlobalNexDynamics@email.fr', 2); 

-- Subcontractor 40
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('EpicScape Innovations', 'EpicScapeInnovations@email.fr', 2); 

-- Subcontractor 41
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('VortexCraft Technologies', 'VortexCraftTechnologies@email.fr', 2); 

-- Subcontractor 42
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('InfernoByte Labs', 'InfernoByteLabs@email.fr', 2); 

-- Subcontractor 43
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('TechNest Innovations', 'TechNestInnovations@email.fr', 2); 

-- Subcontractor 44
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('QuantumSync Systems', 'QuantumSyncSystems@email.fr', 2); 

-- Subcontractor 45
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('VisionWave Technologies', 'VisionWaveTechnologies@email.fr', 2); 

-- Subcontractor 46
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('ByteBlaze Innovations', 'ByteBlazeInnovations@email.fr', 2); 

-- Subcontractor 47
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('NexCore Dynamics', 'NexCoreDynamics@email.fr', 2); 

-- Subcontractor 48
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('FusionCrafters Inc.', 'FusionCraftersInc.@email.fr', 2); 

-- Subcontractor 49
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('InfinitePulse Technologies', 'InfinitePulseTechnologies@email.fr', 2); 

-- Subcontractor 50
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('CogniByte Innovations', 'CogniByteInnovations@email.fr', 2); 

-- Subcontractor 51
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('InnoScape Dynamics', 'InnoScapeDynamics@email.fr', 2); 

-- Subcontractor 52
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('QuantumFleet Technologies', 'QuantumFleetTechnologies@email.fr', 2); 

-- Subcontractor 53
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('VistaLogic Innovations', 'VistaLogicInnovations@email.fr', 2); 

-- Subcontractor 54
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('CodeWave Dynamics', 'CodeWaveDynamics@email.fr', 2); 

-- Subcontractor 55
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('SynthCore Technologies', 'SynthCoreTechnologies@email.fr', 2); 

-- Subcontractor 56
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('ByteShift Innovations', 'ByteShiftInnovations@email.fr', 2); 

-- Subcontractor 57
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('SkyVista Dynamics', 'SkyVistaDynamics@email.fr', 2); 

-- Subcontractor 58
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('PulseLogic Technologies', 'PulseLogicTechnologies@email.fr', 2); 

-- Subcontractor 59
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('NexStream Innovations', 'NexStreamInnovations@email.fr', 2); 

-- Subcontractor 60
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('EcoTech Labs', 'EcoTechLabs@email.fr', 2); 

-- Subcontractor 61
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('QuantumNest Innovations', 'QuantumNestInnovations@email.fr', 3); 

-- Subcontractor 62
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('StriveScape Dynamics', 'StriveScapeDynamics@email.fr', 3); 

-- Subcontractor 63
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('VortexSync Innovations', 'VortexSyncInnovations@email.fr', 3); 

-- Subcontractor 64
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('DigitalCrafters Technologies', 'DigitalCraftersTechnologies@email.fr', 3); 

-- Subcontractor 65
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('InnoVista Dynamics', 'InnoVistaDynamics@email.fr', 3); 

-- Subcontractor 66
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('FusionNest Innovations', 'FusionNestInnovations@email.fr', 3); 

-- Subcontractor 67
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('TechScape Dynamics', 'TechScapeDynamics@email.fr', 3); 

-- Subcontractor 68
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('QuantumWave Innovations', 'QuantumWaveInnovations@email.fr', 3); 

-- Subcontractor 69
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('ByteVista Dynamics', 'ByteVistaDynamics@email.fr', 3); 

-- Subcontractor 70
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('InfernoCrafters Innovations', 'InfernoCraftersInnovations@email.fr', 3); 

-- Subcontractor 71
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('VirtuosoCraft Technologies', 'VirtuosoCraftTechnologies@email.fr', 3); 

-- Subcontractor 72
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('SkyCrafters Innovations', 'SkyCraftersInnovations@email.fr', 3); 

-- Subcontractor 73
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('CodeScape Dynamics', 'CodeScapeDynamics@email.fr', 3); 

-- Subcontractor 74
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('SwiftCraft Technologies', 'SwiftCraftTechnologies@email.fr', 3); 

-- Subcontractor 75
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('SynthNex Innovations', 'SynthNexInnovations@email.fr', 3); 

-- Subcontractor 76
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('CogniCrafters Technologies', 'CogniCraftersTechnologies@email.fr', 3); 

-- Subcontractor 77
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('TechSync Innovations', 'TechSyncInnovations@email.fr', 3); 

-- Subcontractor 78
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('InnoVortex Dynamics', 'InnoVortexDynamics@email.fr', 3); 

-- Subcontractor 79
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('PulseVista Innovations', 'PulseVistaInnovations@email.fr', 3); 

-- Subcontractor 80
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('NexCraft Technologies', 'NexCraftTechnologies@email.fr', 3); 

-- Subcontractor 81
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('QuantumScape Innovations', 'QuantumScapeInnovations@email.fr', 3); 

-- Subcontractor 82
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('VortexCrafters Technologies', 'VortexCraftersTechnologies@email.fr', 3); 

-- Subcontractor 83
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('EpicByte Dynamics', 'EpicByteDynamics@email.fr', 3); 

-- Subcontractor 84
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('VisionaryCraft Innovations', 'VisionaryCraftInnovations@email.fr', 3); 

-- Subcontractor 85
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('InnoVirtuoso Dynamics', 'InnoVirtuosoDynamics@email.fr', 3); 

-- Subcontractor 86
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('FusionScape Innovations', 'FusionScapeInnovations@email.fr', 3); 

-- Subcontractor 87
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('ByteSprint Dynamics', 'ByteSprintDynamics@email.fr', 3); 

-- Subcontractor 88
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('TechByte Innovations', 'TechByteInnovations@email.fr', 3); 

-- Subcontractor 89
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('SwiftScape Dynamics', 'SwiftScapeDynamics@email.fr', 3); 

-- Subcontractor 90
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('SkySprint Innovations', 'SkySprintInnovations@email.fr', 3); 

-- Subcontractor 91
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('QuantumByte Technologies', 'QuantumByteTechnologies@email.fr', 3); 

-- Subcontractor 92
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('EpicSprint Innovations', 'EpicSprintInnovations@email.fr', 3); 

-- Subcontractor 93
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('InnoPulse Dynamics', 'InnoPulseDynamics@email.fr', 3); 

-- Subcontractor 94
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('VisionaryNex Innovations', 'VisionaryNexInnovations@email.fr', 3); 

-- Subcontractor 95
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('PulseByte Technologies', 'PulseByteTechnologies@email.fr', 3); 

-- Subcontractor 96
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('NexLogic Dynamics', 'NexLogicDynamics@email.fr', 3); 

-- Subcontractor 97
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('QuantumCrafters Innovations', 'QuantumCraftersInnovations@email.fr', 3); 

-- Subcontractor 98
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('VirtuosoSync Technologies', 'VirtuosoSyncTechnologies@email.fr', 3); 

-- Subcontractor 99
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('TechSprint Dynamics', 'TechSprintDynamics@email.fr', 3); 

-- Subcontractor 100
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('CodeNex Innovations', 'CodeNexInnovations@email.fr', 3); 

-- Subcontractor 101
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('SynthLogic Dynamics', 'SynthLogicDynamics@email.fr', 3); 

-- Subcontractor 102
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('SkyLogic Innovations', 'SkyLogicInnovations@email.fr', 4); 

-- Subcontractor 103
INSERT INTO gst_subcontractor (s_name, s_email, s_fk_status_id) VALUES ('CogniSync Technologies', 'CogniSyncTechnologies@email.fr', 4); 

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