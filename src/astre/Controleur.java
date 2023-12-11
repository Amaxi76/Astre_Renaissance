package astre;

/** Classe Controleur 
  * @author : Maximilien Lesterlin
  * @version : 1.0 - 11/12/2023
  * @date : 06/12/2023
  */

import astre.vue.*;

public class Controleur
{
	private FrameAccueil ihm;
	
	public Controleur ( )
	{
		//
		this.ihm = new FrameAccueil ( this );
	}

	public static void main ( String[] args )
	{
		new Controleur ( );
	}
}
