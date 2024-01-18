package astre.vue.rendus;

import java.awt.Font;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import java.util.List;

/** 
 * Classe permettant de modifier le fonctionnement d'une cellule lorsqu'on l'édite.
 * Ici, on utilise un JComboBox pour afficher les valeurs possibles.
 * @author Maxime Lemoine
 * @version : 1.0 - 18/01/2024
 * @date : 17/01/2024
 * 
 */
public class EditionComboBoxCelluleTableau extends DefaultCellEditor
{
	public EditionComboBoxCelluleTableau ( List<String> valeurs )
	{
		// Appel du constructeur de la classe parente pour gérer correctement les combobox
		super( new JComboBox<> ( ) );

		valeurs.sort( null );

		JComboBox<String> cb = ( JComboBox<String> ) this.getComponent ( );
		for ( String s : valeurs )
			cb.addItem ( s );

		// Réduire la taille du texte car sinon le texte est coupé
		Font font = cb.getFont().deriveFont(Font.PLAIN, 10);
		cb.setFont(font);
	}
}

