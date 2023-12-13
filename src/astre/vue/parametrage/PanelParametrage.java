package astre.vue.parametrage;

import javax.swing.JPanel;

import astre.Controleur;

/** Classe PanelParametrage
  * @author : Maximilien LESTERLIN
  * @version : 1.0 - 13/12/23
  * @date : 13/12/2023
  */

public class PanelParametrage extends JPanel
{
	private PanelParametrage panel;
	private Controleur       ctrl;
	
	public PanelParametrage ( Controleur ctrl )
	{
		this.ctrl = ctrl;
	}
}
