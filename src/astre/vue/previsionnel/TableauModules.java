package astre.vue.previsionnel;

import javax.swing.*;
import java.awt.Component;
import javax.swing.table.*;

import astre.vue.outils.*;

/** Classe représentant un tableau personnalisable.
 *  @author Maxime
 *  @version : 1.0 - 11/12/2023
 *  @date : 11/12/2023
*/
public class TableauModules extends JTable
{
	private String[] nomColonnes;
	//private DefaultTableModel modele;
	private ModeleTableau modele;
	
	public TableauModules ( String[] nomColonnes )
	{
		this.nomColonnes = nomColonnes;
		//this.modele = new DefaultTableModel ( this.nomColonnes, 1 );
		this.modele = new ModeleTableau(this.nomColonnes, true);
		
		this.setModel ( this.modele );
		//this.modele.addTableModelListener(this);
		this.setSelectionMode ( ListSelectionModel.SINGLE_SELECTION );
		
		//Permet au tableau de prendre toute la frame
		this.setAutoResizeMode ( JTable.AUTO_RESIZE_ALL_COLUMNS );
		//Permet d'enpecher de déplacer les cases (je crois)(ne marche pas vraiment)
		this.setDragEnabled ( false );

	}
	
	/**
	* Ajuste la taille des colonnes.
	*/
	private void ajusterTailleColonnes ( )
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
		//this.modele.addRow ( new Object[ this.nomColonnes.length ] );
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
			//this.modele.removeRow ( selection );
			this.modele.supprimerLigne(selection);
		}
		this.ajusterTailleColonnes ( );
	}

}
