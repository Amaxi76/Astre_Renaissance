/**
 * Permet de créer les fonctions (insert) d'ASTRE
 * @author Maxime LEMOINE
 * @version 2.0.0 - 26/01/2024
 * @date 26/01/2024
 */


--Permet de récupérer les doubles en string
DROP FUNCTION IF EXISTS astre.f_conversion ( sIdContrat INTEGER ) CASCADE;
CREATE OR REPLACE FUNCTION astre.f_conversion ( sIdContrat INTEGER ) RETURNS VARCHAR AS
$$
DECLARE
	fraction VARCHAR;
BEGIN

	SELECT
	TRIM(BOTH '0' FROM
		CONCAT(
		CASE WHEN coefTP < 0 THEN '-' ELSE '' END,
		FLOOR(ABS(coefTP))::TEXT,
		' ',
		CASE 
			WHEN ABS(coefTP)::numeric % 0.66 = 0 THEN '2/3'
			ELSE FLOOR((ABS(coefTP) - FLOOR(ABS(coefTP))) * 100)::TEXT
		END
		)
	) INTO fraction
	FROM astre.Contrat
	WHERE idContrat = sIdContrat;

	-- Retourner le résultat et si la requête est nulle, on renvoie 0
	RETURN COALESCE(fraction, '0');
END;
$$ LANGUAGE plpgsql;
