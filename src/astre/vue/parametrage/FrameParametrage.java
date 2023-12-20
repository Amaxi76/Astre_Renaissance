package astre.vue.parametrage;

import javax.swing.JPanel;
import java.awt.BorderLayout;

import astre.Controleur;
import astre.vue.outils.AFrame;
import astre.vue.outils.ConstantesVue;

/** Page de gestion des intervenants
  * @author : Maximilien LESTERLIN
  * @version : 1.0 - 11/12/2023
  * @date : 06/12/2023
  */

public class FrameParametrage extends AFrame
{
	private PanelEnsParametre pnlEnsParametre;
	
	public FrameParametrage ( Controleur ctrl )
	{
		super ( ctrl );
		
		this.setTitle              ( "Paramètrage" );
		this.setSize               ( 800, 700      );
		this.setLocationRelativeTo ( null          );

		JPanel panelBordure = new JPanel ( new BorderLayout ( ) );
		panelBordure.setBorder ( ConstantesVue.MARGE_INTERIEURE_FENETRE );

		this.pnlEnsParametre = new PanelEnsParametre ( this.ctrl );

		panelBordure.add ( this.pnlEnsParametre , BorderLayout.CENTER );

		this.add ( panelBordure                   , BorderLayout.CENTER );

		this.requestFocus (      );
		this.setVisible   ( true );
	}
}
