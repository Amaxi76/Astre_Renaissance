package astre.vue.previsionnel;

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
import astre.vue.outils.Tableau;
import astre.vue.previsionnel.module.*;
import astre.Controleur;
import astre.modele.elements.ModuleIUT;
import astre.modele.outils.Utilitaire;

//TODO: c'est une dinguerie de pas nommer ses variables en lowerCamelCase
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
	private JButton btncreerPPP;
	private JButton btnModifier;
	private JButton btnSupprimer;

	private static final String REQUETE = "v_Module";

	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/
	
	public PanelBouton ( Controleur ctrl, FramePrevisionnel frame )
	{
		this.ctrl              = ctrl;
		this.framePrevisionnel = frame;
		this.frameModule       = null;
		this.setBorder( new EmptyBorder ( ConstantesVue.MARGE_EXTERIEURE_COMPOSANT, 0, 0, 0 ) );
		
		/* ------------------------- */
		/* Création des composants   */
		/* ------------------------- */

		this.setLayout ( new GridLayout ( 1, 5, 5, 0 ) );

		this.btncreerRessource = new JButton ( "<html>créer<br>Ressource</html>"   );
		this.btncreerSAE       = new JButton ( "<html>créer<br>SAÉ</html>"         );
		this.btncreerStage     = new JButton ( "<html>créer<br>Stage/Suivi</html>" );
		this.btncreerPPP       = new JButton ( "<html>créer<br>PPP</html>"         );
		this.btnModifier       = new JButton ( "modifier"                          );
		this.btnSupprimer      = new JButton ( "supprimer"                         );

		/* ----------------------------- */
		/* Positionnement des composants */
		/* -----------------------    -- */
		
		this.centrerTexte ( this.btncreerRessource );
		this.centrerTexte ( this.btncreerSAE       );
		this.centrerTexte ( this.btncreerStage     );
		this.centrerTexte ( this.btncreerPPP       );
		this.centrerTexte ( this.btnModifier       );
		this.centrerTexte ( this.btnSupprimer      );
		
		this.add ( this.btncreerRessource );
		this.add ( this.btncreerSAE       );
		this.add ( this.btncreerStage     );
		this.add ( this.btncreerPPP       );
		this.add ( this.btnModifier       );
		this.add ( this.btnSupprimer      );

		/* ------------------------- */
		/* Activation des composants */
		/* ------------------------- */

		this.btncreerRessource.addActionListener ( this );
		this.btncreerSAE      .addActionListener ( this );
		this.btncreerStage    .addActionListener ( this );
		this.btncreerPPP      .addActionListener ( this );
		this.btnModifier      .addActionListener ( this );
		this.btnSupprimer     .addActionListener ( this );

	}

	/* ActionListener */
	public void actionPerformed ( ActionEvent e )
	{
		//Gestion des cas si création
		String typeModule = null;

		if ( e.getSource ( ) == this.btncreerRessource ) { typeModule = "Ressource"; }
		if ( e.getSource ( ) == this.btncreerSAE       ) { typeModule = "SAE";       }
		if ( e.getSource ( ) == this.btncreerStage     ) { typeModule = "Stage";     }
		if ( e.getSource ( ) == this.btncreerPPP       ) { typeModule = "PPP";       }

		if ( typeModule != null )
		{
			this.frameModule = new FrameModule ( this.ctrl, framePrevisionnel, typeModule, framePrevisionnel.getPanelEnsSemestre ( ).getSelectedIndex ( ), 'A' );
			this.frameModule.setVisible ( true );
		}

		// Gestion du cas si modification
		if ( e.getSource ( ) == this.btnModifier )
		{
			if ( ! this.framePrevisionnel.getModuleSelection ( ).equals ( "pas de selection" ) )
			{
				ModuleIUT module = this.ctrl.getModule ( this.framePrevisionnel.getModuleSelection ( ) );
				this.frameModule = new FrameModule ( this.ctrl, framePrevisionnel, module.getTypeModule ( ), framePrevisionnel.getPanelEnsSemestre ( ).getSelectedIndex ( ), 'M' );
				this.frameModule.setValeursPanel ( module.getCode ( ) );
				this.frameModule.setVisible      ( true               );
			}
		}

		// Gestion du cas si suppression
		if ( e.getSource ( ) == this.btnSupprimer )
		{
			if ( !this.framePrevisionnel.getModuleSelection ( ).equals ( "pas de selection" ) )
			{
				int idSemestre = this.ctrl.getModule ( this.framePrevisionnel.getModuleSelection ( ) ).getSemestre ( ).getIdSemestre ( );


				Tableau ensSemestre = this.framePrevisionnel.getTableauSemetre ( idSemestre );
				ensSemestre.supprimerLigne ( );

				this.ctrl.majTableauBD ( preparerTableau ( ensSemestre.getDonnees ( ) ), ModuleIUT.class );

				ensSemestre.modifDonnees ( this.ctrl.getTableauParticulier ( REQUETE + " WHERE id_semestre = " + idSemestre ) );

				ensSemestre.ajusterTailleColonnes ( );
				this.repaint ( );
			}
		}
	}

	/**
	 * Raccourcir le tableau de l'affichage pour mettre à jour la base de données
	 */
	private Object[][] preparerTableau ( Object[][] tab  )
	{
		// Enlever les colonnes en trop
		Object[][] tab2 = Utilitaire.formater ( tab, 7 );
		
		// Remplacer les noms de contrat par les objets contrat
		int COLONNE_SEMESTRE = 1;
		
		for ( int lig = 0; lig < tab.length; lig++ )
		{
			tab2[lig][COLONNE_SEMESTRE] = this.ctrl.getSemestre ( ( int ) ( tab2[lig][COLONNE_SEMESTRE] ) );
		}
		
		// Replacer les objets dans le bon ordre pour le constructeur
		for ( int lig = 0; lig < tab2.length; lig++ )
		{
			String codeTmp = "" + tab2[lig][2];

			tab2[lig][2] = this.ctrl.getModule(codeTmp).getTypeModule ( );
			tab2[lig][3] = codeTmp;
			tab2[lig][4] = this.ctrl.getModule(codeTmp).getLibLong    ( );
			tab2[lig][5] = this.ctrl.getModule(codeTmp).getLibCourt   ( );
			tab2[lig][6] = this.ctrl.getModule(codeTmp).estValide     ( );
	
		}
		
		return tab2;
	}

	private void centrerTexte ( JButton btn )
	{
		btn.setHorizontalAlignment ( JButton.CENTER );
		btn.setVerticalAlignment   ( JButton.CENTER );
	}
}
