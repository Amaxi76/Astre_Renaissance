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

	private PanelModuleLabel    panelModuleLabel;
	private PanelPNLocal	    panelPNLocal;
	private PanelPNLocalBis	    panelPNLocalBis;
	private PanelRepartition    panelRepartition;
	private PanelRepartitionBis panelRepartitionBis;

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

		this.panelModuleLabel    = new PanelModuleLabel  ( this.ctrl, this );
		this.panelPNLocal        = new PanelPNLocal      ( this.ctrl, this );
		this.panelPNLocalBis     = new PanelPNLocalBis   ( this.ctrl );
		this.panelRepartition    = new PanelRepartition  ( this.ctrl, this );
		this.panelRepartitionBis = new PanelRepartitionBis ( this.ctrl );

		this.cbValidation = new JCheckBox ( "Validation" );


		this.add ( panelModuleLabel, BorderLayout.NORTH  );


		JPanel panelCentre  = new JPanel ( new GridBagLayout() );
		
		this.add ( panelCentre, BorderLayout.CENTER );

		GridBagConstraints gbcC = new GridBagConstraints();
		gbcC.insets = new Insets ( 5, 10, 15, 10 );

		gbcC.gridy = 0;
		gbcC.gridx = 0;
		panelCentre.add ( this.panelRepartition,    gbcC );
		panelCentre.add ( this.panelRepartitionBis, gbcC );
		this.panelRepartitionBis.setVisible ( false );


		gbcC.gridy = 1;
		gbcC.gridx = 0;
		panelCentre.add ( new PanelAffectation ( this.ctrl ), gbcC );
		

		JPanel panelOuest = new JPanel ( new GridBagLayout() );

		GridBagConstraints gbcO = new GridBagConstraints();
		gbcO.insets = new Insets ( 5, 10, 15, 10 );

		this.add ( panelOuest, BorderLayout.WEST );

		gbcO.gridy = 0;
		gbcO.gridx = 0;
		panelOuest.add ( this.panelPNLocal,    gbcO  );
		panelOuest.add ( this.panelPNLocalBis, gbcO  );
		this.panelPNLocalBis.setVisible ( false );

		gbcO.gridy = 1;
		gbcO.gridx = 0;
		panelOuest.add ( this.cbValidation, gbcO );


		this.add ( new PanelModuleBouton ( this.ctrl ), BorderLayout.SOUTH);

		this.setVisible ( true );
	}

	public PanelModuleLabel getPanelModuleLabel ( ) { return this.panelModuleLabel; }

	public void setVisiblePanels(String typeModule)
	{
		if (typeModule.equals("SAE") || typeModule.equals("Stage"))
		{
			this.panelRepartition   .setVisible ( false );
			this.panelRepartitionBis.setVisible ( true );

			this.panelPNLocal		.setVisible ( false );
			this.panelPNLocalBis	.setVisible ( true );
		}
		else
		{
			this.panelRepartition   .setVisible ( true );
			this.panelRepartitionBis.setVisible ( false );

			this.panelPNLocal		.setVisible ( true );
			this.panelPNLocalBis	.setVisible ( false );
		}
	}
}
