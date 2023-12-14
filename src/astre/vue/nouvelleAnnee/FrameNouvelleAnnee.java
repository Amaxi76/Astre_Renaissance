package astre.vue.nouvelleAnnee;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import astre.Controleur;


public class FrameNouvelleAnnee extends JFrame implements ActionListener
{
	/*--------------------*/
	/*      Attributs     */
	/*--------------------*/

	private Controleur ctrl;
	private JButton    btnZero;
	private JButton    btnNew;
	private JButton    btnAnnuler;

	/*---------------------*/
	/*     Constructeur    */
	/*---------------------*/

	/** Constructeur de FramePrevisionnel
	 * @param ctrl le controleur
	 * 
	 */

	public FrameNouvelleAnnee ( Controleur ctrl )
	{
		this.ctrl = ctrl;

		/* --------------------------------------- */
		/*           Option de la frame            */
		/* --------------------------------------- */

        this.setTitle( "Nouvelle Année" );
        this.setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE ); // Quitter la frame quand on appuie sur la croix
        this.setSize ( 400, 300 );

		/* --------------------------------------- */
		/*         Création des composants         */
		/* --------------------------------------- */

        // Création d'un panel 
		JPanel panel    = new JPanel ( new GridBagLayout ( ) ); //Permet de faire une grille sur le panel

		//Création d'un label
		JLabel lbl      = new JLabel ("Choisissez une des options suivantes pour changer d'année :");

        // Création des trois boutons
        this.btnNew     = new JButton ( "Garder les données importantes" );
        this.btnZero    = new JButton ( "Recommencer une année de zéro"  );
        this.btnAnnuler = new JButton ( "Annuler"                        );

		// Gestion de la taille des boutons
		this.btnZero   .setPreferredSize( new Dimension( 300, 30 ) );
        this.btnNew    .setPreferredSize( new Dimension( 300, 30 ) );
        this.btnAnnuler.setPreferredSize( new Dimension( 300, 30 ) );

        // Création des contraintes de la grille
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 10, 0); // Espacement vertical

		//Ajout du label au panel
		panel.add ( lbl    , gbc );
		
        // Ajout des boutons au panel

		gbc.gridy++;
		panel.add ( this.btnNew    , gbc );

		gbc.gridy++;
        panel.add ( this.btnZero   , gbc );

        gbc.gridy++;
        panel.add ( this.btnAnnuler, gbc );

		/* ------------------------- */
		/* Activation des composants */
		/* ------------------------- */

		// Ajout de l'écoute des boutons
		this.btnZero   .addActionListener ( this );
		this.btnNew    .addActionListener ( this );
		this.btnAnnuler.addActionListener ( this );

        // Ajout du panel à la frame
        this.add ( panel );

        // Centrer la frame au milieu de l'écran
        setLocationRelativeTo ( null );

		// Afficher la frame
		this.setVisible ( true );
	}

	public void actionPerformed ( ActionEvent e )
	{
		if ( e.getSource ( ) == this.btnZero ) 
		{
			int retour1 = JOptionPane.showConfirmDialog(this, "ATTENTION \n Cela effacera TOUTES les données",  "Êtes-vous certains de vouloir commencer une nouvelle année ?", JOptionPane.OK_CANCEL_OPTION);
			
			if (retour1 == 0)
			{
				int retour2 = JOptionPane.showConfirmDialog(this, "ATTENTION \n Vous êtes vraiment sûr de vouloir TOUT effacer ?",  "Êtes-vous certains de vouloir commencer une nouvelle année ?", JOptionPane.OK_CANCEL_OPTION);
			
				if (retour2 == 0)
				{
					if ( this.ctrl.nouvelleAnneeZero() )
					{
						JOptionPane.showMessageDialog ( this, "Les données de l'année précédente ont été effacées :D", "Réussite !", JOptionPane.OK_CANCEL_OPTION );
						this.dispose();
					}
						
					else
					{
						JOptionPane.showMessageDialog ( this, "Erreur, contactez l'équipe de développeurs D:", "Échec !", JOptionPane.OK_CANCEL_OPTION );
						this.dispose();
					}
				}
			}
		}

		if (e.getSource ( ) == this.btnNew )
		{
			int retour1 = JOptionPane.showConfirmDialog(this, "ATTENTION \n Cela effacera les attributions des intervenants aux modules",  "Êtes-vous certains de vouloir commencer une nouvelle année ?", JOptionPane.OK_CANCEL_OPTION);
			
			if (retour1 == 0)
			{
				int retour2 = JOptionPane.showConfirmDialog(this, "ATTENTION \n Vous êtes vraiment sûr de vouloir tout effacer ?",  "Êtes-vous certains de vouloir commencer une nouvelle année ?", JOptionPane.OK_CANCEL_OPTION);
			
				if (retour2 == 0)
				{
					if ( this.ctrl.nouvelleAnnee() )
					{
						JOptionPane.showMessageDialog ( this, "Les données de l'année précédente ont été effacées :D", "Réussite !", JOptionPane.OK_CANCEL_OPTION );
						this.dispose();
					}
					else
					{
						JOptionPane.showMessageDialog ( this, "Erreur, contactez l'équipe de développeurs D:", "Échec !", JOptionPane.OK_CANCEL_OPTION );
						this.dispose();
					}
				}
				
			}
		}

		if ( e.getSource ( ) == this.btnAnnuler ) 
		{
			this.dispose();
		}

		
	}
}
