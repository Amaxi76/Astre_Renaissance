package astre.vue.nouvelleAnnee;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;

import astre.Controleur;
import astre.vue.outils.AFrame;


public class FrameNouvelleAnnee extends AFrame implements ActionListener
{
	/*--------------------*/
	/*      Attributs     */
	/*--------------------*/

	private Controleur ctrl;
	private JButton    btnZero;
	private JButton    btnNew;
	private JButton    btnAnnuler;
	private JTextField txtNom;

	/*---------------------*/
	/*     Constructeur    */
	/*---------------------*/

	/** Constructeur de FramePrevisionnel
	 * @param ctrl le controleur
	 * 
	 */

	public FrameNouvelleAnnee ( Controleur ctrl )
	{
		super ( ctrl );
		this.ctrl = ctrl;

		/* --------------------------------------- */
		/*           Option de la frame            */
		/* --------------------------------------- */

		this.setTitle ( "Gestion des années" );
		this.setSize  ( 500, 300  );

		this.setLayout ( new BorderLayout ( 10, 10 ) );

		/* --------------------------------------- */
		/*         Création des composants         */
		/* --------------------------------------- */

		// A changer
		// JComboBox<String> cbAnnee = new JComboBox<> ( );
		// for ( String s : this.ctrl.getEnsAnnee ( ) )
		// {
		// 	cbAnnee.addItem ( s );
		// }
		
		JPanel panel = new JPanel ( new GridLayout ( 3, 1, 10, 10 ) );

		JPanel pnlTxt = new JPanel( );
		
		// Création d'un panel 
		//JPanel panel    = new JPanel ( new GroupLayout(rootPane) );

		//Ajout d'un bordure
		panel.setBorder ( BorderFactory.createEmptyBorder ( 10, 10, 10, 10 ) );

		//Création d'un label
		JLabel lblNom   = new JLabel  ( "nom de l'année :" ); 

		// Création des trois boutons
		this.btnNew     = new JButton ( "Garder les données importantes" );
		this.btnZero    = new JButton ( "Recommencer une année de zéro"  );
		this.btnAnnuler = new JButton ( "Annuler"                        );

		//création du txtfield
		this.txtNom = new JTextField ( 10 );

		//Ajout du label au panel


		pnlTxt.add( lblNom );
		pnlTxt.add( this.txtNom );
		
		// Ajout des boutons au panel
		panel.add ( pnlTxt    );
		panel.add ( this.btnNew     );
		panel.add ( this.btnZero    );

		// Ajout du panel à la frame
		this.add ( this.btnAnnuler, BorderLayout.SOUTH);
		this.add ( panel, BorderLayout.CENTER );

		/* ------------------------- */
		/* Activation des composants */
		/* ------------------------- */

		// Ajout de l'écoute des boutons
		this.btnZero   .addActionListener ( this );
		this.btnNew    .addActionListener ( this );
		this.btnAnnuler.addActionListener ( this );

		// Centrer la frame au milieu de l'écran
		setLocationRelativeTo ( null );

		// Afficher la frame
		this.setVisible ( true );
	}

	public void actionPerformed ( ActionEvent e )
	{
		if ( e.getSource ( ) == this.btnZero ) 
		{
			int retour1 = JOptionPane.showConfirmDialog ( this, "ATTENTION \n Cela effacera TOUTES les données",  "Êtes-vous certains de vouloir commencer une nouvelle année ?", JOptionPane.OK_CANCEL_OPTION );
			
			if ( retour1 == 0 )
			{
				int retour2 = JOptionPane.showConfirmDialog ( this, "ATTENTION \n Vous êtes vraiment sûr de vouloir TOUT effacer ?",  "Êtes-vous certains de vouloir commencer une nouvelle année ?", JOptionPane.OK_CANCEL_OPTION );
			
				if ( retour2 == 0 )
				{
					if ( this.ctrl.nouvelleAnneeZero ( this.txtNom.getText ( ) ) )
					{
						JOptionPane.showMessageDialog ( this, "Les données de l'année précédente ont été effacées :D", "Réussite !", JOptionPane.OK_CANCEL_OPTION );
						this.dispose ( );
					}
						
					else
					{
						JOptionPane.showMessageDialog ( this, "Erreur, contactez l'équipe de développeurs D:", "Échec !", JOptionPane.OK_CANCEL_OPTION );
						this.dispose ( );
					}
				}
			}
		}

		if (e.getSource ( ) == this.btnNew )
		{
			int retour1 = JOptionPane.showConfirmDialog(this, "ATTENTION \n Cela effacera les attributions des intervenants aux modules",  "Êtes-vous certains de vouloir commencer une nouvelle année ?", JOptionPane.OK_CANCEL_OPTION);
			
			if ( retour1 == 0 )
			{
				int retour2 = JOptionPane.showConfirmDialog ( this, "ATTENTION \n Vous êtes vraiment sûr de vouloir tout effacer ?",  "Êtes-vous certains de vouloir commencer une nouvelle année ?", JOptionPane.OK_CANCEL_OPTION );
			
				if ( retour2 == 0 )
				{
					if ( this.ctrl.nouvelleAnnee ( this.txtNom.getText ( ) ) )
					{
						JOptionPane.showMessageDialog ( this, "Les données de l'année précédente ont été effacées :D", "Réussite !", JOptionPane.OK_CANCEL_OPTION );
						this.dispose ( );
					}
					else
					{
						JOptionPane.showMessageDialog ( this, "Erreur, contactez l'équipe de développeurs D:", "Échec !", JOptionPane.OK_CANCEL_OPTION );
						this.dispose ( );
					}
				}
			}
		}

		if ( e.getSource ( ) == this.btnAnnuler ) 
		{
			AFrame.retourAccueil ( ctrl );
			this.dispose ( );
		}
	}

	
}
