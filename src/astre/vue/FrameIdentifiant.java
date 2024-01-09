package astre.vue;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
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
		this.setTitle( "Identifiant" );
		this.setSize(250,250);

		this.setLayout(new GridLayout(3,2,10,10));

		this.txtId = new JTextField();
		this.txtMdp = new JPasswordField();
		this.btnVal = new JButton("Valider");

		this.add(new JLabel("Identifiant :"));
		this.add(this.txtId);
		this.add(new JLabel("Mdp :"));
		this.add(this.txtMdp);
		this.add(this.btnVal);

		this.btnVal.addActionListener(this);

		this.setVisible(true);
	}

	@Override
	public void actionPerformed ( ActionEvent e )
	{
		if( e.getSource() == this.btnVal )
		{
			try
			{
				PrintWriter pw = new PrintWriter( new FileOutputStream("./data/identifiant/identifiant.txt") );

				pw.println ( this.txtId.getText() );
				char[] a = this.txtMdp.getPassword();
				String s = new String(a);
				pw.println ( a );

				pw.close();
			}
			catch ( Exception ex) { ex.printStackTrace(); }

			new Controleur();
			this.dispose();
		}
	}

}
