package astre.vue.parametrage;

import astre.Controleur;
import astre.vue.outils.ConstantesVue;
import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

/** Classe PanelEnsParametre
  * @author : Maximilien LESTERLIN
  * @version : 1.0 - 11/12/23
  * @date : 06/12/2023
  */

public class PanelEnsParametre extends JTabbedPane
{
	private Controleur     ctrl;
	private PanelContrat   pnlContrat;
	private PanelTypeHeure pnlTypeHeure;

	public PanelEnsParametre ( Controleur ctrl )
	{
		// Configuration
		this.ctrl         = ctrl;
		this.pnlContrat   = new PanelContrat   ( this.ctrl );
		this.pnlTypeHeure = new PanelTypeHeure ( this.ctrl );
		this.setPreferredSize   ( ConstantesVue.DIMENSION_TAB );
		//this.setUI              ( new BasicTabbedPaneUI ( ) );
		this.setTabLayoutPolicy ( JTabbedPane.SCROLL_TAB_LAYOUT );

		// Creation et positionnement des composants
		this.add ( "Contrat"   , this.pnlContrat   );
		this.add ( "Type Heure", this.pnlTypeHeure );
	}
	
}
