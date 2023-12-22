package astre.vue.outils;

import javax.swing.*;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.*;

import java.util.List;
import java.util.ArrayList;

import astre.Controleur;
import astre.vue.FrameAccueil;
import astre.vue.intervenants.FrameIntervenants;
import astre.vue.parametrage.FrameParametrage;
import astre.vue.previsionnel.FramePrevisionnel;
import astre.vue.nouvelleAnnee.FrameNouvelleAnnee;
import astre.vue.etats.FrameEtats;

/** Menu de l'application
  * @author : Maxime Lemoine
  * @version : 2.0 - 21/12/2023
  * @date : 06/12/2023
  */
public class MenuBarAstre extends JMenuBar implements ActionListener
{
	private static final String REPERTOIRE = "./data/images/";

	// Identifiants des éléments du menu
	private static final String MENU			= "M";
	private static final String ITEM			= "I";
	private static final String SEPARATEUR		= "S";
	private static final String SOUS_MENU		= "m";
	private static final String ITEM_SM			= "i";
	private static final String SEPARATEUR_SM	= "s";

	// Position des éléments du menu dans le "modeleBar"
	private static final int TYPE = 0;
	private static final int NAME = 1;
	private static final int ICON = 2;
	private static final int CHAR = 3;
	private static final int KEYS = 4;

	// Attributs
	private Controleur ctrl;
	private Frame      parent;

	public MenuBarAstre ( Controleur ctrl, Frame parent )
	{
		//Initialisation
		super ( );
		this.ctrl             = ctrl;
		this.parent           = parent;

		this.initComposants ( );
	} 

	/**
	 * Initialisation des composants de la barre
	 */
	private void initComposants ( )
	{
		JMenu  menuEnCreation     = null;
		JMenu  sousMenuEnCreation = null;
		String hotkey;

		// Format du MenuBar
		String[][] modeleBar = MenuBarAstre.getModeleBar ( );

		// Générer les composants
		for ( int cptLig = 0; cptLig < modeleBar.length; cptLig++ )
		{
			String[] ligne = modeleBar[cptLig];

			switch ( ligne[TYPE] )
			{
				case MENU:
					menuEnCreation = this.creerMenu ( ligne[NAME], ligne[ICON], ligne[CHAR] );
					this.add ( menuEnCreation );
					break;

				case SOUS_MENU:
					sousMenuEnCreation = this.creerMenu ( ligne[NAME], ligne[ICON], ligne[CHAR] );
					menuEnCreation.add ( sousMenuEnCreation );
					break;

				case ITEM:
					if ( ligne.length-1 == KEYS ) { hotkey = ligne[KEYS]; }
					else { hotkey = null; }
					menuEnCreation.add ( this.creerMenui ( ligne[NAME], ligne[ICON], ligne[CHAR], hotkey ) );
					break;

				case ITEM_SM:
					if ( ligne.length-1 == KEYS ) { hotkey = ligne[KEYS]; }
					else { hotkey = null; }
					sousMenuEnCreation.add ( this.creerMenui ( ligne[NAME], ligne[ICON], ligne[CHAR], hotkey ) );
					break;

				case SEPARATEUR:
					menuEnCreation.addSeparator ( );
					break;

				case SEPARATEUR_SM:
					sousMenuEnCreation.addSeparator ( );
					break;
			}
		}
	}

	/**
	 * Simplification de la création d'un élément Menu (correspond au premier niveau)
	 */
	private JMenu creerMenu ( String nom, String image, String mnemo )
	{
		JMenu menuTmp = new JMenu ( nom );

		if ( !image.equals ( "" ) )
		{
			menuTmp.setIcon ( genererIcone( image, 20 ) );
		}

		menuTmp.setMnemonic ( mnemo.charAt( 0 ) );
		return menuTmp;
	}

	/**
	 * Simplification de la création d'un élément MenuItem (correspond au sous-niveau)
	 */
	private JMenuItem creerMenui ( String nom, String image, String mnemo, String hotkey )
	{
		JMenuItem menui = new JMenuItem ( nom );

		if ( !image.equals ( "" ) )
		{
			menui.setIcon ( genererIcone ( image, 20 ) );
		}

		if ( !mnemo.equals( "" ) )
		{
			menui.setMnemonic ( mnemo.charAt ( 0 ) );
		}

		if ( hotkey != null )
		{
			menui.setAccelerator( getEquivalentKeyStroke ( hotkey ) );
		}

		menui.addActionListener ( this );
		return menui;
	}

	/**
	 * Détection des évènements sur le JMenuBar
	 */
	public void actionPerformed ( ActionEvent e )
	{
		if ( e.getSource ( ) instanceof JMenuItem )
		{
			String nom = ( ( JMenuItem ) e.getSource () ).getText ( );
			this.allerVersPage ( nom );
		}
	}

	/**
	 * Actions à réaliser lors des selections
	 */

	private void allerVersPage ( String nom )
	{
		String[] options = MenuBarAstre.getOptionsBarre ( );

		if ( nom.equals ( options[0] ) )
		{
			new FrameAccueil      ( this.ctrl );
			this.parent.dispose ( );
		}
			
		if ( nom.equals ( options[1] ) )
			this.parent.dispose ( );

		if ( nom.equals ( options[2] ) )
		{
			new FrameParametrage  ( this.ctrl );
			this.parent.dispose ( );
		}
			
		if ( nom.contains ( "S" ) ) // cas des sous menus avec semestre
		{
			new FramePrevisionnel ( this.ctrl, Integer.parseInt( nom.charAt ( 1 ) +"" )-1 );
			this.parent.dispose ( );
		}

		if ( nom.equals ( options[9] ) )
		{
			new FrameIntervenants ( this.ctrl );
			this.parent.dispose ( );
		}
			
		if ( nom.equals ( options[10] ) )
		{
			new FrameAccueil       ( this.ctrl );
			new FrameNouvelleAnnee ( this.ctrl );
			this.parent.dispose ( );
		}

		if ( nom.equals ( options[11] ) )
		{
			new FrameEtats        ( this.ctrl );
			this.parent.dispose ( );
		}
	}

	/**
	 * Méthode utilitaire permettant de convertir une chaine de caractère en KeyStroke pour les raccourcis
	 */
	public static KeyStroke getEquivalentKeyStroke ( String hotkey )
	{
		String[] setTmp = hotkey.split ( "\\+" );
		String sTmp="";

		for ( int cpt=0; cpt<setTmp.length-1; cpt++ )
		{
			sTmp += setTmp[cpt].toLowerCase ( ) + " ";
		}
		sTmp += setTmp[ setTmp.length-1 ];

		return KeyStroke.getKeyStroke ( sTmp );
	}

	/**
	 * Méthode utilitaire permettant de générer une image redimensionnée
	 */
	public static ImageIcon genererIcone ( String image, int taille )
	{
		ImageIcon icone = new ImageIcon ( REPERTOIRE + image );
		Image imgDim = icone.getImage ( ).getScaledInstance ( taille, taille, Image.SCALE_SMOOTH );
		return new ImageIcon ( imgDim );
	}

	/**
	 * Méthodes qui permet de récupérer toutes les options du MenuBar
	 */
	public static final String[] getOptionsBarre ( )
	{
		List<String> options = new ArrayList<> ( );

		for ( String[] ligne : MenuBarAstre.getModeleBar ( ) )
		{
			if ( ligne[TYPE].equals ( ITEM ) || ligne[TYPE].equals ( ITEM_SM ) )
			{
				options.add ( ligne[NAME] );
			}
		}

		return options.toArray ( new String[options.size ( )] );
	}

	/**
	 * Méthodes très facilement modifiable pour ajuster les éléments du MenuBar
	 */
	public static String[][] getModeleBar ( )
	{
		return new String[][] {
			{	MENU, 				"Fichier",			"",					"F"				},
			{		ITEM, 			"Accueil",			"accueil.png",		"A", "CTRL+H"	},
		//	{		SEPARATEUR																},
		//	{		SOUS_MENU, 		"Exporter...",		"exporter.png",		"E", "CTRL+P"	},
		//	{			ITEM_SM,	"Format PDF",		"pdf.png",			"P" 			},
		//	{			ITEM_SM,	"Format HTML",		"html.png",			"H" 			},
			{		SEPARATEUR																},
			{		ITEM, 			"Quitter",			"quitter.png",		"Q", "CTRL+Q"	},
			{	MENU, 				"Edition",			"",					"E", "CTRL+E"	},
			{		ITEM, 			"Paramètres",		"parametres.png",	"P"				},
			{		SEPARATEUR																},
			{		SOUS_MENU, 		"Prévisionnel...",	"previsionnel.png",	"P"				},
			{			ITEM_SM,	"S1",				"",					"1" 			},
			{			ITEM_SM,	"S2",				"",					"2" 			},
			{			ITEM_SM,	"S3",				"",					"3" 			},
			{			ITEM_SM,	"S4",				"",					"4" 			},
			{			ITEM_SM,	"S5",				"",					"5" 			},
			{			ITEM_SM,	"S6",				"",					"6" 			},
			{		ITEM, 			"Intervenants",		"enseignants.png",	"I"				},
			{		ITEM, 			"Nouvelle année",	"parametres.png",	"N"				},
			{	MENU, 				"Affichage",		"",					"A"				},
			{		ITEM, 			"Etats",			"apercu.png",		"E", "CTRL+P"	}
								};
	}

}
