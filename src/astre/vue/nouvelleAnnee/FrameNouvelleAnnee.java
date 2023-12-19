package astre.vue.nouvelleAnnee;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
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
		this.setSize ( 500, 300 );

		/* --------------------------------------- */
		/*         Création des composants         */
		/* --------------------------------------- */

        // Création d'un panel 
		JPanel panel    = new JPanel ( new GridLayout ( 4, 1, 10, 10 ) );

		//Ajout d'un bordure
		panel.setBorder ( BorderFactory.createEmptyBorder ( 10, 10, 10, 10 ) );

		//Création d'un label
		JLabel lbl      = new JLabel ("Choisissez une des options suivantes pour changer d'année :");

        // Création des trois boutons
        this.btnNew     = new JButton ( "Garder les données importantes" );
        this.btnZero    = new JButton ( "Recommencer une année de zéro"  );
        this.btnAnnuler = new JButton ( "Annuler"                        );

		//Ajout du label au panel
		panel.add ( lbl            );
		
        // Ajout des boutons au panel
		panel.add ( this.btnNew    );
        panel.add ( this.btnZero   );
        panel.add ( this.btnAnnuler);

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
