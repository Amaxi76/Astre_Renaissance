package astre.vue.outils;

import javax.swing.*;
import java.awt.Component;
import javax.swing.table.*;

/** Classe représentant un tableau personnalisable.
 *  @author Matéo et Maxime
 *  @version : 1.0 - 11/12/2023
 *  @date : 06/12/2023
*/

//TODO: simplifier les constructeurs, en les appelant les uns les autres
//TODO: ajouter un booleen pour le constructeur sans titre de colonnes
//TODO: nettoyer les constructeurs qui semblent inutiles (utilisés avant pour les tests avec tableaux vides)

public class Tableau extends JTable
{
	private String[] nomColonnes;
	private ModeleTableau modele;
	
	public Tableau ( String[] nomColonnes, Object[][] tabDonnees, boolean estModifiable )
	{
		this.nomColonnes = nomColonnes;
		this.modele = new ModeleTableau(this.nomColonnes, tabDonnees, estModifiable);
		
		this.setModel ( this.modele );
		this.setSelectionMode ( ListSelectionModel.SINGLE_SELECTION );
		
		//Permet au tableau de prendre toute la frame
		this.setAutoResizeMode ( JTable.AUTO_RESIZE_ALL_COLUMNS );

		//Permet d'enpecher de déplacer les cases (je crois)(ne marche pas vraiment)
		this.setDragEnabled ( false );
	}
	
	public Tableau ( Object[][] tabDonnees, boolean estModifiable )
	{
		this ( new String[] { "","","","" }, tabDonnees, estModifiable );
		this.setTableHeader(null);
	}
	
	/**
	* Ajuste la taille des colonnes.
	*/
	public void ajusterTailleColonnes ( )
	{
		TableColumnModel columnModel = this.getColumnModel ( );
		for ( int i = 0; i < columnModel.getColumnCount ( ); i++ )
		{
			int maxWidth = 0;
			for ( int j = 0; j < this.getRowCount ( ); j++ )
			{
				TableCellRenderer cellRenderer = this.getCellRenderer ( j, i );
				Object value = this.getValueAt ( j, i );
				Component cellRendererComponent = cellRenderer.getTableCellRendererComponent ( this, value, false, false, j, i );
				maxWidth = Math.max ( maxWidth, cellRendererComponent.getPreferredSize ( ).width );
			}
			columnModel.getColumn ( i ).setPreferredWidth ( maxWidth - 30 );
		}
	}
	
	/**
	* Ajoute une ligne vide au tableau.
	*/
	public void ajouterLigne ( )
	{
		this.modele.ajouterLigne();
		this.ajusterTailleColonnes ( );
	}
	
	/**
	* Supprime la ligne sélectionnée du tableau.
	*/
	public void supprimerLigne ( )
	{
		int selection = this.getSelectedRow ( );
		if ( selection != -1 )
		{
			this.modele.supprimerLigne(selection);
		}
		this.ajusterTailleColonnes ( );
	}

	/**
	* Permet de récupérer les données du modele
	*/
	public Object[][] getDonnees()
    {
        return this.modele.getDonnees();
    }

}
