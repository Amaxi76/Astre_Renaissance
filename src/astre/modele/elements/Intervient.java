package astre.modele.elements;

import javax.swing.JOptionPane;

/** Classe Intervient
  * @author : Alizéa Lebaron
  * @version : 1.0 - 12/12/2023
  * @date : 12/12/2023
  */

public class Intervient 
{
	private Intervenant intervenant;
	private Heure       heure;
	private ModuleIUT   module;
	private int         nbSemaine;
	private int         nbGroupe;
	private double      nbHeure;
	private String      commentaire;

	/** Constructeur d'intervient
	 * @param intervenant
	 * @param heure
	 * @param module
	 * @param nbSemaine
	 * @param nbGroupe
	 * @param nbHeure
	 * @param commentaire
	 */
	private Intervient ( Intervenant intervenant, Heure heure, ModuleIUT module, int nbSemaine, int nbGroupe, double nbHeure, String commentaire ) 
	{
		this.intervenant = intervenant;
		this.heure       = heure;
		this.module      = module;
		this.nbSemaine   = nbSemaine;
		this.nbGroupe    = nbGroupe;
		this.nbHeure     = nbHeure;
		this.commentaire = commentaire;
	}

	/** Crée un objet Intervient en prenant en paramètre un tableau d'objet.
	 * @return L'objet créée si les données du tableau sont correctes.
	 */
	public static Intervient creation ( Object[] heure )
	{
		Object i   = heure[0];
		Object h   = heure[1];
		Object m   = heure[2];
		Object nbS = heure[3];
		Object nbG = heure[4];
		Object nbH = heure[5];
		Object c   = heure[6];

		boolean iOk   = ( i   instanceof Intervenant );
		boolean hOk   = ( h   instanceof Heure       );
		boolean mOk   = ( m   instanceof ModuleIUT   );
		boolean nbSOk = ( nbS instanceof Integer     );
		boolean nbHOk = ( nbH instanceof Double      );
		boolean nbGOk = ( nbG instanceof Integer     );
		boolean cOk   = ( c   instanceof String      );
 
		if ( !iOk || !hOk || !mOk || !nbSOk || !nbGOk || !nbHOk || !cOk )
		{
			JOptionPane.showMessageDialog ( null, "Une des données n'est pas du bon type ou est vide.", "Création Impossible", JOptionPane.ERROR_MESSAGE );
			throw new IllegalArgumentException ( "Les données de l'intervient ne sont pas du bon type" );
		}
		
		return Intervient.creation ( (Intervenant)i, (Heure)h, (ModuleIUT)m, (int)nbS, (int)nbG, (double)nbH, (String)c );
	}

	/** Crée un objet Intervient en prenant en paramètre un Intervenant, une Heure, un ModuleIUT, un nombre de semaine, un nombre de groupe, un nombre d'heure et un commentaire.
	 * @return L'objet créée si les objets ne sont pas vides et que les valeurs numériques sont positives.
	 */
	public static Intervient creation ( Intervenant i, Heure h, ModuleIUT m, int nbS, int nbG, double nbH, String c )
	{
		if ( i == null || h == null || m == null || nbS < 0 || nbG < 0 || nbH < 0 )
		{
			JOptionPane.showMessageDialog ( null, "Une des données est vide.", "Création Impossible", JOptionPane.ERROR_MESSAGE );
			throw new IllegalArgumentException ( "Une des données est vide" );
		}
			
		return new Intervient ( i, h, m, nbS, nbG, nbH, c );
	}

	/*---------------------------------------*/
	/*                GETTEUR                */
	/*---------------------------------------*/

	/** Retourne l'intervenant d'intervient
	 * @return intervenant
	 */
	public Intervenant getIntervenant ( ) { return intervenant; }

	/** Retourne l'heure d'intervient
	 * @return heure
	 */
	public Heure       getHeure       ( ) { return heure;       }

	/** Retourne le module d'intervient
	 * @return module
	 */
	public ModuleIUT   getModule      ( ) { return module;      }

	/** Retourne le nombre de semaine d'intervient
	 * @return nbSemaine
	 */
	public int         getNbSemaine   ( ) { return nbSemaine;   }

	/** Retourne le nombre de groupe d'intervient
	 * @return nbGroupe
	 */
	public int         getNbGroupe    ( ) { return nbGroupe;    }

	/** Retourne le nombre d'heure d'intervient
	 * @return nbHeure
	 */
	public double      getNbHeure     ( ) { return nbHeure;     }

	/** Retourne le commentaire d'intervient
	 * @return commentaire
	 */
	public String      getCommentaire ( ) { return commentaire; }

	/*---------------------------------------*/
	/*                SETTEUR                */
	/*---------------------------------------*/

	/** Permet de modifier l'intervenant
	 * @param intervenant the intervenant to set
	 */
	public void setIntervenant ( Intervenant intervenant ) { this.intervenant = intervenant; }

	/** Permet de modifier l'heure
	 * @param heure the heure to set
	 */
	public void setHeure       ( Heure       heure       ) { this.heure       = heure;       }

	/** Permet de modifier le module
	 * @param module the module to set
	 */
	public void setModule      ( ModuleIUT      module   ) { this.module      = module;      }

	/** Permet de modifier le nombre de semaine
	 * @param nbSemaine the nbSemaine to set
	 */
	public void setNbSemaine   ( int         nbSemaine   ) { this.nbSemaine   = nbSemaine;   }

	/** Permet de modifier le nombre de groupe
	 * @param nbGroupe the nbGroupe to set
	 */
	public void setNbGroupe    ( int         nbGroupe    ) { this.nbGroupe    = nbGroupe;    }

	/** Permet de modifier le nombre d'heure
	 * @param nbHeure the nbHeure to set
	 */
	public void setNbHeure     ( double      nbHeure     ) { this.nbHeure     = nbHeure;     }

	/** Permet de modifier le commentaire
	 * @param commentaire the commentaire to set
	 */
	public void setCommentaire ( String      commentaire ) { this.commentaire = commentaire; }

	/*---------------------------------------*/
	/*                METHODES               */
	/*---------------------------------------*/


	/** Renvoie le descriptif des attributs d'intervient
	 * @return descriptif des attributs d'intervient
	 */
	public String toString ( )
	{
		String sRet = "";

		sRet += "L'intervenant " + this.intervenant.getNom ( ) + " intervient en "  + this.heure.getNom ( ) + " dans la matière "   + this.module.getLibLong ( ) +
		        " pendant "      + this.nbSemaine              + " pour "           + this.nbGroupe         + " groupe(s) durant "  + this.nbHeure               + " heures. ";

		return sRet;
	}
}
