package astre.vue.previsionnel.module;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import astre.Controleur;
import astre.modele.elements.Intervenant;
import astre.vue.outils.Tableau;

/** Classe AbstractPanelAffectation
  * @author : Maxime Lemoine, Maximilien Lesterlin
  * @version : 1.0 - 09/01/2024
  * @date : 09/01/2024
  */

public abstract class AbstractPanelAffectation extends JPanel implements ActionListener
{
	/*---------------------*/
	/*--Constantes classe--*/
	/*---------------------*/
	protected static final int DECALAGE_TABLEAU = 2;

	protected static final int COL_MODIF          = 0;
	protected static final int COL_ID_INTERVENANT = 1;
	protected static final int COL_INTERVENANT    = 2;
	protected static final int COL_HEURE          = 3;

	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	protected Controleur  ctrl;
	protected FrameModule frmModule;

	protected String[]    ensIntituleTypeHeure;

	protected Tableau     tableau;
	protected JScrollPane scrollPane;

	protected JButton     btnAjouter;
	protected JButton     btnSupprimer;

	protected String []   ensEntete;
	protected Object []   ensTypeDefaut;
	protected boolean[]   ensModifiable;

	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/

	protected AbstractPanelAffectation ( Controleur ctrl, FrameModule frmModule, String[] ensIntituleTypeHeure, String[] ensEntete, Object[] ensTypeDefaut, boolean[] ensModifiable )
	{
		this.ctrl                 = ctrl;
		this.frmModule            = frmModule;
		this.ensIntituleTypeHeure = ensIntituleTypeHeure;
		this.ensEntete            = ensEntete;
		this.ensTypeDefaut        = ensTypeDefaut;
		this.ensModifiable        = ensModifiable;

		/* ------------------------- */
		/*    Paramètres du panel    */
		/* ------------------------- */

		this.setLayout        ( new BorderLayout ( )       );
		this.setPreferredSize ( new Dimension ( 900, 500 ) );

		/* ------------------------- */
		/*  Création des composants  */
		/* ------------------------- */
		
		// Création du tableau
		this.tableau = Tableau.initialiserTableau ( this.ensEntete, this.ensTypeDefaut, this.ensModifiable, DECALAGE_TABLEAU, null );
		this.scrollPane = new JScrollPane ( this.tableau );

		//Ajout d'une JComboBox pour les intervenants au tableau
		JComboBox<String> cbEditInter = creerCbIntervenant ( );
		this.tableau.getColumnModel ( ).getColumn ( COL_INTERVENANT-DECALAGE_TABLEAU ).setCellEditor ( new DefaultCellEditor ( cbEditInter ) );

				//Ajout d'une JComboBox pour les intervenants au tableau
		JComboBox<String> cbEditHeure = creerCbHeure ( );
		this.tableau.getColumnModel ( ).getColumn ( COL_HEURE-DECALAGE_TABLEAU ).setCellEditor ( new DefaultCellEditor ( cbEditHeure ) );

		// Création des boutons
		this.btnAjouter   = new JButton ( "Ajouter"   );
		this.btnSupprimer = new JButton ( "Supprimer" );

		/* ---------------------------- */
		/* Postionnement des composants */
		/* ---------------------------- */

		JPanel panelBoutons = new JPanel ( );
		panelBoutons.add ( this.btnAjouter   );
		panelBoutons.add ( this.btnSupprimer );
		
		this.add ( this.scrollPane, BorderLayout.NORTH );
		this.add ( panelBoutons   , BorderLayout.SOUTH );

		/* ------------------------- */
		/* Activation des composants */
		/* ------------------------- */

		this.btnAjouter  .addActionListener ( this );
		this.btnSupprimer.addActionListener ( this );

		this.tableau.addKeyListener ( frmModule );
	}
	
	
	/* ------------------------- */
	/*  Méthodes initialisation  */
	/* ------------------------- */

	/**
	 * Méthode qui permet de générer les combobox d'heure
	 */
	protected JComboBox<String> creerCbHeure ( )
	{
		//Ajout d'une JComboBox pour les intervenants au tableau
		JComboBox<String> cbEditHeure = new JComboBox<> ( );
		for ( String heure : this.ensIntituleTypeHeure )
			cbEditHeure.addItem ( heure );

		return cbEditHeure;
	}

	/**
	 * Méthode qui permet de générer les combobox d'Intervenant
	 */
	protected JComboBox<String> creerCbIntervenant ( )
	{
		//Ajout d'une JComboBox pour les intervenants au tableau
		JComboBox<String> cbEditInter = new JComboBox<> ( );
		for ( Intervenant i : this.ctrl.getTable ( Intervenant.class ) )
			cbEditInter.addItem ( i.getNom ( ) + " " + i.getPrenom ( ) );

		return cbEditInter;
	}


	/* ------------------------- */
	/*          Méthodes         */
	/* ------------------------- */

	/**
	 * Méthode qui permet de mettre à jour la colonne tot eqtd
	 */
	public abstract void majTotEqtd ( );

	/**
	 * Méthode qui permet de récupérer les valeurs du tableau
	 * @return un tableau d'object à doubles dimentions qui contient les valeurs du tableau 
	 */
	public Object[][] getValeurs ( )
	{
		return this.tableau.getDonnees ( );
	}

	/*
	 * Méthode qui permet de récupérer les sommes des heures en eqtd
	 */
	public abstract Map<String, Double> getSommesEQTD ( );

	/*
	 * Méthode qui permet de mettre à jour les valeurs du tableau
	 */
	public void setValeurs ( Object[][] tabValeurs )
	{
		this.tableau.modifDonnees ( tabValeurs );
	}


	/* ------------------------- */
	/*          Listener         */
	/* ------------------------- */
	
	/**
	 * Méthode qui permet de gérer les actions sur les boutons du tableau
	 */
	public void actionPerformed ( ActionEvent e )
	{
		if ( e.getSource ( ) == this.btnAjouter )
			this.tableau.ajouterLigne ( );

		if ( e.getSource ( ) == this.btnSupprimer )
			this.tableau.supprimerLigne ( );
	}
}
