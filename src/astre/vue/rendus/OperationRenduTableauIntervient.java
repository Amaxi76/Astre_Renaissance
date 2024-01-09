package astre.vue.rendus;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;

import astre.modele.outils.ModeleTableau;

/** Rendu des cellules de tableau Intervient
  * @author  : Maxime Lemoine, Maximilien Lesterlin
  * @version : 1.0 - 08/08/2024
  * @date    : 08/08/2024
  */

public class OperationRenduTableauIntervient extends OperationRenduTableau
{
	@Override
	public Component getTableCellRendererComponent ( JTable tbl, Object valeur, boolean estSelectionne, boolean focus, int lig, int col )
	{
		// Appel de la méthode de la classe parente
		Component cellule = super.getTableCellRendererComponent ( tbl, valeur, estSelectionne, focus, lig, col );

		// Affichage à gauche ou à droite en fonction du nombre de semaines
		Object nbSemaineCelluleLig = ( ( ModeleTableau ) ( tbl.getModel ( ) ) ).getObjet ( lig, 4 );
		if ( ( int ) nbSemaineCelluleLig == 1 && col == 4 ) setHorizontalAlignment ( RIGHT );
		else                                                setHorizontalAlignment ( LEFT  );
 
		// Couleur de la cellule en gris si une seule semaine (par défaut)
		if ( col == 3 && ( int ) nbSemaineCelluleLig == 1 ) cellule.setForeground ( Color.GRAY );

		if ( col == 4 ) cellule.setForeground ( Color.GRAY );
		else            cellule.setForeground ( tbl.getForeground ( ) );

		return cellule;
	}
}
