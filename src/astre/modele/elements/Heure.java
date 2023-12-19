package astre.modele.elements;

/** Classe Heure 
  * @author : Maximilien Lesterlin
  * @version : 1.0 - 12/12/2023
  * @date : 06/12/2023
  */

// TODO: Faire en sorte que ça ne supprime pas toutes la base de donnée 

import java.util.ArrayList;
import java.util.List;

public class Heure
{
	private static List<Heure> ensHeure = new ArrayList<> ( );
	
	private int    id;
	private String nom;
	private double coefTd;

	/** Constructeur d'horaire
	 * @param id
	 * @param nom
	 * @param coefTd
	 */

	private Heure ( int id, String nom, double coefTd )
	{
		this.id     = id;
		this.nom    = nom;
		this.coefTd = coefTd;
	}

	/** Crée une heure en prenant en paramètre un tableau d'objet.
	 * @return L'heure créée si les données du tableau sont correctes.
	 */
	public static Heure creation ( Object[] heure )
	{
		Object i = heure[0];
		Object n = heure[1];
		Object c = heure[2];

		if ( ( i != null && !( i instanceof Integer ) ) ||  !( n instanceof String ) || !( c instanceof Number ) )
			throw new IllegalArgumentException ( "Les données de l'heure ne sont pas du bon type" );
		
		int    id   = ( i == null ) ? 0 : Integer.parseInt ( i.toString ( ) );
		double coef = Double.parseDouble ( c.toString ( ) );
		String nom  = n.toString ( );

		return Heure.creation ( id, nom, coef );
	}

	/** Crée une heure en prenant en paramètre un id, un nom et un coefficient équivalent TD.
	 * @return L'heure créée si les données sont remplies et que le coefficient TP est positif.
	 */
	public static Heure creation ( int id, String nom, double coefTD )
	{
		if ( nom.equals ( "" ) )
			throw new IllegalArgumentException ( "Une des données est vide" );

		//TODO: Demander confirmation au prof
		if ( coefTD <= 0  )
			throw new IllegalArgumentException ( "Le coef TP doit être supérieur à 0" );

		return new Heure ( id, nom, coefTD );
	}

	/*---------------------------------------*/
	/*                GETTEUR                */
	/*---------------------------------------*/

	/** Retourne la liste des heures
	 * @return ensHeure
	 */
	public static List<Heure> getHeures ( ) { return Heure.ensHeure;  }

	/** Retourne l'id d'une heure
	 * @return id
	 */
	public int    getId     ( ) { return this.id;     }

	/** Retourne le nom d'une heure
	 * @return nom
	 */
	public String getNom    ( ) { return this.nom;    }

	/** Retourne le coefficient équivalent TD d'une heure
	 * @return coefTd
	 */
	public double getCoefTd ( ) { return this.coefTd; }

	/*---------------------------------------*/
	/*                SETTEUR                */
	/*---------------------------------------*/
	
	/** Permet de modifier le nom
	 * @param nom the nom to set
	 */
	public void setNom    ( String nom    ) { this.nom    = nom;    }

	/** Permet de modifier le coefficient équivalent TD
	 * @param coefTd the coefTd to set
	 */
	public void setCoefTd ( double coefTd ) { this.coefTd = coefTd; }

	/*---------------------------------------*/
	/*                METHODES               */
	/*---------------------------------------*/

	/** Retire un élément de la liste heure
	 * @return true si la liste contient l'élément, sinon false
	 */
	public boolean retirerListe ( )
	{
		if ( ! Heure.ensHeure.contains ( this ) ) return false;
		
		Heure.ensHeure.remove ( this );
		return true;
	}
	
}