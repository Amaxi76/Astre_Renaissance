package astre.vue.etats;

/** Page de gestion des Etats
  * @author : Matéo Sa
  * @version : 1.0 - 14/12/2023
  * @date : 14/12/2023
  */

import astre.Controleur;
import astre.vue.outils.AFrame;

public class FrameEtats extends AFrame
{
	private PanelEtats panel;
	
	public FrameEtats ( Controleur ctrl )
	{
		super ( ctrl );
		
		this.panel = new PanelEtats ( this.ctrl );
		
		this.setTitle              ( "Etats"    );
		this.setSize               ( 500, 420   );
		this.setLocationRelativeTo ( null       );
		
		this.add ( this.panel );

		this.setVisible ( true );
	}
}
