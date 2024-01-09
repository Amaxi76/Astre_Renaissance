package astre.vue.previsionnel.module;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;


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

//FIXME: Problème les nombres ne se mettent pas à droite (malgré l'application d'un style )

public class FrameModule extends JDialog implements KeyListener //JDialog pour garder le focus sur la fenêtre
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	private Controleur          ctrl;

	private PanelModuleLabel         panelModuleLabel;
	private PanelPNLocal             panelPNLocal;
	private AbstractPanelRepartition panelRepartition;
	private PanelModuleBouton        panelModuleBouton;
	private PanelAffectation         panelAffectation;

	private JCheckBox cbValidation;
	private JLabel    lblMessageErreur;
	private Timer     timerMessageErreur;

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
			case "Ressource" -> new String[] { "CM"  , "TD"   , "TP"             };
			case "SAE"       -> new String[] { "SAE" , "Tut"                     }; //ne pas mettre le "h" sur les "h Tut" par exemple (car sinon il y a des problèmes avec le métier)
			case "Stage"     -> new String[] { "REH" , "Tut"                     };
			case "PPP"       -> new String[] { "CM"  , "TP"   , "TD", "HP", "HT" };
			default -> new String[] {}; //Cas en cas de type de module innexistant
		};

		this.setSize               ( 1500, 1000 );
		this.setLocationRelativeTo ( parent    );

		/* ------------------------- */
		/* Création des composants   */
		/* ------------------------- */

		this.panelModuleLabel    = new PanelModuleLabel (       this.ctrl, typeModule, numSemestre + 1      );
		this.panelPNLocal        = new PanelPNLocal     ( this, this.ctrl, typeModule, ensIntituleTypeHeure );
		this.panelAffectation    = new PanelAffectation ( this, this.ctrl, typeModule, ensIntituleTypeHeure );

		if ( action == Controleur.MODIFIER ) this.panelModuleLabel.formatTxt ( this.panelModuleLabel.getTxtCode ( ) );

		boolean avecGroupe = typeModule.equals ( "Ressource" );
		if ( avecGroupe )
			this.panelRepartition = new PanelRepartitionAvecGroupes ( this, this.ctrl, ensIntituleTypeHeure );
		else
			this.panelRepartition = new PanelRepartitionSansGroupes ( this, ensIntituleTypeHeure );

		this.panelModuleBouton   = new PanelModuleBouton   ( this.ctrl, this, action );

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

		//Pour gérer le panel centre et le tableau
		JPanel panelContenu = new JPanel ( new FlowLayout ( ) );

		//Pour gérer les panels pnLocal et repartition
		JPanel panelCentre = new JPanel ( new GridBagLayout ( ) );

		GridBagConstraints gbcC = new GridBagConstraints ( );
		gbcC.insets = new Insets ( 5, 10, 15, 10 );

		gbcC.gridy = 0;
		gbcC.gridx = 0;
		panelCentre.add ( this.panelPNLocal,    gbcC  );

		gbcC.gridy = 1;
		gbcC.gridx = 0;
		panelCentre.add ( this.cbValidation    , gbcC );

		gbcC.anchor = GridBagConstraints.EAST;
		gbcC.gridy = 0;
		gbcC.gridx = 1;
		panelCentre.add ( this.panelRepartition, gbcC );

		gbcC.gridy = 1;
		gbcC.gridx = 1;
		panelCentre.add ( this.lblMessageErreur, gbcC );
		
		panelContenu.add ( panelCentre );
		
		this.panelAffectation.setBorder ( new EmptyBorder ( 5, 170, 10, 30 ) );
		panelContenu.add ( this.panelAffectation );

		this.add ( panelContenu, BorderLayout.CENTER );

		/*-------*/
		/*  Sud  */
		/*-------*/

		this.add ( this.panelModuleBouton, BorderLayout.SOUTH );
		this.setVisible ( false ); //très important (lié à l'utilisation de JDialog)

		// Ajout des types d'heures aux panels
		for ( String entetetypeHeure : ensIntituleTypeHeure )
			this.panelRepartition.ajouterTypeHeure ( entetetypeHeure );
	}

	/**
	 * met à jour les panels avec les données de la base de données
	 */
	public void setValeursPanel ( String code )
	{
		final String REQUETE_HORAIRE     = "f_selectHoraire    ('" + code + "')";
		final String REQUETE_REPARTION   = "f_selectHoraireBis ('" + code + "')";
		final String REQUETE_AFFECTATION = "f_selectIntervient ('" + code + "')";

		// Mise à jour du module
		ModuleIUT module = this.ctrl.getModule ( code );

		Object[]   tabModule      = Utilitaire.toArray  ( module );
		Object[][] tabHoraire     = Utilitaire.formater ( this.ctrl.getTableauParticulier ( REQUETE_HORAIRE     ), 1, 2 );
		Object[][] tabRepartition = Utilitaire.formater ( this.ctrl.getTableauParticulier ( REQUETE_REPARTION   ), 1, 3 );
		Object[][] tabAffectation =                       this.ctrl.getTableauParticulier ( REQUETE_AFFECTATION );

		this.panelPNLocal    .setValeurs ( tabHoraire     );
		this.panelModuleLabel.setValeurs ( tabModule      );
		this.panelRepartition.setValeurs ( tabRepartition );
		this.panelAffectation.setValeurs ( tabAffectation );
		
		this.panelRepartition.majIHM ( );
		
		this.cbValidation.setSelected ( module.estValide ( ) );
	}

	/**
	 * Met à jour la base de donnée
	 */
	public void majDonnees ( char action )
	{
		if ( action == 'A' )
			this.ctrl.majObjetBD ( this.panelModuleLabel.getDonnees ( ), ModuleIUT.class, Controleur.AJOUTER );
		else if ( action == 'M' )
		{
			// Mise à jour de la CheckBox
			Object[] tabModule = this.panelModuleLabel.getDonnees ( );
			tabModule[5] = this.cbValidation.isSelected ( );
			this.ctrl.majObjetBD ( tabModule, ModuleIUT.class, Controleur.MODIFIER );
		}

		// Mise à jour de la répartion des heures
		Object[][] heurePn        = this.panelPNLocal    .getDonnees ( );
		Object[][] heureAffectees = this.panelRepartition.getDonnees ( );
		Object[][] tabHorraire    = new Object[heureAffectees.length][6];

		int indiceHP = heureAffectees.length - 1;
		for ( int cpt = 0; cpt < heureAffectees.length; cpt++ )
		{
			// Cas des heures ponctuelles
			if ( this.panelRepartition instanceof PanelRepartitionAvecGroupes && cpt == indiceHP )
			{
				tabHorraire[indiceHP][1] = this.ctrl.getHeure  ( "HP" ); // Heure (Objet)
				tabHorraire[indiceHP][3] = 0;                            // nbHeurePN
			}
			else
			{
				tabHorraire[cpt][1] = this.ctrl.getHeure ( heurePn[cpt][0].toString ( ) ); // Heure (Objet)
				tabHorraire[cpt][3] = heurePn [cpt][1];                                    // nbHeurePN
			}

			tabHorraire[cpt][0] = action;                                                    // Action
			tabHorraire[cpt][2] = this.ctrl.getModule ( this.panelModuleLabel.getCode ( ) ); // Module (Objet)
			tabHorraire[cpt][4] = heureAffectees[cpt][0];                                    // NbSemaine
			tabHorraire[cpt][5] = heureAffectees[cpt][1];                                    // nbHeureAffectees
		}

		this.ctrl.majTableauBD ( tabHorraire, Horaire.class );
	}

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

	public Map<String, Double> getSommesEQTD ( )
	{
		return this.panelAffectation.getSommesEQTD ( );
	}

	public int getNbHeureSemaine ( String typeHeure )
	{
		if ( this.panelRepartition instanceof PanelRepartitionAvecGroupes )
			return ( ( PanelRepartitionAvecGroupes ) this.panelRepartition ).getValeurTypeHeure ( typeHeure )[1];
		else
			return 1; // élément neutre de la multiplication
	}

	//TODO: mettre des hashmap dans le panelModulelabel pour remplacer le getNbGp TP et TD et rendre modulable
	public int getNbGroupe ( String typeHeure )
	{
		if ( typeHeure.equals ( "TP" ) )
			return this.panelModuleLabel.getNbGpTP ( );
		
		if ( typeHeure.equals ( "TD" ) || typeHeure.equals ( "HP" ) )
			return this.panelModuleLabel.getNbGpTD ( );

		return 1; // élément neutre de la multiplication
	}

	@Override public void keyTyped   ( KeyEvent e ) { /**/ }
	@Override public void keyPressed ( KeyEvent e ) { /**/ }

	@Override
	public void keyReleased ( KeyEvent e )
	{
		majIHM ( );
	}

	public void majIHM ( )
	{
		this.panelRepartition.majIHM ( );
		this.panelAffectation.majSomme ( );
	}
}