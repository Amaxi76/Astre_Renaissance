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
/*CREATE VIEW astre.v_Intervenant AS
SELECT
	i.idIntervenant,
	c.nom AS nomContrat,
	i.nom,
	i.prenom,
	i.nbHeureService AS hServ,
	i.maxHeureService AS hMax,
	astre.f_conversion ( c.idContrat ) AS ratioTP,
	astre.f_selectNBHeureParSemestre       ( 1, idIntervenant  ) AS s1,
	astre.f_selectNBHeureParSemestre       ( 3, idIntervenant  ) AS s3,
	astre.f_selectNBHeureParSemestre       ( 5, idIntervenant  ) AS s5,
	astre.f_selectNBHeureParSemestreImpair ( idIntervenant     ) AS totImp,
	astre.f_selectNBHeureParSemestre       ( 2, idIntervenant  ) AS s2,
	astre.f_selectNBHeureParSemestre       ( 4, idIntervenant  ) AS s4,
	astre.f_selectNBHeureParSemestre       ( 6, idIntervenant  ) AS s6,
	astre.f_selectNBHeureParSemestrePair   ( idIntervenant     ) AS totPai,
	astre.f_selectNBHeureParSemestreTot    ( idIntervenant     ) AS total
FROM
	astre.Intervenant i
	JOIN astre.Contrat c ON i.idContrat = c.idContrat
ORDER BY
	idIntervenant ASC;*/

-- Vue des modules
DROP VIEW IF EXISTS v_Module;
CREATE OR REPLACE VIEW v_Module AS
SELECT idSemestre, code, libLong, totalHeureAffectee || '/' || totalHeurePN AS recap, valide
FROM   astre.ModuleIUT;