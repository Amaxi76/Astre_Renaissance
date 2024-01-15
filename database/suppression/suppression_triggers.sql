/**
 * Permet de supprimer les triggers d'ASTRE
 * @author Maxime LEMOINE
 * @version 1.0.0 - 15/01/2024
 * @date 15/01/2024
 */

DO $$ 
DECLARE
    nomDeclencheur TEXT;
BEGIN
    FOR nomDeclencheur IN (SELECT trigger_name FROM information_schema.triggers WHERE trigger_schema = 'astre') 
    LOOP
        EXECUTE 'DROP TRIGGER IF EXISTS ' || nomDeclencheur;
    END LOOP;
END $$;
