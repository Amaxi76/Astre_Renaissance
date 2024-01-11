/*
	@author Alizéa LEBARON
	@version 1.0 - 13/12/2023 
	@date 11/12/2023 
	@description Script d'insertion pour tester notre base
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
(1,  5, 6, 85, 10),
(2,  5, 6, 66, 20),
(3,  8, 6, 58, 30),
(4,  8, 6, 45, 40),
(5, 10, 6, 25, 50),
(6, 10, 6, 10, 60);

INSERT INTO Heure ( nomHeure, coeffTD ) VALUES
('TP' , 1   ),
('TD' , 1   ),
('CM' , 1.5 ),
('REH', 1   ),
('SAE', 1   ),
('HP' , 1   ),
('Tut', 1   );