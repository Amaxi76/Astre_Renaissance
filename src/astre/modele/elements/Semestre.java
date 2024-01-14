package astre.modele.elements;

/** Classe Semestre 
  * @author : Alizéa LEBARON, Maximilien Lesterlin
  * @version : 2.0 - 14/01/2024
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
	 * @param idSemestre l'identifiant du semestre
	 * @param nbGroupeTP le nombre de groupe TP
	 * @param nbGroupeTD le nombre de groupe TD
	 * @param nbEtudiant le nombre d'étudiant
	 * @param nbSemaine le nombre de semaine
	 */
	private Semestre ( int idSemestre, int nbGroupeTP, int nbGroupeTD, int nbEtudiant, int nbSemaine )
	{
		this.idSemestre = idSemestre;
		this.nbGroupeTP = nbGroupeTP;
		this.nbGroupeTD = nbGroupeTD;
		this.nbEtudiant = nbEtudiant;
		this.nbSemaine  = nbSemaine ;
	}

	/*---------------------------------------*/
	/*                FACTORY                */
	/*---------------------------------------*/

	/** Factory de semestre à partir d'un tableau
	 * @param semestre Prend un tableau d'objet définissant le semestre
	 * @return Le semestre crée si les données du tableau sont correctes.
	 */
	public static Semestre creation ( Object[] semestre )
	{
		Object i  = semestre[0];
		Object tp = semestre[1];
		Object td = semestre[2];
		Object e  = semestre[3];
		Object s  = semestre[4];

		if ( ! ( i  instanceof Integer ) ) throw new IllegalArgumentException ( "L'identifiant n'est pas du bon type" );
		if ( ! ( tp instanceof Integer ) ) throw new IllegalArgumentException ( "Le nombre de groupe TP n'est pas du bon type" );
		if ( ! ( td instanceof Integer ) ) throw new IllegalArgumentException ( "Le nombre de groupe TD n'est pas du bon type" );
		if ( ! ( e  instanceof Integer ) ) throw new IllegalArgumentException ( "Le nombre d'étudiant n'est pas du bon type" );
		if ( ! ( s  instanceof Integer ) ) throw new IllegalArgumentException ( "Le nombre de semaine n'est pas du bon type" );
		
		int idSemestre = ( int ) i ;
		int nbGroupeTP = ( int ) tp;
		int nbGroupeTD = ( int ) td;
		int nbEtudiant = ( int ) e ;
		int nbSemaine  = ( int ) s ;

		return Semestre.creation ( idSemestre, nbGroupeTP, nbGroupeTD, nbEtudiant, nbSemaine );
	}

	/** Factory de semestre prenant en paramètres toutes les données d'un semestre
	 * @param id l'identifiant du semestre
	 * @param nom le nom du semestre
	 * @param prenom le prénom du semestre
	 * @param heureService le nombre d'heure de service du semestre
	 * @param heureMaximum le nombre d'heure maximum du semestre
	 * @param contrat le contrat du semestre
	 * @return Le semestre crée si les données ont une valeur et que le nombre d'heure de service est inférieur au nombre d'heure maximum.
	 */
	public static Semestre creation ( int idSemestre, int nbGroupeTP, int nbGroupeTD, int nbEtudiant, int nbSemaine )
	{
		if ( nbGroupeTP < 0 ) throw new IllegalArgumentException ( "Le nombre de groupe TP est négatif" );
		if ( nbGroupeTD < 0 ) throw new IllegalArgumentException ( "Le nombre de groupe TD est négatif" );
		if ( nbEtudiant < 0 ) throw new IllegalArgumentException ( "Le nombre d'étudiant est négatif"   );
		if ( nbSemaine  < 0 ) throw new IllegalArgumentException ( "Le nombre de semaine est négatif"   );

		return new Semestre ( idSemestre, nbGroupeTP, nbGroupeTD, nbEtudiant, nbSemaine );
	}

	/*---------------------------------------*/
	/*                GETTEUR                */
	/*---------------------------------------*/ 

	/** Retourne l'id d'un semestre
	 * @return l'indentifiant du semestre
	 */
	public int getIdSemestre ( ) { return this.idSemestre; }

	/** Retourne le nombre de groupe TP
	 * @return le nombre de groupe TP
	 */
	public int getNbGroupeTP ( ) { return this.nbGroupeTP; }

	/** Retourne le nombre de groupe TD
	 * @return le nombre de groupe TD
	 */
	public int getNbGroupeTD ( ) { return this.nbGroupeTD; }

	/** Retourne le nombre d'étudiant
	 * @return le nombre d'étudiant
	 */
	public int getNbEtudiant ( ) { return this.nbEtudiant; }

	/** Retourne le nombre de Semaine
	 * @return le nombre de semaine
	 */
	public int getNbSemaine ( ) { return this.nbSemaine;   }

	/*---------------------------------------*/
	/*                SETTEUR                */
	/*---------------------------------------*/

	/** Permet de modifier l'Id du semestre
	 * @param idSemestre l'identifiant du semestre
	 */
	public void setIdSemestre ( int idSemestre ) { this.idSemestre = idSemestre; }

	/** Permet de modifier le nombre de groupe TP
	 * @param nbGroupeTP le nombre de groupe TP
	 */
	public void setNbGroupeTP ( int nbGroupeTP ) { this.nbGroupeTP = nbGroupeTP; }

	/** Permet de modifier le nombre de groupe TD
	 * @param nbGroupeTD le nombre de groupe TD
	 */
	public void setNbGroupeTD ( int nbGroupeTD ) { this.nbGroupeTD = nbGroupeTD; }

	/** Permet de modifer le nombre d'étudiant 
	 * @param nbEtudiant le nombre d'étudiant
	 */
	public void setNbEtudiant ( int nbEtudiant ) { this.nbEtudiant = nbEtudiant; }

	/** Permet de modifier le nombre de semaine
	 * @param nbSemaine le nombre de semaine
	 */
	public void setNbSemaine ( int nbSemaine   ) { this.nbSemaine = nbSemaine; }

	/*---------------------------------------*/
	/*                METHODES               */
	/*---------------------------------------*/

	/** Indique si deux semestres sont égaux
	 * @return true si les deux semestres sont égaux, false sinon
	 */
	@Override
	public boolean equals ( Object o )
	{
		if ( ! ( o instanceof Semestre ) ) return false;
		if ( o == this                   ) return true;

		Semestre c = ( Semestre ) o;

		return this.idSemestre == c.idSemestre &&
		       this.nbGroupeTP == c.nbGroupeTP &&
		       this.nbGroupeTD == c.nbGroupeTD &&
		       this.nbEtudiant == c.nbEtudiant &&
		       this.nbSemaine  == c.nbSemaine ;
	}

	/** Renvoie une description d'un semestre
	 * @return descriptif des attributs de semestre
	 */
	public String toString ( )
	{
		return String.format ( "Semestre%nIdentifiant du semestre : %d%nNombre de groupe TP     : %d%nNombre de groupe TD     : %d%nNombre d'étudiant       : %d%nNombre de semaine       : %d", this.idSemestre, this.nbGroupeTP, this.nbGroupeTD, this.nbEtudiant, this.nbSemaine );
	}
}