package astre.vue.previsionnel.module;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import astre.Controleur;
import astre.modele.BD;
import astre.modele.elements.Horaire;
import astre.modele.elements.Intervient;
import astre.modele.elements.ModuleIUT;
import astre.vue.outils.Tableau;

/** Classe PanelAffectation 
  * @author : Clémentin Ly
  * @version : 2.0 - 14/12/2023
  * @date : 13/12/2023
  */

public class PanelAffectation extends JPanel implements ActionListener
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	private Controleur ctrl;

	private Tableau tableau;
	private JScrollPane scrollPane;

	private JButton btnAjouter;
	private JButton btnSupprimer;

	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/

	public PanelAffectation( Controleur ctrl )
	{
		this.ctrl = ctrl;

		this.setLayout ( new BorderLayout() );

		/* ------------------------- */
		/* Création des composants   */
		/* ------------------------- */

		String[] noms = {"Intervenant", "type", "nb sem", "nb Gp|nb H", "tot eqtd", "commentaire" };

		this.tableau = new Tableau ( noms, this.ctrl.getTableauIntervient(), 0);
		this.tableau.ajusterTailleColonnes( );

		this.scrollPane = new JScrollPane ( this.tableau );


		JPanel panelSud = new JPanel( );

		this.btnAjouter   = new JButton ( "Ajouter"   );
		this.btnSupprimer = new JButton ( "Supprimer" );

		this.add ( this.scrollPane,   BorderLayout.NORTH );

		panelSud.add ( this.btnAjouter   );
		panelSud.add ( this.btnSupprimer );

		this.add ( panelSud, BorderLayout.SOUTH);

		/* ------------------------- */
		/* Activation des composants */
		/* ------------------------- */

		this.btnAjouter  .addActionListener ( this );
		this.btnSupprimer.addActionListener ( this );
	}

	/* ActionListener */
	public void actionPerformed ( ActionEvent e )
	{
		if ( e.getSource() == this.btnAjouter )
		{
			this.tableau.ajouterLigne();
			this.repaint();
		}
		
		if ( e.getSource() == this.btnSupprimer )
		{
			this.tableau.supprimerLigne();
			this.repaint();
		}
	}

	public void setDonnee ( ModuleIUT module )
	{
		//TODO verif si ce truc marche
		this.tableau.modifDonnees(this.ctrl.getTableauContrat());
	}	
}