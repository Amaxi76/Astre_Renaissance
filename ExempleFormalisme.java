public class ExempleFormalisme
{
	private static int[] tab;

	private int arg;

	public ExempleFormalisme ( int arg )
	{
		// Tous les cas qui sont faux
		if ( test == true )
		{
			return;
		}

		// Code a éxécuter si les tests sont réussis
	}

	/*---------------------------------------*/
	/*                GETTEUR                */
	/*---------------------------------------*/

	private int getArg  ( ) { return this.arg; }
	private int getArg2 ( ) { return this.arg; }


	/*---------------------------------------*/
	/*                SETTEUR                */
	/*---------------------------------------*/

	private void setArg ( int valeur ) { this.arg = valeur; }


	/*---------------------------------------*/
	/*                TESTEUR                */
	/*---------------------------------------*/

	private int estArg ( ) { return this.arg == arg; }

	// On ne retourne pas le metier, on fait le lien dans le controleur
	private boolean estMetier ( ) { return this.metier.test ( ); }
	
}
