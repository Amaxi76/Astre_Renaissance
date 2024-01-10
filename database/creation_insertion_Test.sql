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

/* Création de vue */

-- Vue intervenant
CREATE VIEW v_Intervenant AS
SELECT
      Id_Intervenant,
      nomContrat,
      nom,
      prenom,
      hService,
      hMax,
      f_conversion ( c.Id_Contrat ) AS ratioTP,
      f_selectNBHeureParSemestre ( 1, Id_Intervenant  ) AS s1,
      f_selectNBHeureParSemestre ( 3, Id_Intervenant  ) AS s3,
      f_selectNBHeureParSemestre ( 5, Id_Intervenant  ) AS s5,
      f_selectNBHeureParSemestreImpair(Id_Intervenant ) AS totImp,
      f_selectNBHeureParSemestre ( 2, Id_Intervenant  ) AS s2,
      f_selectNBHeureParSemestre ( 4, Id_Intervenant  ) AS s4,
      f_selectNBHeureParSemestre ( 6, Id_Intervenant  ) AS s6,
      f_selectNBHeureParSemestrePair(Id_Intervenant) AS totPai,
      f_selectNBHeureParSemestreTot(Id_Intervenant) AS total
FROM
      Intervenant i
      JOIN Contrat c ON i.Id_Contrat = c.Id_Contrat
ORDER BY
      Id_intervenant ASC;

-- Vue des modules
CREATE VIEW v_Module AS
SELECT id_semestre, Code_ModuleIUT, libLong, (f_selectTotHeureRep(Code_ModuleIUT) || ' / ' || f_selectTotHeurePN(Code_ModuleIUT)) AS Recap, valide
FROM   ModuleIUT;
/*
	@author Alizéa LEBARON
	@description Script d'insertion pour cette année
*/

/* ---------------------------------------------------- */
/*          Suppression des tuples existantes           */
/* ---------------------------------------------------- */ 

DELETE FROM Intervient  CASCADE; 
DELETE FROM Horaire     CASCADE;
DELETE FROM ModuleIUT   CASCADE;
DELETE FROM Semestre    CASCADE;
DELETE FROM Intervenant CASCADE;
DELETE FROM Contrat     CASCADE;
DELETE FROM Heure       CASCADE;

/* ---------------------------------------------------- */
/*                  Création des tuples                 */
/* ---------------------------------------------------- */

 INSERT INTO Semestre VALUES 
(1,5,6,85,10), 
(2,5,6,66,20), 
(3,8,6,58,30), 
(4,8,6,45,40), 
(5,10,6,25,50), 
(6,10,6,10,60); 

INSERT INTO Contrat (nomContrat, hServiceContrat, hMaxContrat, ratioTP) VALUES 
('Enseignant 2nd degrès',250,360,'1.0'), 
('Enseignant chercheur',25,389,'0.66'), 
('Contractuel',85,125,'0.66'); 

INSERT INTO Heure ( nomHeure, coeffTD ) VALUES 
('TP','1.0'), 
('TD','1.0'), 
('CM','1.5'), 
('REH','1.0'), 
('SAE','1.0'), 
('HP','1.0'), 
('Tut','1.0'); 

INSERT INTO ModuleIUT VALUES 
('R1.02','Développement interfaces Web','Dev_Web','Ressource',true,1), 
('S2.05','Gestion dun projet','Gestion_proj','SAE',false,2), 
('R3.05','Programmation Systéme','prog_sys','Ressource',false,3), 
('S4.ST','Stages','stages','Stage',false,4), 
('R5.03','Politique de communication','comm','Ressource',false,5), 
('R5.06','Programmation multimédia','prog_media','Ressource',false,5), 
('S5.01','Développement avancé','dev_avancé','SAE',false,5), 
('S6.01','évolution dune application','ev_appli','SAE',false,6), 
('S6.ST','Stages','stages','Stage',false,6), 
('R1.01','Initiation Développement','Init_Dev','Ressource',true,1); 

INSERT INTO Intervenant (nom, prenom, hService, hMax, Id_Contrat) VALUES 
('De la Fontaine','Jean',250,360,1), 
('Orwell','Georges',25,389,2), 
('Lovecraft','Howard',85,125,2), 
('Maupassant','Guy',2,4,1), 
('De Balzac','Honoré',65,89,3), 
('Lovelace','Ada',102,365,3), 
('Toriyama','Akira',420,478,2); 

INSERT INTO Intervient VALUES 
(1,2,'R5.03',6,2,5,'null'), 
(1,1,'R1.01',8,1,6,'null'), 
(1,5,'S6.01',8,1,6,'null'), 
(1,2,'R1.01',8,1,6,'null'), 
(4,3,'R1.01',2,1,6,'3 CM 3H'), 
(2,1,'R1.01',8,1,9,'commentaire'), 
(3,6,'S2.05',1,1,9,'null'), 
(6,1,'R5.06',12,2,2,'null'), 
(3,4,'S6.ST',1,1,12,'null'), 
(1,3,'R1.01',8,1,6,'null'); 

INSERT INTO Horaire VALUES 
(5,'S5.01',60,0,3), 
(4,'S2.05',2,0,1), 
(6,'S6.ST',6,0,3), 
(3,'R1.01',5,2,5), 
(2,'R1.01',30,28,11), 
(1,'R1.01',85,5,12); 

