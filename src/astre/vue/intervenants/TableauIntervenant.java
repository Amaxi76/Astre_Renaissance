package astre.vue.intervenants;

import javax.swing.*;
import java.awt.Component;
import javax.swing.table.*;

import astre.Controleur;
import astre.modele.elements.Contrat;
import astre.vue.outils.ModeleTableau;

/** Classe représentant un tableau pour Intervenant.
 *  @author Matéo
 *  @version : 1.1 - 13/12/2023
 *  @date : 06/12/2023
*/

//TODO: simplifier les constructeurs, en les appelant les uns les autres
//TODO: ajouter un booleen pour le constructeur sans titre de colonnes
//TODO: nettoyer les constructeurs qui semblent inutiles (utilisés avant pour les tests avec tableaux vides)

public class TableauIntervenant extends JTable
{
	private String[] nomColonnes;
	private ModeleTableau modele;

	Controleur ctrl;
	
	public TableauIntervenant ( String[] nomColonnes, Object[][] tabDonnees, Controleur ctrl )
	{
		this.ctrl = ctrl;
		
		this.nomColonnes = nomColonnes;
		//this.modele = new ModeleTableauIntervenant(this.nomColonnes, tabDonnees);
		this.modele = new ModeleTableau(nomColonnes, tabDonnees);
		this.modele.setEditable( new int[] {0, 1, 2, 3, 4} );
		this.modele.setDecalage(0);
		
		this.setModel ( this.modele );
		this.setSelectionMode ( ListSelectionModel.SINGLE_SELECTION );
		
		//Permet au tableau de prendre toute la frame
		this.setAutoResizeMode ( JTable.AUTO_RESIZE_ALL_COLUMNS );

		//Permet d'enpecher de déplacer les cases (je crois)(ne marche pas vraiment)
		this.setDragEnabled ( false );

		//Permet la création de la comboBox
		JComboBox<String> cbEdit = new JComboBox<>();
		for ( Contrat c : this.ctrl.getContrats ( ) )
		{
			cbEdit.addItem ( c.getNom ( ) );
		}
		this.getColumnModel ( ).getColumn (0 ).setCellEditor ( new DefaultCellEditor ( cbEdit ) );
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

	/**
	* Permet de récupérer les données du modele
	*/
	public Object[][] getDonnees ( )
    {
        return this.modele.getDonnees ( );
    }

	/**
	* Permet de modifier les données du modele
	*/
	public void modifDonnees ( Object[][] donnee )
	{
		this.modele.modifDonnees ( donnee );
	}
}
