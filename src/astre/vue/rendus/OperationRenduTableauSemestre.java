package astre.vue.rendus;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JTable;

import astre.modele.outils.ModeleTableau;
import astre.vue.outils.ConstantesVue;

//FIXME: UTILISER LA NOUVELLE CLASSE ET NOUVEAU NOM : RenduCelluleTableau
/** Rendu des cellules de tableau EnsSemestre
  * @author : Maxime Lemoine, Maximilien Lesterlin
  * @version : 1.0 - 10/08/2024
  * @date : 10/08/2024
  */

public class OperationRenduTableauSemestre extends OperationRenduTableau
{
	@Override
	public Component getTableCellRendererComponent ( JTable tbl, Object valeur, boolean estSelectionne, boolean focus, int lig, int col )
	{
		Component cellule = super.getTableCellRendererComponent ( tbl, valeur, estSelectionne, focus, lig, col );

		//Récupération des données
		ModeleTableau modele = ( ModeleTableau ) ( tbl.getModel ( ) );
		Object[][] donnees   = modele.getDonnees ( );

		//changement des couleurs de bordure pour alertes
		if ( donnees.length != 0 )
		{
			String[] entete = modele.getEntete ( );

			if ( entete.length == 6 && entete[0].equals("") && col == 2 )//tablo module (ya pas d'entete)
			{
				String[] ratio = donnees[lig][4].toString ( ).split("/");
				
				for ( int i =0; i< ratio.length ; i++ )
					ratio[i] = ratio[i].strip( );

				double hPN  = Double.parseDouble ( ratio[1] );
				double hRep = Double.parseDouble ( ratio[0] );

				if   ( hRep > hPN || ( hRep < hPN - hPN / 10 ) ) cellule.setForeground ( ConstantesVue.ROUGE_ERREUR   );
				else                                             cellule.setForeground ( ConstantesVue.VERT_VALIDATION );
			}
		}
		return cellule;
	}
}
