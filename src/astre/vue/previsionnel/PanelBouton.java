package astre.vue.previsionnel;

/** Classe FramePrevisionnel
  * @author : Clémentin Ly, Maxime Lemoine
  * @version : 1.0 - 11/12/2023
  * @date : 06/12/2023
  */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import astre.vue.outils.ConstantesVue;
import astre.vue.outils.Tableau;
import astre.vue.previsionnel.module.*;
import astre.Controleur;
import astre.modele.elements.Intervenant;
import astre.modele.elements.ModuleIUT;

public class PanelBouton extends JPanel implements ActionListener
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	private Controleur ctrl;
	
	private FrameModule       frameModule;
	private FramePrevisionnel framePrevisionnel;

	private JButton btncreerRessource;
	private JButton btncreerSAE;
	private JButton btncreerStage;
	private JButton btnModifier;
	private JButton btnSupprimer;


	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/
	
	public PanelBouton ( Controleur ctrl, FramePrevisionnel frame )
	{
		this.ctrl = ctrl;
		this.framePrevisionnel = frame;
		this.frameModule = null;
		this.setBorder( new EmptyBorder ( ConstantesVue.MARGE_EXTERIEURE_COMPOSANT, 0, 0, 0 ) );
		
		/* ------------------------- */
		/* Création des composants   */
		/* ------------------------- */

		this.setLayout ( new GridLayout ( 1, 5, 5, 0 ) );

		this.btncreerRessource = new JButton ( "<html>créer<br>Ressource</html>"   );
		this.btncreerSAE       = new JButton ( "<html>créer<br>SAÉ</html>"         );
		this.btncreerStage     = new JButton ( "<html>créer<br>Stage/Suivi</html>" );
		this.btnModifier       = new JButton ( "modifier"                          );
		this.btnSupprimer      = new JButton ( "supprimer"                         );

		/* ----------------------------- */
		/* Positionnement des composants */
		/* -----------------------    -- */
		
		this.centrerTexte ( this.btncreerRessource );
		this.centrerTexte ( this.btncreerSAE       );
		this.centrerTexte ( this.btncreerStage     );
		this.centrerTexte ( this.btnModifier       );
		this.centrerTexte ( this.btnSupprimer      );
		
		this.add ( this.btncreerRessource );
		this.add ( this.btncreerSAE       );
		this.add ( this.btncreerStage     );
		this.add ( this.btnModifier       );
		this.add ( this.btnSupprimer      );

		/* ------------------------- */
		/* Activation des composants */
		/* ------------------------- */

		this.btncreerRessource.addActionListener ( this );
		this.btncreerSAE      .addActionListener ( this );
		this.btncreerStage    .addActionListener ( this );
		this.btnModifier      .addActionListener ( this );
		this.btnSupprimer     .addActionListener ( this );

	}

	/* ActionListener */
	public void actionPerformed ( ActionEvent e )
	{
		if ( e.getSource ( ) == this.btncreerRessource )
		{
			this.frameModule = new FrameModule ( this.ctrl );
		}

		if ( e.getSource ( ) == this.btncreerSAE )
		{
			this.frameModule = new FrameModule ( this.ctrl );
		}

		if ( e.getSource ( ) == this.btncreerStage )
		{
			this.frameModule = new FrameModule ( this.ctrl );
		}

		if ( e.getSource ( ) == this.btnModifier )
		{
			if( !this.framePrevisionnel.getModuleSelection( ).equals("pas de selection") )
			{
				this.frameModule = new FrameModule ( this.ctrl );
				this.frameModule.setModule( this.framePrevisionnel.getModuleSelection( ) );
			}
		}

		if ( e.getSource ( ) == this.btnSupprimer )
		{
			if( !this.framePrevisionnel.getModuleSelection( ).equals("pas de selection") )
			{
				Tableau ensSemestre = this.framePrevisionnel.getTableauSemetre ( );
				ensSemestre.supprimerLigne ( );
				this.ctrl.majTableauBD ( ensSemestre.getDonnees ( ), ModuleIUT.class );
				ensSemestre.ajusterTailleColonnes ( );
				this.repaint ( );
			}
		}
	}

	private void centrerTexte ( JButton btn )
	{
		btn.setHorizontalAlignment ( JButton.CENTER );
		btn.setVerticalAlignment   ( JButton.CENTER );
	}
}
