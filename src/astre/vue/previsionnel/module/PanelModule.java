package astre.vue.previsionnel.module;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import astre.Controleur;

public class PanelModule  extends JPanel implements ActionListener
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	private Controleur ctrl;

	private TextField tfType;
	private TextField tfSemestre;
	private TextField tfCode;
	private TextField tfLibLong;
	private TextField tfLibCourt;

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

		this.tfType	    = new TextField ("Ressource");
		this.tfSemestre	= new TextField ("S1");
		this.tfCode	    = new TextField ();
		this.tfLibLong	= new TextField ();
		this.tfLibCourt	= new TextField ();

		this.add ( new JLabel ( "Type : " ) );
		this.add ( this.tfType     );

		this.add ( new JLabel ( "Semestre : " ) );
		this.add ( this.tfSemestre );

		this.add ( new JLabel ( "Code : "  ) );
		this.add ( this.tfCode     );

		this.add ( new JLabel ( "Libellé long : " ) );
		this.add ( this.tfLibLong  );

		this.add ( new JLabel ( "Libellé court : " ) );
		this.add ( this.tfLibCourt );

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
