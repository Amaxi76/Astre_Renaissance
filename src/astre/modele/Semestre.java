package astre.modele;

/** Classe Semestre 
  * @author : Alizéa LEBARON
  * @version : 1.0 - jj/mm/aaaa (Sauf avec monsieur Seguin ou aaaa-mm-jj)
  */

public class Semestre
{
	private int idSemestre;
	private int nbGroupeTP;
	private int nbGroupeTD;
	private int nbEtudiant;
	private int nbSemestre;

	/*---------------------------------------*/
	/*                GETTEUR                */
	/*---------------------------------------*/ 

	/**
	 * @return the idSemestre
	 */
	public int getIdSemestre ( ) { return idSemestre; }

	/**
	 * @return the nbGroupeTP
	 */
	public int getNbGroupeTP ( ) { return nbGroupeTP; }

	/**
	 * @return the nbGroupeTD
	 */
	public int getNbGroupeTD ( ) { return nbGroupeTD; }

	/**
	 * @return the nbEtudiant
	 */
	public int getNbEtudiant ( ) { return nbEtudiant; }

	/**
	 * @return the nbSemestre
	 */
	public int getNbSemestre ( ) { return nbSemestre; }

	/*---------------------------------------*/
	/*                SETTEUR                */
	/*---------------------------------------*/

	/**
	 * @param idSemestre the idSemestre to set
	 */
	public void setIdSemestre ( int idSemestre ) { this.idSemestre = idSemestre; }

	/**
	 * @param nbGroupeTP the nbGroupeTP to set
	 */
	public void setNbGroupeTP ( int nbGroupeTP ) { this.nbGroupeTP = nbGroupeTP; }

	/**
	 * @param nbGroupeTD the nbGroupeTD to set
	 */
	public void setNbGroupeTD ( int nbGroupeTD ) { this.nbGroupeTD = nbGroupeTD; }

	/**
	 * @param nbEtudiant the nbEtudiant to set
	 */
	public void setNbEtudiant ( int nbEtudiant ) { this.nbEtudiant = nbEtudiant; }

	/**
	 * @param nbSemestre the nbSemestre to set
	 */
	public void setNbSemestre ( int nbSemestre ) { this.nbSemestre = nbSemestre; }


	
}
