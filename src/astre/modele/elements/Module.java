package astre.modele.elements;

/** Classe Module 
  * @author : Maximilien Lesterlin
  * @version : 1.0 - 11/12/2023
  * @date : 06/12/2023
  */

import java.util.Map;
import java.util.HashMap;

public class Module
{
	HashMap<Heure, Integer> hsHeuresPn;
	HashMap<Heure, Integer> hsHeuresRepariees;
	
	
	Semestre   semestre;
	TypeModule typeModule;
	String     code;
	String     libLong;
	String     libCourt;
	boolean    valide;

	public Module ( Semestre semestre, TypeModule typeModule, String code, String libLong, String libCourt )
	{
		this.hsHeuresPn         = new HashMap<> ( );
		this.hsHeuresRepariees  = new HashMap<> ( );
		this.semestre           = semestre;
		this.typeModule         = typeModule;
		this.code               = code;
		this.libLong            = libLong;
		this.libCourt           = libCourt;
		this.valide             = false;
	}

	/*---------------------------------------*/
	/*                GETTEUR                */
	/*---------------------------------------*/

	public boolean             estValide             ( ) { return this.valide;            }
	public Semestre            getSemestre           ( ) { return this.semestre;          }
	public TypeModule          getTypeModule         ( ) { return this.typeModule;        }
	public String              getCode               ( ) { return this.code;              }
	public String              getLibLong            ( ) { return this.libLong;           }
	public String              getLibCourt           ( ) { return this.libCourt;          }
	public Map<Heure, Integer> getHsHeureRepartiees  ( ) { return this.hsHeuresRepariees; }
	public Map<Heure, Integer> getHsHeurePn          ( ) { return this.hsHeuresPn;        }

	public int getHeureRepartiees ( )
	{
		int somme = 0;
		
		for ( Heure h : this.hsHeuresRepariees.keySet ( ) )
			somme += this.hsHeuresRepariees.get ( h );

		return somme;
	}

	public int getHeurePn ( )
	{
		int somme = 0;
		
		for ( Heure h : this.hsHeuresPn.keySet ( ) )
			somme += this.hsHeuresPn.get ( h );

		return somme;
	}


	/*---------------------------------------*/
	/*                SETTEUR                */
	/*---------------------------------------*/

	public void setSemestre   ( Semestre   semestre   ) { this.semestre   = semestre;   }
	public void setTypeModule ( TypeModule typeModule ) { this.typeModule = typeModule; }
	public void setCode       ( String     code       ) { this.code       = code;       }
	public void setLibLong    ( String     libLong    ) { this.libLong    = libLong;    }
	public void setLibCourt   ( String     libCourt   ) { this.libCourt   = libCourt;   }
	public void setValide     ( boolean    choix      ) { this.valide     = choix;      }
}
