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

	public int     getId           ( ) { return this.id;           }
	public String  getNom          ( ) { return this.nom;          }
	public String  getPrenom       ( ) { return this.prenom;       }
	public Contrat getContrat      ( ) { return this.contrat;      }
	public int     getheureService ( ) { return this.heureService; }
	public int     getHeureMaximum ( ) { return this.heureMaximum; }

	/*---------------------------------------*/
	/*                SETTEUR                */
	/*---------------------------------------*/

	public void setId           ( int     id           ) { this.id           = id;           }
	public void setNom          ( String  nom          ) { this.nom          = nom;          }
	public void setPrenom       ( String  prenom       ) { this.prenom       = prenom;       }
	public void setContrat      ( Contrat contrat      ) { this.contrat      = contrat;      }
	public void setheureService ( int     heureService ) { this.heureService = heureService; }
	public void setHeureMaximum ( int     heureMaximum ) { this.heureMaximum = heureMaximum; }

	/*---------------------------------------*/
	/*                METHODES               */
	/*---------------------------------------*/

	/**
	 * @return descriptif des attributs de semestre
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
