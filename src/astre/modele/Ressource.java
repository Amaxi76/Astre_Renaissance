// @author : Maximilien Lesterlin
package astre.modele;

public class Ressource extends Module
{
	public Ressource ( Semestre semestre, String code, String libLong, String libCourt )
	{
		super ( semestre, code, libLong, libCourt );


		for ( Heure h : Heure.getHeures ( ) )
		{
			switch ( h.getNom ( ) )
			{
				case "CM": super.hsHeure.put ( h, 0 );
				case "TD": super.hsHeure.put ( h, 0 );
				case "TP": super.hsHeure.put ( h, 0 );
				case "PN": super.hsHeure.put ( h, 0 );
				case "HP": super.hsHeure.put ( h, 0 );
				default:
					break;
			}
			
		}
	}
}