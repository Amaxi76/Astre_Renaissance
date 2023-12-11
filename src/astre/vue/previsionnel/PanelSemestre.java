package astre.vue.previsionnel;

import astre.Controleur;
import javax.swing.*;
import java.awt.*;


/** Classe PanelEnsSemestre
  * @author : Maximeuuu
  * @version : 1.0 le 11/12/23
  * @date : 06/12/2023
  */

public class PanelSemestre extends JPanel
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

		//INSTRUCTIONS

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
		this.pnlOptionSemestre.add ( this.txtNbGrTD );
		this.pnlOptionSemestre.add ( new JLabel ( "nb gr TP"    ) );
		this.pnlOptionSemestre.add ( this.txtNbGrTP );
		this.pnlOptionSemestre.add ( new JLabel ( "nb Etd"      ) );
		this.pnlOptionSemestre.add ( this.nbEtud );
		this.pnlOptionSemestre.add ( new JLabel ( "nb semaines" ) );
		this.pnlOptionSemestre.add ( this.nbSemaine );

		
		this.add ( this.pnlOptionSemestre );
		this.add ( new JLabel ( "Page du semestre : " + numSemestre ) );


	}
}
