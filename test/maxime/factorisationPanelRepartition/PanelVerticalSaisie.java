import java.awt.Dimension;
import java.awt.event.KeyListener;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/** Classe PanelVerticalSaisie
 * @author : Maxime Lemoine
 * @version : 1.0 - 20/12/2023
 * @date : 27/12/2023
 */
public class PanelVerticalSaisie extends JPanel
{
	private JTextField[] ensTxt;

	/**
	 * @param listener : le KeyListener qui sera ajouté à tous les champs de saisie modifiables
	 * @param titreColonne : le titre de la colonne
	 * @param saisiesModifiables : un tableau de booléens indiquant le nombre de champ de saisie et s'il est modifiable ou non
	 */
	public PanelVerticalSaisie ( KeyListener listener, String titreColonne, boolean[] saisiesModifiables )
	{
		this.ensTxt = new JTextField[ saisiesModifiables.length ];

		this.setLayout ( new BoxLayout ( this, BoxLayout.Y_AXIS ) );
		this.setBorder( new EmptyBorder( new Insets ( 5,5,5,5 ) ) );
		
		this.add ( new JLabel ( titreColonne ) );

		for ( int i = 0; i < saisiesModifiables.length; i++ )
		{
			boolean estModifiable = saisiesModifiables[ i ];
			this.ensTxt[i] = FrameModule.creerTextFieldEntier ( estModifiable );
			
			if ( estModifiable )
				this.ensTxt[i].addKeyListener ( listener );
	
			this.add ( this.ensTxt[i] );
			this.add(Box.createRigidArea(new Dimension(0,5)));
		}
	}

	public void setValeur ( int index, double valeur )
	{
		this.ensTxt[ index ].setText ( FrameModule.formaterDouble ( valeur ) );
	}

	public double[] getValeurs ( )
	{
		double[] valeurs = new double[ this.ensTxt.length ];

		for ( int i = 0; i < valeurs.length; i++ )
		{
			try
			{
				valeurs[ i ] = Double.parseDouble ( this.ensTxt[i].getText () );
			}
			catch ( NumberFormatException e )
			{
				valeurs[ i ] = 0.0;
			}
		}
		return valeurs;
	}
}