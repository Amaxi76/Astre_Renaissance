package astre.vue.historique;

import java.util.ArrayList;

import javax.swing.JPanel;
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

		//création du textArea
		this.txtHistorique = new JTextArea ( 30, 80 );
		this.txtHistorique.setEditable ( false );

		//création de la scrollbar
		JScrollPane bar = new JScrollPane(txtHistorique);
		this.add(bar);

		//récupération de la table historique
		ArrayList<String> lst = this.ctrl.getHistorique();

		//Ajout du texte dans la zone dédiée
		for(String s : lst)
		{
			this.txtHistorique.append(s + "\n");
		}

	}
}
