/**
 * Permet de supprimer les données d'ASTRE
 * @author Alizéa LEBARON, Maxime LEMOINE
 * @version 1.1.0 - 15/01/2024
 * @date 11/12/2023
 */

DELETE FROM astre.Intervient  CASCADE; 
DELETE FROM astre.Horaire     CASCADE;
DELETE FROM astre.ModuleIUT   CASCADE;
DELETE FROM astre.Semestre    CASCADE;
DELETE FROM astre.Intervenant CASCADE;
DELETE FROM astre.Contrat     CASCADE;
DELETE FROM astre.Heure       CASCADE;
