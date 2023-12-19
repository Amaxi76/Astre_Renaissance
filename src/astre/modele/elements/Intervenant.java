package astre.modele.elements;

/** Classe Intervenant 
  * @author : Maximilien Lesterlin, Alizéa Lebaron
  * @version : 1.0.1 - 12/12/2023
  * @date : 06/12/2023
  */

public class Intervenant
{
	private int 	id;
	private String  nom;
	private String  prenom;
	private int     heureService;
	private int     heureMaximum;
	private Contrat contrat;

	/** Constructeur d'intervenant
	 * @param id
	 * @param nom
	 * @param prenom
	 * @param contrat
	 * @param heureService
	 * @param heureMaximum
	 */
	public Intervenant (int id, String nom, String prenom, Contrat contrat, int heureService, int heureMaximum )
	{
		this.id           = id;
		this.nom          = nom;
		this.prenom       = prenom;
		this.contrat      = contrat;
		this.heureService = heureService;
		this.heureMaximum = heureMaximum;
	}

	/*---------------------------------------*/
	/*                GETTEUR                */
	/*---------------------------------------*/

	/** Retourne l'id de l'intervenant
	 * @return id
	 */
	public int     getId           ( ) { return this.id;           }

	/** Retourne le nom de l'intervenant
	 * @return nom
	 */
	public String  getNom          ( ) { return this.nom;          }

	/** Retourne le prénom de l'intervenant
	 * @return prenom
	 */
	public String  getPrenom       ( ) { return this.prenom;       }

	/** Retourne le contrat de l'intervenant
	 * @return contrat
	 */
	public Contrat getContrat      ( ) { return this.contrat;      }

	/** Retourne le nombre d'heure de service de l'intervenant
	 * @return heureService
	 */
	public int     getheureService ( ) { return this.heureService; }

	/** Retourne le nombre d'heure maximum de l'intervenant
	 * @return heureMaximum
	 */
	public int     getHeureMaximum ( ) { return this.heureMaximum; }

	/*---------------------------------------*/
	/*                SETTEUR                */
	/*---------------------------------------*/

	/** Permet de modifier l'id
	 * @param id the id to set
	 */
	public void setId           ( int     id           ) { this.id           = id;           }

	/** Permet de modifier le nom
	 * @param nom the nom to set
	 */
	public void setNom          ( String  nom          ) { this.nom          = nom;          }

	/** Permet de modifier le prénom
	 * @param prenom the prenom to set
	 */
	public void setPrenom       ( String  prenom       ) { this.prenom       = prenom;       }

	/** Permet de modifier le contrat
	 * @param contrat the contrat to set
	 */
	public void setContrat      ( Contrat contrat      ) { this.contrat      = contrat;      }

	/** Permet de modifier le nombre d'heure de service
	 * @param heureService the heureService to set
	 */
	public void setheureService ( int     heureService ) { this.heureService = heureService; }

	/** Permet de modifier le nombre d'heure maximum
	 * @param heureMaximum the heureMaximum to set
	 */
	public void setHeureMaximum ( int     heureMaximum ) { this.heureMaximum = heureMaximum; }

	/*---------------------------------------*/
	/*                METHODES               */
	/*---------------------------------------*/

	/** Renvoie le descriptif des attributs d'intervenants
	 * @return descriptif des attributs d'intervenants
	 */
	public String toString ( )
	{
		String sRet = "";

		sRet = String.format ( "Nom               : %20s - ",  this.nom          ) +
			   String.format ( "Prénom            : %20s - ",  this.prenom       ) +
			   String.format ( "heureService      : %3d  - ",  this.heureService ) +
			   String.format ( "Heure Max         : %3d  - ",  this.heureMaximum ) ;

		return sRet;
	}

	/** Compare l'égalité entre l'intervenant passé en paramètre et l'invernant passé par le constructeur
	 * @return true si les deux intervenants sont égaux, sinon false
	 */
	public boolean equals( Intervenant i )
	{
		if( this.id == i.getId() && this.nom.equals( i.getNom() ) && this.prenom.equals( i.getPrenom() ) && this.heureService == i.getheureService() && this.heureMaximum == i.getHeureMaximum() )
			return true;
		
		return false;
	}
	
}
