import javax.swing.JFrame;

public class FrameModule extends JFrame
{
	public FrameModule()
	{
		this.add ( new PanelRepartition() );
		this.setVisible(true);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args)
	{
		new FrameModule();
	}
}
