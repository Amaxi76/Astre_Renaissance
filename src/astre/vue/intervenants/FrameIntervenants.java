package astre.vue.intervenants;

import javax.swing.*;
import java.awt.event.*;

/**
 * Page de gestion des intervenants.
 * @author Matéo
 */
public class FrameIntervenants extends JFrame implements KeyListener
{
	private PanelIntervenants panel;
	
	public FrameIntervenants ( )
	{
		this.panel = new PanelIntervenants();
		
		this.setTitle ( "Intervenants" );
		this.setSize ( 1000,500 );
		
		this.add(this.panel );
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		this.requestFocus();
	}
	
	public static void main(String[] a)
	{
		new FrameIntervenants();
	}
	
	public void keyPressed​(KeyEvent e)
	{
		System.out.println("test");
	}
	
	public void keyReleased​(KeyEvent e)
	{
		
	}
	public void keyTyped(KeyEvent e)
	{
		
	}
}
