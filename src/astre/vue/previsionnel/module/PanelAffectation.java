package astre.vue.previsionnel.module;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import astre.Controleur;
import astre.modele.BD;
import astre.modele.elements.Heure;
import astre.modele.elements.Intervenant;
import astre.modele.elements.ModuleIUT;
import astre.vue.outils.Tableau;

/** Classe PanelAffectation
  * @author : Clémentin Ly
  * @version : 2.0 - 14/12/2023
  * @date : 13/12/2023
  */

//TODO: séparer la partie ihm et partie métier (ex: utilisation de DB au lieu d'un appel vers le controleur puis métier)
public class PanelAffectation extends JPanel implements ActionListener
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	private Controleur  ctrl;

	private Tableau     tableau;
	private JScrollPane scrollPane;

	private JButton     btnAjouter;
	private JButton     btnSupprimer;

	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/

	public PanelAffectation ( Controleur ctrl )
	{
		this.ctrl = ctrl;

		this.setLayout ( new BorderLayout ( ) );

		/* ------------------------- */
		/* Création des composants   */
		/* ------------------------- */

		String[] noms = { "action", "Intervenant", "type", "nb sem", "nb Gp|nb H", "tot eqtd", "commentaire" };
		Object[] typeDefaut = { 'A', "", "", 0, 0, 0, "..." };

		this.tableau = Tableau.initialiserTableau ( noms, typeDefaut, true, 1, null );
		if ( this.tableau == null ){ System.out.println( "tableau de panel affectation est null ");}

		//Ajout d'une JComboBox pour les intervenants au tableau
		JComboBox<String> cbEditInter = new JComboBox<> ( );
		for ( Intervenant i : this.ctrl.getTable ( Intervenant.class ) )
		{
			cbEditInter.addItem ( i.getNom ( ) );
		}
		this.tableau.getColumnModel ( ).getColumn ( 0 ).setCellEditor ( new DefaultCellEditor ( cbEditInter ) );

		//Ajout d'une JComboBox pour les intervenants au tableau
		JComboBox<String> cbEditHeure = new JComboBox<> ( );
		for ( Heure h : this.ctrl.getTable ( Heure.class ) )
		{
			cbEditHeure.addItem ( h.getNom ( ) );
		}
		this.tableau.getColumnModel ( ).getColumn ( 1 ).setCellEditor ( new DefaultCellEditor ( cbEditHeure ) );

		this.scrollPane = new JScrollPane ( this.tableau );

		JPanel panelSud = new JPanel ( );

		this.btnAjouter   = new JButton ( "Ajouter"   );
		this.btnSupprimer = new JButton ( "Supprimer" );

		this.add ( this.scrollPane,   BorderLayout.NORTH );

		panelSud.add ( this.btnAjouter   );
		panelSud.add ( this.btnSupprimer );

		this.add ( panelSud, BorderLayout.SOUTH );

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
			this.tableau.ajouterLigne ( );
			this.repaint ( );
		}

		if ( e.getSource ( ) == this.btnSupprimer )
		{
			this.tableau.supprimerLigne ( );
			this.repaint ( );
		}
	}

	public void setDonnee ( ModuleIUT module )
	{
		this.tableau.modifDonnees ( BD.getInstance ( ).getIntervientsTableau ( module.getCode ( ) ) );
	}
}
