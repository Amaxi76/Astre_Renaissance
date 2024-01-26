/**
 * Permet de créer les fonctions (insert) d'ASTRE
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


-- Insérer un contrat
DROP FUNCTION IF EXISTS f_insertContrat ( i_nomContrat VARCHAR(50), i_hServiceContrat INTEGER, i_hMaxContrat INTEGER, i_ratioTP DOUBLE PRECISION );
CREATE OR REPLACE FUNCTION f_insertContrat ( i_nomContrat VARCHAR(50), i_hServiceContrat INTEGER, i_hMaxContrat INTEGER, i_ratioTP DOUBLE PRECISION ) RETURNS VOID AS
$$
BEGIN

	INSERT INTO Contrat ( nomContrat, hServiceContrat, hMaxContrat, ratioTP ) VALUES ( i_nomContrat, i_hServiceContrat, i_hMaxContrat, i_ratioTP );

END;
$$ LANGUAGE plpgsql;


-- Insérer une heure
DROP FUNCTION IF EXISTS f_insertHeure ( i_nomHeure VARCHAR(50), i_coeffTD DOUBLE PRECISION );
CREATE OR REPLACE FUNCTION f_insertHeure ( i_nomHeure VARCHAR(50), i_coeffTD DOUBLE PRECISION ) RETURNS VOID AS
$$
BEGIN

	INSERT INTO Heure ( nomHeure, coeffTD ) VALUES ( i_nomHeure, i_coeffTD );

END;
$$ LANGUAGE plpgsql;


-- Insérer un Module
DROP FUNCTION IF EXISTS f_insertModule ( i_Code_ModuleIUT VARCHAR(5), i_libLong VARCHAR(60),  i_libCourt VARCHAR(15), i_typeModule VARCHAR(20), i_Id_Semestre INTEGER);
CREATE OR REPLACE FUNCTION f_insertModule ( i_Code_ModuleIUT VARCHAR(5), i_libLong VARCHAR(60),  i_libCourt VARCHAR(15), i_typeModule VARCHAR(20), i_Id_Semestre INTEGER ) RETURNS VOID AS
$$
BEGIN

	INSERT INTO Module (   Code_ModuleIUT,   libLong,   libCourt,   typeModule,   Id_Semestre ) 
	VALUES             ( i_Code_ModuleIUT, i_libLong, i_libCourt, i_typeModule, i_Id_Semestre );

END;
$$ LANGUAGE plpgsql;


-- Insérer un Intervenanti_inte
DROP FUNCTION IF EXISTS f_insertIntervenant ( i_nom VARCHAR(50), i_prenom VARCHAR(50), i_hService INTEGER, i_hMax INTEGER, i_Id_Contrat INTEGER );
CREATE OR REPLACE FUNCTION f_insertIntervenant ( i_nom VARCHAR(50), i_prenom VARCHAR(50), i_hService INTEGER, i_hMax INTEGER, i_Id_Contrat INTEGER ) RETURNS VOID AS
$$
BEGIN

	INSERT INTO Intervenant (   nom ,   prenom,   hService,   hMax,   Id_Contrat ) 
	VALUES                  ( i_nom , i_prenom, i_hService, i_hMax, i_Id_Contrat );

END;
$$ LANGUAGE plpgsql;


-- Insérer dans intervient
DROP FUNCTION IF EXISTS f_insertIntervient ( i_Id_Intervenant INTEGER, i_Id_Heure INTEGER, i_Code_ModuleIUT VARCHAR(5), i_nbSemaine INTEGER, i_nbGroupe INTEGER, i_nbHeure INTEGER, i_commentaire VARCHAR(50));
CREATE OR REPLACE FUNCTION f_insertIntervient ( i_Id_Intervenant INTEGER, i_Id_Heure INTEGER, i_Code_ModuleIUT VARCHAR(5), i_nbSemaine INTEGER, i_nbGroupe INTEGER, i_nbHeure INTEGER, i_commentaire VARCHAR(50)) RETURNS VOID AS
$$
BEGIN

	INSERT INTO Intervient  (   Id_Intervenant,   Id_Heure,   Code_ModuleIUT,   nbSemaine,   nbGroupe,   nbHeure,   commentaire,   nbHeure,   commentaire ) 
	VALUES                  ( i_Id_Intervenant, i_Id_Heure, i_Code_ModuleIUT, i_nbSemaine, i_nbGroupe, i_nbHeure, i_commentaire, i_nbHeure, i_commentaire );

END;
$$ LANGUAGE plpgsql;


-- Insérer dans horaire
DROP FUNCTION IF EXISTS f_insertHoraire ( i_Id_Heure INTEGER, i_Code_ModuleIUT VARCHAR(5), i_nbHeurePN INTEGER, i_nbHeureRepartie INTEGER, i_nbSemaine VARCHAR(50));
CREATE OR REPLACE FUNCTION f_insertHoraire ( i_Id_Heure INTEGER, i_Code_ModuleIUT VARCHAR(5), i_nbHeurePN INTEGER, i_nbHeureRepartie INTEGER, i_nbSemaine VARCHAR(50)) RETURNS VOID AS
$$
BEGIN

	INSERT INTO Horaire  (   Id_Heure ,   Code_ModuleIUT,   nbHeurePN ,   nbHeureRepartie ,   nbSemaine ) 
	VALUES               ( i_Id_Heure , i_Code_ModuleIUT, i_nbHeurePN , i_nbHeureRepartie , i_nbSemaine );

END;
$$ LANGUAGE plpgsql;