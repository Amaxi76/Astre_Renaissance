package astre.vue.previsionnel.module;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import astre.Controleur;
import astre.modele.BD;
import astre.modele.elements.Horaire;
import astre.modele.elements.ModuleIUT;
import astre.vue.outils.FiltreTextFieldEntier;

/** Classe PanelRepartitionPPP
  * @author : Clémentin Ly
  * @version : 2.0 - 14/12/2023
  * @date : 14/12/2023
  */

public class PanelRepartitionPPP extends JPanel
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	private Controleur  ctrl;

	private JTextField txtCM;
	private JTextField txtTD;
	private JTextField txtTP;
	private JTextField txtHTut;
	private JTextField txtHP;
	private JLabel     lblSomme;

	private JLabel lblTotalCM;
	private JLabel lblTotalTD;
	private JLabel lblTotalTP;
	private JLabel lblTotalHTut;
	private JLabel lblTotalHP;
	private JLabel lblTotalSomme;

	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/

	public PanelRepartitionPPP ( Controleur ctrl )
	{
		this.ctrl = ctrl;

		/* ------------------------- */
		/* Création des composants   */
		/* ------------------------- */

		this.setLayout ( new GridBagLayout() );
		this.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets ( 5, 5, 5, 5 );

		this.txtCM	  = new JTextField ( "", 2 );
		FiltreTextFieldEntier.appliquer ( txtCM );

		this.txtTD	  = new JTextField ( "", 2 );
		FiltreTextFieldEntier.appliquer ( txtTD );

		this.txtTP	  = new JTextField ( "", 2 );
		FiltreTextFieldEntier.appliquer ( txtTP );

		this.txtHTut  = new JTextField ( "", 2 );
		FiltreTextFieldEntier.appliquer ( txtHTut );

		this.txtHP    = new JTextField ( "", 2 );
		FiltreTextFieldEntier.appliquer ( txtHP );
		
		this.lblSomme = new JLabel();

		this.lblTotalCM   = new JLabel();
		this.lblTotalTD   = new JLabel();
		this.lblTotalTP   = new JLabel();
		this.lblTotalHTut = new JLabel();
		this.lblTotalHP   = new JLabel();
		this.lblTotalSomme = new JLabel();

		gbc.gridy = 0;
		gbc.gridx = 1;
		this.add ( new JLabel ( "CM" ), gbc );

		gbc.gridx = 2;
		this.add ( new JLabel ( "TD" ), gbc );
		
		gbc.gridx = 3;
		this.add ( new JLabel ( "TP" ), gbc );

		gbc.gridx = 4;
		this.add ( new JLabel ( "h Tut" ), gbc );

		gbc.gridx = 5;
		this.add ( new JLabel ( "h Ponct" ), gbc );

		gbc.gridx = 6;
		this.add ( new JLabel ( "Σ" ), gbc  );


		gbc.gridy = 1;
		gbc.gridx = 0;
		this.add ( new JLabel ( "Total promo (eqtd)" ), gbc );

		gbc.gridx = 1;
		this.add ( this.txtCM, gbc     );

		gbc.gridx = 2;
		this.add ( this.txtTD, gbc     );

		gbc.gridx = 3;
		this.add ( this.txtTP, gbc    );

		gbc.gridx = 4;
		this.add ( this.txtHTut, gbc    );

		gbc.gridx = 5;
		this.add ( this.txtHP, gbc    );

		gbc.gridx = 6;
		this.add ( this.lblSomme, gbc );

		gbc.gridy = 2;
		gbc.gridx = 0;
		this.add ( new JLabel ( "Total affecté (eqtd)" ), gbc );

		gbc.gridx = 1;
		this.add ( this.lblTotalCM, gbc    );

		gbc.gridx = 2;
		this.add ( this.lblTotalTD, gbc    );

		gbc.gridx = 3;
		this.add ( this.lblTotalTP, gbc    );

		gbc.gridx = 4;
		this.add ( this.lblTotalHTut, gbc    );

		gbc.gridx = 5;
		this.add ( this.lblTotalHP, gbc    );

		gbc.gridx = 6;
		this.add ( this.lblTotalSomme, gbc );


		/* ------------------------- */
		/* Activation des composants */
		/* ------------------------- */

		this.txtCM.addKeyListener   ( new AjoutKeyListenerSomme() );
		this.txtTD.addKeyListener   ( new AjoutKeyListenerSomme() );
		this.txtTP.addKeyListener   ( new AjoutKeyListenerSomme() );
		this.txtHTut.addKeyListener ( new AjoutKeyListenerSomme() );
		this.txtHP.addKeyListener   ( new AjoutKeyListenerSomme() );

		this.lblSomme.setBackground ( Color.LIGHT_GRAY );
		this.lblSomme.setPreferredSize ( new Dimension ( 40, 15 ) );
		this.lblSomme.setOpaque( true );

		this.lblTotalCM.setBackground ( Color.LIGHT_GRAY );
		this.lblTotalCM.setPreferredSize ( new Dimension ( 40, 15 ) );
		this.lblTotalCM.setOpaque( true );

		this.lblTotalTD.setBackground( Color.LIGHT_GRAY );
		this.lblTotalTD.setPreferredSize( new Dimension ( 40, 15 ) );
		this.lblTotalTD.setOpaque( true );

		this.lblTotalTP.setBackground( Color.LIGHT_GRAY );
		this.lblTotalTP.setPreferredSize( new Dimension ( 40, 15 ) );
		this.lblTotalTP.setOpaque( true );

		this.lblTotalHTut.setBackground( Color.LIGHT_GRAY );
		this.lblTotalHTut.setPreferredSize( new Dimension ( 40, 15 ) );
		this.lblTotalHTut.setOpaque( true );

		this.lblTotalHP.setBackground( Color.LIGHT_GRAY );
		this.lblTotalHP.setPreferredSize( new Dimension ( 40, 15 ) );
		this.lblTotalHP.setOpaque( true );

		this.lblTotalSomme.setBackground( Color.LIGHT_GRAY );
		this.lblTotalSomme.setPreferredSize( new Dimension ( 40, 15 ) );
		this.lblTotalSomme.setOpaque( true );
	}

	private class AjoutKeyListenerSomme implements KeyListener
	{
		public void keyTyped   ( KeyEvent e ) { majSomme();}
		public void keyPressed ( KeyEvent e ) {}
		public void keyReleased( KeyEvent e ) {}
	}

	private void majSomme()
	{
		try
		{
			int hCM     = 0;
			int hTD     = 0;
			int hTP     = 0;
			int hTut    = 0;
			int hPonct  = 0;

			if (!txtCM.getText().isEmpty() )
			{
				hCM = getCMRepartition();
			}

			if ( !txtTD.getText().isEmpty() )
			{
				hTD = getTDRepartition();
			}

			if ( !txtTP.getText().isEmpty() )
			{
				hTP = getTPRepartition();
			}

			if ( !txtHTut.getText().isEmpty() )
			{
				hTut = getHTutRepartition();
			}

			if ( !txtHP.getText().isEmpty() )
			{
				hPonct = getHPRepartition();
			}

			int somme = hCM + hTD + hTP + hTut + hPonct;

			this.lblSomme.setText ( Integer.toString ( somme ) );
		}
		catch ( NumberFormatException e )
		{
			this.lblSomme.setText ( "Erreur" );
		}
	}

	public void setModule ( ModuleIUT module )
	{
		this.txtCM  .setText( "0" );
		this.txtTD  .setText( "0" );
		this.txtTP  .setText( "0" );
		this.txtHTut.setText( "0" );
		this.txtHP  .setText( "0" );

		ArrayList<Horaire> lstHoraire = (ArrayList<Horaire>) BD.getInstance().getHoraires( module.getCode() );

		for(Horaire h : lstHoraire)
		{
			switch( h.getHeure().getNom().toUpperCase() )
			{
				case "CM"    : this.txtCM  .setText( h.getNbHeurePN() + "" ); break;
				case "TD"    : this.txtTD  .setText( h.getNbHeurePN() + "" ); break;
				case "TP"    : this.txtTP  .setText( h.getNbHeurePN() + "" ); break;
				case "TUT"   : this.txtHTut.setText( h.getNbHeurePN() + "" ); break;
				case "PONCT" : this.txtHP  .setText( h.getNbHeurePN() + "" ); break;
				default : ;
			}
		}
		majSomme();
	}

	public int getCMRepartition   ( ) { return Integer.parseInt ( this.txtCM  .getText() ); }
	public int getTDRepartition   ( ) { return Integer.parseInt ( this.txtTD  .getText() ); }
	public int getTPRepartition   ( ) { return Integer.parseInt ( this.txtTP  .getText() ); }
	public int getHTutRepartition ( ) { return Integer.parseInt ( this.txtHTut.getText() ); }
	public int getHPRepartition   ( ) { return Integer.parseInt ( this.txtHP  .getText() ); }
}