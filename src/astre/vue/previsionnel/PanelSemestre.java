package astre.vue.previsionnel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import astre.Controleur;
import astre.vue.outils.Tableau;


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
	
	private Tableau tableauEnsembleModule;
	
	public PanelSemestre ( int numSemestre, Controleur ctrl )
	{
		this.ctrl = ctrl;
		this.numSemestre = numSemestre;

		this.setLayout ( new BorderLayout ( ) );

		this.pnlOptionSemestre = new JPanel ( new FlowLayout ( ) );

		/* ----------------------------- */
		/*    Création des composants    */
		/* -----------------------    -- */

		this.txtNbGrTD = new JTextField ( );
		this.txtNbGrTP = new JTextField ( );
		this.nbEtud    = new JTextField ( );
		this.nbSemaine = new JTextField ( );

		this.txtNbGrTD.setText ( "" + this.ctrl.getSemestre ( this.numSemestre ).getNbGroupeTD ( ) );
		this.txtNbGrTP.setText ( "" + this.ctrl.getSemestre ( this.numSemestre ).getNbGroupeTP ( ) );
		this.nbEtud   .setText ( "" + this.ctrl.getSemestre ( this.numSemestre ).getNbEtudiant ( ) );
		this.nbSemaine.setText ( "" + this.ctrl.getSemestre ( this.numSemestre ).getNbSemaine  ( ) );
	
		this.txtNbGrTD.setColumns ( 2 );
		this.txtNbGrTP.setColumns ( 2 );
		this.nbEtud   .setColumns ( 2 );
		this.nbSemaine.setColumns ( 2 );


		this.tableauEnsembleModule = new Tableau ( this.ctrl.getTableauModule() );

		/* ----------------------------- */
		/* Positionnement des composants */
		/* -----------------------    -- */

		this.pnlOptionSemestre.add ( new JLabel ( "nb gr TD"    ) );
		this.pnlOptionSemestre.add ( this.txtNbGrTD );
		this.pnlOptionSemestre.add ( new JLabel ( "nb gr TP"    ) );
		this.pnlOptionSemestre.add ( this.txtNbGrTP               );
		this.pnlOptionSemestre.add ( new JLabel ( "nb Etd"      ) );
		this.pnlOptionSemestre.add ( this.nbEtud                  );
		this.pnlOptionSemestre.add ( new JLabel ( "nb semaines" ) );
		this.pnlOptionSemestre.add ( this.nbSemaine               );

		this.add ( this.pnlOptionSemestre     , BorderLayout.NORTH  );
		this.add ( this.tableauEnsembleModule , BorderLayout.CENTER );

		/* ----------------------------- */
		/*   Activation des composants   */
		/* -----------------------    -- */

		this.txtNbGrTD.addActionListener ( this );
		this.txtNbGrTP.addActionListener ( this );
		this.nbEtud   .addActionListener ( this );
		this.nbSemaine.addActionListener ( this );
	}

	public void actionPerformed ( ActionEvent e )
	{
	}
}
