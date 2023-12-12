package astre.vue.intervenants;

import astre.Controleur;
import astre.modele.BD;
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
	
	private JButton btnAjouter;
	private JButton btnSupprimer;
	
	private JButton btnEnregistrer;
	private JButton btnAnnuler;

	private Controleur ctrl;
	
	/**
	 * 	Panel pour la frame des intervenants..
	 * @author Matéo
	 */
	public PanelIntervenants ( Controleur ctrl )
	{
		this.ctrl = ctrl;
		
		this.setLayout ( new BorderLayout() );
		int marginSize = 10;
		this.setBorder ( BorderFactory.createEmptyBorder ( marginSize, marginSize, marginSize, marginSize ) );
		
		//création des composants
		String[] noms = { "Catégorie", "Nom", "Prénom", "hServ", "hMax", "Coef TP", "S1", "S3", "S5", "sTot", "S2", "S4", "S6", "sTot", "Total" };
		//this.tableau = new Tableau(noms);

		this.tableau = new Tableau ( noms, this.ctrl.getTableauIntervenant(), true );
		this.tableau.ajusterTailleColonnes();

		this.btnAjouter     = new JButton ( "Ajouter" );
		this.btnSupprimer   = new JButton ( "Supprimer" );
		this.btnEnregistrer = new JButton ( "Enregistrer" );
		this.btnAnnuler     = new JButton ( "Annuler" );
		
		JPanel panelCentre = new JPanel( );
		panelCentre.setLayout ( new BorderLayout() );
		panelCentre.setBorder ( BorderFactory.createEmptyBorder ( marginSize, marginSize, marginSize, marginSize ) );
		
		JPanel panelCentre2 = new JPanel( );
		JPanel panelSud     = new JPanel( );
		
		JScrollPane scrollPane = new JScrollPane ( this.tableau );
		
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
		if ( e.getSource() == this.btnAjouter )
		{
			this.tableau.ajouterLigne();
			this.repaint();
		}
		
		if ( e.getSource() == this.btnSupprimer )
		{
			this.tableau.supprimerLigne();
			this.repaint();
		}
		
		if ( e.getSource() == this.btnEnregistrer )
		{
			comparerTableaux2(this.ctrl.getTableauIntervenant(), this.tableau.getDonnees());
		}
		
		if ( e.getSource() == this.btnAnnuler )
		{
			String[] noms = { "Catégorie", "Nom", "Prénom", "hServ", "hMax", "Coef TP", "S1", "S3", "S5", "sTot", "S2", "S4", "S6", "sTot", "Total" };
			this.tableau = new Tableau(noms, this.ctrl.getTableauIntervenant(), true);
			this.tableau.ajusterTailleColonnes();
		}
	}

	public void comparerTableaux(Object[][] premier, Object[][] deuxieme)
	{
        for (int i = 0; i < premier.length; i++)
		{
            if (i < deuxieme.length)
			{
                // Comparaison des lignes existantes dans les deux tableaux
                if (!compareLignes(premier[i], deuxieme[i]))
				{
                    System.out.println("Ligne " + i + " a été modifiée.");
                }
            }
			else
			{
                // La ligne existe dans le premier tableau mais pas dans le deuxième
                System.out.println("Ligne " + i + " a été supprimée.");
            }
        }

        // Vérification des lignes ajoutées dans le deuxième tableau
        for (int i = premier.length; i < deuxieme.length; i++)
		{
            System.out.println("Ligne " + i + " a été ajoutée.");
        }
    }

	public void comparerTableaux2(Object[][] premier, Object[][] deuxieme)
	{
		ArrayList<Intervenant> lst = new ArrayList<Intervenant>();
		BD bd = BD.getInstance();//a modif ptet
		
		for (int i = 0; i < deuxieme.length; i++)
		{
			Intervenant inter = new Intervenant(i, (String)deuxieme[i][1], (String)deuxieme[i][2], null, (int)deuxieme[i][3], (int)deuxieme[i][4]);
			
			if( bd.existe(inter) )
			{
				//bd.update(inter);
				System.out.println("Ligne " + i + " doit etre modifiée.");
			}
			else
			{
				//bd.insert(inter);
				System.out.println("Ligne " + i + " doit etre ajouter.");
			}
		}

		for (int i = 0; i < premier.length; i++)
		{
			Intervenant inter = new Intervenant(i, (String)premier[i][1], (String)premier[i][2], null, (int)premier[i][3], (int)premier[i][4]);
			if(!lst.contains(inter))
			{
				//bd.delete(inter);
				System.out.println("Ligne " + i + " doit etre supprimée.");
			}
		}
	}

    public boolean compareLignes(Object[] ligne1, Object[] ligne2)
	{
        for (int i = 0; i < ligne1.length; i++)
		{
            if (!ligne1[i].equals(ligne2[i]))
			{
                return false; // Au moins un élément est différent
            }
        }
        return true; // Tous les éléments sont identiques
    }
}
