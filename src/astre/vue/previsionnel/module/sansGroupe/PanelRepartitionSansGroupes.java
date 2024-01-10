package astre.vue.previsionnel.module.sansGroupe;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import astre.Controleur;
import astre.vue.previsionnel.module.AbstractPanelRepartition;
import astre.vue.previsionnel.module.FrameModule;
import astre.vue.previsionnel.module.PanelVerticalSaisie;

import java.util.HashMap;
import java.util.Map;

/** Classe PanelRepartitionSansGroupes
 * @author : Maxime Lemoine
 * @version : 1.0 - 20/12/2023
 * @date : 27/12/2023
 */
public class PanelRepartitionSansGroupes extends AbstractPanelRepartition
{
	private PanelRepartitionTypesHeures pnlRepartitionTypesHeures;
	private PanelVerticalSaisie         pnlHeuresTotales;
	
	private String[]                    ensIntituleTypeHeure;

	public PanelRepartitionSansGroupes ( Controleur ctrl, FrameModule listenerModule, String[] ensIntituleTypeHeure )
	{
		super ( ctrl, listenerModule );
		this.ensIntituleTypeHeure = ensIntituleTypeHeure;
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
		this.pnlRepartitionTypesHeures = new PanelRepartitionTypesHeures ( );
		this.add ( this.pnlRepartitionTypesHeures, gbc );

		// Ajout du panel des heures totales
		gbc.gridx = 1;
		gbc.gridy = 0;
		this.pnlHeuresTotales = new PanelVerticalSaisie(super.listenerModule, "Σ", new boolean[]{false, false});
		this.add ( this.pnlHeuresTotales, gbc );
	}

	@Override
	public void ajouterTypeHeure ( String typeHeure )
	{
		this.pnlRepartitionTypesHeures.ajouterTypeHeure ( typeHeure );
	}

	@Override
	public void supprimerTypeHeure ( String typeHeure )
	{
		this.pnlRepartitionTypesHeures.supprimerTypeHeure ( typeHeure );
	}

	@Override
	public void majIHM ( )
	{
		this.pnlRepartitionTypesHeures.majIHM ( );
	}

	@Override
	public void setValeurs ( Object[][] donnees )
	{
		for ( int i = 0; i < donnees.length; i++ )
			this.pnlRepartitionTypesHeures.setValeursTypeHeure ( this.ensIntituleTypeHeure[i], ( int ) donnees[i][1] );
	}

	@Override
	public Object[][] getDonnees ( )
	{
		Object [][] donnees = new Object[this.ensIntituleTypeHeure.length][2];

		int i = 0;
		for ( String typeHeure : this.ensIntituleTypeHeure )
		{
			donnees[i][0] = 1;
			donnees[i][1] = this.pnlRepartitionTypesHeures.getValeur ( typeHeure );
			i++;
		}
		
		return donnees;
	}

//---------------------------------------------------------------------------------------//
//---------------------------------------------------------------------------------------//

	/**
	 * Classe interne pour le panel des heures du panel de repartition SANS GROUPES
	 */
	private class PanelRepartitionTypesHeures extends JPanel
	{
		private Map <String, PanelVerticalSaisie> ensPnlTypeHeure;

		public PanelRepartitionTypesHeures ( )
		{
			this.ensPnlTypeHeure = new HashMap <>();
			
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

		public void setValeursTypeHeure ( String typeHeure, int val )
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
			for ( String typeHeure : this.ensPnlTypeHeure.keySet ( ) )
			{
				double valeur = this.ensPnlTypeHeure.get ( typeHeure ).getValeurs ( )[0];
				sommeHeures += valeur;

				double operation = PanelRepartitionSansGroupes.super.frameModule.getSommesEQTD ( ).get ( typeHeure );
				sommeHeuresEQTD += operation;

				this.ensPnlTypeHeure.get ( typeHeure ).setValeur ( 1, operation );
			}

			// maj des heures totales
			PanelRepartitionSansGroupes.this.pnlHeuresTotales.setValeur ( 0, sommeHeures     );
			PanelRepartitionSansGroupes.this.pnlHeuresTotales.setValeur ( 1, sommeHeuresEQTD );
		}
	}
}