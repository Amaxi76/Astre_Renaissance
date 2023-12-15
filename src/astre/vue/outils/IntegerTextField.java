package astre.vue.outils;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class IntegerTextField extends JTextField {
    public IntegerTextField() {
        // Utilisation d'un DocumentFilter pour limiter la saisie à des entiers
        ((AbstractDocument) getDocument()).setDocumentFilter(new IntegerDocumentFilter());
    }

    private static class IntegerDocumentFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            // Permet uniquement les chiffres
            if (string.matches("\\d+")) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            // Permet uniquement les chiffres
            if (text.matches("\\d+")) {
                super.replace(fb, offset, length, text, attrs);
            }
        }
    }

    /*public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("IntegerTextField Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            IntegerTextField integerTextField = new IntegerTextField();
            frame.add(integerTextField);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }*/
}

