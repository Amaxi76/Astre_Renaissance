/* Création de vue */

-- Vue intervenant
CREATE VIEW v_Intervenant AS
SELECT
		Id_Intervenant,
		nomContrat,
		nom,
		prenom,
		hService,
		hMax,
		f_conversion ( c.Id_Contrat ) AS ratioTP,
		f_selectNBHeureParSemestre ( 1, Id_Intervenant  ) AS s1,
		f_selectNBHeureParSemestre ( 3, Id_Intervenant  ) AS s3,
		f_selectNBHeureParSemestre ( 5, Id_Intervenant  ) AS s5,
		f_selectNBHeureParSemestreImpair(Id_Intervenant ) AS totImp,
		f_selectNBHeureParSemestre ( 2, Id_Intervenant  ) AS s2,
		f_selectNBHeureParSemestre ( 4, Id_Intervenant  ) AS s4,
		f_selectNBHeureParSemestre ( 6, Id_Intervenant  ) AS s6,
		f_selectNBHeureParSemestrePair(Id_Intervenant)    AS totPai,
		f_selectNBHeureParSemestreTot(Id_Intervenant)     AS total
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