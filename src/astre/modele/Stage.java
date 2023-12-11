// @author : Maximilien Lesterlin
package astre.modele;

public class Stage extends Module
{
	
	public Stage ( Semestre semestre, String code, String libLong, String libCourt )
	{
		super ( semestre, code, libLong, libCourt );

		for ( Heure h : Heure.getHeures ( ) )
			if ( h.getNom ( ).equals ( "REH" ) || h.getNom ( ).equals ( "HP" ) )
				super.hsHeure.put ( h, 0 );
	}
}