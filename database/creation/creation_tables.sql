/**
 * Permet de créer toutes les tables d'ASTRE
 * @author Alizéa LEBARON, Maxime LEMOINE
 * @version 3.0.0 - 20/02/2024
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
	idAnnee     INTEGER NOT NULL          ,
	idSemestre  SERIAL                    ,
	nbGroupesTP INTEGER NOT NULL DEFAULT 0,
	nbGroupesTD INTEGER NOT NULL DEFAULT 0,
	nbEtudiants INTEGER NOT NULL DEFAULT 0,
	nbSemaines  INTEGER NOT NULL DEFAULT 0,

	PRIMARY KEY ( idAnnee, idSemestre ),
	FOREIGN KEY ( idAnnee             ) REFERENCES astre.Annee ( idAnnee )
);

CREATE TABLE astre.Contrat
(
	idAnnee          INTEGER          NOT NULL          ,
	idContrat        SERIAL                             ,
	nom              VARCHAR ( 50   ) NOT NULL          ,
	nbHeuresService  DECIMAL ( 6, 2 ) NOT NULL DEFAULT 0,
	maxHeuresService DECIMAL ( 6, 2 ) NOT NULL DEFAULT 0,
	coefTP           DOUBLE PRECISION NOT NULL DEFAULT 1,

	PRIMARY KEY ( idAnnee, idContrat ),
	FOREIGN KEY ( idAnnee            ) REFERENCES astre.Annee ( idAnnee )
);

CREATE TABLE astre.Heure
(
	idAnnee  INTEGER          NOT NULL          ,
	idHeure  SERIAL                             ,
	nom      VARCHAR ( 20 )   NOT NULL          ,
	coefEQTD DOUBLE PRECISION NOT NULL DEFAULT 1,

	PRIMARY KEY ( idAnnee, idHeure ),
	FOREIGN KEY ( idAnnee          ) REFERENCES astre.Annee ( idAnnee )
);

CREATE TABLE astre.ModuleIUT
(
	idAnnee              INTEGER           NOT NULL              , /*utile pour rendre unique la table des modules*/
	idModuleIUT          SERIAL                                  ,
	idSemestre           INTEGER           NOT NULL              ,
	code                 VARCHAR ( 5  )    NOT NULL              ,
	libLong              VARCHAR ( 60 )    NOT NULL              ,
	libCourt             VARCHAR ( 15 )    NOT NULL              ,
	typeModule           VARCHAR ( 20 )    NOT NULL              ,
	valide               BOOLEAN           NOT NULL DEFAULT false,
	totalHeuresPN        DECIMAL ( 10, 2 ) NOT NULL DEFAULT 0    ,
	totalHeuresAffectees DECIMAL ( 10, 2 ) NOT NULL DEFAULT 0    ,

	PRIMARY KEY ( idAnnee, idModuleIUT ),
	FOREIGN KEY ( idAnnee              ) REFERENCES astre.Annee    ( idAnnee    ),
	FOREIGN KEY ( idSemestre           ) REFERENCES astre.Semestre ( idSemestre )
);

CREATE TABLE astre.Intervenant
(
	idAnnee          INTEGER          NOT NULL          ,
	idIntervenant    SERIAL                             ,
	nom              VARCHAR ( 50 )   NOT NULL          ,
	prenom           VARCHAR ( 50 )   NOT NULL          ,
	nbHeuresService  DECIMAL ( 6, 2 ) NOT NULL DEFAULT 0,
	maxHeuresService DECIMAL ( 6, 2 ) NOT NULL DEFAULT 0,
	coefTP           DOUBLE PRECISION NOT NULL          ,
	idContrat        INTEGER          NOT NULL          ,

	PRIMARY KEY ( idAnnee, idIntervenant ),
	FOREIGN KEY ( idAnnee                ) REFERENCES astre.Annee   ( idAnnee   ),
	FOREIGN KEY ( idContrat              ) REFERENCES astre.Contrat ( idContrat )
);

CREATE TABLE astre.Intervient
(
	idAnnee       INTEGER          NOT NULL          ,
	idIntervenant INTEGER          NOT NULL          ,
	idHeure       INTEGER          NOT NULL          ,
	idModuleIUT   INTEGER          NOT NULL          ,
	nbSemaines    INTEGER          NOT NULL DEFAULT 0,
	nbGroupes     INTEGER          NOT NULL DEFAULT 0,
	nbHeures      DECIMAL ( 6, 2 ) NOT NULL DEFAULT 0,
	commentaire   VARCHAR ( 50 )   NOT NULL          ,

	PRIMARY KEY ( idAnnee, idIntervenant, idHeure, idModuleIUT ),
	FOREIGN KEY ( idAnnee                                      ) REFERENCES astre.Annee       ( idAnnee       ),
	FOREIGN KEY ( idIntervenant                                ) REFERENCES astre.Intervenant ( idIntervenant ),
	FOREIGN KEY ( idHeure                                      ) REFERENCES astre.Heure       ( idHeure       ),
	FOREIGN KEY ( idModuleIUT                                  ) REFERENCES astre.ModuleIUT   ( idModuleIUT   )
);

CREATE TABLE astre.Horaire
(
	idAnnee         INTEGER          NOT NULL          ,
	idHeure         INTEGER          NOT NULL          ,
	idModuleIUT     INTEGER          NOT NULL          ,
	nbHeuresPN      DECIMAL ( 6, 2 ) NOT NULL DEFAULT 0,
	nbHeureSemaines DECIMAL ( 6, 2 ) NOT NULL DEFAULT 0,
	nbSemaines      INTEGER          NOT NULL DEFAULT 0,
	
	PRIMARY KEY ( idAnnee, idHeure, idModuleIUT ),
	FOREIGN KEY ( idAnnee                       ) REFERENCES astre.Annee     ( idAnnee     ),
	FOREIGN KEY ( idHeure                       ) REFERENCES astre.Heure     ( idHeure     ),
	FOREIGN KEY ( idModuleIUT                   ) REFERENCES astre.ModuleIUT ( idModuleIUT )
);

CREATE TABLE astre.Historique
(
	idAnnee          INTEGER          NOT NULL                   ,
	idHistorique     SERIAL                                      ,
	dateModification TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	commentaire      TEXT      NOT NULL                          ,

	PRIMARY KEY ( idAnnee, idHistorique ),
	FOREIGN KEY ( idAnnee               ) REFERENCES astre.Annee ( idAnnee )
);