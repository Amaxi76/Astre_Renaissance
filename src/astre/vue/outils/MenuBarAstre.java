package astre.vue.outils;

import javax.swing.*;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.*;
import javax.swing.event.*;

import astre.Controleur;
import astre.vue.FrameAccueil;
import astre.vue.intervenants.FrameIntervenants;
import astre.vue.parametrage.FrameParametrage;
import astre.vue.previsionnel.FramePrevisionnel;

/** Menu de l'application
  * @author : Maxime Lemoine
  * @version : 2.0 - 14/12/2023
  * @date : 06/12/2023
  */

//TODO : désactiver les détections via le JMenu car une fois cliqué, la souris peut rester et glisser sur les différents boutons et quitter sans cliquer
// ou probablement supprimer les MenuListener pour mettre un ActionListener sur les JMenu

//TODO : ajouter les sous-menus

public class MenuBarAstre extends JMenuBar implements ActionListener
{
	private static final String REPERTOIRE = "../data/images/";
	
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
	private JFrame     parent; //TODO: à supprimer

	public MenuBarAstre ( Controleur ctrl, JFrame parent )
	{
		//Initialisation
		super ( );
		//TODO: ne fonctionne pas encore : this.setMargin​ ( new Insets ( 2,20,2,20 ) );
		
		this.ctrl             = ctrl;
		this.parent           = parent;
		
		this.initComposants();
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

		if( hotkey != null )
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

	private void allerVersPage ( String nom ) //TODO: ajouter tous les accès aux frames

	{
		// TODO: récupérer la chaine "options" d'une autre manière qu'en dur dans le code (chercher dans le modèle du tableau ou créer une hashmap)
		String[] options = {, "Paramètres", "Prévisionnel", "Intervenants", "Quitter", "Nouvelle année"};
		System.out.println( "selectionné : " + nom ); // TODO: à supprimer
		
		if ( nom.equals( options[0] ) ){ new FrameAccueil      ( this.ctrl );  }
		if ( nom.equals( options[1] ) ){ new FrameParametrage  ( this.ctrl );  }
		if ( nom.equals( options[2] ) ){ new FramePrevisionnel ( this.ctrl );  }
		if ( nom.equals( options[3] ) ){ new FrameIntervenants ( this.ctrl );  }
		if ( nom.equals( options[4] ) ){ parent.dispose ( );                   }
		if ( nom.equals( options[5] ) ){ new FrameNouvelleAnnee ( this.ctrl ); }
		
		if ( java.util.Arrays.asList(options).contains(nom) )
		{
			this.parent.dispose ( );
		}
	}
	
	/*private void allerVersPage ( Class c )
	{
		try
		{
			this.parent.dispose ( );
			
			// Obtenez le constructeur avec un paramètre de type Ctrl (ou le type approprié)
        	java.lang.reflect.Constructor<?> constructor = c.getDeclaredConstructor(Controleur.class);

	        // Instanciez la nouvelle classe en passant l'objet ctrl dans le constructeur
    	    constructor.newInstance(this.ctrl);
		}
		catch ( Exception e )
		{
			System.out.println( "Erreur menubar : " + e );
		}
	}*/

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
	 * Méthodes modifiable très facilement pour ajuster les éléments du MenuBar
	 */
	public static String[][] getModeleBar ( )
	{
		return new String[][] {
			{	MENU, 				"Fichier",			"",					"F"				},
			{		ITEM, 			"Accueil",			"",					"A", "CTRL+H"	},
			{		SEPARATEUR																},
			{		SOUS_MENU, 		"Exporter...",		"exportation.png",	"E", "CTRL+P"	},
			{			ITEM_SM,	"Format PDF",		"pdf.png",			"P" 			},
			{			ITEM_SM,	"Format HTML",		"html.png",			"H" 			},
			{		SEPARATEUR																},
			{		ITEM, 			"Quitter",			"",					"Q", "CTRL+Q"	},
			{	MENU, 				"Edition",			"",					"E", "CTRL+E"	},
			{		ITEM, 			"Paramètres",		"",					"P"				},
			{		SEPARATEUR																},
			{		ITEM, 			"Prévisionnel",		"modules.png",		"P"				},
			{		ITEM, 			"Intervenants",		"enseignants.png",	"I"				},
			{		ITEM, 			"Nouvelle année",	"",					"N"				},
			{	MENU, 				"Affichage",		"",					"A"				},
			{		ITEM, 			"Etats",			"apercu.png",		"E"				},
								};
								
		/* //Première version (fonctionne)
		return new String[][] {
			{ "M", "Retour Accueil","",					"A", "CTRL+H"	},

			{ "M", "Saisies",		"",					"S"				},
			{ "I", "Paramètre",		"",					""				},
			{ "S"														},
			{ "I", "Prévisionnel" ,	"modules.png",		""				},
			{ "I", "Intervenants",	"enseignants.png",	""				},
			
			{ "M", "Etats",			"apercu.png",		"A"				},

			{ "M", "Exportation",	"exportation.png",	"E"				},
			{ "I", "Format PDF",	"pdf.png",			"P", "CTRL+P"	},
			{ "I", "Format HTML" ,	"html.png",			"H"				},
			
			{ "M", "Quitter",		"",					"Q", "CTRL+Q"	},
								};*/
	}

}
