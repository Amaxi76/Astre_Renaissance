/**
 * Permet de créer les fonctions (delete) d'ASTRE
 * @author Maxime LEMOINE
 * @version 2.0.0 - 26/01/2024
 * @date 26/01/2024
 */



/*
CE QUI EST DESSOUS A ETE COPIE COLLE DEPUIS L'ANCIEN FICHIER
FIXME: ajouter le préfixe "astre." du schéma
FIXME: maj des paramètres (noms et nombre)
FIXME: vérifier l'utilité de chaque fonction
FIXME: factoriser ?
*/



-- Supprimer TOUTES les données
DROP FUNCTION IF EXISTS f_deleteAll ( );
CREATE OR REPLACE FUNCTION f_deleteAll ( ) RETURNS VOID AS 
$$
BEGIN

	DELETE FROM Intervient  CASCADE;
	DELETE FROM Horaire     CASCADE;
	DELETE FROM ModuleIUT   CASCADE;
	DELETE FROM Intervenant CASCADE;
	DELETE FROM Contrat     CASCADE;
	DELETE FROM Heure       CASCADE;

END;
$$ LANGUAGE plpgsql;


-- Supprimer un contrat

DROP FUNCTION IF EXISTS f_deleteContrat ( d_id_contrat INTEGER );
CREATE OR REPLACE FUNCTION f_deleteContrat ( d_id_contrat INTEGER ) RETURNS VOID AS 
$$
BEGIN

	DELETE FROM Contrat WHERE Id_Contrat = d_id_contrat;

END;
$$ LANGUAGE plpgsql;


-- Supprimer une heure

DROP FUNCTION IF EXISTS f_deleteHeure ( d_nomHeure VARCHAR(50) );
CREATE OR REPLACE FUNCTION f_deleteHeure ( d_nomHeure VARCHAR(50) ) RETURNS VOID AS
$$
BEGIN

	DELETE FROM Heure WHERE nomHeure = d_nomHeure;

END;
$$ LANGUAGE plpgsql;


-- Supprimer un ModuleIUT

DROP FUNCTION IF EXISTS f_deleteModuleIUT ( d_Code_ModuleIUT VARCHAR(5) );
CREATE OR REPLACE FUNCTION f_deleteModuleIUT ( d_Code_ModuleIUT VARCHAR(5) ) RETURNS VOID AS
$$
BEGIN

	DELETE FROM ModuleIUT WHERE Code_ModuleIUT = d_Code_ModuleIUT;

END;
$$ LANGUAGE plpgsql;


-- Supprimer un intervenant

DROP FUNCTION IF EXISTS f_deleteIntervenant ( d_Id_Intervenant INTEGER );
CREATE OR REPLACE FUNCTION f_deleteIntervenant ( d_Id_Intervenant INTEGER ) RETURNS VOID AS
$$
BEGIN

	DELETE FROM Intervenant WHERE Id_Intervenant = d_Id_Intervenant;

END;
$$ LANGUAGE plpgsql;


-- Supprimer dans intervient

DROP FUNCTION IF EXISTS f_deleteIntervient ( d_Id_Intervenant INTEGER, d_Id_Heure INTEGER, d_Code_ModuleIUT VARCHAR(5) );
CREATE OR REPLACE FUNCTION f_deleteIntervient ( d_Id_Intervenant INTEGER, d_Id_Heure INTEGER, d_Code_ModuleIUT VARCHAR(5) ) RETURNS VOID AS
$$
BEGIN

	DELETE FROM Intervient WHERE Id_Intervenant = d_Id_Intervenant AND Id_Heure = d_Id_Heure AND Code_ModuleIUT = d_Code_ModuleIUT;

END;
$$ LANGUAGE plpgsql;


-- Supprimer une année 

DROP FUNCTION IF EXISTS f_deleteIntervient ( );
CREATE OR REPLACE FUNCTION f_deleteIntervient ( ) RETURNS VOID AS
$$
BEGIN

	DELETE FROM Intervient;

END;
$$ LANGUAGE plpgsql;


-- Supprimer dans horaire

DROP FUNCTION IF EXISTS f_deleteHoraire ( d_Id_Heure INTEGER, d_Code_ModuleIUT VARCHAR(5) );
CREATE OR REPLACE FUNCTION f_deleteHoraire ( d_Id_Heure INTEGER, d_Code_ModuleIUT VARCHAR(5) ) RETURNS VOID AS
$$
BEGIN

	DELETE FROM Horaire WHERE Id_Heure = d_Id_Heure AND Code_ModuleIUT = d_Code_ModuleIUT;

END;
$$ LANGUAGE plpgsql;