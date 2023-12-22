package astre.vue;

/** Page de gestion des intervenants
  * @author : Maxime Lemoine
  * @version : 1.0 - 11/12/2023
  * @date : 06/12/2023
  */

import astre.vue.previsionnel.FramePrevisionnel;
import astre.vue.etats.FrameEtats;
import astre.vue.historique.FrameHistorique;
import astre.vue.intervenants.FrameIntervenants;
import astre.vue.nouvelleAnnee.FrameNouvelleAnnee;
import astre.vue.parametrage.FrameParametrage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import astre.Controleur;
import astre.vue.outils.AFrame;


public class FrameAccueil extends AFrame implements ActionListener
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	/** Un Controleur pour pouvoir accéder au controleur
	 * 
	 */
	private JButton btnParametre;
	private JButton btnPrevisionnel;
	private JButton btnIntervenants;
	private JButton btnEtat;
	private JButton btnAnnee;
	private JButton btnHistorique;

	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/

	/** Constructuer de FrameAcceuil qui crée un panelGraphe et panelAction
	 * @param ctrl le controleur
	 * 
	 */
	public FrameAccueil ( Controleur ctrl )
	{
		super( ctrl );

		this.setSize               ( 1000, 700 );
		this.setTitle              ( "ASTRE"   );
		this.setLocationRelativeTo ( null      );

		/* ------------------------- */
		/* Création des composants   */
		/* ------------------------- */

		JPanel panel         = new JPanel ( new BorderLayout (      )         );
		JPanel panelTest     = new JPanel ( new GridLayout   ( 1, 3 )         );
		JPanel panelButton   = new JPanel ( new GridLayout   ( 7, 1, 15, 15 ) );
		JPanel panelInutile  = new JPanel (                                   );
		JPanel panelInutile2 = new JPanel (                                   );

		//JLabel lblAnneee     = new JLabel ("Année en cours "); //TODO: Affilier une année ?
		
		JLabel j = new JLabel ( new ImageIcon ( Toolkit.getDefaultToolkit ( ).getImage ( "./data/images/astre.png" ) ) );

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
		this.btnHistorique   = new JButton ( "Historique"                   );

		panelButton.add ( this.btnParametre    );
		panelButton.add ( this.btnPrevisionnel );
		panelButton.add ( this.btnIntervenants );
		panelButton.add ( this.btnEtat         );
		panelButton.add ( this.btnAnnee        );
		panelButton.add ( this.btnHistorique   );

		panelTest.add ( panelInutile  );
		panelTest.add ( panelButton   );
		panelTest.add ( panelInutile2 );

		panel.add ( panelTest, BorderLayout.SOUTH );
		
		JPanel test = new JPanel(new GridLayout(2,1));
		test.add(j);
		test.add(panel);
		this.add(test);

		//this.getContentPane ( ).add ( j );

		/* ------------------------- */
		/* Activation des composants */
		/* ------------------------- */

		this.btnParametre   .addActionListener ( this );
		this.btnPrevisionnel.addActionListener ( this );
		this.btnIntervenants.addActionListener ( this );
		this.btnEtat        .addActionListener ( this );
		this.btnAnnee       .addActionListener ( this );
		this.btnHistorique  .addActionListener ( this );

		this.setVisible ( true );
	}

	/* ActionListener */
	public void actionPerformed ( ActionEvent e )
	{
		if ( e.getSource ( ) == this.btnPrevisionnel )
			new FramePrevisionnel ( this.ctrl );
		
		if ( e.getSource ( ) == this.btnIntervenants )
			new FrameIntervenants ( this.ctrl );
		
		if ( e.getSource ( ) == this.btnParametre    )
			new FrameParametrage ( this.ctrl );

		if ( e.getSource ( ) == this.btnEtat         )
			new FrameEtats ( this.ctrl );

		if ( e.getSource ( ) == this.btnAnnee        )
		{
			new FrameNouvelleAnnee ( this.ctrl );
			return;
		}

		if ( e.getSource ( ) == this.btnHistorique   )
			new FrameHistorique ( this.ctrl );

		this.dispose ( );
	}
}
