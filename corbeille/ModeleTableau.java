package astre.vue.outils;

import java.util.ArrayList;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

/**
 * Classe des données et d'affichage du tableau.
 * @author Matéo, Maximilien, Maxime
 */
public class ModeleTableau extends AbstractTableModel 
{
	private String[]   ensEntete;
	private Object[]   ensDefaut;
	private boolean[]  ensModifiable;
	private Object[][] tabDonnees;

	private int        decalage;

	/*public ModeleTableau ( String[] ensEntete, Object[] ensDefaut, boolean modifiable ,Object[][] tabDonnees )
	{
		...
		this ( tabEntetes, tabDefaut,  tabDonnees );
	}*/

	public ModeleTableau ( String[] ensEntete, Object[] ensDefaut, boolean[] ensModifiable ,Object[][] tabDonnees )
	{
		this.ensEntete     = ensEntete;
		this.ensDefaut     = ensDefaut;
		this.ensModifiable = ensModifiable;
		
		this.decalage      = 0;
		
		if ( tabDonnees == null )
		{
			this.tabDonnees = new Object[0][ensEntete.length];
			this.ajouterLigne ( );
		}
		else
		{
			this.tabDonnees    = tabDonnees;
		}

		//ecriture des tableau pour test
		//System.out.println ( this.toString() );
	}
	
	//TODO: revoir
	//si la méthode est en anglais c'est qu'elle est obligatoire.
	public int      getColumnCount ( )                  { return this.tabEntetes.length - decalage;    }
	public int      getRowCount    ( )                  { return this.tabDonnees.length;               }
	public Object   getValueAt     ( int row, int col ) { return this.tabDonnees[row][col + decalage]; }
	public String   getNomColonne  ( int col )          { return this.tabEntetes[col];                 }
	public Class<?> getColumnClass ( int c )            { return getValueAt ( 0, c ).getClass ( );     }
	public String   getColumnName  ( int c )            { return this.tabEntetes[c + decalage];        }

	//TODO: revoir
	/**
	* Donne la liste des cellules éditables.
	*/
	public boolean isCellEditable ( int row, int col )
	{
		if ( modifiable.contains(col) )
			return true;
		
		return false;
	}
	
	//TODO: revoir
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

	//TODO: revoir
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

	//TODO: revoir
	/**
	* Ajoute une valeur dans une case.
	*/
	public void setValueAt ( Object value, int row, int col )
	{
		this.tabDonnees[row][col + decalage] = value;
	}

	//TODO: revoir
	/**
	* Permet de cacher une colonne
	*/
	public void setDecalage ( int d )
	{
		this.decalage = d;
		//System.out.println(d);
		fireTableDataChanged ( );
	}

	//TODO: revoir (Maxime) - normalement c'est bon
	//TODO: faire des copies profondes des listes (à voir)
	/**
	* Ajoute une ligne vide au tableau.
	*/
	public void ajouterLigne ( )
	{
		int nbLignes   = this.tabDonnees.length;
		int nbColonnes = this.ensEntete.length;

		// tester si la ligne précédente est déjà vide
		if ( this.tabDonnees[nbLignes-1] == this.ensDefaut )
		{
			return;
		}

		// initialiser un nouveau tableau avec la taille pour une ligne en plus
		Object[][] nouveauTableau = new Object[nbLignes + 1][nbColonnes];

		// ajouter dans ce tableau les anciennes données
		for ( int i = 0; i < nbLignes; i++ )
		{
			for ( int j = 0; j < nbColonnes; j++ )
			{
				nouveauTableau[i][j] = this.tabDonnees[i][j];
			}
		}

		// ajouter les valeurs par défaut d'une nouvelle ligne
		for ( int j = 0; j < nbColonnes; j++ )
		{
			nouveauTableau[nbLignes][j] = this.ensDefaut;
		}

		// mettre à jour les nouvelles valeurs
		this.tabDonnees = nouveauTableau;
		this.fireTableDataChanged ( );

		// TODO: à supprimer si le code précédent fonctionne
		/*if ( tabDonnees.length < 1 )
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
			fireTableDataChanged ( );
		}*/
		
	}

	//TODO: revoir
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

	//TODO: revoir
	/**
	* Permet de récupérer les données du modele
	*/
	public Object[][] getDonnees ( )
	{
		return this.tabDonnees;
	}

	//TODO: revoir
	/**
	* Permet de modifier les données du modele
	*/
	public void modifDonnees ( Object[][] donnee )
	{
		this.tabDonnees = donnee;
		fireTableDataChanged ( );
	}

	public String toString ( )
	{
		String sRet = "";

		for ( int i=0; i<tabDonnees.length; i++ )
		{
			for ( int j=0; j<tabDonnees[0].length; j++ )
			{
				sRet += tabDonnees[i][j] + " ";
			}
			sRet += "\n";
		}

		return sRet;
	}
}
