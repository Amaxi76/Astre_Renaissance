package astre.vue.historique;

/** Page de gestion de l'historique
  * @author : Matéo Sa
  * @version : 1.0 - 18/12/2023
  * @date : 18/12/2023
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
