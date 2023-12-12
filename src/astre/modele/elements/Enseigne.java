package astre.modele.elements;

/** Classe Enseigne
  * @author : Alizéa Lebaron
  * @version : 1.0 - 12/12/2023
  * @date : 12/12/2023
  */

public class Enseigne 
{
	private Intervenant intervenant;
	private Heure       heure;
	private ModuleIUT   module;
	private int         nbSemaine;
	private int         nbGroupe;
	private int         nbHeure;
	private String      commentaire;

	/**
	 * @param intervenant
	 * @param heure
	 * @param module
	 * @param nbSemaine
	 * @param nbGroupe
	 * @param nbHeure
	 * @param commentaire
	 */
	public Enseigne ( Intervenant intervenant, Heure heure, ModuleIUT module, int nbSemaine, int nbGroupe, int nbHeure, String commentaire ) 
	{
		this.intervenant = intervenant;
		this.heure = heure;
		this.module = module;
		this.nbSemaine = nbSemaine;
		this.nbGroupe = nbGroupe;
		this.nbHeure = nbHeure;
		this.commentaire = commentaire;
	}

	/*---------------------------------------*/
	/*                GETTEUR                */
	/*---------------------------------------*/

	/**
	 * @return intervenant
	 */
	public Intervenant getIntervenant ( ) { return intervenant; }

	/**
	 * @return heure
	 */
	public Heure       getHeure       ( ) { return heure;       }

	/**
	 * @return module
	 */
	public ModuleIUT      getModule      ( ) { return module;      }

	/**
	 * @return nbSemaine
	 */
	public int         getNbSemaine   ( ) { return nbSemaine;   }

	/**
	 * @return nbGroupe
	 */
	public int         getNbGroupe    ( ) { return nbGroupe;    }

	/**
	 * @return nbHeure
	 */
	public int         getNbHeure     ( ) { return nbHeure;     }

	/**
	 * @return commentaire
	 */
	public String      getCommentaire ( ) { return commentaire; }

	/*---------------------------------------*/
	/*                SETTEUR                */
	/*---------------------------------------*/

	/**
	 * @param intervenant the intervenant to set
	 */
	public void setIntervenant ( Intervenant intervenant ) { this.intervenant = intervenant; }

	/**
	 * @param heure the heure to set
	 */
	public void setHeure       ( Heure       heure       ) { this.heure       = heure;       }

	/**
	 * @param module the module to set
	 */
	public void setModule      ( ModuleIUT      module      ) { this.module      = module;      }

	/**
	 * @param nbSemaine the nbSemaine to set
	 */
	public void setNbSemaine   ( int         nbSemaine   ) { this.nbSemaine   = nbSemaine;   }

	/**
	 * @param nbGroupe the nbGroupe to set
	 */
	public void setNbGroupe    ( int         nbGroupe    ) { this.nbGroupe    = nbGroupe;    }

	/**
	 * @param nbHeure the nbHeure to set
	 */
	public void setNbHeure     ( int         nbHeure     ) { this.nbHeure     = nbHeure;     }

	/**
	 * @param commentaire the commentaire to set
	 */
	public void setCommentaire ( String      commentaire ) { this.commentaire = commentaire; }

	/*---------------------------------------*/
	/*                METHODES               */
	/*---------------------------------------*/

	public String toString ( )
	{
		String sRet = "";

		sRet += "L'intervenant " + this.intervenant.getNom() + " intervient en "  + this.heure.getNom() + " dans la matière "   + this.module.getLibLong() +
		        " pendant "      + this.nbSemaine            + " pour "           + this.nbGroupe       + " groupe(s) durant "  + this.nbHeure             + " heures. ";

		return sRet;
	}
	




}
