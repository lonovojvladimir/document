-- auto-generated definition
create table test.audit_document
(
    id_audit_document       numeric not null,
    operation               varchar,
    code                    varchar,
    property                varchar,
    old_value               varchar,
    new_value               varchar,
    modifier                varchar,
    modified                date
);

alter table test.audit_document
    owner to postgres;


-- auto-generated definition
create sequence test.audit_document_sequence
    minvalue 5
    maxvalue 999999;

alter sequence test.audit_document_sequence owner to postgres;
