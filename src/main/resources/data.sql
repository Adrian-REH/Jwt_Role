-- Crear el esquema "security"
CREATE SCHEMA "security" AUTHORIZATION postgres;

-- Crear la tabla "ob_role" dentro del esquema "security"
CREATE TABLE "security".ob_role (
  id VARCHAR(50) PRIMARY KEY,
  description VARCHAR(255)
);

-- Insertar datos en la tabla "ob_role"
INSERT INTO "security".ob_role (description, id) VALUES ('Admin role', 'ADMIN');
INSERT INTO "security".ob_role (description, id) VALUES ('Atm role', 'ATM');
INSERT INTO "security".ob_role (description, id) VALUES ('Super admin role', 'SUPADMIN');
INSERT INTO "security".ob_role (description, id) VALUES ('View role', 'VIEW');

-- Crear la tabla "ob_user" dentro del esquema "security"
CREATE TABLE "security".ob_user (
  username VARCHAR(50) PRIMARY KEY,
  nombre VARCHAR(255),
  apellido VARCHAR(255),
  email VARCHAR(255),
  password VARCHAR(255),
  role_id VARCHAR(255),
    CONSTRAINT fk_user_role FOREIGN KEY (role_id) REFERENCES "security".ob_role (id)

);

-- Insertar datos en la tabla "ob_user"
INSERT INTO "security".ob_user (username, nombre, apellido, email, password, role_id) VALUES ('adrian', 'adrian elias', 'Herrera', 'adrian@herrera.com',  'asdasddd','VIEW');
