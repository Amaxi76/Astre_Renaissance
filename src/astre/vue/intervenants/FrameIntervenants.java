package astre.vue.intervenants;

/** Page de gestion des intervenants
  * @author : Matéo Sa
  * @version : 1.0 - 11/12/2023
  * @date : 06/12/2023
  */

import javax.swing.*;

public class FrameIntervenants extends JFrame
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
	}
	
	public static void main(String[] a)
	{
		new FrameIntervenants();
	}
}
