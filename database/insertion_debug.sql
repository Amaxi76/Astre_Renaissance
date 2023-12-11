/*
	@author Alizéa LEBARON
	@version 1.0 - 11/12/2023 
	@date 11/12/2023 
	@description Script d'insertion pour tester notre base'
*/

DELETE FROM Intervenant CASCADE;
DELETE FROM Contrat     CASCADE;

INSERT INTO Contrat (nomContrat, hServiceContrat, hMaxContrat, ratioTP) VALUES
('Enseignant 2nd degrès', 250, 360, 1.0  ),
('Enseignant chercheur' , 25 , 389, 0.66 ),
('Contractuel'          , 85 , 125, 0.66 );

INSERT INTO Intervenant (nomInter, prenom, hService, hMax, Id_Contrat) VALUES
('De la Fontaine', 'Jean'   , 250, 360, 1 ),
('Orwell'        , 'Georges',  25, 389, 2 ),
('Lovecraft'     , 'Howard' ,  85, 125, 2 );

