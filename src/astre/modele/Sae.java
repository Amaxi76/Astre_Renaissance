// @author : Maximilien Lesterlin
package astre.modele;

public class Sae extends Module
{

	public Sae ( Semestre semestre, String code, String libLong, String libCourt )
	{
		super ( semestre, code, libLong, libCourt );

		for ( Heure h : Heure.getHeures ( ) )
			if ( h.getNom ( ).equals ( "SAE" ) || h.getNom ( ).equals ( "HP" ) )
				super.hsHeure.put ( h, 0 );
	}
}