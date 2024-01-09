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

/** Classe PanelModuleLabel
  * @author : Clémentin Ly, Maximilien Lesterlin, Maxime Lemoine
  * @version : 3.0 - 22/12/2023
  * @date : 11/12/2023
  */
public class PanelPNLocal extends JPanel
{
	private Controleur ctrl;

	private String[] ensIntitule; //TODO: remplacer par une List<String> voir peut être à le supprimer ?
	private Map<String, JTextField> ensTxtNbHeure;
	private Map<String, JTextField> ensTxtTotalPromo;

	private JTextField txtSommePromo;
	private JTextField txtSommeEQTDPromo;

	public PanelPNLocal ( Controleur ctrl, String nomTypeModule, String[] ensIntitule )
	{
		/* -------------------------- */
		/*    Propriétés générales    */
		/* -------------------------- */

		this.ctrl = ctrl;
		this.setLayout ( new GridBagLayout ( ) );
		this.setBorder ( BorderFactory.createLineBorder ( Color.GRAY, 1 ) );

		/* -------------------------- */
		/*  Création des composants   */
		/* -------------------------- */

		this.initialiserComposantsModule ( nomTypeModule, ensIntitule );

		/* -------------------------- */
		/*    Ajout des composants    */
		/* -------------------------- */

		GridBagConstraints gbc = new GridBagConstraints ( );
		gbc.insets = new Insets ( 5, 5, 5, 5 );
		this.placementComposantsModule ( gbc );

		/* -------------------------- */
		/* Activation des composants  */
		/* -------------------------- */

		this.activationComposantsModule ( );
	}

	/*---------------------------------------*/
	/*              METHODES IHM             */
	/*---------------------------------------*/

	/**
	 * Créer tous les composants
	 */
	private void initialiserComposantsModule ( String nomTypeModule, String[] ensIntitule )
	{
		this.ensTxtNbHeure    = new HashMap<> ( );
		this.ensTxtTotalPromo = new HashMap<> ( );
		this.ensIntitule = ensIntitule;

		for ( String intitule : this.ensIntitule )
		{
			this.ensTxtNbHeure   .put ( intitule, creerTextField ( true  ) );
			this.ensTxtTotalPromo.put ( intitule, creerTextField ( false ) );
		}

		this.txtSommePromo     = creerTextField ( false );
		this.txtSommeEQTDPromo = creerTextField ( false );
	}

	/**
	 * Methode interne permettant de créer les TextField avec des paramètres par défaut
	 */
	private static JTextField creerTextField ( boolean editable )
	{
		JTextField txtTmp = new JTextField ( );
		txtTmp.setPreferredSize ( new Dimension ( 55, 20 ) ); //de base : (40,15)

		if ( editable )
		{
			FiltreTextFieldEntier.appliquer ( txtTmp );
		}
		else
		{
			txtTmp.setEditable   ( false            );
			txtTmp.setBackground ( Color.LIGHT_GRAY );
			//txtTmp.setForeground ( Color.BLACK      );
		}
		
		return txtTmp;
	}

	/**
	 * Ajouter tous les composants créés au panel
	 */
	private void placementComposantsModule ( GridBagConstraints gbc )
	{
		//placement du commentaire
		gbc.gridx = 0;
		gbc.gridy = 2;
		this.add ( new JLabel ( "Total (eqtd) promo" ) );

		//placement des intitulés et leurs informations
		for ( int cptColonnes = 0; cptColonnes < this.ensIntitule.length; cptColonnes++ )
		{
			gbc.gridx = cptColonnes+1;

			String intitule = this.ensIntitule [ cptColonnes ];
			
			gbc.gridy = 0;
			this.add ( new JLabel ( "h " + intitule ), gbc );
			gbc.gridy = 1;
			this.add ( this.ensTxtNbHeure   .get ( intitule ), gbc );
			gbc.gridy = 2;
			this.add ( this.ensTxtTotalPromo.get ( intitule ), gbc );
		}

		//placement de la colonne des sommes
		gbc.gridx = this.ensIntitule.length + 1;
		gbc.gridy = 0;
		this.add ( new JLabel ( "Σ" ), gbc );
		gbc.gridy = 1;
		this.add ( this.txtSommePromo, gbc );
		gbc.gridy = 2;
		this.add ( this.txtSommeEQTDPromo, gbc );
	}


	/*---------------------------------------*/
	/*           METHODES D'ECOUTE           */
	/*---------------------------------------*/

	/**
	 * Activer la détection de la saisie sur les zones de saisie
	 */
	private void activationComposantsModule ( )
	{
		for ( String intitule : this.ensIntitule )
		{
			this.ensTxtNbHeure.get ( intitule ).addKeyListener ( new AjoutKeyListenerSomme ( ) );
		}
	}

	/**
	 * Classe interne permettant la détection à chaque caractères écrits
	 */
	private class AjoutKeyListenerSomme implements KeyListener
	{
		@Override public void keyTyped   ( KeyEvent e ) { /* */         }
		@Override public void keyPressed ( KeyEvent e ) { /* */         }
		@Override public void keyReleased( KeyEvent e ) { majSomme ( ); }
	}
	

	/*---------------------------------------*/
	/*              METHODES MAJ             */
	/*---------------------------------------*/

	/**
	 * Méthode qui récupère toutes les informations nécessaires à la complétion du tableau des sommes et équivant TD
	 */
	//TODO: voir à déplacer cette méthode dans le métier (inutilement complexe)
	private void majSomme ( )
	{
		double somme     = 0;
		double sommeEQTD = 0;
		
		for ( String intitule : ensIntitule )
		{
			int valeur = this.getSaisie ( intitule );
			
			// Total des heures sans l'équivalent TD
			somme += valeur;
			
			// Calcul de l'équivalent TD
			double valeurEQTD = valeur * this.coeffHeure ( intitule );
			this.ensTxtTotalPromo.get ( intitule ).setText ( "" + valeurEQTD );

			// Total de l'équivalent TD
			sommeEQTD += valeurEQTD;
		}

		this.txtSommePromo    .setText ( "" + somme     );
		this.txtSommeEQTDPromo.setText ( "" + sommeEQTD );
	}

	//TODO: ne prends pas en compte les ajouts ou modifications du nombre de types d'heure différentes
	public void majIhm ( Object[][] donnees )
	{
		// mettre à jour les zones de texte
		for ( Object[] lig : donnees )
		{
			if ( this.ensTxtNbHeure.containsKey ( lig [0] ) )
			{
				JTextField txtTmp = this.ensTxtNbHeure.get ( lig [0] );
				txtTmp.setText ( "" + lig[1] );
			}
		}
		
		// mettre à jour les sommes
		this.majSomme ( );
	}

	/**
	 * Met à jour la base de donnée
	 */
	public Object[][] getDonnees ( ) // ancien nom : setModule
	{
		Object[][] heurePn = new Object[ this.ensIntitule.length ][2];

		for ( int cpt = 0; cpt < this.ensIntitule.length; cpt++ )
		{
			String intitule = this.ensIntitule [ cpt ];
			heurePn [ cpt ][0] = intitule;
			heurePn [ cpt ][1] = this.getSaisie ( intitule );
		}

		return heurePn;
	}


	/*---------------------------------------*/
	/*        METHODES ACCES DONNEES         */
	/*---------------------------------------*/

	/**
	 * Récupérer des informations utilisables sur les coefficients des heures
	 */
	private double coeffHeure ( String nomHeure )
	{
		Heure heure = this.ctrl.getHeure ( nomHeure );

		return heure == null ? 0.0 : heure.getCoefTd ( );
	}

	/**
	 * Récupérer les valeurs entières sur les zones de texte
	 */
	public int getSaisie ( String nomHeure )
	{
		try
		{
			return Integer.parseInt ( this.ensTxtNbHeure.get ( nomHeure ).getText ( ) );
		}
		catch ( Exception e )
		{
			return 0;
		}
	}
}