package astre.modele;

/** Classe Ressource 
  * @author : Maximilien Lesterlin
  * @version : 1.0 - 11/12/2023
  * @date : 06/12/2023
  */

public class Ressource extends Module
{
	public Ressource ( Semestre semestre, String code, String libLong, String libCourt )
	{
		super ( semestre, code, libLong, libCourt );

		for ( Heure h : Heure.getHeures ( ) )
		{
			if ( h.getNom ( ).equals ( "CM" ) || h.getNom ( ).equals ( "TD" ) ||
			     h.getNom ( ).equals ( "TP" ) || h.getNom ( ).equals ( "HP" )    )
			{
				super.hsHeuresPn       .put ( h, 0 );
				super.hsHeuresRepariees.put ( h, 0 );
			}
		}
	}
}