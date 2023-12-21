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

/** Classe PanelPNLocalBis
  * @author : Clémentin Ly
  * @version : 2.0 - 14/12/2023
  * @date : 14/12/2023
  */

public class PanelPNLocalBis extends JPanel
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	private Controleur  ctrl;

	private JTextField txtHSae;
	private JTextField txtHTut;
	private JLabel     lblSomme;

	private List<JLabel>     lstLabelsHeures      = new ArrayList<>();
	private List<JTextField> lstTextFieldsHeures  = new ArrayList<>();

	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/

	public PanelPNLocalBis ( Controleur ctrl )
	{
		this.ctrl = ctrl;

		/* ------------------------- */
		/* Création des composants   */
		/* ------------------------- */

		this.setLayout ( new GridBagLayout() );
		this.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets ( 5, 5, 5, 5 );

		this.txtHSae = new JTextField ( "", 2 );
		FiltreTextFieldEntier.appliquer ( txtHSae );
		this.lstTextFieldsHeures.add ( txtHSae );

		this.txtHTut = new JTextField ( "", 2 );
		FiltreTextFieldEntier.appliquer ( txtHTut );
		this.lstTextFieldsHeures.add ( txtHTut );
		
		this.lblSomme   = new JLabel();

		gbc.gridy = 0;
		gbc.gridx = 1;
		this.add ( new JLabel ( "h Sae" ), gbc );
		this.lstLabelsHeures.add ( new JLabel ( "SAE" ) );

		gbc.gridx = 2;
		this.add ( new JLabel ( "h Tut" ), gbc );
		this.lstLabelsHeures.add ( new JLabel ( "Tut" ) );

		gbc.gridx = 3;
		this.add ( new JLabel ( "Σ" ), gbc  );


		gbc.gridy = 1;
		gbc.gridx = 0;
		this.add ( new JLabel ( "Total (eqtd) promo" ), gbc );

		gbc.gridx = 1;
		this.add ( this.txtHSae, gbc    );

		gbc.gridx = 2;
		this.add ( this.txtHTut, gbc    );

		gbc.gridx = 3;
		this.add ( this.lblSomme, gbc );

		/* ------------------------- */
		/* Activation des composants */
		/* ------------------------- */

		this.txtHSae.addKeyListener ( new AjoutKeyListenerSomme() );
		this.txtHTut.addKeyListener ( new AjoutKeyListenerSomme() );

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
			int hSae    = 0;
			int hTut    = 0;

			int nouvHeureValeur = 0;

			if (!txtHSae.getText().isEmpty() )
			{
				hSae = getSae();
			}

			if ( !txtHTut.getText().isEmpty() )
			{
				hTut = getTut();
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
				case "SAE" : this.txtHSae.setText( h.getNbHeurePN() + "" ); break;
				case "TUT" : this.txtHTut.setText( h.getNbHeurePN() + "" ); break;
				//case "REH" : this.txtTD.setText( h.getNbHeurePN() + "" ); break;
				default : ;
			}
		}

		majSomme();
	}

	public int getSae() { return Integer.parseInt( this.txtHSae.getText() ); }
	public int getTut() { return Integer.parseInt( this.txtHTut.getText() ); }

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