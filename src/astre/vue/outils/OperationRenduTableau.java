package astre.vue.outils;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

//TODO: Revoir tout le code pour le formatage
//TODO: mettre un style grisé pour une ligne sur deux
public class OperationRenduTableau extends DefaultTableCellRenderer
{
	private static final long serialVersionUID = 1L;
		
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		// Appeler la méthode de la classe parent pour obtenir le rendu par défaut
		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		// Assurez-vous que la valeur est un caractère
		//if (value instanceof Character) {
			char operation = (char) table.getModel().getValueAt(row, 0);

			// Définir la couleur en fonction de la valeur de l'opération
			switch (operation) {
				case 'M':
					c.setBackground(Color.YELLOW);
					c.setForeground(Color.BLACK);
					break;
				case 'S':
					setBackground(Color.RED);
					setForeground(Color.WHITE);
					break;
				case 'A':
					setBackground(Color.GREEN);
					setForeground(Color.BLACK);
					break;
				default:
					// Par défaut, utilisez les couleurs par défaut de la table
					setBackground(table.getBackground());
					setForeground(table.getForeground());
					break;
			}
	   //}

		return this;
	}
}
