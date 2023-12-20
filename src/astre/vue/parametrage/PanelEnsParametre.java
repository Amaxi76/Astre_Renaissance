package astre.vue.parametrage;

import astre.Controleur;
import astre.vue.outils.ConstantesVue;
import astre.modele.elements.Contrat;
import astre.modele.elements.Heure;

import javax.swing.*;

/** Classe PanelEnsParametre
  * @author : Maximilien LESTERLIN
  * @version : 1.0 - 11/12/23
  * @date : 06/12/2023
  */

public class PanelEnsParametre extends JTabbedPane
{
	private Controleur       ctrl;
	private PanelParametrage pnlContrat;
	private PanelParametrage pnlTypeHeure;
	
	public PanelEnsParametre ( Controleur ctrl )
	{
		// Configuration
		this.ctrl = ctrl;
	
		//this.setUI              ( new BasicTabbedPaneUI ( ) );
		this.setTabLayoutPolicy ( JTabbedPane.SCROLL_TAB_LAYOUT );
		this.setPreferredSize   ( ConstantesVue.DIMENSION_TAB );

		// Creation et positionnement des composants
		String[] enTeteContrat   = { "action", "id", "Nom", "Heure Service Contrat", "Heure Max Contrat", "Ratio TP" };
		String[] enTeteTypeHeure = { "action", "id_Heure" , "Nom Heure"            , " Coeff TD" };

		Object[] tabObjectsContrat   = { ' ', 0, "Nom du contrat", 0, 0, 0.0 };
		Object[] tabObjectsTypeHeure = { ' ', 0, "Nom de l'heure", 0.0       };

		String   nomTabContrat   = "Liste des contrats";
		String   nomTabTypeHeure = "Liste des types d'heures";

		this.pnlContrat   = new PanelParametrage ( this.ctrl, enTeteContrat  , tabObjectsContrat  , this.ctrl.getTableau ( Contrat.class ), nomTabContrat  , Contrat.class );
		this.pnlTypeHeure = new PanelParametrage ( this.ctrl, enTeteTypeHeure, tabObjectsTypeHeure, this.ctrl.getTableau ( Heure  .class ), nomTabTypeHeure, Heure  .class );

		// Positionnement des composants
		this.add ( "Contrat"   , this.pnlContrat   );
		this.add ( "Type Heure", this.pnlTypeHeure );
	}
}
