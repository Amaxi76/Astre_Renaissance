/*
	@author Alizéa LEBARON, Clémentin LI, Matéo SA, Maximilien LESTERLIN, Maxime LEMOINE
	@version 1.1.0 - 06/12/2023 
	@date 06/12/2023 
	@description Script de création de la base de données
*/

/* ---------------------------------------------------- */
/*          Suppression des tables existantes           */
/* ---------------------------------------------------- */

DROP TABLE Semestre    CASCADE;
DROP TABLE Contrat     CASCADE;
DROP TABLE Heure       CASCADE;
DROP TABLE TypeModule  CASCADE;
DROP TABLE ModuleIUT   CASCADE;
DROP TABLE Intervenant CASCADE;
DROP TABLE Enseigne    CASCADE;
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


CREATE TABLE TypeModule
(
   Id_TypeModule SERIAL,
   nom           VARCHAR(60),

   PRIMARY KEY(Id_TypeModule)
);

CREATE TABLE ModuleIUT
(
   Id_ModuleIUT VARCHAR(5),
   libLong      VARCHAR(60),
   libCourt     VARCHAR(15),
   Id_Semestre  INTEGER    NOT NULL,
   
   PRIMARY KEY(Id_ModuleIUT),
   FOREIGN KEY(Id_Semestre) REFERENCES Semestre(Id_Semestre)
);

CREATE TABLE Intervenant
(
   Id_Intervenant SERIAL,
   nomInter       VARCHAR(50)  NOT NULL,
   prenom         VARCHAR(50)  NOT NULL,
   categorie      VARCHAR(50)  NOT NULL,
   coeffTP        DOUBLE PRECISION,
   hService       INTEGER      NOT NULL,
   hMax           INTEGER      NOT NULL,
   Id_Contrat     INTEGER      NOT NULL,

   PRIMARY KEY(Id_Intervenant),
   FOREIGN KEY(Id_Contrat) REFERENCES Contrat(Id_Contrat)
);

CREATE TABLE Enseigne
(
   Id_Intervenant INTEGER,
   Id_ModuleIUT   VARCHAR(5),  

   PRIMARY KEY(Id_Intervenant, Id_ModuleIUT),
   FOREIGN KEY(Id_Intervenant) REFERENCES Intervenant(Id_Intervenant),
   FOREIGN KEY(Id_ModuleIUT) REFERENCES ModuleIUT(Id_ModuleIUT)
);

CREATE TABLE Horaire
(
   nomHeure      VARCHAR(50),
   Id_TypeModule INTEGER,
   Id_ModuleIUT  VARCHAR(5) ,
   nbHeurePN     INTEGER,
   nbHeure       INTEGER,
   nbSemaine     VARCHAR(50) ,

   PRIMARY KEY(nomHeure, Id_TypeModule, Id_ModuleIUT),
   FOREIGN KEY(nomHeure) REFERENCES Heure(nomHeure),
   FOREIGN KEY(Id_TypeModule) REFERENCES TypeModule(Id_TypeModule),
   FOREIGN KEY(Id_ModuleIUT) REFERENCES ModuleIUT(Id_ModuleIUT)
);
