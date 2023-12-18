/*
	@author Alizéa LEBARON / Maxime LEMOINE / Maximilien LESTERLIN
	@version 1.0.0 - 13/12/2023 
	@date 12/12/2023 
	@description Script de création des fonctions
*/

/* ------------------------------------------ */
/*                   SELECT                   */
/* ------------------------------------------ */

-- Sélection globale pour toutes les tables
DROP              FUNCTION f_selectAll ( table_name VARCHAR );
CREATE OR REPLACE FUNCTION f_selectAll ( table_name VARCHAR ) RETURNS TABLE ( result_row RECORD ) AS
$$
BEGIN

	RETURN QUERY EXECUTE 'SELECT * FROM ' || table_name;

END;
$$ LANGUAGE plpgsql;

-- Sélection de tous les modules d'un semestre
DROP              FUNCTION f_selectModuleParSemestre ( numSemestre INTEGER );
CREATE OR REPLACE FUNCTION f_selectModuleParSemestre ( numSemestre INTEGER ) RETURNS TABLE ( result_row RECORD ) AS
$$
BEGIN

	RETURN QUERY EXECUTE 'SELECT * FROM ModuleIUT WHERE Id_Semestre = ' || numSemestre;

END;
$$ LANGUAGE plpgsql;

-- Sélectionner un semestre en particulier
DROP              FUNCTION f_selectUnSemestre ( numSemestre INTEGER );
CREATE OR REPLACE FUNCTION f_selectUnSemestre ( numSemestre INTEGER ) RETURNS TABLE ( result_row RECORD ) AS
$$
BEGIN

	RETURN QUERY EXECUTE 'SELECT * FROM Semestre WHERE Id_Semestre = ' || numSemestre;

END;
$$ LANGUAGE plpgsql;

-- Sélectionner un contrat en particulier

DROP              FUNCTION f_selectUnContrat ( numContrat INTEGER );
CREATE OR REPLACE FUNCTION f_selectUnContrat ( numContrat INTEGER ) RETURNS TABLE ( result_row RECORD ) AS
$$
BEGIN

	RETURN QUERY EXECUTE 'SELECT * FROM Contrat WHERE Id_Semestre = ' || numContrat;

END;
$$ LANGUAGE plpgsql;

-- Selecion des ModulesIUT par semestres
DROP              FUNCTION f_selectModulesIUTParSemestre ( numSemestre INTEGER );
CREATE OR REPLACE FUNCTION f_selectModulesIUTParSemestre ( numSemestre INTEGER ) RETURNS TABLE ( code_moduleiut VARCHAR, liblong VARCHAR, libcourt VARCHAR, typemodule VARCHAR, valide BOOLEAN, id_semestre INTEGER, nbgroupetp INTEGER, nbgroupetd INTEGER, nbetud INTEGER, nbsemaine INTEGER ) AS
$$
BEGIN

	RETURN QUERY
		SELECT m.code_moduleiut, m.liblong, m.libcourt, m.typemodule, m.valide, s.id_semestre, s.nbgroupetp, s.nbgroupetd, s.nbetud, s.nbsemaine
		FROM   ModuleIUT m join Semestre s on m.id_semestre = s.id_semestre
		WHERE  m.id_semestre = $1;

END;
$$ LANGUAGE plpgsql;

-- Selecion des heures par rapport à un module
DROP              FUNCTION f_selectHoraireParModule ( code VARCHAR );
CREATE OR REPLACE FUNCTION f_selectHoraireParModule ( code VARCHAR ) RETURNS TABLE ( Id_heure INTEGER, nbHeurePn INTEGER, nbHeureRepartie INTEGER ) AS
$$
BEGIN

	RETURN QUERY
		SELECT he.Id_Heure, ho.nbHeurePn, ho.nbHeureRepartie
		FROM   Horaire ho JOIN Heure he    ON ho.Id_Heure       = he.Id_Heure 
		                  JOIN ModuleIUT m ON ho.Code_ModuleIUT = m.Code_ModuleIUT 
		WHERE ho.Code_ModuleIUT = $1;

END;
$$ LANGUAGE plpgsql;

-- Sélection du nombre d'heure pour un enseignant, un module et une heure précise
-- Utilisée dans la génération HTML des modules

DROP              FUNCTION f_selectNBHeureParModule ( s_code VARCHAR(5), s_Id_Intervenant INTEGER, s_Id_Heure INTEGER );
CREATE OR REPLACE FUNCTION f_selectNBHeureParModule ( s_code VARCHAR(5), s_Id_Intervenant INTEGER, s_Id_Heure INTEGER ) RETURNS INTEGER AS
$$
DECLARE
    v_result INTEGER;
BEGIN
   
    SELECT nbSemaine * nbGroupe * nbHeure
    INTO v_result
    FROM Intervient
    WHERE Code_ModuleIUT = s_code AND 
          Id_Intervenant = s_Id_Intervenant AND 
          Id_Heure = s_Id_Heure;

    -- Retourner le résultat et si la requête est nulle, on renvoie 0
    RETURN COALESCE(v_result, 0);
END;
$$ LANGUAGE plpgsql;

-- Sélection du nombre d'heure PN pour un module et une heure donnée
-- Utilisée dans la génération HTML des modules

DROP              FUNCTION f_selectNBHeurePNParModule ( s_code VARCHAR(5), s_Id_Heure INTEGER );
CREATE OR REPLACE FUNCTION f_selectNBHeurePNParModule ( s_code VARCHAR(5), s_Id_Heure INTEGER ) RETURNS INTEGER AS
$$
DECLARE
    v_result INTEGER;
BEGIN
   
    SELECT nbHeurePN
    INTO v_result
    FROM Horaire
    WHERE Code_ModuleIUT = s_code AND 
          Id_Heure = s_Id_Heure;

    -- Retourner le résultat et si la requête est nulle, on renvoie 0
    RETURN COALESCE(v_result, 0);
END;
$$ LANGUAGE plpgsql;

-- Sélection du nombre d'heure Répartie pour un module et une heure donnée
-- Utilisée dans la génération HTML des modules

DROP              FUNCTION f_selectNBHeureRepParModule ( s_code VARCHAR(5), s_Id_Heure INTEGER );
CREATE OR REPLACE FUNCTION f_selectNBHeureRepParModule ( s_code VARCHAR(5), s_Id_Heure INTEGER ) RETURNS INTEGER AS
$$
DECLARE
    v_result INTEGER;
BEGIN
   
    SELECT nbHeureRepartie
    INTO v_result
    FROM Horaire
    WHERE Code_ModuleIUT = s_code AND 
          Id_Heure = s_Id_Heure;

    -- Retourner le résultat et si la requête est nulle, on renvoie 0
    RETURN COALESCE(v_result, 0);
END;
$$ LANGUAGE plpgsql;

-- Sélection du nombre d'heure pour une enseignant et un semestre
-- Utilisé dans la génération de HTML Intervenant

DROP              FUNCTION f_selectNBHeureParSemestre ( s_Id_Semestre INTEGER, s_Id_Intervenant INTEGER );
CREATE OR REPLACE FUNCTION f_selectNBHeureParSemestre ( s_Id_Semestre INTEGER, s_Id_Intervenant INTEGER ) RETURNS INTEGER AS
$$
DECLARE
    totalHeures INTEGER;
BEGIN
    -- Calcul du nombre total d'heures pour l'intervenant dans le semestre donné
    SELECT  SUM (nbSemaine * nbGroupe * nbHeure) INTO totalHeures
    FROM    Intervient i JOIN ModuleIUT m ON i.Code_ModuleIUT = m.Code_ModuleIUT
    WHERE   Id_Intervenant = s_Id_Intervenant AND 
            Id_Semestre = s_Id_Semestre;

    -- Retourner le résultat et si la requête est nulle, on renvoie 0
    RETURN COALESCE(totalHeures, 0);
END;
$$ LANGUAGE plpgsql;

-- Sélection du nombre d'heure etqd
-- Utilisé dans l'affectation et la répartition

DROP              FUNCTION f_selectNBHeureEQTD ( s_code VARCHAR(5), s_nomHeure VARCHAR ( 50 ) );
CREATE OR REPLACE FUNCTION f_selectNBHeureEQTD ( s_code VARCHAR(5), s_nomHeure VARCHAR ( 50 ) ) RETURNS INTEGER AS
$$
DECLARE
    totalHeures INTEGER;
BEGIN
    -- Calcul du nombre total d'heures pour l'intervenant dans le semestre donné
    SELECT  SUM (nbSemaine * nbGroupe * coeffTD) INTO totalHeures
    FROM    Intervient i JOIN Heure h ON i.Id_Heure = h.Id_Heure
    WHERE   Code_ModuleIUT = s_code AND 
            nomHeure = s_nomHeure;

    -- Retourner le résultat et si la requête est nulle, on renvoie 0
    RETURN COALESCE(totalHeures, 0);
END;
$$ LANGUAGE plpgsql;

/* FONCTIONS NON UTILISÉES POUR LE MOMENT ? */

-- -- Sélectionner les heuresPN

-- DROP              FUNCTION f_selectNBHeurePN ( code VARCHAR(5) );
-- CREATE OR REPLACE FUNCTION f_selectNBHeurePN ( code VARCHAR(5) ) RETURNS TABLE ( result_row RECORD ) AS
-- $$
-- BEGIN

-- 	RETURN QUERY EXECUTE 'SELECT he.nomHeure, ho.nbHeurePN
-- 						  FROM Horaire ho JOIN Heure he ON ho.Id_Heure = he.Id_Heure
-- 										  JOIN ModuleIUT m ON ho.Code_ModuleIUT = m.Code_ModuleIUT
-- 						  WHERE ho.Code_ModuleIUT = $1'
-- 	USING p_code;

-- END;
-- $$ LANGUAGE plpgsql;

-- -- Sélectionner les heureRepartie

-- DROP              FUNCTION f_selectNBHeureRepartie ( code VARCHAR(5) );
-- CREATE OR REPLACE FUNCTION f_selectNBHeureRepartie ( code VARCHAR(5) ) RETURNS TABLE ( result_row RECORD ) AS
-- $$
-- BEGIN

-- 	RETURN QUERY EXECUTE 'SELECT he.nomHeure, ho.nbHeureRepartie
-- 						  FROM Horaire ho JOIN Heure he ON ho.Id_Heure = he.Id_Heure
-- 										  JOIN ModuleIUT m ON ho.Code_ModuleIUT = m.Code_ModuleIUT
-- 						  WHERE ho.Code_ModuleIUT = $1'
-- 	USING p_code;

-- END;
-- $$ LANGUAGE plpgsql;



/* ------------------------------------------ */
/*                   INSERT                   */
/* ------------------------------------------ */

-- Insérer un contrat

DROP              FUNCTION f_insertContrat ( i_nomContrat VARCHAR(50), i_hServiceContrat INTEGER, i_hMaxContrat INTEGER, i_ratioTP DOUBLE PRECISION );
CREATE OR REPLACE FUNCTION f_insertContrat ( i_nomContrat VARCHAR(50), i_hServiceContrat INTEGER, i_hMaxContrat INTEGER, i_ratioTP DOUBLE PRECISION ) RETURNS VOID AS
$$
BEGIN

	INSERT INTO Contrat ( nomContrat, hServiceContrat, hMaxContrat, ratioTP ) VALUES ( i_nomContrat, i_hServiceContrat, i_hMaxContrat, i_ratioTP );

END;
$$ LANGUAGE plpgsql;

-- Insérer une heure

DROP              FUNCTION f_insertHeure ( i_nomHeure VARCHAR(50), i_coeffTD DOUBLE PRECISION );
CREATE OR REPLACE FUNCTION f_insertHeure ( i_nomHeure VARCHAR(50), i_coeffTD DOUBLE PRECISION ) RETURNS VOID AS
$$
BEGIN

	INSERT INTO Heure ( nomHeure, coeffTD ) VALUES ( i_nomHeure, i_coeffTD );

END;
$$ LANGUAGE plpgsql;

-- Insérer un Module

DROP              FUNCTION f_insertModule ( i_Code_ModuleIUT VARCHAR(5), i_libLong VARCHAR(60),  i_libCourt VARCHAR(15), i_typeModule VARCHAR(20), i_Id_Semestre INTEGER);
CREATE OR REPLACE FUNCTION f_insertModule ( i_Code_ModuleIUT VARCHAR(5), i_libLong VARCHAR(60),  i_libCourt VARCHAR(15), i_typeModule VARCHAR(20), i_Id_Semestre INTEGER ) RETURNS VOID AS
$$
BEGIN

    INSERT INTO Module (   Code_ModuleIUT,   libLong,   libCourt,   typeModule,   Id_Semestre ) 
	VALUES             ( i_Code_ModuleIUT, i_libLong, i_libCourt, i_typeModule, i_Id_Semestre );

END;
$$ LANGUAGE plpgsql;

-- Insérer un Intervenanti_inte

DROP              FUNCTION f_insertIntervenant ( i_nom VARCHAR(50), i_prenom VARCHAR(50), i_hService INTEGER, i_hMax INTEGER, i_Id_Contrat INTEGER );
CREATE OR REPLACE FUNCTION f_insertIntervenant ( i_nom VARCHAR(50), i_prenom VARCHAR(50), i_hService INTEGER, i_hMax INTEGER, i_Id_Contrat INTEGER ) RETURNS VOID AS
$$
BEGIN

	INSERT INTO Intervenant (   nom ,   prenom,   hService,   hMax,   Id_Contrat ) 
	VALUES                  ( i_nom , i_prenom, i_hService, i_hMax, i_Id_Contrat );

END;
$$ LANGUAGE plpgsql;


-- Insérer dans intervient

DROP              FUNCTION f_insertIntervient ( i_Id_Intervenant INTEGER, i_Id_Heure INTEGER, i_Code_ModuleIUT VARCHAR(5), i_nbSemaine INTEGER, i_nbGroupe INTEGER, i_nbHeure INTEGER, i_commentaire VARCHAR(50));
CREATE OR REPLACE FUNCTION f_insertIntervient ( i_Id_Intervenant INTEGER, i_Id_Heure INTEGER, i_Code_ModuleIUT VARCHAR(5), i_nbSemaine INTEGER, i_nbGroupe INTEGER, i_nbHeure INTEGER, i_commentaire VARCHAR(50)) RETURNS VOID AS
$$
BEGIN

	INSERT INTO Intervient  (   Id_Intervenant,   Id_Heure,   Code_ModuleIUT,   nbSemaine,   nbGroupe,   nbHeure,   commentaire,   nbHeure,   commentaire ) 
	VALUES                  ( i_Id_Intervenant, i_Id_Heure, i_Code_ModuleIUT, i_nbSemaine, i_nbGroupe, i_nbHeure, i_commentaire, i_nbHeure, i_commentaire );

END;
$$ LANGUAGE plpgsql;

-- Insérer dans horaire

DROP              FUNCTION f_insertHoraire ( i_Id_Heure INTEGER, i_Code_ModuleIUT VARCHAR(5), i_nbHeurePN INTEGER, i_nbHeureRepartie INTEGER, i_nbSemaine VARCHAR(50));
CREATE OR REPLACE FUNCTION f_insertHoraire ( i_Id_Heure INTEGER, i_Code_ModuleIUT VARCHAR(5), i_nbHeurePN INTEGER, i_nbHeureRepartie INTEGER, i_nbSemaine VARCHAR(50)) RETURNS VOID AS
$$
BEGIN

	INSERT INTO Horaire  (   Id_Heure ,   Code_ModuleIUT,   nbHeurePN ,   nbHeureRepartie ,   nbSemaine ) 
	VALUES               ( i_Id_Heure , i_Code_ModuleIUT, i_nbHeurePN , i_nbHeureRepartie , i_nbSemaine );

END;
$$ LANGUAGE plpgsql;

/* ------------------------------------------ */
/*                   UPDATE                   */
/* ------------------------------------------ */

-- Update de semestre
DROP              FUNCTION f_updateSemestre ( u_Id_Semestre INTEGER, u_nbGroupeTP INTEGER, u_nbGroupeTD INTEGER, u_nbEtud INTEGER, u_nbSemaine INTEGER );
CREATE OR REPLACE FUNCTION f_updateSemestre ( u_Id_Semestre INTEGER, u_nbGroupeTP INTEGER, u_nbGroupeTD INTEGER, u_nbEtud INTEGER, u_nbSemaine INTEGER )
RETURNS VOID AS
$$
BEGIN
    UPDATE Semestre

    SET    nbGroupeTP  = u_nbGroupeTP,
           nbGroupeTD  = u_nbGroupeTD,
           nbEtud      = u_nbEtud,
           nbSemaine   = u_nbSemaine

    WHERE  Id_Semestre = u_Id_Semestre;
END;
$$ LANGUAGE plpgsql;

-- Update Semestre nouvelle année
DROP              FUNCTION f_updateAnneeSemestre ( );
CREATE OR REPLACE FUNCTION f_updateAnneeSemestre ( )
RETURNS VOID AS
$$
BEGIN
    UPDATE Semestre

    SET    nbGroupeTP  = 0,
           nbGroupeTD  = 0,
           nbEtud      = 0,
           nbSemaine   = 0;
END;
$$ LANGUAGE plpgsql;

-- Update de contrat
DROP              FUNCTION f_updateContrat ( u_id_contrat INTEGER, u_nomContrat VARCHAR(50), u_hServiceContrat INTEGER, u_hMaxContrat INTEGER, u_ratioTP DOUBLE PRECISION );
CREATE OR REPLACE FUNCTION f_updateContrat ( u_id_contrat INTEGER, u_nomContrat VARCHAR(50), u_hServiceContrat INTEGER, u_hMaxContrat INTEGER, u_ratioTP DOUBLE PRECISION )
RETURNS VOID AS
$$
BEGIN
    UPDATE Contrat

    SET    nomContrat      = u_nomContrat,
           hServiceContrat = u_hServiceContrat,
           hMaxContrat     = u_hMaxContrat,
           ratioTP         = u_ratioTP

    WHERE  Id_Contrat = u_id_contrat;
END;
$$ LANGUAGE plpgsql;

-- Update de Heure

DROP              FUNCTION f_updateHeure ( u_Id_Heure INTEGER, u_nomHeure VARCHAR(50), u_coeffTD DOUBLE PRECISION );
CREATE OR REPLACE FUNCTION f_updateHeure ( u_Id_Heure INTEGER, u_nomHeure VARCHAR(50), u_coeffTD DOUBLE PRECISION )
RETURNS VOID AS
$$
BEGIN
    UPDATE Heure
    SET    coeffTD  = u_coeffTD,
           nomHeure = u_nomHeure
           
    WHERE  Id_Heure = u_Id_Heure;
END;
$$ LANGUAGE plpgsql;

-- Update des modules

DROP              FUNCTION f_updateModuleIUT ( u_Code_ModuleIUT VARCHAR(50), u_libLong VARCHAR(60), u_libCourtModuleIUT VARCHAR(15), u_typeModule VARCHAR(20), u_Id_Semestre INTEGER );
CREATE OR REPLACE FUNCTION f_updateModuleIUT ( u_Code_ModuleIUT VARCHAR(50), u_libLong VARCHAR(60), u_libCourtModuleIUT VARCHAR(15), u_typeModule VARCHAR(20), u_Id_Semestre INTEGER )
RETURNS VOID AS
$$
BEGIN
    UPDATE ModuleIUT

    SET    libLong     = u_libLong,  
           libCourt    = u_libCourt, 
           typeModule  = u_typeModule,    
           Id_Semestre = u_Id_Semestre  

    WHERE  Code_ModuleIUT = u_Code_ModuleIUT;
END;
$$ LANGUAGE plpgsql;

-- Update d'intervenant

DROP              FUNCTION f_updateIntervenant ( u_Id_Intervenant INTEGER, u_nom VARCHAR(50), u_prenom VARCHAR(50), u_hService INTEGER, u_hMax INTEGER, u_Id_Contrat INTEGER );
CREATE OR REPLACE FUNCTION f_updateIntervenant ( u_Id_Intervenant INTEGER, u_nom VARCHAR(50), u_prenom VARCHAR(50), u_hService INTEGER, u_hMax INTEGER, u_Id_Contrat INTEGER )
RETURNS VOID AS
$$
BEGIN
    UPDATE Intervenant

    SET    nom        = u_nom,
           prenom     = u_prenom,
           hService   = u_hService,
           hMax       = u_hMax,
           Id_Contrat = u_Id_Contrat

    WHERE  Id_Intervenant = u_Id_Intervenant;
END;
$$ LANGUAGE plpgsql;

-- Update d'intervient

DROP              FUNCTION f_updateIntervient ( u_Id_Intervenant INTEGER, u_Id_Heure INTEGER, u_Code_ModuleIUT VARCHAR(5), u_nbSemaine INTEGER, u_nbGroupe INTEGER, u_nbHeure INTEGER, u_commentaire VARCHAR(50) );
CREATE OR REPLACE FUNCTION f_updateIntervient ( u_Id_Intervenant INTEGER, u_Id_Heure INTEGER, u_Code_ModuleIUT VARCHAR(5), u_nbSemaine INTEGER, u_nbGroupe INTEGER, u_nbHeure INTEGER, u_commentaire VARCHAR(50) )
RETURNS VOID AS
$$
BEGIN
    UPDATE Intervient

    SET    nbSemaine   = u_nbSemaine,  
           nbGroupe    = u_nbGroupe,
           nbHeure     = u_nbHeure,
           commentaire = u_commentaire

    WHERE  Id_Intervenant = u_Id_Intervenant AND Id_Heure = u_Id_Heure AND Code_ModuleIUT = u_Code_ModuleIUT;
END;
$$ LANGUAGE plpgsql;

-- Update d'horaire

DROP              FUNCTION f_updateHoraire ( u_Id_Heure VARCHAR(50), u_Code_ModuleIUT VARCHAR(5), u_nbHeurePN INTEGER, u_nbHeureRepartie INTEGER, u_nbSemaine INTEGER );
CREATE OR REPLACE FUNCTION f_updateHoraire ( u_Id_Heure VARCHAR(50), u_Code_ModuleIUT VARCHAR(5), u_nbHeurePN INTEGER, u_nbHeureRepartie INTEGER, u_nbSemaine INTEGER )
RETURNS VOID AS
$$
BEGIN
    UPDATE Horaire

    SET    nbSemaine         = u_nbSemaine,
           nbHeureRepartie   = u_nbHeureRepartie,
           nbHeurePN         = u_nbHeurePN

    WHERE  Id_Heure = u_Id_Heure AND Code_ModuleIUT = u_Code_ModuleIUT;
END;
$$ LANGUAGE plpgsql;


/* ------------------------------------------ */
/*                   DELETE                   */
/* ------------------------------------------ */

-- Supprimer TOUTES les données
DROP              FUNCTION f_deleteAll ( );
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

DROP              FUNCTION f_deleteContrat ( d_id_contrat INTEGER );
CREATE OR REPLACE FUNCTION f_deleteContrat ( d_id_contrat INTEGER ) RETURNS VOID AS 
$$
BEGIN

	DELETE FROM Contrat WHERE Id_Contrat = d_id_contrat;

END;
$$ LANGUAGE plpgsql;

-- Supprimer une heure

DROP              FUNCTION f_deleteHeure ( d_nomHeure VARCHAR(50) );
CREATE OR REPLACE FUNCTION f_deleteHeure ( d_nomHeure VARCHAR(50) ) RETURNS VOID AS
$$
BEGIN

	DELETE FROM Heure WHERE nomHeure = d_nomHeure;

END;
$$ LANGUAGE plpgsql;

-- Supprimer un ModuleIUT

DROP              FUNCTION f_deleteModuleIUT ( d_Code_ModuleIUT INTEGER );
CREATE OR REPLACE FUNCTION f_deleteModuleIUT ( d_Code_ModuleIUT INTEGER ) RETURNS VOID AS
$$
BEGIN

	DELETE FROM ModuleIUT WHERE Code_ModuleIUT = d_Code_ModuleIUT;

END;
$$ LANGUAGE plpgsql;

-- Supprimer un intervenant

DROP              FUNCTION f_deleteIntervenant ( d_Id_Intervenant INTEGER );
CREATE OR REPLACE FUNCTION f_deleteIntervenant ( d_Id_Intervenant INTEGER ) RETURNS VOID AS
$$
BEGIN

	DELETE FROM Intervenant WHERE Id_Intervenant = d_Id_Intervenant;

END;
$$ LANGUAGE plpgsql;

-- Supprimer dans intervient

DROP              FUNCTION f_deleteIntervient ( d_Id_Intervenant INTEGER, d_Id_Heure INTEGER, d_Code_ModuleIUT VARCHAR(5) );
CREATE OR REPLACE FUNCTION f_deleteIntervient ( d_Id_Intervenant INTEGER, d_Id_Heure INTEGER, d_Code_ModuleIUT VARCHAR(5) ) RETURNS VOID AS
$$
BEGIN

	DELETE FROM Intervient WHERE Id_Intervenant = d_Id_Intervenant AND Id_Heure = d_Id_Heure AND Code_ModuleIUT = d_Code_ModuleIUT;

END;
$$ LANGUAGE plpgsql;

-- Supprimer une année 

DROP              FUNCTION f_deleteIntervient ( );
CREATE OR REPLACE FUNCTION f_deleteIntervient ( ) RETURNS VOID AS
$$
BEGIN

	DELETE FROM Intervient;

END;
$$ LANGUAGE plpgsql;

-- Supprimer dans horaire

DROP              FUNCTION f_deleteHoraire ( d_Id_Heure INTEGER, d_Code_ModuleIUT VARCHAR(5) );
CREATE OR REPLACE FUNCTION f_deleteHoraire ( d_Id_Heure INTEGER, d_Code_ModuleIUT VARCHAR(5) ) RETURNS VOID AS
$$
BEGIN

	DELETE FROM Horaire WHERE Id_Heure = d_Id_Heure AND Code_ModuleIUT = d_Code_ModuleIUT;

END;
$$ LANGUAGE plpgsql;