package astre.modele.outils;

import java.util.Arrays;

import javax.swing.table.AbstractTableModel;

/**
 * Classe des données et d'affichage du tableau.
 * @author Matéo, Maxime, Maximilien
 */

public class ModeleTableau extends AbstractTableModel
{
	public static final char IGNORER   = 'I';
	public static final char MODIFIER  = 'M';
	public static final char SUPPRIMER = 'S';
	public static final char AJOUTER   = 'A';
	public static final char DEFAUT    = 'D';

	private String []  ensEntete;
	private Object []  ensDefaut;
	private boolean[]  ensModifiable;
	private Object[][] tabDonnees;
	private int        decalage;

	//permet de définir les lignes qui ne sont pas modifiables
	private boolean[]  ensEditableLigne;
	//TODO: pareil pour les colonnes un jour

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
	}

	/*---------------------------------------*/
	/*                GETTEUR                */
	/*---------------------------------------*/

	@Override public int               getColumnCount  (                  ) { return this.ensDefaut.length - decalage;          }
	@Override public int               getRowCount     (                  ) { return this.tabDonnees.length; }
	@Override public Object            getValueAt      ( int row, int col ) { return this.tabDonnees[row][col + decalage];      }
	@Override public Class<?>          getColumnClass  ( int c            ) { return this.ensDefaut[c + decalage].getClass ( ); }
	@Override public String            getColumnName   ( int c            ) { return this.ensEntete[c + decalage];              }

	public String     getNomColonne ( int col          ) { return this.ensEntete[col];        } //à supprimer ?
	public Object     getObjet      ( int lig, int col ) { return this.tabDonnees[lig][col];  }
	public Object[][] getDonnees    (                  ) { return this.tabDonnees;            }
	public String[]   getEntete     (                  ) { return this.ensEntete;             }


	/*---------------------------------------*/
	/*                TESTEUR                */
	/*---------------------------------------*/

	/**
	 * Donne la liste des cellules éditables.
	 */
	@Override
	public boolean isCellEditable ( int row, int col )
	{
		return this.ensModifiable[col+decalage];
	}

	public boolean estVide ( )
	{
		return this.tabDonnees == null || this.tabDonnees.length == 0;
	}

	/*---------------------------------------*/
	/*                SETTEUR                */
	/*---------------------------------------*/

	/**
	 * Permet d'éditer ou non toutes les colonnes.
	 */
	public void setEditable ( boolean modifiable )
	{
		for ( int i = decalage; i < this.ensEntete.length; i++ )
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
		// vérifier qu'il y a bien eu une modif
		if ( this.tabDonnees[row][col + decalage] != null && !this.tabDonnees[row][col + decalage].equals ( value ) )
		{
			this.tabDonnees[row][col + decalage] = value;

			this.modifierLigne        ( row );
			this.fireTableDataChanged (     );
		}
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
		int nbLignes = this.tabDonnees.length;

		// tester si la ligne précédente n'est pas déjà vide
		if ( this.tabDonnees.length == 0 || ! Arrays.equals ( this.tabDonnees[nbLignes-1], this.ensDefaut ) )
		{
			// initialiser un nouveau tableau avec la taille pour une ligne en PLUS

			this.ensDefaut[0] = AJOUTER;
			Object[][] nouveauTableau = Utilitaire.copier ( this.tabDonnees, this.ensDefaut );

			// mettre à jour les nouvelles valeurs
			this.majDonnees ( nouveauTableau );
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

		// mettre à jour les nouvelles valeurs
		this.majDonnees ( this.tabDonnees );
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

		// mettre à jour les nouvelles valeurs
		this.majDonnees ( this.tabDonnees );
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
