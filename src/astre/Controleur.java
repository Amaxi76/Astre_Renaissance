package astre;

/** Classe Controleur 
  * @author : Maximilien Lesterlin, Maxime Lemoine, Mateo Sa et Clémentin Ly
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

	public <T> List<T>   getTable              ( Class<T> type   ) { return this.metier.getTable              ( type        ); }

	public Semestre      getSemestre           ( int      numSemestre ) { return this.metier.getSemestre      ( numSemestre ); }
	public Object[][]    getTableauModule      ( int      numSemestre ) { return this.metier.getTableauModule ( numSemestre ); } //TODO: à refaire / enlever
	public Object[][]    getTableau            ( Class<?> type        ) { return this.metier.getTableau       ( type        ); }
	public Heure         getHeure              ( int      nom         ) { return this.metier.getHeure         ( nom         ); }
	public Heure         getHeure              ( String   nom         ) { return this.metier.getHeure         ( nom         ); }
	public Contrat       getContrat            ( String   nom         ) { return this.metier.getContrat       ( nom         ); }
	public ModuleIUT     getModule             ( String   nom         ) { return this.metier.getModule        ( nom         ); }

	public void update ( Object o ) { this.metier.update ( o ); }
	public void insert ( Object o ) { this.metier.insert ( o ); }
	public void delete ( Object o ) { this.metier.delete ( o ); }

	public void majTableauBD ( Object[][] tab, Class<?> type ) { this.metier.majTableauBD ( tab, type ); }

	public static void afficherErreur ( String titre, String message )
	{
		new PopUpErreur ( titre, message );
	}

	public boolean nouvelleAnnee     ( ) { return this.metier.nouvelleAnnee    (); }
	public boolean nouvelleAnneeZero ( ) { return this.metier.nouvelleAnneeZero(); }

	public static void main ( String[] args )
	{
		new Controleur ( );
	}
}
