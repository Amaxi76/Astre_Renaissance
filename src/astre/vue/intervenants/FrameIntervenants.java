package astre.vue.intervenants;

import java.awt.Dimension;

/** Page de gestion des intervenants
  * @author : Matéo Sa
  * @version : 1.0 - 11/12/2023
  * @date : 06/12/2023
  */

import astre.Controleur;
import astre.vue.outils.AFrame;

public class FrameIntervenants extends AFrame
{
	private static final String    TITRE     = "Intervenants";
	private static final Dimension DIMENSION = new Dimension ( 1000,600 );
	
	private PanelIntervenants panel;
	
	public FrameIntervenants ( Controleur ctrl )
	{
		super ( ctrl );
		this.panel = new PanelIntervenants ( this.ctrl );
		
		this.setTitle              ( TITRE     );
		this.setSize               ( DIMENSION );
		this.setLocationRelativeTo ( null           );
		
		this.add ( this.panel );

		this.setVisible ( true );
	}
}
