package astre.vue.previsionnel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import astre.Controleur;

public class FramePrevisionnel extends JFrame implements ActionListener
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	/** Un Controleur pour pouvoir accéder au controleur
	 * 
	 */
	private Controleur  ctrl;

	private JButton btncreerRessource;
	private JButton btncreerSAE;
	private JButton btncreerStage;
	private JButton btnModifier;
	private JButton btnSupprimer;

	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/

	/** Constructuer de FramePrevisionnel qui crée un panelGraphe et panelAction
	 * @param ctrl le controleur
	 * 
	 */
	public FramePrevisionnel ( Controleur ctrl )
	{
		this.ctrl = ctrl;

		this.setSize     ( 1000, 700 );
		this.setTitle    ( "Prévisionnel"   );

		/* ------------------------- */
		/* Création des composants   */
		/* ------------------------- */

		JPanel panelButton = new JPanel(new GridLayout(1, 5 ));

		this.btncreerRessource = new JButton ( "créer Ressource"   );
		this.btncreerSAE       = new JButton ( "créer SAÉ"         );
		this.btncreerStage     = new JButton ( "créer Stage/Suivi" );
		this.btnModifier       = new JButton ( "modifier"          );
		this.btnSupprimer      = new JButton ( "supprimer"         );

		panelButton.add ( this.btncreerRessource );
		panelButton.add ( this.btncreerSAE       );
		panelButton.add ( this.btncreerStage     );
		panelButton.add ( this.btnModifier       );
		panelButton.add ( this.btnSupprimer      );

		/* ------------------------- */
		/* Activation des composants */
		/* ------------------------- */

		this.btncreerRessource.addActionListener(this);
		this.btncreerSAE      .addActionListener(this);
		this.btncreerStage    .addActionListener(this);
		this.btnModifier      .addActionListener(this);
		this.btnSupprimer     .addActionListener(this);


		this.setDefaultCloseOperation ( EXIT_ON_CLOSE );
		this.setVisible               ( true        );
	}

	/* ActionListener */
	public void actionPerformed ( ActionEvent e )
	{
		if ( e.getSource ( ) == this.btncreerRessource )
		{
			System.out.println("créerRessource");
		}
	}
}
