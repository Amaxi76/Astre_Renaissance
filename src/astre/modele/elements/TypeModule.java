package astre.modele.elements;

/** Classe TypeModule
  * @author : Alizéa Lebaron
  * @version : 1.0 - 12/12/2023
  * @date : 12/12/2023
  */

import java.util.ArrayList;
import java.util.List;

/**
 * TypeModule
 */
public class TypeModule 
{
	private static List<TypeModule> ensTypeModule = new ArrayList<> ( );

	private int    id;
	private String nom;

	/**
	 * @param id
	 * @param nom
	 */
	public TypeModule ( int id, String nom ) 
	{
		this.id  = id;
		this.nom = nom;
	}

	/*---------------------------------------*/
	/*                GETTEUR                */
	/*---------------------------------------*/

	/**
	 * @return
	 */
	public static List<TypeModule> getEnsTypeModule ( ) { return ensTypeModule; }

	/**
	 * @return
	 */
	public int    getId  ( ) { return id;  }

	/**
	 * @return
	 */
	public String getNom ( ) { return nom; }

	

	/*---------------------------------------*/
	/*                SETTEUR                */
	/*---------------------------------------*/

	/**
	 * @param ensTypeModule
	 */
	public static void setEnsTypeModule ( List<TypeModule> ensTypeModule ) { TypeModule.ensTypeModule = ensTypeModule; }

	/**
	 * @param id
	 */
	public void setId  ( int    id  ) { this.id  = id;  }
	/**
	 * @param nom
	 */
	public void setNom ( String nom ) { this.nom = nom; }

	/*---------------------------------------*/
	/*                METHODES               */
	/*---------------------------------------*/

	/**
	 * @return 
	 */
	public String toString ( ) 
	{
		String sRet = "";

		sRet += String.format ( "ID  : %3d  \n", this.id  );
		sRet += String.format ( "Nom : %15s \n", this.nom );

		return sRet;
	}

	
}