package astre.vue.previsionnel.module;

import java.util.HashMap;
import java.util.Map;

import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import astre.Controleur;
import astre.modele.elements.Heure;
import astre.modele.elements.ModuleIUT;
import astre.vue.outils.FiltreTextFieldEntier;

public class PanelPNLocal extends JPanel
{
	private Controleur ctrl;

	private String[] ensIntitule;
	private Map<String, JTextField> ensTxtNbHeure;
	private Map<String, JLabel>     ensLblTotalPromo;

	private JLabel lblSommePromo;
	private JLabel lblSommeEQTDPromo;

	public PanelPNLocal ( Controleur ctrl, String nomTypeModule )
	{
		/* ------------------------- */
		/* Propriétés générales      */
		/* ------------------------- */

		this.ctrl = ctrl;
		this.setLayout ( new GridBagLayout() );
		this.setBorder ( BorderFactory.createLineBorder ( Color.GRAY, 1 ) );


		/* ------------------------- */
		/* Création des composants   */
		/* ------------------------- */

		this.initialiserComposantsModule ( nomTypeModule );


		/* ------------------------- */
		/* Ajout des composants      */
		/* ------------------------- */

		GridBagConstraints gbc = new GridBagConstraints ( );
		gbc.insets = new Insets ( 5, 5, 5, 5 );
		this.placementComposantsModule ( gbc );


		/* ------------------------- */
		/* Activation des composants */
		/* ------------------------- */

		this.activationComposantsModule ( );

	}


	private void initialiserComposantsModule ( String nomTypeModule )
	{
		this.ensTxtNbHeure    = new HashMap<String, JTextField>();
		this.ensLblTotalPromo = new HashMap<String, JLabel>();

		this.ensIntitule = switch ( nomTypeModule )
		{
			case "Ressource" -> new String[] { "CM", "TP", "TD" };
			case "SAE"       -> new String[] { "h Sae", "h Tut" };
			case "Stage"     -> new String[] { "REH", "h Tut"   };
			case "PPP"       -> new String[] { "CM", "TP", "TD", "HP", "HT" };
			default -> { break; }
		};

		for ( String intitule : this.ensIntitule )
		{
			JTextField txtTmp = new JTextField ( ); //3
			txtTmp.setPreferredSize ( new Dimension ( 40, 15 ) );
			FiltreTextFieldEntier.appliquer ( txtTmp );
			
			JLabel lblTmp = new JLabel ( );
			lblTmp.setPreferredSize ( new Dimension ( 40, 15 ) );
			lblTmp.setBackground ( Color.LIGHT_GRAY );
			lblTmp.setOpaque ( true );

			this.ensTxtNbHeure.put    ( intitule, new JTextField ( 3 )  );
			this.ensLblTotalPromo.put ( intitule, new JLabel ( )        );
		}

		this.lblSommePromo = new JLabel ( "0", 3 );
		this.lblSommeEQTDPromo = new JLabel ( "0", 3 );
	}


	private void placementComposantsModule ( GridBagConstraints gbc )
	{
		//placement du commentaire
		gbc.gridx = 0;
		gbc.gridy = 2;
		this.add ( new JLabel ("Total (eqtd) promo") );

		//placement des intitulés et leurs informations
		for ( int cptColonnes = 0; cptColonnes < this.ensIntitule.length; cptColonnes++ )
		{
			gbc.gridx = cptColonnes+1;

			String intitule = this.ensIntitule [ cptColonnes ];
			
			gbc.gridy = 0;
			this.add ( new JLabel ( intitule ), gbc );
			gbc.gridy = 1;
			this.add ( this.ensTxtNbHeure.get    ( intitule ), gbc );
			gbc.gridy = 2;
			this.add ( this.ensLblTotalPromo.get ( intitule ), gbc );
		}

		//placement de la colonne des sommes
		gbc.gridx = this.ensIntitule.length+1;
		gbc.gridy = 0;
		this.add ( new JLabel ( "Σ" ), gbc );
		gbc.gridy = 1;
		this.add ( this.lblSommePromo, gbc );
		gbc.gridy = 2;
		this.add ( this.lblSommeEQTDPromo, gbc );
	}


	private void activationComposantsModule ( )
	{
		for ( String intitule : this.ensIntitule )
		{
			this.ensTxtNbHeure.get ( intitule ).addKeyListener ( new AjoutKeyListenerSomme ( ) );
		}
	}

	private class AjoutKeyListenerSomme implements KeyListener
	{
		@Override public void keyTyped   ( KeyEvent e ) { /* */ }
		@Override public void keyPressed ( KeyEvent e ) { /* */ }
		@Override public void keyReleased( KeyEvent e )
		{
			majSomme      ( );
			majTotalHeure ( );
		}
	}

	private void majSomme ( )
	{
		//TODO: adapter l'ancien mais avec les hashmap
		ensTxtNbHeure.forEach( ( cle, valeur ) ->
		{
			int somme = 0;
			if ( !valeur.getText().isEmpty() )
			{
				somme = Integer.parseInt ( valeur.getText() );
			}
			this.lblSommePromo.setText ( String.valueOf ( somme ) );
		} );

	}

	private void majSomme()
	{
		try
		{
			int CM    = 0;
			int TD    = 0;
			int TP    = 0;
			
			//TEST MODULABLE
			int nouvHeureValeur = 0;
			
			if (!txtCM.getText().isEmpty() )
			{
				CM = Integer.parseInt ( txtCM.getText() );
			}

			if ( !txtTD.getText().isEmpty() )
			{
				TD = Integer.parseInt ( txtTD.getText() );
			}

			if (!txtTP.getText().isEmpty() )
			{
				TP = Integer.parseInt ( txtTP.getText() );
			}

			int somme = CM + TD + TP;

			//TEST MODULABLE
			for (int i = 3; i < lstTextFieldsHeures.size(); i++) //i = 3 car il y a déjà CM, TD et TP
			{
				JTextField textField = lstTextFieldsHeures.get(i);
				if ( !textField.getText().isEmpty() )
				{
					nouvHeureValeur = Integer.parseInt(textField.getText());
					somme += nouvHeureValeur;
				}
			}

			lblSomme.setText ( String.valueOf ( somme ) );


			double totalCM = 0;
			double totalTD = 0;
			double totalTP = 0;

			//TEST MODULABLE
			double nouvTotalHeureValeur = 0;

			if ( !lblTotalCM.getText().isEmpty() )
			{
				totalCM = Double.parseDouble ( lblTotalCM.getText() );
			}

			if ( !lblTotalTD.getText().isEmpty() )
			{
				totalTD = Double.parseDouble ( lblTotalTD.getText() );
			}
			
			if ( !lblTotalTP.getText().isEmpty() )
			{
				totalTP = Double.parseDouble ( lblTotalTP.getText() );
			}

			double totalSomme = totalCM + totalTD + totalTP;

			//TEST MODULABLE
			for (int i = 3; i < lstLabelsTotalHeures.size(); i++) //i = 3 car il y a déjà CM, TD et TP
			{
				JLabel labelTotalHeure = lstLabelsTotalHeures.get(i);

				if ( !labelTotalHeure.getText().isEmpty() )
				{
					nouvTotalHeureValeur = Double.parseDouble ( labelTotalHeure.getText() );
					totalSomme += nouvTotalHeureValeur;
				}
			}

			lblTotalSomme.setText ( String.valueOf ( totalSomme ) );
		}
		catch ( NumberFormatException ex )
		{
			lblSomme.setText ( "Erreur" );
		}
	}

	private void majTotalHeure ( )
	{
		//TODO:
	}

	private double coeffHeure ( String nomHeure )
	{
		Heure heure = this.ctrl.getHeure ( nomHeure );
		if ( heure == null )
		{
			return 0.0;
		}
		return heure.getCoefTd();
	}

	public void setModule ( ModuleIUT module )
	{
		//TODO:
	}

	public int getSaisie ( String nomHeure )
	{
		return Integer.parseInt( this.ensTxtNbHeure.get ( nomHeure ).getText() );
	}
}