package astre.vue;

/** Page de gestion des intervenants
  * @author : Maxime Lemoine
  * @version : 1.0 - 11/12/2023
  * @date : 06/12/2023
  */

import astre.vue.previsionnel.FramePrevisionnel;
import astre.vue.intervenants.FrameIntervenants;
import astre.vue.parametrage.FrameParametrage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import astre.Controleur;


public class FrameAccueil extends JFrame implements ActionListener
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	/** Un Controleur pour pouvoir accéder au controleur
	 * 
	 */
	private Controleur  ctrl;

	private JButton btnParametre;
	private JButton btnPrevisionnel;
	private JButton btnIntervenants;
	private JButton btnEtat;
	private JButton btnAnnee;

	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/

	/** Constructuer de FrameAcceuil qui crée un panelGraphe et panelAction
	 * @param ctrl le controleur
	 * 
	 */
	public FrameAccueil ( Controleur ctrl )
	{
		this.ctrl = ctrl;
		// Image
		// Image  img = null;
		// Image icon = null;


		// try
		// {
		// 	img = new ImageIcon ( ImageIO.read ( Controleur.class.getResourceAsStream ( "/images/imgAccueil.png" ) ) ).getImage();
		// }
		// catch ( Exception e ) { }
		
		// try
		// {
		// 	icon = new ImageIcon ( ImageIO.read ( Controleur.class.getResourceAsStream ( "/images/icon.png" ) ) ).getImage();
		// }
		// catch ( Exception e ) { }

		//Image scaledImg = img.getScaledInstance ( 1000, 700, Image.SCALE_SMOOTH );

		Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit ( ).getScreenSize ( );

		int l = ( tailleEcran.width  - 1000 ) / 2;
		int h = ( tailleEcran.height -  700 ) / 2;

		this.setSize     ( 1000, 700 );
		this.setLocation (    l,   h );
		this.setTitle    ( "ASTRE"   );

		/* ------------------------- */
		/* Création des composants   */
		/* ------------------------- */

		JPanel panel         = new JPanel ( new BorderLayout (      )         );
		JPanel panelTest     = new JPanel ( new GridLayout   ( 1, 3 )         );
		JPanel panelButton   = new JPanel ( new GridLayout   ( 6, 1, 15, 15 ) );
		JPanel panelInutile  = new JPanel (                                   );
		JPanel panelInutile2 = new JPanel (                                   );

		//JLabel lblAnneee     = new JLabel ("Année en cours "); //TODO: Affilier une année ?
		
		//JLabel j = new JLabel ( new ImageIcon ( scaledImg ) );
		JLabel j = new JLabel ( );
		j.setLayout ( new BorderLayout ( ) );

		panel        .setOpaque ( false );
		panelTest    .setOpaque ( false );
		panelButton  .setOpaque ( false );
		panelInutile .setOpaque ( false );
		panelInutile2.setOpaque ( false );

		this.btnParametre    = new JButton ( "Paramètre"                    );
		this.btnPrevisionnel = new JButton ( "Prévisionnel"                 );
		this.btnIntervenants = new JButton ( "Intervenants"                 );
		this.btnEtat         = new JButton ( "Etats"                        );
		this.btnAnnee        = new JButton ( "Commencer une nouvelle année" );

		panelButton.add ( this.btnParametre    );
		panelButton.add ( this.btnPrevisionnel );
		panelButton.add ( this.btnIntervenants );
		panelButton.add ( this.btnEtat         );
		panelButton.add ( this.btnAnnee        );

		panelTest.add ( panelInutile  );
		panelTest.add ( panelButton   );
		panelTest.add ( panelInutile2 );

		panel.add ( panelTest, BorderLayout.SOUTH );
		
		j.add ( panel, BorderLayout.CENTER );

		this.getContentPane ( ).add ( j );

		/* ------------------------- */
		/* Activation des composants */
		/* ------------------------- */

		this.btnParametre   .addActionListener ( this );
		this.btnPrevisionnel.addActionListener ( this );
		this.btnIntervenants.addActionListener ( this );
		this.btnEtat        .addActionListener ( this );
		this.btnAnnee       .addActionListener ( this );

		//this.setIconImage             ( icon          );
		this.setDefaultCloseOperation ( EXIT_ON_CLOSE );
		this.setVisible               ( true          );
	}

	/* ActionListener */
	public void actionPerformed ( ActionEvent e )
	{
		if ( e.getSource ( ) == this.btnPrevisionnel )
		{
			new FramePrevisionnel ( this.ctrl );
		}
		
		if ( e.getSource ( ) == this.btnIntervenants )
		{
			new FrameIntervenants ( this.ctrl );
		}
		
		if ( e.getSource ( ) == this.btnParametre )
		{
			new FrameParametrage ( this.ctrl );
		}

		if ( e.getSource ( ) == this.btnEtat )
		{
			JOptionPane.showMessageDialog ( this, "En cours de développement...", "Erreur", JOptionPane.OK_CANCEL_OPTION );
			new FrameAccueil ( this.ctrl );
		}

		if ( e.getSource() == this.btnAnnee )
		{
			System.out.println("Hello");
			int retour1 = JOptionPane.showConfirmDialog(this, "ATTENTION \n Cela effacera les attributions des intervenants aux modules",  "Êtes-vous certains de vouloir commencer une nouvelle année ?", JOptionPane.OK_CANCEL_OPTION);
			
			if (retour1 == 0)
			{
				int retour2 = JOptionPane.showConfirmDialog(this, "ATTENTION \n Vous êtes vraiment sûr de vouloir tout effacer ?",  "Êtes-vous certains de vouloir commencer une nouvelle année ?", JOptionPane.OK_CANCEL_OPTION);
			
				if (retour2 == 0)
				{
					if ( this.ctrl.nouvelleAnnee() )
						JOptionPane.showMessageDialog ( this, "Les données de l'année précédente ont été effacées :D", "Réussite !", JOptionPane.OK_CANCEL_OPTION );
					else
						JOptionPane.showMessageDialog ( this, "Erreur, contactez l'équipe de développeurs D:", "Échec !", JOptionPane.OK_CANCEL_OPTION );
				}
				
			}
			
			return;
		}

		this.cacher ( );
	}

	public void cacher ( ) { this.dispose ( ); }

}
