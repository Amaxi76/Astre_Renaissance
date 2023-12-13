package astre;

/** Classe Controleur 
  * @author : Maximilien Lesterlin et Maxime Lemoine
  * @version : 2.0 - 12/12/2023
  * @date : 06/12/2023
  */

import astre.modele.elements.*;

import java.util.List;

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

	public Semestre      getSemestre           ( int numSemestre ) { return this.metier.getSemestre           ( numSemestre ); }
	public Heure         getHeure              ( String nom      ) { return this.metier.getHeure              ( nom         ); }
	public Object[][]    getTableauModule      ( int numSemestre ) { return this.metier.getTableauModule      ( numSemestre ); }
	public Object[][]    getTableauIntervenant (                 ) { return this.metier.getTableauIntervenant (             ); }
	public Object[][]    getTableauIntervient  (                 ) { return this.metier.getTableauIntervient  (             ); }
	public List<Contrat> getContrats           (                 ) { return this.metier.getContrats           (             ); }

	public void majSemestre ( Semestre s ) { this.metier.majSemestre ( s ); }

	public static void afficherErreur ( String titre, String message )
	{
		new PopUpErreur ( titre, message );
	}

	public static void main ( String[] args )
	{
		//afficherErreur ( "Erreur java", "petite fenetre de test pour afficher les erreurs" );
		new Controleur ( );
	}
}
