package astre.vue.outils;

import astre.vue.outils.MenuBarAstre;
import astre.Controleur;
import javax.swing.JFrame;
import java.awt.Component;
import java.awt.Frame;

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
		// Image  img = null;
		// Image icon = null;


		// try
		// {
		// 	img = new ImageIcon ( ImageIO.read ( Controleur.class.getResourceAsStream ( "/images/imgAccueil.png" ) ) ).getImage();
		// }
		// catch ( Exception e ) { }
		
		// try
		// {
		// 	icon = new ImageIcon ( ImageIO.read ( Controleur.class.getResourceAsStream ( "/images/icon.png" ) ) ).getImage();
		// }
		// catch ( Exception e ) { }

		//Image scaledImg = img.getScaledInstance ( 1000, 700, Image.SCALE_SMOOTH );
	
		//this.setIconImage             ( icon          );
	
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
	public static void fermer ( Component compo )
	{
		java.awt.Window window = javax.swing.SwingUtilities.getWindowAncestor ( compo );

		if ( window instanceof Frame )
		{
			Frame parentFrame = ( Frame ) window;
			parentFrame.dispose ( );
		}
	}
	
	/*public void allerVersPage ( Class c )
	{
		try
		{
			this.dispose ( );
			
			// Obtenez le constructeur avec un paramètre de type Ctrl (ou le type approprié)
			java.lang.reflect.Constructor<?> constructor = c.getDeclaredConstructor ( Controleur.class );

			// Instanciez la nouvelle classe en passant l'objet ctrl dans le constructeur
			constructor.newInstance ( this.ctrl );
		}
		catch ( Exception e )
		{
			System.out.println( "Erreur génération de la page " +c.getClass().getName()+ ": " + e );
		}
	}*/
}
