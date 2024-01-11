package astre.modele.elements;

import javax.swing.JOptionPane;

/** Classe Intervenant 
  * @author : Maximilien Lesterlin, Alizéa Lebaron
  * @version : 1.0.1 - 12/12/2023
  * @date : 06/12/2023
  */

//FIXME: Faire correctement les choses du premier coup (quand on lève les exceptions, on peut récupérer les messages pour factoriser le code)

public class Intervenant
{
	private int 	id;
	private String  nom;
	private String  prenom;
	private int     heureService;
	private int     heureMaximum;
	private Contrat contrat;

	/** Constructeur d'intervenant
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

		if ( ( i != null && ! ( i instanceof Integer ) ) ||  ! ( n instanceof String ) || ! ( p instanceof String ) || ! ( c instanceof Contrat ) || ! ( hs instanceof Integer ) || ! ( hm instanceof Integer ))
		{
			JOptionPane.showMessageDialog ( null, "Les données de l'invervenant intervenant ne sont pas du bon type.", "Création Impossible", JOptionPane.ERROR_MESSAGE );
			throw new IllegalArgumentException ( "Les données de l'invervenant intervenant ne sont pas du bon type" );
		}
		
		int    id           = ( i == null ) ? 0 : Integer.parseInt ( i.toString ( ) );
		int    heureService = Integer.parseInt ( hs.toString ( ) );
		int    heureMaximum = Integer.parseInt ( hm.toString ( ) );
		String nom          = n.toString ( );
		String prenom       = p.toString ( );
		Contrat contrat     = ( Contrat ) c ; 

		return Intervenant.creation ( id, nom, prenom, contrat, heureService, heureMaximum );
	}

	public static Intervenant creation ( int id, String nom, String prenom, Contrat contrat, int heureService, int heureMaximum )
	{
		if ( nom.equals ( "" ) || prenom.equals ( "" ) )
		{
			JOptionPane.showMessageDialog ( null, "Veuillez renseigner le nom et le prenom.", "Création Impossible", JOptionPane.ERROR_MESSAGE );
			throw new IllegalArgumentException ( "Veuillez renseigner le nom et le prenom" );
		}

		// Il n'a pas de contrat
		if ( contrat == null )
		{
			JOptionPane.showMessageDialog ( null, "Veuillez renseigner un contrat pour l'intervenant.", "Création Impossible", JOptionPane.ERROR_MESSAGE );
			throw new IllegalArgumentException ( "Veuillez renseigner un contrat pour l'intervenant" );
		}

		//hserv > hmax
		if ( heureService > heureMaximum )
		{
			JOptionPane.showMessageDialog ( null, "Les heures de services sont supérieur à ses heures max.", "Création Impossible", JOptionPane.ERROR_MESSAGE );
			throw new IllegalArgumentException ( "Les heures de services sont supérieur à ses heures max" );
		}

		//hserv < 0 ou hmax < 0
		if ( heureService < 0 || heureMaximum < 0 )
		{
			JOptionPane.showMessageDialog ( null, "Les heures de services ou maximums sont nuls ou négatives.", "Création Impossible", JOptionPane.ERROR_MESSAGE );
			throw new IllegalArgumentException ( "Les heures de services ou maximums sont ou négatives" );
		}
		
		return new Intervenant ( id, nom, prenom, contrat, heureService, heureMaximum );
	}

	/*---------------------------------------*/
	/*                GETTEUR                */
	/*---------------------------------------*/

	/** Retourne l'id de l'intervenant
	 * @return id
	 */
	public int     getId           ( ) { return this.id;           }

	/** Retourne le nom de l'intervenant
	 * @return nom
	 */
	public String  getNom          ( ) { return this.nom;          }

	/** Retourne le prénom de l'intervenant
	 * @return prenom
	 */
	public String  getPrenom       ( ) { return this.prenom;       }

	/** Retourne le contrat de l'intervenant
	 * @return contrat
	 */
	public Contrat getContrat      ( ) { return this.contrat;      }

	/** Retourne le nombre d'heure de service de l'intervenant
	 * @return heureService
	 */
	public int     getheureService ( ) { return this.heureService; }

	/** Retourne le nombre d'heure maximum de l'intervenant
	 * @return heureMaximum
	 */
	public int     getHeureMaximum ( ) { return this.heureMaximum; }

	/*---------------------------------------*/
	/*                SETTEUR                */
	/*---------------------------------------*/

	/** Permet de modifier l'id
	 * @param id the id to set
	 */
	public void setId           ( int     id           ) { this.id           = id;           }

	/** Permet de modifier le nom
	 * @param nom the nom to set
	 */
	public void setNom          ( String  nom          ) { this.nom          = nom;          }

	/** Permet de modifier le prénom
	 * @param prenom the prenom to set
	 */
	public void setPrenom       ( String  prenom       ) { this.prenom       = prenom;       }

	/** Permet de modifier le contrat
	 * @param contrat the contrat to set
	 */
	public void setContrat      ( Contrat contrat      ) { this.contrat      = contrat;      }

	/** Permet de modifier le nombre d'heure de service
	 * @param heureService the heureService to set
	 */
	public void setheureService ( int     heureService ) { this.heureService = heureService; }

	/** Permet de modifier le nombre d'heure maximum
	 * @param heureMaximum the heureMaximum to set
	 */
	public void setHeureMaximum ( int     heureMaximum ) { this.heureMaximum = heureMaximum; }

	/*---------------------------------------*/
	/*                METHODES               */
	/*---------------------------------------*/

	/** Renvoie le descriptif des attributs d'intervenants
	 * @return descriptif des attributs d'intervenants
	 */
	public String toString ( )
	{
		//return this.nom + " " + this.prenom; //ce toString est utilisé pour l'affichage dans la liste des intervenants des combobox
		
		String sRet = "";

		sRet = String.format ( "Nom               : %20s - ",  this.nom          ) +
			   String.format ( "Prénom            : %20s - ",  this.prenom       ) +
			   String.format ( "heureService      : %3d  - ",  this.heureService ) +
			   String.format ( "Heure Max         : %3d  - ",  this.heureMaximum ) ;

		return sRet;
	}

	@Override
	/** Compare l'égalité entre l'intervenant passé en paramètre et l'invernant passé par le constructeur
	 * @return true si les deux intervenants sont égaux, sinon false
	 */
	public boolean equals ( Object o )
	{
		if ( o == null ) return false;
		if ( o == this ) return true;
		
		if ( o instanceof Intervenant )
		{
			Intervenant i = ( Intervenant ) o;

			if ( this.id == i.getId ( ) && this.nom.equals ( i.getNom ( ) ) && this.prenom.equals ( i.getPrenom ( ) ) && this.heureService == i.getheureService ( ) && this.heureMaximum == i.getHeureMaximum ( ) )
				return true;
		}
		
		return false;
	}
	
}
