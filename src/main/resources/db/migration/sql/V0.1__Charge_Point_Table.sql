DROP TABLE IF EXISTS charge_point;

DROP TYPE IF EXISTS charge_type CASCADE;
DROP TYPE IF EXISTS status CASCADE;

DROP CAST IF EXISTS (varchar AS charge_type) CASCADE;
DROP CAST IF EXISTS (varchar AS status) CASCADE;

CREATE TYPE charge_type AS ENUM ('FAST', 'SLOW', 'OFF');
CREATE TYPE status AS ENUM ('OCCUPIED', 'AVAILABLE');

CREATE CAST (varchar AS charge_type) WITH INOUT AS IMPLICIT;
CREATE CAST (varchar AS status) WITH INOUT AS IMPLICIT;

CREATE TABLE charge_point
(
    id           bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    status       status,
    charge_type  charge_type,
    last_plugged timestamp,

    CONSTRAINT PK_charge_point PRIMARY KEY (id)
);

