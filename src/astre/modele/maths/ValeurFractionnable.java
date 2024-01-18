package astre.modele.maths;

/**
 * Objet permettant de stocker une valeur sous la forme d'une fraction si nécessaire
 * @version : 1.0 - 17/01/2024
 * @date : 17/01/2024
 * @author Maxime Lemoine
 */

public class ValeurFractionnable extends Fraction
{
	private String[] fractionsValide = { "1/2", "1/3", "2/3", "1/4", "3/4", "1/5", "2/5", "3/5", "4/5" };

	public ValeurFractionnable ( Double nombre  ) { super ( nombre ); }
	public ValeurFractionnable ( String objet   ) { super ( objet  ); }
	public ValeurFractionnable ( Integer nombre ) { super ( nombre ); }

	@Override
	public String toString ( )
	{
		String val = super.toString ( );
		for ( String fraction : fractionsValide )
		{
			if ( val.equals ( fraction ) )
				return val;
		}
		return super.toDouble() + "";
	}
}