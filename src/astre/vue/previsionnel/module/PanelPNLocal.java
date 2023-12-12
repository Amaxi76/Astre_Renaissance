package astre.vue.previsionnel.module;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import astre.Controleur;

public class PanelPNLocal extends JPanel
{
	private Controleur ctrl;

	private JTextField txtCM;
	private JTextField txtTD;
	private JTextField txtTP;
	private JLabel lblSomme;

	private JLabel totalCM;
	private JLabel totalTD;
	private JLabel totalTP;
	private JLabel totalSomme;

	public PanelPNLocal(Controleur ctrl)
	{
		this.ctrl = ctrl;

		this.setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);

		// Labels
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add(new JLabel("CM"), gbc);

		gbc.gridx = 1;
		this.add(new JLabel("TD"), gbc);

		gbc.gridx = 2;
		this.add(new JLabel("TP"), gbc);

		gbc.gridx = 3;
		this.add(new JLabel("Σ"), gbc);

		// TextFields
		gbc.gridy = 1;
		gbc.gridx = 0;
		this.txtCM = new JTextField("", 2);
		this.add(this.txtCM, gbc);

		gbc.gridx = 1;
		this.txtTD = new JTextField("", 2);
		this.add(this.txtTD, gbc);

		gbc.gridx = 2;
		this.txtTP = new JTextField("", 2);
		this.add(this.txtTP, gbc);

		gbc.gridx = 3;
		this.lblSomme = new JLabel();
		this.add(this.lblSomme, gbc);

		// Total Labels
		gbc.gridy = 2;
		gbc.gridx = 0;
		this.totalCM = new JLabel();
		this.add(this.totalCM, gbc);

		gbc.gridx = 1;
		this.totalTD = new JLabel();
		this.add(this.totalTD, gbc);

		gbc.gridx = 2;
		this.totalTP = new JLabel();
		this.add(this.totalTP, gbc);

		gbc.gridx = 3;
		this.totalSomme = new JLabel();
		this.add(this.totalSomme, gbc);

		// Ajout du KeyListener pour chaque champ de texte
		this.txtCM.addKeyListener(new CustomKeyListener());
		this.txtTD.addKeyListener(new CustomKeyListener());
		this.txtTP.addKeyListener(new CustomKeyListener());

		// Initialisation des couleurs et préférences
		this.lblSomme.setBackground(Color.LIGHT_GRAY);
		this.lblSomme.setPreferredSize(new Dimension(25, 15));
		this.lblSomme.setOpaque(true);

		this.totalCM.setBackground(Color.LIGHT_GRAY);
		this.totalCM.setPreferredSize(new Dimension(25, 15));
		this.totalCM.setOpaque(true);

		this.totalTD.setBackground(Color.LIGHT_GRAY);
		this.totalTD.setPreferredSize(new Dimension(25, 15));
		this.totalTD.setOpaque(true);

		this.totalTP.setBackground(Color.LIGHT_GRAY);
		this.totalTP.setPreferredSize(new Dimension(25, 15));
		this.totalTP.setOpaque(true);

		this.totalSomme.setBackground(Color.LIGHT_GRAY);
		this.totalSomme.setPreferredSize(new Dimension(25, 15));
		this.totalSomme.setOpaque(true);
	}

	private class CustomKeyListener implements KeyListener {
		@Override
		public void keyTyped(KeyEvent e) {
			majSomme();
		}

		@Override
		public void keyPressed(KeyEvent e) {
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}
	}

	private void majSomme()
	{
		try
		{
			int CM = Integer.parseInt(txtCM.getText());
			int TD = Integer.parseInt(txtTD.getText());
			int TP = Integer.parseInt(txtTP.getText());

			int somme = CM + TD + TP;
			lblSomme.setText(String.valueOf(somme));
		}
		catch (NumberFormatException ex)
		{
			lblSomme.setText("Erreur");
		}
	}
}
