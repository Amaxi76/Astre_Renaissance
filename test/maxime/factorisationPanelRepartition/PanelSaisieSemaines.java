import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
		this.txtNbSemaine = FrameModule.creerTextFieldEntier ( true );
		this.txtNbSemaine.addKeyListener ( listener );
		this.add ( this.txtNbSemaine, gbc );

		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 1;
		this.txtNbHeureSemaine = FrameModule.creerTextFieldEntier ( true );
		this.txtNbHeureSemaine.addKeyListener ( listener );
		this.add ( this.txtNbHeureSemaine, gbc );
	}

	public void setValeurs ( String[] valeurs )
	{
		this.txtNbSemaine.setText ( valeurs[0] );
		this.txtNbSemaine.setText ( valeurs[1] );
	}

	public double[] getValeurs ( )
	{
		try
		{
			return new double[] { Double.parseDouble ( this.txtNbSemaine.getText ( ) ), Double.parseDouble ( this.txtNbHeureSemaine.getText ( ) ) };
		}
		catch ( NumberFormatException e )
		{
			return new double[] { 0.0, 0.0 };
		}
	}
}
