package astre.vue.outils;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

//TODO: Passer en paramètre du constructeur le regex
//TODO: passer ce filtre en sous-classe d'un JTextFieldPersonnalisé
/** Filtre pour JTextField
  * @author : Maxime Lemoine
  * @version : 1.0 - 14/12/2023
  * @date : 14/12/2023
  */
public class FiltreTextFieldEntier extends DocumentFilter
{

	@Override
	public void insertString ( FilterBypass fb, int offset, String string, AttributeSet attr ) throws BadLocationException
	{
		// Permet uniquement les chiffres
		if ( string.matches ( "\\d+" ) )
		{
			super.insertString ( fb, offset, string, attr );
		}
	}

	@Override
	public void replace ( FilterBypass fb, int offset, int length, String text, AttributeSet attrs ) throws BadLocationException
	{
		// Permet uniquement les chiffres
		if ( text.matches ( "\\d+" ) )
		{
			super.replace ( fb, offset, length, text, attrs );
		}
	}

	public static void appliquer ( JTextField txt )
	{
		( ( AbstractDocument ) txt.getDocument ( ) ).setDocumentFilter ( new FiltreTextFieldEntier ( ) );
	}
}
