package astre.vue.previsionnel.module;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import astre.Controleur;
import astre.modele.elements.Heure;
import astre.modele.elements.Intervenant;
import astre.modele.elements.Intervient;
import astre.modele.elements.ModuleIUT;
import astre.vue.outils.Tableau;
import astre.vue.previsionnel.module.avecGroupe.PanelAffectationAvecGroupes;

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

	/*-----------------------*/
	/*--Colonnes du tableau--*/
	/*-----------------------*/

	protected int colModif         = 0;
	protected int colIdIntervenant = 1;
	protected int colIntervenant   = 2;
	protected int colHeure         = 3;

	//ces variables sont à définir dans les classes filles si nécessaire
	protected int colNbSemaine;
	protected int colNbHeureOuGroupe;
	protected int colEqtd;
	protected int colCommentaire;

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

		this.setLayout        ( new BoxLayout ( this, BoxLayout.Y_AXIS ) );
		this.setPreferredSize ( new Dimension ( 900,500                ) );

		/* ------------------------- */
		/*  Création des composants  */
		/* ------------------------- */
		
		// Création des "combobox"
		this.ensTypeDefaut[colHeure] = creerListeHeure ( );
		this.ensTypeDefaut[colIntervenant] = creerListeIntervenant ( );

		// Création du tableau
		this.tableau = Tableau.initialiserTableau ( this.ensEntete, this.ensTypeDefaut, this.ensModifiable, DECALAGE_TABLEAU, null );
		this.scrollPane = new JScrollPane ( this.tableau );

		// Création des boutons
		this.btnAjouter   = new JButton ( "Ajouter"   );
		this.btnSupprimer = new JButton ( "Supprimer" );

		/* ---------------------------- */
		/* Postionnement des composants */
		/* ---------------------------- */

		JPanel panelBoutons = new JPanel ( new FlowLayout ( FlowLayout.LEFT ) );
		panelBoutons.add ( this.btnAjouter   );
		panelBoutons.add ( this.btnSupprimer );
		
		this.add ( this.scrollPane );
		this.add ( panelBoutons    );

		/* ------------------------- */
		/* Activation des composants */
		/* ------------------------- */

		this.btnAjouter  .addActionListener ( this );
		this.btnSupprimer.addActionListener ( this );

		/* inutile mtn normalement
		cbEditInter.addActionListener ( e ->
		{
			int selectedRow = this.tableau.getSelectedRow ( );
			if ( selectedRow != -1 )
			{
				Intervenant selectedIntervenant = this.ctrl.getTable ( Intervenant.class ).get ( cbEditInter.getSelectedIndex ( ) );
				this.tableau.setValueAt ( selectedIntervenant.getId ( ), selectedRow, -1 );
			}
		} );*/

		this.tableau.addKeyListener ( frmModule );

	}
	
	
	/* ------------------------- */
	/*  Méthodes initialisation  */
	/* ------------------------- */

	protected List<String> creerListeHeure ( )
	{
		List<String> ensHeure = new ArrayList<> ( );
		for ( String heure : this.ensIntituleTypeHeure )
			ensHeure.add ( heure );

		return ensHeure;
	}

	protected List<String> creerListeIntervenant ( )
	{
		List<String> ensIntervenant = new ArrayList<> ( );
		for ( Intervenant i : this.ctrl.getTable ( Intervenant.class ) )
		ensIntervenant.add ( i.getNom ( ) + " " + i.getPrenom ( ) );

		return ensIntervenant;
	}

	/**
	 * Méthode qui permet de générer les map des intitules
	 */
	protected Map<String, Double> creerMapIntitule ( )
	{
		Map<String, Double> map = new HashMap<> ( );

		// Initialisation des entetes par défaut
		for ( String intitule : this.ensIntituleTypeHeure )
			map.put ( intitule, 0.0 );

		return map;
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
	 * Méthode qui permet de mettre à jour les valeurs du tableau
	 */
	public void setValeurs ( Object[][] tabValeurs )
	{
		this.tableau.setDonnees ( tabValeurs );
	}

	/*
	 * Méthode qui permet de récupérer les sommes des heures en eqtd
	 */
	public Map<String, Double> getSommesEQTD ( )
	{
		Map<String, Double> map = this.creerMapIntitule ( );

		// Calcul des sommes par type d'heures
		for ( int cpt = 0; cpt < this.tableau.getDonnees ( ).length; cpt++ )
		{
			if ( ( char ) this.tableau.getDonnees ( ) [cpt][0] != Controleur.SUPPRIMER )
			{
				String cle = this.tableau.getValueAt ( cpt, 1 ).toString ( );
				Double val = Double.parseDouble ( this.tableau.getDonnees ( ) [cpt][colEqtd].toString ( ) );
			
				//dans le cas ou la clé est vide
				if ( ! cle.equals ( "" ) )
					map.put ( cle, map.get ( cle ) + val );
			}
		}

		return map;
	}

	/* ------------------------- */
	/*  Préparation des données  */
	/* ------------------------- */

	/**
	 * Méthode qui permet de récupérer un tableau formater pour l'enregistrement dans la base de donnée
	 */
	public Object[][] preparerTableau ( ModuleIUT module )
	{
		// Informations ihm
		Object[][] tabLocalIHM = this.tableau.getDonnees ( );

		// Préparation du nouveau tableau
		int nbAttributsIntervient = Intervient.class.getDeclaredFields ( ).length + 1; //+1 pck c com sa caractère de modification
		Object[][] tab            = new Object [tabLocalIHM.length][nbAttributsIntervient];
		
		for ( int ligne = 0; ligne < tabLocalIHM.length; ligne++ )
		{
			Intervenant i = this.ctrl.getIntervenant ( ( int ) ( tabLocalIHM[ligne][colIdIntervenant]            ) );
			Heure       h = this.ctrl.getHeure       (           tabLocalIHM[ligne][colHeure        ].toString ( ) );

			tab [ligne][0] = tabLocalIHM[ligne][0];                                                                             // Caractère de l'action que la BD doit effectuer
			tab [ligne][1] = i;                                                                                                 // Intevenant
			tab [ligne][2] = h;                                                                                                 // Heure
			tab [ligne][3] = module;                                                                                            // Module
			tab [ligne][4] = ( this instanceof PanelAffectationAvecGroupes ) ? (int)tabLocalIHM[ligne][colNbSemaine] : (int) 1; // Nombre de semaine
			tab [ligne][5] = tabLocalIHM[ligne][colNbHeureOuGroupe];                                                            // Nombre de groupe
			tab [ligne][6] = Double.parseDouble(tabLocalIHM[ligne][colEqtd           ].toString ( ) );                          // Nombre d'heure //TODO: temporaire : mettre en double 
			tab [ligne][7] = tabLocalIHM[ligne][colCommentaire    ];                                                            // Commentaire
		}

		return tab;
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
			
		this.frmModule.majIHM ( );
	}
}