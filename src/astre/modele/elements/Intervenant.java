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
	private int     heureService;
	private int     heureMaximum;
	private Contrat contrat;

	/** Constructeur d'intervenant
	 * @param id l'indentifiant de l'intervenant
	 * @param nom le nom de l'intervenant
	 * @param prenom le prénom de l'intervenant
	 * @param contrat le contrat de l'intervenant
	 * @param heureService le nombre d'heure de service de l'intervenant
	 * @param heureMaximum le nombre d'heure maximum de l'intervenant
	 */
	private Intervenant ( int id, String nom, String prenom,int heureService, int heureMaximum, Contrat contrat)
	{
		this.id           = id;
		this.nom          = nom;
		this.prenom       = prenom;
		this.heureService = heureService;
		this.heureMaximum = heureMaximum;
		this.contrat      = contrat;
	}


	/*---------------------------------------*/
	/*                FACTORY                */
	/*---------------------------------------*/

	public static Intervenant creation ( Object[] intervenant )
	{
		Object i  = intervenant[0];
		Object n  = intervenant[1];
		Object p  = intervenant[2];
		Object hs = intervenant[3];
		Object hm = intervenant[4];
		Object c  = intervenant[5];

		if ( ! ( i  instanceof Integer ) ) throw new IllegalArgumentException ( "L'identifiant n'est pas du bon type"                );
		if ( ! ( n  instanceof String  ) ) throw new IllegalArgumentException ( "Le nom n'est pas du bon type"                       );
		if ( ! ( p  instanceof String  ) ) throw new IllegalArgumentException ( "Le prénom n'est pas du bon type"                    );
		if ( ! ( hs instanceof Integer ) ) throw new IllegalArgumentException ( "Le nombre d'heure de service n'est pas du bon type" );
		if ( ! ( hm instanceof Integer ) ) throw new IllegalArgumentException ( "Le nombre d'heure maximum n'est pas du bon type"    );
		if ( ! ( c  instanceof Contrat ) ) throw new IllegalArgumentException ( "Le contrat n'est pas du bon type"                   );
		
		int    id           = ( int ) i ;
		String nom          = n.toString ( );
		String prenom       = p.toString ( );
		int    heureService = ( int ) hs;
		int    heureMaximum = ( int ) hm;
		Contrat contrat     = ( Contrat ) c ; 

		return Intervenant.creation ( id, nom, prenom, heureService, heureMaximum, contrat);
	}

	public static Intervenant creation ( int id, String nom, String prenom, int heureService, int heureMaximum, Contrat contrat)
	{
		if ( nom   .equals ( "" )        ) throw new IllegalArgumentException ( "Le nom doit être reseigné"                              );
		if ( prenom.equals ( "" )        ) throw new IllegalArgumentException ( "Le prénom doit être reseigné"                           );
		if ( heureService > heureMaximum ) throw new IllegalArgumentException ( "Les heures de services sont supérieur à ses heures max" );
		if ( heureService < 0            ) throw new IllegalArgumentException ( "Les heures de services sont négatives"                  );
		if ( heureMaximum < 0            ) throw new IllegalArgumentException ( "Les heures maximums sont négatives"                     );

		return new Intervenant ( id, nom, prenom, heureService, heureMaximum, contrat );
	}


	/*---------------------------------------*/
	/*                GETTEUR                */
	/*---------------------------------------*/

	/** Retourne l'id de l'intervenant
	 * @return l'indentifiant
	 */
	public int     getId           ( ) { return this.id;           }

	/** Retourne le nom de l'intervenant
	 * @return le nom
	 */
	public String  getNom          ( ) { return this.nom;          }

	/** Retourne le prénom de l'intervenant
	 * @return le prenom
	 */
	public String  getPrenom       ( ) { return this.prenom;       }

	/** Retourne le contrat de l'intervenant
	 * @return le contrat
	 */
	public Contrat getContrat      ( ) { return this.contrat;      }

	/** Retourne le nombre d'heure de service de l'intervenant
	 * @return le nombre d'heure de service
	 */
	public int     getheureService ( ) { return this.heureService; }

	/** Retourne le nombre d'heure maximum de l'intervenant
	 * @return le nombre d'heure maximum
	 */
	public int     getHeureMaximum ( ) { return this.heureMaximum; }


	/*---------------------------------------*/
	/*                SETTEUR                */
	/*---------------------------------------*/

	/** Permet de modifier l'id
	 * @param id l'identifiant à modifier
	 */
	public void setId           ( int     id           ) { this.id           = id;           }

	/** Permet de modifier le nom
	 * @param nom le nom à modifier
	 */
	public void setNom          ( String  nom          ) { this.nom          = nom;          }

	/** Permet de modifier le prénom
	 * @param prenom le prénom à modifier
	 */
	public void setPrenom       ( String  prenom       ) { this.prenom       = prenom;       }

	/** Permet de modifier le nombre d'heure de service
	 * @param heureService le nombre d'heure de service à modifier
	 */
	public void setheureService ( int     heureService ) { this.heureService = heureService; }
	
	/** Permet de modifier le nombre d'heure maximum
	 * @param heureMaximum le nombre d'heure maximum à modifier
	 */
	public void setHeureMaximum ( int     heureMaximum ) { this.heureMaximum = heureMaximum; }
	
	/** Permet de modifier le contrat
	 * @param contrat le contrat à modifier
	 */
	public void setContrat      ( Contrat contrat      ) { this.contrat      = contrat;      }


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
		       this.heureService == c.heureService          &&
		       this.heureMaximum == c.heureMaximum          &&
		       this.contrat      .equals ( c.contrat      );

	}

	/** Renvoie la description et le contenu d'une horaire
	 * @return Une description du contenue d'une horaire
	 */
	@Override
	public String toString ( )
	{
		return String.format ( "Intervenant%nId              : %d%nNom             : %s%nPrénom          : %s%nHeure de service : %d%nHeure maximum    : %d%nContrat          : %s", this.id, this.nom, this.prenom, this.heureService, this.heureMaximum, this.contrat );
	}
	
}
