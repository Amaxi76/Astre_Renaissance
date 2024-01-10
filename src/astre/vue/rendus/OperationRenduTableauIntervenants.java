package astre.vue.rendus;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;

import astre.modele.outils.ModeleTableau;

/** Rendu des cellules de tableau EnsSemestre
  * @author : Maxime Lemoine, Maximilien Lesterlin
  * @version : 1.0 - 08/08/2024
  * @date : 08/08/2024
  */

public class OperationRenduTableauIntervenants extends OperationRenduTableau
{
	@Override
	public Component getTableCellRendererComponent ( JTable tbl, Object valeur, boolean estSelectionne, boolean focus, int lig, int col )
	{
		Component cellule = super.getTableCellRendererComponent ( tbl, valeur, estSelectionne, focus, lig, col );

		if ( !( ( ModeleTableau ) ( tbl.getModel ( ) ) ).isCellEditable ( lig, col )  )
			cellule.setForeground ( Color.GRAY );
		else
			cellule.setForeground ( tbl.getForeground ( ) );

		return cellule;
	}
}
