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
	 * Methode permettant de créer les TextField avec des paramètres par défaut
	 */
	public static JTextField creerTextFieldEntier ( boolean editable )
	{
		JTextField txtTmp = new JTextField ( );
		txtTmp.setPreferredSize ( new Dimension ( 55, 20 ) ); //de base : (40,15)

		if ( editable )
		{
			FiltreTextFieldEntier.appliquer ( txtTmp ); //TODO: vérifier que les "." du décimal peuvent être saisis
		}
		else
		{
			txtTmp.setEditable   ( false            );
			txtTmp.setBackground ( new Color ( 225,225,225 ) );
			//txtTmp.setForeground ( Color.BLACK      );
		}
		
		return txtTmp;
	}
}
