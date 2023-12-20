package astre.vue.outils;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import astre.modele.BD;
import astre.modele.elements.*;

/** Classe permettant d'afficher les erreurs.
 *  @author Maxime
 *  @version : 1.0 - 11/12/2023
 *  @date : 11/12/2023
*/
public class PopUpErreur extends JDialog
{
	private JLabel lblErreur;

	private JButton btn1;
	private JButton btn2;

	Object o;
	
	//TODO: faire en sorte que le texte fasse des retour à la ligne pour une meilleur lisibilité
	
	public PopUpErreur ( String titre, String message )
	{
		super.setTitle ( titre );
		JPanel panel = new JPanel ( );
		this.lblErreur = new JLabel ( message );
		panel.add ( this.lblErreur );
		this.setContentPane ( panel );

		this.afficher ( );
	}
	
	private void afficher ( )
	{
		this.pack ( );
		this.setLocationRelativeTo ( null );
		this.setVisible ( true );
		this.requestFocus(); //TODO : demander un premier plan forcé
	}
	
	public PopUpErreur ( String message )
	{
		this ( "Erreur", message );
	}
}
