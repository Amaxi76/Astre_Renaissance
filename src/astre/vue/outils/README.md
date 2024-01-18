HOW TO USE Tableau.java

code :
```java
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PanelTableau extends JPanel implements ActionListener
{
	private Tableau    tab;
	
	private JButton btnEnregistrer;
	private JButton btnAnnuler;
	private JButton btnSupprimer;
	private JButton btnAjouter;

	public PanelTableau ( )
	{
		String[] enTete = { "modif", "Nom", "Prénom", "Age", "Ratio","CB", "list" };
		Object[] tabObjects = { 'D',"Nom", "Prénom", 0, new ValeurFractionnable(0.0), true, new ArrayList<>( Arrays.asList( new String[]{"aaa","bbb","ccc", "aaaa"} ) ) };
		boolean[] estModifiable = { false,true, true, true, true, true, true };
		Object[][] tabDonnee = { { 'D', "DUPONT", "Jean", 20, new ValeurFractionnable( "1/3" ), true, "" }, { 'D', "DURAND", "Paul", 30, new ValeurFractionnable( "1/3"), true, "" }, { 'D', "DUBOIS", "Jacques", 40, new ValeurFractionnable( "1/3"), true, "" } };

		this.setLayout ( new GridLayout ( 1 ,1 ) );

		/* ------------------------- */
		/*  Création des composants  */
		/* ------------------------- */

		JPanel pnlContenu   = new JPanel ( new BorderLayout (                  ) );
		JPanel pnlBouttonBD = new JPanel ( new FlowLayout   ( FlowLayout.RIGHT ) );
		JPanel pnlBoutton   = new JPanel ( new GridLayout   ( 1, 2             ) );
		
		this.tab = Tableau.initialiserTableau ( enTete, tabObjects, estModifiable, 1, tabDonnee );

		this.btnEnregistrer = new JButton ( "Enregistrer" );
		this.btnAnnuler     = new JButton ( "Annuler"     );
		this.btnSupprimer   = new JButton ( "Supprimer"   );
		this.btnAjouter     = new JButton ( "Ajouter"     );

		pnlContenu.setBorder ( new EmptyBorder ( 20,20,20,20 ) );

		// Ajout du titre et rend la liste défilable 
		JScrollPane spTab = new JScrollPane ( this.tab );
		spTab.setBorder                  ( new TitledBorder ( "Un tableau" ) );
		spTab.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );

		/* ----------------------------- */
		/* Positionnement des composants */
		/* ----------------------------- */

		this.centrerTexte ( this.btnEnregistrer );
		this.centrerTexte ( this.btnAnnuler     );
		this.centrerTexte ( this.btnSupprimer   );
		this.centrerTexte ( this.btnAjouter     );

		pnlBouttonBD.setBorder ( new EmptyBorder ( 20, 0, 0, 0 ) );

		pnlBouttonBD.add ( this.btnEnregistrer );
		pnlBouttonBD.add ( this.btnAnnuler     );
		pnlBouttonBD.add ( this.btnSupprimer   );
		pnlBouttonBD.add ( this.btnAjouter     );
		
		pnlBoutton.add ( pnlBouttonBD                        );

		pnlContenu.add ( spTab     , BorderLayout.CENTER );
		pnlContenu.add ( pnlBoutton, BorderLayout.SOUTH  );

		this.add ( pnlContenu );

		/* ------------------------- */
		/* Activation des composants */
		/* ------------------------- */

		this.btnEnregistrer.addActionListener ( this );
		this.btnAnnuler    .addActionListener ( this );
		this.btnSupprimer  .addActionListener ( this );
		this.btnAjouter    .addActionListener ( this );
	}

	//TODO: déplacer cette méthode dans une classe utilitaiare Swing
	private void centrerTexte ( JButton btn )
	{
		btn.setHorizontalAlignment ( JButton.CENTER );
		btn.setVerticalAlignment   ( JButton.CENTER );
	}

	public void actionPerformed ( ActionEvent e )
	{
		if ( e.getSource ( ) == this.btnEnregistrer ) //FIXME: appuyer sur enregistrer doit confirmer la combobox avant (si c'est le dernier élément sélectionné) sinon c relou c pas prit en compte mais trkl
		{
			System.out.println( Utilitaire.afficherTypes( this.tab.getDonnees ( ) ) );
			System.out.println( Utilitaire.afficherValeurs( this.tab.getDonnees ( ) ) );
			//this.tab.modifDonnees  ( this.ctrl.getTableau ( this.classe ) );
			/*AFrame.fermer        ( this      );
			new FrameParametrage ( this.ctrl );*/
		}

		if ( e.getSource ( ) == this.btnAnnuler )
		{
			/*AFrame.fermer        ( this );
			new FrameParametrage ( this.ctrl );*/
		}

		if ( e.getSource ( ) == this.btnSupprimer )
		{
			this.tab.supprimerLigne ( );
		}

		if ( e.getSource ( ) == this.btnAjouter )
		{
			this.tab.ajouterLigne ( );
		}
	}

	public Tableau getTab ( ) { return this.tab; }
}
```