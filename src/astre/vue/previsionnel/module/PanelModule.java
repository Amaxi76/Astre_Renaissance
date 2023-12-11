package astre.vue.previsionnel.module;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import astre.Controleur;

public class PanelModule  extends JPanel implements ActionListener
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	private Controleur ctrl;

	private JButton btnEnregistrer;
	private JButton btnAnnuler;

	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/
	
	public PanelModule ( Controleur ctrl )
	{
		this.ctrl = ctrl;
		/* ------------------------- */
		/* Création des composants   */
		/* ------------------------- */

		this.btnEnregistrer = new JButton ( "Enregistrer" );
		this.btnAnnuler     = new JButton ( "Annuler"     );

		this.add ( this.btnEnregistrer );
		this.add ( this.btnAnnuler     );

		/* ------------------------- */
		/* Activation des composants */
		/* ------------------------- */

		this.btnEnregistrer.addActionListener(this);
		this.btnAnnuler    .addActionListener(this);
	}

	/* ActionListener */
	public void actionPerformed ( ActionEvent e )
	{
		if ( e.getSource ( ) == this.btnEnregistrer )
		{
			System.out.println("Enregistrer");
		}
	}
}
