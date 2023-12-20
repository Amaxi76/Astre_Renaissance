package astre.modele.elements;

/** Classe Module 
  * @author : Maximilien Lesterlin
  * @version : 1.1.0 - 13/12/2023
  * @date : 06/12/2023
  */

public class ModuleIUT
{
	Semestre semestre;
	String   typeModule;
	String   code;
	String   libLong;
	String   libCourt;
	boolean  valide;

	private ModuleIUT ( Semestre semestre, String typeModule, String code, String libLong, String libCourt, boolean valide )
	{
		this.semestre          = semestre;
		this.typeModule        = typeModule;
		this.code              = code;
		this.libLong           = libLong;
		this.libCourt          = libCourt;
		this.valide            = valide;
	}

	public static ModuleIUT creation ( Object[] contrat )
	{
		Object s  = contrat[0];
		Object tm = contrat[1];
		Object c  = contrat[2];
		Object ll = contrat[3];
		Object lc = contrat[4];
		Object v  = contrat[5];

		if ( ( s instanceof Semestre )  ||  ! ( tm instanceof String ) || ! ( c instanceof String ) || ! ( ll instanceof String ) ||
		                                    ! ( ll instanceof String ) || ! ( c instanceof Boolean ) )
			throw new IllegalArgumentException ( "Les données du moduleIUT ne sont pas du bon type" );
		
		Semestre semestre          = ( Semestre ) s;
		String   typeModule        = tm.toString ( );
		String   code              = c .toString ( );
		String   libLong           = ll.toString ( );
		String   libCourt          = lc.toString ( );
		boolean  valide            = ( Boolean ) v;

		return ModuleIUT.creation ( semestre, typeModule, code, libLong, libCourt, valide );
	}

	public static ModuleIUT creation ( Semestre semestre, String typeModule, String code, String libLong, String libCourt, boolean valide )
	{
		if ( typeModule.equals ( "" ) )
			throw new IllegalArgumentException ( "Le typeModule n'est pas rempli" );

		if ( code.equals ( "" ) )
			throw new IllegalArgumentException ( "Le code n'est pas rempli" );

		if ( libLong.equals ( "" ) )
			throw new IllegalArgumentException ( "Le libellé long n'est pas rempli" );
		
		if ( libCourt.equals ( "" ) )
			throw new IllegalArgumentException ( "Le libellé court n'est pas rempli" );

		// Coef TD
		if ( ( Boolean ) valide == null )
			throw new IllegalArgumentException ( "Pas d'indication pour la validation" );
		
		return new ModuleIUT ( semestre, typeModule, code, libLong, libCourt, valide );
	}

	/*---------------------------------------*/
	/*                GETTEUR                */
	/*---------------------------------------*/

	/** Retourne la validité d'un module
	 * @return valide
	 */
	public boolean             estValide             ( ) { return this.valide;            }

	/** Retourne le semestre d'un module
	 * @return semestre
	 */
	public Semestre            getSemestre           ( ) { return this.semestre;          }

	/** Retourne le type d'un module
	 * @return typeModule
	 */
	public String              getTypeModule         ( ) { return this.typeModule;        }

	/** Retourne le code d'un module
	 * @return code
	 */
	public String              getCode               ( ) { return this.code;              }

	/** Retourne le libellé long d'un module
	 * @return libLong
	 */
	public String              getLibLong            ( ) { return this.libLong;           }

	/** Retourne le libellé court d'un module
	 * @return libCourt
	 */
	public String              getLibCourt           ( ) { return this.libCourt;          }

	/*---------------------------------------*/
	/*                SETTEUR                */
	/*---------------------------------------*/

	/** Permet de modifier le semestre
	 * @param semestre the semestre to set
	 */
	public void setSemestre   ( Semestre   semestre   ) { this.semestre   = semestre;   }

	/** Permet de modifier le type de module
	 * @param typeModule the typeModule to set
	 */
	public void setTypeModule ( String     typeModule ) { this.typeModule = typeModule; }

	/** Permet de modifier le code d'un module
	 * @param code the code to set
	 */
	public void setCode       ( String     code       ) { this.code       = code;       }

	/** Permet de modifier le libellé long d'un module
	 * @param libLong the libLong to set
	 */
	public void setLibLong    ( String     libLong    ) { this.libLong    = libLong;    }

	/** Permet de modifier le libellé court d'un module
	 * @param libCourt the libCourt to set
	 */
	public void setLibCourt   ( String     libCourt   ) { this.libCourt   = libCourt;   }

	/** Permet de modifier la validité d'un module
	 * @param choix the choix to set
	 */
	public void setValide     ( boolean    choix      ) { this.valide     = choix;      }


	/** Renvoie le descriptif des attributs d'un module
	 * @return descriptif des attributs d'un module
	 */
	@Override
	public String toString ( )
	{
		return "ModuleIUT [semestre=" + this.semestre + ", typeModule=" + this.typeModule + ", code=" + this.code + ", libLong="
				+ this.libLong + ", libCourt=" + this.libCourt + ", valide=" + valide + "]";
	}
}