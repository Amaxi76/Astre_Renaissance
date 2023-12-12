package astre.vue.previsionnel.module;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import astre.Controleur;

public class PanelModuleLabel  extends JPanel
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

	private JLabel lblNbEtd;
	private JLabel lblNbGpTD;
	private JLabel lblNbGpTP;


	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/
	
	public PanelModuleLabel ( Controleur ctrl )
	{
		this.ctrl = ctrl;
		/* ------------------------- */
		/* Création des composants   */
		/* ------------------------- */

		this.setLayout ( new GridLayout( 2, 5 ) );

		this.lblType	 = new JLabel ( );
		this.lblSemestre = new JLabel ( );
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


		this.lblNbEtd  = new JLabel( );
		this.lblNbGpTD = new JLabel( );
		this.lblNbGpTP = new JLabel( );

		this.add ( new JLabel ( "nb Etd : " ) );
		this.add ( this.lblNbEtd  );

		this.add ( new JLabel ( "nb gp TD : " ) );
		this.add ( this.lblNbGpTD );

		this.add ( new JLabel ( "nb gp TP : " ) );
		this.add ( this.lblNbGpTP );

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
			public void keyTyped(KeyEvent e) { validationTextField(); }

			@Override
			public void keyPressed(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {}
		} );


		this.lblNbEtd .setBackground ( Color.LIGHT_GRAY );
		this.lblNbEtd .setPreferredSize ( new Dimension ( 25, 15) );
		this.lblNbEtd .setOpaque ( true );

		this.lblNbGpTD.setBackground ( Color.LIGHT_GRAY );
		this.lblNbGpTD.setPreferredSize ( new Dimension ( 25, 15) );
		this.lblNbGpTD.setOpaque ( true );

		this.lblNbGpTP.setBackground ( Color.LIGHT_GRAY );
		this.lblNbGpTP.setPreferredSize ( new Dimension ( 25, 15) );
		this.lblNbGpTP.setOpaque ( true );
	}

	private void validationTextField()
	{
		String code = this.txtCode.getText();

		if (code.startsWith("R"))
		{
			this.lblType.setText("Ressource");
		}

		else if (code.startsWith("S"))
		{
			this.lblType.setText("SAE");
		}

		else if (code.contains("ST"))
		{
			if (!code.startsWith("ST"))
			{
				this.lblType.setText("Stage");
			}
		}

		int valSemestre = (code.length() > 1) ? Character.getNumericValue(code.charAt(1)) : -1;

		if (valSemestre >= 1 && valSemestre <= 6)
			this.lblSemestre.setText("S" + valSemestre);
	}
}
