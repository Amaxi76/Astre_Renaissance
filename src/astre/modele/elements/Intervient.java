package astre.modele.elements;

/** Classe Intervient
  * @author : Alizéa Lebaron
  * @version : 1.0 - 12/12/2023
  * @date : 12/12/2023
  */

public class Intervient
{
	private Intervenant intervenant;
	private Heure       heure;
	private ModuleIUT   module;
	private int         nbSemaine;
	private int         nbGroupe;
	private double      nbHeure;
	private String      commentaire;

	/** Constructeur d'intervient
	 * @param intervenant l'intervenant
	 * @param heure l'heure
	 * @param module le module
	 * @param nbSemaine le nombre de semaine
	 * @param nbGroupe le nombre de groupe
	 * @param nbHeure le nombre d'heure
	 * @param commentaire le commentaire
	 */
	private Intervient ( Intervenant intervenant, Heure heure, ModuleIUT module, int nbSemaine, int nbGroupe, double nbHeure, String commentaire ) 
	{
		this.intervenant = intervenant;
		this.heure       = heure;
		this.module      = module;
		this.nbSemaine   = nbSemaine;
		this.nbGroupe    = nbGroupe;
		this.nbHeure     = nbHeure;
		this.commentaire = commentaire;
	}

	/*---------------------------------------*/
	/*                FACTORY                */
	/*---------------------------------------*/

	/** Fabrique d'intervient à partir d'un tableau
	 * @param intervient Prend un tableau d'objet définissant l'intervient
	 * @return L'objet créée si les données du tableau sont correctes.
	 */
	public static Intervient creation ( Object[] intervient )
	{
		Object i   = intervient[0];
		Object h   = intervient[1];
		Object m   = intervient[2];
		Object nbS = intervient[3];
		Object nbG = intervient[4];
		Object nbH = intervient[5];
		Object c   = intervient[6];

		if ( ! ( i   instanceof Intervenant ) ) throw new IllegalArgumentException ( "L'intervenant n'est pas du bon type"        );
		if ( ! ( h   instanceof Heure       ) ) throw new IllegalArgumentException ( "L'heure n'est pas du bon type"              );
		if ( ! ( m   instanceof ModuleIUT   ) ) throw new IllegalArgumentException ( "Le module n'est pas du bon type"            );
		if ( ! ( nbS instanceof Integer     ) ) throw new IllegalArgumentException ( "Le nombre de semaine n'est pas du bon type" );
		if ( ! ( nbG instanceof Integer     ) ) throw new IllegalArgumentException ( "Le nombre de groupe n'est pas du bon type"  );
		if ( ! ( nbH instanceof Number      ) ) throw new IllegalArgumentException ( "Le nombre d'heure n'est pas du bon type"    );
		if ( ! ( c   instanceof String      ) ) throw new IllegalArgumentException ( "Le commentaire n'est pas du bon type"       );

		Intervenant intervenant = ( Intervenant ) i;
		Heure       heure       = ( Heure       ) h;
		ModuleIUT   module      = ( ModuleIUT   ) m;
		int         nbSemaine   = ( int         ) nbS;
		int         nbGroupe    = ( int         ) nbG;
		double      nbHeure     = ( double      ) nbH;
		String      commentaire = ( String      ) c;
		
		return Intervient.creation ( intervenant, heure, module, nbSemaine, nbGroupe, nbHeure, commentaire );
	}

	/** Fabrique d'intervient à partir des données
	 * @param intervenant l'intervenant
	 * @param heure l'heure
	 * @param module le module
	 * @param nbSemaine le nombre de semaine
	 * @param nbGroupe le nombre de groupe
	 * @param nbHeure le nombre d'heure
	 * @param commentaire le commentaire
	 * @return L'objet créée si les données du tableau sont correctes.
	 */
	public static Intervient creation ( Intervenant i, Heure h, ModuleIUT m, int nbS, int nbG, double nbH, String c )
	{
		if ( i == null ) throw new IllegalArgumentException ( "L'intervenant est vide"           );
		if ( h == null ) throw new IllegalArgumentException ( "L'heure est vide"                 );
		if ( m == null ) throw new IllegalArgumentException ( "Le module est vide"               );
		if ( nbS < 0   ) throw new IllegalArgumentException ( "Le nombre de semaine est négatif" );
		if ( nbG < 0   ) throw new IllegalArgumentException ( "Le nombre de groupe est négatif"  );
		if ( nbH < 0   ) throw new IllegalArgumentException ( "Le nombre d'heure est négatif"    );
		if ( c == null ) throw new IllegalArgumentException ( "Le commentaire est vide"          );
		
		return new Intervient ( i, h, m, nbS, nbG, nbH, c );
	}


	/*---------------------------------------*/
	/*                GETTEUR                */
	/*---------------------------------------*/

	/** Retourne l'intervenant d'intervient
	 * @return l'intervenant
	 */
	public Intervenant getIntervenant ( ) { return intervenant; }

	/** Retourne l'heure d'intervient
	 * @return l'heure
	 */
	public Heure       getHeure       ( ) { return heure;       }

	/** Retourne le module d'intervient
	 * @return le module
	 */
	public ModuleIUT   getModule      ( ) { return module;      }

	/** Retourne le nombre de semaine d'intervient
	 * @return le nombre de semaine
	 */
	public int         getNbSemaine   ( ) { return nbSemaine;   }

	/** Retourne le nombre de groupe d'intervient
	 * @return le nombre de groupe
	 */
	public int         getNbGroupe    ( ) { return nbGroupe;    }

	/** Retourne le nombre d'heure d'intervient
	 * @return le nombre d'heure
	 */
	public double      getNbHeure     ( ) { return nbHeure;     }

	/** Retourne le commentaire d'intervient
	 * @return le commentaire
	 */
	public String      getCommentaire ( ) { return commentaire; }


	/*---------------------------------------*/
	/*                SETTEUR                */
	/*---------------------------------------*/

	/** Permet de modifier l'intervenant
	 * @param intervenant l'intervenant à modifier
	 */
	public void setIntervenant ( Intervenant intervenant ) { this.intervenant = intervenant; }

	/** Permet de modifier l'heure
	 * @param heure l'heure à modifier
	 */
	public void setHeure       ( Heure       heure       ) { this.heure       = heure;       }

	/** Permet de modifier le module
	 * @param module le module à modifier
	 */
	public void setModule      ( ModuleIUT      module   ) { this.module      = module;      }

	/** Permet de modifier le nombre de semaine
	 * @param nbSemaine le nombre de semaine à modifier
	 */
	public void setNbSemaine   ( int         nbSemaine   ) { this.nbSemaine   = nbSemaine;   }

	/** Permet de modifier le nombre de groupe
	 * @param nbGroupe le nombre de groupe à modifier
	 */
	public void setNbGroupe    ( int         nbGroupe    ) { this.nbGroupe    = nbGroupe;    }

	/** Permet de modifier le nombre d'heure
	 * @param nbHeure le nombre d'heure à modifier
	 */
	public void setNbHeure     ( double      nbHeure     ) { this.nbHeure     = nbHeure;     }

	/** Permet de modifier le commentaire
	 * @param commentaire le commentaire à modifier
	 */
	public void setCommentaire ( String      commentaire ) { this.commentaire = commentaire; }

	
	/*---------------------------------------*/
	/*                METHODES               */
	/*---------------------------------------*/

	/** Indique si deux Intervient sont égaux
	 * @return true si les deux Intervient sont égaux, false sinon
	 */
	@Override
	public boolean equals ( Object o )
	{
		if ( ! ( o instanceof Intervient ) ) return false;
		if ( o == this                     ) return true;

		Intervient c = ( Intervient ) o;

		return this.intervenant.equals ( c.intervenant ) &&
		       this.heure      .equals ( c.heure       ) &&
		       this.module     .equals ( c.module      ) &&
		       this.nbSemaine  == c.nbSemaine            &&
		       this.nbGroupe   == c.nbGroupe             &&
		       this.nbHeure    == c.nbHeure              &&
		       this.commentaire.equals ( c.commentaire );
	}

	/** Renvoie la description et le contenu d'un Intervient
	 * @return Une description du contenue d'un Intervient
	 */
	@Override
	public String toString ( )
	{
		return String.format ( "Intervient%nIntervenant : %s%nHeure       : %s%nModule      : %s%nNombre de semaine : %d%nNombre de groupe  : %d%nNombre d'heure    : %f%nCommentaire       : %s", this.intervenant, this.heure, this.module, this.nbSemaine, this.nbGroupe, this.nbHeure, this.commentaire );
	}
}
