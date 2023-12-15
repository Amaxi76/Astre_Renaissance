package astre.vue.intervenants;

/** Page de gestion des intervenants
  * @author : Matéo Sa
  * @version : 1.0 - 11/12/2023
  * @date : 06/12/2023
  */

import astre.Controleur;
import astre.vue.outils.MenuBarAstre;
import javax.swing.*;

public class FrameIntervenants extends JFrame
{
	private PanelIntervenants panel;
	private Controleur        ctrl;
	
	public FrameIntervenants ( Controleur ctrl )
	{
		this.ctrl  = ctrl;
		this.panel = new PanelIntervenants ( this.ctrl );
		
		this.setTitle              ( "Intervenants"  );
		this.setSize               ( 1000,500 );
		this.setLocationRelativeTo ( null                );
		
		this.setJMenuBar ( new MenuBarAstre ( this.ctrl, this ) );
		
		this.add ( this.panel );

		this.setVisible ( true );
	}
}
