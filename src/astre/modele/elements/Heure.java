package astre.modele.elements;

import javax.swing.JOptionPane;

/** Classe Heure 
  * @author : Maximilien Lesterlin
  * @version : 1.0 - 12/12/2023
  * @date : 06/12/2023
  */

public class Heure
{
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

		if ( ( i != null && ! ( i instanceof Integer ) ) ||  ! ( n instanceof String ) || ! ( c instanceof Number ) )
		{
			JOptionPane.showMessageDialog ( null, "Une des données n'est pas du bon type ou est vide.", "Création Impossible", JOptionPane.ERROR_MESSAGE );
			throw new IllegalArgumentException ( "Les données de l'heure ne sont pas du bon type" );
		}
		
		int    id   = ( i == null ) ? 0 : Integer.parseInt ( i.toString ( ) );
		double coef = Double.parseDouble ( c.toString ( ) );
		String nom  = n.toString ( );

		return Heure.creation ( id, nom, coef );
	}

	/** Crée une heure en prenant en paramètre un id, un nom et un coefficient équivalent TD.
	 * @return L'heure créée si les données ont une valeur et que le coefficient TD est positif.
	 */
	public static Heure creation ( int id, String nom, double coefTD )
	{
		if ( nom.equals ( "" ) )
		{
			JOptionPane.showMessageDialog ( null, "Une des données est vide.", "Création Impossible", JOptionPane.ERROR_MESSAGE );
			throw new IllegalArgumentException ( "Une des données est vide" );
		}
			
		if ( coefTD <= 0  )
		{
			JOptionPane.showMessageDialog ( null, "Le coef TP doit être supérieur à 0.", "Création Impossible", JOptionPane.ERROR_MESSAGE );
			throw new IllegalArgumentException ( "Le coef TP doit être supérieur à 0" );
		}
			
		return new Heure ( id, nom, coefTD );
	}

	/*---------------------------------------*/
	/*                GETTEUR                */
	/*---------------------------------------*/

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

}