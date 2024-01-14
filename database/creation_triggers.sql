

-- Pour les intervenants
CREATE TRIGGER tr_update_Intervenant
AFTER INSERT OR UPDATE OR DELETE
ON Intervenant
FOR EACH ROW
EXECUTE FUNCTION f_update_historique_Intervenant();

-- Pour les Semestre
CREATE TRIGGER tr_update_Semestre
AFTER INSERT OR UPDATE OR DELETE
ON Semestre
FOR EACH ROW
EXECUTE FUNCTION f_update_historique_Semestre();

-- Pour les Contrat
CREATE TRIGGER tr_update_Contrat
AFTER INSERT OR UPDATE OR DELETE
ON Contrat
FOR EACH ROW
EXECUTE FUNCTION f_update_historique_Contrat();

-- Pour les heures
CREATE TRIGGER tr_update_Heure
AFTER INSERT OR UPDATE OR DELETE
ON Heure
FOR EACH ROW
EXECUTE FUNCTION f_update_historique_Heure();

-- Pour les modules
CREATE TRIGGER tr_update_ModuleIUT
AFTER INSERT OR UPDATE OR DELETE
ON ModuleIUT
FOR EACH ROW
EXECUTE FUNCTION f_update_historique_ModuleIUT();

-- Pour intervient
CREATE TRIGGER tr_update_Intervient
AFTER INSERT OR UPDATE OR DELETE
ON Intervient
FOR EACH ROW
EXECUTE FUNCTION f_update_historique_Intervient();

-- Pour horaire
CREATE TRIGGER tr_update_Horaire
AFTER INSERT OR UPDATE OR DELETE
ON Horaire
FOR EACH ROW
EXECUTE FUNCTION f_update_historique_Horaire();