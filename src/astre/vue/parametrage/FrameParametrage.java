package astre.vue.parametrage;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;

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
	private static final String    TITRE     = "Paramètrage";
	private static final Dimension DIMENSION = new Dimension ( 800, 700 );

	private PanelEnsParametre pnlEnsParametre;
	
	public FrameParametrage ( Controleur ctrl )
	{
		super ( ctrl );

		this.setTitle              ( TITRE     );
		this.setSize               ( DIMENSION );
		this.setLocationRelativeTo ( null      );

		JPanel panelBordure = new JPanel ( new BorderLayout ( ) );
		panelBordure.setBorder ( ConstantesVue.MARGE_INTERIEURE_FENETRE );

		this.pnlEnsParametre = new PanelEnsParametre ( this.ctrl );

		panelBordure.add ( this.pnlEnsParametre , BorderLayout.CENTER );

		this.add ( panelBordure                   , BorderLayout.CENTER );

		this.requestFocus (      );
		this.setVisible   ( true );
	}
}
