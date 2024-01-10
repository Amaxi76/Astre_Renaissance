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
import astre.modele.elements.Horaire;
import astre.modele.elements.ModuleIUT;
import astre.vue.outils.FiltreTextFieldEntier;

/** Classe PanelRepartitionBis
  * @author : Clémentin Ly
  * @version : 2.0 - 14/12/2023
  * @date : 14/12/2023
  */

public class PanelRepartitionBis extends JPanel
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	private Controleur  ctrl;
	private FrameModule frm;

	private JTextField txtHSae;
	private JTextField txtHTut;
	private JLabel     lblSomme;

	private JLabel     lblTotalHSaeAff;
	private JLabel     lblTotalHTutAff;
	private JLabel     lblTotalSommeAff;

	private List<JLabel>     lstLabelsHeures      = new ArrayList<>();
	private List<JTextField> lstTextFieldsHeures  = new ArrayList<>();

	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/

	public PanelRepartitionBis ( Controleur ctrl, FrameModule frm )
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

		this.txtHSae  = new JTextField ( "", 2 );
		FiltreTextFieldEntier.appliquer ( txtHSae );
		this.lstTextFieldsHeures.add ( txtHSae );

		this.txtHTut  = new JTextField ( "", 2 );
		FiltreTextFieldEntier.appliquer ( txtHTut );
		this.lstTextFieldsHeures.add ( txtHTut );
		
		this.lblSomme = new JLabel();

		this.lblTotalHSaeAff  = new JLabel();
		this.lblTotalHTutAff  = new JLabel();
		this.lblTotalSommeAff = new JLabel();

		gbc.gridy = 0;
		gbc.gridx = 1;
		this.add ( new JLabel ( "h Sae" ), gbc );
		this.lstLabelsHeures.add ( new JLabel ( "SAE" ) );

		gbc.gridx = 2;
		this.add ( new JLabel ( "h Tut" ), gbc );
		this.lstLabelsHeures.add ( new JLabel ( "Tut" ) );

		gbc.gridx = 3;
		this.add ( new JLabel ( "Σ" ), gbc      );


		gbc.gridy = 1;
		gbc.gridx = 0;
		this.add ( new JLabel ( "Total promo (eqtd)" ), gbc );

		gbc.gridx = 1;
		this.add ( this.txtHSae, gbc  );

		gbc.gridx = 2;
		this.add ( this.txtHTut, gbc  );

		gbc.gridx = 3;
		this.add ( this.lblSomme, gbc );

		gbc.gridy = 2;
		gbc.gridx = 0;
		this.add ( new JLabel ( "Total affecté (eqtd)" ), gbc );

		gbc.gridx = 1;
		this.add ( this.lblTotalHSaeAff, gbc  );

		gbc.gridx = 2;
		this.add ( this.lblTotalHTutAff, gbc  );

		gbc.gridx = 3;
		this.add ( this.lblTotalSommeAff, gbc );
		

		/* ------------------------- */
		/* Activation des composants */
		/* ------------------------- */

		this.txtHSae.addKeyListener ( new AjoutKeyListenerSomme() );
		this.txtHTut.addKeyListener ( new AjoutKeyListenerSomme() );

		this.lblSomme.setBackground ( Color.LIGHT_GRAY );
		this.lblSomme.setPreferredSize ( new Dimension ( 40, 15 ) );
		this.lblSomme.setOpaque( true );

		this.lblTotalHSaeAff.setBackground ( Color.LIGHT_GRAY );
		this.lblTotalHSaeAff.setPreferredSize ( new Dimension ( 40, 15 ) );
		this.lblTotalHSaeAff.setOpaque( true );

		this.lblTotalHTutAff.setBackground( Color.LIGHT_GRAY );
		this.lblTotalHTutAff.setPreferredSize( new Dimension ( 40, 15 ) );
		this.lblTotalHTutAff.setOpaque( true );

		this.lblTotalSommeAff.setBackground( Color.LIGHT_GRAY );
		this.lblTotalSommeAff.setPreferredSize( new Dimension ( 40, 15 ) );
		this.lblTotalSommeAff.setOpaque( true );
	}

	private class AjoutKeyListenerSomme implements KeyListener
	{
		public void keyTyped   ( KeyEvent e ) {}
		public void keyPressed ( KeyEvent e ) {}
		public void keyReleased( KeyEvent e ) { majSomme(); }
	}

	private void majSomme()
	{
		try
		{
			int hSae    = 0;
			int hTut    = 0;

			int nouvHeureValeur = 0;

			if (!txtHSae.getText().isEmpty() )
			{
				hSae = getSaeRepartition();
			}

			if ( !txtHTut.getText().isEmpty() )
			{
				hTut = getTutRepartition();
			}

			int somme = hSae + hTut;

			for (int i = 2; i < lstTextFieldsHeures.size(); i++)
			{
				JTextField textField = lstTextFieldsHeures.get(i);
				if ( !textField.getText().isEmpty() )
				{
					nouvHeureValeur = Integer.parseInt(textField.getText());
					somme += nouvHeureValeur;
				}
			}

			lblSomme.setText ( String.valueOf ( somme ) );
		}
		catch ( NumberFormatException ex )
		{
			lblSomme.setText ( "Erreur" );
		}

		majAffectation();
	}

	private void majAffectation()
	{
		lblTotalHSaeAff.setText ( String.valueOf ( this.ctrl.getNBHeureEQTD ( this.frm.getPanelModuleLabel().getCode(), "SAE") ) );
		lblTotalHTutAff.setText ( String.valueOf ( this.ctrl.getNBHeureEQTD ( this.frm.getPanelModuleLabel().getCode(), "TUT") ) );

		majAffectationSomme();
	}

	private void majAffectationSomme()
	{
		int SAE = Integer.parseInt( lblTotalHSaeAff.getText() );
		int TUT = Integer.parseInt( lblTotalHTutAff.getText() );

		int somme = SAE + TUT;
		lblTotalSommeAff.setText ( String.valueOf ( somme ) );
	}

	public void setModule ( ModuleIUT module )
	{
		this.txtHSae.setText( "0" );
		this.txtHTut.setText( "0" );
		
		ArrayList<Horaire> lstHoraire = (ArrayList<Horaire>) BD.getInstance().getHoraires( module.getCode() );

		for(Horaire h : lstHoraire)
		{
			switch( h.getHeure().getNom().toUpperCase() )
			{
				case "SAE" : this.txtHSae.setText( h.getNbHeure  () + "" );
				case "TUT" : this.txtHTut.setText( h.getNbHeure  () + "" );

				default : ;
			}
		}
		majSomme();
	}

	public int getSaeRepartition() { return Integer.parseInt ( this.txtHSae.getText() ); }
	public int getTutRepartition() { return Integer.parseInt ( this.txtHTut.getText() ); }

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

		JTextField textFieldHeure  = new JTextField ( "", 2 );
		FiltreTextFieldEntier.appliquer ( textFieldHeure );
	
		this.lstLabelsHeures     .add ( labelNomHeure   );
		this.lstTextFieldsHeures .add ( textFieldHeure  );

	
		gbc.gridy = this.lstLabelsHeures.size();
		gbc.gridx = 1;
		this.add ( labelNomHeure, gbc );
	
		gbc.gridx = 2;
		this.add ( textFieldHeure, gbc );

		textFieldHeure.addKeyListener ( new AjoutKeyListenerSomme() );

		this.revalidate();
	}

	public void supprimerHeure ( String nomHeure )
	{
		for ( int i = 0; i < this.lstLabelsHeures.size(); i++)
		{
			if ( nomHeure.equals ( "SAE" ) || nomHeure.equals ( "Tut" ) )
			{
				return;
			}
			if ( nomHeure.equals ( lstLabelsHeures.get(i).getText() ) )
			{
				//Supprimer du Panel
				this.remove ( lstLabelsHeures     .get(i) );
				this.remove ( lstTextFieldsHeures .get(i) );

				//Supprimer de la liste
				lstLabelsHeures     .remove(i);
				lstTextFieldsHeures .remove(i);

				this.revalidate();
			}
		}
	}
}