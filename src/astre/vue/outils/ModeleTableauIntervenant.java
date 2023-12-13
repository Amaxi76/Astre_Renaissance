package astre.vue.outils;

import javax.swing.table.AbstractTableModel;

/**
 * Classe des données et d'affichage du tableau.
 * @author Matéo
 */
public class ModeleTableauIntervenant extends AbstractTableModel 
{
    private String[]   tabEntetes;
	private Object[][] tabDonnees;
	private boolean    estModifiable=true;
    
    public ModeleTableauIntervenant ( String[] tabEntetes, Object[][] tabDonnees, boolean estModifiable )
    {
    	this.estModifiable = estModifiable;
        this.tabEntetes = tabEntetes;
        this.tabDonnees = tabDonnees;
    }
    
    //si la méthode est en anglais c'est qu'elle est obligatoire.
    public int    getColumnCount()                 { return this.tabEntetes.length - 1;      }//permet de cacher la 1ere colonne
	public int    getRowCount   ()                 { return this.tabDonnees.length;      }
	public Object getValueAt    (int row, int col) { return this.tabDonnees[row][col + 1];   } //TODO certaine colonne veulent des Numeric pas des String??
	public String getNomColonne (int col)          { return this.tabEntetes[col];        }
	//public Class  getColumnClass(int c)            { return getValueAt(0, c).getClass(); }
	public String getColumnName (int c)             { return this.tabEntetes[c + 1];           }//permet de cacher la 1ere colonne

    /**
	* Donne la liste des cellules éditables.
	*/
	public boolean isCellEditable(int row, int col)
	{
		return this.estModifiable;
	}
	
	public void setEditable( boolean editable )
	{
		this.estModifiable = editable;
	}

    /**
	* Ajoute une valeur dans une case.
	*/
	public void setValueAt(Object value, int row, int col)
	{
		this.tabDonnees[row][col + 1] = value;
	}

    /**
	* Ajoute une ligne vide au tableau.
	*/
    public void ajouterLigne()
    {
        
        
        if(tabDonnees != null && tabDonnees.length != 0)
        {
            Object[][] nouveauTableau = new Object[tabDonnees.length + 1][tabDonnees[0].length];

            for (int i = 0; i < tabDonnees.length; i++)
            {
                for (int j = 0; j < tabDonnees[0].length; j++)
                {
                    nouveauTableau[i][j] = tabDonnees[i][j];
                }
            }

            for (int j = 0; j < tabDonnees[0].length; j++)
            {
                //if(tabEntetes[j].startsWith("h") || tabEntetes[j].startsWith("Coef"))
                //    nouveauTableau[tabDonnees.length][j] = 0;
                //else
                    nouveauTableau[tabDonnees.length][j] = "";
            }

            tabDonnees = nouveauTableau;
        }
        else
        {
            tabDonnees = new Object[1][tabEntetes.length];
        }
        fireTableDataChanged();
    }

    /**
	* Supprime la ligne sélectionnée.
	*/
    public void supprimerLigne(int index)
    {
        Object[][] nouveauTableau = new Object[tabDonnees.length - 1][tabDonnees[0].length];

        for (int i = 0, k = 0; i < tabDonnees.length; i++)
        {
            if (i != index)
            {
                for (int j = 0; j < tabDonnees[0].length; j++)
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
