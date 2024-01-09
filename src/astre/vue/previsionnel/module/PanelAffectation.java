package astre.vue.previsionnel.module;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import astre.Controleur;
import astre.modele.elements.Contrat;
import astre.modele.elements.Heure;
import astre.modele.elements.Intervenant;
import astre.modele.outils.Utilitaire;
import astre.vue.outils.Tableau;
import astre.vue.rendus.OperationRenduTableauIntervient;

/** Classe PanelAffectation
  * @author : Maxime Lemoine, Maximilien Lesterlin
  * @version : 2.0 - 14/12/2023
  * @date : 13/12/2023
  */

  public class PanelAffectation extends JPanel implements ActionListener, TableModelListener
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/
	private Controleur  ctrl;
	private FrameModule frmModule;

	private String[]    ensIntitule;

	private Tableau     tableau;
	private JScrollPane scrollPane;

	private JButton     btnAjouter;
	private JButton     btnSupprimer;



	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/

	public PanelAffectation ( FrameModule frmModule, Controleur ctrl, String typeModule, String[] ensIntitule )
	{
		this.ctrl        = ctrl;
		this.ensIntitule = ensIntitule;
		this.frmModule   = frmModule;



		this.setLayout        ( new BorderLayout ( )       );
		this.setPreferredSize ( new Dimension ( 900, 500 ) );

		/* ------------------------- */
		/* Création des composants   */
		/* ------------------------- */

		String [] enTete;
		boolean[] modifiable;
		Object [] typeDefaut;
		
		// Le nombre de colonne diffère selon le type de module
		if ( typeModule.equals ( "Ressource" ) ) 
		{
			//TODO: total eqtd devrait être en float (dans bd aussi)
			enTete     = new String [] { "action",  "idIntervenant", "Intervenant", "type", "nb sem", "nb Gp|nb H", "tot eqtd", "commentaire" };
			typeDefaut = new Object [] {      'A',                0,            "",     "",        1,            0,          0, "..."         };
			modifiable = new boolean[] {     true,            false,          true,   true,     true,         true,      false, true          };
		}
		else
		{
			enTete     = new String [] { "action",  "idIntervenant", "Intervenant", "type", "nb H", "tot eqtd", "commentaire" };
			typeDefaut = new Object [] {      'A',                0,            "",     "",    0.0,          0, "..."         };
			modifiable = new boolean[] {     true,            false,          true,   true,   true,      false, true          };
		}

		// Création du tableau
		this.tableau = Tableau.initialiserTableau ( enTete, typeDefaut, modifiable, 2, null );

		// Cas particulier pour les ressources
		if ( typeModule.equals ( "Ressource" ) )
		{
			// Permet de gérer les différents rendus des cellulese (Notamment pur nb Semaine et nb Groupe|nb Heure)
			for ( int i = 0; i < this.tableau.getColumnCount ( ); i++ )
				this.tableau.getColumnModel ( ).getColumn ( i ).setCellRenderer ( new OperationRenduTableauIntervient ( ) );
		}
		
		//Ajout d'une JComboBox pour les intervenants au tableau
		JComboBox<String> cbEditInter = new JComboBox<> ( );
		for ( Intervenant i : this.ctrl.getTable ( Intervenant.class ) )
			cbEditInter.addItem ( i.getNom ( ) + " " + i.getPrenom ( ) );

		this.tableau.getColumnModel ( ).getColumn ( 0 ).setCellEditor ( new DefaultCellEditor ( cbEditInter ) );

		cbEditInter.addActionListener ( e ->
		{
			int selectedRow = this.tableau.getSelectedRow ( );
			if ( selectedRow != -1 )
			{
				Intervenant selectedIntervenant = this.ctrl.getTable ( Intervenant.class ).get ( cbEditInter.getSelectedIndex ( ) );
				this.tableau.setValueAt ( selectedIntervenant.getId ( ), selectedRow, -1 );
			}
			} );

		//Ajout d'une JComboBox pour les intervenants au tableau
		JComboBox<String> cbEditHeure = new JComboBox<> ( );
		for ( String heure : this.ensIntitule )
		{
			cbEditHeure.addItem ( heure );
		}
		
		// Cas particulier des ressources
		if ( typeModule.equals ( "Ressource" ) ) 
			cbEditHeure.addItem ( "HP" );

		this.tableau.getColumnModel ( ).getColumn ( 1 ).setCellEditor ( new DefaultCellEditor ( cbEditHeure ) );

		this.scrollPane = new JScrollPane ( this.tableau );

		JPanel panelSud = new JPanel ( );

		this.btnAjouter   = new JButton ( "Ajouter"   );
		this.btnSupprimer = new JButton ( "Supprimer" );

		panelSud.add ( this.btnAjouter   );
		panelSud.add ( this.btnSupprimer );
		
		this.add ( this.scrollPane, BorderLayout.NORTH );
		this.add ( panelSud       , BorderLayout.SOUTH );

		/* ------------------------- */
		/* Activation des composants */
		/* ------------------------- */

		this.btnAjouter  .addActionListener ( this );
		this.btnSupprimer.addActionListener ( this );

		this.tableau.addKeyListener ( frmModule );
		this.tableau.getModel ( ).addTableModelListener ( this );
	}

	/* ActionListener */
	public void actionPerformed ( ActionEvent e )
	{
		if ( e.getSource ( ) == this.btnAjouter )
		{
			this.tableau.ajouterLigne ( );
			this.repaint ( );
		}

		if ( e.getSource ( ) == this.btnSupprimer )
		{
			this.tableau.supprimerLigne ( );
			this.repaint ( );
		}
	}

	public void setValeurs ( Object[][] tabValeurs )
	{
		this.tableau.modifDonnees ( tabValeurs );
	}

	public Map<String, Double> getSommesEQTD ( )
	{
		Map<String, Double> map = new HashMap<> ( );

		// Initialisation des entetes par défaut
		for ( String intitule : this.ensIntitule )
			map.put ( intitule, 0.0 );

		// Cas particulier pour les ressources
		map.putIfAbsent ( "HP", 0.0 );

		// Calcul des sommes par type d'heures
		// 
		for ( int cpt = 0; cpt < this.tableau.getDonnees ( ).length; cpt++ )
		{
			if ( ( char ) this.tableau.getDonnees ( ) [cpt][0] != Controleur.SUPPRIMER )
			{
				String cle = this.tableau.getValueAt ( cpt, 1 ).toString ( );
				Double val = Double.parseDouble ( this.tableau.getDonnees ( ) [cpt][6].toString ( ) );
			
				//dans le cas ou la clé est vide
				if ( ! cle.equals ( "" ) )
					map.put ( cle, map.get ( cle ) + val );
			}
		}

		return map;
	}

	/**
	 * Méthode qui calcul l'équivalent TD pour le tableau
	 */
	public void majSomme ( )
	{
		final int COLONNE_INTERVENANT = 1;
		final int COLONNE_HEURE       = 3;
		final int COLONNE_NB_SEMAINE  = 4;
		final int COLONNE_NB_GROUPE   = 5;
		final int COLONNE_EQTD        = 6;
		
		for ( int ligne = 0; ligne < this.tableau.getDonnees ( ).length; ligne++ )
		{
			if ( ( int ) this.tableau.getDonnees ( ) [ligne][COLONNE_INTERVENANT] != 0 ) 
			{
				Heure       h = this.ctrl.getHeure                          ( this.tableau.getDonnees ( ) [ligne][COLONNE_HEURE]      .toString ( )   );				
				Intervenant i = this.ctrl.getIntervenant ( Integer.parseInt ( this.tableau.getDonnees ( ) [ligne][COLONNE_INTERVENANT].toString ( ) ) );
				Contrat     c = i.getContrat ( );
				
				double coefHeure       = h.getCoefTd ( );
				double coefIntervenant = h.getNom ( ).equals ( "TP" ) ? c.getRatioTP ( ) : 1;
				double nbSemaine       = Double.parseDouble ( this.tableau.getDonnees ( ) [ligne][COLONNE_NB_SEMAINE].toString ( ) );
				double nbHeure         = h.getNom ( ).equals ( "HP" ) ? 1 : this.frmModule.getNbHeureSemaine ( h.getNom ( ) );
				double nbGroupe        = Double.parseDouble ( this.tableau.getDonnees ( ) [ligne][COLONNE_NB_GROUPE].toString ( ) );
				
				this.tableau.setValueAt ( coefHeure * coefIntervenant * nbSemaine * nbHeure * nbGroupe, ligne, COLONNE_EQTD - 2 );
			}
			
		}
	}

	@Override
	public void tableChanged ( TableModelEvent e )
	{
		this.frmModule.majIHM ( );
	}
}