package astre.vue.previsionnel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import astre.Controleur;

public class FramePrevisionnel extends JFrame implements ActionListener
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	/** Un Controleur pour pouvoir accéder au controleur
	 * 
	 */
	private Controleur  ctrl;

	private JButton btncreerRessource;
	private JButton btncreerSAE;
	private JButton btncreerStage;
	private JButton btnModifier;
	private JButton btnSupprimer;

	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/

	/** Constructuer de FramePrevisionnel qui crée un panelGraphe et panelAction
	 * @param ctrl le controleur
	 * 
	 */
	public FramePrevisionnel ( Controleur ctrl )
	{
		this.ctrl = ctrl;

		this.setSize     ( 1000, 700 );
		this.setTitle    ( "Prévisionnel"   );
	}
}
