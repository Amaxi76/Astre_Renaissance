package astre.vue.outils;

import javax.swing.*;
import java.awt.Component;
import java.util.List;
import javax.swing.table.*;

/** 
 * Classe représentant un tableau personnalisable.
 * @author Maxime Lemoine et Maximilien Lesterlin
 * @version : 3.0 - 18/01/2024
 * @date : 06/12/2023
 */

//TODO: adapter les largeurs des colonnes en fonction des tailles des cellules et des entetes et des types de données ? (String plus grand que int ?)

public final class Tableau extends JTable
{
	private static final int DECALAGE_DEFAUT = 0;
	private ModeleTableau modele;

	/*---------------------------------------*/
	/*             CONSTRUCTEUR              */
	/*---------------------------------------*/

	/**
	 * @param ensEntete : entête des colonnes (facultatif)
	 * @param ensDefaut : paramètre qui donne les données par défaut à utiliser (obligatoire)
	 * @param ensModifiable : tableau de booléens correspondant aux colonnes modifiables (facultatif)
	 * @param decalage : nombre de colonnes à ne pas afficher à partir de la gauche (facultatif)
	 * @param tabDonnees : tableau de données à afficher (facultatif)
	 * @notice Les différents tableaux doivent avoir le même nombre de colonne
	 */
	private Tableau ( String[] ensEntete, Object[] ensDefaut, boolean[] ensModifiable, int decalage, Object[][] tabDonnees )
	{
		// Modèle
		this.modele = new ModeleTableau ( ensEntete, ensDefaut, ensModifiable, decalage, tabDonnees );
		super.setModel ( this.modele );

		// Entête
		if ( !this.possedeEntete(ensEntete) )
			super.setTableHeader ( null );
		else
			super.getTableHeader ( ).setReorderingAllowed ( false );

		// Paramètres
		super.setSelectionMode ( ListSelectionModel.SINGLE_SELECTION );
		super.setAutoResizeMode ( JTable.AUTO_RESIZE_ALL_COLUMNS     ); //Permet au tableau de prendre toute la frame

		//TODO: méga stylé mais problème de cohérence entre métier et ihm mais sinon au niveau BD ça devrait passer sans problème
		//super.setAutoCreateRowSorter ( true ); //Permet de trier les colonnes

		// Affichage
		this.majRendus ( decalage );
		this.ajusterTailleColonnes ( );
	}

	/*---------------------------------------*/
	/*          OUTILS CONSTRUCTEUR          */
	/*---------------------------------------*/

	/**
	 * Test si le tableau possède un entête
	 * @param ensEntete
	 * @return true si l'entête n'est pas vide
	 */
	private boolean possedeEntete ( String[] ensEntete )
	{
		for ( String s : ensEntete )
		{
			boolean enteteColonneVide = ( s == null || s.equals ( "" ) );
			if ( !enteteColonneVide )
				return true;
		}
		return false;
	}


	/*---------------------------------------*/
	/*                FACTORY                */
	/*---------------------------------------*/

	/**
	 * Factory pour créer un tableau avec tous ses paramètres
	 * @notice le seul paramètre obligatoire est ensDefaut
	 */
	public static Tableau initialiserTableau ( String[] ensEntete, Object[] ensDefaut, boolean[] ensModifiable, int decalage, Object[][] tabDonnees )
	{
		if ( ensDefaut == null )
			throw new IllegalArgumentException ( "Paramètre ensDefaut : obligatoire et non null" );
		
		int nbColonnes = ensDefaut.length;

		// initialisation des entetes si null
		if ( ensEntete == null )
		{
			ensEntete = new String[nbColonnes];

			for ( int i = 0; i < nbColonnes; i++ )
				ensEntete[i] = "";
		}

		// initialisation du tableau modifiable
		if ( ensModifiable == null )
		{
			ensModifiable = new boolean[nbColonnes];
			for ( int i = 0; i < nbColonnes; i++ )
				ensModifiable[i] = true;
		}

		// correction du decalage
		if ( decalage < 0 || decalage > nbColonnes )
			decalage = DECALAGE_DEFAUT;

		// initialisation des données à vide
		if ( tabDonnees == null || tabDonnees.length == 0 )
			tabDonnees = new Object[0][nbColonnes];

		// vérifier que les tableaux ont les mêmes longueurs
		boolean enteteOk     = ensEntete.length     == nbColonnes;
		boolean modifiableOk = ensModifiable.length == nbColonnes;
		boolean donneesOk    = tabDonnees != null && (tabDonnees.length == 0 || tabDonnees[0].length == nbColonnes); //pas changer condition pcq la galère sinon

		if ( ! ( enteteOk && modifiableOk && donneesOk ) )
			throw new IllegalArgumentException ( "Les paramètres de construction du tableau ne correspondent pas." );

		// construction du tableau
		return new Tableau ( ensEntete, ensDefaut, ensModifiable, decalage , tabDonnees );
	}

	/**
	 * Factory pour créer un tableau en précisant si toutes les colonnes sont modifiables ou non
	 * @notice le seul paramètre obligatoire est ensDefaut
	 */
	public static Tableau initialiserTableau ( String[] ensEntete, Object[] ensDefaut, boolean estModifiable, int decalage, Object[][] tabDonnees )
	{
		int nbColonnes = ensDefaut.length;

		// correction du decalage
		if ( decalage < 0 || decalage > nbColonnes )
			decalage = DECALAGE_DEFAUT;

		// initialisation du tableau modifiable
		boolean[] ensModifiable = new boolean[nbColonnes];
		for ( int i = 0; i < nbColonnes; i++ )
			ensModifiable[i] = estModifiable;

		return initialiserTableau ( ensEntete, ensDefaut, ensModifiable, decalage, tabDonnees );
	}

	/**
	 * Factory pour créer un tableau le plus simple possible
	 */
	public static Tableau initialiserTableau ( Object[] ensDefaut )
	{
		return Tableau.initialiserTableau ( null, ensDefaut, true, DECALAGE_DEFAUT, null );
	}


	/*---------------------------------------*/
	/*                TESTEUR                */
	/*---------------------------------------*/

	public boolean estVide ( ) { return this.modele.estVide ( ); }


	/*---------------------------------------*/
	/*                GETTEUR                */
	/*---------------------------------------*/

	//@Override public ModeleTableau getModel ( ) { return ( ModeleTableau ) super.getModel ( ); } //ATTENTION: ANCIEN NOM : getModeleTableau

	public Object[][] getDonnees ( ) { return this.modele.getDonnees ( ); }
	@Override public Object getValueAt ( int lig, int col ) { return this.modele.getValueAt(lig, col); }
	public Object getObjet ( int lig, int col ) { return this.modele.getObjet(lig, col); } //retourne la valeur sans le décalage


	/*---------------------------------------*/
	/*                SETTEUR                */
	/*---------------------------------------*/

	/**
	 * Modifie le décalage du tableau
	 * @param d nombre de colonnes à ne pas afficher à partir de la gauche
	 * @notice le décalage ne peut pas être négatif
	 */
	public void setDecalage ( int d ) //REMARQUE : ne pas changer pcq sinon la galère
	{
		if ( d < 0 || d > this.modele.getColumnCount() )
			d = DECALAGE_DEFAUT;
		this.modele.setDecalage ( d );
		this.majRendus ( d );
	}

	public void setDonnees ( Object[][] donnees ) //ATTENTION : ANCIEN NOM : modifDonnees
	{
		this.modele.majDonnees ( donnees );
		this.ajusterTailleColonnes ( );
	}

	//public void setEditable ( boolean editable                   ) { this.modele.setEditable ( editable           ); }
	//public void setEditable ( boolean[] lst                      ) { this.modele.setEditable ( lst                ); }
	public void setEditable      ( int col, int lig, boolean editable ) { this.modele.setEditable ( col, lig, editable ); }
	public void setLigneEditable ( int lig, boolean editable          ) { this.modele.setLigneEditable ( lig, editable ); }


	/*---------------------------------------*/
	/*                METHODES               */
	/*---------------------------------------*/

	/**
	 * Met à jour les rendus des cellules
	 * @param decalage
	 */
	private void majRendus ( int decalage )
	{
		for ( int i = 0; i < this.getColumnCount ( ); i++ )
		{
			TableColumn colonne = this.getColumnModel ( ).getColumn ( i );
			colonne.setCellRenderer ( new RenduCelluleTableau ( ) ); //rendu dans le tableau

			if ( this.modele.getValeursDefaut ( i + decalage ) instanceof List )
				colonne.setCellEditor ( new EditionComboBoxCelluleTableau ( ( List ) this.modele.getValeursDefaut( i + decalage ) ) ); //rendu dans l'edition
		}
	}

	/**
	 * Ajuste la taille des colonnes.
	 */
	//TODO: réviser cette méthode
	public void ajusterTailleColonnes ( )
	{
		if ( this.estVide ( ) ) return;
		
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

	/**
	* Ajoute une ligne vide au tableau.
	*/
	public void ajouterLigne ( )
	{
		this.modele.ajouterLigne ( );
		this.ajusterTailleColonnes ( );
	}

	/**
	* Supprime la ligne sélectionnée du tableau.
	*/
	public void supprimerLigne ( )
	{
		int selection = this.getSelectedRow ( );
		if ( selection != -1 )
		{
			this.modele.supprimerLigne ( selection );
		}
		this.ajusterTailleColonnes ( );
	}
}
