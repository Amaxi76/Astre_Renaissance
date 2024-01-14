package astre.modele.elements;

/** Classe Heure
  * @author : Maximilien Lesterlin
  * @version : 2.0 - 13/01/2024
  * @date : 06/12/2023
  */

public class Heure
{
	private int    id;
	private String nom;
	private double coefTd;

	/** Constructeur d'horaire
	 * @param id l'indentifiant de l'horaire
	 * @param nom le nom de l'horaire
	 * @param coefTd le coefficient équivalent TD de l'horaire
	 */

	private Heure ( int id, String nom, double coefTd )
	{
		this.id     = id;
		this.nom    = nom;
		this.coefTd = coefTd;
	}


	/*---------------------------------------*/
	/*                FACTORY                */
	/*---------------------------------------*/

	/** Crée une heure en prenant en paramètre un tableau d'objet.
	 * @return L'heure créée si les données du tableau sont correctes.
	 */
	public static Heure creation ( Object[] heure )
	{
		Object i = heure[0];
		Object n = heure[1];
		Object c = heure[2];

		if ( ! ( i instanceof Integer ) ) throw new IllegalArgumentException ( "L'identifiant n'est pas du bon type"  );
		if ( ! ( n instanceof String  ) ) throw new IllegalArgumentException ( "Le nom n'est pas du bon type"         );
		if ( ! ( c instanceof Number  ) ) throw new IllegalArgumentException ( "Le coefficient n'est pas du bon type" );
		
		int    id   = ( int ) i;
		double coef = Double.parseDouble ( c.toString ( ) );
		String nom  = n.toString ( );

		return Heure.creation ( id, nom, coef );
	}

	/** Crée une heure en prenant en paramètre un id, un nom et un coefficient équivalent TD.
	 * @return L'heure créée si les données ont une valeur et que le coefficient TD est positif.
	 */
	public static Heure creation ( int id, String nom, double coefTD )
	{
		// Teste si le nom
		if ( nom.equals ( "" ) ) throw new IllegalArgumentException ( "Une des données est vide"           );
		if ( coefTD <= 0       ) throw new IllegalArgumentException ( "Le coef TP doit être supérieur à 0" );
			
		return new Heure ( id, nom, coefTD );
	}


	/*---------------------------------------*/
	/*                GETTEUR                */
	/*---------------------------------------*/

	/** Retourne l'id d'une heure
	 * @return l'indentifiant
	 */
	public int    getId     ( ) { return this.id;     }

	/** Retourne le nom de l'heure
	 * @return nom de l'heure
	 */
	public String getNom    ( ) { return this.nom;    }

	/** Retourne le coefficient équivalent TD d'une heure
	 * @return le coefficient équivalent TD
	 */
	public double getCoefTd ( ) { return this.coefTd; }


	/*---------------------------------------*/
	/*                SETTEUR                */
	/*---------------------------------------*/
	
	/** Permet de modifier le nom
	 * @param nom le nom à modifier
	 */
	public void setNom    ( String nom    ) { this.nom    = nom;    }

	/** Permet de modifier le coefficient équivalent TD
	 * @param coefTd le nom à modifier
	 */
	public void setCoefTd ( double coefTd ) { this.coefTd = coefTd; }

	
	/*---------------------------------------*/
	/*                METHODES               */
	/*---------------------------------------*/

	/** Indique si deux contrats sont égaux
	 * @return true si les deux contrats sont égaux, false sinon
	 */
	@Override
	public boolean equals ( Object o )
	{
		if ( ! ( o instanceof Heure ) ) return false;
		if ( o == this                ) return true;

		Heure c = ( Heure ) o;

		return this.id                  == c.id      &&
		       this.nom.equals ( c.nom )             &&
		       this.coefTd              == c.coefTd;
	}

	/** Donne une description d'un contrat
	 * @return
	 */
	@Override
	public String toString ( )
	{
		return String.format ( "Heure%nIdentifiant : %d%nNom         : %-22s%nCoef TD     : %,.2f", this.id, this.nom, this.coefTd );
	}
}