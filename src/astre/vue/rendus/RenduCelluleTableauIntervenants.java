package astre.vue.rendus;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JTable;

import astre.modele.outils.ModeleTableau;
import astre.vue.outils.Tableau;

/** Rendu des cellules de tableau EnsSemestre
  * @author : Maxime Lemoine, Maximilien Lesterlin
  * @version : 1.1 - 18/01/2024
  * @date : 08/08/2024
  */

public class RenduCelluleTableauIntervenants extends RenduCelluleTableau
{
	@Override
	public Component getTableCellRendererComponent ( JTable jtbl, Object valeur, boolean estSelectionne, boolean focus, int lig, int col )
	{
		Tableau tbl = ( Tableau ) jtbl;

		Component cellule = super.getTableCellRendererComponent ( tbl, valeur, estSelectionne, focus, lig, col );

		if ( !( tbl.isCellEditable ( lig, col )  )
			cellule.setForeground ( Color.GRAY );
		else
			cellule.setForeground ( tbl.getForeground ( ) );

		/*
		COLORATION DES CASES EN FONCTION DES HEURES MIN ET MAX
		*/

		//changement des couleurs de bordure pour alertes
		JComponent jcellule = ( JComponent ) cellule;

		int hmin  = ( int ) tbl.getObjet ( lig, 5  );
		int hmax  = ( int ) tbl.getObjet ( lig, 6  );
		int total = ( int ) tbl.getObjet ( lig, 16 );

		if   ( hmin > total || hmax < total ) jcellule.setForeground ( Color.RED   );
		else                                  jcellule.setForeground ( Color.BLACK );

		return cellule;
	}
}
