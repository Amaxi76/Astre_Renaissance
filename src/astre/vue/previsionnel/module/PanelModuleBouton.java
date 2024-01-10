package astre.vue.previsionnel.module;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import astre.Controleur;
import astre.vue.outils.AFrame;
import astre.vue.previsionnel.FramePrevisionnel;

/** Classe PanelModuleBouton
  * @author : Clémentin Ly, Maximilien Lesterlin, Maxime Lemoine
  * @version : 2.0 - 14/12/2023
  * @date : 11/12/2023
  */

public class PanelModuleBouton  extends JPanel implements ActionListener
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	private Controleur ctrl;
	private FrameModule frm;

	private JButton btnEnregistrer;
	private JButton btnAnnuler;

	private char action;

	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/

	public PanelModuleBouton ( Controleur ctrl, FrameModule frm, char action )
	{
		this.ctrl = ctrl;
		this.frm  = frm;
		this.action = action;

		/* ------------------------- */
		/* Création des composants   */
		/* ------------------------- */

		this.btnEnregistrer = new JButton ( "Enregistrer" );
		this.btnAnnuler     = new JButton ( "Annuler"     );

		this.add ( this.btnEnregistrer );
		this.add ( this.btnAnnuler     );

		/* ------------------------- */
		/* Activation des composants */
		/* ------------------------- */

		this.btnEnregistrer.addActionListener ( this );
		this.btnAnnuler    .addActionListener ( this );
	}

	/* ActionListener */
	public void actionPerformed ( ActionEvent e )
	{
		if ( e.getSource ( ) == this.btnEnregistrer )
		{
			try
			{
				this.frm.majDonnees ( this.action );
				AFrame.fermerFenetreContenant ( this );
				( ( FramePrevisionnel ) this.ctrl.getFrameActuelle ( ) ).majTableau ( );
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog ( null, ex.getMessage ( ), "Erreur de création ", JOptionPane.ERROR_MESSAGE );
				//ex.printStackTrace ( );
			}
		}

		if ( e.getSource ( ) == this.btnAnnuler )
		{
			AFrame.fermerFenetreContenant ( this );
		}
	}
}
