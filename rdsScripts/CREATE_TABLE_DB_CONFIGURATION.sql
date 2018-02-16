CREATE TABLE db_configuration (
     configid	SERIAL PRIMARY KEY,
     configdata	jsonb,
     tablename 	character varying(64),
     historytablename 	character varying(64),
     executejob character varying(64) DEFAULT 'N'::character varying,
     functionname character varying(64),
     createddate timestamp without time zone DEFAULT now(),
     createdby character varying(64) DEFAULT 'System'::character varying,
     updateddate timestamp without time zone,
     updatedby character varying(64)
    );