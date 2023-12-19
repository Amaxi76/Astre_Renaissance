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
public class PopUpErreur extends JDialog implements ActionListener
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

	//Aucun moyen de réactualiser le tablo apres donc rip
	public PopUpErreur ( String titre, String message, String opt1, String opt2, Object o )
	{
		super.setTitle ( titre );

		JPanel panel = new JPanel ( );
		this.lblErreur = new JLabel ( message );

		btn1 = new JButton( opt1 );
		btn2 = new JButton( opt2 );

		this.o=o;

		panel.setLayout( new GridLayout(1, 2, 10, 10) );

		this.setLayout(new BorderLayout(10, 10));

		panel.add(btn1);
		panel.add(btn2);

		this.add( this.lblErreur, BorderLayout.CENTER);
		this.add( panel, BorderLayout.SOUTH);

		this.btn1.addActionListener(this);
		this.btn2.addActionListener(this);

		this.afficher ( );

	}

	//marche mais pose probleme d'actualisation et de surpose de PopoUp
	public void actionPerformed(ActionEvent e)
	{
		if( e.getSource() == btn1 )
		{
			System.out.println("ok");
		}

		if( e.getSource() == btn2 )
		{
			String test = o.getClass().toString();
			String str[] = test.split("\\.");

			BD bd = BD.getInstance();

			switch(str[str.length - 1])
			{
				case "Contrat"     : //del Intervenant
				                     bd.delete ( ( Contrat     ) o ); break;

				case "Heure"       : //del Intervient et Horaire
				                     bd.delete ( ( Heure       ) o ); break;

				case "Intervenant" : bd.delete ( bd.getIntervient ( ((Intervenant) o).getId() ) );
				                     bd.delete ( ( Intervenant ) o ); 
									 //Aucun moyen de recup le tablo pour l'actualiser
									 break;

				case "ModuleIUT"   : //del Horaire et Intervient
				                     bd.delete ( ( ModuleIUT   ) o ); break;

				default : System.out.println("def");
			}
		}

		this.dispose();
	}
}
