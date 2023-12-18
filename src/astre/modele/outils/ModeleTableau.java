package astre.modele.outils;

import java.util.Arrays;

import javax.swing.table.AbstractTableModel;

/**
 * Classe des données et d'affichage du tableau.
 * @author Matéo, Maxime, Maximilien
 */

public class ModeleTableau extends AbstractTableModel
{
	private String[]   ensEntete;
	private Object[]   ensDefaut;
	private boolean[]  ensModifiable;
	private Object[][] tabDonnees;
	private int        decalage;


	/*---------------------------------------*/
	/*              CONSTRUCTEUR             */
	/*---------------------------------------*/ 

	/**
	 * Constructeur par défaut
	 * Prends tout en paramètres
	 */
	public ModeleTableau ( String[] ensEntete, Object[] ensDefaut, boolean[] ensModifiable, int decalage , Object[][] tabDonnees )
	{
		this.ensEntete     = ensEntete;
		this.ensDefaut     = ensDefaut;
		this.ensModifiable = ensModifiable;
		this.decalage      = decalage;
		this.tabDonnees    = tabDonnees;

		// mettre à jour les nouvelles valeurs
		this.majDonnees ( tabDonnees );

		// affichage des tableaux pour les tests
		// System.out.println(this.toString());
	}
	
	/*---------------------------------------*/
	/*                GETTEUR                */
	/*---------------------------------------*/ 

	@Override public int      getColumnCount (                  ) { return this.ensEntete.length - decalage;          }
	@Override public int      getRowCount    (                  ) { return this.tabDonnees.length;                    }
	@Override public Object   getValueAt     ( int row, int col ) { return this.tabDonnees[row][col + decalage];      }
	@Override public Class<?> getColumnClass ( int c            ) { return this.ensDefaut[c + decalage].getClass ( ); }
	@Override public String   getColumnName  ( int c            ) { return this.ensEntete[c + decalage];              }

	public String     getNomColonne ( int col          ) { return this.ensEntete[col]; }
	public Object[][] getDonnees    (                  ) { return this.tabDonnees;     }


	/*---------------------------------------*/
	/*                TESTEUR                */
	/*---------------------------------------*/

	/**
	 * Donne la liste des cellules éditables.
	 */
	@Override
	public boolean isCellEditable ( int row, int col )
	{
		return this.ensModifiable[col+decalage] == true;
	}

	/*---------------------------------------*/
	/*                SETTEUR                */
	/*---------------------------------------*/

	/**
	 * Permet d'éditer ou non toutes les colonnes.
	 */
	public void setEditable ( boolean modifiable )
	{
		for ( int i=decalage; i<this.ensEntete.length; i++ )
			this.ensModifiable[ i ] = modifiable;
	}

	/**
	 * Permet de modifier la liste des cellules éditables
	 */
	public void setEditable ( boolean[] ensModifiable )
	{
		this.ensModifiable = ensModifiable;
	}
	
	/**
	 * Ajoute une valeur dans une case.
	 */
	@Override
	public void setValueAt ( Object value, int row, int col )
	{
		this.tabDonnees[row][col + decalage] = value;
		this.fireTableDataChanged ( );
	}

	/**
	 * Permet de cacher une colonne
	 */
	public void setDecalage ( int d )
	{
		this.decalage = d;
		this.fireTableDataChanged ( );
	}

	/*---------------------------------------*/
	/*                METHODES               */
	/*---------------------------------------*/ 

	/**
	 * Ajoute une ligne vide au tableau.
	 */
	public void ajouterLigne ( )
	{
		int nbLignes   = this.tabDonnees.length;

		// tester si la ligne précédente n'est pas déjà vide
		if ( this.tabDonnees.length == 0 || ! Arrays.equals ( this.tabDonnees[nbLignes-1], this.ensDefaut ) )
		{
			// initialiser un nouveau tableau avec la taille pour une ligne en PLUS
			Object[][] nouveauTableau = TableauUtilitaire.copier ( this.tabDonnees, this.ensDefaut );

			// mettre à jour les nouvelles valeurs
			this.majDonnees ( nouveauTableau );
		}
	}

	/**
	 * Supprime la ligne sélectionnée.
	 */
	public void supprimerLigne ( int index )
	{
		// initialiser un nouveau tableau avec la taille pour une ligne en MOINS
		Object[][] nouveauTableau = TableauUtilitaire.copier ( this.tabDonnees, index );

		// mettre à jour les nouvelles valeurs
		this.majDonnees ( nouveauTableau );
	}

	/**
	 * Permet de modifier les données du modele et le mettre à jour
	 */
	public void majDonnees ( Object[][] donnee )
	{
		this.tabDonnees = donnee;
		this.fireTableDataChanged ( );
	}

	@Override
	public String toString ( )
	{
		String sRet = "";

		for ( int i=0; i<tabDonnees.length; i++ )
		{
			for ( int j=0; j<tabDonnees[0].length; j++ )
				sRet += tabDonnees[i][j] + " ";

			sRet += "\n";
		}

		return sRet;
	}
}
