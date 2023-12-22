package astre.vue.outils;

import astre.modele.outils.ModeleTableau;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JCheckBox;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/** Menu de l'application
  * @author : Maxime Lemoine
  * @version : 1.0 - 19/12/2023
  * @date : 19/12/2023
  */
//TODO: Ligne sélectionner en bleu
public class OperationRenduTableau extends DefaultTableCellRenderer
{
	//private static final long serialVersionUID = 1L;
	private static final Color COULEUR_MODIFIER  = new Color ( 174,214,241 );
	private static final Color COULEUR_SUPPRIMER = new Color ( 230,176,170 );
	private static final Color COULEUR_AJOUTER   = new Color ( 171,235,198 );
	private static final Color COULEUR_FOND_1    = new Color ( 253,254,254 );
	private static final Color COULEUR_FOND_2    = new Color ( 248,249,249 );

	@Override
	public Component getTableCellRendererComponent ( JTable tbl, Object valeur, boolean estSelectionne, boolean focus, int lig, int col )
	{
		// Appeler la méthode de la classe parent pour obtenir le rendu par défaut
		Component cellule = super.getTableCellRendererComponent ( tbl, valeur, estSelectionne, focus, lig, col );
		
		if ( valeur instanceof Boolean )
		{
			JCheckBox checkBox = new JCheckBox ( );
			checkBox.setSelected            ( ( Boolean ) valeur );
			checkBox.setHorizontalAlignment ( JCheckBox.CENTER   );
			cellule = checkBox;
		}
		
		// par défaut il y a une alternance de couleurs
		if ( ! focus )
		{
			if ( lig % 2 == 0 )
				cellule.setBackground ( COULEUR_FOND_1 );
			else
				cellule.setBackground ( COULEUR_FOND_2 );
		}
		
		cellule.setForeground ( tbl.getForeground ( ) );
		
		Object premiereCelulleLigne = ( ( ModeleTableau ) ( tbl.getModel ( ) ) ).getObjet ( lig, 0 );
		if ( premiereCelulleLigne instanceof Character )
		{
			char operation = ( char ) premiereCelulleLigne;

			/*
			 * Ajouter des actions à réaliser sur la ligne en fonction de l'opération donnée
			*/
			switch ( operation )
			{
				case ModeleTableau.MODIFIER :
					cellule.setBackground ( COULEUR_MODIFIER  );
					cellule.setForeground ( Color.BLACK       );
					break;

				case ModeleTableau.IGNORER   :
				case ModeleTableau.SUPPRIMER :
					cellule.setBackground ( COULEUR_SUPPRIMER );
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
			
		// Aligner le texte à droite si la valeur est numérique
		if ( valeur instanceof Number )
		{
			setHorizontalAlignment ( RIGHT );
		}
		else
		{
			// Rétablir l'alignement par défaut pour le texte
			setHorizontalAlignment ( LEFT );
		}
		
<<<<<<< HEAD
		/*// Mettre les cases à cocher
		if ( valeur instanceof Boolean )
		{
			setHorizontalAlignment(SwingConstants.CENTER);
			setSelected((Boolean) value);
		}*/

		return this;
=======
		return cellule;
>>>>>>> 0636f4d34a7c1d6fef540d5b1b9e21d7a2241c44
	}
}
