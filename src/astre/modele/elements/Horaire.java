package astre.modele.elements;

/** Classe Horaire
  * @author : Alizéa Lebaron
  * @version : 1.1 - 19/12/2023
  * @date : 12/12/2023
  */

public class Horaire
{
	private Heure     heure;
	private ModuleIUT module;
	private int       nbHeurePN;
	private int       nbSemaine;
	private int       nbHeure;

	/** Constructeur d'horaire
	 * @param heure
	 * @param module
	 * @param nbHeurePN
	 * @param nbSemaine
	 * @param nbHeure
	 */
	public Horaire ( Heure heure, ModuleIUT module, int nbHeurePN, int nbSemaine, int nbHeure )
	{
		this.heure     = heure;
		this.module    = module;
		this.nbHeurePN = nbHeurePN;
		this.nbSemaine = nbSemaine;
		this.nbHeure   = nbHeure;
	}

	/*---------------------------------------*/
	/*                GETTEUR                */
	/*---------------------------------------*/

	/** Retourne l'heure d'une horaire
	 * @return heure
	 */
	public Heure  getHeure     ( ) { return heure;     }

	/** Retourne le module d'une horaire
	 * @return the module
	 */
	public ModuleIUT getModule ( ) { return module;    }

	/** Retourne le nombre d'heure PN d'une horaire
	 * @return the nbHeurePN
	 */
	public int    getNbHeurePN ( ) { return nbHeurePN; }

	/** Retourne le nombre de semaine d'une horaire
	 * @return the nbSemaine
	 */
	public int    getNbSemaine ( ) { return nbSemaine; }

	/** Retourne le nombre d'heure répartie d'une horaire
	 * @return the nbHeure
	 */
	public int    getNbHeure   ( ) { return nbHeure;   }

	/*---------------------------------------*/
	/*                SETTEUR                */
	/*---------------------------------------*/

	/** Permet de modifier l'heure
	 * @param heure the heure to set
	 */
	public void setHeure     ( Heure  heure        ) { this.heure     = heure;     }

	/** Permet de modifier le module
	 * @param module the module to set
	 */
	public void setModule    ( ModuleIUT module    ) { this.module    = module;    }

	/** Permet de modifier le nombre d'heure PN
	 * @param nbHeurePN the nbHeurePN to set
	 */
	public void setNbHeurePN ( int    nbHeurePN    ) { this.nbHeurePN = nbHeurePN; }

	/** Permet de modifier le nombre de semaine
	 * @param nbSemaine the nbSemaine to set
	 */
	public void setNbSemaine ( int    nbSemaine    ) { this.nbSemaine = nbSemaine; }

	/** Permet de modifier le nombre d'heure répartie
	 * @param nbHeure the nbHeure to set
	 */
	public void setNbHeure   ( int    nbHeure      ) { this.nbHeure = nbHeure;     }

	/*---------------------------------------*/
	/*                METHODES               */
	/*---------------------------------------*/

	/** Renvoie la description et le contenu d'une horaire
	 * @return Une description du contenue d'une horaire
	 */
	public String toString ( )
	{
		String sRet = "";

		sRet += "("                       + this.heure.getNom ( ) + ") "             + this.module.getLibLong ( ) + " | NB Heure PN : " + this.nbHeurePN +
		        " | Nombre de semaine : " + this.nbSemaine      + " | NB Heure : " + this.nbHeure;

		return sRet;
	}

}
