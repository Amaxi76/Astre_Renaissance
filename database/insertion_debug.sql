/*
	@author Alizéa LEBARON
	@version 1.0 - 11/12/2023 
	@date 11/12/2023 
	@description Script d'insertion pour tester notre base'
*/

/* ---------------------------------------------------- */
/*          Suppression des tuples existantes           */
/* ---------------------------------------------------- */ 

DELETE FROM Semestre    CASCADE;
DELETE FROM Contrat     CASCADE;
DELETE FROM Heure       CASCADE;
DELETE FROM TypeModule  CASCADE;
DELETE FROM ModuleIUT   CASCADE;
DELETE FROM Intervenant CASCADE;
DELETE FROM Enseigne    CASCADE; 
DELETE FROM Horaire     CASCADE;

/* ---------------------------------------------------- */
/*                  Création des tuples                 */
/* ---------------------------------------------------- */

INSERT INTO Semestre VALUES
(1,  5, 6, 85, 10),
(2,  5, 6, 66, 20),
(3,  8, 6, 58, 30),
(4,  8, 6, 45, 40),
(5, 10, 6, 25, 50),
(6, 10, 6, 10, 60);

INSERT INTO Contrat (nomContrat, hServiceContrat, hMaxContrat, ratioTP) VALUES
('Enseignant 2nd degrès', 250, 360, 1.0  ),
('Enseignant chercheur' , 25 , 389, 0.66 ),
('Contractuel'          , 85 , 125, 0.66 );

INSERT INTO Intervenant (nomInter, prenom, hService, hMax, Id_Contrat) VALUES
('De la Fontaine', 'Jean'   , 250, 360, 1 ),
('Orwell'        , 'Georges',  25, 389, 2 ),
('Lovecraft'     , 'Howard' ,  85, 125, 2 );