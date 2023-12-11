package astre.vue.previsionnel;

import astre.Controleur;
import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;

/** Classe PanelEnsSemestre
  * @author : Maximeuuu
  * @version : 1.0 - 11/12/23
  * @date : 06/12/2023
  */

public class PanelEnsSemestre extends JTabbedPane
{
	private Controleur ctrl;

	public static final Dimension DIMENSION_TAB = new Dimension ( 350,400 );
	private static final int NB_SEMESTRE = 6;

	public PanelEnsSemestre ( Controleur ctrl )
	{
		// Configuration
		this.ctrl = ctrl;
		this.setPreferredSize ( PanelEnsSemestre.DIMENSION_TAB );
		this.setUI ( new CustomTabbedPaneUI ( ) );
		this.setTabLayoutPolicy ( JTabbedPane.SCROLL_TAB_LAYOUT );

		// Creation et positionnement des composants
		for ( int cptSemestre=1; cptSemestre<NB_SEMESTRE+1; cptSemestre++ )
		{
			this.add ( "S"+cptSemestre, new PanelSemestre ( cptSemestre, this.ctrl ) );
		}
	}
	
	class CustomTabbedPaneUI extends BasicTabbedPaneUI
	{
		//pour changer l'aspect par la suite
	}
}
