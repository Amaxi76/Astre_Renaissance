package astre.vue.previsionnel.module;

import javax.swing.JFrame;
import javax.swing.JPanel;

import astre.Controleur;
import astre.vue.previsionnel.PanelBouton;
import astre.vue.previsionnel.PanelEnsSemestre;

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
	
	private JPanel panelModule;

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

		this.setSize     ( 1000, 700 );
		this.setTitle    ( "Prévisionnel : Module"   );

		/* ------------------------- */
		/* Création des composants   */
		/* ------------------------- */

		this.add ( new PanelModule  ( this.ctrl ) );


		this.setVisible               ( true        );
	}
}
