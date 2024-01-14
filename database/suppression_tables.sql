/*
	@author Maxime LEMOINE
	@version 1.0.0 - 14/01/2024
	@date 14/01/2024
	@description Script permettant de drop la database
*/

/* ---------------------------------------------------- */
/*          Suppression des tables existantes           */
/* ---------------------------------------------------- */ 

DROP TABLE Annee       CASCADE;
DROP TABLE Semestre    CASCADE;
DROP TABLE Contrat     CASCADE;
DROP TABLE Heure       CASCADE;
DROP TABLE ModuleIUT   CASCADE;
DROP TABLE Intervenant CASCADE;
DROP TABLE Intervient  CASCADE;
DROP TABLE Horaire     CASCADE;
DROP TABLE Historique  CASCADE;