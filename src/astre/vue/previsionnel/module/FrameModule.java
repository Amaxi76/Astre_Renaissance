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

	private PanelModuleLabel  panelModuleLabel;
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

		this.setSize   ( 1300, 1000       );
		this.setTitle  ( "Prévisionnel : Module" );
		this.setLocationRelativeTo ( null           );

		/* ------------------------- */
		/* Création des composants   */
		/* ------------------------- */

		this.panelModuleLabel  = new PanelModuleLabel  ( this.ctrl );
		this.cbValidation = new JCheckBox ( "Validation" );


		JPanel panelCentre  = new JPanel ( new GridBagLayout() );

		GridBagConstraints gbcC = new GridBagConstraints();
		gbcC.insets = new Insets ( 5, 10, 15, 10 );

		this.add ( panelModuleLabel, BorderLayout.NORTH  );
		
		this.add ( panelCentre, BorderLayout.CENTER );

		gbcC.gridy = 0;
		gbcC.gridx = 0;
		panelCentre.add ( new PanelRepartition  ( this.ctrl ), gbcC );

		gbcC.gridy = 1;
		gbcC.gridx = 0;
		panelCentre.add ( new PanelAffectation ( this.ctrl ), gbcC );
		

		JPanel panelOuest = new JPanel ( new GridBagLayout() );

		GridBagConstraints gbcO = new GridBagConstraints();
		gbcO.insets = new Insets ( 5, 10, 15, 10 );

		this.add ( panelOuest, BorderLayout.WEST );

		gbcO.gridy = 0;
		gbcO.gridx = 0;
		panelOuest.add ( new PanelPNLocal ( this.ctrl, this ), gbcO  );

		gbcO.gridy = 1;
		gbcO.gridx = 0;
		panelOuest.add ( this.cbValidation, gbcO );


		this.add ( new PanelModuleBouton ( this.ctrl ), BorderLayout.SOUTH);

		this.setVisible ( true );
	}

	public PanelModuleLabel getPanelModuleLabel ( ) { return this.panelModuleLabel; }
}
