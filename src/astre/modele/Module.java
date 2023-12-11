package astre.modele;

/** Classe Module 
  * @author : Maximilien Lesterlin
  * @version : 1.0 - 11/12/2023
  * @date : 06/12/2023
  */

import java.util.HashMap;

public class Module
{
	HashMap<Heure, Integer> hsHeure;
	
	Semestre semestre;
	String   code;
	String   libLong;
	String   libCourt;

	public Module ( Semestre semestre, String code, String libLong, String libCourt )
	{
		this.hsHeure  = new HashMap<> ( );
		this.semestre = semestre;
		this.code     = code;
		this.libLong  = libLong;
		this.libCourt = libCourt;
	}

	/*---------------------------------------*/
	/*                GETTEUR                */
	/*---------------------------------------*/

	public Semestre                getSemestre ( ) { return this.semestre; }
	public String                  getCode     ( ) { return this.code;     }
	public String                  getLibLong  ( ) { return this.libLong;  }
	public String                  getLibCourt ( ) { return this.libCourt; }
	public HashMap<Heure, Integer> getHsHeure  ( ) { return this.hsHeure;  }

	/*---------------------------------------*/
	/*                SETTEUR                */
	/*---------------------------------------*/

	public void setSemestre ( Semestre semestre ) { this.semestre = semestre; }
	public void setCode     ( String   code     ) { this.code     = code;     }
	public void setLibLong  ( String   libLong  ) { this.libLong  = libLong;  }
	public void setLibCourt ( String   libCourt ) { this.libCourt = libCourt; }

}
