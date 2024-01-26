package astre.vue.outils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

/** Classe ConstantesVue contenant les constantes liées aux vues
 * @author : Maximilien Lesterlin, Maxime Lemoine
 * @version : 1.0 - 12/12/2023
 * @date : 12/12/2023
 * Attention à la modification des variables déjà définies
 */

public abstract class ConstantesVue
{
	private ConstantesVue ( ) { /*Constructeur privé pour empêcher l'instanciation de la classe*/ }

	// Dimensions des composants
	public static final Dimension DIMENSION_TAB = new Dimension ( 350,400 );

	// Dimensions dispositions
	public static final int    LARGEUR_COLONNE_NOMBRE     = 3;
	public static final Border MARGE_INTERIEURE_FENETRE   = new EmptyBorder ( 20,20,20,20 );
	public static final int    MARGE_EXTERIEURE_COMPOSANT = 20;
	public static final int    ESPACE_BOUTONS             = 10;

	// Couleurs
	public static final Color VERT_VALIDATION = new Color ( 0, 109, 10 );
	public static final Color ROUGE_ERREUR    = Color.RED;

	// Images
	public static final Image IMAGE_ACCUEIL = Toolkit.getDefaultToolkit ( ).getImage ( "./data/images/astre.png"       );
	public static final Image IMAGE_ASTRE   = Toolkit.getDefaultToolkit ( ).getImage ( "./data/images/astre_petit.png" );

	// Autres
	public static final int NB_SEMESTRE = 6; //TODO: à déplacer dans le métier / Controleur
}