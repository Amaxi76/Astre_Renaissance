package astre.vue.parametrage;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;

import astre.Controleur;
import astre.vue.outils.Tableau;

/** Classe PanelParametrage
  * @author : Maximilien LESTERLIN
  * @version : 1.0 - 13/12/23
  * @date : 13/12/2023
  */

public class PanelTypeHeure extends JPanel
{
	private Controleur ctrl;
	private Tableau    tabContrat;
	
	public PanelTypeHeure ( Controleur ctrl )
	{
		this.ctrl = ctrl;

		this.setLayout ( new GridLayout ( 1, 2 ) );
		
		String[] enTete = { "Nom Heure", " Coeff TD" };

		JPanel pnlListeModule = new JPanel ( new BorderLayout ( ) );

		this.tabContrat = new Tableau ( enTete , this.ctrl.getTableauHeure ( ), 0 );
		this.tabContrat.setEditable(true);
		
		this.tabContrat.setShowGrid ( false );
		this.tabContrat.setIntercellSpacing ( new Dimension ( 0, 0 ) );

		// Ajout du titre et rend la liste défilable
		JScrollPane spTab = new JScrollPane ( this.tabContrat );
		spTab.setBorder                  ( new TitledBorder ( "Liste des type d'heure" ) );
		spTab.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );

		pnlListeModule.add ( spTab, BorderLayout.CENTER );

		this.add ( pnlListeModule, BorderLayout.CENTER );
	}
}