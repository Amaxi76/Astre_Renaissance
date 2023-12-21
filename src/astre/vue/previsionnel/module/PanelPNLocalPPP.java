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

/** Classe PanelPNLocalPPP
  * @author : Clémentin Ly
  * @version : 2.0 - 14/12/2023
  * @date : 14/12/2023
  */

public class PanelPNLocalPPP extends JPanel
{
	
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	private Controleur  ctrl;
	private FrameModule frm;

	private JTextField txtCM;
	private JTextField txtTD;
	private JTextField txtTP;
	private JTextField txtHTut;
	private JTextField txtHP;
	private JLabel     lblSomme;

	private List<JLabel>     lstLabelsHeures      = new ArrayList<>();
	private List<JTextField> lstTextFieldsHeures  = new ArrayList<>();

	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/

	public PanelPNLocalPPP ( Controleur ctrl, FrameModule frm )
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

		this.txtCM	  = new JTextField ( "", 2 );
		FiltreTextFieldEntier.appliquer ( txtCM );
		this.lstTextFieldsHeures.add ( txtCM );

		this.txtTD	  = new JTextField ( "", 2 );
		FiltreTextFieldEntier.appliquer ( txtTD );
		this.lstTextFieldsHeures.add ( txtTD );

		this.txtTP	  = new JTextField ( "", 2 );
		FiltreTextFieldEntier.appliquer ( txtTP );
		this.lstTextFieldsHeures.add ( txtTP );

		this.txtHTut  = new JTextField ( "", 2 );
		FiltreTextFieldEntier.appliquer ( txtHTut );
		this.lstTextFieldsHeures.add ( txtHTut );

		this.txtHP    = new JTextField ( "", 2 );
		FiltreTextFieldEntier.appliquer ( txtHP );
		this.lstTextFieldsHeures.add ( txtHP );
		
		this.lblSomme = new JLabel();

		gbc.gridy = 0;
		gbc.gridx = 1;
		this.add ( new JLabel ( "CM" ), gbc );
		this.lstLabelsHeures.add ( new JLabel ( "CM" ) );

		gbc.gridx = 2;
		this.add ( new JLabel ( "TD" ), gbc );
		this.lstLabelsHeures.add ( new JLabel ( "TD" ) );

		gbc.gridx = 3;
		this.add ( new JLabel ( "TP" ), gbc );
		this.lstLabelsHeures.add ( new JLabel ( "TP" ) );

		gbc.gridx = 4;
		this.add ( new JLabel ( "h Tut" ), gbc );
		this.lstLabelsHeures.add ( new JLabel ( "Tut" ) );

		gbc.gridx = 5;
		this.add ( new JLabel ( "h Ponct" ), gbc );
		this.lstLabelsHeures.add ( new JLabel ( "PONCT" ) );

		gbc.gridx = 6;
		this.add ( new JLabel ( "Σ" ), gbc  );


		gbc.gridy = 1;
		gbc.gridx = 0;
		this.add ( new JLabel ( "Total (eqtd) promo" ), gbc );

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

			int nouvHeureValeur = 0;

			if (!txtCM.getText().isEmpty() )
			{
				hCM = getCM();
			}

			if ( !txtTD.getText().isEmpty() )
			{
				hTD = getTD();
			}

			if ( !txtTP.getText().isEmpty() )
			{
				hTP = getTP();
			}

			if ( !txtHTut.getText().isEmpty() )
			{
				hTut = getTut();
			}

			if ( !txtHP.getText().isEmpty() )
			{
				hPonct = getPonct();
			}

			int somme = hCM + hTD + hTP + hTut + hPonct;

			for (int i = 5; i < lstTextFieldsHeures.size(); i++)
			{
				JTextField textField = lstTextFieldsHeures.get(i);
				if ( !textField.getText().isEmpty() )
				{
					nouvHeureValeur = Integer.parseInt(textField.getText());
					somme += nouvHeureValeur;
				}
			}

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
				case "Tut"   : this.txtHTut.setText( h.getNbHeurePN() + "" ); break;
				case "PONCT" : this.txtHP  .setText( h.getNbHeurePN() + "" ); break;
				
				default : ;
			}
		}

		majSomme();
	}

	public int getCM    ( ) { return Integer.parseInt( this.txtCM  .getText() ); }
	public int getTD    ( ) { return Integer.parseInt( this.txtTD  .getText() ); }
	public int getTP    ( ) { return Integer.parseInt( this.txtTP  .getText() ); }
	public int getTut   ( ) { return Integer.parseInt( this.txtHTut.getText() ); }
	public int getPonct ( ) { return Integer.parseInt( this.txtHP  .getText() ); }

	public void ajouterHeure ( String nomHeure )
	{
		for ( int i = 0; i < this.lstLabelsHeures.size(); i++)
		{
			if ( nomHeure.equals ( lstLabelsHeures.get(i).getText() ) )
			{
				this.frm.messageErreurAjouter();
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
			if ( nomHeure.equals ( "CM" ) || nomHeure.equals ( "TD" ) || nomHeure.equals ( "TP" ) || nomHeure.equals ( "Tut" ) || nomHeure.equals ( "PONCT" ) )
			{
				this.frm.messageErreurSupprimer();
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