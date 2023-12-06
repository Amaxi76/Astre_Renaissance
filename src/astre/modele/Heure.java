package astre.modele;

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

	public static List<Heure> getHeures ( ) { return Heure.ensHeure;  }

	public String getNom    ( ) { return nom;    }
	public double getCoefTd ( ) { return coefTd; }
	
	public void setNom    ( String nom    ) { this.nom    = nom;    }
	public void setCoefTd ( double coefTd ) { this.coefTd = coefTd; }

	public boolean retirerListe ( )
	{
		if ( ! Heure.ensHeure.contains ( this ) ) return false;
		
		Heure.ensHeure.remove ( this );
		return true;
	}
	
}
