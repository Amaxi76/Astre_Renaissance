import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class FiltreTextFieldEntier extends DocumentFilter {
    
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
    
    public static void appliquer ( JTextField txt )
    {  
           ((AbstractDocument) txt.getDocument()).setDocumentFilter(new FiltreTextFieldEntier());
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("IntegerTextField Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JTextField integerTextField = new JTextField();
            FiltreTextFieldEntier.appliquer ( integerTextField );
            frame.add(integerTextField);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}


