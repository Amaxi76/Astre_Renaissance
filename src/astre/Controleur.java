package astre;

/** Classe Controleur 
  * @author : Maximilien Lesterlin
  * @version : 1.0 - 11/12/2023
  * @date : 06/12/2023
  */

import astre.vue.*;
import astre.vue.previsionnel.module.FrameModule;

public class Controleur
{
	private FrameAccueil ihm;
	private FrameModule  frmModule;
	
	public Controleur ( )
	{
		//
		this.ihm = new FrameAccueil ( this );
	}

	public void ouvrirFrameModule ( )
	{
		this.frmModule = new FrameModule ( this );
	}

	public static void main ( String[] args )
	{
		new Controleur ( );
	}
}
