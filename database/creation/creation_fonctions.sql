/**
 * Permet de créer les fonctions d'ASTRE
 * @author Maxime LEMOINE
 * @version 2.0.0 - 14/01/2024
 * @date 12/12/2023 
 */

-- fonction liée au trigger pour la gestion de l'historique
--TODO: améliorer l'affichage
CREATE OR REPLACE FUNCTION f_update_Historique ( )
RETURNS TRIGGER AS
$$
BEGIN
	IF TG_OP = 'INSERT' THEN
		INSERT INTO Historique ( commentaire )
		VALUES ( 'Création de ' || TG_TABLE_NAME || ' : ' || to_jsonb ( NEW ) );

	ELSIF TG_OP = 'UPDATE' THEN
		INSERT INTO Historique ( commentaire )
		VALUES ( 'Mise à jour de ' || TG_TABLE_NAME || ' : ' || to_jsonb ( OLD ) || ' -> ' || to_jsonb ( NEW ) );

	ELSIF TG_OP = 'DELETE' THEN
		INSERT INTO Historique ( commentaire )
		VALUES ( 'Suppression de ' || TG_TABLE_NAME || ' : ' || to_jsonb ( OLD ) );
	END IF;

	RETURN NEW;
END;
$$ LANGUAGE plpgsql;