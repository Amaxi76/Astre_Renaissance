package astre.vue.rendus;

import java.awt.Component;
import javax.swing.JTable;
import astre.vue.outils.ConstantesVue;
import astre.vue.outils.Tableau;

/** Rendu des cellules de tableau EnsSemestre
  * @author : Maxime Lemoine, Maximilien Lesterlin
  * @version : 1.1 - 18/01/2024
  * @date : 10/08/2024
  */

public class RenduCelluleTableauSemestre extends RenduCelluleTableau
{
	@Override
	public Component getTableCellRendererComponent ( JTable jtbl, Object valeur, boolean estSelectionne, boolean focus, int lig, int col )
	{
		Tableau tbl = ( Tableau ) jtbl;

		Component cellule = super.getTableCellRendererComponent ( tbl, valeur, estSelectionne, focus, lig, col );

		//changement des couleurs de bordure pour alertes

		if ( tbl.getColumnName ( col ).equals ( "" ) && col == 2 ) // Les tableaux de module n'ont pas d'entête -> j'ai repris la version de matéo mais wtf
		{
			String[] ratio = tbl.getObjet( lig, 4 ).toString ( ).split("/");
			
			for ( int i =0; i< ratio.length ; i++ )
				ratio[i] = ratio[i].strip( );

			double hPN  = Double.parseDouble ( ratio[1] );
			double hRep = Double.parseDouble ( ratio[0] );

			if   ( hRep > hPN || ( hRep < hPN - hPN / 10 ) ) cellule.setForeground ( ConstantesVue.ROUGE_ERREUR   );
			else                                             cellule.setForeground ( ConstantesVue.VERT_VALIDATION );
		}

		return cellule;
	}
}
