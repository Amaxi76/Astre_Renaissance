package astre.vue.previsionnel.module;

import javax.swing.*;
import java.awt.Component;
import javax.swing.table.*;

import astre.vue.outils.ModeleTableau;

public class TableauIntervient extends JTable
{
	private String[]      nomColonnes;
	private ModeleTableau modele;

	public TableauIntervient ( String[] nomColonnes, Object[][] tabDonnees )
	{
		this.nomColonnes = nomColonnes;
		this.modele      = new ModeleTableau(this.nomColonnes, tabDonnees);
		
		this.setModel ( this.modele );
		this.setSelectionMode ( ListSelectionModel.SINGLE_SELECTION );
		
		this.setAutoResizeMode ( JTable.AUTO_RESIZE_ALL_COLUMNS );
		this.setDragEnabled ( false );
	}

	public TableauIntervient ( Object[][] tabDonnees )
	{
		this ( new String[] { "","","","" }, tabDonnees );
		this.setTableHeader(null);
	}

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

	public void ajouterLigne ( )
	{
		this.modele.ajouterLigne();
	}

	public void supprimerLigne ( )
	{
		int selection = this.getSelectedRow ( );
		if ( selection != -1 )
		{
			this.modele.supprimerLigne(selection);
		}
		this.ajusterTailleColonnes ( );
	}

	public Object[][] getDonnees()
	{
		return this.modele.getDonnees();
	}

	public void modifDonnees(Object[][] donnee)
	{
		this.modele.modifDonnees(donnee);
	}
}