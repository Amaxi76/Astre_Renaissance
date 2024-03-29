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
import astre.modele.elements.Heure;
import astre.vue.outils.ConstantesVue;
import astre.vue.outils.Tableau;

/** Classe PanelParametrage
  * @author : Maximilien LESTERLIN
  * @version : 1.0 - 13/12/23
  * @date : 13/12/2023
  */

public class PanelParametrage extends JPanel implements ActionListener
{
	private Controleur ctrl;
	private Tableau    tab;
	private Class<?>   classe;
	
	private JButton btnEnregistrer;
	private JButton btnAnnuler;

	public PanelParametrage ( Controleur ctrl, String[] enTete, Object[] tabObjects , Object[][] tabDonnee, boolean[] estModifiable, String nomTab, Class<?> classe )
	{
		this.ctrl   = ctrl;
		this.classe = classe;
		this.setLayout ( new GridLayout ( 1 ,1 ) );

		/* ------------------------- */
		/*  Création des composants  */
		/* ------------------------- */

		JPanel pnlContenu  = new JPanel ( new BorderLayout (                  ) );
		JPanel pnlBoutonBD = new JPanel ( new FlowLayout   ( FlowLayout.RIGHT ) );
		JPanel pnlBouton   = new JPanel ( new GridLayout   ( 1, 2             ) );
		
		this.tab = Tableau.initialiserTableau ( enTete, tabObjects, estModifiable, 2, tabDonnee );

		this.btnEnregistrer = new JButton ( "Enregistrer" );
		this.btnAnnuler     = new JButton ( "Annuler"     );

		pnlContenu.setBorder ( ConstantesVue.MARGE_INTERIEURE_FENETRE );

		this.tab.setShowGrid ( false );
		this.tab.setIntercellSpacing ( new Dimension ( 0, 0 ) );

		// Ajout du titre et rend la liste défilable 
		JScrollPane spTab = new JScrollPane ( this.tab );
		spTab.setBorder                  ( new TitledBorder ( nomTab ) );
		spTab.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );

		/* ----------------------------- */
		/* Positionnement des composants */
		/* ----------------------------- */

		this.centrerTexte ( this.btnEnregistrer );
		this.centrerTexte ( this.btnAnnuler     );

		pnlBoutonBD.setBorder ( new EmptyBorder ( ConstantesVue.MARGE_EXTERIEURE_COMPOSANT, 0, 0, 0 ) );

		pnlBoutonBD.add ( this.btnEnregistrer );
		pnlBoutonBD.add ( this.btnAnnuler     );

		if ( classe != Heure.class )
			pnlBouton.add ( new PanelBouton ( this.ctrl, this ) );
		
		pnlBouton.add ( pnlBoutonBD                        );

		pnlContenu.add ( spTab    , BorderLayout.CENTER );
		pnlContenu.add ( pnlBouton, BorderLayout.SOUTH  );

		this.add ( pnlContenu );

		/* ------------------------- */
		/* Activation des composants */
		/* ------------------------- */

		this.btnEnregistrer.addActionListener ( this );
		this.btnAnnuler    .addActionListener ( this );
	}

	//TODO: déplacer cette méthode dans une classe utilitaiare Swing
	private void centrerTexte ( JButton btn )
	{
		btn.setHorizontalAlignment ( JButton.CENTER );
		btn.setVerticalAlignment   ( JButton.CENTER );
	}

	public void actionPerformed ( ActionEvent e )
	{
		if ( e.getSource ( ) == this.btnEnregistrer )
		{
			this.ctrl.majTableauBD ( this.tab.getDonnees ( ), this.classe );
			this.tab.setDonnees  ( this.ctrl.getTableau ( this.classe ) );
		}

		if ( e.getSource ( ) == this.btnAnnuler )
		{
			this.tab.setDonnees ( this.ctrl.getTableau ( this.classe ) );
		}
	}

	public Tableau getTab ( ) { return this.tab; }
}
