/*
	@author Alizéa LEBARON, Maxime LEMOINE
	@version 1.0.0 - 14/01/2024
	@date 11/12/2023
	@description Script permettant d'insérer les données essentielles à une premiere utilisation de l'application
*/

INSERT INTO Semestre VALUES 
( 1, 0, 0, 0, 0 ),
( 2, 0, 0, 0, 0 ),
( 3, 0, 0, 0, 0 ),
( 4, 0, 0, 0, 0 ),
( 5, 0, 0, 0, 0 ),
( 6, 0, 0, 0, 0 );

INSERT INTO Heure ( nomHeure, coeffTD ) VALUES
( 'TP' , 1   ),
( 'TD' , 1   ),
( 'CM' , 1.5 ),
( 'REH', 1   ),
( 'SAE', 1   ),
( 'HP' , 1   ),
( 'Tut', 1   );