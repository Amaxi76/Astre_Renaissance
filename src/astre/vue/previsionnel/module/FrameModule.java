package astre.vue.previsionnel.module;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import astre.Controleur;
import astre.modele.elements.Horaire;
import astre.modele.elements.Intervient;
import astre.modele.elements.ModuleIUT;
import astre.modele.elements.Semestre;
import astre.modele.outils.Utilitaire;
import astre.vue.outils.Saisie;
import astre.vue.previsionnel.FramePrevisionnel;
import astre.vue.previsionnel.module.avecGroupe.PanelAffectationAvecGroupes;
import astre.vue.previsionnel.module.avecGroupe.PanelRepartitionAvecGroupes;
import astre.vue.previsionnel.module.sansGroupe.PanelAffectationSansGroupes;
import astre.vue.previsionnel.module.sansGroupe.PanelRepartitionSansGroupes;

/** Classe FrameModule
 * @author : Clémentin Ly, Maximilien Lesterlin, Maxime Lemoine
 * @version : 3.0 - 27/12/2023
 * @date : 11/12/2023
 */
//TODO: mettre des hashmap dans le panelModulelabel pour remplacer le getNbGp TP et TD et rendre modulable
public class FrameModule extends JDialog implements KeyListener //JDialog pour garder le focus sur la fenêtre
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/
	
	private static final String    TITRE     = "Prévisionnel : Module";
	private static final Dimension DIMENSION = new Dimension ( 1500, 1000 );

	private Controleur          ctrl;

	private PanelModuleLabel         panelModuleLabel;
	private PanelPNLocal             panelPNLocal;
	private AbstractPanelRepartition panelRepartition;
	private PanelModuleBouton        panelModuleBouton;
	private AbstractPanelAffectation panelAffectation;

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
		super ( parent, TITRE, true ); //JDialog pour garder le focus sur la fenêtre
		
		this.ctrl = ctrl;

		String[] ensIntituleTypeHeure = switch ( typeModule )
		{
			case "Ressource" -> new String[] { "CM"  , "TD"   , "TP"             };
			case "SAE"       -> new String[] { "SAE" , "Tut"                     }; //ne pas mettre le "h" sur les "h Tut" par exemple (car sinon il y a des problèmes avec le métier)
			case "Stage"     -> new String[] { "REH" , "Tut"                     };
			case "PPP"       -> new String[] { "CM"  , "TP"   , "TD", "HP", "HT" };
			default          -> new String[] {}; //Cas en cas de type de module innexistant
		};

		this.setSize               ( DIMENSION );
		this.setLocationRelativeTo ( parent    );

		/* ------------------------- */
		/* Création des composants   */
		/* ------------------------- */

		this.panelModuleLabel    = new PanelModuleLabel ( this.ctrl,       typeModule, numSemestre + 1      );
		this.panelPNLocal        = new PanelPNLocal     ( this.ctrl, this, typeModule, ensIntituleTypeHeure );

		if ( action == Controleur.MODIFIER ) Saisie.JTextFieldToLabel ( this.panelModuleLabel.getTxtCode ( ) );

		boolean avecGroupe = typeModule.equals ( "Ressource" );
		if ( avecGroupe )
		{
			String  [] ensEntete     = new String [] { "action",  "idIntervenant", "Intervenant", "type", "nb sem", "nb Gp  |  nb H", "tot eqtd", "commentaire" };
			Object  [] ensTypeDefaut = new Object [] {      'A',                0,            "",     "",        1,            0,          0,         "..." };
			boolean [] ensModifiable = new boolean[] {     true,            false,          true,   true,     true,         true,      false,          true };
			
			this.panelAffectation = new PanelAffectationAvecGroupes ( this.ctrl, this, ensIntituleTypeHeure, ensEntete, ensTypeDefaut, ensModifiable );
			this.panelRepartition = new PanelRepartitionAvecGroupes ( this.ctrl, this, ensIntituleTypeHeure );
		}
		else
		{
			String  [] ensEntete     = new String [] { "action",  "idIntervenant", "Intervenant", "type", "nb H", "tot eqtd", "commentaire" };
			Object  [] ensTypeDefaut = new Object [] {      'A',                0,            "",     "",      0,          0,         "..." };
			boolean [] ensModifiable = new boolean[] {     true,            false,          true,   true,   true,      false,          true };

			this.panelAffectation = new PanelAffectationSansGroupes ( this.ctrl, this, ensIntituleTypeHeure, ensEntete, ensTypeDefaut, ensModifiable );
			this.panelRepartition = new PanelRepartitionSansGroupes ( this.ctrl, this, ensIntituleTypeHeure );
		}

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

		//FIXME: mettre des trucs dans les machins poour que ça soit plus stylé dans les bidules (en gros on enlève le BorderLayout pour mettre un BoxLayout)
		
		/*JPanel panelPanelsModule = new JPanel ( new BoxLayout ( this, BoxLayout.Y_AXIS ) );
		panelPanelsModule.add ( this.panelModuleLabel );
		this.add ( panelPanelsModule, BorderLayout.NORTH );*/
	

		/*----------*/
		/*  Centre  */
		/*----------*/

		//Pour gérer les panels
		JPanel panelContenu = new JPanel ( new GridBagLayout ( ) );

		GridBagConstraints gbcC = new GridBagConstraints ( );
	
		/* ------------------*/
		/*  Partie PN Local  */
		/*-------------------*/

		gbcC.anchor = GridBagConstraints.NORTHWEST;
		gbcC.insets = new Insets ( 0, 0, 0, 0 );
		
		gbcC.gridy = 0;
		gbcC.gridx = 0;
		panelContenu.add ( new JLabel ( "PN local (nb h tot/etd)" ), gbcC );

		gbcC.gridy = 1;
		gbcC.gridx = 0;
		panelContenu.add ( this.panelPNLocal,    gbcC  );

		gbcC.gridy = 2;
		gbcC.gridx = 0;
		panelContenu.add ( this.cbValidation    , gbcC );

		/*--------------------*/
		/* Partie répartition */
		/*--------------------*/

		gbcC.anchor = GridBagConstraints.NORTHWEST;
		gbcC.insets = new Insets ( 0, 30, 0, 0 );

		gbcC.gridy = 0;
		gbcC.gridx = 1;
		panelContenu.add ( new JLabel ( "Répartition" ), gbcC );

		gbcC.gridy = 1;
		gbcC.gridx = 1;
		panelContenu.add ( this.panelRepartition, gbcC );

		// Ajout des types d'heures aux panels
		for ( String entetetypeHeure : ensIntituleTypeHeure )
			this.panelRepartition.ajouterTypeHeure ( entetetypeHeure );

		/*--------------------*/
		/* Partie Affectation */
		/*--------------------*/

		gbcC.anchor = GridBagConstraints.NORTHWEST;
		
		gbcC.insets = new Insets ( 30, 30, 0, 0 );
		gbcC.gridy = 2;
		gbcC.gridx = 1;
		panelContenu.add ( new JLabel ( "Affectation" ), gbcC );

		gbcC.insets = new Insets ( 0, 30, 0, 0 );
		gbcC.gridy = 3;
		gbcC.gridx = 1;
		panelContenu.add ( this.panelAffectation, gbcC );
		
		this.add ( panelContenu, BorderLayout.CENTER );

		/*-------*/
		/*  Sud  */
		/*-------*/

		this.add ( this.panelModuleBouton, BorderLayout.SOUTH );
		this.setVisible ( false ); //très important (lié à l'utilisation de JDialog)
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
	 * @throws Exception
	 */
	public void majDonnees ( char action ) throws Exception
	{
		try
		{
			Object[] tabModule      = this.panelModuleLabel.getDonnees ( );

			ModuleIUT module = ModuleIUT.creation ( ( Semestre ) tabModule[0], tabModule[1].toString ( ), tabModule[2].toString ( ), tabModule[3].toString ( ), tabModule[4].toString ( ), ( Boolean ) tabModule[5], this.panelPNLocal.getSommeEQTDPromo ( ), this.panelRepartition.getSommeEQTDAffecte ( ) );
		
			if ( action == 'A' )
			{
				this.ctrl.insert ( module );
			}
			else if ( action == 'M' )
			{
				// Mise à jour de la CheckBox
				module.setValide ( this.cbValidation.isSelected ( ) );
				this.ctrl.update ( module );
			}

			Object[][] tabRepartition = this.panelAffectation.preparerTableau (                                           module );
			Object[][] tabHorraire    = this.panelRepartition.preparerTableau ( action, this.panelPNLocal.getDonnees ( ), module );

			this.ctrl.majTableauBD ( tabHorraire   , Horaire   .class );
			this.ctrl.majTableauBD ( tabRepartition, Intervient.class );
		}
		catch ( Exception e )
		{
			throw new Exception ( e.getMessage ( ) );
		}
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
		this.panelAffectation.majTotEqtd ( );
		this.panelRepartition.majIHM     ( );
	}
}