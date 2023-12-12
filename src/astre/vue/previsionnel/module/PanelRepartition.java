package astre.vue.previsionnel.module;

import java.awt.*;

import javax.swing.JPanel;

import astre.Controleur;

public class PanelRepartition extends JPanel
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	private Controleur ctrl;

	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/
	
	public PanelRepartition ( Controleur ctrl )
	{
		this.ctrl = ctrl;
		/* ------------------------- */
		/* Création des composants   */
		/* ------------------------- */

		this.setLayout ( new GridLayout( 1, 11 ) );
	}
}