package astre.vue.previsionnel;

/** Classe FramePrevisionnel
  * @author : Clémentin Ly
  * @version : 1.0 - 11/12/2023
  * @date : 06/12/2023
  */

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;

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

		this.setLayout( new BorderLayout ( ) );

		this.add ( new PanelEnsSemestre  ( this.ctrl ), BorderLayout.CENTER );
		this.add ( new PanelBouton ( this.ctrl ), BorderLayout.SOUTH );

		this.setVisible ( true );
	}
}