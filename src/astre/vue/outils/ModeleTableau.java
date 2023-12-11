package astre.vue.outils;

import javax.swing.table.AbstractTableModel;

/** Classe des données et d'affichage du tableau.
  * @author : Maxime Lemoine
  * @version : 1.0 - 11/12/2023
  * @date : 06/12/2023
 */
public class ModeleTableau extends AbstractTableModel 
{
    private String[]   tabEntetes;
	private Object[][] tabDonnees;

    public ModeleTableau(String[] tabEntetes)
    {
        this.tabEntetes = tabEntetes;
        this.tabDonnees = new Object[1][this.tabEntetes.length];

        for(int cpt=0; cpt < tabDonnees[0].length; cpt++)
        {
            tabDonnees[0][cpt] = "aa";
        }
    }
    
    //si la méthode est en anglais c'est qu'elle est obligatoire.
    public int    getColumnCount()                 { return this.tabEntetes.length;      }
	public int    getRowCount   ()                 { return this.tabDonnees.length;      }
	public Object getValueAt    (int row, int col) { return this.tabDonnees[row][col];   }
	public String getNomColonne (int col)          { return this.tabEntetes[col];        }

    /**
	* Donne la liste des cellules éditables.
	*/
	public boolean isCellEditable(int row, int col)
	{
		return true;
	}

    /**
	* Ajoute une valeur dans une case.
	*/
	public void setValueAt(Object value, int row, int col)
	{
		this.tabDonnees[row][col] = value;
	}

    /**
	* Ajoute une ligne vide au tableau.
	*/
    public void ajouterLigne()
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
            nouveauTableau[tabDonnees.length][j] = "";
        }

        tabDonnees = nouveauTableau;
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
}
