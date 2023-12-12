package astre.modele.elements;

/** Classe Horaire
  * @author : Alizéa Lebaron
  * @version : 1.0 - 12/12/2023
  * @date : 12/12/2023
  */

public class Horaire 
{
	private Heure     heure;
	private ModuleIUT module;
	private int       nbHeurePN;
	private int       nbSemaine;
	private int       nbHeure;

	/**
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

	/**
	 * @return heure
	 */
	public Heure  getHeure     ( ) { return heure;     }

	/**
	 * @return the module
	 */
	public ModuleIUT getModule    ( ) { return module;    }

	/**
	 * @return the nbHeurePN
	 */
	public int    getNbHeurePN ( ) { return nbHeurePN; }

	/**
	 * @return the nbSemaine
	 */
	public int    getNbSemaine ( ) { return nbSemaine; }

	/**
	 * @return the nbHeure
	 */
	public int    getNbHeure   ( ) { return nbHeure;   }

	/*---------------------------------------*/
	/*                SETTEUR                */
	/*---------------------------------------*/

	/**
	 * @param heure the heure to set
	 */
	public void setHeure     ( Heure  heure        ) { this.heure     = heure;     }

	/**
	 * @param module the module to set
	 */
	public void setModule    ( ModuleIUT module    ) { this.module    = module;    }

	/**
	 * @param nbHeurePN the nbHeurePN to set
	 */
	public void setNbHeurePN ( int    nbHeurePN    ) { this.nbHeurePN = nbHeurePN; }

	/**
	 * @param nbSemaine the nbSemaine to set
	 */
	public void setNbSemaine ( int    nbSemaine    ) { this.nbSemaine = nbSemaine; }

	/**
	 * @param nbHeure the nbHeure to set
	 */
	public void setNbHeure   ( int    nbHeure   ) { this.nbHeure = nbHeure;     }

	/*---------------------------------------*/
	/*                METHODES               */
	/*---------------------------------------*/

	public String toString ()
	{
		String sRet = "";

		sRet += "("                       + this.heure.getNom() + ") "             + this.module.getLibLong() + " | NB Heure PN : " + this.nbHeurePN +
		        " | Nombre de semaine : " + this.nbSemaine      + " | NB Heure : " + this.nbHeure;

		return sRet;
	}

}
