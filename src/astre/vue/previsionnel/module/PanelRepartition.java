package astre.vue.previsionnel.module;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import astre.Controleur;

public class PanelRepartition extends JPanel
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	private Controleur ctrl;

	private JTextField txtNbSemCM;
	private JTextField txtNbHCM;
	private JTextField txtNbSemTD;
	private JTextField txtNbHTD;
	private JTextField txtNbSemTP;
	private JTextField txtNbHTP;

	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/
	
	public PanelRepartition ( Controleur ctrl )
	{
		this.ctrl = ctrl;
		/* ------------------------- */
		/* Création des composants   */
		/* ------------------------- */

		this.setLayout ( new GridLayout( 2, 11 ) );

		this.txtNbSemCM = new JTextField ("", 2);
		this.txtNbHCM   = new JTextField ("", 2);
		this.txtNbSemTD = new JTextField ("", 2);
		this.txtNbHTD   = new JTextField ("", 2);
		this.txtNbSemTP = new JTextField ("", 2);
		this.txtNbHTP   = new JTextField ("", 2);

		this.add ( new JLabel ( "nb Sem"   ) );
		this.add ( new JLabel ( "nb h/sem" ) );
		this.add ( new JLabel ( "nb Sem"   ) );
		this.add ( new JLabel ( "nb h/sem" ) );
		this.add ( new JLabel ( "nb Sem"   ) );
		this.add ( new JLabel ( "nb h/sem" ) );

		this.add ( this.txtNbSemCM );
		this.add ( this.txtNbHCM   );
		this.add ( this.txtNbSemTD );
		this.add ( this.txtNbHTD   );
		this.add ( this.txtNbSemTP );
		this.add ( this.txtNbHTP   );

		/* ------------------------- */
		/* Activation des composants */
		/* ------------------------- */

		this.txtNbSemCM.addKeyListener ( new KeyListener()
		{
			public void keyTyped    ( KeyEvent e ) {}
			public void keyPressed  ( KeyEvent e ) {}
			public void keyReleased ( KeyEvent e ) {}
		});

		this.txtNbHCM.addKeyListener ( new KeyListener()
		{
			public void keyTyped    ( KeyEvent e ) {}
			public void keyPressed  ( KeyEvent e ) {}
			public void keyReleased ( KeyEvent e ) {}
		});

		this.txtNbSemTD.addKeyListener ( new KeyListener()
		{
			public void keyTyped    ( KeyEvent e ) {}
			public void keyPressed  ( KeyEvent e ) {}
			public void keyReleased ( KeyEvent e ) {}
		});

		this.txtNbHTD.addKeyListener ( new KeyListener()
		{
			public void keyTyped    ( KeyEvent e ) {}
			public void keyPressed  ( KeyEvent e ) {}
			public void keyReleased ( KeyEvent e ) {}
		});

		this.txtNbSemTP.addKeyListener ( new KeyListener()
		{
			public void keyTyped    ( KeyEvent e ) {}
			public void keyPressed  ( KeyEvent e ) {}
			public void keyReleased ( KeyEvent e ) {}
		});

		this.txtNbHTP.addKeyListener ( new KeyListener()
		{
			public void keyTyped    ( KeyEvent e ) {}
			public void keyPressed  ( KeyEvent e ) {}
			public void keyReleased ( KeyEvent e ) {}
		});
		
	}
}