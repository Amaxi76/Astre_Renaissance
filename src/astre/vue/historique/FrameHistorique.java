package astre.vue.historique;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/** Page de gestion de l'historique
  * @author : Matéo Sa
  * @version : 1.0 - 18/12/2023
  * @date : 18/12/2023
  */

import astre.Controleur;
import astre.vue.outils.AFrame;

public class FrameHistorique extends AFrame implements ActionListener
{
	private static final String    TITRE     = "Historique";
	private static final Dimension DIMENSION = new Dimension ( 1000, 600 );

	private PanelHistorique panel;
	private JButton         btnRetour;
	
	/** Constructeur de FrameHistorique
	 * @param ctrl
	 */
	public FrameHistorique ( Controleur ctrl )
	{
		super ( ctrl );

		this.setTitle              ( TITRE                       );
		this.setSize               ( DIMENSION                   );
		this.setLocationRelativeTo ( null                        );
		this.setLayout             ( new BorderLayout ( 10, 10 ) );
		
		this.panel     = new PanelHistorique ( this.ctrl );
		this.btnRetour = new JButton         ( "Retour"  );
		
		this.add ( this.panel    , BorderLayout.CENTER );
		this.add ( this.btnRetour, BorderLayout.SOUTH  );

		this.btnRetour.addActionListener ( this );

		this.setVisible ( true );
	}

	public void actionPerformed ( ActionEvent e )
	{
		AFrame.retourAccueil ( ctrl );
		this.dispose ( );
	}
}
