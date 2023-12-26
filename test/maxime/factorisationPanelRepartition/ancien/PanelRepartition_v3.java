import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//FIXME: probablement enlever les classes internes (factoriser)
//FIXME: peut surement avoir des problèmes d'arrondis (vu qu'on gère que des entiers)
public class PanelRepartition_v3 extends JPanel
{
	private KeyListener listenerModule;
	private Map <String, JTextField[]> ensTxtSemainesTypeHeure;
	private Map <String, JTextField[]> ensTxtEquivalentTypeHeure;

	private JTextField   txtNbHeurePonctuelle;
	private JTextField[] ensTxtEquivalentHeurePonctuelle;

	private JTextField[] txtSommeHeureTotale;

	//version que afficher groupes
	public PanelRepartition_v3_avecGroupes ( KeyListener listenerModule )
	{
		this.listenerModule = listenerModule;
		this.setLayout ( new GridBagLayout ( ) );
		this.setBorder ( BorderFactory.createLineBorder ( Color.GRAY, 1 ) );

		this.initialiser ( );
	}

	private void initialiser ( )
	{
		GridBagConstraints gbc = new GridBagConstraints ( );
		gbc.insets = new Insets ( 5, 5, 5, 30 );
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridwidth = 1;

		// Ajout du panel des repartition des heures
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add ( new PanelRepartitionTypesHeuresAvecGroupes ( new String[]{ "CM", "TP", "TD", "Tut" } ), gbc );

		// Ajout du panel d'affichage des équivalents de types d'heures
		gbc.gridx = 1;
		gbc.gridy = 0;
		this.add ( new PanelEquivalentTypesHeures  ( new String[]{ "CM", "TP", "TD", "Tut" } ), gbc );

		// Ajout du panel des heures ponctuelles
		gbc.gridx = 2;
		gbc.gridy = 0;
		this.add ( new PanelVerticalSaisie(this.listenerModule, "hp", new boolean[]{true, false, false}), gbc );

		// Ajout du panel des heures totales
		gbc.gridx = 3;
		gbc.gridy = 0;
		this.add ( new PanelVerticalSaisie(this.listenerModule, "Z", new boolean[]{false, false, false}), gbc );
	}

	private JPanel initialiserSaisieSemaine ( )
	{
		JPanel pnl = new JPanel ( );

		this.ensPnlTypeHeure = new HashMap <String, JPanel>();
		this.ensTxtNbSemaine = new HashMap <String, JTextField>();
		this.ensTxtNbHeureSemaine = new HashMap <String, JTextField>();
			
		pnl.setLayout( new BoxLayout ( this, BoxLayout.X_AXIS ) );
		pnl.setBorder( new EmptyBorder ( new Insets ( 0, 10, 0, 10 ) ) );
	}

	public JPanel ajouterSaisieSemaine ( String typeHeure )
	{
		JPanel panelTypeHeure;
		panelTypeHeure = new JPanel ( );
		panelTypeHeure.setLayout ( new GridBagLayout ( ) );
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets ( 2, 5, 2, 5 );

		// Ajout du nom du type d'heure
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		panelTypeHeure.add ( new JLabel ( typeHeure ), gbc );

		// Ajout des titres des colonnes
		gbc.gridy = 1;
		gbc.gridwidth = 1;

		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 0;
		panelTypeHeure.add ( new JLabel ( "nb Sem" ), gbc );
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 1;
		panelTypeHeure.add ( new JLabel ( "nb h/Sem" ), gbc );

		// Ajout des champs de saisie
		gbc.gridy = 2;
		gbc.gridwidth = 1;

		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 0;
		JTextField txtNbSemaine = new JTextField ( 3 );
		this.ensTxtNbSemaine.put ( typeHeure, txtNbSemaine );
		panelTypeHeure.add ( txtNbSemaine, gbc );

		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 1;
		JTextField txtNbHeureSemaine = new JTextField ( 3 );
		this.ensTxtNbHeureSemaine.put ( typeHeure, txtNbHeureSemaine );
		panelTypeHeure.add ( txtNbHeureSemaine, gbc );

		return panelTypeHeure;
	}

	private JPanel initialiserEquivalent ( )
	{
		this.ensPnlTypeHeure = new HashMap <String, PanelVerticalSaisie>();

		this.setLayout( new BoxLayout ( this, BoxLayout.X_AXIS ) );
		this.setBorder( new EmptyBorder ( new Insets ( 60, 2, 2, 2 ) ) );
	}

	public JPanel ajouterEquivalent ( String typeHeure )
	{

	}

	private JPanel initialiserHeurePonctuelle ( )
	{

	}

	private JPanel initialiserHeureTotale ( )
	{

	}

//---------------------------------------------------------------------------------------//

	/**
	 * Classe interne pour le panel des équivalences de types d'heures
	 */
	private class PanelEquivalencesTypesHeures extends JPanel
	{
		private Map <String, PanelVerticalSaisie> ensPnlTypeHeure;

		public PanelEquivalencesTypesHeures ( String[] ensTypeHeure )
		{
			this.ensPnlTypeHeure = new HashMap <String, PanelVerticalSaisie>();

			this.setLayout( new BoxLayout ( this, BoxLayout.X_AXIS ) );
			this.setBorder( new EmptyBorder ( new Insets ( 60, 2, 2, 2 ) ) );

			for ( String typeHeure : ensTypeHeure )
			{
				this.ajouterTypeHeure ( typeHeure );
			}
		}

		/**
		 * Permet d'ajouter un nouveau type d'heure visuellement et physiquement
		 * @param typeHeure
		 */
		public void ajouterTypeHeure ( String typeHeure )
		{
			// ajouter le panel visuelement
			PanelVerticalSaisie pnlTypeHeure = new PanelVerticalSaisie ( PanelRepartition_v2.this.listenerModule, typeHeure, new boolean[]{false, false, false} );
			this.ensPnlTypeHeure.put ( typeHeure, pnlTypeHeure );
			this.add ( pnlTypeHeure );
		}

		/**
		 * Permet de supprimer un type d'heure visuellement et physiquement
		 * @param typeHeure
		 */
		public void supprimerTypeHeure ( String typeHeure )
		{
			// supprimer le panel visuelement
			this.remove ( this.ensPnlTypeHeure.get ( typeHeure ) );
			this.ensPnlTypeHeure.remove ( typeHeure );
		}

		public void setValeursTypeHeure ( String typeHeure, int val1, int val2, int val3 )
		{
			this.ensPnlTypeHeure.get ( typeHeure ).setValeur ( 0, Integer.toString ( val1 ) );
			this.ensPnlTypeHeure.get ( typeHeure ).setValeur ( 1, Integer.toString ( val2 ) );
			this.ensPnlTypeHeure.get ( typeHeure ).setValeur ( 2, Integer.toString ( val3 ) );
		}

		//TODO: à faire
		public void majIHM ( Object[] ensValeurs )
		{
		}
	}

//---------------------------------------------------------------------------------------//

	/**
	 * Classe interne pour le panel des heures du panel de repartition AVEC GROUPES
	 */
	private class PanelSaisiesSemaines extends JPanel
	{
		private Map <String, JPanel> ensPnlTypeHeure;
		private Map <String, JTextField> ensTxtNbSemaine; //FIXME: à enlever
		private Map <String, JTextField> ensTxtNbHeureSemaine; //FIXME: à enlever

		public PanelRepartitionTypesHeuresAvecGroupes ( String[] ensTypeHeure )
		{
			this.ensPnlTypeHeure = new HashMap <String, JPanel>();
			this.ensTxtNbSemaine = new HashMap <String, JTextField>();
			this.ensTxtNbHeureSemaine = new HashMap <String, JTextField>();
			
			this.setLayout( new BoxLayout ( this, BoxLayout.X_AXIS ) );
			this.setBorder( new EmptyBorder ( new Insets ( 0, 10, 0, 10 ) ) );

			for ( String typeHeure : ensTypeHeure )
			{
				this.ajouterTypeHeure ( typeHeure );
			}
		}

		/**
		 * Permet d'ajouter un nouveau type d'heure visuellement et physiquement
		 * @param typeHeure
		 */
		public void ajouterTypeHeure ( String typeHeure )
		{
			// ajouter le panel visuelement
			JPanel pnlTypeHeure = this.panelNouveauTypeHeure ( typeHeure );
			this.ensPnlTypeHeure.put ( typeHeure, pnlTypeHeure );
			this.add ( pnlTypeHeure );
		}

		/**
		 * Permet de supprimer un type d'heure visuellement et physiquement
		 * @param typeHeure
		 */
		public void supprimerTypeHeure ( String typeHeure )
		{
			// supprimer le panel visuelement
			this.remove ( this.ensPnlTypeHeure.get ( typeHeure ) );
			this.ensPnlTypeHeure.remove ( typeHeure );

			// supprimer les champs de saisie physiquement
			this.ensTxtNbSemaine.remove ( typeHeure );
			this.ensTxtNbHeureSemaine.remove ( typeHeure );
		}

		/**
		 * Constructeur simplifié du panel d'un type d'heure
		 * @param typeHeure
		 * @return JPanel
		 */
		private JPanel panelNouveauTypeHeure ( String typeHeure )
		{
			JPanel panelTypeHeure;
			panelTypeHeure = new JPanel ( );
			panelTypeHeure.setLayout ( new GridBagLayout ( ) );
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets ( 2, 5, 2, 5 );

			// Ajout du nom du type d'heure
			gbc.anchor = GridBagConstraints.CENTER;
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.gridwidth = 2;
			panelTypeHeure.add ( new JLabel ( typeHeure ), gbc );

			// Ajout des titres des colonnes
			gbc.gridy = 1;
			gbc.gridwidth = 1;

			gbc.anchor = GridBagConstraints.EAST;
			gbc.gridx = 0;
			panelTypeHeure.add ( new JLabel ( "nb Sem" ), gbc );
			gbc.anchor = GridBagConstraints.WEST;
			gbc.gridx = 1;
			panelTypeHeure.add ( new JLabel ( "nb h/Sem" ), gbc );

			// Ajout des champs de saisie
			gbc.gridy = 2;
			gbc.gridwidth = 1;

			gbc.anchor = GridBagConstraints.EAST;
			gbc.gridx = 0;
			JTextField txtNbSemaine = new JTextField ( 3 );
			this.ensTxtNbSemaine.put ( typeHeure, txtNbSemaine );
			panelTypeHeure.add ( txtNbSemaine, gbc );

			gbc.anchor = GridBagConstraints.WEST;
			gbc.gridx = 1;
			JTextField txtNbHeureSemaine = new JTextField ( 3 );
			this.ensTxtNbHeureSemaine.put ( typeHeure, txtNbHeureSemaine );
			panelTypeHeure.add ( txtNbHeureSemaine, gbc );

			return panelTypeHeure;
		}

		//TODO: à faire
		public void majIHM ( Object[] ensValeurs )
		{

		}
	}
}