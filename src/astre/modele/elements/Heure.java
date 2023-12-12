package astre.modele.elements;

/** Classe Heure 
  * @author : Maximilien Lesterlin
  * @version : 1.0 - 11/12/2023
  * @date : 06/12/2023
  */

import java.util.ArrayList;
import java.util.List;

public class Heure
{
	private static List<Heure> ensHeure = new ArrayList<> ( );
	
	private String nom;
	private double coefTd;

	public Heure ( String nom, double coefTd )
	{
		this.nom    = nom;
		this.coefTd = coefTd;
	}

	/*---------------------------------------*/
	/*                GETTEUR                */
	/*---------------------------------------*/

	public static List<Heure> getHeures ( ) { return Heure.ensHeure;  }

	public String getNom    ( ) { return nom;    }
	public double getCoefTd ( ) { return coefTd; }

	/*---------------------------------------*/
	/*                SETTEUR                */
	/*---------------------------------------*/
	
	public void setNom    ( String nom    ) { this.nom    = nom;    }
	public void setCoefTd ( double coefTd ) { this.coefTd = coefTd; }

	/*---------------------------------------*/
	/*                METHODES               */
	/*---------------------------------------*/

	public boolean retirerListe ( )
	{
		if ( ! Heure.ensHeure.contains ( this ) ) return false;
		
		Heure.ensHeure.remove ( this );
		return true;
	}
	
}
