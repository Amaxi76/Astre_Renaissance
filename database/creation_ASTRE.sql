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
   FOREIGN KEY(Id_Semestre) REFERENCES Semestre(Id_Semestre) ON DELETE CASCADE
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
   FOREIGN KEY(Id_Contrat) REFERENCES Contrat(Id_Contrat) ON DELETE CASCADE
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
   FOREIGN KEY(Id_Intervenant) REFERENCES Intervenant(Id_Intervenant) ON DELETE CASCADE,
   FOREIGN KEY(Id_Heure)       REFERENCES Heure(Id_Heure)             ON DELETE CASCADE,
   FOREIGN KEY(Code_ModuleIUT) REFERENCES ModuleIUT(Code_ModuleIUT)   ON DELETE CASCADE
);


CREATE TABLE Horaire
( 
   Id_Heure             INTEGER,
   Code_ModuleIUT       VARCHAR(5),
   nbHeurePN            INTEGER,
   nbHeureRepartie      INTEGER,
   nbSemaine            INTEGER,
   
   PRIMARY KEY(Id_Heure, Code_ModuleIUT),
   FOREIGN KEY(Id_Heure)       REFERENCES Heure(Id_Heure)           ON DELETE CASCADE,
   FOREIGN KEY(Code_ModuleIUT) REFERENCES ModuleIUT(Code_ModuleIUT) ON DELETE CASCADE
);

