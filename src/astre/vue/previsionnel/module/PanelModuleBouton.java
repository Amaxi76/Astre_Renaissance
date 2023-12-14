package astre.vue.previsionnel.module;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;

import astre.Controleur;

public class PanelModuleBouton  extends JPanel implements ActionListener
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
	
	public PanelModuleBouton ( Controleur ctrl )
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

		this.btnEnregistrer.addActionListener ( this );
		this.btnAnnuler    .addActionListener ( this );
	}

	/* ActionListener */
	public void actionPerformed ( ActionEvent e )
	{
		if ( e.getSource ( ) == this.btnEnregistrer )
		{
			System.out.println ( "Enregistrer" );
		}

		if ( e.getSource ( ) == this.btnAnnuler )
		{
			( (JFrame)(this.getParent().getParent().getParent().getParent()) ).dispose(); //TODO: fonctionne mais peut être mettre qqch de plus propre via une valeur passée en paramètre de contructeur ?
			System.out.println ( "Annuler" );
		}
	}
}
