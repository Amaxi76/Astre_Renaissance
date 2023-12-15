package astre.vue.intervenants;

import astre.Controleur;
import astre.modele.elements.*;

/** Page de gestion des intervenants
  * @author : Matéo Sa
  * @version : 1.0 - 11/12/2023
  * @date : 06/12/2023
  */

import astre.vue.outils.*;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.BorderLayout;

public class PanelIntervenants extends JPanel implements ActionListener
{
	private Tableau tableau;
	private JScrollPane scrollPane;
	
	private JButton btnAjouter;
	private JButton btnSupprimer;
	
	private JButton btnEnregistrer;
	private JButton btnAnnuler;

	private JPanel panelCentre;

	private Controleur ctrl;
	
	/**
	 * 	Panel pour la frame des intervenants..
	 * @author Matéo
	 */
	public PanelIntervenants ( Controleur ctrl )
	{
		this.ctrl = ctrl;

		this.setLayout ( new BorderLayout ( ) );
		int marginSize = 10;
		this.setBorder ( BorderFactory.createEmptyBorder ( marginSize, marginSize, marginSize, marginSize ) );
		
		//création des composants
		String[] noms = {"Id", "Catégorie", "Nom", "Prénom", "hServ", "hMax", "Coef TP", "S1", "S3", "S5", "sTot", "S2", "S4", "S6", "sTot", "Total" };

		//Création du tableau
		this.tableau = new Tableau ( noms, this.ctrl.getTableauIntervenant ( ), 1 );

		//Ajout d'une JComboBox au tableau
		JComboBox<String> cbEdit = new JComboBox<> ( );
		for ( Contrat c : this.ctrl.getContrats ( ) )
		{
			cbEdit.addItem ( c.getNom ( ) );
		}
		this.tableau.getColumnModel ( ).getColumn (0 ).setCellEditor ( new DefaultCellEditor ( cbEdit ) );

		//Parametres du tableau
		this.tableau.setEditable( new int[] {0, 1, 2, 3, 4} );
		this.tableau.ajusterTailleColonnes ( );

		this.btnAjouter     = new JButton ( "Ajouter"     );
		this.btnSupprimer   = new JButton ( "Supprimer"   );
		this.btnEnregistrer = new JButton ( "Enregistrer" );
		this.btnAnnuler     = new JButton ( "Annuler"     );
		
		this.panelCentre = new JPanel( );
		panelCentre.setLayout ( new BorderLayout ( ) );
		panelCentre.setBorder ( BorderFactory.createEmptyBorder ( marginSize, marginSize, marginSize, marginSize ) );
		
		JPanel panelCentre2 = new JPanel( );
		JPanel panelSud     = new JPanel( );
		
		this.scrollPane = new JScrollPane ( this.tableau );
		
		//Ajout des composants
		panelCentre2.add ( this.btnAjouter   );
		panelCentre2.add ( this.btnSupprimer );
		
		panelCentre.add ( panelCentre2, BorderLayout.SOUTH );
		panelCentre.add ( scrollPane, BorderLayout.CENTER  );
		
		panelSud.add ( this.btnEnregistrer );
		panelSud.add ( this.btnAnnuler     );
		
		this.add ( new JLabel ( "Liste des intervenants" ), BorderLayout.NORTH );
		this.add ( panelCentre, BorderLayout.CENTER );
		this.add ( panelSud   , BorderLayout.SOUTH  );
		
		//met les actionListener
		this.btnAjouter    .addActionListener ( this );
		this.btnSupprimer  .addActionListener ( this );
		this.btnEnregistrer.addActionListener ( this );
		this.btnAnnuler    .addActionListener ( this );
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
			this.repaint ( );
		}
		
		if ( e.getSource ( ) == this.btnEnregistrer )
		{
			if( enregistrer( this.tableau.getDonnees ( ) ) )
			{
				this.tableau.modifDonnees ( this.ctrl.getTableauIntervenant ( ) );
			}
			this.tableau.ajusterTailleColonnes ( );
		}
		
		if ( e.getSource (  ) == this.btnAnnuler )
		{
			this.tableau.modifDonnees ( this.ctrl.getTableauIntervenant ( ) );
			this.tableau.ajusterTailleColonnes ( );
		}
	}

	public boolean enregistrer ( Object[][] deuxieme )
	{
		ArrayList<Intervenant> lst = new ArrayList<Intervenant> ( );
		ArrayList<Intervenant> lstBD = (ArrayList<Intervenant>) this.ctrl.getIntervenants();
		
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
				inter = new Intervenant ( 0, deuxieme[i][2].toString ( ), deuxieme[i][3].toString ( ), this.ctrl.getContrat ( deuxieme[i][1].toString ( ) ), Integer.parseInt ( deuxieme[i][4].toString ( ) ), Integer.parseInt ( deuxieme[i][5].toString ( ) ) );
			else
				inter = new Intervenant ( Integer.parseInt ( deuxieme[i][0].toString ( ) ), deuxieme[i][2].toString ( ), deuxieme[i][3].toString ( ), this.ctrl.getContrat ( deuxieme[i][1].toString ( ) ), Integer.parseInt ( deuxieme[i][4].toString ( ) ), Integer.parseInt ( deuxieme[i][5].toString ( ) ) );
			
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
	}
}
