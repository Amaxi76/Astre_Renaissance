package astre.modele;

/** Classe Contrat 
  * @author : Maximilien Lesterlin
  * @version : 1.0 - 11/12/2023
  * @date : 06/12/2023
  */

import java.util.ArrayList;
import java.util.List;

public class Contrat
{
	private static List<Contrat> ensContrat = new ArrayList<> ( );

	private int    id;
	private String nom;
	private int    heureServiceContrat;
	private int    heureMaxContrat;
	private double ratioTP;

	public Contrat ( int id, String nom, int heureServiceContrat, int heureMaxContrat, double ratioTP )
	{
		this.id                  = id;
		this.nom                 = nom;
		this.heureServiceContrat = heureServiceContrat;
		this.heureMaxContrat     = heureMaxContrat;
		this.ratioTP             = ratioTP;
	}

	/*---------------------------------------*/
	/*                GETTEUR                */
	/*---------------------------------------*/

	public int    getId                  ( ) { return this.id;                  }
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
	public void setId                  ( int    id                  ) { this.id                  = id;                  }

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
	
}