package astre.vue.outils;

import astre.vue.FrameAccueil;
import astre.Controleur;
import javax.swing.JFrame;
import java.awt.Component;
import java.awt.Image;
import java.awt.Toolkit;

/** Classe principale pour la génération de fenetres dans l'application Astre
  * @author : Maxime Lemoine
  * @version : 1.0 - 15/12/2023
  * @date : 15/12/2023
  */
public class AFrame extends JFrame
{
	protected Controleur ctrl;
	
	/**
	 * Constructeur général
	 */
	public AFrame ( Controleur ctrl )
	{
		// Image
		Image icon = Toolkit.getDefaultToolkit ( ).getImage ( "./data/images/astre_petit.png" );  
		this.setIconImage ( icon ); 
		ctrl.setFrameActuelle ( this );
	
		// attributs
		this.ctrl = ctrl;
		
		// barre de menu
		this.setJMenuBar ( new MenuBarAstre ( this.ctrl, this ) );
		
		// propriétés
		this.setDefaultCloseOperation ( EXIT_ON_CLOSE );
	}
	
	/**
	 * Récupérer la fenetre utilisée pour le composant
	 */
	public static void fermerFenetreContenant ( Component compo ) //ancien nom : fermer
	{
		javax.swing.SwingUtilities.getWindowAncestor ( compo ).dispose ( );
	}
	
	public static void retourAccueil ( Controleur ctrl )
	{
		ctrl.setFrameActuelle ( new FrameAccueil ( ctrl ) );
	}
}
