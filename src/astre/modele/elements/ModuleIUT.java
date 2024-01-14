package astre.modele.elements;

/** Classe ModuleIUT
  * @author : Maximilien Lesterlin
  * @version : 2.0 - 14/01/2024
  * @date : 06/12/2023
  */

public class ModuleIUT
{
	private String   code;
	private String   libLong;
	private String   libCourt;
	private String   typeModule;
	private boolean  valide;
	private Semestre semestre;
	private double   totalHeurePN;
	private double   totalHeureAffectee;

	/** Constructeur d'un module
	 * @param code le code du module
	 * @param libLong le libellé long du module
	 * @param libCourt le libellé court du module
	 * @param typeModule le type de module
	 * @param valide la validité du module
	 * @param semestre le semestre du module
	 * @param totalHeurePN le total d'heure PN du module
	 * @param totalHeureAffectee le total d'heure affectée du module
	 */
	private ModuleIUT ( String code, String libLong, String libCourt, String typeModule, boolean valide, Semestre semestre, double totalHeurePN, double totalHeureAffectee )
	{
		this.code                = code;
		this.libLong             = libLong;
		this.libCourt            = libCourt;
		this.typeModule          = typeModule;
		this.valide              = valide;
		this.semestre            = semestre;
		this.totalHeurePN        = totalHeurePN;
		this.totalHeureAffectee  = totalHeureAffectee;
	}


	/*---------------------------------------*/
	/*                FACTORY                */
	/*---------------------------------------*/

	/** Fabrique de module à partir d'un tableau
	 * @param moduleIUT Prend un tableau d'objet définissant le module
	 * @return Le module crée si les données du tableau sont correctes.
	 */
	public static ModuleIUT creation ( Object[] moduleIUT )
	{
		Object c    = moduleIUT[0];
		Object ll   = moduleIUT[1];
		Object lc   = moduleIUT[2];
		Object tm   = moduleIUT[3];
		Object v    = moduleIUT[4];
		Object s    = moduleIUT[5];
		Object thPN = moduleIUT[6];
		Object thA  = moduleIUT[7];

		if ( ! ( c    instanceof String   ) ) throw new IllegalArgumentException ( "Le code n'est pas du bon type"                   );
		if ( ! ( ll   instanceof String   ) ) throw new IllegalArgumentException ( "Le libellé long n'est pas du bon type"           );
		if ( ! ( lc   instanceof String   ) ) throw new IllegalArgumentException ( "Le libellé court n'est pas du bon type"          );
		if ( ! ( tm   instanceof String   ) ) throw new IllegalArgumentException ( "Le type de module n'est pas du bon type"         );
		if ( ! ( v    instanceof Boolean  ) ) throw new IllegalArgumentException ( "La validité n'est pas du bon type"               );
		if ( ! ( s    instanceof Semestre ) ) throw new IllegalArgumentException ( "Le semestre n'est pas du bon type"               );
		if ( ! ( thPN instanceof Number   ) ) throw new IllegalArgumentException ( "Le total d'heure PN n'est pas du bon type"       );
		if ( ! ( thA  instanceof Number   ) ) throw new IllegalArgumentException ( "Le total d'heure affectée n'est pas du bon type" );
		
		String   code              = c .toString ( );
		String   libLong           = ll.toString ( );
		String   libCourt          = lc.toString ( );
		String   typeModule        = tm.toString ( );
		boolean  valide            = ( Boolean  ) v;
		Semestre semestre          = ( Semestre ) s;
		int      totalHeurePN      = ( int ) thPN;
		int      totalHeureAffecter= ( int ) thA;

		return ModuleIUT.creation ( code, libLong, libCourt, typeModule, valide, semestre, totalHeurePN, totalHeureAffecter );
	}

	/** Fabrique de module à partir d'un tableau
	 * @param code le code du module
	 * @param libLong le libellé long du module
	 * @param libCourt le libellé court du module
	 * @param typeModule le type de module
	 * @param valide la validité du module
	 * @param semestre le semestre du module
	 * @param totalHeurePN le total d'heure PN du module
	 * @param totalHeureAffectee le total d'heure affectée du module
	 * @return Le module crée si les données du tableau sont correctes.
	 */
	public static ModuleIUT creation ( String code, String libLong, String libCourt, String typeModule, boolean valide, Semestre semestre, double totalHeurePN, double totalHeureAffectee )
	{
		// Teste que le code est correct
		if ( code      .equals ( "" )   ) throw new IllegalArgumentException ( "Le code du module n'est pas rempli"          );

		// Teste que les libellés sont corrects
		if ( libLong   .equals ( "" )   ) throw new IllegalArgumentException ( "Le libellé long du module n'est pas rempli"  );

		// Teste que les libellés sont corrects
		if ( libCourt  .equals ( "" )   ) throw new IllegalArgumentException ( "Le libellé court du module n'est pas rempli" );

		// Teste que le type de module est correct
		if ( typeModule.equals ( "" )   ) throw new IllegalArgumentException ( "Le typeModule n'est pas rempli"              );

		// Teste que la validité est correcte
		if ( ( Boolean ) valide == null ) throw new IllegalArgumentException ( "Pas d'indication pour la validation"         );

		// Teste que le semestre est correct
		if ( semestre  == null          ) throw new IllegalArgumentException ( "Pas de semestre"                             );

		// Teste que le total d'heure affectée est correct
		if ( totalHeureAffectee < 0     ) throw new IllegalArgumentException ( "Le total d'heure affectée est négatif"       );

		// Teste que le total d'heure PN est correct
		if ( totalHeurePN       < 0     ) throw new IllegalArgumentException ( "Le total d'heure PN est négatif"             );
		
		return new ModuleIUT ( code, libLong, libCourt, typeModule, valide, semestre, totalHeurePN, totalHeureAffectee );
	}


	/*---------------------------------------*/
	/*                GETTEUR                */
	/*---------------------------------------*/

	/** Retourne le code d'un module
	 * @return le code
	 */
	public String              getCode               ( ) { return this.code;               }

	/** Retourne le libellé long d'un module
	 * @return le libLong
	 */
	public String              getLibLong            ( ) { return this.libLong;            }

	/** Retourne le libellé court d'un module
	 * @return le libCourt
	 */
	public String              getLibCourt           ( ) { return this.libCourt;           }

	/** Retourne le type d'un module
	 * @return le typeModule
	 */
	public String              getTypeModule         ( ) { return this.typeModule;         }

	/** Retourne la validité d'un module
	 * @return la validité du module
	 */
	public boolean             estValide             ( ) { return this.valide;            }

	/** Retourne le semestre d'un module
	 * @return le semestre
	 */
	public Semestre            getSemestre           ( ) { return this.semestre;          }

	/** Retourne le total d'heure PN d'un module
	 * @return le totalHeurePN
	 */
	public double              getTotalHeurePN       ( ) { return this.totalHeurePN;       }

	/** Retourne le total d'heure affectée d'un module
	 * @return le totalHeureAffecter
	 */
	public double              getTotalHeureAffectee ( ) { return this.totalHeureAffectee; }


	/*---------------------------------------*/
	/*                SETTEUR                */
	/*---------------------------------------*/

	/** Permet de modifier le code d'un module
	 * @param code le code à modifier
	 */
	public void setCode       ( String     code       ) { this.code       = code;       }

	/** Permet de modifier le libellé long d'un module
	 * @param libLong le libellé long à modifier
	 */
	public void setLibLong    ( String     libLong    ) { this.libLong    = libLong;    }

	/** Permet de modifier le libellé court d'un module
	 * @param libCourt le libellé court à modifier
	 */
	public void setLibCourt   ( String     libCourt   ) { this.libCourt   = libCourt;   }

	/** Permet de modifier le type de module
	 * @param typeModule le type de module à modifier
	 */
	public void setTypeModule ( String     typeModule ) { this.typeModule = typeModule; }

	/** Permet de modifier la validité d'un module
	 * @param choix le choix à modifier
	 */
	public void setValide     ( boolean    choix      ) { this.valide     = choix;      }

	/** Permet de modifier le semestre
	 * @param semestre le semestre à modifier
	 */
	public void setSemestre   ( Semestre   semestre   ) { this.semestre   = semestre;   }
	
	/** Permet de modifier le total d'heure PN
	 * @param totalHeurePN le totalHeurePN à modifier
	 */
	public void setTotalHeurePN       ( double totalHeurePN       ) { this.totalHeurePN       = totalHeurePN;       }

	/** Permet de modifier le total d'heure affectée
	 * @param totalHeureAffecter le totalHeureAffecter à modifier
	 */
	public void setTotalHeureAffectee ( double totalHeureAffecter ) { this.totalHeureAffectee = totalHeureAffecter; }


	/*---------------------------------------*/
	/*                METHODE                */
	/*---------------------------------------*/

	/** Indique si deux ModuleIUT sont égaux
	 * @return true si les deux ModuleIUT sont égaux, false sinon
	 */
	@Override
	public boolean equals ( Object o )
	{
		if ( ! ( o instanceof ModuleIUT ) ) return false;
		if ( o == this                    ) return true;

		ModuleIUT c = ( ModuleIUT ) o;

		return this.code              .equals ( c.code              ) &&
		       this.libLong           .equals ( c.libLong           ) &&
		       this.libCourt          .equals ( c.libCourt          ) &&
		       this.typeModule        .equals ( c.typeModule        ) &&
		       this.valide             ==     c.valide                &&
		       this.semestre          .equals ( c.semestre          ) &&
		       this.totalHeurePN       ==     c.totalHeurePN          &&
		       this.totalHeureAffectee ==     c.totalHeureAffectee;
	}

	/** Renvoie la description et le contenu d'un ModuleIUT
	 * @return Une description du contenue d'un ModuleIUT
	 */
	@Override
	public String toString ( )
	{
		return String.format ( "ModuleIUT%nCode               : %s%nLibellé long       : %s%nLibellé court      : %s%nType de module     : %s%nValidité           : %b%nSemestre           : %s%nTotal d'heure PN   : %f%nTotal d'heure affectée : %f", this.code, this.libLong, this.libCourt, this.typeModule, this.valide, this.semestre, this.totalHeurePN, this.totalHeureAffectee );
	}
}