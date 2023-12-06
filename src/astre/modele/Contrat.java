package astre.modele;

public class Contrat
{
	private String nom;
	private int    heureServiceContrat;
	private int    heureMaxContrat;
	private double ratioTP;

	public Contrat(String nom, int heureServiceContrat, int heureMaxContrat, double ratioTP)
	{
		this.nom                 = nom;
		this.heureServiceContrat = heureServiceContrat;
		this.heureMaxContrat     = heureMaxContrat;
		this.ratioTP             = ratioTP;
	}

	public String getNom (            ) { return nom;     }
	public void   setNom ( String nom ) { this.nom = nom; }

	public int  getHeureServiceContrat (                         ) { return heureServiceContrat;                     }
	public void setHeureServiceContrat ( int heureServiceContrat ) { this.heureServiceContrat = heureServiceContrat; }


	public int  getHeureMaxContrat (                     ) { return heureMaxContrat;                 }
	public void setHeureMaxContrat ( int heureMaxContrat ) { this.heureMaxContrat = heureMaxContrat; }

	public double getRatioTP (                ) { return ratioTP;         }
	public void   setRatioTP ( double ratioTP ) { this.ratioTP = ratioTP; }


	public String toString ()
	{
		String sRet = "";

		sRet = String.format ( "Nom : %-20s - ",                   this.nom                 ) +
			   String.format ( "Heure Service Contrat : %02d - ",  this.heureServiceContrat ) +
			   String.format ( "Heure Max Contrat : %02d - ",      this.heureMaxContrat     ) +
			   String.format ( "Ratio TP : %,.2f",                 this.ratioTP             );

		return sRet;
	}
	
}