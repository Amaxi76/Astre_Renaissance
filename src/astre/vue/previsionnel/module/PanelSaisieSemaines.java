package astre.vue.previsionnel.module;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import astre.vue.outils.Saisie;

/** Classe PanelSaisieSemaines
 * @author : Maxime Lemoine
 * @version : 1.0 - 20/12/2023
 * @date : 27/12/2023
 */
public class PanelSaisieSemaines extends JPanel
{
	private JTextField txtNbSemaine;
	private JTextField txtNbHeureSemaine;

	/**
	 * @param listener : le KeyListener qui sera ajouté à tous les champs de saisie modifiables
	 * @param typeHeure : le titre de la colonne
	 */
	public PanelSaisieSemaines ( KeyListener listener, String typeHeure )
	{
		this.setLayout ( new GridBagLayout ( ) );
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets ( 2, 5, 2, 5 );

		// Ajout du nom du type d'heure
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		this.add ( new JLabel ( typeHeure ), gbc );

		// Ajout des titres des colonnes
		gbc.gridy = 1;
		gbc.gridwidth = 1;

		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 0;
		this.add ( new JLabel ( "nb Sem" ), gbc );
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 1;
		this.add ( new JLabel ( "nb h/Sem" ), gbc );

		// Ajout des champs de saisie
		gbc.gridy = 2;
		gbc.gridwidth = 1;

		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 0;
		this.txtNbSemaine = Saisie.creerTextFieldEntier ( true );
		this.txtNbSemaine.addKeyListener ( listener );
		this.add ( this.txtNbSemaine, gbc );

		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 1;
		this.txtNbHeureSemaine = Saisie.creerTextFieldEntier ( true );
		this.txtNbHeureSemaine.addKeyListener ( listener );
		this.add ( this.txtNbHeureSemaine, gbc );
	}

	public void setValeurs ( String[] valeurs )
	{
		this.txtNbSemaine     .setText ( valeurs[0] );
		this.txtNbHeureSemaine.setText ( valeurs[1] );
	}

	public int[] getValeurs ( )
	{
		try
		{
			return new int[] { Integer.parseInt ( this.txtNbSemaine.getText ( ) ), Integer.parseInt ( this.txtNbHeureSemaine.getText ( ) ) };
		}
		catch ( NumberFormatException e )
		{
			return new int[] { 0, 0 };
		}
	}
}
