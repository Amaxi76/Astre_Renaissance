package astre.vue.parametrage;

import java.awt.FlowLayout;

/** Classe PanelBouton
  * @author : Maximilien LESTERLIN
  * @version : 1.0 - 11/12/2023
  * @date : 06/12/2023
  */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import astre.vue.outils.ConstantesVue;
import astre.Controleur;

public class PanelBouton extends JPanel implements ActionListener
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	private Controleur ctrl;
	
	private JPanel pnl;

	private JButton btnCreer;
	private JButton btnSupprimer;

	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/
	
	public PanelBouton ( Controleur ctrl, JPanel pnl )
	{
		this.ctrl        = ctrl;
		this.pnl         = pnl;
		this.setBorder ( new EmptyBorder ( ConstantesVue.MARGE_EXTERIEURE_COMPOSANT, 0, 0, 0 ) );
		this.setLayout ( new FlowLayout  ( FlowLayout.LEFT                                   ) );
		
		/* ------------------------- */
		/* Création des composants   */
		/* ------------------------- */
		
		this.btnCreer     = new JButton ( "créer" );
		this.btnSupprimer = new JButton ( "supprimer"          );
		
		/* ----------------------------- */
		/* Positionnement des composants */
		/* -----------------------    -- */
		
		this.centrerTexte ( this.btnCreer     );
		this.centrerTexte ( this.btnSupprimer );
		
		this.add ( this.btnCreer     );
		this.add ( this.btnSupprimer );

		/* ------------------------- */
		/* Activation des composants */
		/* ------------------------- */

		this.btnCreer     .addActionListener ( this );
		this.btnSupprimer .addActionListener ( this );
	}

	/* ActionListener */
	public void actionPerformed ( ActionEvent e )
	{
		if ( e.getSource ( ) == this.btnCreer )
		{
			if ( this.pnl instanceof PanelTypeHeure )
				( ( PanelTypeHeure ) this.pnl ).getTab ( ).ajouterLigne ( );
			if ( this.pnl instanceof PanelContrat )
				( ( PanelContrat   ) this.pnl ).getTab ( ).ajouterLigne ( );
		}

		if ( e.getSource ( ) == this.btnSupprimer )
		{
			if ( this.pnl instanceof PanelTypeHeure )
				( ( PanelTypeHeure ) this.pnl ).getTab ( ).supprimerLigne ( );
			if ( this.pnl instanceof PanelContrat )
				( ( PanelContrat   ) this.pnl ).getTab ( ).supprimerLigne ( );
		}

	}

	private void centrerTexte ( JButton btn )
	{
		btn.setHorizontalAlignment ( JButton.CENTER );
		btn.setVerticalAlignment   ( JButton.CENTER );
	}
}
