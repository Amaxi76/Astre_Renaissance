package astre.vue.previsionnel.module;

import javax.swing.table.AbstractTableModel;

public class ModeleTableauIntervient extends AbstractTableModel 
{
	private String[]   tabEntetes;
	private Object[][] tabDonnees;
	
	public ModeleTableauIntervient ( String[] tabEntetes, Object[][] tabDonnees )
	{
		this.tabEntetes = tabEntetes;
		this.tabDonnees = tabDonnees;
	}
	
	public int    getColumnCount()                   { return this.tabEntetes.length;        }
	public int    getRowCount   ()                   { return this.tabDonnees.length;        }
	public Object getValueAt    ( int row, int col ) { return this.tabDonnees[row][col + 1]; }
	public String getNomColonne ( int col )          { return this.tabEntetes[col];          }
	public String getColumnName ( int c )            { return this.tabEntetes[c];            }


	public boolean isCellEditable ( int row, int col )
	{
		if( col == 0 || col == 1 || col == 2 || col == 3 || col == 4 )
			return true;
		
		return false;
	}


	public void setValueAt ( Object value, int row, int col )
	{
		this.tabDonnees[row][col + 1] = value;
	}


	public void ajouterLigne()
	{
		if ( tabDonnees != null && tabDonnees.length != 0 )
		{
			Object[][] nouveauTableau = new Object[tabDonnees.length + 1][tabDonnees[0].length];

			for ( int i = 0; i < tabDonnees.length; i++ )
			{
				for ( int j = 0; j < tabDonnees[0].length; j++ )
				{
					nouveauTableau[i][j] = tabDonnees[i][j];
				}
			}

			for ( int j = 0; j < tabDonnees[0].length; j++) 
			{
				if ( tabEntetes[j].startsWith ( "Intervenant" ) || tabEntetes[j].startsWith ( "type" ) || tabEntetes[j].startsWith ( "commentaire" ) )
					nouveauTableau[tabDonnees.length][j] = "";
				else
					nouveauTableau[tabDonnees.length][j] = 0;
			}

			tabDonnees = nouveauTableau;
		}
		else
		{
			tabDonnees = new Object[1][tabEntetes.length];
		}
		fireTableDataChanged();
	}


	public void supprimerLigne ( int index )
	{
		Object[][] nouveauTableau = new Object[tabDonnees.length - 1][tabDonnees[0].length];

		for ( int i = 0, k = 0; i < tabDonnees.length; i++ )
		{
			if ( i != index )
			{
				for ( int j = 0; j < tabDonnees[0].length; j++ )
				{
					nouveauTableau[k][j] = tabDonnees[i][j];
				}
				k++;
			}
		}

		tabDonnees = nouveauTableau;
		fireTableDataChanged();
	}

	public Object[][] getDonnees()
	{
		return this.tabDonnees;
	}

	public void modifDonnees(Object[][] donnee)
	{
		this.tabDonnees = donnee;
		fireTableDataChanged();
	}
}