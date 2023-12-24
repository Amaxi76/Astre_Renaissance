import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JTextField;

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

	/**
	 * Methode permettant de créer les TextField avec des paramètres par défaut
	 */
	public static JTextField creerTextFieldEntier ( boolean editable )
	{
		JTextField txtTmp = new JTextField ( );
		txtTmp.setPreferredSize ( new Dimension ( 55, 20 ) ); //de base : (40,15)

		if ( editable )
		{
			//TODO: à rajouter quand la classe sera dans le projet et le package
			//FiltreTextFieldEntier.appliquer ( txtTmp );
		}
		else
		{
			txtTmp.setEditable   ( false            );
			txtTmp.setBackground ( Color.LIGHT_GRAY );
			//txtTmp.setForeground ( Color.BLACK      );
		}
		
		return txtTmp;
	}
}
