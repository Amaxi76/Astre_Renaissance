package astre.vue.outils;

import javax.swing.*;
import java.awt.Image;
import java.awt.event.*;

/** Page de gestion des intervenants
  * @author : Maxime Lemoine
  * @version : 1.0 - 11/12/2023
  * @date : 06/12/2023
  */
  
public class MenuBarAstre extends JMenuBar implements ActionListener
{
	private static final String REPERTOIRE = "../../../../data/images/";
	
	//Position des éléments d'un menu dans le "modeleBar"
	private final int TYPE = 0;
	private final int NAME = 1;
	private final int ICON = 2;
	private final int CHAR = 3;
	private final int KEYS = 4;

	public MenuBarAstre ( )
	{
		//Initialisation
		super ( );
		JMenu menuEnCreation = null;

		//Format du MenuBar
		String[][] modeleBar = MenuBarAstre.getModeleBar ( );
		String hotkey;

		for ( int cptLig = 0; cptLig < modeleBar.length; cptLig++ )
		{
			switch ( modeleBar[cptLig][TYPE] )
			{
				case "M":
					menuEnCreation = this.creerMenu ( modeleBar[cptLig][NAME], modeleBar[cptLig][ICON], modeleBar[cptLig][CHAR] );
					this.add ( menuEnCreation );
					break;

				case "I":
					if ( modeleBar[cptLig].length-1 == KEYS ) { hotkey = modeleBar[cptLig][KEYS]; }
					else { hotkey = null; }
					menuEnCreation.add ( this.creerMenui ( modeleBar[cptLig][NAME], modeleBar[cptLig][ICON], modeleBar[cptLig][CHAR], hotkey ) );
					break;

				case "S":
					menuEnCreation.addSeparator ( );
			}
		}
	}

	/**
	 * Simplification de la création d'un élément Menu (correspond au premier niveau)
	 */
	public JMenu creerMenu ( String nom, String image, String mnemo )
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
	public JMenuItem creerMenui ( String nom, String image, String mnemo, String hotkey )
	{
		JMenuItem menui = new JMenuItem ( nom );

		if ( !image.equals ( "" ) )
		{
			menui.setIcon ( genererIcone ( image, 20 ) );
		}

		menui.setMnemonic ( mnemo.charAt ( 0 ) );

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
		//A décomenter lorsque l'on aura un lien vers les autres pages
		/*if ( e.getSource ( ) instanceof JMenuItem )
		{
			String nom = ( ( JMenuItem ) e.getSource () ).getText ( );

			switch ( nom )
			{
				case "Semestres" 	-> this.ctrl.afficherSemestres ( );
				case "Enseignants" 	-> this.ctrl.afficherEnseignants ( );
				case "Modules" 		-> this.ctrl.afficherModules ( );
			}
		}*/
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
		String REPERTOIRE = "imagesMenu/";
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
			{ "M", "Gestion",		"gestion.png",		"G"				},
			{ "I", "Semestres",		"semestres.png",	"S",			},
			{ "I", "Enseignants" ,	"enseignants.png",	"E",			},
			{ "I", "Modules",		"modules.png",		"M"				},
			{ "S"														},
			{ "I", "Horaires",		"horaires.png",		"H",			},

			{ "M", "Aperçu",		"apercu.png",		"A"				},
			{ "I", "Enseignants",	"",					"E",			},
			{ "I", "Modules" ,		"",					"M", 			},
			{ "I", "Semestres",		"",					"S"				},
			{ "S"														},
			{ "I", "Général",		"",					"G", 			},

			{ "M", "Exportation",	"exportation.png",	"E"				},
			{ "I", "Format PDF",	"pdf.png",			"P", "CTRL+P"	},
			{ "I", "Format HTML" ,	"html.png",			"H", 			},
								};
	}

}
