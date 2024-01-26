package astre.vue.etats;

import java.awt.Dimension;

/** Page de gestion des Etats
  * @author : Matéo Sa
  * @version : 1.0 - 14/12/2023
  * @date : 14/12/2023
  */

import astre.Controleur;
import astre.vue.outils.AFrame;

public class FrameEtats extends AFrame
{
	private static final String    TITRE     = "Etats";
	private static final Dimension DIMENSION = new Dimension ( 500, 420 );

	private PanelEtats panel;
	
	/** Constructeur de FrameEtats
	 * @param ctrl
	 */
	public FrameEtats ( Controleur ctrl )
	{
		super ( ctrl );
		
		this.panel = new PanelEtats ( this.ctrl );
		
		this.setTitle              ( TITRE     );
		this.setSize               ( DIMENSION );
		this.setLocationRelativeTo ( null      );
		
		this.add ( this.panel );

		this.setVisible ( true );
	}
}
