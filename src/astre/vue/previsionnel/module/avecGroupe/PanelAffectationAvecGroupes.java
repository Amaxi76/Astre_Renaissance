package astre.vue.previsionnel.module.avecGroupe;

import java.util.Map;

import javax.swing.JComboBox;

import astre.Controleur;

import astre.modele.elements.Contrat;
import astre.modele.elements.Heure;
import astre.modele.elements.Intervenant;
import astre.vue.previsionnel.module.*;
import astre.vue.rendus.OperationRenduTableauIntervient;

/** Classe PanelAffectationAvecGroupe
  * @author : Maxime Lemoine, Maximilien Lesterlin
  * @version : 1.0 - 10/01/2024
  * @date : 10/01/2024
  */
public class PanelAffectationAvecGroupes extends AbstractPanelAffectation
{
	
	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/

	public PanelAffectationAvecGroupes ( Controleur ctrl, FrameModule frmModule, String[] ensIntituleTypeHeure, String[] ensEntete, Object[] ensTypeDefaut, boolean[] ensModifiable )
	{
		super ( ctrl, frmModule, ensIntituleTypeHeure, ensEntete, ensTypeDefaut, ensModifiable );

		super.colNbSemaine       = 4;
		super.colNbHeureOuGroupe = 5;
		super.colEqtd            = 6;
		super.colCommentaire     = 7;

		// Permet de gérer les différents rendus des cellulese (Notamment pur nb Semaine et nb Groupe|nb Heure)
		for ( int i = 0; i < this.tableau.getColumnCount ( ); i++ )
			super.tableau.getColumnModel ( ).getColumn ( i ).setCellRenderer ( new OperationRenduTableauIntervient ( true ) );

	}

	/* ------------------------- */
	/*  Méthodes initialisation  */
	/* ------------------------- */

	@Override
	protected JComboBox<String> creerCbHeure ( )
	{
		JComboBox<String> cb = super.creerCbHeure ( );

		cb.addItem ( "HP" );

		return cb;
	}
	
	@Override
	protected Map<String, Double> creerMapIntitule ( )
	{
		Map<String, Double> map = super.creerMapIntitule ( );

		map.put ( "HP", 0.0 );

		return map;
	}

	/* ------------------------- */
	/*          Méthodes         */
	/* ------------------------- */

	/**
	 * Méthode qui calcul l'équivalent TD pour le tableau
	 */
	@Override
	public void majTotEqtd ( )
	{
		
		for ( int ligne = 0; ligne < this.tableau.getDonnees ( ).length; ligne++ )
		{
			double valeur = 0.0;
			if ( ( int ) this.tableau.getDonnees ( ) [ligne][colIdIntervenant] != 0 )
			{
				Heure       h = this.ctrl.getHeure       (         this.tableau.getDonnees ( ) [ligne][colHeure        ] .toString ( ) );
				Intervenant i = this.ctrl.getIntervenant ( ( int ) this.tableau.getDonnees ( ) [ligne][colIdIntervenant]               );

				if ( i != null && ( char ) this.tableau.getDonnees ( ) [ligne][colModif] != 'S' && ( char ) this.tableau.getDonnees ( ) [ligne][colModif] != 'I' )
				{
					Contrat     c = i.getContrat ( );

					double coefHeure       = h.getCoefTd ( );
					double coefIntervenant = h.getNom ( ).equals ( "TP" ) ? c.getRatioTP ( ) : 1;
					double nbSemaine       = Double.parseDouble ( this.tableau.getDonnees ( ) [ligne][colNbSemaine].toString ( ) );
					double nbHeure         = h.getNom ( ).equals ( "HP" ) ? 1 : this.frmModule.getNbHeureSemaine ( h.getNom ( ) );
					double nbGroupe        = Double.parseDouble ( this.tableau.getDonnees ( ) [ligne][colNbHeureOuGroupe].toString ( ) );

					valeur = coefHeure * coefIntervenant * nbSemaine * nbHeure * nbGroupe;
				}
				
				this.tableau.setValueAt ( valeur, ligne, colEqtd - DECALAGE_TABLEAU );
			}
		}
	}
}