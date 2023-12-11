package astre.modele;

/** Classe Intervenant 
  * @author : Maximilien Lesterlin
  * @version : 1.0 - 11/12/2023
  * @date : 06/12/2023
  */

public class Intervenantrgrgehhtt
{
	private String  nom;
	private String  prenom;
	private Contrat statut;
	private int     service;
	private int     heureMaximum;

	public Intervenant ( String nom, String prenom, Contrat statut, int service, int heureMaximum )
	{
		this.nom          = nom;
		this.prenom       = prenom;
		this.statut       = statut;
		this.service      = service;
		this.heureMaximum = heureMaximum;
	}

	/*---------------------------------------*/
	/*                GETTEUR                */
	/*---------------------------------------*/

	public String  getNom          ( ) { return this.nom;          }
	public String  getPrenom       ( ) { return this.prenom;       }
	public Contrat getStatut       ( ) { return this.statut;       }
	public int     getService      ( ) { return this.service;      }
	public int     getHeureMaximum ( ) { return this.heureMaximum; }

	/*---------------------------------------*/
	/*                SETTEUR                */
	/*---------------------------------------*/

	public void setNom          ( String  nom          ) { this.nom          = nom;          }
	public void setPrenom       ( String  prenom       ) { this.prenom       = prenom;       }
	public void setStatut       ( Contrat statut       ) { this.statut       = statut;       }
	public void setService      ( int     service      ) { this.service      = service;      }
	public void setHeureMaximum ( int     heureMaximum ) { this.heureMaximum = heureMaximum; }

}
