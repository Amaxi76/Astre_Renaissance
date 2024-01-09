package astre.vue;

/** Classe Controleur
  * @author : Mateo Sa et Clémentin Ly
  * @version : 2.2 - 09/01/2024
  * @date : 09/01/2024
  */

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import javax.swing.*;

import astre.Controleur;

public class FrameIdentifiant extends JFrame implements ActionListener
{
	private JTextField     txtId;
	private JPasswordField txtMdp;

	private JButton        btnVal;

	public FrameIdentifiant ( )
	{
		this.setTitle( "Identifiant"  );
		this.setSize (500,250  );
		this.setLocationRelativeTo ( null );

		this.setLayout ( new GridBagLayout() );
		GridBagConstraints gbc = new GridBagConstraints( );
		gbc.insets = new Insets ( 5, 5, 5, 5 );

		this.txtId  = new JTextField     ( 20 );
		this.txtMdp = new JPasswordField ( 20 );
		this.btnVal = new JButton ( "Valider" );

		gbc.gridy = 0;
		gbc.gridx = 0;
		this.add ( new JLabel ( "Identifiant :" ), gbc );

		gbc.gridx = 1;
		this.add ( this.txtId, gbc );

		gbc.gridy = 1;
		gbc.gridx = 0;
		this.add ( new JLabel ( "Mot de Passe :" ), gbc );

		gbc.gridx = 1;
		this.add ( this.txtMdp, gbc );


		gbc.gridy = 2;
		gbc.gridx = 0;
		this.add ( this.btnVal, gbc );

		this.btnVal.addActionListener ( this );
		this.setVisible ( true );
	}

	@Override
	public void actionPerformed ( ActionEvent e )
	{
		if ( e.getSource ( ) == this.btnVal )
		{
			try
			{
				PrintWriter pw = new PrintWriter ( new FileOutputStream ( "./data/identifiant/identifiant.txt" ) );

				pw.println ( this.txtId.getText ( ) );
				char[] a = this.txtMdp.getPassword ( );
				new String ( a );
				pw.println ( a );

				pw.close();
			}
			catch ( Exception ex) { ex.printStackTrace(); }

			new Controleur();
			this.dispose();
		}
	}

}
