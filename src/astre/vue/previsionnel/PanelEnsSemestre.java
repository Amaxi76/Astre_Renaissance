package astre.vue.previsionnel;

import astre.Controleur;
import astre.vue.outils.ConstantesVue;
import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

/** Classe PanelEnsSemestre
  * @author : Maximeuuu
  * @version : 1.0 - 11/12/23
  * @date : 06/12/2023
  */

public class PanelEnsSemestre extends JTabbedPane
{
	private Controleur ctrl;

	public PanelEnsSemestre ( Controleur ctrl )
	{
		// Configuration
		this.ctrl = ctrl;
		this.setPreferredSize   ( ConstantesVue.DIMENSION_TAB );
		//this.setUI              ( new BasicTabbedPaneUI ( ) );
		this.setTabLayoutPolicy ( JTabbedPane.SCROLL_TAB_LAYOUT );

		// Creation et positionnement des composants
		for ( int cptSemestre = 1; cptSemestre < ConstantesVue.NB_SEMESTRE + 1; cptSemestre++ )
			this.add ( "S" + cptSemestre, new PanelSemestre ( cptSemestre, this.ctrl ) );

	}
	
}
