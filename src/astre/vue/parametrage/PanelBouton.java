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

public class PanelBouton extends JPanel implements ActionListener
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	private Controleur ctrl;
	
	private FrameModule       frameModule;
	private PanelEnsParametre pnl;

	private JButton btnCreer;
	private JButton btnModifier;
	private JButton btnSupprimer;


	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/
	
	public PanelBouton ( Controleur ctrl, PanelEnsParametre pnl )
	{
		this.ctrl        = ctrl;
		this.pnl         = pnl;
		this.frameModule = null;
		this.setBorder ( new EmptyBorder ( ConstantesVue.MARGE_EXTERIEURE_COMPOSANT, 0, 0, 0 ) );
		
		/* ------------------------- */
		/* Création des composants   */
		/* ------------------------- */

		this.setLayout ( new GridLayout ( 1, 5, 5, 0 ) );

		this.btnCreer     = new JButton ( "<html>créer</html>" );
		this.btnModifier  = new JButton ( "modifier"           );
		this.btnSupprimer = new JButton ( "supprimer"          );

		/* ----------------------------- */
		/* Positionnement des composants */
		/* -----------------------    -- */
		
		this.centrerTexte ( this.btnCreer     );
		this.centrerTexte ( this.btnModifier  );
		this.centrerTexte ( this.btnSupprimer );
		
		this.add ( this.btnCreer     );
		this.add ( this.btnModifier  );
		this.add ( this.btnSupprimer );

		/* ------------------------- */
		/* Activation des composants */
		/* ------------------------- */

		this.btnCreer     .addActionListener ( this );
		this.btnModifier  .addActionListener ( this );
		this.btnSupprimer .addActionListener ( this );
	}

	/* ActionListener */
	public void actionPerformed ( ActionEvent e )
	{
		if ( e.getSource ( ) == this.btnCreer )
		{
			if ( this.pnl.getSelectedIndex ( ) == 0 )
				System.out.println( " oui ");
			else
				System.out.println ( "non" );
		}

		if ( e.getSource ( ) == this.btnModifier )
		{
			this.frameModule = new FrameModule ( this.ctrl );
		}

		if ( e.getSource ( ) == this.btnSupprimer )
		{
			this.frameModule = new FrameModule ( this.ctrl );
		}
	}

	private void centrerTexte ( JButton btn )
	{
		btn.setHorizontalAlignment ( JButton.CENTER );
		btn.setVerticalAlignment   ( JButton.CENTER );
	}
}
