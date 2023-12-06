// @author : Maximilien Lesterlin
package astre.modele;

import java.util.ArrayList;
import java.util.List;

public class Sae extends Module
{
	private List<Heure> ensHeure;
	
	public Sae ( Semestre semestre, String code, String libLong, String libCourt )
	{
		super ( semestre, code, libLong, libCourt );

		this.ensHeure = new ArrayList<> ( );

		for ( Heure h : Heure.getHeures ( ) )
			if ( h.getNom ( ).equals ( "SAE" ) || h.getNom ( ).equals ( "HP" ) )
				this.ensHeure.add ( h );
	}
}