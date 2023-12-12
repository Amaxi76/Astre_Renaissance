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

	private JLabel     lblType;
	private JLabel     lblSemestre;
	private JTextField txtCode;
	private JTextField txtLibLong;
	private JTextField txtLibCourt;

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

		this.lblType	 = new JLabel ();
		this.lblSemestre = new JLabel ();
		this.txtCode	 = new JTextField ("", 5);
		this.txtLibLong	 = new JTextField ("", 20);
		this.txtLibCourt = new JTextField ("", 10);

		this.add ( new JLabel ( "Type : " ) );
		this.add ( this.lblType     );

		this.add ( new JLabel ( "Semestre : " ) );
		this.add ( this.lblSemestre );

		this.add ( new JLabel ( "Code : "  ) );
		this.add ( this.txtCode     );

		this.add ( new JLabel ( "Libellé long : " ) );
		this.add ( this.txtLibLong  );

		this.add ( new JLabel ( "Libellé court : " ) );
		this.add ( this.txtLibCourt );

		this.btnEnregistrer = new JButton ( "Enregistrer" );
		this.btnAnnuler     = new JButton ( "Annuler"     );

		this.add ( this.btnEnregistrer );
		this.add ( this.btnAnnuler     );

		/* ------------------------- */
		/* Activation des composants */
		/* ------------------------- */

		this.lblType    .setBackground ( Color.LIGHT_GRAY );
		this.lblType    .setPreferredSize ( new Dimension ( 100, 15) );
		this.lblType    .setOpaque ( true );

		this.lblSemestre.setBackground ( Color.LIGHT_GRAY );
		this.lblSemestre.setPreferredSize ( new Dimension ( 50, 15 ) );
		this.lblSemestre.setOpaque ( true );

		this.txtCode.addKeyListener(new KeyListener()
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
		String code = this.txtCode.getText();

		if (code.startsWith("R"))
		{
			this.lblType.setText("Ressource");
		}

		if (code.startsWith("S"))
		{
			this.lblType.setText("SAE");
		}

		int valSemestre = (code.length() > 1) ? Character.getNumericValue(code.charAt(1)) : -1;

		if (valSemestre >= 1 && valSemestre <= 6)
			this.lblSemestre.setText("S" + valSemestre);
	}

	/* ActionListener */
	public void actionPerformed ( ActionEvent e )
	{
		if ( e.getSource ( ) == this.btnEnregistrer )
		{
			System.out.println("Enregistrer");
		}
	}
}
