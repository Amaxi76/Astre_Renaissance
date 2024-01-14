package astre.modele.elements;

/** Classe Horaire
  * @author : Alizéa Lebaron, Maxmilien Lesterlin
  * @version : 2.0 - 14/01/2024
  * @date : 12/12/2023
  */

public class Horaire
{
	private Heure     heure;
	private ModuleIUT module;
	private double    nbHeurePN;
	private double    nbHeureRepartie;
	private int       nbSemaine;

	/** Constructeur d'horaire
	 * @param heure l'heure de l'horaire
	 * @param module le module de l'horaire
	 * @param nbHeurePN le nombre d'heure PN de l'horaire
	 * @param nbSemaine le nombre de semaine de l'horaire
	 * @param nbHeure le nombre d'heure répartie de l'horaire
	 */
	private Horaire ( Heure heure, ModuleIUT module, double nbHeurePN, double nbHeureRepartie, int nbSemaine )
	{
		this.heure           = heure;
		this.module          = module;
		this.nbHeurePN       = nbHeurePN;
		this.nbHeureRepartie = nbHeureRepartie;
		this.nbSemaine       = nbSemaine;
	}


	/*---------------------------------------*/
	/*                FACTORY                */
	/*---------------------------------------*/

	/** Crée un horraire en prenant en paramètre un tableau d'objet.
	 * @return L'horaire créée si les données du tableau sont correctes.
	 */
	public static Horaire creation ( Object[] horaire )
	{
		Object h  = horaire[0];
		Object m  = horaire[1];
		Object hp = horaire[2];
		Object nr = horaire[3];
		Object ns = horaire[4];

		if ( ! ( h  instanceof Heure     ) ) throw new IllegalArgumentException ( "L'heure n'est pas du bon type"                    );
		if ( ! ( m  instanceof ModuleIUT ) ) throw new IllegalArgumentException ( "Le module n'est pas du bon type"                  );
		if ( ! ( hp instanceof Number    ) ) throw new IllegalArgumentException ( "Le nombre d'heure PN n'est pas du bon type"       );
		if ( ! ( nr instanceof Number    ) ) throw new IllegalArgumentException ( "Le nombre d'heure repartie n'est pas du bon type" );
		if ( ! ( ns instanceof Integer   ) ) throw new IllegalArgumentException ( "Le nombre de semaine n'est pas du bon type"       );
		
		Heure     heure             = ( Heure     ) h;
		ModuleIUT module            = ( ModuleIUT ) m;
		double    nbHeurePN         = Double.parseDouble ( hp.toString ( ) );
		double    nbHeureRepartie   = Double.parseDouble ( nr.toString ( ) );
		int       nbSemaine         = (int) ns;

		return Horaire.creation ( heure, module, nbHeurePN, nbHeureRepartie, nbSemaine );
	}

	/** Crée un horaire en prenant en paramètre une heure, un module, un nombre d'heure PN, un nombre de semaine et un nombre d'heure répartie.
	 * @return L'horaire créée si les données ont une valeur et que le nombre d'heure PN, le nombre de semaine et le nombre d'heure répartie sont positifs.
	 */
	public static Horaire creation ( Heure heure, ModuleIUT module, double nbHeurePN, double nbHeureRepartie, int nbSemaine )
	{
		if ( nbHeurePN       < 0 ) throw new IllegalArgumentException ( "Le nombre d'heure PN doit être supérieur à 0" );
		if ( nbHeureRepartie < 0 ) throw new IllegalArgumentException ( "Le nombre d'heure doit être supérieur à 0"    );
		if ( nbSemaine       < 0 ) throw new IllegalArgumentException ( "Le nombre de semaine doit être supérieur à 0" );

		return new Horaire ( heure, module, nbHeurePN, nbHeureRepartie, nbSemaine );
	}

	
	/*---------------------------------------*/
	/*                GETTEUR                */
	/*---------------------------------------*/

	/** Retourne l'heure d'une horaire
	 * @return l'heure
	 */
	public Heure     getHeure             ( ) { return this.heure;             }

	/** Retourne le module d'une horaire
	 * @return le module
	 */
	public ModuleIUT getModule            ( ) { return this.module;            }

	/** Retourne le nombre d'heure PN d'une horaire
	 * @return le nombre d'heure PN
	 */
	public double    getNbHeurePN         ( ) { return this.nbHeurePN;         }

	/** Retourne le nombre d'heure répartie d'une horaire
	 * @return le nombre d'heure repartie
	 */
	public double    getNbHeureRepartie   ( ) { return this.nbHeureRepartie;   }
	
	/** Retourne le nombre de semaine d'une horaire
	 * @return le nombreb de semaine
	 */
	public int       getNbSemaine         ( ) { return this.nbSemaine;         }
	
	/*---------------------------------------*/
	/*                SETTEUR                */
	/*---------------------------------------*/

	/** Permet de modifier l'heure
	 * @param heure l'heure à modifier
	 */
	public void setHeure             ( Heure  heure                ) { this.heure           = heure;             }

	/** Permet de modifier le module
	 * @param module le module à modifier
	 */
	public void setModule            ( ModuleIUT module            ) { this.module          = module;            }

	/** Permet de modifier le nombre d'heure PN
	 * @param nbHeurePN le nombre d'heure PN à modifier
	 */
	public void setNbHeurePN         ( double    nbHeurePN         ) { this.nbHeurePN       = nbHeurePN;         }

	/** Permet de modifier le nombre d'heure répartie
	 * @param nbHeure le nombre d'heure à modifier
	 */
	public void setNbHeureRepartie   ( double    nbHeureRepartie   ) { this.nbHeureRepartie = nbHeureRepartie;   }
	
	/** Permet de modifier le nombre de semaine
	 * @param nbSemaine le nombre de semaine à modifier
	 */
	public void setNbSemaine         ( int       nbSemaine         ) { this.nbSemaine       = nbSemaine;         }
	
	/*---------------------------------------*/
	/*                METHODES               */
	/*---------------------------------------*/

	/** Indique si deux heures sont égals
	 * @return true si les deux heures sont égals, false sinon
	 */
	@Override
	public boolean equals ( Object o )
	{
		if ( ! ( o instanceof Horaire ) ) return false;
		if ( o == this                  ) return true;

		Horaire c = ( Horaire ) o;

		return this.heure .equals ( c.heure  )           &&
		       this.module.equals ( c.module )           &&
		       this.nbHeurePN       == c.nbHeurePN       &&
		       this.nbHeureRepartie == c.nbHeureRepartie &&
		       this.nbSemaine       == c.nbSemaine;

	
	}

	/** Renvoie la description et le contenu d'une horaire
	 * @return Une description du contenue d'une horaire
	 */
	@Override
	public String toString ( )
	{
		return String.format( "Horaire%nHeure           : %s%nModule          : %s%nNombre d'heure PN       : %,.2f%nNombre d'heure repartie : %,.2f%nNombre de semaine       : %d", this.heure, this.module, this.nbHeurePN, this.nbHeureRepartie, this.nbSemaine );
	}
}