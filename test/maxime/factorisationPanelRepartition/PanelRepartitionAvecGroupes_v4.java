import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.KeyListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.util.HashMap;
import java.util.Map;

public class PanelRepartitionAvecGroupes_v4 extends AbstractPanelRepartition
{
	private PanelRepartitionTypesHeures  pnlRepartitionTypesHeures;
	private PanelEquivalencesTypesHeures pnlEquivalencesTypesHeures;
	private PanelVerticalSaisie          pnlHeuresPonctuelles;
	private PanelVerticalSaisie          pnlHeuresTotales;

	public PanelRepartitionAvecGroupes_v4 ( KeyListener listenerModule )
	{
		super ( listenerModule );
		this.initialiserPanels ( );
	}

	private void initialiserPanels ( )
	{
		GridBagConstraints gbc = new GridBagConstraints ( );
		gbc.insets = new Insets ( 5, 15, 5, 0 );

		// Ajout du panel des repartition des heures
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.pnlRepartitionTypesHeures = new PanelRepartitionTypesHeures ( );
		this.add ( this.pnlRepartitionTypesHeures, gbc );

		// Ajout du panel des titres des lignes d'équivalences de types d'heures
		gbc.insets = new Insets ( 25, 10, 5, 0 );
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.gridheight = 3;
		gbc.gridx = 1;
		gbc.gridy = 0;
		this.add ( new PanelLabelsEquivalences ( ), gbc );

		gbc.insets = new Insets ( 25, 5, 5, 20 );
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridheight = 3;

		// Ajout du panel d'affichage des équivalents de types d'heures
		gbc.gridx = 2;
		gbc.gridy = 0;
		this.pnlEquivalencesTypesHeures = new PanelEquivalencesTypesHeures ( );
		this.add ( this.pnlEquivalencesTypesHeures, gbc );

		// Ajout du panel des heures ponctuelles
		gbc.gridx = 3;
		gbc.gridy = 0;
		this.pnlHeuresPonctuelles = new PanelVerticalSaisie ( super.listenerModule, "hp", new boolean[]{true, false, false} );
		this.add ( this.pnlHeuresPonctuelles, gbc );

		// Ajout du panel des heures totales
		gbc.gridx = 4;
		gbc.gridy = 0;
		this.pnlHeuresTotales = new PanelVerticalSaisie ( super.listenerModule, "Σ", new boolean[]{false, false, false} );
		this.add ( this.pnlHeuresTotales, gbc );
	}

	@Override
	public void ajouterTypeHeure ( String typeHeure )
	{
		this.pnlRepartitionTypesHeures.ajouterTypeHeure ( typeHeure );
		this.pnlEquivalencesTypesHeures.ajouterTypeHeure ( typeHeure );
	}

	@Override
	public void supprimerTypeHeure ( String typeHeure )
	{
		this.pnlRepartitionTypesHeures.supprimerTypeHeure ( typeHeure );
		this.pnlEquivalencesTypesHeures.supprimerTypeHeure ( typeHeure );
	}

	@Override
	public void majIHM ( )
	{
		// maj ihm via la classe de pnlEquivalencesTypesHeures
		this.pnlEquivalencesTypesHeures.majIHM ( );

		// maj ihm locale de pnlHeuresPonctuelles
		double valeur = this.pnlHeuresPonctuelles.getValeurs()[0];
		this.pnlHeuresPonctuelles.setValeur ( 1, valeur*5 );
		this.pnlHeuresPonctuelles.setValeur ( 2, 0.0 ); //TODO: remplacer le x5 par le nb de semaines du ctrl et calculer le 0.0

		// maj ihm locale de pnlHeuresTotales et préparation du calcul des sommes
		double[] sommes = new double[3];
		int i = 0;
		for ( String typeHeure : this.pnlEquivalencesTypesHeures.ensPnlTypeHeure.keySet ( ) ) //TODO: ameliorer la façon dont je récupère pcq c chaud là mdr
		{
			double[] valeurs = this.pnlEquivalencesTypesHeures.getValeurTypeHeure ( typeHeure );
			sommes[0] += valeurs[0];
			sommes[1] += valeurs[1];
			sommes[2] += valeurs[2];
		}

		// maj ihm locale de pnlHeuresTotales
		double[] valeursHP = this.pnlHeuresPonctuelles.getValeurs ( );
		this.pnlHeuresTotales.setValeur ( 0, sommes[0] + valeursHP[0] );
		this.pnlHeuresTotales.setValeur ( 1, sommes[1] + valeursHP[1] );
		this.pnlHeuresTotales.setValeur ( 2, sommes[2] + valeursHP[2] );
	}

//---------------------------------------------------------------------------------------//
//---------------------------------------------------------------------------------------//

	/**
	 * Classe interne pour le panel des heures du panel de repartition AVEC GROUPES
	 */
	private class PanelRepartitionTypesHeures extends JPanel
	{
		private Map <String, PanelSaisieSemaines> ensPnlSaisieSemaines;

		public PanelRepartitionTypesHeures ( )
		{
			this.ensPnlSaisieSemaines = new HashMap <String, PanelSaisieSemaines>();
			
			this.setLayout( new BoxLayout ( this, BoxLayout.X_AXIS ) );
		}

		/**
		 * Permet d'ajouter un nouveau type d'heure visuellement et physiquement
		 * @param typeHeure
		 */
		public void ajouterTypeHeure ( String typeHeure )
		{
			this.ensPnlSaisieSemaines.put ( typeHeure, new PanelSaisieSemaines ( PanelRepartitionAvecGroupes_v4.this.listenerModule, typeHeure ) );
			this.add ( this.ensPnlSaisieSemaines.get ( typeHeure ) );
		}

		/**
		 * Permet de supprimer un type d'heure visuellement et physiquement
		 * @param typeHeure
		 */
		public void supprimerTypeHeure ( String typeHeure )
		{
			// supprimer le panel visuelement
			this.remove ( this.ensPnlSaisieSemaines.get ( typeHeure ) );

			// supprimer le panel physiquement
			this.ensPnlSaisieSemaines.remove ( typeHeure );
		}

		public double[] getValeurTypeHeure ( String typeHeure )
		{
			return this.ensPnlSaisieSemaines.get ( typeHeure ).getValeurs ( );
		}
	}
	
//---------------------------------------------------------------------------------------//
//---------------------------------------------------------------------------------------//

	/**
	 * Classe interne pour le panel des labels des équivalences de types d'heures
	 */
	private class PanelLabelsEquivalences extends JPanel
	{
		public PanelLabelsEquivalences ( )
		{
			this.setLayout ( new BoxLayout ( this, BoxLayout.Y_AXIS ) );
			this.setBorder( new EmptyBorder( new Insets ( 5,5,5,5 ) ) );

			this.add ( Box.createRigidArea ( new Dimension ( 55, 20      ) ) ); //dimension d'un JTextField
			this.add ( Box.createRigidArea ( new Dimension ( 0,5         ) ) ); //dimension d'un espace vertical
			this.add ( Box.createRigidArea ( new Dimension ( 55, 20      ) ) );
			this.add ( new JLabel          ( "Total promo (eqtd)" , JLabel.RIGHT   ) );
			this.add ( Box.createRigidArea ( new Dimension ( 0,5         ) ) );
			this.add ( new JLabel          ( "Total affecté (eqtd)", JLabel.RIGHT  ) );
		}
	}

//---------------------------------------------------------------------------------------//
//---------------------------------------------------------------------------------------//

	/**
	 * Classe interne pour le panel des équivalences de types d'heures
	 */
	private class PanelEquivalencesTypesHeures extends JPanel
	{
		private Map <String, PanelVerticalSaisie> ensPnlTypeHeure;

		public PanelEquivalencesTypesHeures ( )
		{
			this.ensPnlTypeHeure = new HashMap <String, PanelVerticalSaisie>();

			this.setLayout( new BoxLayout ( this, BoxLayout.X_AXIS ) );
			//this.setBorder( new EmptyBorder ( new Insets ( 80, 2, 2, 2 ) ) );
		}

		/**
		 * Permet d'ajouter un nouveau type d'heure visuellement et physiquement
		 * @param typeHeure
		 */
		public void ajouterTypeHeure ( String typeHeure )
		{
			// ajouter le panel visuelement
			PanelVerticalSaisie pnlTypeHeure = new PanelVerticalSaisie ( PanelRepartitionAvecGroupes_v4.this.listenerModule, typeHeure, new boolean[]{false, false, false} );
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

		public void setValeursTypeHeure ( String typeHeure, double val1, double val2, double val3 )
		{
			this.ensPnlTypeHeure.get ( typeHeure ).setValeur ( 0, val1 );
			this.ensPnlTypeHeure.get ( typeHeure ).setValeur ( 1, val2 );
			this.ensPnlTypeHeure.get ( typeHeure ).setValeur ( 2, val3 );
		}

		public double[] getValeurTypeHeure ( String typeHeure )
		{
			System.out.println( this.ensPnlTypeHeure.get ( typeHeure ).getValeurs ( )[0] + " : " + this.ensPnlTypeHeure.get ( typeHeure ).getValeurs ( )[1] + " : " + this.ensPnlTypeHeure.get ( typeHeure ).getValeurs ( )[2]);
			return this.ensPnlTypeHeure.get ( typeHeure ).getValeurs ( );
		}

		public void majIHM ( )
		{
			System.out.println("majIHM");
			for ( String typeHeure : this.ensPnlTypeHeure.keySet ( ) )
			{
				double[] valeurs = PanelRepartitionAvecGroupes_v4.this.pnlRepartitionTypesHeures.getValeurTypeHeure ( typeHeure );
				double operation = valeurs[0] * valeurs[1];
				this.setValeursTypeHeure ( typeHeure, operation, operation*5, 0.0 ); //TODO: remplacer le x5 par le nb de semaines du ctrl et calculer le 0.0
			}
		}
	}
}