package astre.vue.outils;

import javax.swing.table.AbstractTableModel;

/**
 * Classe des données et d'affichage du tableau.
 * @author Matéo
 */
public class ModeleTableau extends AbstractTableModel 
{
    private String[]   tabEntetes;
	private Object[][] tabDonnees;
	private boolean    estModifiable=true;

    //Constructeur présent que pour les test
    /*public ModeleTableau ( String[] tabEntetes, boolean estModifiable )
    {
    	this.estModifiable = estModifiable;
        this.tabEntetes = tabEntetes;
		//this.tabEntetes = new String[] { "Catégorie", "Nom", "Prénom", "hServ", "hMax", "Coef TP", "S1", "S3", "S5", "sTot", "S2", "S4", "S6", "sTot", "Total" };
        
        this.tabDonnees = new Object[1][this.tabEntetes.length];
        for(int cpt=0; cpt < tabDonnees[0].length; cpt++)
        {
            tabDonnees[0][cpt] = "test";//mettre les trucs de la bd
        }
    }*/
    
    public ModeleTableau ( String[] tabEntetes, Object[][] tabDonnees, boolean estModifiable )
    {
    	this.estModifiable = estModifiable;
        this.tabEntetes = tabEntetes;
        this.tabDonnees = tabDonnees;
    }
    
    //si la méthode est en anglais c'est qu'elle est obligatoire.
    public int    getColumnCount()                 { return this.tabEntetes.length;      }
	public int    getRowCount   ()                 { return this.tabDonnees.length;      }
	public Object getValueAt    (int row, int col) { return this.tabDonnees[row][col];   }
	public String getNomColonne (int col)          { return this.tabEntetes[col];        }
	public Class  getColumnClass(int c)            { return getValueAt(0, c).getClass(); }
	public String getColumnName (int c)             { return this.tabEntetes[c];           }

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

    public Object[][] getDonnees()
    {
        return this.tabDonnees;
    }
}
