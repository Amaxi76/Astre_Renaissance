package astre.vue.historique;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import astre.Controleur;

/** Classe PanelHistorique
  * @author : Matéo Sa
  * @version : 1.0 - 18/12/23
  * @date : 18/12/2023
  */

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
