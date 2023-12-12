package astre.modele.elements;

/** Classe SAE
  * @author : Maximilien Lesterlin
  * @version : 1.0 - 11/12/2023
  * @date : 06/12/2023
  */

public class Sae extends Module
{
	public Sae ( Semestre semestre, String code, String libLong, String libCourt )
	{
		super ( semestre, code, libLong, libCourt );
		
		for ( Heure h : Heure.getHeures ( ) )
		{
			if ( h.getNom ( ).equals ( "SAE" ) || h.getNom ( ).equals ( "HT" ) )
			{
				super.hsHeuresPn       .put ( h, 0 );
				super.hsHeuresRepariees.put ( h, 0 );
			}
		}
	}
}