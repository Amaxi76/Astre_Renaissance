/**
 * Permet de créer les fonctions (update) d'ASTRE
 * @author Maxime LEMOINE
 * @version 2.0.0 - 14/01/2024
 * @date 12/12/2023 
 */

-- fonction liée au trigger pour la gestion de l'historique
--TODO: améliorer l'affichage
CREATE OR REPLACE FUNCTION astre.f_updateHistorique ( )
RETURNS TRIGGER AS
$$
DECLARE
	commentaire TEXT;
BEGIN
	IF TG_OP = 'INSERT' THEN
		commentaire := 'Création de ' || TG_TABLE_NAME || ' : ' || to_jsonb ( NEW );
	ELSIF TG_OP = 'UPDATE' THEN
		commentaire := 'Mise à jour de ' || TG_TABLE_NAME || ' : ' || to_jsonb ( OLD ) || ' -> ' || to_jsonb ( NEW );
	ELSIF TG_OP = 'DELETE' THEN
		commentaire := 'Suppression de ' || TG_TABLE_NAME || ' : ' || to_jsonb ( OLD );
	END IF;

	INSERT INTO astre.Historique ( commentaire ) VALUES ( commentaire );

	RETURN NEW;
END;
$$ LANGUAGE plpgsql;



/*
CE QUI EST DESSOUS A ETE COPIE COLLE DEPUIS L'ANCIEN FICHIER
FIXME: ajouter le préfixe "astre." du schéma
FIXME: maj des paramètres (noms et nombre)
FIXME: vérifier l'utilité de chaque fonction
FIXME: factoriser ?
*/



-- Update de semestre
DROP FUNCTION IF EXISTS f_updateSemestre ( u_Id_Semestre INTEGER, u_nbGroupeTP INTEGER, u_nbGroupeTD INTEGER, u_nbEtud INTEGER, u_nbSemaine INTEGER );
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
DROP FUNCTION IF EXISTS f_updateAnneeSemestre ( );
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
DROP FUNCTION IF EXISTS f_updateContrat ( u_id_contrat INTEGER, u_nomContrat VARCHAR(50), u_hServiceContrat INTEGER, u_hMaxContrat INTEGER, u_ratioTP DOUBLE PRECISION );
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
DROP FUNCTION IF EXISTS f_updateHeure ( u_Id_Heure INTEGER, u_nomHeure VARCHAR(50), u_coeffTD DOUBLE PRECISION );
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
DROP FUNCTION IF EXISTS f_updateModuleIUT ( u_Code_ModuleIUT VARCHAR(50), u_libLong VARCHAR(60), u_libCourtModuleIUT VARCHAR(15), u_typeModule VARCHAR(20), u_Id_Semestre INTEGER );
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
DROP FUNCTION IF EXISTS f_updateIntervenant ( u_Id_Intervenant INTEGER, u_nom VARCHAR(50), u_prenom VARCHAR(50), u_hService INTEGER, u_hMax INTEGER, u_Id_Contrat INTEGER );
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
DROP FUNCTION IF EXISTS f_updateIntervient ( u_Id_Intervenant INTEGER, u_Id_Heure INTEGER, u_Code_ModuleIUT VARCHAR(5), u_nbSemaine INTEGER, u_nbGroupe INTEGER, u_nbHeure INTEGER, u_commentaire VARCHAR(50) );
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
DROP FUNCTION IF EXISTS f_updateHoraire ( u_Id_Heure VARCHAR(50), u_Code_ModuleIUT VARCHAR(5), u_nbHeurePN INTEGER, u_nbHeureRepartie INTEGER, u_nbSemaine INTEGER );
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
