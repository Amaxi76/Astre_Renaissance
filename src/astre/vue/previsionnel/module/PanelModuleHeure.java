package astre.vue.previsionnel.module;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import astre.Controleur;
import astre.modele.elements.Heure;

public class PanelModuleHeure extends JPanel implements ActionListener
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	private Controleur  ctrl;
	private FrameModule frm;

	private JComboBox<String>	cbHeuresAjouter;
	private JComboBox<String>	cbHeuresSupprimer;
	private JButton             btnAjouter;
	private JButton             btnSupprimer;

	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/

	public PanelModuleHeure(Controleur ctrl, FrameModule frm)
	{
		this.ctrl = ctrl;
		this.frm  = frm;

		/* ------------------------- */
		/* Création des composants   */
		/* ------------------------- */

		this.setLayout ( new GridBagLayout() );
		this.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets ( 5, 5, 5, 5 );

		this.cbHeuresAjouter   = new JComboBox<>();
		for ( Heure h : this.ctrl.getTable ( Heure.class ) )
		{
			cbHeuresAjouter.addItem ( h.getNom ( ) );
		}

		this.cbHeuresSupprimer = new JComboBox<>();
		for ( Heure h : this.ctrl.getTable ( Heure.class ) )
		{
			cbHeuresSupprimer.addItem ( h.getNom ( ) );
		}

		this.btnAjouter   = new JButton ( "Ajouter"   );
		this.btnSupprimer = new JButton ( "Supprimer" );


		gbc.gridy = 0;
		gbc.gridx = 0;
		this.add ( new JLabel ( "Ajouter Heure " ), gbc );

		gbc.gridx = 1;
		this.add ( new JLabel ( "Supprimer Heure " ), gbc );


		gbc.gridy = 1;
		gbc.gridx = 0;
		this.add ( this.cbHeuresAjouter, gbc );
		
		gbc.gridx = 1;
		this.add ( this.cbHeuresSupprimer, gbc );


		gbc.gridy = 2;
		gbc.gridx = 0;
		this.add ( this.btnAjouter, gbc );

		gbc.gridx = 1;
		this.add ( this.btnSupprimer, gbc );

		/* ------------------------- */
		/* Activation des composants */
		/* ------------------------- */

		this.btnAjouter  .addActionListener ( this );
		this.btnSupprimer.addActionListener ( this );
	}

	public void actionPerformed ( ActionEvent e )
	{
		if ( e.getSource ( ) == this.btnAjouter )
		{
			String nomHeure = ( String ) this.cbHeuresAjouter.getSelectedItem();

			if( this.frm.getPanelPNLocal()   .isVisible() ) this.frm.getPanelPNLocal()   .ajouterHeure ( nomHeure );
			if( this.frm.getPanelPNLocalBis().isVisible() ) this.frm.getPanelPNLocalBis().ajouterHeure ( nomHeure );
			if( this.frm.getPanelPNLocalPPP().isVisible() ) this.frm.getPanelPNLocalPPP().ajouterHeure ( nomHeure );

			if( this.frm.getPanelRepartition()   .isVisible() ) this.frm.getPanelRepartition()   .ajouterHeure ( nomHeure );
			if( this.frm.getPanelRepartitionBis().isVisible() ) this.frm.getPanelRepartitionBis().ajouterHeure ( nomHeure );
			if( this.frm.getPanelRepartitionPPP().isVisible() ) this.frm.getPanelRepartitionPPP().ajouterHeure ( nomHeure );
		}

		if ( e.getSource() == this.btnSupprimer )
		{
			String nomHeure = ( String ) this.cbHeuresSupprimer.getSelectedItem();

			if( this.frm.getPanelPNLocal()   .isVisible() ) this.frm.getPanelPNLocal()   .supprimerHeure(nomHeure);
			if( this.frm.getPanelPNLocalBis().isVisible() ) this.frm.getPanelPNLocalBis().supprimerHeure ( nomHeure );
			if( this.frm.getPanelPNLocalPPP().isVisible() ) this.frm.getPanelPNLocalPPP().supprimerHeure ( nomHeure );

			if( this.frm.getPanelRepartition()   .isVisible() ) this.frm.getPanelRepartition()   .supprimerHeure ( nomHeure );
			if( this.frm.getPanelRepartitionBis().isVisible() ) this.frm.getPanelRepartitionBis().supprimerHeure ( nomHeure );
			if( this.frm.getPanelRepartitionPPP().isVisible() ) this.frm.getPanelRepartitionPPP().supprimerHeure ( nomHeure );
		}
	}
}