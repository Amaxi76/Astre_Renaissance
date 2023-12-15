package astre.vue.previsionnel;

/** Classe FramePrevisionnel
  * @author : Clémentin Ly
  * @version : 1.0 - 11/12/2023
  * @date : 06/12/2023
  */

import javax.swing.*;
import java.awt.BorderLayout;

import astre.Controleur;
import astre.vue.outils.MenuBarAstre;
import astre.vue.outils.ConstantesVue;

public class FramePrevisionnel extends JFrame
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	private static final int SEMESTRE_DEFAUT = 1;

	private Controleur ctrl;

	private PanelBouton pnlBouton;
	private PanelEnsSemestre pnlEnsSemestre;

	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/

	/** Constructeur de FramePrevisionnel
	 * @param ctrl le controleur
	 * 
	 */

	public FramePrevisionnel ( Controleur ctrl, int semestreDefaut )
	{
		this.ctrl = ctrl;

		this.setSize               ( 1000, 700      );
		this.setTitle              ( "Prévisionnel" );
		this.setLocationRelativeTo ( null           );

		this.setJMenuBar ( new MenuBarAstre ( this.ctrl, this ) );
			
		/* ------------------------- */
		/* Création des composants   */
		/* ------------------------- */

		this.pnlEnsSemestre = new PanelEnsSemestre  ( this.ctrl, semestreDefaut );
		this.pnlBouton      = new PanelBouton       ( this.ctrl, this           );

		JPanel panelBordure = new JPanel ( new BorderLayout ( ) );
		panelBordure.setBorder ( ConstantesVue.MARGE_INTERIEURE_FENETRE );

		panelBordure.add ( this.pnlEnsSemestre, BorderLayout.CENTER );
		panelBordure.add ( this.pnlBouton     , BorderLayout.SOUTH  );

		this.add ( panelBordure );

		this.setVisible ( true );
	}

	public FramePrevisionnel ( Controleur ctrl )
	{
		this ( ctrl, SEMESTRE_DEFAUT-1 );
	}

	public String getModuleSelection ( )
	{
		return this.pnlEnsSemestre.getModuleSelection ( );
	}
}
