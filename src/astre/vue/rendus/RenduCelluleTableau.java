package astre.vue.rendus;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import astre.modele.maths.ValeurFractionnable;
import astre.modele.outils.ModeleTableau;
import astre.vue.outils.Tableau;

/** Rendu des cellules de tous les tableaux
  * @author : Maxime Lemoine
  * @version : 1.1 - 18/01/2024
  * @date : 19/12/2023
  * @see : Tableau
  * @warning : ne plus utiliser d'appel au modele du tableau
  */

public class RenduCelluleTableau extends DefaultTableCellRenderer //ancien nom : OperationRenduTableau
{
	private static final Color COULEUR_MODIFIER  = new Color ( 174,214,241 );
	private static final Color COULEUR_SUPPRIMER = new Color ( 230,176,170 );
	private static final Color COULEUR_AJOUTER   = new Color ( 171,235,198 );
	private static final Color COULEUR_FOND_1    = new Color ( 253,254,254 );
	private static final Color COULEUR_FOND_2    = new Color ( 248,249,249 );

	@Override
	public Component getTableCellRendererComponent ( JTable jtbl, Object valeur, boolean estSelectionne, boolean focus, int lig, int col )
	{	
		Tableau tbl = ( Tableau ) jtbl;

		// Appeler la méthode de la classe parent pour obtenir le rendu par défaut
		Component cellule = super.getTableCellRendererComponent ( tbl, valeur, estSelectionne, focus, lig, col );
		

		/* ---------------------------------------------- */
		/* Conversion en un objet graphique si nécessaire */
		/* ---------------------------------------------- */

		if ( valeur instanceof Boolean )
		{
			JCheckBox checkBox = new JCheckBox ( );
			checkBox.setSelected            ( ( Boolean ) valeur );
			checkBox.setHorizontalAlignment ( JCheckBox.CENTER   );
			
			cellule = checkBox;
		}

		if ( valeur instanceof java.util.List )
		{
			System.out.println( "RenduCelluleTableau : valeur = List" );
			JLabel label = new JLabel ( );
			label.setOpaque ( true );
			cellule = label;
		}


		/* --------------------------------- */
		/* Application des styles par défaut */
		/* --------------------------------- */

		cellule.setForeground          ( tbl.getSelectionForeground ( ) );
		
		// par défaut il y a une alternance de couleurs
		if ( ! estSelectionne )
		{
			if ( lig % 2 == 0 )
				cellule.setBackground ( COULEUR_FOND_1 );
			else
				cellule.setBackground ( COULEUR_FOND_2 );
		}
		else
		{
			cellule.setBackground ( tbl.getSelectionBackground ( ) );
		}
		

		/* ------------------------------- */
		/* Alignements par type de valeurs */
		/* ------------------------------- */

		if ( valeur instanceof Number || valeur instanceof ValeurFractionnable )
		{
			setHorizontalAlignment ( RIGHT );
		}
		else
		{
			// Rétablir l'alignement par défaut pour le texte
			setHorizontalAlignment ( LEFT );
		}


		/* ------------------------------------------------------- */
		/* Application des couleurs en fonction de la modification */
		/* ------------------------------------------------------- */

		Object premiereCelulleLigne = tbl.getObjet ( lig, 0 );
		if ( premiereCelulleLigne instanceof Character )
		{
			char operation = ( char ) premiereCelulleLigne;

			switch ( operation ) //actions à réaliser sur la ligne en fonction de l'opération donnée
			{
				case ModeleTableau.MODIFIER :
					cellule.setBackground ( COULEUR_MODIFIER  );
					cellule.setForeground ( Color.BLACK       );
					break;

				case ModeleTableau.IGNORER   :
				case ModeleTableau.SUPPRIMER :
					cellule.setBackground ( COULEUR_SUPPRIMER );
					tbl.setLigneEditable  ( lig, false        );
					cellule.setForeground ( Color.BLACK       );
					break;

				case ModeleTableau.AJOUTER :
					cellule.setBackground ( COULEUR_AJOUTER   );
					cellule.setForeground ( Color.BLACK       );
					break;

				default :
					break;
			}
		}
		
		return cellule;
	}
}