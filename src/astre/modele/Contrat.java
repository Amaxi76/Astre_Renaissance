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
	}

	/*---------------------------------------*/
	/*                GETTEUR                */
	/*---------------------------------------*/

	public String getNom                 ( ) { return this.nom;                 }
	public int    getHeureServiceContrat ( ) { return this.heureServiceContrat; }
	public int    getHeureMaxContrat     ( ) { return this.heureMaxContrat;     }
	public double getRatioTP             ( ) { return this.ratioTP;             }

	/*---------------------------------------*/
	/*                SETTEUR                */
	/*---------------------------------------*/

	public void setHeureServiceContrat ( int    heureServiceContrat ) { this.heureServiceContrat = heureServiceContrat; }
	public void setNom                 ( String nom                 ) { this.nom                 = nom;                 }
	public void setHeureMaxContrat     ( int    heureMaxContrat     ) { this.heureMaxContrat     = heureMaxContrat;     }
	public void setRatioTP             ( double ratioTP             ) { this.ratioTP             = ratioTP;             }

	/*---------------------------------------*/
	/*                METHODES               */
	/*---------------------------------------*/

	public boolean retirerListe ( )
	{
		if ( ! Contrat.ensContrat.contains ( this ) ) return false;
		
		Contrat.ensContrat.remove ( this );
		return true;
	}
	

	public String toString ( )
	{
		return String.format ( "Nom : %-20s - "                 , this.nom                 ) +
		       String.format ( "Heure Service Contrat : %02d - ", this.heureServiceContrat ) +
		       String.format ( "Heure Max Contrat : %02d - "    , this.heureMaxContrat     ) +
		       String.format ( "Ratio TP : %,.2f"               , this.ratioTP             );

	}

	private static void test ( )
	{
		System.out.println ( Contrat.ensContrat );

		Contrat c = new Contrat ( "test", 0, 0, 0 );

		System.out.println ( Contrat.ensContrat );

		c.retirerListe ( );

		System.out.println ( Contrat.ensContrat );
	}

	public static void main ( String[] args )
	{
		Contrat.test ( );
	}
	
}