package astre.modele;

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

	/**
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

	/**
	 * @return
	 */
	public int     getId           ( ) { return this.id;           }
	/**
	 * @return
	 */
	public String  getNom          ( ) { return this.nom;          }
	/**
	 * @return
	 */
	public String  getPrenom       ( ) { return this.prenom;       }
	/**
	 * @return
	 */
	public Contrat getContrat      ( ) { return this.contrat;      }
	/**
	 * @return
	 */
	public int     getheureService ( ) { return this.heureService; }
	/**
	 * @return
	 */
	public int     getHeureMaximum ( ) { return this.heureMaximum; }

	/*---------------------------------------*/
	/*                SETTEUR                */
	/*---------------------------------------*/

	/**
	 * @param id
	 */
	public void setId           ( int     id           ) { this.id           = id;           }
	/**
	 * @param nom
	 */
	public void setNom          ( String  nom          ) { this.nom          = nom;          }
	/**
	 * @param prenom
	 */
	public void setPrenom       ( String  prenom       ) { this.prenom       = prenom;       }
	/**
	 * @param contrat
	 */
	public void setContrat      ( Contrat contrat      ) { this.contrat      = contrat;      }
	/**
	 * @param heureService
	 */
	public void setheureService ( int     heureService ) { this.heureService = heureService; }
	/**
	 * @param heureMaximum
	 */
	public void setHeureMaximum ( int     heureMaximum ) { this.heureMaximum = heureMaximum; }

	/*---------------------------------------*/
	/*                METHODES               */
	/*---------------------------------------*/

	/**
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
	
}
