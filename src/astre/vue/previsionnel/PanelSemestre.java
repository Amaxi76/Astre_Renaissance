package astre.vue.previsionnel;

import astre.Controleur;
import javax.swing.*;
import java.awt.*;


/** Classe PanelEnsSemestre
  * @author : Maximeuuu
  * @version : 1.0 le 6/12/23
  */
public class PanelSemestre extends JPanel
{
	private Controleur ctrl;
	private int numSemestre;
	
	public PanelSemestre ( int numSemestre, Controleur ctrl )
	{
		this.ctrl = ctrl;
		
		this.add ( new JLabel ( "Page du semestre : " + numSemestre ) );
	}
}
