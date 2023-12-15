package astre.vue.outils;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

/**
 * Classe des données et d'affichage du tableau.
 * @author Matéo
 */
public class ModeleTableau extends AbstractTableModel 
{
    private String[]   tabEntetes;
	private Object[][] tabDonnees;

    private int        decalage;
    private ArrayList<Integer> modifiable;

    public ModeleTableau ( String[] tabEntetes, Object[][] tabDonnees )
    {
        this.tabEntetes = tabEntetes;

        this.decalage = 0;
        this.modifiable = new ArrayList<Integer>();
        
        if(tabDonnees == null)
        {
            this.tabDonnees = new Object[0][tabEntetes.length];
            ajouterLigne();
        }
        else
            this.tabDonnees = tabDonnees;

        //ecriture des tableau pour test
        /*for(int i=0; i<tabDonnees.length; i++)
        {
            for(int j=0; j<tabDonnees[0].length; j++)
            {
                System.out.print(tabDonnees[i][j] + " ");
            }
            System.out.println();
        }*/
    }
    
    //si la méthode est en anglais c'est qu'elle est obligatoire.
    public int    getColumnCount ( )                  { return this.tabEntetes.length - decalage;    }
	public int    getRowCount    ( )                  { return this.tabDonnees.length;               }
	public Object getValueAt     ( int row, int col ) { return this.tabDonnees[row][col + decalage]; }
	public String getNomColonne  ( int col )          { return this.tabEntetes[col];                 }
	public Class  getColumnClass ( int c )            { return getValueAt ( 0, c ).getClass ( );     }
	public String getColumnName  ( int c )            { return this.tabEntetes[c + decalage];        }

    /**
	* Donne la liste des cellules éditables.
	*/
	public boolean isCellEditable ( int row, int col )
	{
		if ( modifiable.contains(col) )
            return true;
        
        return false;
	}
	
    /**
	* Permet d'éditer ou non toutes les colonnes.
	*/
	public void setEditable ( boolean editable )
	{
		modifiable.clear ( );
        if ( editable )
        {
            for ( int cpt=0; cpt < tabEntetes.length; cpt++ )
            {
                modifiable.add ( cpt );
            }
        }
	}

    /**
	* Permet de modifier la liste des cellules éditables avec les numéros de colonne choisi.
	*/
    public void setEditable ( int[] lst )
    {
        modifiable.clear ( );
        for ( int i : lst )
        {
            if ( !modifiable.contains ( i ) )
            {
                modifiable.add ( i );
            }
        }
    }

    /**
	* Ajoute une valeur dans une case.
	*/
	public void setValueAt ( Object value, int row, int col )
	{
		this.tabDonnees[row][col + decalage] = value;
	}

    /**
	* Permet de cacher une colonne
	*/
	public void setDecalage ( int d )
	{
		this.decalage = d;
        //System.out.println(d);
        fireTableDataChanged ( );
	}

    /**
	* Ajoute une ligne vide au tableau.
	*/
    public void ajouterLigne ( )
    {
        if( tabDonnees.length < 1 )
        {
            tabDonnees = new Object[1][tabEntetes.length];
            for ( int j = 0; j < tabDonnees[0].length; j++ )
            {
                tabDonnees[0][j] = ""; //TODO: comment faire pour savoir la classe de la case ?
            }
        }
        else
        {
            Object[][] nouveauTableau = new Object[tabDonnees.length + 1][tabDonnees[0].length];
            for ( int i = 0; i < tabDonnees.length; i++ )
            {
                for ( int j = 0; j < tabDonnees[0].length; j++ )
                {
                    nouveauTableau[i][j] = tabDonnees[i][j];
                }
            }

            for ( int j = 0; j < tabDonnees[0].length; j++ )
            {
                if ( tabDonnees[0][j].getClass ( ) == String.class )
                {
                    nouveauTableau[tabDonnees.length][j] = "";
                }

                if ( tabDonnees[0][j].getClass ( ) == Integer.class || tabDonnees[0][j].getClass ( ) == Double.class )
                {
                    nouveauTableau[tabDonnees.length][j] = 0;
                }
            }
            tabDonnees = nouveauTableau;
        }
        fireTableDataChanged ( );
    }

    /**
	* Supprime la ligne sélectionnée.
	*/
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
        fireTableDataChanged ( );
    }

    /**
	* Permet de récupérer les données du modele
	*/
    public Object[][] getDonnees ( )
    {
        return this.tabDonnees;
    }

    /**
	* Permet de modifier les données du modele
	*/
    public void modifDonnees ( Object[][] donnee )
    {
        this.tabDonnees = donnee;
        fireTableDataChanged ( );
    }
}
