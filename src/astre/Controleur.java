package astre;

/** Classe Controleur 
  * @author : Maximilien Lesterlin et Maxime
  * @version : 1.0 - 11/12/2023
  * @date : 06/12/2023
  */

import astre.modele.*;
import astre.vue.*;
import astre.vue.outils.PopUpErreur;

public class Controleur
{
	private FrameAccueil ihm;
	private Astre        metier;
	
	public Controleur ( )
	{
		this.ihm    = new FrameAccueil ( this );
		this.metier = new Astre        (      );
	}

	public static void afficherErreur ( String titre, String message )
	{
		new PopUpErreur( titre, message );
	}

	public static void main ( String[] args )
	{
		afficherErreur ( "Erreur java", "petite fenetre de test pour afficher les erreurs" );
		new Controleur ( );
	}
}
