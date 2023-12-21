package astre.vue.outils;

import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.Dimension;

/** Classe ConstantesVue contenant les constantes liées aux vues
 * @author : Maximilien Lesterlin, Maxime Lemoine
 * @version : 1.0 - 12/12/2023
 * @date : 12/12/2023
 * Attention à la modification des variables déjà définies
 */

public abstract class ConstantesVue
{
	private ConstantesVue ( ) { /*Constructeur privé pour empêcher l'instanciation de la classe*/ }

	public static final int    LARGEUR_COLONNE_NOMBRE     = 3;
	public static final Border MARGE_INTERIEURE_FENETRE   = new EmptyBorder ( 20,20,20,20 );
	public static final int    MARGE_EXTERIEURE_COMPOSANT = 20;
	public static final int    ESPACE_BOUTONS             = 10;

	public static final Dimension DIMENSION_TAB = new Dimension ( 350,400 );
	public static final int NB_SEMESTRE = 6;
}