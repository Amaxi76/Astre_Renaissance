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
	private TableauIntervenant tableau;
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

		this.setLayout ( new BorderLayout() );
		int marginSize = 10;
		this.setBorder ( BorderFactory.createEmptyBorder ( marginSize, marginSize, marginSize, marginSize ) );
		
		//création des composants
		String[] noms = {"Id", "Catégorie", "Nom", "Prénom", "hServ", "hMax", "Coef TP", "S1", "S3", "S5", "sTot", "S2", "S4", "S6", "sTot", "Total" };
		//this.tableau = new Tableau(noms);

		this.tableau = new TableauIntervenant ( noms, this.ctrl.getTableauIntervenant() );
		this.tableau.ajusterTailleColonnes();

		this.btnAjouter     = new JButton ( "Ajouter"     );
		this.btnSupprimer   = new JButton ( "Supprimer"   );
		this.btnEnregistrer = new JButton ( "Enregistrer" );
		this.btnAnnuler     = new JButton ( "Annuler"     );
		
		this.panelCentre = new JPanel( );
		panelCentre.setLayout ( new BorderLayout() );
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
			if(!enregistrer( this.tableau.getDonnees() ))
			{
				Controleur.afficherErreur("Enregistrement impossible", "Une ligne vide ne peut pas être enregistrer");
			}
			
		}
		
		if ( e.getSource() == this.btnAnnuler )
		{
			this.tableau.modifDonnees(this.ctrl.getTableauIntervenant());
		}
	}

	public boolean enregistrer(Object[][] deuxieme)
	{
		ArrayList<Intervenant> lst = new ArrayList<Intervenant>();
		BD bd = BD.getInstance();//a modif ptet

		ArrayList<Intervenant> lstBD = (ArrayList<Intervenant>) bd.getIntervenants();
		
		Intervenant inter = null;
		for (int i = 0; i < deuxieme.length; i++)
		{
			for(int cpt=1; cpt < deuxieme[0].length; cpt++)
			{
				System.out.println(deuxieme[i][cpt].toString());
				
				if(deuxieme[i][cpt].toString().equals(""))
					return false; //faire une popup ? ou bien continuer en ignorant la ligne ?
			}

			if(deuxieme[i][0].toString().equals(""))
				inter = new Intervenant(0, deuxieme[i][2].toString(), deuxieme[i][3].toString(), bd.getContrat(deuxieme[i][1].toString()), Integer.parseInt(deuxieme[i][4].toString()), Integer.parseInt(deuxieme[i][5].toString()) );
			else
				inter = new Intervenant(Integer.parseInt(deuxieme[i][0].toString()), deuxieme[i][2].toString(), deuxieme[i][3].toString(), bd.getContrat(deuxieme[i][1].toString()), Integer.parseInt(deuxieme[i][4].toString()), Integer.parseInt(deuxieme[i][5].toString()) );
			
			lst.add(inter);

			boolean up = false;
			for(Intervenant venant : lstBD)
			{
				if(inter.getId() == venant.getId())
				{
					up = true;
				}
			}

			if(up)
			{
				bd.update(inter);
			}
			else
			{
				bd.insert(inter);
			}
		}

		for(Intervenant venant : lstBD)
		{
			boolean del = true;
			for(Intervenant venant2 : lst)
			{
				if(venant2.getId() == venant.getId())
				{
					del = false;
					break;
				}
				System.out.println(venant2.getId() +" "+venant.getId());
			}

			if(del)
			{
				bd.delete(venant);
			}
		}
		return true;
	}
}
