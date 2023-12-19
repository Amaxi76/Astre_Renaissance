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

		if ( ( s instanceof Semestre )  ||  !( tm instanceof String ) || !( c instanceof String ) || !( ll instanceof String ) ||
		                                    !( ll instanceof String ) || !( c instanceof Boolean ) )
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

	public boolean             estValide             ( ) { return this.valide;            }
	public Semestre            getSemestre           ( ) { return this.semestre;          }
	public String              getTypeModule         ( ) { return this.typeModule;        }
	public String              getCode               ( ) { return this.code;              }
	public String              getLibLong            ( ) { return this.libLong;           }
	public String              getLibCourt           ( ) { return this.libCourt;          }

	/*---------------------------------------*/
	/*                SETTEUR                */
	/*---------------------------------------*/

	public void setSemestre   ( Semestre   semestre   ) { this.semestre   = semestre;   }
	public void setTypeModule ( String     typeModule ) { this.typeModule = typeModule; }
	public void setCode       ( String     code       ) { this.code       = code;       }
	public void setLibLong    ( String     libLong    ) { this.libLong    = libLong;    }
	public void setLibCourt   ( String     libCourt   ) { this.libCourt   = libCourt;   }
	public void setValide     ( boolean    choix      ) { this.valide     = choix;      }

	@Override
	public String toString ( )
	{
		return "ModuleIUT [semestre=" + semestre + ", typeModule=" + typeModule + ", code=" + code + ", libLong="
				+ libLong + ", libCourt=" + libCourt + ", valide=" + valide + "]";
	}

	
}
