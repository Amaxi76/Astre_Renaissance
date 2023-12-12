/*
	@author Alizéa LEBARON
	@version 1.0.0 - 12/12/2023 
	@date 12/12/2023 
	@description Script de création des fonctions
*/

/* ------------------------------------------ */
/*                   SELECT                   */
/* ------------------------------------------ */

-- Sélection globale pour toutes les tables
DROP              FUNCTION selectAll ( table_name VARCHAR );
CREATE OR REPLACE FUNCTION selectAll ( table_name VARCHAR ) RETURNS TABLE ( result_row RECORD ) AS
$$
BEGIN

    RETURN QUERY EXECUTE 'SELECT * FROM ' || table_name;

END;
$$ LANGUAGE plpgsql;

-- Sélection de tous les modules d'un semestre
DROP              FUNCTION selectModuleParSemestre ( numSemestre INTEGER );
CREATE OR REPLACE FUNCTION selectModuleParSemestre ( numSemestre INTEGER ) RETURNS TABLE ( result_row RECORD ) AS
$$
BEGIN

    RETURN QUERY EXECUTE 'SELECT * FROM ModuleIUT WHERE Id_Semestre = ' || numSemestre;

END;
$$ LANGUAGE plpgsql;

-- Sélectionner un semestre en particulier
DROP              FUNCTION selectUnSemestre ( numSemestre INTEGER );
CREATE OR REPLACE FUNCTION selectUnSemestre ( numSemestre INTEGER ) RETURNS TABLE ( result_row RECORD ) AS
$$
BEGIN

    RETURN QUERY EXECUTE 'SELECT * FROM Semestre WHERE Id_Semestre = ' || numSemestre;

END;
$$ LANGUAGE plpgsql;

-- Sélectionner un contrat en particulier
DROP              FUNCTION selectUnContrat ( numContrat INTEGER );
CREATE OR REPLACE FUNCTION selectUnContrat ( numContrat INTEGER ) RETURNS TABLE ( result_row RECORD ) AS
$$
BEGIN

    RETURN QUERY EXECUTE 'SELECT * FROM Contrat WHERE Id_Semestre = ' || numContrat;

END;
$$ LANGUAGE plpgsql;

/* ------------------------------------------ */
/*                   INSERT                   */
/* ------------------------------------------ */

-- Insérer un contrat

DROP              FUNCTION insertContrat ( i_nomContrat VARCHAR(50), i_hServiceContrat INTEGER, i_hMaxContrat INTEGER, i_ratioTP DOUBLE PRECISION );
CREATE OR REPLACE FUNCTION insertContrat ( i_nomContrat VARCHAR(50), i_hServiceContrat INTEGER, i_hMaxContrat INTEGER, i_ratioTP DOUBLE PRECISION ) RETURNS VOID AS
$$
BEGIN

    INSERT INTO Contrat ( nomContrat, hServiceContrat, hMaxContrat, ratioTP ) VALUES ( i_nomContrat, i_hServiceContrat, i_hMaxContrat, i_ratioTP );

END;
$$ LANGUAGE plpgsql;

-- Insérer une heure

DROP              FUNCTION insertHeure ( i_nomHeure VARCHAR(50), i_coeffTD DOUBLE PRECISION );
CREATE OR REPLACE FUNCTION insertHeure ( i_nomHeure VARCHAR(50), i_coeffTD DOUBLE PRECISION ) RETURNS VOID AS
$$
BEGIN

    INSERT INTO Heure ( nomHeure, coeffTD ) VALUES ( i_nomHeure, i_coeffTD );

END;
$$ LANGUAGE plpgsql;

-- Insérer un Module

DROP              FUNCTION insertModule ( i_Code_ModuleIUT VARCHAR(5), i_libLong VARCHAR(60),  i_libCourt VARCHAR(15), i_Id_TypeModule INTEGER, i_Id_Semestre INTEGER);
CREATE OR REPLACE FUNCTION insertModule ( i_Code_ModuleIUT VARCHAR(5), i_libLong VARCHAR(60),  i_libCourt VARCHAR(15), i_Id_TypeModule INTEGER, i_Id_Semestre INTEGER ) RETURNS VOID AS
$$
BEGIN

    INSERT INTO Module (   Code_ModuleIUT,   libLong,   libCourt,   Id_TypeModule,   Id_Semestre ) 
	VALUES             ( i_Code_ModuleIUT, i_libLong, i_libCourt, i_Id_TypeModule, i_Id_Semestre );

END;
$$ LANGUAGE plpgsql;

-- Insérer un Intervenanti_inte

DROP              FUNCTION insertIntervenant ( i_nom VARCHAR(50), i_prenom VARCHAR(50), i_hService INTEGER, i_hMax INTEGER, i_Id_Contrat INTEGER );
CREATE OR REPLACE FUNCTION insertIntervenant ( i_nom VARCHAR(50), i_prenom VARCHAR(50), i_hService INTEGER, i_hMax INTEGER, i_Id_Contrat INTEGER ) RETURNS VOID AS
$$
BEGIN

    INSERT INTO Intervenant (   nom ,   prenom,   hService,   hMax,   Id_Contrat ) 
	VALUES                  ( i_nom , i_prenom, i_hService, i_hMax, i_Id_Contrat );

END;
$$ LANGUAGE plpgsql;


-- Insérer dans intervient

DROP              FUNCTION insertIntervient ( i_Id_Intervenant INTEGER, i_nomHeure VARCHAR(50), i_Code_ModuleIUT VARCHAR(5), i_nbSemaine INTEGER, i_nbGroupe INTEGER, i_nbHeure INTEGER, i_commentaire VARCHAR(50));
CREATE OR REPLACE FUNCTION insertIntervient ( i_Id_Intervenant INTEGER, i_nomHeure VARCHAR(50), i_Code_ModuleIUT VARCHAR(5), i_nbSemaine INTEGER, i_nbGroupe INTEGER, i_nbHeure INTEGER, i_commentaire VARCHAR(50)) RETURNS VOID AS
$$
BEGIN

    INSERT INTO Intervient  (   Id_Intervenant,   nomHeure,   Code_ModuleIUT,   nbSemaine,   nbGroupe,   nbHeure,   commentaire,   nbHeure,   commentaire ) 
	VALUES                  ( i_Id_Intervenant, i_nomHeure, i_Code_ModuleIUT, i_nbSemaine, i_nbGroupe, i_nbHeure, i_commentaire, i_nbHeure, i_commentaire );

END;
$$ LANGUAGE plpgsql;

-- Insérer dans horaire

DROP              FUNCTION insertHoraire ( i_nomHeure INTEGER, i_Code_ModuleIUT VARCHAR(5), i_nbHeurePN INTEGER, i_nbHeureRepartie INTEGER, i_nbSemaine VARCHAR(50));
CREATE OR REPLACE FUNCTION insertHoraire ( i_nomHeure INTEGER, i_Code_ModuleIUT VARCHAR(5), i_nbHeurePN INTEGER, i_nbHeureRepartie INTEGER, i_nbSemaine VARCHAR(50)) RETURNS VOID AS
$$
BEGIN

    INSERT INTO Horaire  (   nomHeure ,   Code_ModuleIUT,   nbHeurePN ,   nbHeureRepartie ,   nbSemaine ) 
	VALUES               ( i_nomHeure , i_Code_ModuleIUT, i_nbHeurePN , i_nbHeureRepartie , i_nbSemaine );

END;
$$ LANGUAGE plpgsql;

/* ------------------------------------------ */
/*                   UPDATE                   */
/* ------------------------------------------ */


/* ------------------------------------------ */
/*                   DELETE                   */
/* ------------------------------------------ */