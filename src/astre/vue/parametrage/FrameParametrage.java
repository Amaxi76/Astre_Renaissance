package astre.vue.parametrage;

/** Page de gestion des intervenants
  * @author : Maximilien LESTERLIN
  * @version : 1.0 - 11/12/2023
  * @date : 06/12/2023
  */

import astre.Controleur;
import javax.swing.*;

public class FrameParametrage extends JFrame
{
	private PanelParametrage panel;
	private Controleur       ctrl;
	
	public FrameParametrage ( Controleur ctrl )
	{
		this.ctrl  = ctrl;
		this.panel = new PanelParametrage ( this.ctrl );
		
		this.setTitle              ( "Paramètrage" );
		this.setSize               ( 1000,500      );
		this.setLocationRelativeTo ( null          );
		
		//this.add ( this.panel );

		this.setVisible   ( true );
		this.requestFocus (      );
	}
	
}