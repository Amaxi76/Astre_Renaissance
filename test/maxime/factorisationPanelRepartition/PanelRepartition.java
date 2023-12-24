import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class PanelRepartition extends JPanel
{
	public PanelRepartition()
	{
		this.setLayout ( new GridBagLayout() );
		this.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

		/*
		INFO : POUR L'INSTANT TOUTE LA PARTIE GBC DE PANELREPARTITION CI-DESSOUS EST COMPLEMENT INUTILE ET PEUT ETRE REMPLACE PAR UN BORDERLAYOUT PAR EXEMPLE
		 */
		// Ajout du panel des heures du PN local
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets ( 5, 10, 5, 10 );
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		// INFO : LA LISTE DE STRING CI-DESSOUS PEUT ETRE REMPLACEE PAR UNE LISTE DE N4IMPORTE QUELLE NOM DE TYPE D'HEURE
		this.add ( new PanelHeuresPNLocal ( new String[]{ "CM", "TP", "TD", "Tut", "Bonus" } ), gbc );
		
	}
	
	/*private void ajouterTypeHeurePNLocal()
	{
		JPanel panelTypeHeure;
		panelTypeHeure = new JPanel ( );
		panelTypeHeure.setLayout ( new GridBagLayout() );
		panelTypeHeure.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
	}

	//
	public void initialiserAutres()
	{
		JPanel panelHeuresAffectees;
		JPanel panelHeuresPonctuelles;
		JPanel panelTotalHeures;
		JPanel panelTitres;
	}*/

	/**
	 * Classe interne pour le panel des heures du PN local
	 */
	class PanelHeuresPNLocal extends JPanel
	{
		public PanelHeuresPNLocal ( String[] ensTypeHeure )
		{
			int nbTypesHeure = ensTypeHeure.length;
			this.setLayout( new BoxLayout ( this, BoxLayout.X_AXIS ) );
			this.setBorder( new EmptyBorder ( new Insets ( 5, 10, 5, 10 ) ) );

			for ( String typeHeure : ensTypeHeure )
			{
				this.add ( panelNouveauTypeHeure ( typeHeure ) );
			}
		}

		private static JPanel panelNouveauTypeHeure ( String typeHeure )
		{
			JPanel panelTypeHeure;
			panelTypeHeure = new JPanel ( );
			panelTypeHeure.setLayout ( new GridBagLayout ( ) );
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets ( 2, 5, 2, 5 );

			// Ajout du nom du type d'heure
			gbc.anchor = GridBagConstraints.CENTER;
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.gridwidth = 2;
			panelTypeHeure.add ( new JLabel ( typeHeure ), gbc );

			// Ajout des titres des colonnes
			gbc.gridy = 1;
			gbc.gridwidth = 1;

			gbc.anchor = GridBagConstraints.EAST;
			gbc.gridx = 0;
			panelTypeHeure.add ( new JLabel ( "nb Sem" ), gbc );
			gbc.anchor = GridBagConstraints.WEST;
			gbc.gridx = 1;
			panelTypeHeure.add ( new JLabel ( "nb h/Sem" ), gbc );

			// Ajout des champs de saisie
			gbc.gridy = 2;
			gbc.gridwidth = 1;

			gbc.anchor = GridBagConstraints.EAST;
			gbc.gridx = 0;
			panelTypeHeure.add ( new JTextField ( 3 ), gbc );
			gbc.anchor = GridBagConstraints.WEST;
			gbc.gridx = 1;
			panelTypeHeure.add ( new JTextField ( 3 ), gbc );

			return panelTypeHeure;
		}
	}
}
