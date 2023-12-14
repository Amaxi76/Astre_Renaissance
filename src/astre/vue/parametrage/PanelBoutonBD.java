package astre.vue.parametrage;

/** Classe FramePrevisionnel
  * @author : Clémentin Ly, Maxime Lemoine
  * @version : 1.0 - 11/12/2023
  * @date : 06/12/2023
  */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import astre.vue.outils.ConstantesVue;
import astre.vue.previsionnel.module.*;
import astre.Controleur;

public class PanelBoutonBD extends JPanel implements ActionListener
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	private Controleur ctrl;

	private JButton btnEnregistrer;
	private JButton btnAnnuler;


	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/
	
	public PanelBoutonBD ( Controleur ctrl )
	{
		this.ctrl        = ctrl;
		this.setBorder ( new EmptyBorder ( ConstantesVue.MARGE_EXTERIEURE_COMPOSANT, 0, 0, 0 ) );
		
		/* ------------------------- */
		/* Création des composants   */
		/* ------------------------- */

		this.setLayout ( new GridLayout ( 1, 5, 5, 0 ) );

		this.btnEnregistrer = new JButton ( "Enregistrer" );
		this.btnAnnuler     = new JButton ( "Annuler    " );

		/* ----------------------------- */
		/* Positionnement des composants */
		/* -----------------------    -- */

		JPanel pnlBoutton = new JPanel ( new GridLayout ( 1, 2 ) );
		pnlBoutton.add ( this.btnEnregistrer );
		pnlBoutton.add ( this.btnAnnuler     );
		
		this.centrerTexte ( this.btnEnregistrer );
		this.centrerTexte ( this.btnAnnuler     );
		
		this.add ( this.btnEnregistrer );
		this.add ( this.btnAnnuler     );

		/* ------------------------- */
		/* Activation des composants */
		/* ------------------------- */

		this.btnEnregistrer.addActionListener ( this );
	}

	/* ActionListener */
	public void actionPerformed ( ActionEvent e )
	{
		// if ( e.getSource ( ) == this.btnCreer )
		// {
		// 	if ( this.pnl instanceof PanelTypeHeure )
		// 		( ( PanelTypeHeure ) this.pnl ).getTab ( ).ajouterLigne ( );
		// 	if ( this.pnl instanceof PanelContrat )
		// 		( ( PanelContrat   ) this.pnl ).getTab ( ).ajouterLigne ( );
		// }

		// if ( e.getSource ( ) == this.btnSupprimer )
		// {
		// 	this.frameModule = new FrameModule ( this.ctrl );
		// }
	}

	private void centrerTexte ( JButton btn )
	{
		btn.setHorizontalAlignment ( JButton.CENTER );
		btn.setVerticalAlignment   ( JButton.CENTER );
	}
}
