create sequence AUDIT_NORMATIVE_SEQUENCE;

CREATE TABLE AUDIT_NORMATIVE
(
    AUDIT_NORMATIVE_ID number(19,0) not null,

    OPERATION          varchar2(255),
    NORMATIVE          varchar2(255),

    PROPERTY           varchar2(255),
    OLD_VALUE          varchar2(255),
    NEW_VALUE          varchar2(255),

    MODIFIER           varchar2(100),
    MODIFIED           date,

    primary key (AUDIT_NORMATIVE_ID)
);