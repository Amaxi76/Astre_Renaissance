package astre.vue.etats;

import astre.Controleur;
import astre.modele.GenerateurFichier;
import astre.modele.elements.Intervenant;
import astre.modele.elements.ModuleIUT;

/** Page de gestion des intervenants
  * @author : Matéo Sa
  * @version : 1.0 - 11/12/2023
  * @date : 06/12/2023
  */

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.HashMap;

public class PanelEtats extends JPanel implements ActionListener
{
	private JButton btnRecapInter;
	private JButton btnRecapModule;
	private JButton btnRecapTtInter;
	private JButton btnRecapTtModule;
	private JButton btnCsv;

	JComboBox<String> cbInter;
	JComboBox<String> cbModule;
	JComboBox<String> cbStyle;

	HashMap<String,String> style;

	private Controleur ctrl;
	
	/**
	 * 	Panel pour la frame des Etats.
	 */
	public PanelEtats ( Controleur ctrl )
	{
		this.ctrl = ctrl;

		this.setBorder ( new EmptyBorder  ( 10, 10, 10, 10 ) );
		this.setLayout ( new BorderLayout ( 20, 20 ) );

		//styles pour html
		this.style = new HashMap<String, String> ( );
		this.style.put ( "Champs Fleuri    (Rouge et Rose)   ", "Rose" );
		this.style.put ( "Film d'Antan     (Noir et Blanc)   ", "Noir" );
		this.style.put ( "Vague & Marée    (Marine et Corail)", "Bleu" );
		this.style.put ( "Sapin de Noël    (Vert et Marron)  ", "Noel" );
		
		//création des combobox
		this.cbInter = new JComboBox<> ( );
		for ( Intervenant c : this.ctrl.getTable ( Intervenant.class ) )
		{
			cbInter.addItem ( c.getNom ( ) );
		}

		this.cbModule = new JComboBox<> ( );
		for ( ModuleIUT m : this.ctrl.getTable ( ModuleIUT.class ) )
		{
			cbModule.addItem ( m.getCode ( ) + " - " + m.getLibCourt ( ) );
		}

		this.cbStyle = new JComboBox<> ( );
		for ( String s : this.style.keySet ( ) )
		{
			this.cbStyle.addItem ( s );
		}
		cbStyle.setSelectedItem ( "Vague & Marée    (Marine et Corail)" );

		//création des boutons
		this.btnRecapModule   = new JButton ( "Module individuel"      );
		this.btnRecapInter    = new JButton ( "Intervenant individuel" );
		this.btnRecapTtModule = new JButton ( "Tous les Modules"       );
		this.btnRecapTtInter  = new JButton ( "Tous les Intervenants"  );
		this.btnCsv           = new JButton ( "Fichier CSV"            );
		
		JPanel panel = new JPanel ( );

		//Placer les boutons de facon bien mis
		GroupLayout layout = new GroupLayout ( panel );
		panel.setLayout ( layout );

		layout.setAutoCreateGaps ( true );
		layout.setAutoCreateContainerGaps ( true );

		//placer horizontalement les boutons
		GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup ( );

		hGroup.addGroup ( layout.createParallelGroup ( Alignment.CENTER, true ).addComponent ( this.cbInter  ).addComponent ( this.btnRecapInter  ).addComponent ( this.btnRecapTtInter  ) );
		hGroup.addGap   ( 50 );
		hGroup.addGroup ( layout.createParallelGroup ( Alignment.CENTER, true ).addComponent ( this.cbModule ).addComponent ( this.btnRecapModule ).addComponent ( this.btnRecapTtModule ) );
		layout.setHorizontalGroup ( hGroup );

		//placer verticalement les boutons
		GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup ( );

		vGroup.addGroup ( layout.createParallelGroup ( Alignment.BASELINE, true ).addComponent ( this.cbInter         ).addComponent ( this.cbModule         ) );
		vGroup.addGap   ( 20 ); //ajouter d'un espacement
		vGroup.addGroup ( layout.createParallelGroup ( Alignment.BASELINE, true ).addComponent ( this.btnRecapInter   ).addComponent ( this.btnRecapModule   ) );
		vGroup.addGap   ( 20 ); //ajouter d'un espacement
		vGroup.addGroup ( layout.createParallelGroup ( Alignment.BASELINE, true ).addComponent ( this.btnRecapTtInter ).addComponent ( this.btnRecapTtModule ) );
		layout.setVerticalGroup ( vGroup );

		//Mettre touts les boutons à la meme taille
		this.btnRecapInter.setPreferredSize ( new Dimension ( 200, 30 ) );
		layout.linkSize ( this.btnRecapInter, this.btnRecapModule, this.btnRecapTtInter, this.btnRecapTtModule, this.cbInter, this.cbModule );

		JPanel panelHtml = new JPanel ( new GridLayout ( 2, 1 ) );
		panelHtml.add ( new JLabel ( "HTML" ) );
		panelHtml.add ( new JSeparator ( SwingConstants.HORIZONTAL ) );

		JPanel panelCentre = new JPanel ( new BorderLayout ( ) );
		panelCentre.add ( panelHtml, BorderLayout.NORTH  );
		panelCentre.add ( panel    , BorderLayout.CENTER );

		JPanel panelNord = new JPanel ( new GridLayout ( 3, 1 ) );
		panelNord.add ( new JLabel ( "CSV" ) );
		panelNord.add ( new JSeparator ( SwingConstants.HORIZONTAL ) );
		panelNord.add ( this.btnCsv );

		JPanel panelSud = new JPanel ( new BorderLayout ( 10,10 ) );
		panelSud.add ( new JLabel ( "Choix du style :" ), BorderLayout.NORTH  );
		panelSud.add ( this.cbStyle                          , BorderLayout.CENTER );

		this.add ( panelNord  , BorderLayout.NORTH  );
		this.add ( panelCentre, BorderLayout.CENTER );
		this.add ( panelSud   , BorderLayout.SOUTH  );
		
		//met les actionListener
		this.btnRecapInter   .addActionListener ( this );
		this.btnRecapModule  .addActionListener ( this );
		this.btnRecapTtInter .addActionListener ( this );
		this.btnRecapTtModule.addActionListener ( this );
		this.btnCsv          .addActionListener ( this );
	}

	public void actionPerformed ( ActionEvent e )
	{
		if ( e.getSource ( ) == this.btnRecapInter )
		{
			//Génération de l'html pour 1 intervenant
			GenerateurFichier.GenererHTMLIntervenant ( this.ctrl.getTable ( Intervenant.class ).get ( this.cbInter.getSelectedIndex ( ) ), this.style.get ( this.cbStyle.getSelectedItem ( ).toString ( ) ) );
		}
		
		if ( e.getSource ( ) == this.btnRecapModule )
		{
			//Génération de l'html pour 1 module
			GenerateurFichier.GenererHTMLModule ( this.ctrl.getTable ( ModuleIUT.class ).get ( this.cbModule.getSelectedIndex ( ) ), this.style.get ( this.cbStyle.getSelectedItem ( ).toString ( ) ) );
		}
		
		if ( e.getSource ( ) == this.btnCsv )
		{
			//Génération d'un csv pour tous les intervenants
			GenerateurFichier.recapTtInter ( );
		}

		if ( e.getSource ( ) == this.btnRecapTtInter )
		{
			//Génération du html pour tous les intervenants
			GenerateurFichier.GenererHTMLToutIntervenant ( this.ctrl.getTable ( Intervenant.class ), this.style.get ( this.cbStyle.getSelectedItem ( ).toString ( ) ) );
		}

		if ( e.getSource ( ) == this.btnRecapTtModule )
		{
			//Génération du html pour tous les modules
			GenerateurFichier.GenererHTMLToutModule ( this.ctrl.getTable ( ModuleIUT.class ), this.style.get ( this.cbStyle.getSelectedItem ( ).toString ( ) ) );
		}
	}
}
