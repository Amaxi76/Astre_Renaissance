package astre.vue.previsionnel.module;

import java.awt.*;

import javax.swing.JCheckBox;
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

	private JCheckBox cbValidation;

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

		this.cbValidation = new JCheckBox ( "Validation" );


		JPanel panelCentre  = new JPanel ( new GridBagLayout() );

		GridBagConstraints gbcC = new GridBagConstraints();
		gbcC.insets = new Insets ( 5, 10, 15, 10 );

		this.add ( new PanelModuleLabel  ( this.ctrl ), BorderLayout.NORTH  );
		this.add ( new PanelPNLocal      ( this.ctrl ), BorderLayout.WEST   );
		
		this.add ( panelCentre, BorderLayout.CENTER                         );

		gbcC.gridy = 0;
		gbcC.gridx = 0;
		panelCentre.add ( new PanelRepartition  ( this.ctrl ), gbcC );

		gbcC.gridy = 1;
		gbcC.gridx = 0;
		panelCentre.add ( new PanelAffectation ( this.ctrl ), gbcC );
		

		JPanel panelSud = new JPanel ( new GridBagLayout() );

		GridBagConstraints gbcS = new GridBagConstraints();
		gbcS.insets = new Insets ( 5, 10, 15, 10 );

		gbcS.gridy = 0;
		gbcS.gridx = 0;
		panelSud.add ( new PanelModuleBouton ( this.ctrl ), gbcS  );

		gbcS.gridy = 1;
		gbcS.gridx = 0;
		panelSud.add ( this.cbValidation, gbcS );

		this.setVisible ( true );
	}
}