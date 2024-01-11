package astre.vue.previsionnel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import astre.Controleur;
import astre.modele.elements.Semestre;
import astre.vue.outils.ConstantesVue;
import astre.vue.outils.Saisie;
import astre.vue.outils.Tableau;
import astre.vue.previsionnel.module.sansGroupe.PanelRepartitionSansGroupes;
import astre.vue.rendus.OperationRenduTableauIntervenants;
import astre.vue.rendus.OperationRenduTableauSemestre;

/** Classe PanelEnsSemestre
  * @author : Maximeuuu et Amaxi76
  * @version : 1.0 le 11/12/23
  * @date : 06/12/2023
  */

public class PanelSemestre extends JPanel implements ActionListener
{
	private Controleur ctrl;
	private int        numSemestre;

	private JTextField txtNbGpTD;
	private JTextField txtNbGpTP;
	private JTextField txtNbEtud;
	private JTextField txtNbSemaine;
	private JButton    btnEnregistrer;
	private Timer      timerMessageEnregistrement;

	private JLabel     lblMessageEnregistrement;

	private Tableau    tableauEnsembleModule;
	
	public PanelSemestre ( int numSemestre, Controleur ctrl )
	{
		this.ctrl        = ctrl;
		this.numSemestre = numSemestre;

		this.setLayout ( new BorderLayout ( ) );

		JPanel pnlOptionSemestre = new JPanel ( new FlowLayout ( ) );

		/* ----------------------------- */
		/*    Création des composants    */
		/* ----------------------------- */
		this.txtNbGpTD      = Saisie.creerTextFieldEntier ( true );
		this.txtNbGpTP      = Saisie.creerTextFieldEntier ( true );
		this.txtNbEtud      = Saisie.creerTextFieldEntier ( true );
		this.txtNbSemaine   = Saisie.creerTextFieldEntier ( true );
		this.btnEnregistrer = new JButton ( "Enregistrer" );

		//Met un délai de 3 secondes sur le message d'enregistrement
		this.timerMessageEnregistrement = new Timer ( 3000, new ActionListener ( )
		{
			public void actionPerformed ( ActionEvent e )
			{
				lblMessageEnregistrement.setText ( "" );
				timerMessageEnregistrement.stop ( );
			}
		} );

		this.lblMessageEnregistrement = new JLabel ( "" );

		// Affecte toutes les valeurs de la base de donnée aux JTextField
		this.txtNbGpTD   .setText ( "" + this.ctrl.getSemestre ( this.numSemestre ).getNbGroupeTD ( ) );
		this.txtNbGpTP   .setText ( "" + this.ctrl.getSemestre ( this.numSemestre ).getNbGroupeTP ( ) );
		this.txtNbEtud   .setText ( "" + this.ctrl.getSemestre ( this.numSemestre ).getNbEtudiant ( ) );
		this.txtNbSemaine.setText ( "" + this.ctrl.getSemestre ( this.numSemestre ).getNbSemaine  ( ) );

		// Définie la taille du JTextField en fonction de la constante saisie dans la classe dédiée
		this.txtNbGpTD   .setColumns ( ConstantesVue.LARGEUR_COLONNE_NOMBRE );
		this.txtNbGpTP   .setColumns ( ConstantesVue.LARGEUR_COLONNE_NOMBRE );
		this.txtNbEtud   .setColumns ( ConstantesVue.LARGEUR_COLONNE_NOMBRE );
		this.txtNbSemaine.setColumns ( ConstantesVue.LARGEUR_COLONNE_NOMBRE );

		// Ajout d'une bordure vide pour aerer et améliorer l'ergonomie
		JPanel pnlListeModule = new JPanel ( new BorderLayout ( ) );
		pnlListeModule.setBorder ( new EmptyBorder( 0, 10, 10, ConstantesVue.MARGE_EXTERIEURE_COMPOSANT ) );

		Object[] typeDefaut = { "", 0, "", "", "", false };
		this.tableauEnsembleModule = Tableau.initialiserTableau ( null, typeDefaut, false, 2, this.ctrl.getTableauParticulier ( "f_selectModuleParSemestre(" + numSemestre + ")" ) );
		this.tableauEnsembleModule.setShowGrid ( false );
		this.tableauEnsembleModule.setIntercellSpacing ( new Dimension ( 0, 0 ) );

		for ( int i = 0; i < this.tableauEnsembleModule.getColumnCount ( ); i++ )
			this.tableauEnsembleModule.getColumnModel ( ).getColumn ( i ).setCellRenderer ( new OperationRenduTableauSemestre ( ) );

		// Ajout du titre et rend la liste défilable
		JScrollPane spTab = new JScrollPane ( this.tableauEnsembleModule );
		spTab.setBorder                  ( new TitledBorder ( "Liste des modules" )      );
		spTab.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
		
		/* ----------------------------- */
		/* Positionnement des composants */
		/* -----------------------    -- */

		pnlOptionSemestre.add ( new JLabel ( "nb gp TD"    )  );
		pnlOptionSemestre.add ( this.txtNbGpTD                );
		pnlOptionSemestre.add ( new JLabel ( "nb gp TP"    )  );
		pnlOptionSemestre.add ( this.txtNbGpTP                );
		pnlOptionSemestre.add ( new JLabel ( "nb Etd"      )  );
		pnlOptionSemestre.add ( this.txtNbEtud                );
		pnlOptionSemestre.add ( new JLabel ( "nb semaines" )  );
		pnlOptionSemestre.add ( this.txtNbSemaine             );
		pnlOptionSemestre.add ( this.btnEnregistrer           );
		pnlOptionSemestre.add ( this.lblMessageEnregistrement );

		pnlListeModule.add ( spTab, BorderLayout.CENTER );

		this.add ( pnlOptionSemestre, BorderLayout.NORTH  );
		this.add ( pnlListeModule   , BorderLayout.CENTER );

		/* ----------------------------- */
		/*   Activation des composants   */
		/* ----------------------------- */

		this.txtNbGpTD     .addActionListener ( this );
		this.txtNbGpTP     .addActionListener ( this );
		this.txtNbEtud     .addActionListener ( this );
		this.txtNbSemaine  .addActionListener ( this );
		this.btnEnregistrer.addActionListener ( this );
	}

	/**
	 * @return the tableauEnsembleModule
	 */
	public Tableau getTableauEnsembleModule ( ) 
	{
		return this.tableauEnsembleModule;
	}

	public void actionPerformed ( ActionEvent e )
	{
		if ( e.getSource ( ) == this.btnEnregistrer )
		{
			int nbGpTD = Integer.parseInt ( this.txtNbGpTD   .getText ( ) );
			int nbGpTP = Integer.parseInt ( this.txtNbGpTP   .getText ( ) );
			int nbEtud = Integer.parseInt ( this.txtNbEtud   .getText ( ) );
			int nbSem  = Integer.parseInt ( this.txtNbSemaine.getText ( ) );

			// Mise à jour de la base de donnée
			this.ctrl.update ( new Semestre ( this.numSemestre, nbGpTP, nbGpTD, nbEtud, nbSem ) );

			// Affichage du message d'enregistrement pendant 3 secondes
			this.lblMessageEnregistrement.setText ( "Enregistré !" );
			timerMessageEnregistrement.start ( );
		}
	}

	public String getModuleSelection ( )
	{
		if ( this.tableauEnsembleModule.getSelectedRow ( ) != -1)
		{
			return this.tableauEnsembleModule.getValueAt ( this.tableauEnsembleModule.getSelectedRow ( ), 0 ).toString ( );
		}
		return "pas de selection";
	}
	
	public void majTableau ( )
	{
		this.tableauEnsembleModule.modifDonnees ( this.ctrl.getTableauParticulier ( "f_selectModuleParSemestre(" + numSemestre + ")" ) );
	}
}
