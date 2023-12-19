package astre.vue.historique;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import astre.Controleur;

public class PanelHistorique extends JPanel
{
	private JTextArea txtHistorique;
	private Controleur ctrl;

	public PanelHistorique ( Controleur ctrl )
	{
		this.ctrl = ctrl;

		this.txtHistorique = new JTextArea ( 25, 50 );
		this.txtHistorique.setEditable ( false );

		this.txtHistorique.append("Hello world !");

		this.add ( this.txtHistorique );

	}
}
