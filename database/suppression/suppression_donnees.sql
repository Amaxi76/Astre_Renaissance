/**
 * Permet de supprimer les données d'ASTRE
 * @author Alizéa LEBARON, Maxime LEMOINE
 * @version 1.0.0 - 14/01/2024
 * @date 11/12/2023
 */

DELETE FROM Intervient  CASCADE; 
DELETE FROM Horaire     CASCADE;
DELETE FROM ModuleIUT   CASCADE;
DELETE FROM Semestre    CASCADE;
DELETE FROM Intervenant CASCADE;
DELETE FROM Contrat     CASCADE;
DELETE FROM Heure       CASCADE;
