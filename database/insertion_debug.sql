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

INSERT INTO Contrat (nomContrat, hServiceContrat, hMaxContrat, ratioTP) VALUES
('Enseignant 2nd degrès', 250, 360, 1.0  ),
('Enseignant chercheur' , 25 , 389, 0.66 ),
('Contractuel'          , 85 , 125, 0.66 );

INSERT INTO ModuleIUT VALUES
('R1.01', 'Initiation Développement'    , 'Init_Dev'     , 'Ressource', 1),
('R1.02', 'Développement interfaces Web', 'Dev_Web'      , 'Ressource', 1),
('S2.05', 'Gestion dun projet'          , 'Gestion_proj' , 'SAE'      , 2),
('R3.05', 'Programmation Système'       , 'prog_sys'     , 'Ressource', 3),
('S4.ST', 'Stages'                      , 'stages'       , 'Stage'    , 4),
('R5.03', 'Politique de communication'  , 'comm'         , 'Ressource', 5),
('R5.06', 'Programmation multimédia'    , 'prog_media'   , 'Ressource', 5),
('S5.01', 'Développement avancé'        , 'dev_avancé'   , 'SAE'      , 5),
('S6.01', 'évolution dune application'  , 'ev_appli'     , 'SAE'      , 6),
('S6.ST', 'Stages'                      , 'stages'       , 'Stage'    , 6);

INSERT INTO Intervenant (nom, prenom, hService, hMax, Id_Contrat) VALUES
('De la Fontaine', 'Jean'   , 250, 360, 1 ),
('Orwell'        , 'Georges',  25, 389, 2 ),
('Lovecraft'     , 'Howard' ,  85, 125, 2 );

INSERT INTO Heure VALUES
('TP' , 1   ),
('TD' , 1   ),
('CM' , 1.33),
('REH', 1   ),
('SAE', 1   ),
('Tut', 1   );

INSERT INTO Intervient (Id_Intervenant, nomHeure, Code_ModuleIUT, nbSemaine, nbGroupe, nbHeure, commentaire) VALUES
(1,  'TD', 'R5.03',    6,    2,    5, NULL         ),
(1,  'TP', 'R1.01',    8,    1,    6, NULL         ),
(2,  'TP', 'R1.01',    8,    1,    9, 'commentaire'),
(3, 'Tut', 'S2.05', NULL, NULL,    9, NULL         ),
(3, 'REH', 'S6.ST', NULL, NULL,   12, NULL         );
  
INSERT INTO Horaire VALUES
('TP' , 'R1.01', 85, 2, 12),
('TD' , 'R1.01', 30, 1, 11),
('CM' , 'R1.01',  5, 2,  5),
('SAE', 'S5.01', 60, 3,  3),
('REH', 'S2.05',  2, 2,  1),
('Tut', 'S6.ST',  6, 4,  3);




