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
DROP FUNCTION selectAll(table_name VARCHAR);
CREATE OR REPLACE FUNCTION selectAll(table_name VARCHAR) RETURNS TABLE (result_row RECORD) AS
$$
BEGIN

    RETURN QUERY EXECUTE 'SELECT * FROM ' || table_name;

END;
$$ LANGUAGE plpgsql;

-- Sélection de tous les modules d'un semestre
DROP FUNCTION selectModuleParSemestre(numSemestre INTEGER);
CREATE OR REPLACE FUNCTION selectModuleParSemestre(numSemestre INTEGER) RETURNS TABLE (result_row RECORD) AS
$$
BEGIN

    RETURN QUERY EXECUTE 'SELECT * FROM ModuleIUT WHERE Id_Semestre = ' || numSemestre;

END;
$$ LANGUAGE plpgsql;

-- Sélectionner un semestre en particulier
DROP FUNCTION selectUnSemestre(numSemestre INTEGER);
CREATE OR REPLACE FUNCTION selectUnSemestre(numSemestre INTEGER) RETURNS TABLE (result_row RECORD) AS
$$
BEGIN

    RETURN QUERY EXECUTE 'SELECT * FROM Semestre WHERE Id_Semestre = ' || numSemestre;

END;
$$ LANGUAGE plpgsql;

-- Sélectionner un contrat en particulier
DROP FUNCTION selectUnContrat(numContrat INTEGER);
CREATE OR REPLACE FUNCTION selectUnContrat(numContrat INTEGER) RETURNS TABLE (result_row RECORD) AS
$$
BEGIN

    RETURN QUERY EXECUTE 'SELECT * FROM Contrat WHERE Id_Semestre = ' || numContrat;

END;
$$ LANGUAGE plpgsql;

/* ------------------------------------------ */
/*                   INSERT                   */
/* ------------------------------------------ */


/* ------------------------------------------ */
/*                   UPDATE                   */
/* ------------------------------------------ */


/* ------------------------------------------ */
/*                   DELETE                   */
/* ------------------------------------------ */