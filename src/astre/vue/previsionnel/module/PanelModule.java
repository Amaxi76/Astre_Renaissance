package astre.vue.previsionnel.module;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import astre.Controleur;

public class PanelModule  extends JPanel implements ActionListener
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	private Controleur ctrl;

	private JTextField tfType;
	private JTextField tfSemestre;
	private JTextField tfCode;
	private JTextField tfLibLong;
	private JTextField tfLibCourt;

	private JButton btnEnregistrer;
	private JButton btnAnnuler;

	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/
	
	public PanelModule ( Controleur ctrl )
	{
		this.ctrl = ctrl;
		/* ------------------------- */
		/* Création des composants   */
		/* ------------------------- */

		this.tfType	    = new JTextField ("", 10);
		this.tfSemestre	= new JTextField ("", 2);
		this.tfCode	    = new JTextField ("", 5);
		this.tfLibLong	= new JTextField ("", 20);
		this.tfLibCourt	= new JTextField ("", 10);

		this.add ( new JLabel ( "Type : " ) );
		this.add ( this.tfType     );

		this.add ( new JLabel ( "Semestre : " ) );
		this.add ( this.tfSemestre );

		this.add ( new JLabel ( "Code : "  ) );
		this.add ( this.tfCode     );

		this.add ( new JLabel ( "Libellé long : " ) );
		this.add ( this.tfLibLong  );

		this.add ( new JLabel ( "Libellé court : " ) );
		this.add ( this.tfLibCourt );

		this.btnEnregistrer = new JButton ( "Enregistrer" );
		this.btnAnnuler     = new JButton ( "Annuler"     );

		this.add ( this.btnEnregistrer );
		this.add ( this.btnAnnuler     );

		/* ------------------------- */
		/* Activation des composants */
		/* ------------------------- */

		this.tfType    .setEnabled ( false  );
		this.tfType    .setOpaque  ( false );

		this.tfSemestre.setEnabled ( false  );
		this.tfSemestre.setOpaque  ( false );

		 this.tfCode.addKeyListener(new KeyListener()
		 {
            @Override
            public void keyTyped(KeyEvent e) { validateTextField(); }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
		 });

		this.btnEnregistrer.addActionListener(this);
		this.btnAnnuler    .addActionListener(this);
	}

	private void validateTextField()
	{
        String code = this.tfCode.getText();

        if (code.startsWith("R"))
		{
            this.tfType.setText("Ressource");
        }

        if (code.startsWith("S"))
		{
            this.tfType.setText("SAE");
        }

        int valSemestre = (code.length() > 1) ? Character.getNumericValue(code.charAt(1)) : -1;

        if (valSemestre >= 1 && valSemestre <= 6)
            this.tfSemestre.setText("S" + valSemestre);
    }

	/* ActionListener */
	public void actionPerformed ( ActionEvent e )
	{
		/*if (e.getSource() == this.tfCode)
		{
			String code = this.tfCode.getText();
	
			if (code.startsWith("R"))
			{
				this.tfType.setText("Ressource");
			}

			if (code.startsWith("S"))
			{
				this.tfType.setText("SAE");
			}

			int valSemestre = Character.getNumericValue(code.charAt(1));

			if (valSemestre >= 1 && valSemestre <= 6)
			{
				this.tfSemestre.setText("S" + valSemestre);
			}
		}*/

		if ( e.getSource ( ) == this.btnEnregistrer )
		{
			System.out.println("Enregistrer");
		}
	}
}
