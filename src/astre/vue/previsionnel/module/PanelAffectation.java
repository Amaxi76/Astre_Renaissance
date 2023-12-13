package astre.vue.previsionnel.module;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import astre.Controleur;
import astre.vue.outils.TableauIntervenant;

public class PanelAffectation extends JPanel implements ActionListener
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	private Controleur ctrl;

	private TableauIntervenant tableau;

	private JButton btnAjouter;
	private JButton btnSupprimer;

	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/

	public PanelAffectation( Controleur ctrl )
	{
		this.ctrl = ctrl;

		/* ------------------------- */
		/* Création des composants   */
		/* ------------------------- */

		String[] noms = {"Intervenant", "type", "nb sem", "nb Gp|nb H", "tot eqtd", "commentaire" };

		this.tableau = new TableauIntervenant ( noms, this.ctrl.getTableauIntervient(), true );
		this.tableau.ajusterTailleColonnes( );

		this.btnAjouter   = new JButton ( "Ajouter"   );
		this.btnSupprimer = new JButton ( "Supprimer" );

		this.add ( this.btnAjouter   );
		this.add ( this.btnSupprimer );

		/* ------------------------- */
		/* Activation des composants */
		/* ------------------------- */

		this.btnAjouter  .addActionListener ( this );
		this.btnSupprimer.addActionListener ( this );
	}

	/* ActionListener */
	public void actionPerformed ( ActionEvent e )
	{
		if ( e.getSource ( ) == this.btnAjouter )
		{
			System.out.println ( "Ajouter" );
		}

		if ( e.getSource ( ) == this.btnSupprimer )
		{
			System.out.println ( "Supprimer" );
		}
	}
}