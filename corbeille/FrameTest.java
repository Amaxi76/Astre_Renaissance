package astre.vue.previsionnel.module;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Panel;

import astre.Controleur;

public class FrameTest extends JFrame
{
	private Controleur ctrl;

	public FrameTest ( Controleur ctrl )
	{
		this.ctrl = ctrl;

		this.setSize               ( 1600, 1080              );
		this.setTitle              ( "Prévisionnel : Module" );
		this.setLocationRelativeTo ( null                    );

		/*JPanel panel = new JPanel ( new GridLayout ( ) );
		GridBagConstraints gbc = new GridBagConstraints ( );
		gbc.gridx = gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.LINE_START;

		panel.add ( new JLabel("test"), gbc);
		this.add ( panel );*/
		PanelTest panel = new PanelTest ( );
		panel.setLayout(new BorderLayout());
		panel.add ( new PanelModuleLabel(this.ctrl, "Ressource", 1 ), BorderLayout.LINE_START);
		//panel.setMaximumSize(null);
		//this.add ( panel, BorderLayout.LINE_START );

		//this.getContentPane().add(new PanelModuleLabel(this.ctrl, "Ressource", 1));
		this.add ( panel, BorderLayout.NORTH );

		this.setVisible ( true );
	}
}
