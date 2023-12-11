/** Classe Semestre 
  * @author : Maximilien Lesterlin
  * @version : 1.0 - 11/12/2023
  * @date : 06/12/2023
  */

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