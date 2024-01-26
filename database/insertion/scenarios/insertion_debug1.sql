/*
	@author Alizéa LEBARON, Maxime LEMOINE
	@version 2.0 - 26/01/2024
	@date 11/12/2023 
	@description Script d'insertion pour tester notre base
*/

UPDATE Annee SET Actuelle = false;

INSERT INTO Annee VALUES
('Test', true);

INSERT INTO Semestre VALUES ( idSemestre, nbGroupesTP, nbGroupesTD, nbEtudiants, nbSemaines )
(1,  5, 6, 85, 10),
(2,  5, 6, 66, 20),
(3,  8, 6, 58, 30),
(4,  8, 6, 45, 40),
(5, 10, 6, 25, 50),
(6, 10, 6, 10, 60);

INSERT INTO Contrat ( nom, nbHeuresService, maxHeuresService, coefTP ) VALUES 
('Enseignant 2nd degrès', 250, 360, 1.0  ),
('Enseignant chercheur' , 25 , 389, 0.66 ),
('Contractuel'          , 85 , 125, 0.66 );

INSERT INTO ModuleIUT VALUES ( idModuleIUT, code, libLong, libCourt, typeModule, valide, idSemestre, totalHeuresPN, totalHeuresAffectees )
('R1.01', 'Initiation Développement'    , 'Init_Dev'     , 'Ressource', false, 1, 0, 0),
('R1.02', 'Développement interfaces Web', 'Dev_Web'      , 'Ressource', true , 1, 0, 0),
('S2.05', 'Gestion dun projet'          , 'Gestion_proj' , 'SAE'      , false, 2, 0, 0),
('R3.05', 'Programmation Système'       , 'prog_sys'     , 'Ressource', false, 3, 0, 0),
('S4.ST', 'Stages'                      , 'stages'       , 'Stage'    , false, 4, 0, 0),
('R5.03', 'Politique de communication'  , 'comm'         , 'Ressource', false, 5, 0, 0),
('R5.06', 'Programmation multimédia'    , 'prog_media'   , 'Ressource', false, 5, 0, 0),
('S5.01', 'Développement avancé'        , 'dev_avancé'   , 'SAE'      , false, 5, 0, 0),
('S6.01', 'évolution dune application'  , 'ev_appli'     , 'SAE'      , false, 6, 0, 0),
('S6.ST', 'Stages'                      , 'stages'       , 'Stage'    , false, 6, 0, 0);

INSERT INTO Intervenant ( nom, prenom, nbHeuresService, maxHeuresService, idContrat ) VALUES 
('De la Fontaine', 'Jean'   , 250, 360, 1 ),
('Orwell'        , 'Georges',  25, 389, 2 ),
('Lovecraft'     , 'Howard' ,  85, 125, 2 ),
('Maupassant'    , 'Guy'    ,   2,   4, 1 ),
('De Balzac'     , 'Honoré' ,  65,  89, 3 ),
('Lovelace'      , 'Ada'    , 102, 365, 3 ),
('Toriyama'      , 'Akira'  , 420, 478, 2 );

INSERT INTO Heure ( nom, coefEQTD ) VALUES 
('TP' , 1   ),
('TD' , 1   ),
('CM' , 1.5 ),
('REH', 1   ),
('SAE', 1   ),
('HP' , 1   ),
('Tut', 1   );

INSERT INTO Intervient ( idIntervenant, idHeure, idModuleIUT, nbSemaines, nbGroupes, nbHeures, commentaire ) VALUES
(1,  2,  6,    6,    2,    5, ''           ),
(1,  1,  1,    8,    1,    6, ''           ),
(1,  5,  9,    8,    1,    6, ''           ),
(1,  2,  6,    8,    1,    6, ''           ),
(4,  3,  6,    2,    1,    6, '3 CM 3H'    ),
(2,  1,  6,    8,    1,    9, 'commentaire'),
(3,  5,  3,    1,    1,    9, ''           ),
(6,  1,  7,   12,    2,    2, ''           ),
(3,  4, 10,    1,    1,   12, ''           ),
(1,  3,  6,    8,    1,    6, ''           );
  
INSERT INTO Horaire VALUES ( idHoraire, idModuleIUT, idSemestre, idIntervenant, idHeure, nbGroupes, nbHeures )
(1,  1,  85,  5, 12),
(2,  1,  30, 28, 11),
(3,  1,   5,  2,  5),
(6,  1,   0,  5,  1),
(5,  8,  60,  0,  3),
(6, 10,   6,  0,  3);