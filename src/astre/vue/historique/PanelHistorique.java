package astre.vue.historique;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import astre.Controleur;
import astre.modele.BD;

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

		JScrollPane bar = new JScrollPane(txtHistorique);

		this.add(bar);
		//this.add ( this.txtHistorique );

		ArrayList<String> lst = BD.getInstance().getHistorique();

		for(String s : lst)
		{
			
			this.txtHistorique.append(s + "\n");
		}

	}
}
