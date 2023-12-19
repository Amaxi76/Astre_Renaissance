package astre.vue.previsionnel.module;

import java.awt.*;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import astre.Controleur;
import astre.modele.elements.ModuleIUT;

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

	private Controleur          ctrl;

	private PanelModuleLabel    panelModuleLabel;
	private PanelModuleBouton   panelModuleBouton;
	private PanelPNLocal	    panelPNLocal;
	private PanelPNLocalBis	    panelPNLocalBis;
	private PanelPNLocalPPP	    panelPNLocalPPP;
	private PanelRepartition    panelRepartition;
	private PanelRepartitionBis panelRepartitionBis;
	private PanelRepartitionPPP panelRepartitionPPP;
	private PanelAffectation    panelAffectation;

	private JCheckBox           cbValidation;

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
		this.setLocationRelativeTo ( null            );

		/* ------------------------- */
		/* Création des composants   */
		/* ------------------------- */

		this.panelModuleLabel    = new PanelModuleLabel    ( this.ctrl, this );
		this.panelModuleBouton   = new PanelModuleBouton   ( this.ctrl, this );
		this.panelPNLocal        = new PanelPNLocal        ( this.ctrl, this );
		this.panelPNLocalBis     = new PanelPNLocalBis     ( this.ctrl       );
		this.panelPNLocalPPP     = new PanelPNLocalPPP     ( this.ctrl       );
		this.panelRepartition    = new PanelRepartition    ( this.ctrl, this );
		this.panelRepartitionBis = new PanelRepartitionBis ( this.ctrl, this );
		this.panelRepartitionPPP = new PanelRepartitionPPP ( this.ctrl, this );
		this.panelAffectation    = new PanelAffectation    ( this.ctrl       );

		this.cbValidation = new JCheckBox ( "Validation" );

		/*---------*/
		/*  Nord   */
		/*---------*/

		this.add ( panelModuleLabel, BorderLayout.NORTH  );

		/*----------*/
		/*  Centre  */
		/*----------*/

		JPanel panelCentre  = new JPanel ( new GridBagLayout ( ) );
		
		this.add ( panelCentre, BorderLayout.CENTER );

		GridBagConstraints gbcC = new GridBagConstraints ( );
		gbcC.insets = new Insets ( 5, 10, 15, 10 );

		gbcC.gridy = 0;
		gbcC.gridx = 0;
		panelCentre.add ( this.panelRepartition,    gbcC );
		panelCentre.add ( this.panelRepartitionBis, gbcC );
		panelCentre.add ( this.panelRepartitionPPP, gbcC );

		this.panelRepartitionBis.setVisible ( false );
		this.panelRepartitionPPP.setVisible ( false );

		gbcC.gridy = 1;
		gbcC.gridx = 0;
		this.panelAffectation.setPreferredSize ( new Dimension ( 850, 500 ) );
		panelCentre.add ( this.panelAffectation, gbcC );

		
		/*---------*/
		/*  Ouest  */
		/*---------*/

		JPanel panelOuest = new JPanel ( new GridBagLayout ( ) );

		GridBagConstraints gbcO = new GridBagConstraints();
		gbcO.insets = new Insets ( 5, 10, 15, 10 );

		this.add ( panelOuest, BorderLayout.WEST );

		gbcO.gridy = 0;
		gbcO.gridx = 0;
		panelOuest.add ( this.panelPNLocal,    gbcO  );
		panelOuest.add ( this.panelPNLocalBis, gbcO  );
		panelOuest.add ( this.panelPNLocalPPP, gbcO  );
		this.panelPNLocalBis.setVisible ( false );
		this.panelPNLocalPPP.setVisible ( false );

		gbcO.gridy = 1;
		gbcO.gridx = 0;
		panelOuest.add ( this.cbValidation, gbcO );

		/*-------*/
		/*  Sud  */
		/*-------*/

		this.add ( this.panelModuleBouton, BorderLayout.SOUTH);

		this.setVisible ( true );
	}

	/** Retourne si la checkbox est sélectionnée ou non
	 * @return cbValidation
	 */
	public boolean getCbValidation ( ) { return this.cbValidation.isSelected ( ); }

	/** Retourne le panelModuleLabel de FrameModule
	 * @return panelModuleLabel
	 */
	public PanelModuleLabel    getPanelModuleLabel    ( ) { return this.panelModuleLabel;    }

	/** Retourne le panelPNLocal de FrameModule
	 * @return panelPNLocal
	 */
	public PanelPNLocal	       getPanelPNLocal        ( ) { return this.panelPNLocal;        }

	/** Retourne le panelPNLocalBis de FrameModule
	 * @return panelPNLocalBis
	 */
	public PanelPNLocalBis     getPanelPNLocalBis     ( ) { return this.panelPNLocalBis;     }

	/** Retourne le panelPNLocalPPP de FrameModule
	 * @return panelPNLocalPPP
	 */
	public PanelPNLocalPPP     getPanelPNLocalPPP     ( ) { return this.panelPNLocalPPP;     }

	/** Retourne le panelRepartition de FrameModule
	 * @return panelRepartition
	 */
	public PanelRepartition    getPanelRepartition    ( ) { return this.panelRepartition;    }

	/** Retourne le panelRepartitionBis de FrameModule
	 * @return panelRepartitionBis
	 */
	public PanelRepartitionBis getPanelRepartitionBis ( ) { return this.panelRepartitionBis; }

	/** Retourne le panelRepartitionPPP de FrameModule
	 * @return panelRepartitionPPP
	 */
	public PanelRepartitionPPP getPanelRepartitionPPP ( ) { return this.panelRepartitionPPP; }

	public void setModule ( String code )
	{
		ModuleIUT module = this.ctrl.getModule ( code );

		this.panelModuleLabel.setModule ( module );
		this.setVisiblePanels( module.getTypeModule ( ) );

		if( this.panelPNLocal   .isVisible() ) this.panelPNLocal   .setModule ( module );
		if( this.panelPNLocalBis.isVisible() ) this.panelPNLocalBis.setModule ( module );
		if( this.panelPNLocalPPP.isVisible() ) this.panelPNLocalPPP.setModule ( module );
		
		if( this.panelRepartition   .isVisible() ) this.panelRepartition   .setModule ( module );
		if( this.panelRepartitionBis.isVisible() ) this.panelRepartitionBis.setModule ( module );
		if( this.panelRepartitionPPP.isVisible() ) this.panelRepartitionPPP.setModule ( module );
		
		this.panelAffectation.setDonnee ( module );

		this.cbValidation.setSelected( module.estValide( ) );
	}
		

	public void setVisiblePanels ( String typeModule )
	{
		if ( typeModule.equals ( "SAE" ) || typeModule.equals ( "Stage" ) )
		{
			this.panelRepartition   .setVisible ( false );
			this.panelRepartitionBis.setVisible ( true  );
			this.panelRepartitionPPP.setVisible ( false );

			this.panelPNLocal		.setVisible ( false );
			this.panelPNLocalBis	.setVisible ( true  );
			this.panelPNLocalPPP	.setVisible ( false );
		}
		else if ( typeModule.equals ( "PPP" ) )
		{
			this.panelRepartition   .setVisible ( false );
			this.panelRepartitionBis.setVisible ( false );
			this.panelRepartitionPPP.setVisible ( true  );

			this.panelPNLocal		.setVisible ( false );
			this.panelPNLocalBis	.setVisible ( false );
			this.panelPNLocalPPP	.setVisible ( true  );
		}
		else
		{
			this.panelRepartition   .setVisible ( true  );
			this.panelRepartitionBis.setVisible ( false );
			this.panelRepartitionPPP.setVisible ( false );

			this.panelPNLocal		.setVisible ( true  );
			this.panelPNLocalBis	.setVisible ( false );
			this.panelPNLocalPPP	.setVisible ( false );
		}
	}
}
