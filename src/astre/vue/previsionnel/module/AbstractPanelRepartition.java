package astre.vue.previsionnel.module;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import astre.Controleur;
import astre.modele.elements.ModuleIUT;
import astre.vue.previsionnel.module.avecGroupe.PanelRepartitionAvecGroupes;

/** Classe AbstractPanelRepartition
 * Classe abstraite qui permet de factoriser le code des classes PanelRepartitionAvecGroupes et PanelRepartitionSansGroupes
 * @author : Maxime Lemoine
 * @version : 1.1 - 08/01/2024
 * @date : 20/12/2023
 */
public abstract class AbstractPanelRepartition extends JPanel
{
	protected Controleur  ctrl;
	protected FrameModule frameModule;
	protected KeyListener listenerModule;

	/**
	 * Constructeur de la classe AbstractPanelRepartition
	 * @param listenerModule : le KeyListener qui sera ajouté à tous les champs de saisie modifiables
	 */
	protected AbstractPanelRepartition ( Controleur ctrl, FrameModule frame )
	{
		this.frameModule    = frame;
		this.listenerModule = frame;
		this.ctrl           = ctrl;
		
		this.setLayout ( new GridBagLayout ( ) );
		this.setBorder ( BorderFactory.createLineBorder ( Color.GRAY, 1 ) );
	}

	/**
	 * Permet d'ajouter une catégorie pour un nouveau type d'heure
	 * @param typeHeure : le type d'heure
	 */
	public abstract void ajouterTypeHeure   ( String typeHeure );

	/**
	 * Permet de supprimer une catégorie d'un type d'heure
	 * @param typeHeure : le type d'heure
	 */
	public abstract void supprimerTypeHeure ( String typeHeure );

	/**
	 * Permet de mettre à jour les parties non modifiables de l'IHM
	 */
	public abstract void majIHM ( );

	/**
	 * Permet de récupérer les données des parties modifiables de l'IHM
	 */
	public abstract Object[][] getDonnees ( );

	/**
	 * Permet de récupérer la somme eqtd des heures affectées, calculée par le panel
	 */
	public abstract double getSommeEQTDAffecte ( );

	/**
	 * Permet de mettre à jour les parties modifiables de l'IHM avec des valeurs par défaut
	 * @param tabRepartition : le tableau de répartition des heures
	 */
	public abstract void setValeurs ( Object[][] tabRepartition );

	/**
	 * Méthode qui permet de récupérer un tableau formater pour l'enregistrement dans la base de donnée
	 */
	public Object[][] preparerTableau ( char action, Object[][] tabHeurePn, ModuleIUT module )
	{
		Object[][] heureAffectees = this.getDonnees ( );
		Object[][] tabHorraire    = new Object[heureAffectees.length][6];

		int indiceHP = heureAffectees.length - 1;
		for ( int cpt = 0; cpt < heureAffectees.length; cpt++ )
		{
			// Cas des heures ponctuelles
			if ( this instanceof PanelRepartitionAvecGroupes && cpt == indiceHP )
			{
				tabHorraire[indiceHP][1] = this.ctrl.getHeure  ( "HP" ); // Heure (Objet)
				tabHorraire[indiceHP][3] = 0;                            // nbHeurePN
			}
			else
			{
				tabHorraire[cpt][1] = this.ctrl.getHeure ( tabHeurePn[cpt][0].toString ( ) ); // Heure (Objet)
				tabHorraire[cpt][3] = tabHeurePn [cpt][1];                                    // nbHeurePN
			}

			tabHorraire[cpt][0] = action;                 // Action
			tabHorraire[cpt][2] = module;                 // Module (Objet)
			tabHorraire[cpt][4] = heureAffectees[cpt][0]; // NbSemaine
			tabHorraire[cpt][5] = heureAffectees[cpt][1]; // nbHeureAffectees
		}

		return tabHorraire;
	}
}
