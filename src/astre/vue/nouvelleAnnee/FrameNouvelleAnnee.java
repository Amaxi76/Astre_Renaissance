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

	private JComboBox<String> cbAnnee;
	private JButton           btnValider;

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
		this.setSize  ( 500, 500  );

		this.setLayout ( new BorderLayout ( 10, 10 ) );

		/* --------------------------------------- */
		/*         Création des composants         */
		/* --------------------------------------- */

		this.cbAnnee = new JComboBox<> ( );
		for ( String s : this.ctrl.getEnsAnnee ( ) )
		{
			cbAnnee.addItem ( s );
		}
		
		JPanel panel = new JPanel ( new GridLayout ( 4, 1, 10, 10 ) );
		JPanel panel2 = new JPanel ( new GridLayout( 4, 1, 10, 5 ) );
		JPanel pnlTxt = new JPanel( );
		JPanel pnlTxt2 = new JPanel ( );

		//Ajout d'un bordure
		panel.setBorder ( BorderFactory.createEmptyBorder ( 0, 10, 10, 10 ) );
		panel2.setBorder ( BorderFactory.createEmptyBorder ( 0, 10, 0, 10 ) );

		//Création d'un label
		JLabel lblNom   = new JLabel  ( "nom de l'année :" ); 

		// Création des trois boutons
		this.btnNew     = new JButton ( "Garder les données importantes" );
		this.btnZero    = new JButton ( "Commencer une année de zéro"    );
		this.btnAnnuler = new JButton ( "Retour"                         );

		this.btnValider = new JButton ( "Valider" );

		//création du txtfield
		this.txtNom = new JTextField ( 10 );

		//ajouts sur les panels
		pnlTxt.add( lblNom );
		pnlTxt.add( this.txtNom );
		
		panel.add( new JLabel("Creer une nouvelle année"));
		panel.add ( pnlTxt    );
		panel.add ( this.btnNew     );
		panel.add ( this.btnZero    );

		pnlTxt2.add( new JLabel("Choississez une année voulue :"));
		pnlTxt2.add(this.cbAnnee);

		panel2.add(new JLabel("Changer l'année actuelle"));
		panel2.add(pnlTxt2);
		panel2.add(this.btnValider);

		JPanel pnlFin = new JPanel(new GridLayout(2, 1, 10, 0));
		pnlFin.add(panel2);
		pnlFin.add(panel);

		// Ajout du panel à la frame
		this.add ( this.btnAnnuler, BorderLayout.SOUTH);
		this.add ( pnlFin, BorderLayout.CENTER );

		/* ------------------------- */
		/* Activation des composants */
		/* ------------------------- */

		// Ajout de l'écoute des boutons
		this.btnZero      .addActionListener ( this );
		this.btnValider   .addActionListener ( this );
		this.btnNew       .addActionListener ( this );
		this.btnAnnuler   .addActionListener ( this );

		// Centrer la frame au milieu de l'écran
		setLocationRelativeTo ( null );

		// Afficher la frame
		this.setVisible ( true );
	}

	public void actionPerformed ( ActionEvent e )
	{
		if ( e.getSource ( ) == this.btnZero ) 
		{
			int retour1 = JOptionPane.showConfirmDialog ( this, "ATTENTION \n Cela lancera une nouvelle année sans aucune données !",  "Êtes-vous certains de vouloir commencer une nouvelle année ?", JOptionPane.OK_CANCEL_OPTION );
			
			if ( retour1 == 0 )
			{
				if ( this.ctrl.nouvelleAnneeZero ( this.txtNom.getText ( ) ) )
				{
					JOptionPane.showMessageDialog ( this, "Vous avez commencé une nouvelle année vide de donnée :D", "Réussite !", JOptionPane.OK_CANCEL_OPTION );
					AFrame.retourAccueil ( ctrl );
					this.dispose ( );
				}
						
				else
				{
					JOptionPane.showMessageDialog ( this, "Erreur, contactez l'équipe de développeurs D:", "Échec !", JOptionPane.OK_CANCEL_OPTION );
					AFrame.retourAccueil ( ctrl );
					this.dispose ( );
				}

			}
		}

		if (e.getSource ( ) == this.btnNew )
		{
			int retour1 = JOptionPane.showConfirmDialog(this, "ATTENTION \n Cela créera une copie de l'année actuelle !",  "Êtes-vous certains de vouloir commencer une nouvelle année ?", JOptionPane.OK_CANCEL_OPTION);
			
			if ( retour1 == 0 )
			{
				if ( this.ctrl.nouvelleAnnee ( this.txtNom.getText ( ) ) )
				{
					JOptionPane.showMessageDialog ( this, "Les données de l'année précédente ont été copiées :D", "Réussite !", JOptionPane.OK_CANCEL_OPTION );
					AFrame.retourAccueil ( ctrl );
					this.dispose ( );
				}
				else
				{
					JOptionPane.showMessageDialog ( this, "Erreur, contactez l'équipe de développeurs D:", "Échec !", JOptionPane.OK_CANCEL_OPTION );
					AFrame.retourAccueil ( ctrl );
					this.dispose ( );
				}
			}

		}

		if ( e.getSource ( ) == this.btnValider ) 
		{
			this.ctrl.changerAnnee ( this.cbAnnee.getSelectedItem ( ).toString ( ) );
			AFrame.retourAccueil ( ctrl );
			this.dispose ( );
		}

		if ( e.getSource ( ) == this.btnAnnuler ) 
		{
			AFrame.retourAccueil ( ctrl );
			this.dispose ( );
		}
	}

	
}
