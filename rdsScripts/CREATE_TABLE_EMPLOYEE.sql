CREATE TABLE history_employee
(
    employeeid bigserial NOT NULL ,
    name character varying(64) NOT NULL,
    orderid character varying(64) NOT NULL,
    status character varying(64)
);

CREATE TABLE employee
(
    employeeid bigserial NOT NULL ,
    name character varying(64) NOT NULL,
    orderid character varying(64) NOT NULL,
    status character varying(64)
);