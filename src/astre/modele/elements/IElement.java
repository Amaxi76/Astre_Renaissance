package astre.modele.elements;

/**
 * Classe permettant de contenir les classes du paquetage "element".
 * @author : Maxime Lemoine
 */
public interface IElement
{
    /**
     * @param element : Ensemble des attributs d'une classe élément sous forme d'un tableau d'objet
     * @return : instance de la classe élément correspondante
     */
    public IElement créer( Object[] element );
//mettre le "throws Erreur" dans la signature d méthode ?
}