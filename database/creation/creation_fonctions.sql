/**
 * Permet de créer les fonctions d'ASTRE
 * @author Maxime LEMOINE
 * @version 2.0.0 - 14/01/2024
 * @date 12/12/2023 
 * @warning : FIXME: à compléter
 */

-- fonction liée au trigger pour la gestion de l'historique
--TODO: améliorer l'affichage
CREATE OR REPLACE FUNCTION astre.f_update_Historique ( )
RETURNS TRIGGER AS
$$
DECLARE
	commentaire TEXT;
BEGIN
	IF TG_OP = 'INSERT' THEN
		commentaire := 'Création de ' || TG_TABLE_NAME || ' : ' || to_jsonb ( NEW );
	ELSIF TG_OP = 'UPDATE' THEN
		commentaire := 'Mise à jour de ' || TG_TABLE_NAME || ' : ' || to_jsonb ( OLD ) || ' -> ' || to_jsonb ( NEW );
	ELSIF TG_OP = 'DELETE' THEN
		commentaire := 'Suppression de ' || TG_TABLE_NAME || ' : ' || to_jsonb ( OLD );
	END IF;

	INSERT INTO astre.Historique ( commentaire ) VALUES ( commentaire );

	RETURN NEW;
END;
$$ LANGUAGE plpgsql;