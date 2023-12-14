package astre.vue.parametrage;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;

import astre.Controleur;

import astre.vue.intervenants.TableauIntervenant;
import astre.vue.outils.Tableau;

/** Classe PanelParametrage
  * @author : Maximilien LESTERLIN
  * @version : 1.0 - 13/12/23
  * @date : 13/12/2023
  */

public class PanelContrat extends JPanel
{
	private Controleur ctrl;
	private Tableau    tabContrat;

	
	public PanelContrat ( Controleur ctrl )
	{
		this.ctrl = ctrl;

		this.setLayout ( new GridLayout ( 1, 2 ) );
		
		String[] enTete = { "id","Nom", "Heure Service Contrat", "Heure Max Contrat", "Ratio TP" };

		JPanel pnlListeModule = new JPanel ( new BorderLayout ( ) );

		this.tabContrat = new Tableau ( enTete , this.ctrl.getTableauContrat ( ), true);
	
		this.tabContrat.getModeleTableau().setDecalage(1);
		this.tabContrat.setShowGrid ( false );
		this.tabContrat.setIntercellSpacing ( new Dimension ( 0, 0 ) );

		// Ajout du titre et rend la liste défilable 
		JScrollPane spTab = new JScrollPane ( this.tabContrat );
		spTab.setBorder                  ( new TitledBorder ( "Liste des contrats" )     );
		spTab.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );

		pnlListeModule.add ( spTab, BorderLayout.CENTER );

		this.add ( pnlListeModule, BorderLayout.CENTER );
	}


	public Tableau getTabContrat ( )
	{
		return this.tabContrat;
	}

	
}