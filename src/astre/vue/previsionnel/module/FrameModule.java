package astre.vue.previsionnel.module;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import astre.Controleur;

/** Classe FrameModule
 * @author : Clémentin Ly
* @version : 1.0 - 11/12/2023
* @date : 11/12/2023
*/

public class FrameModule extends JFrame
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	private Controleur ctrl;

	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/

	/** Constructeur de FrameModule
	 * @param ctrl le controleur
	 * 
	 */

	public FrameModule ( Controleur ctrl )
	{
		this.ctrl = ctrl;

		this.setLayout ( new BorderLayout ( ) );
		this.setSize   ( 1000, 700 );
		this.setTitle  ( "Prévisionnel : Module" );

		/* ------------------------- */
		/* Création des composants   */
		/* ------------------------- */

		this.add ( new PanelModuleLabel  ( this.ctrl ), BorderLayout.NORTH  );
		this.add ( new PanelPNLocal      ( this.ctrl ), BorderLayout.WEST   );
		this.add ( new PanelRepartition  ( this.ctrl ), BorderLayout.EAST   );
		this.add ( new PanelModuleBouton ( this.ctrl ), BorderLayout.SOUTH  );

		this.setVisible ( true );
	}
}
