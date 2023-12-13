package astre.vue.parametrage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;

import astre.Controleur;
import astre.vue.outils.MenuBarAstre;
import astre.vue.outils.ConstantesVue;

/** Page de gestion des intervenants
  * @author : Maximilien LESTERLIN
  * @version : 1.0 - 11/12/2023
  * @date : 06/12/2023
  */

public class FrameParametrage extends JFrame
{
	private PanelEnsParametre pnlEnsParametre;
	private Controleur        ctrl;
	
	public FrameParametrage ( Controleur ctrl )
	{
		this.ctrl  = ctrl;
		
		this.setTitle              ( "Paramètrage" );
		this.setSize               ( 800, 700      );
		this.setLocationRelativeTo ( null          );
		
		this.setJMenuBar ( new MenuBarAstre ( this.ctrl, this ) );
		
		JPanel panelBordure = new JPanel ( new BorderLayout ( ) );
		panelBordure.setBorder ( ConstantesVue.MARGE_INTERIEURE_FENETRE );

		this.pnlEnsParametre = new PanelEnsParametre ( this.ctrl );

		panelBordure.add ( this.pnlEnsParametre , BorderLayout.CENTER );
		panelBordure.add ( new PanelBouton       ( this.ctrl, this.pnlEnsParametre ), BorderLayout.SOUTH );

		this.add ( panelBordure );

		this.setVisible   ( true );
		this.requestFocus (      );
	}
	
}
