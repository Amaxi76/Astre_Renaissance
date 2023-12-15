package astre.vue.parametrage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import astre.Controleur;
import astre.vue.outils.ConstantesVue;
import astre.vue.outils.Tableau;

import astre.modele.elements.Heure;

/** Classe PanelParametrage
  * @author : Maximilien LESTERLIN
  * @version : 1.0 - 13/12/23
  * @date : 13/12/2023
  */

public class PanelTypeHeure extends JPanel implements ActionListener
{
	private Controleur ctrl;
	private Tableau    tabHeure;
	
	private JButton btnEnregistrer;
	private JButton btnAnnuler;

	public PanelTypeHeure ( Controleur ctrl )
	{
		this.ctrl = ctrl;
		this.setLayout ( new GridLayout ( 1 ,1 ) );

		/* ------------------------- */
		/* Création des composants   */
		/* ------------------------- */

		
		JPanel      pnlContenu   = new JPanel ( new BorderLayout ( ) );
		JPanel      pnlBouttonBD = new JPanel ( new FlowLayout  ( FlowLayout.RIGHT ) );
		JPanel      pnlBoutton   = new JPanel ( new GridLayout ( 1, 2 ) );

		
		this.tabHeure     = Tableau.initialiserTableau ( enTete, tabObjects, true, 1, this.ctrl.getTableauHeure ( ) );

		this.btnEnregistrer = new JButton ( "Enregistrer" );
		this.btnAnnuler     = new JButton ( "Annuler"     );

		pnlContenu.setBorder ( ConstantesVue.MARGE_INTERIEURE_FENETRE );

		this.tabHeure.setEditable ( true  );
		this.tabHeure.setShowGrid ( false );
		this.tabHeure.setIntercellSpacing ( new Dimension ( 0, 0 ) );

		// Ajout du titre et rend la liste défilable 
		JScrollPane spTab        = new JScrollPane ( this.tabHeure );
		spTab.setBorder                  ( new TitledBorder ( "Liste des type d'heure" ) );
		spTab.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );

		/* ----------------------------- */
		/* Positionnement des composants */
		/* -----------------------    -- */

		this.centrerTexte ( this.btnEnregistrer );
		this.centrerTexte ( this.btnAnnuler     );

		pnlBouttonBD.setBorder ( new EmptyBorder ( ConstantesVue.MARGE_EXTERIEURE_COMPOSANT, 0, 0, 0 ) );

		pnlBouttonBD.add ( this.btnEnregistrer );
		pnlBouttonBD.add ( this.btnAnnuler     );

		pnlBoutton.add ( new PanelBouton ( this.ctrl, this ) );
		pnlBoutton.add ( pnlBouttonBD                        );

		pnlContenu.add ( spTab     , BorderLayout.CENTER );
		pnlContenu.add ( pnlBoutton, BorderLayout.SOUTH  );

		this.add ( pnlContenu );

		/* ------------------------- */
		/* Activation des composants */
		/* ------------------------- */

		this.btnEnregistrer.addActionListener ( this );
		this.btnAnnuler    .addActionListener ( this );
	}

	private void centrerTexte ( JButton btn )
	{
		btn.setHorizontalAlignment ( JButton.CENTER );
		btn.setVerticalAlignment   ( JButton.CENTER );
	}

	public void actionPerformed ( ActionEvent e )
	{
		if ( e.getSource ( ) == this.btnEnregistrer )
			this.ctrl.majTableauBD ( this.tabHeure.getDonnees ( ), Heure.class );
	}

	public Tableau getTab ( ) { return this.tabHeure; }

	
}