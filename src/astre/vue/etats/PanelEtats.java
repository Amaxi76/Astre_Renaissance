package astre.vue.etats;

import astre.Controleur;

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

	private Controleur ctrl;
	
	/**
	 * 	Panel pour la frame des Etats.
	 * @author Matéo
	 */
	public PanelEtats ( Controleur ctrl )
	{
		this.ctrl = ctrl;

		this.setBorder(new EmptyBorder(10, 10, 10, 10));
		this.setLayout( new GridLayout( 4, 1, 10, 10) );

		this.btnRecapInter   = new JButton ( "Intervenant individuel" );
		this.btnRecapModule  = new JButton ( "Tous les modules"       );
		this.btnRecapTtInter = new JButton ( "Tous les intervenants"  );
		
		//Ajout des composants
		this.add ( new JLabel ( "Générer un récapitulatif pour :" ) );
		this.add ( this.btnRecapInter   );
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

		}
		
		if ( e.getSource ( ) == this.btnRecapModule )
		{
			
		}
		
		if ( e.getSource ( ) == this.btnRecapTtInter )
		{
			
		}
	}
}
