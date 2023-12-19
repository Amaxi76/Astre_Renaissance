package astre.modele.elements;

/** Classe Module 
  * @author : Maximilien Lesterlin, Alizéa Lebaron
  * @version : 1.1.0 - 13/12/2023
  * @date : 06/12/2023
  */

import java.util.Map;

public class ModuleIUT
{
	Map<Heure, Integer> hmHeuresPn;
	Map<Heure, Integer> hmHeuresRepaties;
	
	Semestre semestre;
	String   typeModule;
	String   code;
	String   libLong;
	String   libCourt;
	boolean  valide;

	/**
	 * Constructeur de ModuleIUT
	 * @param semestre
	 * @param typeModule
	 * @param code
	 * @param libLong
	 * @param libCourt
	 * @param valide
	 * @param hmHeuresPn
	 * @param hmHeuresRepaties
	 */
	public ModuleIUT ( Semestre semestre, String typeModule, String code, String libLong, String libCourt, boolean valide, Map<Heure, Integer> hmHeuresPn, Map<Heure, Integer> hmHeuresRepaties )
	{
		this.semestre          = semestre;
		this.typeModule        = typeModule;
		this.code              = code;
		this.libLong           = libLong;
		this.libCourt          = libCourt;
		this.valide            = valide;
		this.hmHeuresPn        = hmHeuresPn;
		this.hmHeuresRepaties  = hmHeuresRepaties;
	}

	/**
	 * Constructeur de ModuleIUT
	 * @param semestre
	 * @param typeModule
	 * @param code
	 * @param libLong
	 * @param libCourt
	 * @param valide
	 */
	public ModuleIUT ( Semestre semestre, String typeModule, String code, String libLong, String libCourt, boolean valide)
	{
		this.semestre          = semestre;
		this.typeModule        = typeModule;
		this.code              = code;
		this.libLong           = libLong;
		this.libCourt          = libCourt;
		this.valide            = valide;
		this.hmHeuresPn        = null;
		this.hmHeuresRepaties  = null;
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

	/** Retourne la liste des heures réparties
	 * @return hmHeuresReparties
	 */
	public Map<Heure, Integer> getHmHeureReparties   ( ) { return this.hmHeuresRepaties;  }

	/** Retourne la liste des heures du programme national
	 * @return hmHeuresPn
	 */
	public Map<Heure, Integer> getHmHeurePn          ( ) { return this.hmHeuresPn;        }

	/** Retourne le nombre d'heure réparties
	 * @return somme
	 */
	public int getHeureReparties ( )
	{
		int somme = 0;
		
		for ( Heure h : this.hmHeuresRepaties.keySet ( ) )
			somme += this.hmHeuresRepaties.get ( h );

		return somme;
	}

	/** Retourne le nombre d'heure du programme national
	 * @return somme
	 */
	public int getHeurePn ( )
	{
		int somme = 0;
		
		for ( Heure h : this.hmHeuresPn.keySet ( ) )
			somme += this.hmHeuresPn.get ( h );

		return somme;
	}


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
		return "ModuleIUT [hmHeuresPn=" + hmHeuresPn + ", hmHeuresRepaties=" + hmHeuresRepaties + ", semestre="
				+ semestre + ", typeModule=" + typeModule + ", code=" + code + ", libLong=" + libLong + ", libCourt="
				+ libCourt + ", valide=" + valide + "]";
	}
}