package astre.vue.previsionnel;

import astre.Controleur;
import astre.vue.outils.ConstantesVue;

import java.util.ArrayList;

import javax.swing.*;

/**
 * Classe PanelEnsSemestre
 * 
 * @author : Maximeuuu
 * @version : 1.0 - 11/12/23
 * @date : 06/12/2023
 */

public class PanelEnsSemestre extends JTabbedPane {
	private Controleur ctrl;

	private ArrayList<PanelSemestre> lstSemestre;

	public PanelEnsSemestre(Controleur ctrl, int semestreDefaut) {
		// Configuration
		this.ctrl = ctrl;
		this.setPreferredSize(ConstantesVue.DIMENSION_TAB);
		// this.setUI ( new BasicTabbedPaneUI ( ) );
		this.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

		this.lstSemestre = new ArrayList<PanelSemestre>();

		// Creation et positionnement des composants
		for (int cptSemestre = 1; cptSemestre < ConstantesVue.NB_SEMESTRE + 1; cptSemestre++) {
			PanelSemestre temp = new PanelSemestre(cptSemestre, this.ctrl);
			this.lstSemestre.add(temp);
			this.add("S" + cptSemestre, temp);
		}

		// Selection du semestre par défaut
		this.setSelectedIndex(semestreDefaut);
	}

	public String getModuleSelection()
	{
		return this.lstSemestre.get(this.getSelectedIndex()).getModuleSelection() ;
	}

	public void majTableau ( )
	{
		this.lstSemestre.get ( this.getSelectedIndex ( ) ).majTableau ( );
	}

}
