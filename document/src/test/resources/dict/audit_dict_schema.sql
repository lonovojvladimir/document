create sequence AUDIT_DICT_SEQUENCE minvalue 1000;

CREATE TABLE AUDIT_DICT
(
    AUDIT_DICT_ID number(19,0) not null,

    OPERATION        varchar2(255),
    CODE             varchar2(50),
    DICT_TYPE        varchar2(255),
    PROPERTY         varchar2(255),
    OLD_VALUE        varchar2(255),
    NEW_VALUE        varchar2(255),

    MODIFIER         varchar2(100),
    MODIFIED         date,

    primary key (AUDIT_DICT_ID)
);