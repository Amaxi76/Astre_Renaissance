package astre.vue.outils;

import java.awt.Color;
import java.awt.Dimension;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import javax.swing.JTextField;

/** Classe utilitaire sur les saisies
 * @author : Maxime Lemoine
 * @version : 1.0 - 27/12/2023
 * @date : 26/12/2023
 */
public abstract class Saisie
{
	private Saisie ( ) { }

	public static String formaterDouble ( double num )
	{
		DecimalFormatSymbols decimalSymbols = DecimalFormatSymbols.getInstance();
		decimalSymbols.setDecimalSeparator('.');
		return new DecimalFormat("0.00", decimalSymbols).format(num);
	}

	/**
	 * Methode permettant de créer les TextField en précisant si c'est éditable
	 */
	public static JTextField creerTextFieldEntier ( boolean editable )
	{
		JTextField txtTmp = new JTextField ( );
		txtTmp.setPreferredSize ( new Dimension ( 55, 20 ) );

		if ( editable )
		{
			FiltreTextFieldEntier.appliquer ( txtTmp );
			txtTmp.setText ( "0" );
		}
		else
		{
			JTextFieldToLabel ( txtTmp );
		}
		
		return txtTmp;
	}

	public static void JTextFieldToLabel ( JTextField txt )
	{
		txt.setEditable   ( false            );
		txt.setBackground ( new Color ( 225,225,225 ) );
		txt.setForeground ( Color.BLACK      );
		txt.setPreferredSize ( new Dimension ( 55, 20 ) );
	}

}
