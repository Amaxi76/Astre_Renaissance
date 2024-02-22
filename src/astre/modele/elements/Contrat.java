package astre.modele.elements;

/** Classe Contrat
  * @author : Maximilien Lesterlin, Alizéa Lebaron
  * @version : 2.0 - 13/01/2024
  * @date : 06/12/2023
  */

public class Contrat
{
	private int    idAnnee;
	private int    id;
	private String nom;
	private double heureServiceContrat;
	private double heureMaxContrat;
	private double ratioTP;

	/** Constructeur unique de contrat
	 * @param idAnnee identifiant de l'année
	 * @param id identifiant du contrat
	 * @param nom nom du contrat
	 * @param heureServiceContrat nombre d'heure de service du contrat
	 * @param heureMaxContrat nombre d'heure maximum du contrat
	 * @param ratioTP ratio TP du contrat
	 */
	private Contrat ( int idAnnee ,int id, String nom, double heureServiceContrat, double heureMaxContrat, double ratioTP )
	{;
		this.idAnnee             = idAnnee;
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
		Object idA = contrat[0];
		Object i   = contrat[1];
		Object n   = contrat[2];
		Object hsc = contrat[3];
		Object hmc = contrat[4];
		Object rt  = contrat[5];

		if ( ! ( idA instanceof Integer ) ) throw new IllegalArgumentException ( "L'identifiant de l'année n'est pas du bon type"         );
		if ( ! ( i   instanceof Integer ) ) throw new IllegalArgumentException ( "L'identifiant n'est pas du bon type"                    );
		if ( ! ( n   instanceof String  ) ) throw new IllegalArgumentException ( "Le nom n'est pas du bon type"                           );
		if ( ! ( hsc instanceof Number  ) ) throw new IllegalArgumentException ( "Le nombre d'heures de services ne sont pas du bon type" );
		if ( ! ( hmc instanceof Number  ) ) throw new IllegalArgumentException ( "Le nombre d'heures max ne sont pas du bon type"         );
		if ( ! ( rt  instanceof Number  ) ) throw new IllegalArgumentException ( "Le ratio TP n'est pas du bon type"                      );
		
		int    idAnnee             = ( int ) idA;
		int    id                  = ( int ) i;
		double heureServiceContrat = Double.parseDouble ( hsc.toString ( ) );
		double heureMaxContrat     = Double.parseDouble ( hmc.toString ( ) );
		String nom                 = n.toString ( );
		double ratioTP             = Double.parseDouble ( rt.toString ( ) );

		return Contrat.creation ( idAnnee, id, nom, heureServiceContrat, heureMaxContrat, ratioTP );
	}

	/** Fabrique de contrat prenant en paramètres toutes les données d'un contrat
	 * @param idAnnee l'identifiant de l'année
	 * @param id l'indentifiant du contrat
	 * @param nom le nom du contrat
	 * @param heureServiceContrat le nombre d'heure de service du contrat
	 * @param heureMaxContrat le nombre d'heure maximum du contrat
	 * @param ratioTP le ratio TP du contrat
	 * @return Le contrat en question
	 */
	public static Contrat creation ( int idAnnee, int id, String nom, double heureServiceContrat, double heureMaxContrat, double ratioTP )
	{
		// Teste que l'identifiant de l'année est correct
		if ( idAnnee < 0                           ) throw new IllegalArgumentException ( "L'identifiant de l'année est incorrect"                 );
		
		// Teste que l'identifiant est correct
		if ( id < 0                                ) throw new IllegalArgumentException ( "L'identifiant est incorrect"                            );
		
		// Teste la validité du nom (non vide)
		if ( nom.equals ( "" )                     ) throw new IllegalArgumentException ( "Le nom du contrat n'est pas rempli"                     );
			
		// Teste la validité des heures du service du contrat pour qu'elles soient supérieur à 0
		if ( heureServiceContrat < 0               ) throw new IllegalArgumentException ( "Les heures de services doivent être supérieur à 0"      );

		// Teste la validité des heures max du contrat pour qu'elles soient supérieur à 0
		if ( heureMaxContrat     <= 0              ) throw new IllegalArgumentException ( "Les heures max doivent être supérieur à 0"              );

		// Teste si les heures de services sont supérieur aux heures max
		if ( heureServiceContrat > heureMaxContrat ) throw new IllegalArgumentException ( "Les heures de services sont supérieur à ses heures max" );

		// Coef TD
		if ( ratioTP <= 0                          ) throw new IllegalArgumentException ( "Le coef TD ne peut pas être de 0"                       );
		
		return new Contrat ( idAnnee, id, nom, heureServiceContrat, heureMaxContrat, ratioTP );
	}


	/*---------------------------------------*/
	/*                GETTEUR                */
	/*---------------------------------------*/

	/** Retourne l'identifiant de l'année
	 * @return L'identifiant de l'année
	 */
	public int    getIdAnnee             ( ) { return this.idAnnee;             }

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
	public double getHeureServiceContrat ( ) { return this.heureServiceContrat; }

	/** Retourne le nombre d'heure maximales d'un contrat
	 * @return Le nombre d'heure maximales du contrat
	 */
	public double getHeureMaxContrat     ( ) { return this.heureMaxContrat;     }

	/** Retourne le ratio TP d'un contrat
	 * @return Le ratio TP du contrat
	 */
	public double getRatioTP             ( ) { return this.ratioTP;             }


	/*---------------------------------------*/
	/*                SETTEUR                */
	/*---------------------------------------*/

	/** Permet de modifier l'identifiant de l'année
	 * @param idAnnee l'identifiant de l'année
	 */
	public void setIdAnnee             ( int    idAnnee             ) { this.idAnnee             = idAnnee;             }

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
	public void setHeureServiceContrat ( double heureServiceContrat ) { this.heureServiceContrat = heureServiceContrat; }

	/** Permet de modifier le nombre d'heure maximum d'un contrat
	 * @param heureMaxContrat le nombre d'heure maximum
	 */
	public void setHeureMaxContrat     ( double heureMaxContrat     ) { this.heureMaxContrat     = heureMaxContrat;     }

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

		return this.idAnnee             == c.idAnnee             &&
		       this.id                  == c.id                  &&
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
		return String.format ( "Année                 : %d%nID                    : %d%nNom                   : %s%nHeure service contrat : %,.2f%nHeure max contrat     : %,.2f%nRatio TP              : %,.2f", this.idAnnee, this.id, this.nom, this.heureServiceContrat, this.heureMaxContrat, this.ratioTP );
	}
}