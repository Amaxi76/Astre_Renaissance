package astre.modele.elements;

/** Classe Heure 
  * @author : Maximilien Lesterlin
  * @version : 1.0 - 12/12/2023
  * @date : 06/12/2023
  */

// TODO: Faire en sorte que ça ne supprime pas toutes la base de donnée 

import java.util.ArrayList;
import java.util.List;

public class Heure
{
	private static List<Heure> ensHeure = new ArrayList<> ( );
	
	private int    id;
	private String nom;
	private double coefTd;

	public Heure ( int id, String nom, double coefTd )
	{
		this.id     = id;
		this.nom    = nom;
		this.coefTd = coefTd;
	}

	public static Heure creation ( Object[] heure )
	{
		Object i = heure[0];
		Object n = heure[1];
		Object c = heure[2];

		System.out.println(!( c instanceof Number ));

		if ( ( i != null && !( i instanceof Integer ) ) ||  !( n instanceof String ) || !( c instanceof Number ) )
			throw new IllegalArgumentException ( "Les données de l'heure ne sont pas du bon type" );
		
		int    id   = ( i == null ) ? 0 : Integer.parseInt ( i.toString ( ) );
		double coef = Double.parseDouble ( c.toString ( ) );
		String nom  = n.toString ( );

		return Heure.creation ( id, nom, coef );
	}

	public static Heure creation ( int id, String nom, double coefTD )
	{
		if ( nom.equals ( "" ) )
			throw new IllegalArgumentException ( "Une des données est vide" );

		//TODO: Demander confirmation au prof
		if ( coefTD >= 0  )
			throw new IllegalArgumentException ( "Le coef TP doit être supérieur à 0" );

		return new Heure ( id, nom, coefTD );
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
