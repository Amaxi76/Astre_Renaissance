/**
 * Permet de créer les triggers d'ASTRE
 * @author Maxime LEMOINE
 * @version 2.0.0 - 14/01/2024
 * @date 11/12/2023
 * @description 
 */

-- pour la maj de l'historique
CREATE TRIGGER tr_update_ASTRE
AFTER INSERT OR UPDATE OR DELETE
ON Semestre
FOR EACH ROW
EXECUTE FUNCTION f_update_historique ( );

CREATE TRIGGER tr_update_ASTRE
AFTER INSERT OR UPDATE OR DELETE
ON Contrat
FOR EACH ROW
EXECUTE FUNCTION f_update_historique ( );

CREATE TRIGGER tr_update_ASTRE
AFTER INSERT OR UPDATE OR DELETE
ON Heure
FOR EACH ROW
EXECUTE FUNCTION f_update_historique ( );

CREATE TRIGGER tr_update_ASTRE
AFTER INSERT OR UPDATE OR DELETE
ON ModuleIUT
FOR EACH ROW
EXECUTE FUNCTION f_update_historique ( );

CREATE TRIGGER tr_update_ASTRE
AFTER INSERT OR UPDATE OR DELETE
ON Intervenant
FOR EACH ROW
EXECUTE FUNCTION f_update_historique ( );

CREATE TRIGGER tr_update_ASTRE
AFTER INSERT OR UPDATE OR DELETE
ON Intervient
FOR EACH ROW
EXECUTE FUNCTION f_update_historique ( );

CREATE TRIGGER tr_update_ASTRE
AFTER INSERT OR UPDATE OR DELETE
ON Horaire
FOR EACH ROW
EXECUTE FUNCTION f_update_historique ( );