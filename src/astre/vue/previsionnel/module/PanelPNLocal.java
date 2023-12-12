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
	private JLabel     lblSomme;

	private JLabel lblTotalCM;
	private JLabel lblTotalTD;
	private JLabel lblTotalTP;
	private JLabel lblTotalSomme;

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

		this.txtCM      = new JTextField ( "", 2 );
		this.txtTD      = new JTextField ( "", 2 );
		this.txtTP      = new JTextField ( "", 2 );
		this.lblSomme   = new JLabel();

		this.lblTotalCM    = new JLabel();
		this.lblTotalTD    = new JLabel();
		this.lblTotalTP    = new JLabel();
		this.lblTotalSomme = new JLabel();


		gbc.gridy = 0;
		gbc.gridx = 1;
		this.add ( new JLabel ( "CM" ), gbc );

		gbc.gridx = 2;
		this.add ( new JLabel ( "TD" ), gbc );

		gbc.gridx = 3;
		this.add ( new JLabel ( "TP" ), gbc );

		gbc.gridx = 4;
		this.add ( new JLabel ( "Σ" ), gbc  );


		gbc.gridy = 1;
		gbc.gridx = 1;
		this.add ( this.txtCM, gbc     );

		gbc.gridx = 2;
		this.add ( this.txtTD, gbc     );

		gbc.gridx = 3;
		this.add ( this.txtTP, gbc    );

		gbc.gridx = 4;
		this.add ( this.lblSomme, gbc );


		gbc.gridy = 2;
		gbc.gridx = 0;
		this.add ( new JLabel ( "Total (eqtd) promo" ), gbc );

		gbc.gridx = 1;
		this.add ( this.lblTotalCM, gbc    );

		gbc.gridx = 2;
		this.add ( this.lblTotalTD, gbc    );

		gbc.gridx = 3;
		this.add ( this.lblTotalTP, gbc    );

		gbc.gridx = 4;
		this.add ( this.lblTotalSomme, gbc );

		/* ------------------------- */
		/* Activation des composants */
		/* ------------------------- */

		this.txtCM.addKeyListener ( new AjoutKeyListenerSomme() );
		this.txtTD.addKeyListener ( new AjoutKeyListenerSomme() );
		this.txtTP.addKeyListener ( new AjoutKeyListenerSomme() );


		this.lblSomme.setBackground ( Color.LIGHT_GRAY );
		this.lblSomme.setPreferredSize ( new Dimension ( 25, 15 ) );
		this.lblSomme.setOpaque( true );

		this.lblTotalCM.setBackground ( Color.LIGHT_GRAY );
		this.lblTotalCM.setPreferredSize ( new Dimension ( 25, 15 ) );
		this.lblTotalCM.setOpaque( true );

		this.lblTotalTD.setBackground( Color.LIGHT_GRAY );
		this.lblTotalTD.setPreferredSize( new Dimension ( 25, 15 ) );
		this.lblTotalTD.setOpaque( true );

		this.lblTotalTP.setBackground( Color.LIGHT_GRAY );
		this.lblTotalTP.setPreferredSize( new Dimension ( 25, 15 ) );
		this.lblTotalTP.setOpaque( true );

		this.lblTotalSomme.setBackground( Color.LIGHT_GRAY );
		this.lblTotalSomme.setPreferredSize( new Dimension ( 25, 15 ) );
		this.lblTotalSomme.setOpaque( true );
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
			int CM    = 0;
			int TD    = 0;
			int TP    = 0;
			
			if (!txtCM.getText().equals(null) || !txtTD.equals(null) || !txtTP.equals(null) )
			{
				CM = Integer.parseInt ( txtCM.getText() );
				TD = Integer.parseInt ( txtTD.getText() );
				TP = Integer.parseInt ( txtTP.getText() );
			}

			int somme = CM + TD + TP;
			lblSomme.setText ( String.valueOf ( somme ) );
		}
		catch ( NumberFormatException ex )
		{
			lblSomme.setText ( "Erreur" );
		}
	}
}
