package astre.vue.intervenants;

import astre.Controleur;

/** Page de gestion des intervenants
  * @author : Matéo Sa
  * @version : 1.0 - 11/12/2023
  * @date : 06/12/2023
  */

import astre.vue.outils.*;

import javax.swing.*;
import java.awt.event.*;
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
		
		this.setLayout( new BorderLayout() );
		int marginSize = 10;
		this.setBorder(BorderFactory.createEmptyBorder(marginSize, marginSize, marginSize, marginSize));
		
		//création des composants
		String[] noms = { "Catégorie", "Nom", "Prénom", "hServ", "hMax", "Coef TP", "S1", "S3", "S5", "sTot", "S2", "S4", "S6", "sTot", "Total" };
		//this.tableau = new Tableau(noms);

		this.tableau = new Tableau(noms, this.ctrl.getTableauIntervenant());
		
		this.btnAjouter = new JButton( "Ajouter" );
		this.btnSupprimer = new JButton( "Supprimer" );
		this.btnEnregistrer = new JButton( "Enregistrer" );
		this.btnAnnuler = new JButton( "Annuler" );
		
		JPanel panelCentre = new JPanel();
		panelCentre.setLayout( new BorderLayout() );
		panelCentre.setBorder(BorderFactory.createEmptyBorder(marginSize, marginSize, marginSize, marginSize));
		
		JPanel panelCentre2 = new JPanel();
		JPanel panelSud = new JPanel();
		
		JScrollPane scrollPane = new JScrollPane(this.tableau);
		
		//Ajout des composants
		panelCentre2.add( this.btnAjouter);
		panelCentre2.add( this.btnSupprimer);
		
		panelCentre.add(panelCentre2, BorderLayout.SOUTH );
		panelCentre.add(scrollPane, BorderLayout.CENTER);
		
		panelSud.add( this.btnEnregistrer );
		panelSud.add( this.btnAnnuler );
		
		this.add( new JLabel( "Liste des intervenants" ), BorderLayout.NORTH );
		this.add( panelCentre, BorderLayout.CENTER );
		this.add( panelSud, BorderLayout.SOUTH );
		
		//met les actionListener
		this.btnAjouter.addActionListener(this);
		this.btnSupprimer.addActionListener(this);
		this.btnEnregistrer.addActionListener(this);
		this.btnAnnuler.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e)
	{
		if( e.getSource() == this.btnAjouter )
		{
			this.tableau.ajouterLigne();
			this.repaint();
		}
		
		if( e.getSource() == this.btnSupprimer )
		{
			this.tableau.supprimerLigne();
			this.repaint();
		}
		
		if( e.getSource() == this.btnEnregistrer )
		{
			
		}
		
		if( e.getSource() == this.btnAnnuler )
		{
			
		}
	}
}
