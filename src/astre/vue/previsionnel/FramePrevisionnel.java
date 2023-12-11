package astre.vue.previsionnel;

import java.awt.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

import astre.Controleur;

public class FramePrevisionnel extends JFrame
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	private Controleur ctrl;
	
	private JPanel panelPrevisionnel;

	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/

	/** Constructeur de FramePrevisionnel
	 * @param ctrl le controleur
	 * 
	 */

	public FramePrevisionnel ( Controleur ctrl )
	{
		this.ctrl = ctrl;

		this.setSize     ( 1000, 700 );
		this.setTitle    ( "Prévisionnel"   );

		/* ------------------------- */
		/* Création des composants   */
		/* ------------------------- */

		//panelPrevisionnel = new JPanel(new BorderLayout());

		//this.add(panelPrevisionnel);
		
		this.add ( new PanelPrevisionnel ( this.ctrl ) );


		this.setDefaultCloseOperation ( EXIT_ON_CLOSE );
		this.setVisible               ( true        );
	}
}
