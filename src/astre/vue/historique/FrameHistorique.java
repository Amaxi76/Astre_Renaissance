package astre.vue.historique;

import java.awt.BorderLayout;
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

public class FrameHistorique extends AFrame  implements ActionListener
{
	private PanelHistorique panel;
	private JButton         btnRetour;
	
	public FrameHistorique ( Controleur ctrl )
	{
		super ( ctrl );

		this.setLayout             ( new BorderLayout ( 10, 10 ) );
		this.setTitle              ( "Historique"                );
		this.setSize               ( 1000,600                    );
		this.setLocationRelativeTo ( null                        );
		
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
