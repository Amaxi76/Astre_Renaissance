package astre.vue.previsionnel.module;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import astre.Controleur;

public class PanelPNLocal extends JPanel
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	private Controleur ctrl;

	private JTextField txtCM;
	private JTextField txtTD;
	private JTextField txtTP;
	private JLabel lblSomme;

	private JLabel totalCM;
	private JLabel totalTD;
	private JLabel totalTP;
	private JLabel totalSomme;

	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/

	public PanelPNLocal(Controleur ctrl)
	{
		this.ctrl = ctrl;

		/* ------------------------- */
		/* Création des composants   */
		/* ------------------------- */

		this.setLayout ( new GridBagLayout() );

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets ( 5, 5, 5, 5 );

		gbc.gridx = 1;
		gbc.gridy = 0;
		this.add ( new JLabel ( "CM" ), gbc );

		gbc.gridx = 2;
		this.add ( new JLabel ( "TD" ), gbc );

		gbc.gridx = 3;
		this.add ( new JLabel ( "TP" ), gbc );

		gbc.gridx = 4;
		this.add ( new JLabel ( "Σ" ), gbc );


		gbc.gridy  = 1;
		gbc.gridx  = 1;
		this.txtCM = new JTextField ( "", 2 );
		this.add(this.txtCM, gbc);

		gbc.gridx  = 2;
		this.txtTD = new JTextField ( "", 2 );
		this.add(this.txtTD, gbc);

		gbc.gridx  = 3;
		this.txtTP = new JTextField ( "", 2 );
		this.add ( this.txtTP, gbc );

		gbc.gridx     = 4;
		this.lblSomme = new JLabel();
		this.add ( this.lblSomme, gbc );


		gbc.gridy    = 2;
		gbc.gridx    = 0;
		this.add ( new JLabel ( "Total (eqtd) promo" ), gbc );

		gbc.gridx    = 1;
		this.totalCM = new JLabel();
		this.add ( this.totalCM, gbc );

		gbc.gridx    = 2;
		this.totalTD = new JLabel();
		this.add ( this.totalTD, gbc );

		gbc.gridx    = 3;
		this.totalTP = new JLabel();
		this.add ( this.totalTP, gbc );

		gbc.gridx       = 4;
		this.totalSomme = new JLabel();
		this.add ( this.totalSomme, gbc );

		/* ------------------------- */
		/* Activation des composants */
		/* ------------------------- */

		this.txtCM.addKeyListener ( new AjoutKeyListenerSomme() );
		this.txtTD.addKeyListener ( new AjoutKeyListenerSomme() );
		this.txtTP.addKeyListener ( new AjoutKeyListenerSomme() );


		this.lblSomme.setBackground ( Color.LIGHT_GRAY );
		this.lblSomme.setPreferredSize ( new Dimension ( 25, 15 ) );
		this.lblSomme.setOpaque( true );

		this.totalCM.setBackground ( Color.LIGHT_GRAY );
		this.totalCM.setPreferredSize ( new Dimension ( 25, 15 ) );
		this.totalCM.setOpaque( true );

		this.totalTD.setBackground( Color.LIGHT_GRAY );
		this.totalTD.setPreferredSize( new Dimension ( 25, 15 ) );
		this.totalTD.setOpaque( true );

		this.totalTP.setBackground( Color.LIGHT_GRAY );
		this.totalTP.setPreferredSize( new Dimension ( 25, 15 ) );
		this.totalTP.setOpaque( true );

		this.totalSomme.setBackground( Color.LIGHT_GRAY );
		this.totalSomme.setPreferredSize( new Dimension ( 25, 15 ) );
		this.totalSomme.setOpaque( true );
	}

	private class AjoutKeyListenerSomme implements KeyListener
	{
		public void keyTyped   ( KeyEvent e ) { majSomme(); }
		public void keyPressed ( KeyEvent e ) {}
		public void keyReleased( KeyEvent e ) {}
	}

	private void majSomme()
	{
		try
		{
			int CM = Integer.parseInt ( txtCM.getText() );
			int TD = Integer.parseInt ( txtTD.getText() );
			int TP = Integer.parseInt ( txtTP.getText() );

			int somme = CM + TD + TP;
			lblSomme.setText ( String.valueOf ( somme ) );
		}
		catch ( NumberFormatException ex )
		{
			lblSomme.setText ( "Erreur" );
		}
	}
}
