package astre.vue.previsionnel.module;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import astre.Controleur;
import astre.modele.BD;
import astre.modele.elements.Heure;
import astre.modele.elements.Horaire;
import astre.modele.elements.ModuleIUT;
import astre.vue.outils.FiltreTextFieldEntier;

/** Classe PanelRepartition
  * @author : Clémentin Ly
  * @version : 2.0 - 14/12/2023
  * @date : 12/12/2023
  */

public class PanelRepartition extends JPanel
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	private Controleur  ctrl;
	private FrameModule frm;

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

	private List<JLabel>     lstLabelsHeures        = new ArrayList<>();
	private List<JTextField> lstTextFieldsNbSem     = new ArrayList<>();
	private List<JTextField> lstTextFieldsNbHeures  = new ArrayList<>();
	private List<JLabel>     lstLabelsTotalHeures   = new ArrayList<>();

	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/
	
	public PanelRepartition ( Controleur ctrl, FrameModule frm )
	{
		this.ctrl = ctrl;
		this.frm  = frm;

		/* ------------------------- */
		/* Création des composants   */
		/* ------------------------- */

		this.setLayout ( new GridBagLayout() );
		this.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets ( 5, 5, 5, 5 );

		this.txtNbSemCM = new JTextField ("", 2);
		FiltreTextFieldEntier.appliquer ( txtNbSemCM );
		this.lstTextFieldsNbSem.add ( txtNbSemCM );

		this.txtNbHCM   = new JTextField ("", 2);
		FiltreTextFieldEntier.appliquer ( txtNbHCM );
		this.lstTextFieldsNbHeures.add ( txtNbHCM );

		this.txtNbSemTD = new JTextField ("", 2);
		FiltreTextFieldEntier.appliquer ( txtNbSemTD );
		this.lstTextFieldsNbSem.add ( txtNbSemTD );

		this.txtNbHTD   = new JTextField ("", 2);
		FiltreTextFieldEntier.appliquer ( txtNbHTD );
		this.lstTextFieldsNbHeures.add ( txtNbHTD );
		

		this.txtNbSemTP = new JTextField ("", 2);
		FiltreTextFieldEntier.appliquer ( txtNbSemTP );
		this.lstTextFieldsNbSem.add ( txtNbSemTP );

		this.txtNbHTP   = new JTextField ("", 2);
		FiltreTextFieldEntier.appliquer ( txtNbHTP );
		this.lstTextFieldsNbHeures.add ( txtNbHTP );

		this.lblTotalCM    = new JLabel();
		this.lstLabelsTotalHeures.add ( lblTotalCM );

		this.lblTotalTD    = new JLabel();
		this.lstLabelsTotalHeures.add ( lblTotalTD );

		this.lblTotalTP    = new JLabel();
		this.lstLabelsTotalHeures.add ( lblTotalTP );

		this.txtHeureP     = new JTextField("", 2);
		FiltreTextFieldEntier.appliquer ( txtHeureP );
		this.lstTextFieldsNbSem   .add ( txtHeureP );
		this.lstTextFieldsNbHeures.add ( txtHeureP );

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
		this.lstLabelsHeures.add ( new JLabel ( "CM" ) );

		gbc.gridx = 2;
		this.add ( new JLabel ( "TD" ), gbc );
		this.lstLabelsHeures.add ( new JLabel ( "TD" ) );

		gbc.gridx = 4;
		this.add ( new JLabel ( "TP" ), gbc );
		this.lstLabelsHeures.add ( new JLabel ( "TP" ) );


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
		this.lstLabelsHeures.add ( new JLabel ( "PONCT" ) );
		this.lstLabelsTotalHeures.add ( new JLabel ( String.valueOf( txtHeureP ) ) );

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
			public void keyTyped    ( KeyEvent e ) { majTotalCM();
													 majTotalHeure(); }
			public void keyPressed  ( KeyEvent e ) {}
			public void keyReleased ( KeyEvent e ) {}
		});

		this.txtNbHCM.addKeyListener ( new KeyListener()
		{
			public void keyTyped    ( KeyEvent e ) { majTotalCM();
													 majTotalHeure(); }
			public void keyPressed  ( KeyEvent e ) {}
			public void keyReleased ( KeyEvent e ) {}
		});

		this.txtNbSemTD.addKeyListener ( new KeyListener()
		{
			public void keyTyped    ( KeyEvent e ) { majTotalTD();
													 majTotalHeure(); }
			public void keyPressed  ( KeyEvent e ) {}
			public void keyReleased ( KeyEvent e ) {}
		});

		this.txtNbHTD.addKeyListener ( new KeyListener()
		{
			public void keyTyped    ( KeyEvent e ) { majTotalTD();
													 majTotalHeure(); }
			public void keyPressed  ( KeyEvent e ) {}
			public void keyReleased ( KeyEvent e ) {}
		});

		this.txtNbSemTP.addKeyListener ( new KeyListener()
		{
			public void keyTyped    ( KeyEvent e ) { majTotalTP();
													 majTotalHeure(); }
			public void keyPressed  ( KeyEvent e ) {}
			public void keyReleased ( KeyEvent e ) {}
		});

		this.txtNbHTP.addKeyListener ( new KeyListener()
		{
			public void keyTyped    ( KeyEvent e ) { majTotalTP();
													 majTotalHeure(); }
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
		this.lblTotalCM.setPreferredSize ( new Dimension ( 40, 15 ) );
		this.lblTotalCM.setOpaque( true );

		this.lblTotalTD.setBackground( Color.LIGHT_GRAY );
		this.lblTotalTD.setPreferredSize( new Dimension ( 40, 15 ) );
		this.lblTotalTD.setOpaque( true );

		this.lblTotalTP.setBackground( Color.LIGHT_GRAY );
		this.lblTotalTP.setPreferredSize( new Dimension ( 40, 15 ) );
		this.lblTotalTP.setOpaque( true );

		this.lblTotalSomme.setBackground( Color.LIGHT_GRAY );
		this.lblTotalSomme.setPreferredSize( new Dimension ( 40, 15 ) );
		this.lblTotalSomme.setOpaque( true );



		this.lblTotalCMProm.setBackground ( Color.LIGHT_GRAY );
		this.lblTotalCMProm.setPreferredSize ( new Dimension ( 40, 15 ) );
		this.lblTotalCMProm.setOpaque( true );

		this.lblTotalTDProm.setBackground( Color.LIGHT_GRAY );
		this.lblTotalTDProm.setPreferredSize( new Dimension ( 40, 15 ) );
		this.lblTotalTDProm.setOpaque( true );

		this.lblTotalTPProm.setBackground( Color.LIGHT_GRAY );
		this.lblTotalTPProm.setPreferredSize( new Dimension ( 40, 15 ) );
		this.lblTotalTPProm.setOpaque( true );

		this.lblTotalHeurePProm.setBackground( Color.LIGHT_GRAY );
		this.lblTotalHeurePProm.setPreferredSize( new Dimension ( 40, 15 ) );
		this.lblTotalHeurePProm.setOpaque( true );

		this.lblTotalSommeProm.setBackground( Color.LIGHT_GRAY );
		this.lblTotalSommeProm.setPreferredSize( new Dimension ( 40, 15 ) );
		this.lblTotalSommeProm.setOpaque( true );


		this.lblTotalCMAff.setBackground ( Color.LIGHT_GRAY );
		this.lblTotalCMAff.setPreferredSize ( new Dimension ( 40, 15 ) );
		this.lblTotalCMAff.setOpaque( true );

		this.lblTotalTDAff.setBackground( Color.LIGHT_GRAY );
		this.lblTotalTDAff.setPreferredSize( new Dimension ( 40, 15 ) );
		this.lblTotalTDAff.setOpaque( true );

		this.lblTotalTPAff.setBackground( Color.LIGHT_GRAY );
		this.lblTotalTPAff.setPreferredSize( new Dimension ( 40, 15 ) );
		this.lblTotalTPAff.setOpaque( true );

		this.lblTotalHeurePAff.setBackground( Color.LIGHT_GRAY );
		this.lblTotalHeurePAff.setPreferredSize( new Dimension ( 40, 15 ) );
		this.lblTotalHeurePAff.setOpaque( true );

		this.lblTotalSommeAff.setBackground( Color.LIGHT_GRAY );
		this.lblTotalSommeAff.setPreferredSize( new Dimension ( 40, 15 ) );
		this.lblTotalSommeAff.setOpaque( true );
	}

	private void majTotalCM()
	{
		int nbSemCM = 0;
		int nbHCM   = 0;

		if (!txtNbSemCM.getText().isEmpty() )
		{
			nbSemCM = getNbSemCM();
		}

		if (!txtNbHCM.getText().isEmpty() )
		{
			nbHCM = getNbHCM();
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
			nbSemTD = getNbSemTD();
		}

		if (!txtNbHTD.getText().isEmpty() )
		{
			nbHTD = getNbHTD();
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
			nbSemTP = getNbSemTP();
		}

		if (!txtNbHTP.getText().isEmpty() )
		{
			nbHTP = getNbHTP();
		}

		int totalTP = nbSemTP * nbHTP;
		lblTotalTP.setText ( String.valueOf ( totalTP ) );
	}

	private void majTotalNouvHeure()
	{
		int nouvSemaine = 0;
		int nouvHeure   = 0;

		for (int i = 4; i < this.lstTextFieldsNbHeures.size(); i++)
		{
			JTextField textFieldSem = this.lstTextFieldsNbSem   .get(i);
			JTextField textFieldH   = this.lstTextFieldsNbHeures.get(i);

			if ( !textFieldSem.getText().isEmpty() )
			{
				nouvSemaine = Integer.parseInt ( textFieldSem.getText() );
			}
			if ( !textFieldH.getText().isEmpty() )
			{
				nouvHeure = Integer.parseInt ( textFieldH.getText() );
			}

			int totalNouvHeure = nouvSemaine * nouvHeure;
			this.lstLabelsTotalHeures.get(i).setText ( String.valueOf ( totalNouvHeure ) );
		}
	}

	private void majTotalHeure()
	{
		int totalCM = 0;
		int totalTD = 0;
		int totalTP = 0;
		int heureP  = 0;

		int nouvHeure = 0;

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

		for ( int i = 4; i < this.lstLabelsTotalHeures.size(); i++)
		{
			JLabel labelTotalHeure = this.lstLabelsTotalHeures.get(i);

			if ( !labelTotalHeure.getText().isEmpty() )
			{
				nouvHeure = Integer.parseInt ( labelTotalHeure.getText() );
			}
		}

		int somme = totalCM + totalTD + totalTP + heureP + nouvHeure;
		lblTotalSomme.setText ( String.valueOf ( somme ) );

		majAffectation();
		majTotalPromo(totalCM, totalTD, totalTP, heureP);
	}

	private void majTotalPromo(int totalCM, int totalTD, int totalTP, int heureP)
	{
		double coeffCM = coeffHeure ( "CM" );
		double totalCMProm = totalCM * coeffCM;
		lblTotalCMProm.setText( String.valueOf ( totalCMProm ) );

		double coeffTD = coeffHeure ( "TD" );
		int nbGpTD = frm.getPanelModuleLabel().getNbGpTD();
		double totalTDProm = totalTD * coeffTD * nbGpTD;
		lblTotalTDProm.setText( String.valueOf ( totalTDProm ) );

		double coeffTP = coeffHeure ( "TP" );
		int nbGpTP = frm.getPanelModuleLabel().getNbGpTP();
		double totalTPProm = totalTP * coeffTP * nbGpTP;
		lblTotalTPProm.setText( String.valueOf ( totalTPProm ) );

		double totalHeurePProm = heureP * nbGpTD;
		lblTotalHeurePProm.setText( String.valueOf ( totalHeurePProm ) );

		double sommePromo = totalCMProm + totalTDProm + totalTPProm + totalHeurePProm;
		lblTotalSommeProm.setText ( String.valueOf ( sommePromo ) );
	}

	private void majAffectation ()
	{
		lblTotalCMAff    .setText ( String.valueOf ( this.ctrl.getNBHeureEQTD ( this.frm.getPanelModuleLabel().getCode(), "CM"    ) ) );
		lblTotalTDAff    .setText ( String.valueOf ( this.ctrl.getNBHeureEQTD ( this.frm.getPanelModuleLabel().getCode(), "TD"    ) ) );
		lblTotalTPAff    .setText ( String.valueOf ( this.ctrl.getNBHeureEQTD ( this.frm.getPanelModuleLabel().getCode(), "TP"    ) ) );
		lblTotalHeurePAff.setText ( String.valueOf ( this.ctrl.getNBHeureEQTD ( this.frm.getPanelModuleLabel().getCode(), "PONCT" ) ) );

		majAffectationSomme();
	}

	private void majAffectationSomme()
	{
		int CM = Integer.parseInt ( lblTotalCMAff.getText() );
		int TD = Integer.parseInt ( lblTotalTDAff.getText() );
		int TP = Integer.parseInt ( lblTotalTPAff.getText() );
		int HP  = Integer.parseInt ( lblTotalHeurePAff.getText() );

		int somme = CM + TD + TP + HP;
		lblTotalSommeAff.setText ( String.valueOf ( somme ) );
	}

	private double coeffHeure ( String nomHeure )
	{
		Heure heure = this.ctrl.getHeure ( nomHeure );

		double coefficient = 0.0;

		switch (nomHeure)
		{
			case "CM":
				coefficient = heure.getCoefTd();
				break;
			case "TD":
				coefficient = heure.getCoefTd();
				break;
			case "TP":
				coefficient = heure.getCoefTd();
				break;
		
			default:
				break;
		}

		return coefficient;
	}

	public void setModule ( ModuleIUT module )
	{
		this.txtNbSemCM.setText( "0" );
		this.txtNbHCM  .setText( "0" );
		this.txtNbSemTD.setText( "0" );
		this.txtNbHTD  .setText( "0" );
		this.txtNbSemTP.setText( "0" );
		this.txtNbHTP  .setText( "0" );
		this.txtHeureP .setText( "0" );
		
		ArrayList<Horaire> lstHoraire = (ArrayList<Horaire>) BD.getInstance().getHoraires( module.getCode() );

		for(Horaire h : lstHoraire)
		{
			switch( h.getHeure().getNom().toUpperCase() )
			{
				case "CM" : this.txtNbHCM  .setText( h.getNbHeure  () + "" );
				            this.txtNbSemCM.setText( h.getNbSemaine() + "" ); break;

				case "TP" : this.txtNbHTP  .setText( h.getNbHeure  () + "" );
				            this.txtNbSemTP.setText( h.getNbSemaine() + "" ); break;

				case "TD" : this.txtNbHTD  .setText( h.getNbHeure  () + "" );
				            this.txtNbSemTD.setText( h.getNbSemaine() + "" ); break;
				default : ;
			}
		}

		majTotalCM();
		majTotalTD();
		majTotalTP();
		majTotalHeure();
	}

	public int getNbSemCM() { return Integer.parseInt ( this.txtNbSemCM.getText() ); }
	public int getNbHCM()   { return Integer.parseInt ( this.txtNbHCM  .getText() ); }
	public int getNbSemTD() { return Integer.parseInt ( this.txtNbSemTD.getText() ); }
	public int getNbHTD()   { return Integer.parseInt ( this.txtNbHTD  .getText() ); }
	public int getNbSemTP() { return Integer.parseInt ( this.txtNbSemTP.getText() ); }
	public int getNbHTP()   { return Integer.parseInt ( this.txtNbHTP  .getText() ); }

	public void ajouterHeure ( String nomHeure )
	{
		for ( int i = 0; i < this.lstLabelsHeures.size(); i++)
		{
			if ( nomHeure.equals ( lstLabelsHeures.get(i).getText() ) )
			{
				return;
			}
		}

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets ( 5, 5, 5, 5 );
	
		JLabel     labelNomHeure      = new JLabel ( nomHeure );

		JTextField textFieldNbSem  = new JTextField ( "", 2 );
		FiltreTextFieldEntier.appliquer ( textFieldNbSem);

		JTextField textFieldNbHeure  = new JTextField ( "", 2 );
		FiltreTextFieldEntier.appliquer ( textFieldNbHeure );

		JLabel     labelTotalHeure = new JLabel ( );
		labelTotalHeure.setBackground ( Color.LIGHT_GRAY );
		labelTotalHeure.setPreferredSize ( new Dimension ( 40, 15 ) );
		labelTotalHeure.setOpaque( true );
	
		this.lstLabelsHeures       .add ( labelNomHeure     );
		this.lstTextFieldsNbSem	   .add ( textFieldNbSem    );
		this.lstTextFieldsNbHeures .add ( textFieldNbHeure  );
		this.lstLabelsTotalHeures  .add ( labelTotalHeure   );

	
		gbc.gridy = this.lstLabelsHeures.size();
		gbc.gridx = 1;
		this.add ( labelNomHeure, gbc );
	
		gbc.gridx = 2;
		this.add ( textFieldNbSem, gbc );

		gbc.gridx = 3;
		this.add ( textFieldNbHeure, gbc );

		gbc.gridx = 4;
		this.add ( labelTotalHeure, gbc );

		textFieldNbSem.addKeyListener ( new KeyListener()
		{
			public void keyTyped    ( KeyEvent e ) { majTotalNouvHeure();
													 majTotalHeure(); }
			public void keyPressed  ( KeyEvent e ) {}
			public void keyReleased ( KeyEvent e ) {}
		});

		textFieldNbHeure.addKeyListener ( new KeyListener()
		{
			public void keyTyped    ( KeyEvent e ) { majTotalNouvHeure();
													 majTotalHeure(); }
			public void keyPressed  ( KeyEvent e ) {}
			public void keyReleased ( KeyEvent e ) {}
		});

		this.revalidate();
	}

	public void supprimerHeure ( String nomHeure )
	{
		for ( int i = 0; i < this.lstLabelsHeures.size(); i++)
		{
			if ( nomHeure.equals ( lstLabelsHeures.get(i).getText() ) )
			{
				//Supprimer du Panel
				this.remove ( lstLabelsHeures       .get(i) );
				this.remove ( lstTextFieldsNbSem    .get(i) );
				this.remove ( lstTextFieldsNbHeures .get(i) );
				this.remove ( lstLabelsTotalHeures  .get(i) );

				//Supprimer de la liste
				lstLabelsHeures       .remove(i);
				lstTextFieldsNbSem    .remove(i);
				lstTextFieldsNbHeures .remove(i);
				lstLabelsTotalHeures  .remove(i);

				this.revalidate();
			}
		}
	}
}