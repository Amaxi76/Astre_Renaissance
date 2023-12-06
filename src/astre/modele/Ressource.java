// @author : Maximilien Lesterlin
package astre.modele;

import java.util.ArrayList;
import java.util.List;

public class Ressource extends Module
{
	private List<Heure> ensHeure;
	
	public Ressource ( Semestre semestre, String code, String libLong, String libCourt )
	{
		super ( semestre, code, libLong, libCourt );

		this.ensHeure = new ArrayList<> ( );

		for ( Heure h : Heure.getHeures ( ) )
		{
			switch ( h.getNom ( ) )
			{
				case "CM": this.ensHeure.add ( h );
				case "TD": this.ensHeure.add ( h );
				case "TP": this.ensHeure.add ( h );
				case "PN": this.ensHeure.add ( h );
				case "HP": this.ensHeure.add ( h );
				default:
					break;
			}
			
		}
	}
}