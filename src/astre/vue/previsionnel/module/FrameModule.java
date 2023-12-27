package astre.vue.previsionnel.module;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import astre.Controleur;
import astre.modele.elements.Horaire;
import astre.modele.elements.ModuleIUT;
import astre.modele.outils.Utilitaire;
import astre.vue.previsionnel.FramePrevisionnel;

/** Classe FrameModule
 * @author : Clémentin Ly, Maximilien Lesterlin, Maxime Lemoine
 * @version : 3.0 - 27/12/2023
 * @date : 11/12/2023
 */

public class FrameModule extends JDialog implements KeyListener //JDialog pour garder le focus sur la fenêtre
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	private Controleur          ctrl;

	private PanelModuleLabel         panelModuleLabel;
	private PanelPNLocal             panelPNLocal;
	private AbstractPanelRepartition pnlRepartition;
	private PanelModuleBouton        panelModuleBouton;
	//private PanelAffectation       panelAffectation;

	private JCheckBox           cbValidation;

	private JLabel              lblMessageErreur;
	private Timer               timerMessageErreur;

	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/

	/** Constructeur de FrameModule
	 * @param ctrl : le controleur
	 * @param parent : la fenêtre parente
	 * @param typeModule : le type de module
	 * @param numSemestre : le numéro du semestre
	 * @param action : l'action à effectuer
	 */
	public FrameModule ( Controleur ctrl, FramePrevisionnel parent, String typeModule, int numSemestre, char action )
	{
		super ( parent, "Prévisionnel : Module", true ); //JDialog pour garder le focus sur la fenêtre
		
		this.ctrl = ctrl;

		String[] ensIntituleTypeHeure = switch ( typeModule )
		{
			case "Ressource" -> new String[] { "CM"  , "TP"   , "TD"             };
			case "SAE"       -> new String[] { "SAE" , "Tut"                     }; //ne pas mettre le "h" sur les "h Tut" par exemple (car sinon il y a des problèmes avec le métier)
			case "Stage"     -> new String[] { "REH" , "Tut"                     };
			case "PPP"       -> new String[] { "CM"  , "TP"   , "TD", "HP", "HT" };
			default -> new String[] {}; //Cas en cas de type de module innexistant
		};


		this.setSize               ( 1400, 500    );
		this.setLocationRelativeTo ( parent                    );

		/* ------------------------- */
		/* Création des composants   */
		/* ------------------------- */

		this.panelModuleLabel    = new PanelModuleLabel    ( this.ctrl, typeModule, numSemestre + 1 );
		this.panelPNLocal        = new PanelPNLocal        ( this.ctrl, typeModule, ensIntituleTypeHeure );

		boolean avecGroupe = typeModule.equals( "Ressource" );
		if ( avecGroupe )
			this.pnlRepartition = new PanelRepartitionAvecGroupes ( this, this.ctrl, this.panelModuleLabel.getCode ( ) );
		else
			this.pnlRepartition = new PanelRepartitionSansGroupes ( this );

		this.panelModuleBouton   = new PanelModuleBouton   ( this.ctrl, this );
		

		this.cbValidation      = new JCheckBox ( "Validation" );

		this.lblMessageErreur = new JLabel ( "" );

		//Met un délai de 3 secondes sur le message d'erreur
		this.timerMessageErreur = new Timer ( 3000, new ActionListener ( )
		{
			public void actionPerformed ( ActionEvent e )
			{
				lblMessageErreur.setText ( "" );
				timerMessageErreur.stop ( );
			}
		} );
		
		/* ------------------------------- */
		/*  Positionnement des composants  */
		/* ------------------------------- */

		/*---------*/
		/*  Nord   */
		/*---------*/

		JPanel panelNord = new JPanel ( new BorderLayout ( ) );
		panelNord.add ( this.panelModuleLabel, BorderLayout.LINE_START );

		this.add ( panelNord, BorderLayout.NORTH );

		/*----------*/
		/*  Centre  */
		/*----------*/

		/*JPanel panelCentre  = new JPanel ( new GridBagLayout ( ) );
		
		GridBagConstraints gbcC = new GridBagConstraints ( );
		gbcC.insets = new Insets ( 5, 10, 15, 10 );

		gbcC.gridy = 0;
		gbcC.gridx = 0;
		panelCentre.add ( this.pnlRepartition, gbcC );

		gbcC.gridy = 1;
		gbcC.gridx = 0;
		this.panelAffectation.setPreferredSize ( new Dimension ( 850, 500 ) );
		panelCentre.add ( this.panelAffectation, gbcC );

		this.add ( panelCentre, BorderLayout.CENTER );*/

		/*---------*/
		/*  Ouest  */
		/*---------*/

		JPanel panelOuest = new JPanel ( new GridBagLayout ( ) );

		GridBagConstraints gbcO = new GridBagConstraints ( );
		gbcO.insets = new Insets ( 5, 10, 15, 10 );

		gbcO.gridy = 0;
		gbcO.gridx = 0;
		panelOuest.add ( this.panelPNLocal,    gbcO  );

		gbcO.gridy = 1;
		gbcO.gridx = 0;
		panelOuest.add ( this.cbValidation    , gbcO );

		gbcO.anchor = GridBagConstraints.EAST;
		gbcO.gridy = 0;
		gbcO.gridx = 1;
		panelOuest.add ( this.pnlRepartition, gbcO );

		gbcO.gridy = 1;
		gbcO.gridx = 1;
		panelOuest.add ( this.lblMessageErreur, gbcO );

		this.add ( panelOuest, BorderLayout.WEST );

		/*-------*/
		/*  Sud  */
		/*-------*/

		this.add ( this.panelModuleBouton, BorderLayout.SOUTH );
		this.setVisible( false ); //très important (lié à l'utilisation de JDialog)


		// Ajout des types d'heures aux panels
		for ( String entetetypeHeure : ensIntituleTypeHeure )
		{
			this.pnlRepartition.ajouterTypeHeure ( entetetypeHeure );
		}

		// Ajouts des données si l'action est de modifier
		if ( action == 'M' )
		{
			if ( action == Controleur.MODIFIER )
				this.majPanel ( "R1.01" );//FIXME: trouver comment mettre ce code
		}
	}

	/**
	 * met à jour les panels avec les données de la base de données
	 */
	//TODO: Méthode à compléter avec panelRepartition et checkbox cbValidation
	public void majPanel ( String code )
	{
		final String REQUETE = "f_selectHoraire('" + code + "')";

		// Mise à jour du module
		ModuleIUT module = this.ctrl.getModule ( code   );

		Object[]   tabModule  = Utilitaire.toArray  ( module );
		Object[][] tabHoraire = Utilitaire.formater ( this.ctrl.getTableauParticulier ( REQUETE ), 1, 2 );

		//Object[][] heureAffectees = new Object[tabHoraire.length][4];

		this.panelPNLocal    .majIhm ( tabHoraire );

		this.panelModuleLabel.majIhm ( tabModule  );

		/*if ( this.pnlRepartition instanceof PanelRepartitionAvecGroupes ) //TODO: à enlever surement ?
			((PanelRepartitionAvecGroupes)this.pnlRepartition).setValeurs ( );*/
		
		//this.cbValidation.setSelected ( module.estValide ( ) );
	}

	/**
	 * Met à jour la base de donnée
	 */
	public void majDonnees ( char action )
	{
		//TODO: Compléter avec le panelRépartition
		if ( action == 'A' )
			this.ctrl.majObjetBD ( this.panelModuleLabel.getDonnees ( ), ModuleIUT.class, Controleur.AJOUTER );
		
		Object[][] heurePn = this.panelPNLocal.getDonnees ( );
		//Object[][] heureAffectees = this.pnlRepartition.getDonnees ( );
	
		Object[][] tabHorraire = new Object[heurePn.length][6];

		for ( int cpt = 0; cpt < tabHorraire.length; cpt++ )
		{
			tabHorraire[cpt][0] = action; // Action
			tabHorraire[cpt][1] = this.ctrl.getHeure  ( heurePn[0][cpt].toString ( )           ); // Heure (Objet) JSP si ça corrige le problème
			tabHorraire[cpt][2] = this.ctrl.getModule ( this.panelModuleLabel.getCode ( ) ); // Module (Objet)
			tabHorraire[cpt][3] = heurePn[1]; // nbHeurePN
			tabHorraire[cpt][4] = 0; // NbSemaine        TODO: mettre valeur de heureAffectees
			tabHorraire[cpt][5] = 0; // nbHeureAffectees TODO: mettre valeur de heureAffectees
		}

		this.ctrl.majTableauBD ( tabHorraire, Horaire.class );
	}

	/** Retourne si la checkbox est sélectionnée ou non
	 * @return cbValidation
	 */
	public boolean getCbValidation ( ) { return this.cbValidation.isSelected ( ); }

	public void messageErreurAjouter ( )
	{
		this.lblMessageErreur.setText ( "L'heure existe déjà" );
		timerMessageErreur.start ( );
	}

	public void messageErreurSupprimer ( )
	{
		this.lblMessageErreur.setText ( "On ne peut pas supprimer une heure principale" );
		timerMessageErreur.start ( );
	}

	@Override public void keyTyped   ( KeyEvent e ) {}
	@Override public void keyPressed ( KeyEvent e ) {}

	@Override
	public void keyReleased(KeyEvent e)
	{
		this.pnlRepartition.majIHM ( );
		//majPanel ??
	}
}