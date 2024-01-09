package astre.vue.outils;

/** Classe PanelDiagramme
  * @author : Maxime Lemoine
  * @version : 1.0 - 14/12/2023
  * @date : 06/12/2023
 */

// General
import java.util.*;

//IHM
import javax.swing.*;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

//Graphiques
import org.jfree.chart.*;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.*;

import astre.modele.BD;
import astre.modele.elements.Intervient;

import org.jfree.chart.labels.*;

/**
 * @author Maximeuuu
 * Classe permettant de générer seule un diagramme en fournissant les données et les couleurs
 */
public class PanelDiagramme extends JPanel
{
	private static final Color COULEUR_DEFAUT = Color.GRAY;

	private Map donnees;

	private PanelDiagramme ( Map donnees, Color couleurMin, Color couleurMax, String titre, int taille )
	{
		this.donnees = donnees;
		//this.setSize( new Dimension ( 200,50 ) );
		//this.setPreferredSize(new Dimension(200,100));

		//Ajout des données
		final DefaultPieDataset pieDataset = new DefaultPieDataset ( );

		Iterator iterator = this.donnees.entrySet ( ).iterator ( );
		while ( iterator.hasNext ( ) )
		{
			Map.Entry mapentry = ( Map.Entry ) iterator.next ( );
			pieDataset.setValue ( ( String ) mapentry.getKey ( ), ( Double ) mapentry.getValue ( ) );
		}

		final JFreeChart pieChart = ChartFactory.createPieChart ( null, pieDataset, true, true, true );
		final ChartPanel cPanel = new ChartPanel ( pieChart );
		cPanel.setPreferredSize ( new Dimension(taille + 60, taille ) );

		// Ajout des couleurs
		PiePlot plot = ( PiePlot ) pieChart.getPlot ( );

		int nbDonnes = donnees.size ( );
		List<Color> degrade = generateGradientColors ( couleurMin, couleurMax, nbDonnes );
		int cptD=0;
		iterator = this.donnees.entrySet ( ).iterator ( );
		while ( iterator.hasNext ( ) )
		{
			Map.Entry mapentry = ( Map.Entry ) iterator.next ( );

			if ( cptD == nbDonnes-1 ) { plot.setSectionPaint ( ( String ) mapentry.getKey ( ), COULEUR_DEFAUT ); }
			else
			{
				plot.setSectionPaint ( ( String ) mapentry.getKey ( ), degrade.get ( cptD ) );
			}
			cptD++;
		}

		plot.setShadowPaint          ( null );
		plot.setBackgroundAlpha      ( 0.0f );
		plot.setSimpleLabels         ( true );
		plot.setLabelBackgroundPaint ( null );
		plot.setLabelOutlinePaint    ( null );
		plot.setLabelShadowPaint     ( null );
		plot.setLabelPaint           ( Color.WHITE );

		Font font = new Font   ( "Arial", Font.BOLD, 14 );
		plot.setLabelFont      ( font );
		plot.setLabelGenerator ( new StandardPieSectionLabelGenerator ( "{1}h" ) );


		this.add ( cPanel, BorderLayout.CENTER );
		this.setVisible ( true );
	}

	public static PanelDiagramme genererCamembert ( int idIntervenant, int taille )
	{
		// Donnees
		Map<String, Double> donnees = new LinkedHashMap<String,Double> ( );

		donnees.put ( "NA", BD.getInstance ( ).getIntervenant ( idIntervenant ).getHeureMaximum ( ) + 0.0 );

		for ( Intervient inter : BD.getInstance ( ).getTable ( Intervient.class ) )
		{
			if ( inter.getIntervenant ( ).getId ( ) == idIntervenant )
			{
				double nombre = inter.getNbGroupe ( ) * inter.getNbSemaine ( ) * inter.getNbHeure ( ) * inter.getHeure ( ).getCoefTd ( );

				donnees.put ( inter.getHeure ( ).getNom ( ), nombre );
				donnees.put ( "NA", donnees.get ( "NA" ) - nombre );
			}
		}

		return new PanelDiagramme ( donnees, new Color ( 255,99,71 ), new Color ( 255,192,124 ), "Répartitions des heures de " + BD.getInstance ( ).getIntervenant ( idIntervenant ).getNom ( ), taille );
	}

	public static List<Color> generateGradientColors ( Color startColor, Color endColor, int steps )
	{
		List<Color> gradientColors = new ArrayList<> ( );

		for ( int i = 0; i < steps; i++ )
		{
			float ratio = ( float ) i / ( float ) ( steps - 1 );

			int red   = ( int ) ( startColor.getRed   ( ) + ratio * ( endColor.getRed   ( ) - startColor.getRed   ( ) ) );
			int green = ( int ) ( startColor.getGreen ( ) + ratio * ( endColor.getGreen ( ) - startColor.getGreen ( ) ) );
			int blue  = ( int ) ( startColor.getBlue  ( ) + ratio * ( endColor.getBlue  ( ) - startColor.getBlue  ( ) ) );

			Color color = new Color ( red, green, blue );
			gradientColors.add ( color );
		}

		return gradientColors;
	}
}
