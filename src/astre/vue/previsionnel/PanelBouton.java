package astre.vue.previsionnel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import astre.Controleur;

public class PanelBouton extends JPanel implements ActionListener
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	private Controleur ctrl;

	private JButton btncreerRessource;
	private JButton btncreerSAE;
	private JButton btncreerStage;
	private JButton btnModifier;
	private JButton btnSupprimer;


	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/
	
	public PanelBouton ( Controleur ctrl )
	{
		/* ------------------------- */
		/* Création des composants   */
		/* ------------------------- */

		this.setLayout(new GridLayout(1, 5 ));

		this.btncreerRessource = new JButton ( "créer Ressource"   );
		this.btncreerSAE       = new JButton ( "créer SAÉ"         );
		this.btncreerStage     = new JButton ( "créer Stage/Suivi" );
		this.btnModifier       = new JButton ( "modifier"          );
		this.btnSupprimer      = new JButton ( "supprimer"         );

		this.add ( this.btncreerRessource );
		this.add ( this.btncreerSAE       );
		this.add ( this.btncreerStage     );
		this.add ( this.btnModifier       );
		this.add ( this.btnSupprimer      );

		this.add( this );

		/* ------------------------- */
		/* Activation des composants */
		/* ------------------------- */

		this.btncreerRessource.addActionListener(this);
		this.btncreerSAE      .addActionListener(this);
		this.btncreerStage    .addActionListener(this);
		this.btnModifier      .addActionListener(this);
		this.btnSupprimer     .addActionListener(this);

	}

	/* ActionListener */
	public void actionPerformed ( ActionEvent e )
	{
		if ( e.getSource ( ) == this.btncreerRessource )
		{
			System.out.println("créerRessource");
			((JFrame) (this.getParent())).dispose();
		}
	}
}
