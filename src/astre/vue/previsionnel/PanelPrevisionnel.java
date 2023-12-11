package astre.vue.previsionnel;

/** Classe PanelPrevisionnel
  * @author : Clémentin Ly
  * @version : 1.0 - 11/12/2023
  * @date : 06/12/2023
  */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import astre.Controleur;

public class PanelPrevisionnel extends JPanel implements ActionListener
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
	
	public PanelPrevisionnel ( Controleur ctrl )
	{
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

		this.add( panelButton );

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
		}
	}
}
