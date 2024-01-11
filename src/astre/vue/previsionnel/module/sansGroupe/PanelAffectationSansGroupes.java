package astre.vue.previsionnel.module.sansGroupe;

import astre.Controleur;

import astre.modele.elements.Contrat;
import astre.modele.elements.Heure;
import astre.modele.elements.Intervenant;
import astre.modele.outils.Utilitaire;
import astre.vue.previsionnel.module.*;

/** Classe PanelAffectationSansGroupe
  * @author : Maxime Lemoine, Maximilien Lesterlin
  * @version : 1.0 - 10/01/2024
  * @date : 10/01/2024
  */
public class PanelAffectationSansGroupes extends AbstractPanelAffectation
{

	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/

	public PanelAffectationSansGroupes ( Controleur ctrl, FrameModule frmModule, String[] ensIntituleTypeHeure, String[] ensEntete, Object[] ensTypeDefaut, boolean[] ensModifiable )
	{
		super ( ctrl, frmModule, ensIntituleTypeHeure, ensEntete, ensTypeDefaut, ensModifiable );

		super.colNbHeureOuGroupe = 4;
		super.colEqtd            = 5;
		super.colCommentaire	 = 6;
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
				Heure       h = this.ctrl.getHeure                          ( this.tableau.getDonnees ( ) [ligne][colHeure]        .toString ( )   );
				Intervenant i = this.ctrl.getIntervenant ( Integer.parseInt ( this.tableau.getDonnees ( ) [ligne][colIdIntervenant].toString ( ) ) );
				
				if ( i != null && ( char ) this.tableau.getDonnees ( ) [ligne][colModif] != 'S' && ( char ) this.tableau.getDonnees ( ) [ligne][colModif] != 'I' )
				{
					Contrat     c = i.getContrat ( );
				
					double coefHeure       = h.getCoefTd ( );
					double coefIntervenant = h.getNom ( ).equals ( "TP" ) ? c.getRatioTP ( ) : 1;
					double nbHeure         = Double.parseDouble ( this.tableau.getDonnees ( ) [ligne][colNbHeureOuGroupe] .toString ( ) );

					valeur = coefHeure * coefIntervenant * nbHeure;
				}
			}

			this.tableau.setValueAt ( valeur, ligne, colEqtd - DECALAGE_TABLEAU );

		}
	}

	/*
	 * Méthode qui permet de mettre à jour les valeurs du tableau
	 */
	@Override
	public void setValeurs ( Object[][] tabValeurs )
	{
		if ( tabValeurs != null )
			tabValeurs = Utilitaire.supprimerColonne ( tabValeurs, 4 );

		this.tableau.modifDonnees ( tabValeurs );
	}
}
