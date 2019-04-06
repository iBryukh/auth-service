-- CREATE TABLES

DROP TABLE IF EXISTS role CASCADE;
DROP TABLE IF EXISTS customer CASCADE;
DROP TABLE IF EXISTS customer_device CASCADE;

CREATE TABLE IF NOT EXISTS role
(
    id           SERIAL       NOT NULL,
    name         VARCHAR(255) NOT NULL,
    access_level INTEGER      NOT NULL,
    CONSTRAINT role_access_level_check
        CHECK ((access_level <= 3) AND (access_level >= 0)),
    CONSTRAINT role_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS customer
(
    id           SERIAL       NOT NULL,
    email        VARCHAR(255) NOT NULL,
    name         VARCHAR(255) NOT NULL,
    password     VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
    role_id      INT          NOT NULL,
    CONSTRAINT customer_role_fk FOREIGN KEY (role_id) REFERENCES role (id),
    CONSTRAINT customer_pk PRIMARY KEY (id),
    CONSTRAINT email_uq UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS customer_device
(
    customer_id integer NOT NULL,
    device_id   integer NOT NULL,
    CONSTRAINT customer_device_fk FOREIGN KEY (customer_id) REFERENCES customer (id),
    CONSTRAINT customer_device_pk PRIMARY KEY (customer_id, device_id)
);

ALTER TABLE role
    OWNER TO postgres;

ALTER TABLE customer
    OWNER TO postgres;

ALTER TABLE customer_device
    OWNER TO postgres;

-- GENERATE DATA

TRUNCATE TABLE customer CASCADE;
TRUNCATE TABLE role CASCADE;

-- Generate roles
DO $$
    DECLARE
        freeId      BIGINT := 0;
        freeLevel   INT    := 0;
        levelsCount INT    := 4;
        roleNames   TEXT[] := '{"Admin", "Neighbour", "Observer", "Weak observer"}';
    BEGIN

        WHILE freeLevel < levelsCount
            LOOP
                INSERT INTO role (id, name, access_level)
                VALUES (freeId, (select roleNames[freeLevel + 1]), freeLevel);
                freeLevel = freeLevel + 1;
                freeId = freeId + 1;
            END LOOP;
        alter sequence role_id_seq restart with 4; -- levels count

    END $$;

-- Generate customers for test
DO $$
    DECLARE
        freeId              BIGINT       := 0;
        templateEmail       VARCHAR(255) := 'email%s@domain.com';
        templateName        VARCHAR(255) := '%s%s';
        templatePassword    VARCHAR(255) := 'Test Password%s';
        templatePhoneNumber VARCHAR(255) := '+38011111111%s';
        currRoleId          INT          := 0;
        roleCount           INT          := 4;
        customersCount      INT          := 10;
        phoneLastNumber     INT;
        roleNames           TEXT[]       := '{"Admin", "Neighbour", "Observer", "Weak observer"}';
    BEGIN

        WHILE freeId < customersCount
            LOOP
                phoneLastNumber = (freeId % 10);
                INSERT INTO customer (id, email, name, password, phone_number, role_id)
                VALUES (freeId,
                        (SELECT FORMAT(templateEmail, freeId)),
                        (SELECT FORMAT(templateName, (select roleNames[currRoleId + 1]), freeId)),
                        (SELECT FORMAT(templatePassword, freeId)),
                        (SELECT FORMAT(templatePhoneNumber, phoneLastNumber)),
                        currRoleId);
                currRoleId = (currRoleId + 1) % roleCount;
                freeId = freeId + 1;
            END LOOP;
        alter sequence customer_id_seq restart with 10; -- customers count

    END $$;
