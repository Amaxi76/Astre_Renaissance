package astre.modele.elements;

/** Classe Intervenant
  * @author : Maximilien Lesterlin, Alizéa Lebaron
  * @version : 2.0 - 14/01/2024
  * @date : 06/12/2023
  */

public class Intervenant
{
	private int     id;
	private String  nom;
	private String  prenom;
	private double  nbHeureService;
	private double  maxHeureService;
	private double  coefTP;
	private Contrat contrat;

	/** Constructeur d'intervenant
	 * @param id l'indentifiant de l'intervenant
	 * @param nom le nom de l'intervenant
	 * @param prenom le prénom de l'intervenant
	 * @param nbHeureService le nombre d'heure de service de l'intervenant
	 * @param maxHeureService le nombre d'heure maximum de l'intervenant
	 * @param coefficient le coefficient équivalent TD de l'intervenant
	 * @param contrat le contrat de l'intervenant
	 */
	private Intervenant ( int id, String nom, String prenom, double nbHeureService, double maxHeureService, double coefTP, Contrat contrat)
	{
		this.id               = id;
		this.nom              = nom;
		this.prenom           = prenom;
		this.nbHeureService   = nbHeureService;
		this.maxHeureService  = maxHeureService;
		this.coefTP           = coefTP;
		this.contrat          = contrat;
	}


	/*---------------------------------------*/
	/*                FACTORY                */
	/*---------------------------------------*/

	/** Fabrique d'intervenant à partir d'un tableau
	 * @param intervenant Prend un tableau d'objet définissant l'intervenant
	 * @return L'intervenant crée si les données du tableau sont correctes.
	 */
	public static Intervenant creation ( Object[] intervenant )
	{
		Object i  = intervenant[0];
		Object n  = intervenant[1];
		Object p  = intervenant[2];
		Object hs = intervenant[3];
		Object hm = intervenant[4];
		Object co = intervenant[5];
		Object c  = intervenant[6];

		if ( ! ( i  instanceof Integer ) ) throw new IllegalArgumentException ( "L'identifiant n'est pas du bon type"                );
		if ( ! ( n  instanceof String  ) ) throw new IllegalArgumentException ( "Le nom n'est pas du bon type"                       );
		if ( ! ( p  instanceof String  ) ) throw new IllegalArgumentException ( "Le prénom n'est pas du bon type"                    );
		if ( ! ( hs instanceof Number  ) ) throw new IllegalArgumentException ( "Le nombre d'heure de service n'est pas du bon type" );
		if ( ! ( hm instanceof Number  ) ) throw new IllegalArgumentException ( "Le nombre d'heure maximum n'est pas du bon type"    );
		if ( ! ( co instanceof Number  ) ) throw new IllegalArgumentException ( "Le coefficient n'est pas du bon type"               );
		if ( ! ( c  instanceof Contrat ) ) throw new IllegalArgumentException ( "Le contrat n'est pas du bon type"                   );
		
		int     id              = ( int ) i ;
		String  nom             = n.toString ( );
		String  prenom          = p.toString ( );
		double  nbHeureService  = Double.parseDouble ( hs.toString ( ) );
		double  maxHeureService = Double.parseDouble ( hm.toString ( ) );
		double  coefficient     = Double.parseDouble ( co.toString ( ) );
		Contrat contrat         = ( Contrat ) c ; 

		return Intervenant.creation ( id, nom, prenom, nbHeureService, maxHeureService, coefficient, contrat);
	}

	/** Fabrique d'intervenant prenant en paramètres toutes les données d'un intervenant
	 * @param id l'identifiant de l'intervenant
	 * @param nom le nom de l'intervenant
	 * @param prenom le prénom de l'intervenant
	 * @param nbHeureService le nombre d'heure de service de l'intervenant
	 * @param maxHeureService le nombre d'heure maximum de l'intervenant
	 * @param coefficient le coefficient équivalent TD de l'intervenant
	 * @param contrat le contrat de l'intervenant
	 * @return L'intervenant crée si les données ont une valeur et que le nombre d'heure de service et le nombre d'heure maximum sont positifs.
	 */
	public static Intervenant creation ( int id, String nom, String prenom, double nbHeureService, double maxHeureService, double coefficient, Contrat contrat )
	{
		// Teste que l'identifiant est correct
		if ( id < 0                            ) throw new IllegalArgumentException ( "L'identifiant est incorrecte"                           );

		// Teste que le nom est correct
		if ( nom   .equals ( "" )              ) throw new IllegalArgumentException ( "Le nom doit être reseigné"                              );

		// Teste que le prénom est correct
		if ( prenom.equals ( "" )              ) throw new IllegalArgumentException ( "Le prénom doit être reseigné"                           );

		// Teste que le nombre d'heure de service est correct
		if ( nbHeureService >  maxHeureService ) throw new IllegalArgumentException ( "Les heures de services sont supérieur à ses heures max" );

		// Teste que le nombre d'heure de service est correct
		if ( nbHeureService <  0               ) throw new IllegalArgumentException ( "Les heures de services sont négatives"                  );

		// Teste que le nombre d'heure maximum est correct
		if ( maxHeureService <  0              ) throw new IllegalArgumentException ( "Les heures maximums sont négatives"                     );

		// Teste que le coefficient est correct
		if ( coefficient  <= 0                 ) throw new IllegalArgumentException ( "Le coefficient est négatif ou nul"                      );

		return new Intervenant ( id, nom, prenom, nbHeureService, maxHeureService, coefficient, contrat );
	}


	/*---------------------------------------*/
	/*                GETTEUR                */
	/*---------------------------------------*/

	/** Retourne l'id de l'intervenant
	 * @return l'indentifiant
	 */
	public int     getId              ( ) { return this.id;              }

	/** Retourne le nom de l'intervenant
	 * @return le nom
	 */
	public String  getNom             ( ) { return this.nom;             }

	/** Retourne le prénom de l'intervenant
	 * @return le prenom
	 */
	public String  getPrenom          ( ) { return this.prenom;          }

	/** Retourne le nombre d'heure de service de l'intervenant
	 * @return le nombre d'heure de service
	 */
	public double  getNbHeureService  ( ) { return this.nbHeureService;  }
	
	/** Retourne le nombre d'heure maximum de l'intervenant
	 * @return le nombre d'heure maximum
	 */
	public double  getMaxHeureService ( ) { return this.maxHeureService; }

	/** Retourne le coefficient équivalent TD de l'intervenant
	 * @return le coefficient équivalent TD
	 */
	public double  getCoefficient     ( ) { return this.coefTP;          }
	
	/** Retourne le contrat de l'intervenant
	 * @return le contrat
	 */
	public Contrat getContrat         ( ) { return this.contrat;         }

	
	/*---------------------------------------*/
	/*                SETTEUR                */
	/*---------------------------------------*/

	/** Permet de modifier l'id
	 * @param id l'identifiant à modifier
	 */
	public void setId              ( int     id                 ) { this.id              = id;              }

	/** Permet de modifier le nom
	 * @param nom le nom à modifier
	 */
	public void setNom             ( String  nom                ) { this.nom             = nom;             }

	/** Permet de modifier le prénom
	 * @param prenom le prénom à modifier
	 */
	public void setPrenom          ( String  prenom             ) { this.prenom          = prenom;          }

	/** Permet de modifier le nombre d'heure de service
	 * @param nbHeureService le nombre d'heure de service à modifier
	 */
	public void setnbHeureService  ( double     nbHeureService  ) { this.nbHeureService  = nbHeureService;  }
	
	/** Permet de modifier le nombre d'heure maximum
	 * @param maxHeureService le nombre d'heure maximum à modifier
	 */
	public void setmaxHeureService ( double     maxHeureService ) { this.maxHeureService = maxHeureService; }

	/** Permet de modifier le coefficient équivalent TD
	 * @param coefficient le coefficient équivalent TD à modifier
	 */
	public void setCoefficient     ( double  coefficient        ) { this.coefTP          = coefficient;     }
	
	/** Permet de modifier le contrat
	 * @param contrat le contrat à modifier
	 */
	public void setContrat         ( Contrat contrat            ) { this.contrat         = contrat;         }


	/*---------------------------------------*/
	/*                METHODES               */
	/*---------------------------------------*/

	/** Indique si deux heures sont égals
	 * @return true si les deux heures sont égals, false sinon
	 */
	@Override
	public boolean equals ( Object o )
	{
		if ( ! ( o instanceof Intervenant ) ) return false;
		if ( o == this                      ) return true;

		Intervenant c = ( Intervenant ) o;

		return this.id           == c.id                    &&
		       this.nom          .equals ( c.nom          ) &&
		       this.prenom       .equals ( c.prenom       ) &&
		       this.nbHeureService   == c.nbHeureService    &&
		       this.maxHeureService  == c.maxHeureService   &&
		       this.coefTP == c.coefTP                      &&
		       this.contrat      .equals ( c.contrat      );

	}

	/** Renvoie la description et le contenu d'une horaire
	 * @return Une description du contenue d'une horaire
	 */
	@Override
	public String toString ( )
	{
		return String.format ( "Intervenant%nId              : %d%nNom             : %s%nPrénom          : %s%nHeure de service : %f%nHeure maximum    : %f%nCoefficient      : %f%nContrat          : %s", this.id, this.nom, this.prenom, this.nbHeureService, this.maxHeureService, this.coefTP, this.contrat );
	}
}
