package astre.modele.elements;

import javax.swing.JOptionPane;

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
	private Horaire ( Heure heure, ModuleIUT module, int nbHeurePN, int nbSemaine, int nbHeure )
	{
		this.heure     = heure;
		this.module    = module;
		this.nbHeurePN = nbHeurePN;
		this.nbSemaine = nbSemaine;
		this.nbHeure   = nbHeure;
	}

	/** Crée un horraire en prenant en paramètre un tableau d'objet.
	 * @return L'horaire créée si les données du tableau sont correctes.
	 */
	public static Horaire creation ( Object[] horaire )
	{
		Object h  = horaire[0];
		Object m  = horaire[1];
		Object hp = horaire[2];
		Object nb = horaire[3];
		Object nh = horaire[4];

		if ( ! ( h  instanceof Heure   ) || ! ( m  instanceof ModuleIUT ) || ! ( hp instanceof Integer ) || 
		     ! ( nb instanceof Integer ) || ! ( nh instanceof Number ) )
		{
			JOptionPane.showMessageDialog ( null, "Une des données n'est pas du bon type ou est vide.", "Création Impossible", JOptionPane.ERROR_MESSAGE );
			throw new IllegalArgumentException ( "Les données de l'horaire ne sont pas du bon type" );
		}
		
		Heure     heure     = ( Heure     ) h;
		ModuleIUT module    = ( ModuleIUT ) m;
		int       nbHeurePN = (int) Double.parseDouble ( hp.toString ( ) );
		int       nbSemaine = (int) Double.parseDouble ( nb.toString ( ) );
		int       nbHeure   = (int) Double.parseDouble ( nh.toString ( ) );
		

		return Horaire.creation ( heure, module, nbHeurePN, nbSemaine, nbHeure );
	}

	/** Crée un horaire en prenant en paramètre une heure, un module, un nombre d'heure PN, un nombre de semaine et un nombre d'heure répartie.
	 * @return L'horaire créée si les données ont une valeur et que le nombre d'heure PN, le nombre de semaine et le nombre d'heure répartie sont positifs.
	 */
	public static Horaire creation ( Heure heure, ModuleIUT module, int nbHeurePN, int nbSemaine, int nbHeure )
	{
		if ( nbHeurePN < 0 || nbSemaine < 0|| nbHeure < 0  )
		{
			JOptionPane.showMessageDialog ( null, "Les heures doivent être positif", "Création Impossible", JOptionPane.ERROR_MESSAGE );
			throw new IllegalArgumentException ( "Le coef TP doit être supérieur à 0" );
		}
			
		return new Horaire ( heure, module, nbHeurePN, nbSemaine, nbHeure );
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
	@Override
	public String toString ( )
	{
		String sRet = "";

		sRet += "("                       + this.heure.getNom ( ) + ") "             + this.module.getLibLong ( ) + " | NB Heure PN : " + this.nbHeurePN +
		        " | Nombre de semaine : " + this.nbSemaine      + " | NB Heure : " + this.nbHeure;

		return sRet;
	}

}
