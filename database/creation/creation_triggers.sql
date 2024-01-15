/**
 * Permet de créer les triggers d'ASTRE
 * @author Maxime LEMOINE
 * @version 2.1.0 - 15/01/2024
 * @date 11/12/2023
 */

-- pour la maj de l'historique

-- ne pas préciser (en préfixe) le placement des triggers dans le schéma car il y a des problèmes avec postgresql

CREATE TRIGGER tr_update
AFTER INSERT OR UPDATE OR DELETE
ON astre.Semestre
FOR EACH ROW
EXECUTE FUNCTION astre.f_update_historique ( );

CREATE TRIGGER tr_update
AFTER INSERT OR UPDATE OR DELETE
ON astre.Contrat
FOR EACH ROW
EXECUTE FUNCTION astre.f_update_historique ( );

CREATE TRIGGER tr_update
AFTER INSERT OR UPDATE OR DELETE
ON astre.Heure
FOR EACH ROW
EXECUTE FUNCTION astre.f_update_historique ( );

CREATE TRIGGER tr_update
AFTER INSERT OR UPDATE OR DELETE
ON astre.ModuleIUT
FOR EACH ROW
EXECUTE FUNCTION astre.f_update_historique ( );

CREATE TRIGGER tr_update
AFTER INSERT OR UPDATE OR DELETE
ON astre.Intervenant
FOR EACH ROW
EXECUTE FUNCTION astre.f_update_historique ( );

CREATE TRIGGER tr_update
AFTER INSERT OR UPDATE OR DELETE
ON astre.Intervient
FOR EACH ROW
EXECUTE FUNCTION astre.f_update_historique ( );

CREATE TRIGGER tr_update
AFTER INSERT OR UPDATE OR DELETE
ON astre.Horaire
FOR EACH ROW
EXECUTE FUNCTION astre.f_update_historique ( );

/*
-- MIEUX MAIS A DES PROBLEMES
DO $$ 
DECLARE
    nomTable TEXT;
BEGIN
    FOR nomTable IN ( SELECT table_name FROM information_schema.tables WHERE table_schema = 'astre' AND table_name != 'historique' ) 
    LOOP
        EXECUTE 'CREATE TRIGGER tr_update_astre_' || nomTable ||
                ' AFTER INSERT OR UPDATE OR DELETE ON astre.' || nomTable ||
                ' FOR EACH ROW EXECUTE FUNCTION astre.f_update_historique();';
    END LOOP;
END $$;
*/