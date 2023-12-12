package astre.modele;

/** Classe Contrat 
  * @author : Maximilien Lesterlin, Alizéa Lebaron
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

	/**
	 * @param id
	 * @param nom
	 * @param heureServiceContrat
	 * @param heureMaxContrat
	 * @param ratioTP
	 */
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

	/**
	 * @return
	 */
	public int    getId                  ( ) { return this.id;                  }
	/**
	 * @return
	 */
	public String getNom                 ( ) { return this.nom;                 }
	/**
	 * @return
	 */
	public int    getHeureServiceContrat ( ) { return this.heureServiceContrat; }
	/**
	 * @return
	 */
	public int    getHeureMaxContrat     ( ) { return this.heureMaxContrat;     }
	/**
	 * @return
	 */
	public double getRatioTP             ( ) { return this.ratioTP;             }

	/*---------------------------------------*/
	/*                SETTEUR                */
	/*---------------------------------------*/

	/**
	 * @param heureServiceContrat
	 */
	public void setHeureServiceContrat ( int    heureServiceContrat ) { this.heureServiceContrat = heureServiceContrat; }
	/**
	 * @param nom
	 */
	public void setNom                 ( String nom                 ) { this.nom                 = nom;                 }
	/**
	 * @param heureMaxContrat
	 */
	public void setHeureMaxContrat     ( int    heureMaxContrat     ) { this.heureMaxContrat     = heureMaxContrat;     }
	/**
	 * @param ratioTP
	 */
	public void setRatioTP             ( double ratioTP             ) { this.ratioTP             = ratioTP;             }
	/**
	 * @param id
	 */
	public void setId                  ( int    id                  ) { this.id                  = id;                  }

	/*---------------------------------------*/
	/*                METHODES               */
	/*---------------------------------------*/

	/**
	 * @return
	 */
	public boolean retirerListe ( )
	{
		if ( ! Contrat.ensContrat.contains ( this ) ) return false;
		
		Contrat.ensContrat.remove ( this );
		return true;
	}

	/**
	 * @return
	 */
	public String toString ( )
	{
		return String.format ( "Nom : %-20s - "                 , this.nom                 ) +
		       String.format ( "Heure Service Contrat : %02d - ", this.heureServiceContrat ) +
		       String.format ( "Heure Max Contrat : %02d - "    , this.heureMaxContrat     ) +
		       String.format ( "Ratio TP : %,.2f"               , this.ratioTP             );

	}
	
}