package astre.modele;

public class Intervenant
{
	private String  nom;
	private String  prenom;
	private Contrat statut;
	private double  coefTp;
	private int     service;
	private int     heureMaximum;

	public Intervenant ( String nom, String prenom, Contrat statut, double coefTp, int service, int heureMaximum )
	{
		this.nom          = nom;
		this.prenom       = prenom;
		this.statut       = statut;
		this.coefTp       = coefTp;
		this.service      = service;
		this.heureMaximum = heureMaximum;
	}

	/*---------------------------------------*/
	/*                GETTEUR                */
	/*---------------------------------------*/

	public String  getNom          ( ) { return nom;          }
	public String  getPrenom       ( ) { return prenom;       }
	public Contrat getStatut       ( ) { return statut;       }
	public double  getCoefTp       ( ) { return coefTp;       }
	public int     getService      ( ) { return service;      }
	public int     getHeureMaximum ( ) { return heureMaximum; }

	/*---------------------------------------*/
	/*                SETTEUR                */
	/*---------------------------------------*/

	public void setNom          ( String  nom          ) { this.nom          = nom;          }
	public void setPrenom       ( String  prenom       ) { this.prenom       = prenom;       }
	public void setStatut       ( Contrat statut       ) { this.statut       = statut;       }
	public void setCoefTp       ( double  coefTp       ) { this.coefTp       = coefTp;       }
	public void setService      ( int     service      ) { this.service      = service;      }
	public void setHeureMaximum ( int     heureMaximum ) { this.heureMaximum = heureMaximum; }

}
