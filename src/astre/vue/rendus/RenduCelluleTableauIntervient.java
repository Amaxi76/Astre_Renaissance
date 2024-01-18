package astre.vue.rendus;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;

import astre.modele.outils.ModeleTableau;
import astre.vue.outils.Tableau;

/** Rendu des cellules de tableau Intervient
  * @author  : Maxime Lemoine, Maximilien Lesterlin
  * @version : 1.1 - 18/01/2024
  * @date    : 08/08/2024
  */

public class RenduCelluleTableauIntervient extends RenduCelluleTableau
{

	@Override
	public Component getTableCellRendererComponent ( JTable jtbl, Object valeur, boolean estSelectionne, boolean focus, int lig, int col )
	{
		int colNbSemaine       = 3;
		int colNbHeureOuGroupe = 4;

		Tableau tbl = ( Tableau ) jtbl;

		// Appel de la méthode de la classe parente
		Component cellule = super.getTableCellRendererComponent ( tbl, valeur, estSelectionne, focus, lig, col );

		// Affichage à gauche ou à droite en fonction du nombre de semaines
		Object nbSemaineCelluleLig = tbl.getObjet ( lig, colNbHeureOuGroupe );
		if ( ( int ) nbSemaineCelluleLig == 1 && col == colNbSemaine ) setHorizontalAlignment ( RIGHT );
		else                                                           setHorizontalAlignment ( LEFT  );
 
		// Couleur de la cellule en gris si une seule semaine (par défaut)
		if ( col == colNbSemaine && ( int ) nbSemaineCelluleLig == 1 ) cellule.setForeground ( Color.GRAY );

		if ( col == colNbHeureOuGroupe ) cellule.setForeground ( Color.GRAY );
		else                             cellule.setForeground ( tbl.getForeground ( ) );

		return cellule;
	}
}
