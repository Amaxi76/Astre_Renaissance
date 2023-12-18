package astre.vue.historique;

/** Page de gestion des intervenants
  * @author : Matéo Sa
  * @version : 1.0 - 11/12/2023
  * @date : 06/12/2023
  */

import astre.Controleur;
import astre.vue.outils.MenuBarAstre;
import javax.swing.*;

public class FrameHistorique extends JFrame
{
	private PanelHistorique panel;
	private Controleur        ctrl;
	
	public FrameHistorique ( Controleur ctrl )
	{
		this.ctrl  = ctrl;
		this.panel = new PanelHistorique ( this.ctrl );
		
		this.setTitle              ( "Historique"  );
		this.setSize               ( 1000,500 );
		this.setLocationRelativeTo ( null                );
		
		this.setJMenuBar ( new MenuBarAstre ( this.ctrl, this ) );
		
		this.add ( this.panel );

		this.setVisible ( true );
	}
}
