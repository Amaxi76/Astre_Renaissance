package astre.vue.outils;

import astre.modele.outils.ModeleTableau;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JCheckBox;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/** Menu de l'application
  * @author : Maxime Lemoine
  * @version : 1.0 - 19/12/2023
  * @date : 19/12/2023
  */
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

		// Aligner le texte à droite si la valeur est numérique
		if ( valeur instanceof Number )
			setHorizontalAlignment ( RIGHT );
		else
			setHorizontalAlignment ( LEFT ); // Rétablir l'alignement par défaut pour le texte

		// par défaut il y a une alternance de couleurs
		if ( lig % 2 == 0 )
			cellule.setBackground ( COULEUR_FOND_1 );
		else
			cellule.setBackground ( COULEUR_FOND_2 );
		
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

		//Récupération des données
		ModeleTableau modele = ( ModeleTableau ) ( tbl.getModel ( ) );
		Object[][] donnees   = modele.getDonnees ( );

		//changement des couleurs de bordure pour alertes
		if ( donnees.length != 0 )
		{
			JComponent jcellule = ( JComponent ) cellule;
			jcellule.setBorder ( null );

			//Pour le tableau d'intervenant
			if ( donnees[0].length == 17 && col == 14 )//nbcolonne de tablo intervenant et seulement la derniere colonne
			{
				int hmin  = Integer.parseInt ( donnees[lig][ 5].toString ( ) );
				int hmax  = Integer.parseInt ( donnees[lig][ 6].toString ( ) );
				int total = Integer.parseInt ( donnees[lig][16].toString ( ) );

				if ( hmin > total || hmax < total )
				{
					jcellule.setBorder ( BorderFactory.createMatteBorder ( 1,1,1,1,Color.RED ) );
				}
			}

			if ( donnees[0].length == -1 )//TODO faire pour tableau de module (dépassement par rapport au PN ou répartition incomplète.)
			{

			}

		}

		return this;
	}
}
