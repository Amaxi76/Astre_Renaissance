package astre.vue.previsionnel;

import java.awt.event.*;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.FlowLayout;

import astre.Controleur;


/** Classe PanelEnsSemestre
  * @author : Maximeuuu et Amaxi76
  * @version : 1.0 le 11/12/23
  * @date : 06/12/2023
  */

public class PanelSemestre extends JPanel implements ActionListener
{
	private Controleur ctrl;
	private int        numSemestre;
	private JPanel     pnlOptionSemestre;

	private JTextField txtNbGrTD;
	private JTextField txtNbGrTP;
	private JTextField nbEtud;
	private JTextField nbSemaine;
	
	public PanelSemestre ( int numSemestre, Controleur ctrl )
	{
		this.ctrl = ctrl;
		this.numSemestre = numSemestre;

		this.pnlOptionSemestre = new JPanel ( new FlowLayout ( ) );

		this.txtNbGrTD = new JTextField ( );
		this.txtNbGrTP = new JTextField ( );
		this.nbEtud    = new JTextField ( );
		this.nbSemaine = new JTextField ( );

		this.txtNbGrTD.setColumns ( 2 );
		this.txtNbGrTP.setColumns ( 2 );
		this.nbEtud   .setColumns ( 2 );
		this.nbSemaine.setColumns ( 2 );

		this.pnlOptionSemestre.add ( new JLabel ( "nb gr TD"    ) );
		this.pnlOptionSemestre.add ( this.txtNbGrTD               );
		this.pnlOptionSemestre.add ( new JLabel ( "nb gr TP"    ) );
		this.pnlOptionSemestre.add ( this.txtNbGrTP               );
		this.pnlOptionSemestre.add ( new JLabel ( "nb Etd"      ) );
		this.pnlOptionSemestre.add ( this.nbEtud                  );
		this.pnlOptionSemestre.add ( new JLabel ( "nb semaines" ) );
		this.pnlOptionSemestre.add ( this.nbSemaine               );

		this.add ( this.pnlOptionSemestre );

		this.txtNbGrTD.addActionListener ( this );
		this.txtNbGrTP.addActionListener ( this );
		this.nbEtud   .addActionListener ( this );
		this.nbSemaine.addActionListener ( this );
	}

	public void actionPerformed ( ActionEvent e )
	{

	}
}
