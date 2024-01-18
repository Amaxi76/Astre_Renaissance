package astre.modele.outils;

import java.util.Arrays;
import javax.swing.table.AbstractTableModel;

/**
 * Classe des données et d'affichage du tableau.
 * @version : 2.1 - 18/01/2024
 * @date : 06/12/2023
 * @author Matéo Sa, Maxime Lemoine, Maximilien Lesterlin
 */

public class ModeleTableau extends AbstractTableModel
{
	public static final char IGNORER   = 'I';
	public static final char MODIFIER  = 'M';
	public static final char SUPPRIMER = 'S';
	public static final char AJOUTER   = 'A';
	public static final char DEFAUT    = 'D';

	private String []   ensEntete;
	private Object []   ensValeursDefaut;
	private Boolean[]   ensColonnesModifiables;
	private Boolean[][] tabCellulesModifiables;
	private int         decalage;
	private Object[][]  tabDonnees;


	/*---------------------------------------*/
	/*              CONSTRUCTEUR             */
	/*---------------------------------------*/
	
	/**
	 * Constructeur par défaut
	 * Prends tout en paramètres
	 */
	public ModeleTableau ( String[] ensEntete, Object[] ensValeursDefaut, boolean[] ensColonnesModifiables, int decalage , Object[][] tabDonnees )
	{
		this.ensEntete              = ensEntete;
		this.ensValeursDefaut       = ensValeursDefaut;
		this.ensColonnesModifiables = Utilitaire.booleanToObjet ( ensColonnesModifiables );
		this.decalage               = decalage;
		this.tabDonnees             = tabDonnees;

		this.initTabCellulesModifiables ( );
	}


	/*---------------------------------------*/
	/*          OUTILS CONSTRUCTEUR          */
	/*---------------------------------------*/

	private void initTabCellulesModifiables ( )
	{
		this.tabCellulesModifiables = new Boolean[this.tabDonnees.length][this.ensColonnesModifiables.length];

		for ( int lig = 0; lig < this.tabDonnees.length; lig++ )
			for ( int col = 0; col < this.tabDonnees[lig].length; col++ )
				this.tabCellulesModifiables[lig][col] = this.ensColonnesModifiables[col];
	}


	/*---------------------------------------*/
	/*                GETTEUR                */
	/*---------------------------------------*/

	@Override public int               getColumnCount (                  ) { return this.ensValeursDefaut.length - decalage;          }
	@Override public int               getRowCount    (                  ) { return this.tabDonnees.length;                           }
	@Override public Object            getValueAt     ( int row, int col ) { return this.tabDonnees[row][col + decalage];             }
	@Override public Class<?>          getColumnClass ( int c            ) { return this.ensValeursDefaut[c + decalage].getClass ( ); }
	@Override public String            getColumnName  ( int c            ) { return this.ensEntete[c + decalage];                     } //remplace l'ancienne méthode "getNomColonne"

	public Object     getValeursDefaut ( int col          ) { return this.ensValeursDefaut[col];}
	public Object     getObjet         ( int lig, int col ) { return this.tabDonnees[lig][col]; }
	public Object[][] getDonnees       (                  ) { return this.tabDonnees;           } //FIXME: avec les JCombobox ça peut retourner des String ou des List<String>
	public String[]   getEntete        (                  ) { return this.ensEntete;            }
	public int        getDecalage      (                  ) { return this.decalage;             }



	/*---------------------------------------*/
	/*                TESTEUR                */
	/*---------------------------------------*/

	@Override public boolean isCellEditable ( int row, int col )
	{
		return this.tabCellulesModifiables[row][col + decalage]; 
	}

	public boolean estVide ( ) { return this.tabDonnees == null || this.tabDonnees.length == 0; }


	/*---------------------------------------*/
	/*                SETTEUR                */
	/*---------------------------------------*/

	//public void setEditable ( boolean modifiable );
	//public void setEditable ( boolean[] ensModifiable );

	/**
	 * Permet de modifier l'état d'une cellule visible
	 */
	public void setEditable ( int lig, int col, boolean modifiable ) { this.tabCellulesModifiables[lig][col + decalage] = modifiable; }

	/**
	 * Permet de modifier l'état d'une ligne
	 */
	public void setLigneEditable ( int lig, boolean modifiable )
	{
		for ( int col = 0; col < this.tabCellulesModifiables[lig].length; col++ )
			this.tabCellulesModifiables[lig][col] = modifiable;
	}

	/**
	 * Ajoute une valeur dans une case.
	 */
	@Override
	public void setValueAt ( Object value, int row, int col )
	{
		// vérifier qu'il y a bien eu une modif
		if ( this.tabDonnees[row][col + decalage] != null && !this.tabDonnees[row][col + decalage].equals ( value ) )
		{
			this.tabDonnees[row][col + decalage] = value;

			this.modifierLigne        ( row );
			this.fireTableDataChanged (     );
		}
	}

	/**
	 * Permet de cacher les premières colonnes
	 * @param d le nombre de colonnes à cacher
	 */
	public void setDecalage ( int d )
	{
		this.decalage = d;
		this.fireTableStructureChanged ( );
	}


	/*---------------------------------------*/
	/*                METHODES               */
	/*---------------------------------------*/

	/**
	 * Ajoute une ligne vide au tableau.
	 */
	public void ajouterLigne ( )
	{
		int nbLignes = this.tabDonnees.length;

		// tester si la ligne précédente n'est pas déjà vide
		if ( this.tabDonnees.length == 0 || ! Arrays.equals ( this.tabDonnees[nbLignes - 1], this.ensValeursDefaut ) )
		{
			// initialiser un nouveau tableau avec la taille pour une ligne en PLUS
			this.ensValeursDefaut[0] = AJOUTER;
			this.tabDonnees = Utilitaire.copier ( this.tabDonnees, this.ensValeursDefaut );
			this.tabCellulesModifiables = Utilitaire.tabObjetToTabType ( Utilitaire.copier ( this.tabCellulesModifiables, this.ensColonnesModifiables ), Boolean.class );

			this.fireTableRowsInserted ( nbLignes, nbLignes );
		}
	}

	/**
	 * Modiie la ligne sélectionnée.
	 */
	public void modifierLigne ( int index )
	{
		char action = ( char ) this.tabDonnees[index][0];

		// changer le type de donnée
		if ( action != AJOUTER && action !=  SUPPRIMER && action != IGNORER )
			this.tabDonnees[index][0] = MODIFIER;

		this.fireTableRowsUpdated ( index, index );
	}

	/**
	 * Supprime la ligne sélectionnée.
	 */
	public void supprimerLigne ( int index )
	{
		char action = ( char ) this.tabDonnees[index][0];

		// changer le type de donnée
		if ( action != AJOUTER && action != IGNORER )
			this.tabDonnees[index][0] = SUPPRIMER;
		else
			this.tabDonnees[index][0] = IGNORER;

		this.fireTableRowsDeleted ( index, index ); //TODO: à modifier avec le rowSorter
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
			for ( int j=0; j < tabDonnees[0].length; j++ )
				sRet += tabDonnees[i][j] + " ";

			sRet += "\n";
		}

		return sRet;
	}
}
