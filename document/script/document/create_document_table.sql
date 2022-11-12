-- auto-generated definition
create table test.document
(
    id_document      numeric not null,
    code             varchar,
    title            varchar,
    comment          varchar,
    creator         varchar,
    created         date,
    updater         varchar,
    updated         date
);

alter table test.document
    owner to postgres;


-- auto-generated definition
create sequence document_sequence
    minvalue 5
    maxvalue 999999;

alter sequence document_sequence owner to postgres;

