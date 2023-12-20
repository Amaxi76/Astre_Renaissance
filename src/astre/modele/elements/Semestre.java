package astre.modele.elements;

/** Classe Semestre 
  * @author : Alizéa LEBARON
  * @version : 1.0 - 11/12/2023
  * @date : 06/12/2023
  */

public class Semestre
{
	private int idSemestre;
	private int nbGroupeTP;
	private int nbGroupeTD;
	private int nbEtudiant;
	private int nbSemaine;

	/*---------------------------------------*/
	/*             CONSTRUCTEUR              */
	/*---------------------------------------*/ 

	/** Constructeur d'un semestre
	 * @param idSemestre
	 * @param nbGroupeTP
	 * @param nbGroupeTD
	 * @param nbEtudiant
	 * @param nbSemaine
	 */
	public Semestre  (int idSemestre, int nbGroupeTP, int nbGroupeTD, int nbEtudiant, int nbSemaine ) 
	{
		this.idSemestre = idSemestre;
		this.nbGroupeTP = nbGroupeTP;
		this.nbGroupeTD = nbGroupeTD;
		this.nbEtudiant = nbEtudiant;
		this.nbSemaine  = nbSemaine ;
	}

	/*---------------------------------------*/
	/*                GETTEUR                */
	/*---------------------------------------*/ 

	/** Retourne l'id d'un semestre
	 * @return idSemestre
	 */
	public int getIdSemestre ( ) { return this.idSemestre; }

	/** Retourne le nombre de groupe TP
	 * @return nbGroupeTP
	 */
	public int getNbGroupeTP ( ) { return this.nbGroupeTP; }

	/** Retourne le nombre de groupe TD
	 * @return nbGroupeTD
	 */
	public int getNbGroupeTD ( ) { return this.nbGroupeTD; }

	/** Retourne le nombre d'étudiant
	 * @return nbEtudiant
	 */
	public int getNbEtudiant ( ) { return this.nbEtudiant; }

	/** Retourne le nombre de Semaine
	 * @return nbSemaine
	 */
	public int getNbSemaine ( ) { return this.nbSemaine; }

	/*---------------------------------------*/
	/*                SETTEUR                */
	/*---------------------------------------*/

	/** Permet de modifier l'Id du semestre
	 * @param idSemestre idSemestre
	 */
	public void setIdSemestre ( int idSemestre ) { this.idSemestre = idSemestre; }

	/** Permet de modifier le nombre de groupe TP
	 * @param nbGroupeTP nbGroupeTP
	 */
	public void setNbGroupeTP ( int nbGroupeTP ) { this.nbGroupeTP = nbGroupeTP; }

	/** Permet de modifier le nombre de groupe TD
	 * @param nbGroupeTD nbGroupeTD
	 */
	public void setNbGroupeTD ( int nbGroupeTD ) { this.nbGroupeTD = nbGroupeTD; }

	/** Permet de modifer le nombre d'étudiant 
	 * @param nbEtudiant nbEtudiant
	 */
	public void setNbEtudiant ( int nbEtudiant ) { this.nbEtudiant = nbEtudiant; }

	/** Permet de modifier le nombre de semaine
	 * @param nbSemaine nbSemaine
	 */
	public void setNbSemaine ( int nbSemaine ) { this.nbSemaine = nbSemaine; }

	/*---------------------------------------*/
	/*                METHODES               */
	/*---------------------------------------*/

	/** Renvoie une description d'un semestre
	 * @return descriptif des attributs de semestre
	 */
	public String toString ( )
	{
		String sRet = "";

		sRet = String.format ( "IdSemestre        : %-5d - ",  this.idSemestre ) +
			   String.format ( "Nombre groupe TD  : %02d - ",  this.nbGroupeTD ) +
			   String.format ( "Nombre groupe TP  : %02d - ",  this.nbGroupeTP ) +
			   String.format ( "Nombre d'étudiant : %02d"   ,  this.nbEtudiant ) +
			   String.format ( "Nombre de semaine : %02d"   ,  this.nbSemaine  ) ;

		return sRet;
	}
}
