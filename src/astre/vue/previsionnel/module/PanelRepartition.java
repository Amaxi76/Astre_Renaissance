package astre.vue.previsionnel.module;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import astre.Controleur;

public class PanelRepartition extends JPanel
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	private Controleur ctrl;

	private JTextField txtNbSemCM;
	private JTextField txtNbHCM;
	private JTextField txtNbSemTD;
	private JTextField txtNbHTD;
	private JTextField txtNbSemTP;
	private JTextField txtNbHTP;

	private JLabel     lblTotalCM;
	private JLabel     lblTotalTD;
	private JLabel     lblTotalTP;
	private JTextField txtHeureP;
	private JLabel     lblTotalSomme;

	private JLabel     lblTotalCMProm;
	private JLabel     lblTotalTDProm;
	private JLabel     lblTotalTPProm;
	private JLabel     lblTotalHeurePProm;
	private JLabel     lblTotalSommeProm;

	private JLabel     lblTotalCMAff;
	private JLabel     lblTotalTDAff;
	private JLabel     lblTotalTPAff;
	private JLabel     lblTotalHeurePAff;
	private JLabel     lblTotalSommeAff;

	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/
	
	public PanelRepartition ( Controleur ctrl )
	{
		this.ctrl = ctrl;
		/* ------------------------- */
		/* Création des composants   */
		/* ------------------------- */

		this.setLayout ( new GridBagLayout() );
		this.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets ( 5, 5, 5, 5 );

		this.txtNbSemCM = new JTextField ("", 2);
		this.txtNbHCM   = new JTextField ("", 2);
		this.txtNbSemTD = new JTextField ("", 2);
		this.txtNbHTD   = new JTextField ("", 2);
		this.txtNbSemTP = new JTextField ("", 2);
		this.txtNbHTP   = new JTextField ("", 2);

		this.lblTotalCM    = new JLabel();
		this.lblTotalTD    = new JLabel();
		this.lblTotalTP    = new JLabel();
		this.txtHeureP     = new JTextField("", 2);
		this.lblTotalSomme = new JLabel();

		this.lblTotalCMProm     = new JLabel();
		this.lblTotalTDProm     = new JLabel();
		this.lblTotalTPProm     = new JLabel();
		this.lblTotalHeurePProm = new JLabel();
		this.lblTotalSommeProm  = new JLabel();

		this.lblTotalCMAff     = new JLabel();
		this.lblTotalTDAff     = new JLabel();
		this.lblTotalTPAff     = new JLabel();
		this.lblTotalHeurePAff = new JLabel();
		this.lblTotalSommeAff  = new JLabel();


		gbc.gridy = 0;
		gbc.gridx = 0;
		this.add ( new JLabel ( "CM" ), gbc );

		gbc.gridx = 2;
		this.add ( new JLabel ( "TD" ), gbc );

		gbc.gridx = 4;
		this.add ( new JLabel ( "TP" ), gbc );


		gbc.gridy = 1;
		gbc.gridx = 0;
		this.add ( new JLabel ( "nb Sem"   ), gbc );

		gbc.gridx = 1;
		this.add ( new JLabel ( "nb h/sem" ), gbc );

		gbc.gridx = 2;
		this.add ( new JLabel ( "nb Sem"   ), gbc );

		gbc.gridx = 3;
		this.add ( new JLabel ( "nb h/sem" ), gbc );

		gbc.gridx = 4;
		this.add ( new JLabel ( "nb Sem"   ), gbc );

		gbc.gridx = 5;
		this.add ( new JLabel ( "nb h/sem" ), gbc );

		gbc.gridx = 6;
		this.add ( new JLabel ( "CM" ), gbc );

		gbc.gridx = 7;
		this.add ( new JLabel ( "TD" ), gbc );

		gbc.gridx = 8;
		this.add ( new JLabel ( "TP" ), gbc );

		gbc.gridx = 9;
		this.add ( new JLabel ( "heures ponctuelles" ), gbc );

		gbc.gridx = 10;
		this.add ( new JLabel ( "Σ" ), gbc );


		gbc.gridy = 2;
		gbc.gridx = 0;
		this.add ( this.txtNbSemCM, gbc    );

		gbc.gridx = 1;
		this.add ( this.txtNbHCM, gbc      );

		gbc.gridx = 2;
		this.add ( this.txtNbSemTD, gbc    );

		gbc.gridx = 3;
		this.add ( this.txtNbHTD, gbc      );

		gbc.gridx = 4;
		this.add ( this.txtNbSemTP, gbc    );

		gbc.gridx = 5;
		this.add ( this.txtNbHTP, gbc      );

		gbc.gridx = 6;
		this.add ( this.lblTotalCM, gbc    );

		gbc.gridx = 7;
		this.add ( this.lblTotalTD, gbc    );

		gbc.gridx = 8;
		this.add ( this.lblTotalTP, gbc    );

		gbc.gridx = 9;
		this.add ( this.txtHeureP, gbc     );

		gbc.gridx = 10;
		this.add ( this.lblTotalSomme, gbc );


		gbc.gridy = 3;
		gbc.gridx = 5;
		this.add ( new JLabel ( "Total promo (eqtd)" ), gbc );

		gbc.gridx = 6;
		this.add ( this.lblTotalCMProm, gbc     );

		gbc.gridx = 7;
		this.add ( this.lblTotalTDProm, gbc     );

		gbc.gridx = 8;
		this.add ( this.lblTotalTPProm, gbc     );

		gbc.gridx = 9;
		this.add ( this.lblTotalHeurePProm, gbc );

		gbc.gridx = 10;
		this.add ( this.lblTotalSommeProm, gbc  );


		gbc.gridy = 4;
		gbc.gridx = 5;
		this.add ( new JLabel ( "Total affecté (eqtd)" ), gbc );

		gbc.gridx = 6;
		this.add ( this.lblTotalCMAff, gbc     );

		gbc.gridx = 7;
		this.add ( this.lblTotalTDAff, gbc     );

		gbc.gridx = 8;
		this.add ( this.lblTotalTPAff, gbc     );

		gbc.gridx = 9;
		this.add ( this.lblTotalHeurePAff, gbc );

		gbc.gridx = 10;
		this.add ( this.lblTotalSommeAff, gbc  );


		/* ------------------------- */
		/* Activation des composants */
		/* ------------------------- */

		this.txtNbSemCM.addKeyListener ( new KeyListener()
		{
			public void keyTyped    ( KeyEvent e ) { majTotalCM(); }
			public void keyPressed  ( KeyEvent e ) {}
			public void keyReleased ( KeyEvent e ) {}
		});

		this.txtNbHCM.addKeyListener ( new KeyListener()
		{
			public void keyTyped    ( KeyEvent e ) { majTotalCM(); }
			public void keyPressed  ( KeyEvent e ) {}
			public void keyReleased ( KeyEvent e ) {}
		});

		this.txtNbSemTD.addKeyListener ( new KeyListener()
		{
			public void keyTyped    ( KeyEvent e ) { majTotalTD(); }
			public void keyPressed  ( KeyEvent e ) {}
			public void keyReleased ( KeyEvent e ) {}
		});

		this.txtNbHTD.addKeyListener ( new KeyListener()
		{
			public void keyTyped    ( KeyEvent e ) { majTotalTD(); }
			public void keyPressed  ( KeyEvent e ) {}
			public void keyReleased ( KeyEvent e ) {}
		});

		this.txtNbSemTP.addKeyListener ( new KeyListener()
		{
			public void keyTyped    ( KeyEvent e ) { majTotalTP(); }
			public void keyPressed  ( KeyEvent e ) {}
			public void keyReleased ( KeyEvent e ) {}
		});

		this.txtNbHTP.addKeyListener ( new KeyListener()
		{
			public void keyTyped    ( KeyEvent e ) { majTotalTP(); }
			public void keyPressed  ( KeyEvent e ) {}
			public void keyReleased ( KeyEvent e ) {}
		});

		this.txtHeureP.addKeyListener ( new KeyListener()
		{
			public void keyTyped    ( KeyEvent e ) { majTotalHeure(); }
			public void keyPressed  ( KeyEvent e ) {}
			public void keyReleased ( KeyEvent e ) {}
		});

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



		this.lblTotalCMProm.setBackground ( Color.LIGHT_GRAY );
		this.lblTotalCMProm.setPreferredSize ( new Dimension ( 25, 15 ) );
		this.lblTotalCMProm.setOpaque( true );

		this.lblTotalTDProm.setBackground( Color.LIGHT_GRAY );
		this.lblTotalTDProm.setPreferredSize( new Dimension ( 25, 15 ) );
		this.lblTotalTDProm.setOpaque( true );

		this.lblTotalTPProm.setBackground( Color.LIGHT_GRAY );
		this.lblTotalTPProm.setPreferredSize( new Dimension ( 25, 15 ) );
		this.lblTotalTPProm.setOpaque( true );

		this.lblTotalHeurePProm.setBackground( Color.LIGHT_GRAY );
		this.lblTotalHeurePProm.setPreferredSize( new Dimension ( 25, 15 ) );
		this.lblTotalHeurePProm.setOpaque( true );

		this.lblTotalSommeProm.setBackground( Color.LIGHT_GRAY );
		this.lblTotalSommeProm.setPreferredSize( new Dimension ( 25, 15 ) );
		this.lblTotalSommeProm.setOpaque( true );


		this.lblTotalCMAff.setBackground ( Color.LIGHT_GRAY );
		this.lblTotalCMAff.setPreferredSize ( new Dimension ( 25, 15 ) );
		this.lblTotalCMAff.setOpaque( true );

		this.lblTotalTDAff.setBackground( Color.LIGHT_GRAY );
		this.lblTotalTDAff.setPreferredSize( new Dimension ( 25, 15 ) );
		this.lblTotalTDAff.setOpaque( true );

		this.lblTotalTPAff.setBackground( Color.LIGHT_GRAY );
		this.lblTotalTPAff.setPreferredSize( new Dimension ( 25, 15 ) );
		this.lblTotalTPAff.setOpaque( true );

		this.lblTotalHeurePAff.setBackground( Color.LIGHT_GRAY );
		this.lblTotalHeurePAff.setPreferredSize( new Dimension ( 25, 15 ) );
		this.lblTotalHeurePAff.setOpaque( true );

		this.lblTotalSommeAff.setBackground( Color.LIGHT_GRAY );
		this.lblTotalSommeAff.setPreferredSize( new Dimension ( 25, 15 ) );
		this.lblTotalSommeAff.setOpaque( true );
	}

	private void majTotalCM()
	{
		int nbSemCM = 0;
		int nbHCM   = 0;

		if (!txtNbSemCM.getText().isEmpty() )
		{
			nbSemCM = Integer.parseInt ( txtNbSemCM.getText() );
		}

		if (!txtNbHCM.getText().isEmpty() )
		{
			nbHCM = Integer.parseInt ( txtNbHCM.getText() );
		}

		int totalCM = nbSemCM * nbHCM;
		lblTotalCM.setText ( String.valueOf ( totalCM ) );
	}

	private void majTotalTD()
	{
		int nbSemTD = 0;
		int nbHTD   = 0;

		if (!txtNbSemTD.getText().isEmpty() )
		{
			nbSemTD = Integer.parseInt ( txtNbSemTD.getText() );
		}

		if (!txtNbHTD.getText().isEmpty() )
		{
			nbHTD = Integer.parseInt ( txtNbHTD.getText() );
		}

		int totalTD = nbSemTD * nbHTD;
		lblTotalTD.setText ( String.valueOf ( totalTD ) );
	}

	private void majTotalTP()
	{
		int nbSemTP = 0;
		int nbHTP   = 0;

		if (!txtNbSemTP.getText().isEmpty() )
		{
			nbSemTP = Integer.parseInt ( txtNbSemTP.getText() );
		}

		if (!txtNbHTP.getText().isEmpty() )
		{
			nbHTP = Integer.parseInt ( txtNbHTP.getText() );
		}

		int totalTP = nbSemTP * nbHTP;
		lblTotalTP.setText ( String.valueOf ( totalTP ) );
	}

	private void majTotalHeure()
	{
		int totalCM = 0;
		int totalTD = 0;
		int totalTP = 0;
		int heureP  = 0;

		if (!lblTotalCM.getText().isEmpty() )
		{
			totalCM = Integer.parseInt ( lblTotalCM.getText() );
		}

		if (!lblTotalTD.getText().isEmpty() )
		{
			totalTD = Integer.parseInt ( lblTotalTD.getText() );
		}

		if (!lblTotalTP.getText().isEmpty() )
		{
			totalTP = Integer.parseInt ( lblTotalTP.getText() );
		}

		if (!txtHeureP.getText().isEmpty() )
		{
			heureP = Integer.parseInt ( txtHeureP.getText() );
		}

		int somme = totalCM + totalTD + totalTP + heureP;
		lblTotalSomme.setText ( String.valueOf ( somme ) );
	}
}