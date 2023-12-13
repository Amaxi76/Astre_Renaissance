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
  * @version : 1.0 - 11/12/2023
  * @date : 06/12/2023
  */

//TODO : désactiver les détections via le JMenu car une fois cliqué, la souris peut rester et glisser sur les différents boutons et quitter sans cliquer
// ou probablement supprimer les MenuListener pour mettre un ActionListener sur les JMenu

public class MenuBarAstre extends JMenuBar implements ActionListener, MenuListener
{
	private static final String REPERTOIRE = "../data/images/";
	private Controleur ctrl;
	private JFrame     parent;
	
	//Position des éléments d'un menu dans le "modeleBar"
	private final int TYPE = 0;
	private final int NAME = 1;
	private final int ICON = 2;
	private final int CHAR = 3;
	private final int KEYS = 4;

	public MenuBarAstre ( Controleur ctrl, JFrame parent )
	{
		//Initialisation
		super ( );
		//TODO: ne fonctionne pas encore : this.setMargin​ ( new Insets ( 2,20,2,20 ) );
		
		this.ctrl   = ctrl;
		this.parent = parent;
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

		menuTmp.addMenuListener ( this );
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
	 * Détection des évènements sur le JMenu
	 */
	public void menuSelected​ ( MenuEvent e )
	{
		if ( e.getSource ( ) instanceof JMenu  )
		{
			String nom = ( ( JMenu ) e.getSource () ).getText ( );

			this.allerVersPage ( nom );
		}
	}
	
	public void menuCanceled   ( MenuEvent e ) { }
	public void menuDeselected ( MenuEvent e ) { }
	
	private void allerVersPage ( String nom )
	{
		String[] options = {"Retour Accueil", "Paramètre", "Prévisionnel", "Intervenants", "Quitter"};
		System.out.println( "selectionné : " + nom );
		
		if ( nom.equals( options[0] ) ){ new FrameAccueil      ( this.ctrl ); }
		if ( nom.equals( options[1] ) ){ new FrameParametrage  ( this.ctrl ); }
		if ( nom.equals( options[2] ) ){ new FramePrevisionnel ( this.ctrl ); }
		if ( nom.equals( options[3] ) ){ new FrameIntervenants ( this.ctrl ); }
		if ( nom.equals( options[4] ) ){ parent.dispose ( );                    }
		//if ...  "Etats" 			-> this.allerVersPage ( FrameEtats.class );
		
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
		/*return new String[][] {
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
								};*/
		
		return new String[][] {
			{ "M", "Retour Accueil","",		"A", "CTRL+H"	},

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
								};
	}

}
