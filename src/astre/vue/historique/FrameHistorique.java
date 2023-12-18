package astre.vue.historique;

/** Page de gestion des intervenants
  * @author : Matéo Sa
  * @version : 1.0 - 11/12/2023
  * @date : 06/12/2023
  */

import astre.Controleur;
import astre.vue.outils.AFrame;
import javax.swing.*;

public class FrameHistorique extends AFrame
{
	private PanelHistorique panel;
	
	public FrameHistorique ( Controleur ctrl )
	{
		super ( ctrl );
		
		this.panel = new PanelHistorique ( this.ctrl );
		
		this.setTitle              ( "Historique" );
		this.setSize               ( 1000,500     );
		this.setLocationRelativeTo ( null         );
		
		this.add ( this.panel );

		this.setVisible ( true );
	}
}
