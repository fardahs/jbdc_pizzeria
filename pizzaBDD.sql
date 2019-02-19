-- Creation de database pizza
-- CREATE DATABASE pizzaBDD;

-- Selection de la database
USE pizzaBDD;

-- Creation de la table pizza
CREATE TABLE pizza (
ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
CODE CHAR(5),
LIBELLE VARCHAR(45),
PRIX DOUBLE);
