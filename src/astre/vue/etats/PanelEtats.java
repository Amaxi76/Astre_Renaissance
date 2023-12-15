package astre.vue.etats;

import astre.Controleur;
import astre.modele.GenerateurFichier;
import astre.modele.elements.Contrat;
import astre.modele.elements.Intervenant;
import astre.modele.elements.ModuleIUT;

/** Page de gestion des intervenants
  * @author : Matéo Sa
  * @version : 1.0 - 11/12/2023
  * @date : 06/12/2023
  */

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;
import java.awt.event.*;

public class PanelEtats extends JPanel implements ActionListener
{
	private JButton btnRecapInter;
	private JButton btnRecapModule;
	private JButton btnRecapTtInter;

	JComboBox<String> cbEdit;
	JComboBox<String> cbModule;

	private Controleur ctrl;
	
	/**
	 * 	Panel pour la frame des Etats.
	 * @author Matéo
	 */
	public PanelEtats ( Controleur ctrl )
	{
		this.ctrl = ctrl;

		this.setBorder(new EmptyBorder(10, 10, 10, 10));
		this.setLayout( new GridLayout( 6, 1, 10, 10) );

		this.btnRecapInter   = new JButton ( "Intervenant individuel" );

		this.cbEdit = new JComboBox<> ( );
		for ( Intervenant c : this.ctrl.getTable( Intervenant.class ) )
		{
			cbEdit.addItem ( c.getNom ( ) );
		}

		this.cbModule = new JComboBox<> ( );
		for ( ModuleIUT m : this.ctrl.getTable( ModuleIUT.class ) )
		{
			cbModule.addItem ( m.getCode ( ) + " - " + m.getLibCourt ( ) );
		}

		this.btnRecapModule  = new JButton ( "Module Individuels"     );
		this.btnRecapTtInter = new JButton ( "Tous les intervenants"  );
		
		//Ajout des composants
		this.add ( new JLabel ( "Générer un récapitulatif pour :" ) );
		this.add ( this.cbEdit          );
		this.add ( this.btnRecapInter   );
		this.add ( this.cbModule        );
		this.add ( this.btnRecapModule  );
		this.add ( this.btnRecapTtInter );
		
		//met les actionListener
		this.btnRecapInter  .addActionListener ( this );
		this.btnRecapModule .addActionListener ( this );
		this.btnRecapTtInter.addActionListener ( this );
	}

	public void actionPerformed ( ActionEvent e )
	{
		if ( e.getSource ( ) == this.btnRecapInter )
		{
			GenerateurFichier.GenererHTMLIntervenant( this.ctrl.getTable(Intervenant.class).get(this.cbEdit.getSelectedIndex()) );
		}
		
		if ( e.getSource ( ) == this.btnRecapModule )
		{
			GenerateurFichier.GenererHTMLModule( this.ctrl.getTable(ModuleIUT.class).get(this.cbModule.getSelectedIndex()) );
		}
		
		if ( e.getSource ( ) == this.btnRecapTtInter )
		{
			GenerateurFichier.recapTtInter();
		}
	}
}
