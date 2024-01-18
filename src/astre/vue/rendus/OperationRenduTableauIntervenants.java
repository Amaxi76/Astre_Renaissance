package astre.vue.rendus;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JTable;

import astre.modele.outils.ModeleTableau;

//FIXME: UTILISER LA NOUVELLE CLASSE ET NOUVEAU NOM : RenduCelluleTableau
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

		/*
		COLORATION DES CASES EN FONCTION DES HEURES MIN ET MAX
		*/

		//Récupération des données
		ModeleTableau modele = ( ModeleTableau ) ( tbl.getModel ( ) );
		Object[][] donnees   = modele.getDonnees ( );

		//changement des couleurs de bordure pour alertes
		if ( donnees.length != 0 )
		{
			JComponent jcellule = ( JComponent ) cellule;

			int hmin  = Integer.parseInt ( donnees[lig][ 5].toString ( ) );
			int hmax  = Integer.parseInt ( donnees[lig][ 6].toString ( ) );
			int total = Integer.parseInt ( donnees[lig][16].toString ( ) );

			if   ( hmin > total || hmax < total ) jcellule.setForeground ( Color.RED   );
			else                                  jcellule.setForeground ( Color.BLACK );

		}

		return cellule;
	}
}
