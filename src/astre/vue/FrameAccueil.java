package astre.vue;

import astre.vue.previsionnel.FramePrevisionnel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

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
		
		//JLabel j = new JLabel ( new ImageIcon ( scaledImg ) );
		JLabel j = new JLabel ( );
		j.setLayout ( new BorderLayout ( ) );

		panel        .setOpaque ( false );
		panelTest    .setOpaque ( false );
		panelButton  .setOpaque ( false );
		panelInutile .setOpaque ( false );
		panelInutile2.setOpaque ( false );

		this.btnParametre    = new JButton ( "Paramètre"    );
		this.btnPrevisionnel = new JButton ( "Prévisionnel" );
		this.btnIntervenants = new JButton ( "Intervenants" );
		this.btnEtat         = new JButton ( "Etats"        );

		panelButton.add ( this.btnParametre    );
		panelButton.add ( this.btnPrevisionnel );
		panelButton.add ( this.btnIntervenants );
		panelButton.add ( this.btnEtat         );

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

		//this.setIconImage             ( icon          );
		this.setDefaultCloseOperation ( EXIT_ON_CLOSE );
		this.setVisible               ( true          );
	}

	/* ActionListener */
	public void actionPerformed ( ActionEvent e )
	{
		// if ( e.getSource ( ) == this.btn1J       )
		// {
		// 	this.ctrl.initialiserJeu ( );
		// }
		if ( e.getSource ( ) == this.btnPrevisionnel )
		{
			new FramePrevisionnel ( this.ctrl );
		}
		
		if ( e.getSource ( ) == this.btnIntervenants )
		{
			JOptionPane.showMessageDialog ( this, "En cours de développement...", "Erreur", JOptionPane.INFORMATION_MESSAGE );
			new FrameAccueil ( this.ctrl );
		}

		this.cacher ( );
	}

	public void cacher ( ) { this.dispose ( ); }

}
