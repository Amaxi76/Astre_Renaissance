package astre.vue.outils;

import javax.swing.*;
import java.awt.Component;
import javax.swing.table.*;
import astre.modele.outils.ModeleTableau;

/** Classe représentant un tableau personnalisable.
 *  @author Matéo Sa, Maxime Lemoine et Maximilien Lesterlin
 *  @version : 1.0 - 11/12/2023
 *  @date : 06/12/2023
*/

//TODO: ajouter un booleen pour le constructeur sans titre de colonnes

public class Tableau extends JTable
{
	private static final int DECALAGE_DEFAUT = 0;
	
	private ModeleTableau modele;
	
	private Tableau ( String[] ensEntete, Object[] ensDefaut, boolean[] ensModifiable, int decalage, Object[][] tabDonnees )
	{
		this.modele = new ModeleTableau ( ensEntete, ensDefaut, ensModifiable, decalage, tabDonnees );
		//this.modele.setDecalage ( decal );
		
		this.setModel         ( this.modele                         );
		this.setSelectionMode ( ListSelectionModel.SINGLE_SELECTION );
		
		this.getTableHeader ( ).setReorderingAllowed ( false );
		//Permet au tableau de prendre toute la frame
		this.setAutoResizeMode ( JTable.AUTO_RESIZE_ALL_COLUMNS     );

		//Permet de ne pas mettre d'entetes
		boolean contientEntete = false;
		for ( String s : ensEntete )
			if ( !s.equals ( "" ) )
				contientEntete = true;
		
		if ( !contientEntete ) this.setTableHeader ( null );
	}

	public static Tableau initialiserTableau ( String[] ensEntete, Object[] ensDefaut, boolean[] ensModifiable, int decalage, Object[][] tabDonnees )
	{
		// ensDefaut est le seul paramètre obligatoire
		if ( ensDefaut == null ) return null;
		
		int nbColonnes = ensDefaut.length;
		
		// initialisation de tous les attributs
		if ( ensEntete == null )
		{
			ensEntete = new String[nbColonnes];
			
			for ( int i = 0; i < nbColonnes; i++ )
				ensEntete[i] = "";
		}

		if ( ensModifiable == null )
		{
			ensModifiable = new boolean[nbColonnes];
			for ( int i = 0; i < nbColonnes; i++ )
				ensModifiable[i] = true;
		}

		tabDonnees = ( tabDonnees == null ) ? new Object[0][ nbColonnes ] : tabDonnees;

		return new Tableau ( ensEntete, ensDefaut, ensModifiable, decalage , tabDonnees );
	}

	public static Tableau initialiserTableau ( String[] ensEntete, Object[] ensDefaut, boolean estModifiable, int decalage, Object[][] tabDonnees )
	{
		int nbColonnes = ensDefaut.length;

		if ( decalage < 0 || decalage > nbColonnes ) decalage = DECALAGE_DEFAUT; 
		
		boolean[] ensModifiable = new boolean[nbColonnes];
		for ( int i = 0; i < nbColonnes; i++ )
			ensModifiable[i] = estModifiable;

		return initialiserTableau ( ensEntete, ensDefaut, ensModifiable, decalage, tabDonnees );
	}

	public static Tableau initialiserTableau ( Object[] ensDefaut )
	{
		return Tableau.initialiserTableau ( null, ensDefaut, true, DECALAGE_DEFAUT, null );
	}
	
	/**
	* Ajuste la taille des colonnes.
	*/
	public void ajusterTailleColonnes ( )
	{
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
		//this.ajusterTailleColonnes ( );
	}
	
	/**
	* Supprime la ligne sélectionnée du tableau.
	*/
	public void supprimerLigne ( )
	{
		int selection = this.getSelectedRow ( );
		if ( selection != -1 )
		{
			this.modele.supprimerLigne(selection);
		}
		this.ajusterTailleColonnes ( );
	}

	/**
	* Permet de récupérer les données du modele
	*/
	public Object[][] getDonnees ( ) { return this.modele.getDonnees ( ); }

	public ModeleTableau getModeleTableau ( ) { return this.modele; }

	/**
	* Permet de modifier les données du modele
	*/
	public void modifDonnees ( Object[][] donnee )
	{
		this.modele.majDonnees ( donnee );
	}

	public void setDecalage ( int d ) { this.modele.setDecalage ( d ); }

	/**
	* Permet d'éditer ou non toutes les colonnes.
	*/
	public void setEditable ( boolean editable ) { this.modele.setEditable(editable); }

	/**
	* Permet de modifier la liste des cellules éditables avec les numéros de colonne choisi.
	*/
	public void setEditable ( boolean[] lst ) { this.modele.setEditable ( lst ); }

}