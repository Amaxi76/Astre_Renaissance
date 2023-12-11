// @author : Maximilien Lesterlin
package astre.modele;

import java.util.ArrayList;
import java.util.List;

public class Stage extends Module
{
	private List<Heure> ensHeure;
	
	public Stage ( Semestre semestre, String code, String libLong, String libCourt )
	{
		super ( semestre, code, libLong, libCourt );

		this.ensHeure = new ArrayList<> ( );

		for ( Heure h : Heure.getHeures ( ) )
			if ( h.getNom ( ).equals ( "REH" ) || h.getNom ( ).equals ( "HP" ) )
				this.ensHeure.add ( h );
	}
}