package astre.vue.parametrage;

import astre.Controleur;
import astre.vue.outils.ConstantesVue;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

/** Classe PanelEnsParametre
  * @author : Maximilien LESTERLIN
  * @version : 1.0 - 11/12/23
  * @date : 06/12/2023
  */

public class PanelEnsParametre extends JTabbedPane
{
	private Controleur     ctrl;
	private PanelContrat   pnlContrat;
	private PanelTypeHeure pnlTypeHeure;
	
	public PanelEnsParametre ( Controleur ctrl )
	{
		// Configuration
		this.ctrl         = ctrl;
	
		//this.setUI              ( new BasicTabbedPaneUI ( ) );
		this.setTabLayoutPolicy ( JTabbedPane.SCROLL_TAB_LAYOUT );
		this.setPreferredSize   ( ConstantesVue.DIMENSION_TAB );

		// Creation et positionnement des composants
		this.pnlContrat   = new PanelContrat   ( this.ctrl );
		this.pnlTypeHeure = new PanelTypeHeure ( this.ctrl );

		

		

		// Positionnement des composants
		this.add ( "Contrat"   , this.pnlContrat   );
		this.add ( "Type Heure", this.pnlTypeHeure );

		
	}


	
}
