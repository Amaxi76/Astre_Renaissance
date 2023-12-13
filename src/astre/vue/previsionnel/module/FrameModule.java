package astre.vue.previsionnel.module;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

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

		this.setSize   ( 1000, 700               );
		this.setTitle  ( "Prévisionnel : Module" );

		/* ------------------------- */
		/* Création des composants   */
		/* ------------------------- */

		JPanel panelCentre  = new JPanel ( new GridBagLayout() );

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets ( 5, 10, 15, 10 );

		this.add ( new PanelModuleLabel  ( this.ctrl ), BorderLayout.NORTH  );
		this.add ( new PanelPNLocal      ( this.ctrl ), BorderLayout.WEST   );
		
		this.add ( panelCentre, BorderLayout.CENTER                         );

		gbc.gridy = 0;
		gbc.gridx = 0;
		panelCentre.add ( new PanelRepartition  ( this.ctrl ), gbc );

		gbc.gridy = 1;
		gbc.gridx = 0;
		panelCentre.add ( new PanelAffectation ( this.ctrl ), gbc );

		this.add ( new PanelModuleBouton ( this.ctrl ), BorderLayout.SOUTH  );

		this.setVisible ( true );
	}
}