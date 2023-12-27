package astre.vue.previsionnel.module;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

/** Classe AbstractPanelRepartition
 * Classe abstraite qui permet de factoriser le code des classes PanelRepartitionAvecGroupes et PanelRepartitionSansGroupes
 * @author : Maxime Lemoine
 * @version : 1.0 - 20/12/2023
 * @date : 27/12/2023
 */
public abstract class AbstractPanelRepartition extends JPanel
{
	protected KeyListener listenerModule;

	/**
	 * Constructeur de la classe AbstractPanelRepartition
	 * @param listenerModule : le KeyListener qui sera ajouté à tous les champs de saisie modifiables
	 */
	public AbstractPanelRepartition ( KeyListener listenerModule )
	{
		this.listenerModule = listenerModule;
		
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
}
