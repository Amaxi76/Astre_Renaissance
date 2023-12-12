/*
	@author Alizéa LEBARON
	@version 1.0.0 - 12/12/2023 
	@date 12/12/2023 
	@description Script de création des fonctions
*/

/* ------------------------------------------ */
/*                   SELECT                   */
/* ------------------------------------------ */

CREATE OR REPLACE FUNCTION selectAll(table_name VARCHAR) RETURNS TABLE (result_row RECORD) AS
$$
BEGIN

    RETURN QUERY EXECUTE 'SELECT * FROM ' || table_name;

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