package astre.modele.elements;

/** Classe Intervenant 
  * @author : Maximilien Lesterlin, Alizéa Lebaron
  * @version : 1.0.1 - 12/12/2023
  * @date : 06/12/2023
  */

public class Intervenant
{
	private int 	id;
	private String  nom;
	private String  prenom;
	private int     heureService;
	private int     heureMaximum;
	private Contrat contrat;

	/**
	 * @param id
	 * @param nom
	 * @param prenom
	 * @param contrat
	 * @param heureService
	 * @param heureMaximum
	 */
	private Intervenant ( int id, String nom, String prenom, Contrat contrat, int heureService, int heureMaximum )
	{
		this.id           = id;
		this.nom          = nom;
		this.prenom       = prenom;
		this.contrat      = contrat;
		this.heureService = heureService;
		this.heureMaximum = heureMaximum;
	}

	public static Intervenant creation ( Object[] intervenant )
	{
		Object i  = intervenant[0];
		Object n  = intervenant[1];
		Object p  = intervenant[2];
		Object c  = intervenant[3];
		Object hs = intervenant[4];
		Object hm = intervenant[5];

		if ( ( i != null && !( i instanceof Integer ) ) ||  !( n instanceof String ) || !( p instanceof String ) || !( c instanceof Contrat ) || !( hs instanceof Integer ) || !( hm instanceof Integer ))
			throw new IllegalArgumentException ( "Les données de l'invervenant intervenant ne sont pas du bon type" );
		
		int    id           = ( i == null ) ? 0 : Integer.parseInt ( i.toString ( ) );
		int    heureService = Integer.parseInt ( hs.toString ( ) );
		int    heureMaximum = Integer.parseInt ( hm.toString ( ) );
		String nom          = n.toString ( );
		String prenom       = p.toString ( );
		Contrat contrat     = (Contrat)c;

		return Intervenant.creation ( id, nom, prenom, contrat, heureService, heureMaximum );
	}

	public static Intervenant creation ( int id, String nom, String prenom, Contrat contrat, int heureService, int heureMaximum )
	{
		if ( nom.equals ( "" ) || prenom.equals ( "" ) )
			throw new IllegalArgumentException ( "Veuillez renseigner le nom et le prenom" );

		// Il n'a pas de contrat
		if ( contrat == null )
			throw new IllegalArgumentException ( "Veuillez renseigner un contrat pour l'intervenant" );

		//hserv > hmax
		if ( heureService > heureMaximum )
			throw new IllegalArgumentException ( "Les heures de services sont supérieur à ses heures max" );

		//hserv < 0 ou hmax < 0
		if ( heureService < 0 || heureMaximum < 0 )
			throw new IllegalArgumentException ( "Les heures de services ou maximums sont nuls" );
		
		return new Intervenant ( id, nom, prenom, contrat, heureService, heureMaximum );
	}

	/*---------------------------------------*/
	/*                GETTEUR                */
	/*---------------------------------------*/

	/**
	 * @return
	 */
	public int     getId           ( ) { return this.id;           }
	/**
	 * @return
	 */
	public String  getNom          ( ) { return this.nom;          }
	/**
	 * @return
	 */
	public String  getPrenom       ( ) { return this.prenom;       }
	/**
	 * @return
	 */
	public Contrat getContrat      ( ) { return this.contrat;      }
	/**
	 * @return
	 */
	public int     getheureService ( ) { return this.heureService; }
	/**
	 * @return
	 */
	public int     getHeureMaximum ( ) { return this.heureMaximum; }

	/*---------------------------------------*/
	/*                SETTEUR                */
	/*---------------------------------------*/

	/**
	 * @param id
	 */
	public void setId           ( int     id           ) { this.id           = id;           }
	/**
	 * @param nom
	 */
	public void setNom          ( String  nom          ) { this.nom          = nom;          }
	/**
	 * @param prenom
	 */
	public void setPrenom       ( String  prenom       ) { this.prenom       = prenom;       }
	/**
	 * @param contrat
	 */
	public void setContrat      ( Contrat contrat      ) { this.contrat      = contrat;      }
	/**
	 * @param heureService
	 */
	public void setheureService ( int     heureService ) { this.heureService = heureService; }
	/**
	 * @param heureMaximum
	 */
	public void setHeureMaximum ( int     heureMaximum ) { this.heureMaximum = heureMaximum; }

	/*---------------------------------------*/
	/*                METHODES               */
	/*---------------------------------------*/

	/**
	 * @return descriptif des attributs d'intervenants
	 */
	public String toString ( )
	{
		String sRet = "";

		sRet = String.format ( "Nom               : %20s - ",  this.nom          ) +
			   String.format ( "Prénom            : %20s - ",  this.prenom       ) +
			   String.format ( "heureService      : %3d  - ",  this.heureService ) +
			   String.format ( "Heure Max         : %3d  - ",  this.heureMaximum ) ;

		return sRet;
	}

	@Override
	public boolean equals ( Object o )
	{
		if ( o instanceof Intervenant )
		{
			Intervenant i = ( Intervenant ) o;

			if ( this.id == i.getId ( ) && this.nom.equals ( i.getNom ( ) ) && this.prenom.equals ( i.getPrenom ( ) ) && this.heureService == i.getheureService ( ) && this.heureMaximum == i.getHeureMaximum ( ) )
				return true;
		}
		
		return false;
	}
	
}
