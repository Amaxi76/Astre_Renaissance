/**
 * Permet de créer toutes les tables d'ASTRE
 * @author Alizéa LEBARON, Maxime LEMOINE
 * @version 2.1.0 - 15/01/2024
 * @date 06/12/2023 
 */

CREATE TABLE astre.Annee
(
	idAnnee  SERIAL                                          ,
	nom      VARCHAR ( 30 ) NOT NULL DEFAULT 'Nouvelle année',
	actuelle BOOLEAN        NOT NULL DEFAULT false           ,

	PRIMARY KEY ( idAnnee )
);

CREATE TABLE astre.Semestre
(
	idSemestre SERIAL                    ,
	nbGroupeTP INTEGER NOT NULL DEFAULT 0,
	nbGroupeTD INTEGER NOT NULL DEFAULT 0,
	nbEtudiant INTEGER NOT NULL DEFAULT 0,
	nbSemaine  INTEGER NOT NULL DEFAULT 0,

	PRIMARY KEY ( idSemestre )
);

CREATE TABLE astre.Contrat
(
	idContrat       SERIAL                             ,
	nom             VARCHAR ( 50   ) NOT NULL          ,
	nbHeureService  DECIMAL ( 6, 2 ) NOT NULL DEFAULT 0,
	maxHeureService DECIMAL ( 6, 2 ) NOT NULL DEFAULT 0,
	coefTP          DOUBLE PRECISION NOT NULL DEFAULT 1,

	PRIMARY KEY ( idContrat )
);

CREATE TABLE astre.Heure
(
	idHeure  SERIAL                             ,
	nom      VARCHAR ( 20 )   NOT NULL          ,
	coefEQTD DOUBLE PRECISION NOT NULL DEFAULT 1,

	PRIMARY KEY ( idHeure )
);

CREATE TABLE astre.ModuleIUT
(
	idModuleIUT        SERIAL                                  ,
	code               VARCHAR ( 5  )    NOT NULL              ,
	libLong            VARCHAR ( 60 )    NOT NULL              ,
	libCourt           VARCHAR ( 15 )    NOT NULL              ,
	typeModule         VARCHAR ( 20 )    NOT NULL              ,
	valide             BOOLEAN           NOT NULL DEFAULT false,
	idSemestre         INTEGER           NOT NULL              ,
	totalHeurePN       DECIMAL ( 10, 2 ) NOT NULL DEFAULT 0    ,
	totalHeureAffectee DECIMAL ( 10, 2 ) NOT NULL DEFAULT 0    ,

	PRIMARY KEY ( idModuleIUT ),
	FOREIGN KEY ( idSemestre ) REFERENCES astre.Semestre ( idSemestre )
);

CREATE TABLE astre.Intervenant
(
	idIntervenant   SERIAL                             ,
	nom             VARCHAR ( 50 )   NOT NULL          ,
	prenom          VARCHAR ( 50 )   NOT NULL          ,
	nbHeureService  DECIMAL ( 6, 2 ) NOT NULL DEFAULT 0,
	maxHeureService DECIMAL ( 6, 2 ) NOT NULL DEFAULT 0,
	coefTP          DOUBLE PRECISION NOT NULL          ,
	idContrat       INTEGER          NOT NULL          ,

	PRIMARY KEY ( idIntervenant ),
	FOREIGN KEY ( idContrat ) REFERENCES astre.Contrat ( idContrat )
);

CREATE TABLE astre.Intervient
(
	idIntervenant INTEGER          NOT NULL          ,
	idHeure       INTEGER          NOT NULL          ,
	idModuleIUT   INTEGER          NOT NULL          ,
	nbSemaine     INTEGER          NOT NULL DEFAULT 0,
	nbGroupe      INTEGER          NOT NULL DEFAULT 0,
	nbHeure       DECIMAL ( 6, 2 ) NOT NULL DEFAULT 0,
	commentaire   VARCHAR ( 50 )   NOT NULL          ,

	PRIMARY KEY ( idIntervenant, idHeure, idModuleIUT ),
	FOREIGN KEY ( idIntervenant ) REFERENCES astre.Intervenant ( idIntervenant ),
	FOREIGN KEY ( idHeure       ) REFERENCES astre.Heure       ( idHeure       ),
	FOREIGN KEY ( idModuleIUT   ) REFERENCES astre.ModuleIUT   ( idModuleIUT   )
);

CREATE TABLE astre.Horaire
( 
	idHeure         INTEGER          NOT NULL          ,
	idModuleIUT     INTEGER          NOT NULL          ,
	nbHeurePN       DECIMAL ( 6, 2 ) NOT NULL DEFAULT 0,
	nbHeureSemaine  DECIMAL ( 6, 2 ) NOT NULL DEFAULT 0,
	nbSemaine       INTEGER          NOT NULL DEFAULT 0,
	
	PRIMARY KEY ( idHeure, idModuleIUT ),
	FOREIGN KEY ( idHeure     ) REFERENCES astre.Heure     ( idHeure     ),
	FOREIGN KEY ( idModuleIUT ) REFERENCES astre.ModuleIUT ( idModuleIUT )
);

CREATE TABLE astre.Historique
(
	idHistorique     SERIAL                                      ,
	dateModification TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	commentaire      TEXT      NOT NULL                          ,

	PRIMARY KEY ( idHistorique )
);