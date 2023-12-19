/*
	@author Alizéa LEBARON
	@version 1.1.0 - 12/12/2023 
	@date 06/12/2023 
	@description Script de création de la base de données
*/

/* ---------------------------------------------------- */
/*          Suppression des tables existantes           */
/* ---------------------------------------------------- */ 

DROP TABLE Semestre    CASCADE;
DROP TABLE Contrat     CASCADE;
DROP TABLE Heure       CASCADE;
DROP TABLE ModuleIUT   CASCADE;
DROP TABLE Intervenant CASCADE;
DROP TABLE Intervient  CASCADE;
DROP TABLE Horaire     CASCADE;
DROP TABLE Historique  CASCADE;

/* ---------------------------------------------------- */
/*                  Création des tables                 */
/* ---------------------------------------------------- */

CREATE TABLE Semestre
(
   Id_Semestre SERIAL,
   nbGroupeTP  INTEGER NOT NULL,
   nbGroupeTD  INTEGER NOT NULL,
   nbEtud      INTEGER,
   nbSemaine   INTEGER,

   PRIMARY KEY(Id_Semestre)
);

CREATE TABLE Contrat
( 
   Id_Contrat      SERIAL,
   nomContrat      VARCHAR(50)  NOT NULL,
   hServiceContrat INTEGER,
   hMaxContrat     INTEGER,
   ratioTP         DOUBLE PRECISION,

   PRIMARY KEY(Id_Contrat)
);

CREATE TABLE Heure
(
   Id_Heure SERIAL,
   nomHeure VARCHAR(50),
   coeffTD  DOUBLE PRECISION,

   PRIMARY KEY(Id_Heure)
);

CREATE TABLE ModuleIUT
(
   Code_ModuleIUT  VARCHAR(5),
   libLong         VARCHAR(60),
   libCourt        VARCHAR(15),
   typeModule      VARCHAR(20),
   valide          BOOLEAN,
   Id_Semestre     INTEGER NOT NULL,

   PRIMARY KEY(Code_ModuleIUT),
   FOREIGN KEY(Id_Semestre) REFERENCES Semestre(Id_Semestre)
);


CREATE TABLE Intervenant
(
   Id_Intervenant SERIAL,
   nom            VARCHAR(50)  NOT NULL,
   prenom         VARCHAR(50)  NOT NULL,
   hService       INTEGER      NOT NULL,
   hMax           INTEGER      NOT NULL,
   Id_Contrat     INTEGER      NOT NULL,

   PRIMARY KEY(Id_Intervenant),
   FOREIGN KEY(Id_Contrat) REFERENCES Contrat(Id_Contrat)
);

CREATE TABLE Intervient
(
   Id_Intervenant INTEGER,
   Id_Heure       INTEGER ,
   Code_ModuleIUT VARCHAR(5) ,
   nbSemaine      INTEGER,
   nbGroupe       INTEGER,
   nbHeure        INTEGER,
   commentaire    VARCHAR(50) ,

   PRIMARY KEY(Id_Intervenant, Id_Heure, Code_ModuleIUT),
   FOREIGN KEY(Id_Intervenant) REFERENCES Intervenant(Id_Intervenant),
   FOREIGN KEY(Id_Heure)       REFERENCES Heure(Id_Heure)            ,
   FOREIGN KEY(Code_ModuleIUT) REFERENCES ModuleIUT(Code_ModuleIUT)  
);


CREATE TABLE Horaire
( 
   Id_Heure             INTEGER,
   Code_ModuleIUT       VARCHAR(5),
   nbHeurePN            INTEGER,
   nbHeureRepartie      INTEGER,
   nbSemaine            INTEGER,
   
   PRIMARY KEY(Id_Heure, Code_ModuleIUT),
   FOREIGN KEY(Id_Heure)       REFERENCES Heure(Id_Heure)           ,
   FOREIGN KEY(Code_ModuleIUT) REFERENCES ModuleIUT(Code_ModuleIUT) 
);

CREATE TABLE Historique
(
   Id_Historique      SERIAL,
   dateModification   TIMESTAMP     NOT NULL,
   commentaire        VARCHAR(150)  NOT NULL,

   PRIMARY KEY(Id_Historique)
);

/* Création des trigger */

-- Pour les intervenants
CREATE TRIGGER tr_update_Intervenant
AFTER INSERT OR UPDATE OR DELETE
ON Intervenant
FOR EACH ROW
EXECUTE FUNCTION f_update_historique_Intervenant();

-- Pour les Semestre
CREATE TRIGGER tr_update_Semestre
AFTER INSERT OR UPDATE OR DELETE
ON Semestre
FOR EACH ROW
EXECUTE FUNCTION f_update_historique_Semestre();

-- Pour les Contrat
CREATE TRIGGER tr_update_Contrat
AFTER INSERT OR UPDATE OR DELETE
ON Contrat
FOR EACH ROW
EXECUTE FUNCTION f_update_historique_Contrat();

-- Pour les heures
CREATE TRIGGER tr_update_Heure
AFTER INSERT OR UPDATE OR DELETE
ON Heure
FOR EACH ROW
EXECUTE FUNCTION f_update_historique_Heure();

-- Pour les modules
CREATE TRIGGER tr_update_ModuleIUT
AFTER INSERT OR UPDATE OR DELETE
ON ModuleIUT
FOR EACH ROW
EXECUTE FUNCTION f_update_historique_ModuleIUT();

-- Pour intervient
CREATE TRIGGER tr_update_Intervient
AFTER INSERT OR UPDATE OR DELETE
ON Intervient
FOR EACH ROW
EXECUTE FUNCTION f_update_historique_Intervient();

-- Pour horaire
CREATE TRIGGER tr_update_Horaire
AFTER INSERT OR UPDATE OR DELETE
ON Horaire
FOR EACH ROW
EXECUTE FUNCTION f_update_historique_Horaire();