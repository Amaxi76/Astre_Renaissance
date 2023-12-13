/*
	@author Alizéa LEBARON
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

-- Sélectionner les heuresPN

DROP              FUNCTION f_selectNBHeurePN ( code VARCHAR(5) );
CREATE OR REPLACE FUNCTION f_selectNBHeurePN ( code VARCHAR(5) ) RETURNS TABLE ( result_row RECORD ) AS
$$
BEGIN

    RETURN QUERY EXECUTE 'SELECT he.nomHeure, ho.nbHeurePN
                          FROM Horaire ho JOIN Heure he ON ho.nomHeure = he.nomHeure
                                          JOIN ModuleIUT m ON ho.Code_ModuleIUT = m.Code_ModuleIUT
                          WHERE ho.Code_ModuleIUT = $1'
    USING p_code;

END;
$$ LANGUAGE plpgsql;

-- Sélectionner les heureRepartie

DROP              FUNCTION f_selectNBHeureRepartie ( code VARCHAR(5) );
CREATE OR REPLACE FUNCTION f_selectNBHeureRepartie ( code VARCHAR(5) ) RETURNS TABLE ( result_row RECORD ) AS
$$
BEGIN

    RETURN QUERY EXECUTE 'SELECT he.nomHeure, ho.nbHeureRepartie
                          FROM Horaire ho JOIN Heure he ON ho.nomHeure = he.nomHeure
                                          JOIN ModuleIUT m ON ho.Code_ModuleIUT = m.Code_ModuleIUT
                          WHERE ho.Code_ModuleIUT = $1'
    USING p_code;

END;
$$ LANGUAGE plpgsql;


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

DROP              FUNCTION f_insertModule ( i_Code_ModuleIUT VARCHAR(5), i_libLong VARCHAR(60),  i_libCourt VARCHAR(15), i_Id_TypeModule INTEGER, i_Id_Semestre INTEGER);
CREATE OR REPLACE FUNCTION f_insertModule ( i_Code_ModuleIUT VARCHAR(5), i_libLong VARCHAR(60),  i_libCourt VARCHAR(15), i_Id_TypeModule INTEGER, i_Id_Semestre INTEGER ) RETURNS VOID AS
$$
BEGIN

    INSERT INTO Module (   Code_ModuleIUT,   libLong,   libCourt,   Id_TypeModule,   Id_Semestre ) 
	VALUES             ( i_Code_ModuleIUT, i_libLong, i_libCourt, i_Id_TypeModule, i_Id_Semestre );

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

DROP              FUNCTION f_insertIntervient ( i_Id_Intervenant INTEGER, i_nomHeure VARCHAR(50), i_Code_ModuleIUT VARCHAR(5), i_nbSemaine INTEGER, i_nbGroupe INTEGER, i_nbHeure INTEGER, i_commentaire VARCHAR(50));
CREATE OR REPLACE FUNCTION f_insertIntervient ( i_Id_Intervenant INTEGER, i_nomHeure VARCHAR(50), i_Code_ModuleIUT VARCHAR(5), i_nbSemaine INTEGER, i_nbGroupe INTEGER, i_nbHeure INTEGER, i_commentaire VARCHAR(50)) RETURNS VOID AS
$$
BEGIN

    INSERT INTO Intervient  (   Id_Intervenant,   nomHeure,   Code_ModuleIUT,   nbSemaine,   nbGroupe,   nbHeure,   commentaire,   nbHeure,   commentaire ) 
	VALUES                  ( i_Id_Intervenant, i_nomHeure, i_Code_ModuleIUT, i_nbSemaine, i_nbGroupe, i_nbHeure, i_commentaire, i_nbHeure, i_commentaire );

END;
$$ LANGUAGE plpgsql;

-- Insérer dans horaire

DROP              FUNCTION f_insertHoraire ( i_nomHeure INTEGER, i_Code_ModuleIUT VARCHAR(5), i_nbHeurePN INTEGER, i_nbHeureRepartie INTEGER, i_nbSemaine VARCHAR(50));
CREATE OR REPLACE FUNCTION f_insertHoraire ( i_nomHeure INTEGER, i_Code_ModuleIUT VARCHAR(5), i_nbHeurePN INTEGER, i_nbHeureRepartie INTEGER, i_nbSemaine VARCHAR(50)) RETURNS VOID AS
$$
BEGIN

    INSERT INTO Horaire  (   nomHeure ,   Code_ModuleIUT,   nbHeurePN ,   nbHeureRepartie ,   nbSemaine ) 
	VALUES               ( i_nomHeure , i_Code_ModuleIUT, i_nbHeurePN , i_nbHeureRepartie , i_nbSemaine );

END;
$$ LANGUAGE plpgsql;

/* ------------------------------------------ */
/*                   UPDATE                   */
/* ------------------------------------------ */

-- Update de semestre
CREATE OR REPLACE FUNCTION f_updateSemestre ( u_Id_Semestre INTEGER, u_nbGroupeTP INTEGER, u_nbGroupeTD INTEGER, u_nbEtud INTEGER, u_nbSemaine INTEGER )
RETURNS VOID AS
$$
BEGIN
    UPDATE Semestre
    SET 
        nbGroupeTP  = u_nbGroupeTP,
        nbGroupeTD  = u_nbGroupeTD,
        nbEtud      = u_nbEtud,
        nbSemaine   = u_nbSemaine

    WHERE Id_Semestre = u_Id_Semestre;
END;
$$ LANGUAGE plpgsql;

-- Update de contrat
CREATE OR REPLACE FUNCTION f_updateContrat ( u_id_contrat INTEGER, u_nomContrat VARCHAR(50), u_hServiceContrat INTEGER, u_hMaxContrat INTEGER, u_ratioTP DOUBLE PRECISION )
RETURNS VOID AS
$$
BEGIN
    UPDATE Contrat
    SET 
        nomContrat      = u_nomContrat,
        hServiceContrat = u_hServiceContrat,
        hMaxContrat     = u_hMaxContrat,
        ratioTP         = u_ratioTP

    WHERE Id_Contrat = u_id_contrat;
END;
$$ LANGUAGE plpgsql;



/* ------------------------------------------ */
/*                   DELETE                   */
/* ------------------------------------------ */

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

DROP              FUNCTION f_deleteIntervient ( d_Id_Intervenant INTEGER, d_nomHeure VARCHAR(50), d_Code_ModuleIUT VARCHAR(5) );
CREATE OR REPLACE FUNCTION f_deleteIntervient ( d_Id_Intervenant INTEGER, d_nomHeure VARCHAR(50), d_Code_ModuleIUT VARCHAR(5) ) RETURNS VOID AS
$$
BEGIN

    DELETE FROM Intervient WHERE Id_Intervenant = d_Id_Intervenant AND nomHeure = d_nomHeure AND Code_ModuleIUT = d_Code_ModuleIUT;

END;
$$ LANGUAGE plpgsql;

-- Supprimer dans horaire

DROP              FUNCTION f_deleteHoraire ( d_nomHeure VARCHAR(50), d_Code_ModuleIUT VARCHAR(5) );
CREATE OR REPLACE FUNCTION f_deleteHoraire ( d_nomHeure VARCHAR(50), d_Code_ModuleIUT VARCHAR(5) ) RETURNS VOID AS
$$
BEGIN

    DELETE FROM Horaire WHERE nomHeure = d_nomHeure AND Code_ModuleIUT = d_Code_ModuleIUT;

END;
$$ LANGUAGE plpgsql;