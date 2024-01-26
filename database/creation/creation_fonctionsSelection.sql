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



-- Sélection de tous les modules (vue particulière) d'un semestre
DROP FUNCTION IF EXISTS f_selectModuleParSemestre ( numSemestre INTEGER );
CREATE OR REPLACE FUNCTION f_selectModuleParSemestre ( numSemestre INTEGER ) RETURNS TABLE ( id_semestre INTEGER, code_moduleiut VARCHAR, liblong VARCHAR, recap TEXT, valide BOOLEAN ) AS
$$
BEGIN
	RETURN QUERY
		SELECT *
		FROM   v_Module v
		WHERE  v.Id_Semestre = $1;

END;
$$ LANGUAGE plpgsql;

-- Sélectionner un semestre en particulier
DROP FUNCTION IF EXISTS f_selectUnSemestre ( numSemestre INTEGER );
CREATE OR REPLACE FUNCTION f_selectUnSemestre ( numSemestre INTEGER ) RETURNS TABLE ( result_row RECORD ) AS
$$
BEGIN

	RETURN QUERY EXECUTE 'SELECT * FROM Semestre WHERE Id_Semestre = ' || numSemestre;

END;
$$ LANGUAGE plpgsql;

-- Sélectionner un contrat en particulier

DROP FUNCTION IF EXISTS f_selectUnContrat ( numContrat INTEGER );
CREATE OR REPLACE FUNCTION f_selectUnContrat ( numContrat INTEGER ) RETURNS TABLE ( result_row RECORD ) AS
$$
BEGIN

	RETURN QUERY EXECUTE 'SELECT * FROM Contrat WHERE Id_Semestre = ' || numContrat;

END;
$$ LANGUAGE plpgsql;

-- Selecion des ModulesIUT par semestres
DROP FUNCTION IF EXISTS f_selectModulesIUTParSemestre ( numSemestre INTEGER );
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
DROP FUNCTION IF EXISTS f_selectHoraireParModule ( code VARCHAR );
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

DROP FUNCTION IF EXISTS f_selectNBHeureParModule ( s_code VARCHAR(5), s_Id_Intervenant INTEGER, s_Id_Heure INTEGER );
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

DROP FUNCTION IF EXISTS f_selectNBHeurePNParModule ( s_code VARCHAR(5), s_Id_Heure INTEGER );
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

DROP FUNCTION IF EXISTS f_selectNBHeureRepParModule ( s_code VARCHAR(5), s_Id_Heure INTEGER ) CASCADE;
CREATE OR REPLACE FUNCTION f_selectNBHeureRepParModule ( s_code VARCHAR(5), s_Id_Heure INTEGER ) RETURNS INTEGER AS
$$
DECLARE
	v_resultat INTEGER;
BEGIN
   
	SELECT SUM (nbSemaine * nbGroupe * nbHeure)
	INTO v_resultat
	FROM Intervient
	WHERE Code_ModuleIUT = s_code AND 
		  Id_Heure = s_Id_Heure;

	-- Retourner le résultat et si la requête est nulle, on renvoie 0
	RETURN COALESCE(v_resultat, 0);
END;
$$ LANGUAGE plpgsql;

-- Sélection du nombre d'heure pour une enseignant et un semestre
-- Utilisé dans la génération de HTML Intervenant

DROP FUNCTION IF EXISTS f_selectNBHeureParSemestre ( s_Id_Semestre INTEGER, s_Id_Intervenant INTEGER ) CASCADE;
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

-- Sélection du nombre d'heure pour une enseignant et un semestre
-- Utilisé dans la génération de HTML Intervenant

DROP FUNCTION IF EXISTS f_selectNBHeureParSemestrePair (s_Id_Intervenant INTEGER ) CASCADE;
CREATE OR REPLACE FUNCTION f_selectNBHeureParSemestrePair (s_Id_Intervenant INTEGER ) RETURNS INTEGER AS
$$
DECLARE
	totalHeures INTEGER := 0;
BEGIN

	-- Semestre 2
	totalHeures := totalHeures + f_selectNBHeureParSemestre(2, s_Id_Intervenant);

	-- Semestre 4
	totalHeures := totalHeures + f_selectNBHeureParSemestre(4, s_Id_Intervenant);

	-- Semestre 6
	totalHeures := totalHeures + f_selectNBHeureParSemestre(6, s_Id_Intervenant);

	-- Retourner le résultat et si la requête est nulle, on renvoie 0
	RETURN COALESCE(totalHeures, 0);
END;
$$ LANGUAGE plpgsql;

-- Sélection du nombre d'heure pour une enseignant et un semestre
-- Utilisé dans la génération de HTML Intervenant

DROP FUNCTION IF EXISTS f_selectNBHeureParSemestreImpair (s_Id_Intervenant INTEGER ) CASCADE;
CREATE OR REPLACE FUNCTION f_selectNBHeureParSemestreImpair (s_Id_Intervenant INTEGER ) RETURNS INTEGER AS
$$
DECLARE
	totalHeures INTEGER := 0;
BEGIN

	-- Semestre 1
	totalHeures := totalHeures + f_selectNBHeureParSemestre(1, s_Id_Intervenant);

	-- Semestre 3
	totalHeures := totalHeures + f_selectNBHeureParSemestre(3, s_Id_Intervenant);

	-- Semestre 5
	totalHeures := totalHeures + f_selectNBHeureParSemestre(5, s_Id_Intervenant);

	-- Retourner le résultat et si la requête est nulle, on renvoie 0
	RETURN COALESCE(totalHeures, 0);
END;
$$ LANGUAGE plpgsql;

-- Sélection du nombre d'heure pour une enseignant et un semestre
-- Utilisé dans la génération de HTML Intervenant

DROP FUNCTION IF EXISTS f_selectNBHeureParSemestreTot (s_Id_Intervenant INTEGER ) CASCADE;
CREATE OR REPLACE FUNCTION f_selectNBHeureParSemestreTot (s_Id_Intervenant INTEGER ) RETURNS INTEGER AS
$$
DECLARE
	totalHeures INTEGER := 0;
BEGIN

	-- Impair
	totalHeures := totalHeures +  f_selectNBHeureParSemestreImpair (s_Id_Intervenant);

	-- Pair
	totalHeures := totalHeures +  f_selectNBHeureParSemestrePair   (s_Id_Intervenant);


	-- Retourner le résultat et si la requête est nulle, on renvoie 0
	RETURN COALESCE(totalHeures, 0);
END;
$$ LANGUAGE plpgsql;

-- Sélection du nombre d'heure etqd
-- Utilisé dans l'affectation et la répartition

DROP FUNCTION IF EXISTS f_selectNBHeureEQTD ( s_code VARCHAR(5), s_nomHeure VARCHAR ( 50 ) );
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

-- Sélection de la somme des heures réparties dans un module
-- Utilisée dans l'affichage du tableau des modules

DROP FUNCTION IF EXISTS f_selectTotHeureRep ( s_code VARCHAR(5) ) CASCADE;
CREATE OR REPLACE FUNCTION f_selectTotHeureRep ( s_code VARCHAR(5) ) RETURNS INTEGER AS
$$
DECLARE
	totalHeures INTEGER;
BEGIN
	-- Calcul du nombre total d'heures pour l'intervenant dans le semestre donné
	SELECT  SUM (nbHeureRepartie) INTO totalHeures
	FROM    Horaire
	WHERE   Code_ModuleIUT = s_code;

	-- Retourner le résultat et si la requête est nulle, on renvoie 0
	RETURN COALESCE(totalHeures, 0);
END;
$$ LANGUAGE plpgsql;

-- Sélection de la somme des heures PN dans un module
-- Utilisée dans l'affichage du tableau des modulesx

DROP FUNCTION IF EXISTS f_selectTotHeurePN ( s_code VARCHAR(5) ) CASCADE;
CREATE OR REPLACE FUNCTION f_selectTotHeurePN ( s_code VARCHAR(5) ) RETURNS INTEGER AS
$$
DECLARE
	totalHeures INTEGER;
BEGIN
	-- Calcul du nombre total d'heures pour l'intervenant dans le semestre donné
	SELECT  SUM (nbHeurePN) INTO totalHeures
	FROM    Horaire
	WHERE   Code_ModuleIUT = s_code;

	-- Retourner le résultat et si la requête est nulle, on renvoie 0
	RETURN COALESCE(totalHeures, 0);
END;
$$ LANGUAGE plpgsql;

-- Sélectionner le total d'heure d'un enseignant pour un type d'heure
-- Utilisé dans la génération de fichier

DROP FUNCTION IF EXISTS f_selectTotHeureInter ( s_Id_Intervenant INTEGER, s_Id_Heure INTEGER ) CASCADE;
CREATE OR REPLACE FUNCTION f_selectTotHeureInter ( s_Id_Intervenant INTEGER, s_Id_Heure INTEGER ) RETURNS INTEGER AS
$$
DECLARE
	totalHeures INTEGER;
BEGIN
	-- Calcul du nombre total d'heures pour l'intervenant dans le semestre donné
	SELECT  SUM (nbSemaine * nbGroupe * nbHeure) INTO totalHeures
	FROM    Intervient
	WHERE   Id_Intervenant = s_Id_Intervenant AND 
			Id_Heure = s_Id_Heure ;

	-- Retourner le résultat et si la requête est nulle, on renvoie 0
	RETURN COALESCE(totalHeures, 0);
END;
$$ LANGUAGE plpgsql;


DROP FUNCTION IF EXISTS f_selectIntervient ( code_Module VARCHAR );
CREATE OR REPLACE FUNCTION f_selectIntervient ( code_Module VARCHAR ) RETURNS TABLE ( id_intervenant INTEGER, nom_Intervenant TEXT, nom_Heure VARCHAR, nbSemaine INTEGER, nbGroupe INTEGER, nbHeure NUMERIC(7,2), commentaire VARCHAR ) AS
$$
BEGIN

	RETURN QUERY
	
	SELECT inte.id_intervenant ,inte.nom || ' ' || inte.prenom, h.nomHeure, i.nbSemaine, i.nbGroupe, i.nbHeure, i.commentaire
	FROM   Intervient i JOIN Intervenant inte ON i.id_intervenant = inte.Id_Intervenant
	                    JOIN Heure       h    ON i.Id_Heure       = h.Id_Heure
	WHERE  i.Code_ModuleIUT = $1;

END;
$$ LANGUAGE plpgsql;

DROP FUNCTION IF EXISTS f_selectHoraire ( code_Module VARCHAR );
CREATE OR REPLACE FUNCTION f_selectHoraire ( code_Module VARCHAR ) RETURNS TABLE ( nomHeure VARCHAR, nbHeurePN INTEGER ) AS
$$
BEGIN

	RETURN QUERY
	
	SELECT h.nomHeure, ho.nbHeurePN
	FROM   Horaire ho JOIN Heure h     ON h.id_heure    = ho.id_heure
	                  JOIN ModuleIUT m ON m.Code_ModuleIUT = ho.Code_ModuleIUT
	WHERE  ho.Code_ModuleIUT = code_module;

END;
$$ LANGUAGE plpgsql;

DROP FUNCTION IF EXISTS f_selectHoraireBis ( code_Module VARCHAR );
CREATE OR REPLACE FUNCTION f_selectHoraireBis ( code_Module VARCHAR ) RETURNS TABLE ( nomHeure VARCHAR, nbHeureRepartie INTEGER, nbSemaine INTEGER ) AS
$$
BEGIN

	RETURN QUERY
	
	SELECT h.nomHeure, ho.nbheurerepartie, ho.nbSemaine
	FROM   Horaire ho JOIN Heure h     ON h.id_heure    = ho.id_heure
	                  JOIN ModuleIUT m ON m.Code_ModuleIUT = ho.Code_ModuleIUT
	WHERE  ho.Code_ModuleIUT = code_module;

END;
$$ LANGUAGE plpgsql;