import javax.swing.*;

/**
 * Page de gestion des intervenants.
 * @author Matéo
 */
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
