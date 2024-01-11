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
	
		this.setTabLayoutPolicy ( JTabbedPane.SCROLL_TAB_LAYOUT );
		this.setPreferredSize   ( ConstantesVue.DIMENSION_TAB );

		// Creation et positionnement des composants
		String[] enTeteContrat   = { "action", "id", "Nom", "Heure Service Contrat", "Heure Max Contrat", "Ratio TP" };
		String[] enTeteTypeHeure = { "action", "id_Heure" , "Nom Heure"            , " Coeff TD" };

		Object[] tabObjectsContrat   = { ' ', 0, "Nom du contrat", 0, 0, 1.0 };
		Object[] tabObjectsTypeHeure = { ' ', 0, "Nom de l'heure", 1.0       };
			
		boolean[] tabEditableContrat   = { false, false, true, true, true, true };
		boolean[] tabEditableTypeHeure = { false, false, false , true           };

		String   nomTabContrat   = "Liste des contrats";
		String   nomTabTypeHeure = "Liste des types d'heures";

		this.pnlContrat   = new PanelParametrage ( this.ctrl, enTeteContrat  , tabObjectsContrat  , this.ctrl.getTableau ( Contrat.class ), tabEditableContrat  , nomTabContrat  , Contrat.class );
		this.pnlTypeHeure = new PanelParametrage ( this.ctrl, enTeteTypeHeure, tabObjectsTypeHeure, this.ctrl.getTableau ( Heure  .class ), tabEditableTypeHeure, nomTabTypeHeure, Heure  .class );

		// Positionnement des composants
		this.add ( "Contrat"   , this.pnlContrat   );
		this.add ( "Type Heure", this.pnlTypeHeure );
	}
}
