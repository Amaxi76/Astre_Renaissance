package astre.vue.previsionnel.module;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.*;

public class PanelTest extends JPanel
{
	public PanelTest ( )
	{
		this.setLayout ( new GridBagLayout ( ) );
		GridBagConstraints gbc = new GridBagConstraints ( );
		gbc.gridx = gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.LINE_START;
		
		this.add ( new JLabel("test"), gbc);
	}
}