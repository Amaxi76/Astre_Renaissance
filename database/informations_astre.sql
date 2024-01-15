/**
 * Permet d'afficher des informations utiles sur la database d'ASTRE
 * @author Maxime LEMOINE
 * @version 1.0.0 - 15/01/2024
 * @date 15/01/2024
 */

SELECT table_name AS "Tables"
FROM information_schema.tables
WHERE table_schema = 'astre' AND table_type = 'BASE TABLE';

SELECT table_name AS "Vues"
FROM information_schema.tables
WHERE table_schema = 'astre' AND table_type = 'VIEW';

SELECT routine_name AS "Fonctions"
FROM information_schema.routines
WHERE routine_schema = 'astre' AND routine_type = 'FUNCTION';

SELECT DISTINCT trigger_name AS "Triggers"
FROM information_schema.triggers
WHERE trigger_schema = 'astre';