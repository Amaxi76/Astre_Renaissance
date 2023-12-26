import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class FrameModule extends JFrame implements KeyListener
{
	private AbstractPanelRepartition pnlRepartition;

	public FrameModule ( boolean avecGroupe )
	{
		if ( avecGroupe )
			this.pnlRepartition = new PanelRepartitionAvecGroupes_v4 ( this );
		else
			this.pnlRepartition = new PanelRepartitionSansGroupes_v4 ( this );

		this.add ( this.pnlRepartition ); // afficher en version "typeRessource"

		String[] entetes = new String[]{ "CM", "TP", "TD", "Tut" };
		for ( String typeModule : entetes )
		{
			this.pnlRepartition.ajouterTypeHeure ( typeModule );
		}
		//this.pnlRepartition.supprimerTypeHeure ( "Tut" );

		this.setVisible ( true );
		this.pack ( );
		this.setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );
	}

	public static void main ( String[] args )
	{
		new FrameModule ( true );
	}

	public static String formaterDouble ( double num )
	{
		DecimalFormatSymbols decimalSymbols = DecimalFormatSymbols.getInstance();
		decimalSymbols.setDecimalSeparator('.');
		return new DecimalFormat("0.00", decimalSymbols).format(num);
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
			txtTmp.setBackground ( new Color ( 225,225,225 ) );
			//txtTmp.setForeground ( Color.BLACK      );
		}
		
		return txtTmp;
	}

	@Override public void keyTyped   ( KeyEvent e ) {}
	@Override public void keyPressed ( KeyEvent e ) {}

	@Override
	public void keyReleased(KeyEvent e)
	{
		System.out.println ( "maj" );
		this.pnlRepartition.majIHM ( );
	}
}
