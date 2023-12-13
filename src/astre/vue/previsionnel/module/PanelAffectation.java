package astre.vue.previsionnel.module;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

import astre.Controleur;
import astre.vue.outils.Tableau;

public class PanelAffectation extends JPanel implements ActionListener
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	private Controleur ctrl;

	private TableauIntervient tableau;
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

		this.tableau = new TableauIntervient ( noms, this.ctrl.getTableauIntervient());
		this.tableau.ajusterTailleColonnes( );

		this.scrollPane   = new JScrollPane ( this.tableau );


		JPanel panelSud     = new JPanel( );

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
}