package astre.modele.elements;

/**
 * Classe permettant de contenir les classes du paquetage "element".
 * @author : Maxime Lemoine
 */
public abstract class Element
{
	/**
	 * @param element : Ensemble des attributs d'une classe élément sous forme d'un tableau d'objet
	 * @return : instance de la classe élément correspondante
	 */
	public Element creation ( Object[] element )
	{
		return null;
	}

	public boolean equals ( Object object )
	{
		return true;
	}

//mettre le "throws Erreur" dans la signature d méthode ?
}