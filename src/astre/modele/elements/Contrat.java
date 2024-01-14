package astre.modele.elements;

/** Classe Contrat
  * @author : Maximilien Lesterlin, Alizéa Lebaron
  * @version : 2.0 - 13/01/2024
  * @date : 06/12/2023
  */

public class Contrat
{
	private int    id;
	private String nom;
	private int    heureServiceContrat;
	private int    heureMaxContrat;
	private double ratioTP;

	/** Constructeur unique de contrat
	 * @param id identifiant du contrat
	 * @param nom nom du contrat
	 * @param heureServiceContrat nombre d'heure de service du contrat
	 * @param heureMaxContrat nombre d'heure maximum du contrat
	 * @param ratioTP ratio TP du contrat
	 */
	private Contrat ( int id, String nom, int heureServiceContrat, int heureMaxContrat, double ratioTP )
	{
		this.id                  = id;
		this.nom                 = nom;
		this.heureServiceContrat = heureServiceContrat;
		this.heureMaxContrat     = heureMaxContrat;
		this.ratioTP             = ratioTP;
	}
	

	/*---------------------------------------*/
	/*                FACTORY                */
	/*---------------------------------------*/

	/** Fabrique de contrat à partir d'un tableau
	 * @param contrat Prend un tableau d'objet définissant le contrat
	 * @return Le contrat contenu dans le tableau d'objet
	 */
	public static Contrat creation ( Object[] contrat )
	{
		Object i   = contrat[0];
		Object n   = contrat[1];
		Object hsc = contrat[2];
		Object hmc = contrat[3];
		Object rt  = contrat[4];

		if ( ! ( i   instanceof Integer ) ) throw new IllegalArgumentException ( "L'identifiant n'est pas du bon type"                    );
		if ( ! ( n   instanceof String  ) ) throw new IllegalArgumentException ( "Le nom n'est pas du bon type"                           );
		if ( ! ( hsc instanceof Integer ) ) throw new IllegalArgumentException ( "Le nombre d'heures de services ne sont pas du bon type" );
		if ( ! ( hmc instanceof Integer ) ) throw new IllegalArgumentException ( "Le nombre d'heures max ne sont pas du bon type"         );
		if ( ! ( rt  instanceof Number  ) ) throw new IllegalArgumentException ( "Le ratio TP n'est pas du bon type"                      );
		
		int    id                  = ( int ) i;
		int    heureServiceContrat = ( int ) hsc;
		int    heureMaxContrat     = ( int ) hmc;
		String nom                 = n.toString ( );
		double ratioTP             = Double.parseDouble ( rt.toString ( ) );

		return Contrat.creation ( id, nom, heureServiceContrat, heureMaxContrat, ratioTP );
	}

	/** Fabrique de contrat prenant en paramètres toutes les données d'un contrat
	 * @param id l'indentifiant du contrat
	 * @param nom le nom du contrat
	 * @param heureServiceContrat le nombre d'heure de service du contrat
	 * @param heureMaxContrat le nombre d'heure maximum du contrat
	 * @param ratioTP le ratio TP du contrat
	 * @return Le contrat en question
	 */
	public static Contrat creation ( int id, String nom, int heureServiceContrat, int heureMaxContrat, double ratioTP )
	{
		// Teste la validité du nom (non vide)
		if ( nom.equals ( "" )                     ) throw new IllegalArgumentException ( "Le nom du contrat n'est pas rempli"                     );
			
		// Teste la validité des heures du service du contrat pour qu'elles soient supérieur à 0
		if ( heureServiceContrat < 0               ) throw new IllegalArgumentException ( "Les heures de services doivent être supérieur à 0"      );

		// Teste la validité des heures max du contrat pour qu'elles soient supérieur à 0
		if ( heureMaxContrat     <= 0              ) throw new IllegalArgumentException ( "Les heures max doivent être supérieur à 0"              );

		// Teste si les heures de services sont supérieur aux heures max
		if ( heureServiceContrat > heureMaxContrat ) throw new IllegalArgumentException ( "Les heures de services sont supérieur à ses heures max" );

		// Coef TD
		if ( ratioTP <= 0 )                          throw new IllegalArgumentException ( "Le coef TD ne peut pas être de 0"                       );
		
		return new Contrat ( id, nom, heureServiceContrat, heureMaxContrat, ratioTP );
	}


	/*---------------------------------------*/
	/*                GETTEUR                */
	/*---------------------------------------*/

	/** Retourne l'identifiant d'un contrat
	 * @return L'identifiant du contrat
	 */
	public int    getId                  ( ) { return this.id;                  }

	/** Retourne le nom d'un contrat
	 * @return Le nom du contrat
	 */
	public String getNom                 ( ) { return this.nom;                 }

	/** Retourne le nombre d'heure service d'un contrat
	 * @return Le nombre d'heure service du contrat
	 */
	public int    getHeureServiceContrat ( ) { return this.heureServiceContrat; }

	/** Retourne le nombre d'heure maximales d'un contrat
	 * @return Le nombre d'heure maximales du contrat
	 */
	public int    getHeureMaxContrat     ( ) { return this.heureMaxContrat;     }

	/** Retourne le ratio TP d'un contrat
	 * @return Le ratio TP du contrat
	 */
	public double getRatioTP             ( ) { return this.ratioTP;             }


	/*---------------------------------------*/
	/*                SETTEUR                */
	/*---------------------------------------*/

	/** Permet de modifier l'ID
	 * @param id l'ID
	 */
	public void setId                  ( int    id                  ) { this.id                  = id;                  }
	
	/** Permet de modifier le nom d'un contrat
	 * @param nom le nom du contrat
	 */
	public void setNom                 ( String nom                 ) { this.nom                 = nom;                 }

	/** Permet de modifier les heure de service d'un contrat
	 * @param heureServiceContrat le nombre d'heure de service
	 */
	public void setHeureServiceContrat ( int    heureServiceContrat ) { this.heureServiceContrat = heureServiceContrat; }

	/** Permet de modifier le nombre d'heure maximum d'un contrat
	 * @param heureMaxContrat le nombre d'heure maximum
	 */
	public void setHeureMaxContrat     ( int    heureMaxContrat     ) { this.heureMaxContrat     = heureMaxContrat;     }

	/** Permet de modifier le ratio TP d'un contrat 
	 * @param ratioTP le ratio TP
	 */
	public void setRatioTP             ( double ratioTP             ) { this.ratioTP             = ratioTP;             }



	/*---------------------------------------*/
	/*                METHODES               */
	/*---------------------------------------*/

	/** Indique si deux contrats sont égaux
	 * @return true si les deux contrats sont égaux, false sinon
	 */
	@Override
	public boolean equals ( Object o )
	{
		if ( ! ( o instanceof Contrat ) ) return false;
		if ( o == this                  ) return true;

		Contrat c = ( Contrat ) o;

		return this.id                  == c.id                  &&
		       this.nom.equals ( c.nom )                         &&
		       this.heureServiceContrat == c.heureServiceContrat &&
		       this.heureMaxContrat     == c.heureMaxContrat     &&
		       this.ratioTP             == c.ratioTP;
	}

	/** Donne une description d'un contrat
	 * @return
	 */
	@Override
	public String toString ( )
	{
		return String.format( "Contrat%nNom                   : %-22s%nHeure service contrat : %02d%nHeure max contrat     : %02d%nRatio TP              : %,.2f", this.nom, this.heureServiceContrat, this.heureMaxContrat, this.ratioTP );
	}
}