import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Panel;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//FIXME: probablement enlever les classes internes (factoriser)
//FIXME: peut surement avoir des problèmes d'arrondis (vu qu'on gère que des entiers)
public class PanelRepartition_v1 extends JPanel
{
	public PanelRepartition_v1()
	{
		this.setLayout ( new GridBagLayout() );
		this.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets ( 5, 5, 5, 30 );
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridwidth = 1;

		// Ajout du panel des repartition des heures
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add ( new PanelRepartitionTypesHeures ( new String[]{ "CM", "TP", "TD", "Tut" } ), gbc );

		// Ajout du panel d'affichage des équivalents de types d'heures
		gbc.gridx = 1;
		gbc.gridy = 0;
		this.add ( new PanelEquivalentTypesHeures  ( new String[]{ "CM", "TP", "TD", "Tut" } ), gbc );
		
	}

	//TODO:
	/*public void initialiserAutres()
	{
		JPanel PanelHeuresPonctuelles;
		JPanel PanelTotalHeures;
		JPanel PanelTitres;
	}*/

//---------------------------------------------------------------------------------------//

	/**
	 * Classe interne pour le panel des équivalences de types d'heures
	 */
	private class PanelEquivalentTypesHeures extends JPanel
	{
		private List<String> ensTypeHeure;
		private Map <String, JPanel>       ensPnlTypeHeure;
		private Map <String, JTextField[]> ensTxtEquivalentTypeHeure;

		public PanelEquivalentTypesHeures ( String[] ensTypeHeure )
		{
			this.ensTypeHeure = new ArrayList<String> ();
			this.ensPnlTypeHeure = new HashMap  <String, JPanel>();
			this.ensTxtEquivalentTypeHeure = new HashMap  <String, JTextField[]>();

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
			JPanel pnlTypeHeure = this.panelNouveauTypeHeure ( typeHeure );
			this.ensPnlTypeHeure.put ( typeHeure, pnlTypeHeure );
			this.add ( pnlTypeHeure );

			// ajouter les types d'heures physiquement
			this.ensTypeHeure.add ( typeHeure );
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
			this.ensTxtEquivalentTypeHeure.remove ( typeHeure );

			// supprimer les types d'heures physiquement
			this.ensTypeHeure.remove ( typeHeure );
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
			panelTypeHeure.setLayout ( new BoxLayout ( panelTypeHeure, BoxLayout.Y_AXIS ) );
			panelTypeHeure.setBorder( new EmptyBorder( new Insets ( 5,5,5,5 ) ) );

			// Ajout du nom du type d'heure
			panelTypeHeure.add ( new JLabel ( typeHeure ) );

			// Création des 3 champs de saisie
			JTextField[] txtEquivalentTypeHeure = new JTextField[ 3 ];
			txtEquivalentTypeHeure[ 0 ] = FrameModule.creerTextFieldEntier ( false );
			txtEquivalentTypeHeure[ 1 ] = FrameModule.creerTextFieldEntier ( false );
			txtEquivalentTypeHeure[ 2 ] = FrameModule.creerTextFieldEntier ( false );
			this.ensTxtEquivalentTypeHeure.put ( typeHeure, txtEquivalentTypeHeure );

			// Ajout des 3 champs de saisie
			panelTypeHeure.add ( txtEquivalentTypeHeure[ 0 ] );
			panelTypeHeure.add(Box.createRigidArea(new Dimension(0,5)));
			panelTypeHeure.add ( txtEquivalentTypeHeure[ 1 ] );
			panelTypeHeure.add(Box.createRigidArea(new Dimension(0,5)));
			panelTypeHeure.add ( txtEquivalentTypeHeure[ 2 ] );

			return panelTypeHeure;
		}

		public void setValeursTypeHeure ( String typeHeure, int val1, int val2, int val3 )
		{
			this.ensTxtEquivalentTypeHeure.get ( typeHeure )[ 0 ].setText ( Integer.toString ( val1 ) );
			this.ensTxtEquivalentTypeHeure.get ( typeHeure )[ 1 ].setText ( Integer.toString ( val2 ) );
			this.ensTxtEquivalentTypeHeure.get ( typeHeure )[ 2 ].setText ( Integer.toString ( val3 ) );
		}

		//TODO: à faire
		public void majIHM ( Object[] ensValeurs )
		{
		}
	}

//---------------------------------------------------------------------------------------//

	/**
	 * Classe interne pour le panel des heures ponctuelles
	 */
	class PanelHeuresPonctuelles extends JPanel
	{
		private List<String> ensTypeHeure;

	}

//---------------------------------------------------------------------------------------//

	/**
	 * Classe interne pour le panel des heures du panel de repartition
	 */
	private class PanelRepartitionTypesHeures extends JPanel
	{
		private List<String> ensTypeHeure;
		private Map <String, JPanel>     ensPnlTypeHeure;
		private Map <String, JTextField> ensTxtNbSemaine;
		private Map <String, JTextField> ensTxtNbHeureSemaine;

		public PanelRepartitionTypesHeures ( String[] ensTypeHeure )
		{
			this.ensTypeHeure         = new ArrayList<String> ();
			this.ensTxtNbSemaine      = new HashMap  <String, JTextField>();
			this.ensTxtNbHeureSemaine = new HashMap  <String, JTextField>();
			this.ensPnlTypeHeure      = new HashMap  <String, JPanel>();
			
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

			// ajouter les types d'heures physiquement
			this.ensTypeHeure.add ( typeHeure );
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

			// supprimer les types d'heures physiquement
			this.ensTypeHeure.remove ( typeHeure );
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

		/**
		 * Permet de récupérer le nombre de semaine d'un type d'heure
		 * @param typeHeure
		 * @return int
		 */
		public int getNbSemaine ( String typeHeure )
		{
			return Integer.parseInt ( this.ensTxtNbSemaine.get ( typeHeure ).getText() );
		}

		/**
		 * Permet de récupérer le nombre d'heure par semaine d'un type d'heure
		 * @param typeHeure
		 * @return int
		 */
		public int getNbHeureSemaine ( String typeHeure )
		{
			return Integer.parseInt ( this.ensTxtNbHeureSemaine.get ( typeHeure ).getText() );
		}
		
		/**
		 */
		private void setNbSemaine ( String typeHeure, int nbSemaine )
		{
			this.ensTxtNbSemaine.get ( typeHeure ).setText ( Integer.toString ( nbSemaine ) );
		}

		/**
		 */
		private void setNbHeureSemaine ( String typeHeure, int nbHeureSemaine )
		{
			this.ensTxtNbHeureSemaine.get ( typeHeure ).setText ( Integer.toString ( nbHeureSemaine ) );
		}

		//TODO: à faire
		public void majIHM ( Object[] ensValeurs )
		{

		}
	}
}
