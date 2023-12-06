package astre.modele;

import java.util.ArrayList;
import java.util.List;

public class Contrat
{
	private static List<Contrat> ensContrat = new ArrayList<> ( );
	
	private String nom;
	private int    heureServiceContrat;
	private int    heureMaxContrat;
	private double ratioTP;

	public Contrat ( String nom, int heureServiceContrat, int heureMaxContrat, double ratioTP )
	{
		this.nom                 = nom;
		this.heureServiceContrat = heureServiceContrat;
		this.heureMaxContrat     = heureMaxContrat;
		this.ratioTP             = ratioTP;

		Contrat.ensContrat.add ( this );
	}

	public static List<Contrat> getContrats ( ) { return Contrat.ensContrat;  }

	public String  getNom                 ( ) { return nom;                 }
	public int     getHeureServiceContrat ( ) { return heureServiceContrat; }
	public int     getHeureMaxContrat     ( ) { return heureMaxContrat;     }
	public double  getRatioTP             ( ) { return ratioTP;             }
	
	public void setHeureServiceContrat ( int heureServiceContrat ) { this.heureServiceContrat = heureServiceContrat; }
	public void setNom                 ( String nom              ) { this.nom = nom;                                 }
	public void setHeureMaxContrat     ( int heureMaxContrat     ) { this.heureMaxContrat = heureMaxContrat;         }
	public void setRatioTP             ( double ratioTP          ) { this.ratioTP         = ratioTP;                 }

	public String toString ( )
	{
		return String.format ( "Nom : %-20s - "                 , this.nom                 ) +
		       String.format ( "Heure Service Contrat : %02d - ", this.heureServiceContrat ) +
		       String.format ( "Heure Max Contrat : %02d - "    , this.heureMaxContrat     ) +
		       String.format ( "Ratio TP : %,.2f"               , this.ratioTP             );

	}
	
}