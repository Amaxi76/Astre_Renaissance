package astre.vue.intervenants;

import astre.Controleur;
import astre.modele.elements.*;
import astre.modele.outils.Utilitaire;
import astre.vue.FrameAccueil;
import astre.vue.outils.*;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;

import java.awt.event.*;
import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

/** Page de gestion des intervenants
  * @author : Matéo Sa, Maxime Lemoine, Maximilien Lesterlin
  * @version : 2.0 - 21/12/23
  * @date : 06/12/2023
  */

  //TODO: Faire la fraction "2/3" - jcrois pas non

  //TODO: Afficher les heures Réelles ou Théoriques ?

public class PanelIntervenants extends JPanel implements ActionListener
{
	// paramètres du tableau
	private static final String[]  NOMS_COL   = { "action", "Id" ,"Catégorie", "Nom", "Prénom", "hServ", "hMax", "Coef TP", "S1" , "S3" , "S5" , "sTot", "S2" , "S4" , "S6" , "sTot", "Total" };
	private static final Object[]  DEFAUT_COL = { 'D'     , 0    , ""        , ""   , ""      , 0      , 0     , "0"      , 0    , 0    , 0    , 0     , 0    , 0    , 0    , 0     , 0       };
	private static final boolean[] MODIF_COL  = { false   , false, true      , true , true    , true   , true  , true     , false, false, false, false , false, false, false, false , false   };
	private static final int       DECALAGE   = 2;
	
	// requetes
	private static final String REQUETE = "v_intervenant";

	// paramètre du graphique
	private static final int TAILLE_DIAG = 150;
	
	private Tableau     tableau;
	private JScrollPane scrollPane;

	private JButton     btnAjouter;
	private JButton     btnSupprimer;
	private JButton     btnEnregistrer;
	private JButton     btnAnnuler;

	private JPanel      panelCentre;
	private JPanel      panelSud;

	private JPanel      panelDiagramme;
	private Thread      thread;

	private Controleur  ctrl;

	/**
	 * Panel pour la frame des intervenants.
	 * @author Matéo, Maxime, Maximilien
	 */
	public PanelIntervenants ( Controleur ctrl )
	{
		/*-------------*/
		/*--Attributs--*/
		/*-------------*/
		
		this.ctrl = ctrl;
		this.setLayout ( new BorderLayout ( ) );
		int marginSize = 10;
		this.setBorder ( BorderFactory.createEmptyBorder ( marginSize, marginSize, marginSize, marginSize ) );


		/* ------------------------- */
		/* Création des composants   */
		/* ------------------------- */

		//Création du tableau
		this.tableau = Tableau.initialiserTableau ( NOMS_COL, DEFAUT_COL, MODIF_COL, DECALAGE, this.ctrl.getTableauParticulier ( REQUETE ) );

		//Ajout d'une JComboBox au tableau
		JComboBox<String> cbEdit = new JComboBox<> ( );
		for ( Contrat c : this.ctrl.getTable ( Contrat.class ) )
		{
			cbEdit.addItem ( c.getNom ( ) );
		}
		this.tableau.getColumnModel ( ).getColumn ( 0 ).setCellEditor ( new DefaultCellEditor ( cbEdit ) );

		//Ajout de la barre de scroll
		this.scrollPane = new JScrollPane ( this.tableau );

		//Ajout des boutons
		this.btnAjouter     = new JButton ( "Ajouter"     );
		this.btnSupprimer   = new JButton ( "Supprimer"   );
		this.btnEnregistrer = new JButton ( "Enregistrer" );
		this.btnAnnuler     = new JButton ( "Annuler"     );

		//Ajout du diagramme
		this.panelDiagramme = new JPanel ( );

		/* ------------------------- */
		/* Placement des composants  */
		/* ------------------------- */

		//Placement des panels
		this.panelCentre = new JPanel ( );
		this.panelSud    = new JPanel ( new GridLayout ( 1, 2 ) );
		JPanel panelBtn  = new JPanel ( );

		panelCentre.setLayout ( new BorderLayout ( ) );
		panelCentre.setBorder ( BorderFactory.createEmptyBorder ( marginSize, marginSize, marginSize, marginSize ) );

		//Préparation du placement des boutons
		GroupLayout layout = new GroupLayout ( panelBtn );
		panelBtn.setLayout ( layout );

		layout.setAutoCreateGaps          ( true );
		layout.setAutoCreateContainerGaps ( true );

		//placer horizontalement les boutons
		GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup ( );

		hGroup.addGroup ( layout.createParallelGroup ( Alignment.LEADING, true ).addComponent ( this.btnAjouter   ).addComponent ( this.btnEnregistrer ) );
		hGroup.addGroup ( layout.createParallelGroup ( Alignment.LEADING, true ).addComponent ( this.btnSupprimer ).addComponent ( this.btnAnnuler     ) );
		layout.setHorizontalGroup( hGroup );

		//placer verticalement les boutons
		GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup ( );

		vGroup.addGroup ( layout.createParallelGroup ( Alignment.BASELINE, true ).addComponent ( this.btnAjouter     ).addComponent ( this.btnSupprimer ) );
		vGroup.addGap   ( 50 ); //ajouter d'un espacement
		vGroup.addGroup ( layout.createParallelGroup ( Alignment.BASELINE, true ).addComponent ( this.btnEnregistrer ).addComponent ( this.btnAnnuler   ) );
		layout.setVerticalGroup ( vGroup );

		//Mettre touts les boutons à la meme taille
		this.btnAjouter.setPreferredSize ( new Dimension ( 150, 30 ) );
		layout.linkSize ( this.btnAjouter, this.btnSupprimer, this.btnEnregistrer, this.btnAnnuler );

		//Placement des composants
		panelCentre.add ( scrollPane  , BorderLayout.CENTER );

		this.panelSud.add ( panelBtn            );
		this.panelSud.add ( this.panelDiagramme );

		this.add ( new JLabel ( "Liste des intervenants" ), BorderLayout.NORTH  );
		this.add ( panelCentre                                 , BorderLayout.CENTER );
		this.add ( panelSud                                    , BorderLayout.SOUTH  );


		/* ------------------------- */
		/* Activation des composants */
		/* ------------------------- */

		//ajout des actionListener
		this.btnAjouter    .addActionListener ( this );
		this.btnSupprimer  .addActionListener ( this );
		this.btnEnregistrer.addActionListener ( this );
		this.btnAnnuler    .addActionListener ( this );

		//ajout du mouseListener
		MouseAdapter adapter = 	new MouseAdapter ( )
		{
			@Override
			public void mouseClicked ( MouseEvent e )
			{
				if ( PanelIntervenants.this.thread != null )
					PanelIntervenants.this.thread.interrupt ( );
					
				PanelIntervenants.this.thread = new Thread ( ( ) -> {
					int ligne = PanelIntervenants.this.tableau.rowAtPoint ( e.getPoint ( ) );
					int idInter = Integer.parseInt ( tableau.getDonnees ( )[ligne][1].toString ( ) );
				
					if ( idInter > 0 ) 
					{
						changerDiagramme ( idInter );
					} 
					else 
					{
						changerDiagramme ( -1 );
					}
				} );
				PanelIntervenants.this.thread.start ( );
			}
		};

		this.tableau.addMouseListener ( adapter );
	}

	/**
	 * Actions sur les boutons ajouter, supprimer, enregistrer, annuler
	 */
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
			this.tableau.ajusterTailleColonnes ( );
			this.repaint ( );
		}

		if ( e.getSource ( ) == this.btnEnregistrer )
		{
			Object[][] tab = this.preparerTableau ( this.tableau.getDonnees ( ) );

			this.ctrl.majTableauBD ( tab, Intervenant.class );
			
			this.tableau.ajusterTailleColonnes ( );

			this.tableau.modifDonnees ( this.ctrl.getTableauParticulier ( REQUETE ) );
		}

		if ( e.getSource (  ) == this.btnAnnuler )
		{
			this.tableau.modifDonnees ( this.ctrl.getTableauParticulier ( REQUETE ) );
		}
	}

	/**
	 * Raccourcir le tableau de l'affichage pour mettre à jour la base de données
	 */
	private Object[][] preparerTableau ( Object[][] tab  )
	{
		// Enlever les colonnes en trop
		Object[][] tab2 = Utilitaire.formater ( tab, 7 );
		
		// Remplacer les noms de contrat par les objets contrat
		int COLONNE_CONTRAT = 2;
		
		for ( int lig = 0; lig < tab.length; lig++ )
		{
			tab2[lig][COLONNE_CONTRAT] = this.ctrl.getContrat ( tab2[lig][COLONNE_CONTRAT].toString ( ) );
		}
		
		// Replacer les objets dans le bon ordre pour le constructeur
		for ( int lig = 0; lig < tab2.length; lig++ )
		{
			Object tmp = tab2[lig][2];
			
			tab2[lig][2] = tab[lig][3];
			tab2[lig][3] = tab[lig][4];
			tab2[lig][4] = tmp;
		}
		
		return tab2;
	}

	/**
	 * Maj du diagramme en fonction de la ligne cliquée
	 */
	public void changerDiagramme ( int idInter )
	{
		JPanel diagramme;
		if ( idInter == -1 )
			diagramme = new JPanel ( );
		else
			diagramme = PanelDiagramme.genererCamembert ( idInter, PanelIntervenants.TAILLE_DIAG );

		this.panelSud.remove ( this.panelDiagramme );
		this.panelDiagramme = diagramme;
		this.panelSud.add    ( this.panelDiagramme );

		revalidate ( );
	}
}
