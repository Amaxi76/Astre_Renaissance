package astre.vue.intervenants;

/** Page de gestion des intervenants
  * @author : Matéo Sa
  * @version : 1.0 - 11/12/2023
  * @date : 06/12/2023
  */

import astre.Controleur;
import javax.swing.*;
import java.awt.event.*;

public class FrameIntervenants extends JFrame
{
	private PanelIntervenants panel;
	private Controleur        ctrl;
	
	public FrameIntervenants ( Controleur ctrl )
	{
		this.ctrl = ctrl;
		this.panel = new PanelIntervenants();
		
		this.setTitle ( "Intervenants" );
		this.setSize ( 1000,500 );
		
		this.add ( this.panel );
		
		this.setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE ) ;
		this.setVisible ( true );
		
		this.requestFocus ( );
	}
	
	public void keyPressed​ ( KeyEvent e )
	{
		System.out.println ( "test" );
	}
	
	public void keyReleased​ ( KeyEvent e )
	{
		
	}
	public void keyTyped ( KeyEvent e )
	{
		
	}
}
