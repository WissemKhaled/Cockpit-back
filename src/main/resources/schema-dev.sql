
-- Table: public.gst_log

-- DROP TABLE IF EXISTS public.gst_log;
CREATE SEQUENCE public.gst_log_log_id_seq
    INCREMENT 1
    START 1;

ALTER SEQUENCE IF EXISTS public.gst_log_log_id_seq
    OWNER to postgres;

CREATE SEQUENCE public.u_user_u_id_seq
    INCREMENT 1
    START 1;
ALTER SEQUENCE IF EXISTS public.u_user_u_id_seq
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.gst_log
(
    log_id            SERIAL PRIMARY KEY, -- Keeping SERIAL as it implicitly creates a sequence
    log_type          VARCHAR(45)         NOT NULL,
    log_email         VARCHAR(255)        NOT NULL,
    log_password      VARCHAR(255)        DEFAULT NULL, -- Included from the first definition
    log_value         VARCHAR(45),
    log_creation_date TIMESTAMP           DEFAULT CURRENT_TIMESTAMP,
    log_last_update   TIMESTAMP           -- Included from the first definition
    );


ALTER TABLE IF EXISTS public.gst_log
    OWNER to postgres;

CREATE TYPE status_type AS ENUM ('En cours', 'En validation', 'Validé', 'Archivé');

ALTER TYPE status_type OWNER TO postgres;


-- auto-generated definition
create table status
(
    st_id          serial primary key,
    type           status_type NOT NULL,
    st_description varchar(255) default NULL::character varying
);

alter table status
    owner to postgres;


-- auto-generated definition
create table service_provider
(
    sp_id              serial primary key,
    sp_first_name      varchar(250) not null,
    sp_name            varchar(250) not null,
    sp_email           varchar(45)  not null,
    sp_creation_date   timestamp default CURRENT_TIMESTAMP,
    sp_lastupdate_date timestamp
);

alter table service_provider
    owner to postgres;


-- auto-generated definition
-- service_provider (one) -> subcontractor(many)
create table subcontractor
(
    s_id                serial primary key,
    s_name              varchar(250) not null,
    s_email             varchar(45)  not null,
    s_creation_date     timestamp default CURRENT_TIMESTAMP,
    s_lastupdate_date   timestamp,
    service_provider_id INT          NOT NULL,
    FOREIGN KEY (service_provider_id) REFERENCES service_provider (sp_id)
);

alter table subcontractor
    owner to postgres;


-- gst_sc_message_model (one) -> mail(one)
-- gst_sc_message_model (one) -> status(one)
-- subcontractor(one) -> gst_sc_message_model(many)
-- gst_sp_message_model (one) -> mail(one)
-- gst_sp_message_model (one) -> status(one)
-- service_provider(one) -> gst_sp_message_model(many)
create table gst_message_model
(
    mm_id               serial primary key,
--     mm_type             status_type NOT NULL,
    mm_subject          varchar(255) not null,
    mm_body             text         not null,
    mm_creation_date    timestamp default CURRENT_TIMESTAMP,
    mm_send_date        timestamp default CURRENT_TIMESTAMP,
    mm_last_update      timestamp,
    status_fk           INT          NOT NULL,
    FOREIGN KEY (status_fk) REFERENCES status (st_id),
    service_provider_fk INT          NOT NULL,
    FOREIGN KEY (service_provider_fk) REFERENCES service_provider (sp_id),
    subcontractor_fk INT          NOT NULL,
    FOREIGN KEY (subcontractor_fk) REFERENCES subcontractor (s_id)
);

alter table gst_message_model
    owner to postgres;

-- auto-generated definition
-- gst_sp_message_model (one) -> message_send(many)
-- gst_sc_message_model (one) -> message_send(many)
create table message_send
(
    ms_id                   serial primary key,
    ms_sender               varchar(55)  not null,
    ms_to                   varchar(55)  default NULL::character varying,
    ms_cc                   varchar(255) default NULL::character varying,
    ms_subject              varchar(255) not null,
    ms_body                 text         not null,
    ms_error                varchar(250),
    ms_status               smallint,
    gst_message_model_fk INT          NOT NULL,
    FOREIGN KEY (gst_message_model_fk) REFERENCES gst_message_model (mm_id)
);

alter table message_send
    owner to postgres;


CREATE TABLE IF NOT EXISTS public.u_user
(
    u_id             integer                                             NOT NULL DEFAULT nextval('u_user_u_id_seq'::regclass),
    u_email          character varying(45) COLLATE pg_catalog."default"  NOT NULL,
    u_password       character varying(255) COLLATE pg_catalog."default" NOT NULL,
    u_first_name     character varying(45) COLLATE pg_catalog."default"           DEFAULT NULL::character varying,
    u_last_name      character varying(45) COLLATE pg_catalog."default"           DEFAULT NULL::character varying,
    u_status         boolean                                                      DEFAULT false,
    u_insertion_date timestamp without time zone                                  DEFAULT CURRENT_TIMESTAMP,
    u_last_update    timestamp without time zone,
    CONSTRAINT u_user_pkey PRIMARY KEY (u_id)
    )
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.u_user
    OWNER to postgres;


-- auto-generated definition
create table refresh_token
(
    rt_id          serial primary key,
    rt_token       varchar(255) not null,
    rt_expiry_date timestamp    not null,
    rt_fk_user_id  smallint     not null
        references u_user
);

alter table refresh_token
    owner to postgres;


-- Table: public.u_user

-- DROP TABLE IF EXISTS public.u_user;



CREATE TABLE IF NOT EXISTS gst_status_model_service_provider
(
    status_msp_id                     SERIAL PRIMARY KEY,
    status_msp_fk_service_provider_id SERIAL NOT NULL,
    status_msp_fk_sp_message_model_id    SERIAL NOT NULL,
    status_msp_fk_status_id           SERIAL NOT NULL,

    FOREIGN KEY (status_msp_fk_service_provider_id) REFERENCES service_provider (sp_id),
    FOREIGN KEY (status_msp_fk_sp_message_model_id) REFERENCES gst_message_model (mm_id),
    FOREIGN KEY (status_msp_fk_status_id) REFERENCES status (st_id)
    );

CREATE TABLE IF NOT EXISTS gst_status_model_subcontractor
(
    status_ms_id                  SERIAL PRIMARY KEY,
    status_ms_fk_subcontractor_id SERIAL NOT NULL,
    status_ms_fk_sc_message_model_id SERIAL NOT NULL,
    status_ms_fk_status_id        SERIAL NOT NULL,

    FOREIGN KEY (status_ms_fk_subcontractor_id) REFERENCES subcontractor (s_id),
    FOREIGN KEY (status_ms_fk_sc_message_model_id) REFERENCES gst_message_model (mm_id),
    FOREIGN KEY (status_ms_fk_status_id) REFERENCES status (st_id)
    );

