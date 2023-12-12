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

		this.btnAjouter     = new JButton ( "Ajouter"     );
		this.btnSupprimer   = new JButton ( "Supprimer"   );
		this.btnEnregistrer = new JButton ( "Enregistrer" );
		this.btnAnnuler     = new JButton ( "Annuler"     );
		
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

		ArrayList<Intervenant> lstBD = (ArrayList<Intervenant>) bd.getIntervenants();
		
		for (int i = 0; i < deuxieme.length; i++)
		{
			Intervenant inter = new Intervenant(i+1, deuxieme[i][1].toString(), deuxieme[i][2].toString(), bd.getContrat(deuxieme[i][0].toString()), Integer.parseInt(deuxieme[i][3].toString()), Integer.parseInt(deuxieme[i][4].toString()) );
			lst.add(new Intervenant(0, deuxieme[i][1].toString(), deuxieme[i][2].toString(), bd.getContrat(deuxieme[i][0].toString()), Integer.parseInt(deuxieme[i][3].toString()), Integer.parseInt(deuxieme[i][4].toString()) ));

			boolean up = false;
			for(Intervenant venant : lstBD)
			{
				if(inter.equals(venant))
				{
					up = true;
				}
			}

			if(up)
			{
				bd.update(inter);
				System.out.println("Ligne " + i + " doit etre modifiée.");
			}
			else
			{
				bd.insert(inter);
				System.out.println("Ligne " + i + " doit etre ajouter.");
			}
			
			/*if( bd.existe(inter) )
			{
				bd.update(inter);
				System.out.println("Ligne " + i + " doit etre modifiée.");
			}
			else
			{
				bd.insert(inter);
				System.out.println("Ligne " + i + " doit etre ajouter.");
			}*/
		}

		for(Intervenant venant : lstBD)
		{
			boolean del = true;
			for(Intervenant inter : lst)
			{
				if(inter.equals(venant))
				{
					del = false;
				}
			}

			if(del)
			{
				bd.deleteIntervenantNomPrenom(venant);
				System.out.println("Ligne ? doit etre supprimée.");
			}
		}

		/*for (int i = 0; i < premier.length; i++)
		{
			Intervenant inter = new Intervenant(0, premier[i][1].toString(), premier[i][2].toString(), bd.getContrat(premier[i][0].toString()), Integer.parseInt(premier[i][3].toString()), Integer.parseInt(premier[i][4].toString()) );
			
			boolean del = true;
			for(Intervenant venant : lst)
			{
				if(inter.equals(venant))
				{
					del = false;
				}
			}

			if(del)
			{
				bd.deleteIntervenantNomPrenom(inter);
				System.out.println("Ligne " + i + " doit etre supprimée.");
			}
			
			/*if(!lst.contains(inter))
			{
				//bd.delete(inter);
				System.out.println(inter.equals(lst.get(0)));
				System.out.println("Ligne " + i + " doit etre supprimée.");
			}
		}*/
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
