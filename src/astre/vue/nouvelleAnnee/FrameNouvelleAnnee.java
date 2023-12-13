package astre.vue.nouvelleAnnee;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import astre.Controleur;
import astre.vue.outils.ConstantesVue;
import astre.vue.previsionnel.PanelEnsSemestre;

public class FrameNouvelleAnnee extends JFrame
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	private Controleur ctrl;

	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/

	/** Constructeur de FramePrevisionnel
	 * @param ctrl le controleur
	 * 
	 */

	public FrameNouvelleAnnee ( Controleur ctrl )
	{
		this.ctrl = ctrl;

		this.setSize               ( 400, 400         );
		this.setTitle              ( "Nouvelle année" );
		this.setLocationRelativeTo ( null             );

		/* ------------------------- */
		/* Création des composants   */
		/* ------------------------- */

		JPanel panelPrincipal = new JPanel ( new GridLayout   ( 3, 3 ) );

		this.add(panelPrincipal);

		JLabel  lbl1       = new JLabel  ( );
		JLabel  lbl2       = new JLabel  ( );
		JLabel  lbl3       = new JLabel  ( );
		JLabel  lbl4       = new JLabel  ( );
		JLabel  lbl5       = new JLabel  ( );
		JLabel  lbl6       = new JLabel  ( );


		JButton btnReset   = new JButton ( "Réinitialiser à zéro"               );
		JButton btnNew     = new JButton ( "Garder les intervenants et modules" );
		JButton btnAnnuler = new JButton ( "Annuler"                            );

		panelPrincipal.add(lbl1);
		panelPrincipal.add(btnReset);
		panelPrincipal.add(lbl2);
		panelPrincipal.add(lbl3);
		panelPrincipal.add(btnNew);
		panelPrincipal.add(lbl4);
		panelPrincipal.add(lbl5);
		panelPrincipal.add(btnAnnuler);
		panelPrincipal.add(lbl6);


		// int retour1 = JOptionPane.showConfirmDialog(this, "ATTENTION \n Cela effacera les attributions des intervenants aux modules",  "Êtes-vous certains de vouloir commencer une nouvelle année ?", JOptionPane.OK_CANCEL_OPTION);
			
		// 	if (retour1 == 0)
		// 	{
		// 		int retour2 = JOptionPane.showConfirmDialog(this, "ATTENTION \n Vous êtes vraiment sûr de vouloir tout effacer ?",  "Êtes-vous certains de vouloir commencer une nouvelle année ?", JOptionPane.OK_CANCEL_OPTION);
			
		// 		if (retour2 == 0)
		// 		{
		// 			if ( this.ctrl.nouvelleAnnee() )
		// 				JOptionPane.showMessageDialog ( this, "Les données de l'année précédente ont été effacées :D", "Réussite !", JOptionPane.OK_CANCEL_OPTION );
		// 			else
		// 				JOptionPane.showMessageDialog ( this, "Erreur, contactez l'équipe de développeurs D:", "Échec !", JOptionPane.OK_CANCEL_OPTION );
		// 		}
				
		// 	}


		this.setVisible ( true );
	}
}
