package astre.vue.previsionnel.module;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import astre.Controleur;
import astre.modele.elements.Heure;
import astre.modele.elements.Horaire;
import astre.modele.elements.ModuleIUT;
import astre.modele.outils.Utilitaire;
import astre.vue.previsionnel.FramePrevisionnel;

/** Classe FrameModule
 * @author : Clémentin Ly, Maximilien Lesterlin, Maxime Lemoine
* @version : 2.0 - 23/12/2023
* @date : 11/12/2023
*/

public class FrameModule extends JDialog //JDialog pour garder le focus sur la fenêtre
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	private Controleur          ctrl;

	private PanelModuleLabel    panelModuleLabel;
	private PanelModuleBouton   panelModuleBouton;
	private PanelPNLocal        panelPNLocal;
	//private PanelPNLocalBis     panelPNLocalBis;
	//private PanelPNLocalPPP	    panelPNLocalPPP;
	private PanelModuleHeure    panelModuleHeure;
	private PanelRepartition    panelRepartition;
	private PanelRepartitionBis panelRepartitionBis;
	private PanelRepartitionPPP panelRepartitionPPP;
	private PanelAffectation    panelAffectation;

	private JCheckBox           cbValidation;

	private JLabel              lblMessageErreur;
	private Timer               timerMessageErreur;

	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/

	/** Constructeur de FrameModule
	 * @param ctrl le controleur
	 * 
	 */

	public FrameModule ( Controleur ctrl, FramePrevisionnel parent, String typeModule, int numSemestre, char action )
	{
		super ( parent, "Prévisionnel : Module", true ); //JDialog pour garder le focus sur la fenêtre
		
		this.ctrl = ctrl;

		this.setSize               ( 1000, 500    );
		this.setLocationRelativeTo ( parent                    );

		/* ------------------------- */
		/* Création des composants   */
		/* ------------------------- */

		this.panelModuleLabel    = new PanelModuleLabel    ( this.ctrl, typeModule, numSemestre + 1 );
		this.panelModuleBouton   = new PanelModuleBouton   ( this.ctrl, this );
		this.panelPNLocal        = new PanelPNLocal        ( this.ctrl, typeModule );

		/*this.panelPNLocalBis     = new PanelPNLocalBis     ( this.ctrl, this );
		this.panelPNLocalPPP     = new PanelPNLocalPPP     ( this.ctrl, this );
		this.panelModuleHeure    = new PanelModuleHeure    ( this.ctrl, this );
		this.panelRepartition    = new PanelRepartition    ( this.ctrl, this );
		this.panelRepartitionBis = new PanelRepartitionBis ( this.ctrl, this );
		this.panelRepartitionPPP = new PanelRepartitionPPP ( this.ctrl, this );
		this.panelAffectation    = new PanelAffectation    ( this.ctrl       );

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
		} );*/
		
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
		panelCentre.add ( this.panelRepartition,    gbcC );
		panelCentre.add ( this.panelRepartitionBis, gbcC );
		panelCentre.add ( this.panelRepartitionPPP, gbcC );

		this.panelRepartitionBis.setVisible ( false );
		this.panelRepartitionPPP.setVisible ( false );

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
		/*panelOuest.add ( this.panelPNLocalBis, gbcO  );
		panelOuest.add ( this.panelPNLocalPPP, gbcO  );
		this.panelPNLocalBis.setVisible ( false );
		this.panelPNLocalPPP.setVisible ( false );

		gbcO.gridy = 1;
		gbcO.gridx = 0;
		panelOuest.add ( this.cbValidation    , gbcO );

		gbcO.gridy = 2;
		gbcO.gridx = 0;
		panelOuest.add ( this.panelModuleHeure, gbcO );

		gbcO.gridy = 3;
		gbcO.gridx = 0;
		panelOuest.add ( this.lblMessageErreur, gbcO );*/

		this.add ( panelOuest, BorderLayout.WEST );
		/*-------*/
		/*  Sud  */
		/*-------*/

		this.add ( this.panelModuleBouton, BorderLayout.SOUTH );
		this.setVisible( false ); //très important (lié à l'utilisation de JDialog)
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
		//this.panelRepartition.majIhm ( heureAffectees );
		
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

	/** Retourne le panelModuleLabel de FrameModule
	 * @return panelModuleLabel
	 */
	public PanelModuleLabel    getPanelModuleLabel    ( ) { return this.panelModuleLabel;    }

	/** Retourne le panelPNLocal de FrameModule
	 * @return panelPNLocal
	 */
	public PanelPNLocal	       getPanelPNLocal        ( ) { return this.panelPNLocal;        }

	/** Retourne le panelPNLocalBis de FrameModule
	 * @return panelPNLocalBis
	 */
	//public PanelPNLocalBis     getPanelPNLocalBis     ( ) { return this.panelPNLocalBis;     }

	/** Retourne le panelPNLocalPPP de FrameModule
	 * @return panelPNLocalPPP
	 */
	//public PanelPNLocalPPP     getPanelPNLocalPPP     ( ) { return this.panelPNLocalPPP;     }

	/** Retourne le panelRepartition de FrameModule
	 * @return panelRepartition
	 */
	public PanelRepartition    getPanelRepartition    ( ) { return this.panelRepartition;    }

	/** Retourne le panelRepartitionBis de FrameModule
	 * @return panelRepartitionBis
	 */
	public PanelRepartitionBis getPanelRepartitionBis ( ) { return this.panelRepartitionBis; }

	/** Retourne le panelRepartitionPPP de FrameModule
	 * @return panelRepartitionPPP
	 */
	public PanelRepartitionPPP getPanelRepartitionPPP ( ) { return this.panelRepartitionPPP; }

	/** Retourne le panelModuleHeure de FrameModule
	 * @return panelModuleHeure
	 */
	public PanelModuleHeure    getPanelModuleHeure    ( ) { return this.panelModuleHeure;    }

	/** Permet de rendre visible les panels en fonction du type de module.
	 * Si le module est un SAE ou un Stage, on rend visible les panels Bis et on rend invisible le reste.
	 * Si le module est un PPP, on rend visible les panels PPP et on rend invisible le reste.
	 * Si le module est un autre type, on rend visible les panels principaux et on rend invisible le reste.
	 * @param typeModule
	 */
	/*public void setVisiblePanels ( String typeModule )
	{
		if ( typeModule.equals ( "SAE" ) || typeModule.equals ( "Stage" ) )
		{
			this.panelRepartition   .setVisible ( false );
			this.panelRepartitionBis.setVisible ( true  );
			this.panelRepartitionPPP.setVisible ( false );

			this.panelPNLocal    .setVisible ( false );
			this.panelPNLocalBis .setVisible ( true  );
			this.panelPNLocalPPP .setVisible ( false );
		}
		else if ( typeModule.equals ( "PPP" ) )
		{
			this.panelRepartition   .setVisible ( false );
			this.panelRepartitionBis.setVisible ( false );
			this.panelRepartitionPPP.setVisible ( true  );

			this.panelPNLocal    .setVisible ( false );
			this.panelPNLocalBis .setVisible ( false );
			this.panelPNLocalPPP .setVisible ( true  );
		}
		else
		{
			this.panelRepartition   .setVisible ( true  );
			this.panelRepartitionBis.setVisible ( false );
			this.panelRepartitionPPP.setVisible ( false );

			this.panelPNLocal    .setVisible ( true  );
			this.panelPNLocalBis .setVisible ( false );
			this.panelPNLocalPPP .setVisible ( false );
		}
	}*/

	/** Permet de modifier le module avec le code passé en paramètre.
	 * On récupère le panel qui est visible grâce au code passé en paramètre afin de lui affecter les données du module en question.
	 * On affecte les données dans le tableau du panelAffectation.
	 * On valide la checkbox si le module est validé.
	 * @param code
	 */
	/*public void setModule ( String code )
	{
		ModuleIUT module = this.ctrl.getModule ( code );

		this.panelModuleLabel.setModule ( module );
		this.setVisiblePanels ( module.getTypeModule ( ) );

		if ( this.panelPNLocal   .isVisible ( ) ) this.panelPNLocal   .setModule ( module );
		if ( this.panelPNLocalBis.isVisible ( ) ) this.panelPNLocalBis.setModule ( module );
		if ( this.panelPNLocalPPP.isVisible ( ) ) this.panelPNLocalPPP.setModule ( module );
		
		if ( this.panelRepartition   .isVisible ( ) ) this.panelRepartition   .setModule ( module );
		if ( this.panelRepartitionBis.isVisible ( ) ) this.panelRepartitionBis.setModule ( module );
		if ( this.panelRepartitionPPP.isVisible ( ) ) this.panelRepartitionPPP.setModule ( module );
		
		this.panelAffectation.setDonnee ( module );

		this.cbValidation.setSelected ( module.estValide ( ) );
	}*/

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
}