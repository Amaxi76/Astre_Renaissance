package astre.vue.previsionnel.module;

import java.awt.*;
import java.awt.*;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import astre.Controleur;

public class PanelModuleAttribution extends JPanel
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	private Controleur ctrl;

	private JTextField txtCM;
	private JTextField txtTD;
	private JTextField txtTP;
	private JLabel     lblSomme;

	private JLabel     totalCM;
	private JLabel     totalTD;
	private JLabel     totalTP;
	private JLabel     totalSomme;

	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/
	
	public PanelModuleAttribution ( Controleur ctrl )
	{
		this.ctrl = ctrl;
		/* ------------------------- */
		/* Création des composants   */
		/* ------------------------- */

		this.setLayout ( new GridLayout( 2, 4 ) );

		this.txtCM    = new JTextField ("", 2);
		this.txtTD    = new JTextField ("", 2);
		this.txtTP    = new JTextField ("", 2);
		this.lblSomme = new JLabel ( );

		this.totalCM    = new JLabel ( );
		this.totalTD    = new JLabel ( );
		this.totalTP    = new JLabel ( );
		this.totalSomme = new JLabel ( );

		this.add ( new JLabel ( "CM" ) );
		this.add ( new JLabel ( "TD" ) );
		this.add ( new JLabel ( "TP" ) );
		this.add ( new JLabel ( "Σ"  ) );

		this.add ( this.txtCM    );
		this.add ( this.txtTD    );
		this.add ( this.txtTP    );
		this.add ( this.lblSomme );

		this.add ( this.totalCM    );
		this.add ( this.totalTD    );
		this.add ( this.totalTP    );
		this.add ( this.totalSomme );

		/* ------------------------- */
		/* Activation des composants */
		/* ------------------------- */

		this.lblSomme.setBackground ( Color.LIGHT_GRAY );
		this.lblSomme.setPreferredSize ( new Dimension ( 25, 15) );
		this.lblSomme.setOpaque ( true );
	}
}