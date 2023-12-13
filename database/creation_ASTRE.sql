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
   nomHeure VARCHAR(50),
   coeffTD  DOUBLE PRECISION,

   PRIMARY KEY(nomHeure)
);

CREATE TABLE ModuleIUT
(
   Code_ModuleIUT  VARCHAR(5),
   libLong         VARCHAR(60),
   libCourt        VARCHAR(15),
   typeModule      VARCHAR(20),
   Id_Semestre     INTEGER NOT NULL,

   PRIMARY KEY(Code_ModuleIUT),
   FOREIGN KEY(Id_Semestre) REFERENCES Semestre(Id_Semestre)
);


CREATE TABLE Intervenant
(
   Id_Intervenant SERIAL,
   nomInter       VARCHAR(50)  NOT NULL,
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
   nomHeure       VARCHAR(50) ,
   Code_ModuleIUT   VARCHAR(5) ,
   nbSemaine      INTEGER,
   nbGroupe       INTEGER,
   nbHeure        INTEGER,
   commentaire    VARCHAR(50) ,
   PRIMARY KEY(Id_Intervenant, nomHeure, Code_ModuleIUT),
   FOREIGN KEY(Id_Intervenant) REFERENCES Intervenant(Id_Intervenant),
   FOREIGN KEY(nomHeure) REFERENCES Heure(nomHeure),
   FOREIGN KEY(Code_ModuleIUT) REFERENCES ModuleIUT(Code_ModuleIUT)
);


CREATE TABLE Horaire
( 
   nomHeure             VARCHAR(50),
   Code_ModuleIUT       VARCHAR(5),
   nbHeurePN            INTEGER,
   nbHeureRepartie      INTEGER,
   nbSemaine            INTEGER,
   
   PRIMARY KEY(nomHeure, Code_ModuleIUT),
   FOREIGN KEY(nomHeure) REFERENCES Heure(nomHeure),
   FOREIGN KEY(Code_ModuleIUT) REFERENCES ModuleIUT(Code_ModuleIUT)
);

