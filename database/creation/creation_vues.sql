/**
 * Permet de créer les vues d'ASTRE
 * @author Maxime LEMOINE
 * @version 1.0.0 - 15/01/2024
 * @date 15/01/2024
 * @warning : FIXME: SIMPLE COPIE DE LE L'ANCIEN
 */

/* Création de vue */

-- Vue intervenant
--TODO: voir à mettre une vue à la place de tous les f_selectNBHeureParSemestre
CREATE VIEW astre.v_Intervenant AS
SELECT
	i.idIntervenant,
	c.nom AS nomContrat,
	i.nom,
	i.prenom,
	i.nbHeureService AS hServ,
	i.maxHeureService AS hMax,
	f_conversion ( c.idContrat ) AS ratioTP,
	f_selectNBHeureParSemestre       ( 1, Id_Intervenant  ) AS s1,
	f_selectNBHeureParSemestre       ( 3, Id_Intervenant  ) AS s3,
	f_selectNBHeureParSemestre       ( 5, Id_Intervenant  ) AS s5,
	f_selectNBHeureParSemestreImpair ( Id_Intervenant     ) AS totImp,
	f_selectNBHeureParSemestre       ( 2, Id_Intervenant  ) AS s2,
	f_selectNBHeureParSemestre       ( 4, Id_Intervenant  ) AS s4,
	f_selectNBHeureParSemestre       ( 6, Id_Intervenant  ) AS s6,
	f_selectNBHeureParSemestrePair   ( Id_Intervenant     ) AS totPai,
	f_selectNBHeureParSemestreTot    ( Id_Intervenant     ) AS total
FROM
	Intervenant i
	JOIN Contrat c ON i.Id_Contrat = c.Id_Contrat
ORDER BY
	Id_intervenant ASC;

-- Vue des modules
DROP VIEW IF EXISTS v_Module;
CREATE OR REPLACE VIEW v_Module AS
SELECT id_semestre, Code_ModuleIUT, libLong, heureAffecte || '/' || heurePN as recap, valide
FROM   ModuleIUT;