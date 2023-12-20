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
  * @author : Matéo Sa
  * @version : 1.0 - 11/12/2023
  * @date : 06/12/2023
  */

  //TODO: Faire la fraction "2/3"

public class PanelIntervenants extends JPanel implements ActionListener
{
	private static final int TAILLE_DIAG = 150;
	
	private Tableau     tableau;
	private JScrollPane scrollPane;

	private JButton     btnAjouter;
	private JButton     btnSupprimer;
	private JButton     btnEnregistrer;
	private JButton     btnAnnuler;

	private JPanel      panelCentre;
	private JPanel      panelSud;

	private PanelDiagramme panelDiagramme;

	private Controleur  ctrl;

	/**
	 * 	Panel pour la frame des intervenants.
	 * @author Matéo
	 */
	public PanelIntervenants ( Controleur ctrl )
	{
		this.ctrl = ctrl;

		this.setLayout ( new BorderLayout ( ) );
		int marginSize = 10;
		this.setBorder ( BorderFactory.createEmptyBorder ( marginSize, marginSize, marginSize, marginSize ) );

		//création des composants
		String[] noms    = { "action", "Id","Catégorie", "Nom", "Prénom", "hServ", "hMax", "Coef TP", "S1", "S3", "S5", "sTot", "S2", "S4", "S6", "sTot", "Total" };
		Object[] defauts = { 'D', 0, "", "", "", 0, 0, 0.0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

		//Création du tableau
		this.tableau = Tableau.initialiserTableau ( noms, defauts, true, 2, this.ctrl.getTableauParticulier ( "v_intervenant" ) );

		//Ajout d'une JComboBox au tableau
		JComboBox<String> cbEdit = new JComboBox<> ( );
		for ( Contrat c : this.ctrl.getTable ( Contrat.class ) )
		{
			cbEdit.addItem ( c.getNom ( ) );
		}
		this.tableau.getColumnModel ( ).getColumn ( 0 ).setCellEditor ( new DefaultCellEditor ( cbEdit ) );

		//Parametres du tableau
		//this.tableau.setEditable ( new boolean[] { true, true, true, true, true } ); TODO: faire en sorte que le tablo soit modifiable mais pas partout

		this.scrollPane = new JScrollPane ( this.tableau );

		this.btnAjouter     = new JButton ( "Ajouter"     );
		this.btnSupprimer   = new JButton ( "Supprimer"   );
		this.btnEnregistrer = new JButton ( "Enregistrer" );
		this.btnAnnuler     = new JButton ( "Annuler"     );

		this.panelDiagramme = new PanelDiagramme ( );

		this.panelCentre = new JPanel ( );
		this.panelSud  = new JPanel ( new GridLayout(1, 2) );
		JPanel panelBtn  = new JPanel ( );

		panelCentre.setLayout ( new BorderLayout ( ) );
		panelCentre.setBorder ( BorderFactory.createEmptyBorder ( marginSize, marginSize, marginSize, marginSize ) );

		//Placer les boutons de facon bien mis
		GroupLayout layout = new GroupLayout ( panelBtn );
		panelBtn.setLayout ( layout );

		layout.setAutoCreateGaps ( true );
		layout.setAutoCreateContainerGaps ( true );

		//placer horizontalement les boutons
		GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup ( );

		hGroup.addGroup ( layout.createParallelGroup ( Alignment.LEADING, true ).addComponent ( this.btnAjouter   ).addComponent ( this.btnEnregistrer ) );
		hGroup.addGroup ( layout.createParallelGroup ( Alignment.LEADING, true ).addComponent ( this.btnSupprimer ).addComponent ( this.btnAnnuler     ) );
		layout.setHorizontalGroup( hGroup );

		//placer verticalement les boutons
		GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup ( );

		vGroup.addGroup ( layout.createParallelGroup ( Alignment.BASELINE, true ).addComponent ( this.btnAjouter     ).addComponent ( this.btnSupprimer ) );
		vGroup.addGap ( 50 ); //ajouter d'un espacement
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
		this.add ( panelCentre                            , BorderLayout.CENTER );
		this.add ( panelSud                               , BorderLayout.SOUTH  );

		//met les actionListener
		this.btnAjouter    .addActionListener ( this );
		this.btnSupprimer  .addActionListener ( this );
		this.btnEnregistrer.addActionListener ( this );
		this.btnAnnuler    .addActionListener ( this );

		this.tableau.addMouseListener( new MouseAdapter ( )
		{
			@Override
			public void mouseClicked ( MouseEvent e )
			{
				int ligne = PanelIntervenants.this.tableau.rowAtPoint ( e.getPoint ( ) );

				int idInter =  Integer.parseInt ( tableau.getDonnees ( )[ligne][1].toString ( ) );
				if ( idInter > 0  )
					changerDiagramme ( idInter );
				else
					PanelIntervenants.this.panelDiagramme = new PanelDiagramme ( );
			}
		});
	}

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
			Object[][] tab = this.preparerTableau ( this.tableau .getDonnees ( ) );

			this.ctrl.majTableauBD ( tab, Intervenant.class );
			this.tableau.ajusterTailleColonnes ( );

			this.tableau.modifDonnees ( this.ctrl.getTableau ( Intervenant.class ) );
		}

		if ( e.getSource (  ) == this.btnAnnuler )
		{
			( ( JFrame ) ( this.getParent ( ).getParent ( ).getParent ( ).getParent ( ) ) ).dispose ( );
			new FrameAccueil ( this.ctrl );
		}
	}

	private Object[][] preparerTableau ( Object[][] tab  )
	{
		// Enlever les colonnes en trop
		Object[][] tab2 = Utilitaire.formater ( tab, 7 );
		
		// Remplacer les noms de contrat par les objets contrat
		int COLONNE_CONTRAT = 2;
		
		for ( int lig = 0; lig < tab.length; lig++ )
			tab2[lig][COLONNE_CONTRAT] = this.ctrl.getContrat ( tab2[lig][COLONNE_CONTRAT].toString ( ) );
		
		// Replacer les objets dans le bon ordre pour le constructeur
		for ( int lig = 0; lig < tab2.length; lig++ )
		{
			Object tmp = tab2[lig][2];
			
			tab2[lig][2] = tab[lig][3];
			tab2[lig][3] = tab[lig][4];
			tab2[lig][4] = tmp;
		}
			
		

		System.out.println(Utilitaire.afficherValeurs(tab2));
		System.out.println(Utilitaire.afficherTypes(tab2));
		return tab2;
	}

	/*public boolean enregistrer ( Object[][] deuxieme )
	{
		ArrayList<Intervenant> lst = new ArrayList<> ( );
		ArrayList<Intervenant> lstBD = ( ArrayList<Intervenant> ) this.ctrl.getTable ( Intervenant.class );

		//Pour tout intervenant dans le nouveau tab, si ID existe dans BD alors update la ligne sinon insert la ligne
		Intervenant inter = null;
		for ( int i = 0; i < deuxieme.length; i++ )
		{
			//verif des erreurs
			for ( int cpt=1; cpt < deuxieme[0].length; cpt++ )
			{
				//cases vides
				if ( deuxieme[i][cpt].toString ( ).equals ( "" ) )
				{
					Controleur.afficherErreur("Enregistrement impossible", "La colonne " + cpt + " de la ligne " + (i + 1) + " est vide");
					return false;
				}

				//hserv < 0 ou hmax < 0
				if ( Integer.parseInt ( deuxieme[i][4].toString ( ) ) < 0 || Integer.parseInt ( deuxieme[i][5].toString ( ) ) < 0 )
				{
					Controleur.afficherErreur("Enregistrement impossible", "Les heures de services et heures max doivent etre touts 2 supérieur à 0 sur la ligne " + (i + 1) );
					return false;
				}

				//hserv > hmax
				if ( Integer.parseInt ( deuxieme[i][4].toString ( ) ) > Integer.parseInt ( deuxieme[i][5].toString ( ) ) )
				{
					Controleur.afficherErreur("Enregistrement impossible", "Les heures de services sont supérieur à ses heures max sur la ligne " + (i + 1) );
					return false;
				}

			}

			//si pas ID creer une ID à 0
			if ( deuxieme[i][0].toString ( ).equals ("" ) )
				inter = Intervenant.creation ( 0, deuxieme[i][2].toString ( ), deuxieme[i][3].toString ( ), this.ctrl.getContrat ( deuxieme[i][1].toString ( ) ), Integer.parseInt ( deuxieme[i][4].toString ( ) ), Integer.parseInt ( deuxieme[i][5].toString ( ) ) );
			else
				inter = Intervenant.creation ( Integer.parseInt ( deuxieme[i][0].toString ( ) ), deuxieme[i][2].toString ( ), deuxieme[i][3].toString ( ), this.ctrl.getContrat ( deuxieme[i][1].toString ( ) ), Integer.parseInt ( deuxieme[i][4].toString ( ) ), Integer.parseInt ( deuxieme[i][5].toString ( ) ) );

			//Ajout a une liste pour les suppression apres
			lst.add ( inter );

			boolean up = false;
			for ( Intervenant venant : lstBD )
			{
				if ( inter.getId ( ) == venant.getId ( ) )
				{
					up = true;
				}
			}

			if ( up )
			{
				this.ctrl.update(inter);
			}
			else
			{
				this.ctrl.insert(inter);
			}
		}

		//Pour tout intervenant dans BD, si ID dans nouveau tab alors garder l'intervenant
		for ( Intervenant venant : lstBD )
		{
			boolean del = true;
			for ( Intervenant venant2 : lst )
			{
				if ( venant2.getId ( ) == venant.getId ( ) )
				{
					del = false;
					break;
				}
			}

			if ( del )
			{
				this.ctrl.delete(venant);
			}
		}
		return true;
	}*/

	public void changerDiagramme ( int idInter )
	{
		PanelDiagramme diagramme = PanelDiagramme.genererCamembert ( idInter, PanelIntervenants.TAILLE_DIAG );
		System.out.println("bbb");

		this.panelSud.remove(this.panelDiagramme);
		this.panelDiagramme = diagramme;
		this.panelSud.add(this.panelDiagramme);

		this.panelSud.repaint();
		this.repaint ( );
		//TODO voir repaint graphique
	}
}
