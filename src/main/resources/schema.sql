DROP TABLE db_configuration IF EXISTS;
CREATE TABLE db_configuration (
     configid	integer PRIMARY KEY,
     configdata	CLOB,
     tablename 	varchar(64),
     historytablename 	varchar(64),
     executejob varchar(64),
     functionname varchar(64),
    );