import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.util.HashMap;
import java.util.Map;

//FIXME: peut surement avoir des problèmes d'arrondis (vu qu'on gère que des entiers)
public class PanelRepartitionSansGroupes extends AbstractPanelRepartition
{
	private PanelRepartitionTypesHeures pnlEquivalencesTypesHeures;
	private PanelVerticalSaisie         pnlHeuresTotales;

	public PanelRepartitionSansGroupes ( KeyListener listenerModule )
	{
		super ( listenerModule );
		this.initialiserPanels ( );
	}

	private void initialiserPanels ( )
	{
		GridBagConstraints gbc = new GridBagConstraints ( );
		gbc.insets = new Insets ( 5, 5, 5, 30 );
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridwidth = 1;

		// Ajout du panel des repartition des heures
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.pnlEquivalencesTypesHeures = new PanelRepartitionTypesHeures ( );
		this.add ( this.pnlEquivalencesTypesHeures, gbc );

		// Ajout du panel des heures totales
		gbc.gridx = 1;
		gbc.gridy = 0;
		this.pnlHeuresTotales = new PanelVerticalSaisie(super.listenerModule, "Σ", new boolean[]{false, false});
		this.add ( this.pnlHeuresTotales, gbc );
	}

	@Override
	public void ajouterTypeHeure ( String typeHeure )
	{
		this.pnlEquivalencesTypesHeures.ajouterTypeHeure ( typeHeure );
	}

	@Override
	public void supprimerTypeHeure ( String typeHeure )
	{
		this.pnlEquivalencesTypesHeures.supprimerTypeHeure ( typeHeure );
	}

	@Override
	public void majIHM ( )
	{
		this.pnlEquivalencesTypesHeures.majIHM ( );
		//this.pnlHeuresTotales.majIHM ( );
	}

//---------------------------------------------------------------------------------------//

	/**
	 * Classe interne pour le panel des heures du panel de repartition SANS GROUPES
	 */
	private class PanelRepartitionTypesHeures extends JPanel
	{
		private Map <String, PanelVerticalSaisie> ensPnlTypeHeure;

		public PanelRepartitionTypesHeures ( )
		{
			this.ensPnlTypeHeure = new HashMap <String, PanelVerticalSaisie>();
			
			this.setLayout( new BoxLayout ( this, BoxLayout.X_AXIS ) );
			this.setBorder( new EmptyBorder ( new Insets ( 0, 10, 0, 10 ) ) );
		}

		/**
		 * Permet d'ajouter un nouveau type d'heure visuellement et physiquement
		 * @param typeHeure
		 */
		public void ajouterTypeHeure ( String typeHeure )
		{
			// ajouter le panel
			PanelVerticalSaisie pnlTypeHeure = new PanelVerticalSaisie ( PanelRepartitionSansGroupes.this.listenerModule, typeHeure, new boolean[]{true, false} );
			this.ensPnlTypeHeure.put ( typeHeure, pnlTypeHeure );
			this.add ( pnlTypeHeure );
		}

		/**
		 * Permet de supprimer un type d'heure visuellement et physiquement
		 * @param typeHeure
		 */
		public void supprimerTypeHeure ( String typeHeure )
		{
			// supprimer le panel
			this.remove ( this.ensPnlTypeHeure.get ( typeHeure ) );
			this.ensPnlTypeHeure.remove ( typeHeure );
		}

		public void setValeursTypeHeure ( String typeHeure, double val )
		{
			this.ensPnlTypeHeure.get(typeHeure).setValeur ( 0, val );
		}

		public double getValeur ( String typeHeure )
		{
			return this.ensPnlTypeHeure.get ( typeHeure ).getValeurs ( )[0];
		}

		public void majIHM ( )
		{
			double sommeHeures     = 0.0;
			double sommeHeuresEQTD = 0.0;

			// maj des equivalents
			for ( Map.Entry<String, PanelVerticalSaisie> entry : this.ensPnlTypeHeure.entrySet ( ) )
			{
				double valeur = entry.getValue ( ).getValeurs ( )[0];
				sommeHeures += valeur;

				double operation =valeur * 1.5; //TODO: à remplacer par x ratio
				sommeHeuresEQTD += operation;

				entry.getValue().setValeur ( 1, operation );
			}

			// maj des heures totales
			PanelRepartitionSansGroupes.this.pnlHeuresTotales.setValeur ( 0, sommeHeures );
			PanelRepartitionSansGroupes.this.pnlHeuresTotales.setValeur ( 1, sommeHeuresEQTD );
		}
	}
}