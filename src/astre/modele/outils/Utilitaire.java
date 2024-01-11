package astre.modele.outils;

// tableaux
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

// classes
import java.lang.reflect.Field;

/**
 * Classe des données et d'affichage du tableau.
 * @author Maxime Lemoine
 * @version 2.0 20/12/23
 */

public abstract class Utilitaire
{
	/**
	 * Classe utilitaire sans constructeur
	 */
	private Utilitaire () {}


	/*---------------------------------------*/
	/*      METHODES TOSTRING TABLEAUX       */
	/*---------------------------------------*/ 

	/**
	 * toString
	 */
	public static String afficherValeurs ( List<?> liste )
	{
		String s="";
		
		for ( Object o : liste )
			if( o == null )
				s += "null\n";
			else
				s += o.toString ( ) + "\n";
		
		return s;
	}

	/**
	 * toString
	 */
	public static String afficherValeurs ( Object[] liste )
	{
		String s="";
		
		for ( Object o : liste )
			if( o == null )
				s += "null\n";
			else
				s += o.toString ( ) + "\n";
		
		return s;
	}
	
	/**
	 * toString
	 */
	public static String afficherValeurs ( Object[][] tableau )
	{
		String s="";
		
		for ( int cptLig = 0; cptLig < tableau.length; cptLig++ )
		{
			for ( int cptCol = 0; cptCol < tableau[cptLig].length; cptCol++ )
			{
				if( tableau[cptLig][cptCol] == null )
					s += "null ";
				else
					s += tableau[cptLig][cptCol].toString ( ) + " ";
			}
			s+="\n";
		}
		
		return s;
	}

	/**
	 * toString des types
	 */
	public static String afficherTypes ( Object[][] tableau )
	{
		String s="";
		
		for ( int cptLig = 0; cptLig < tableau.length; cptLig++ )
		{
			for ( int cptCol = 0; cptCol < tableau[cptLig].length; cptCol++ )
			{
				if( tableau[cptLig][cptCol] == null )
					s += "null ";
				else
					s += tableau[cptLig][cptCol].getClass().toString ( ) + " ";
			}
			s+="\n";
		}
		
		return s;
	}


	/*---------------------------------------*/
	/*       METHODES COPIES TABLEAUX        */
	/*---------------------------------------*/ 

	/**
	 * Copie profonde d'un tableau avec ajout d'une copie d'une nouvelle ligne
	 */
	public static Object[][] copier ( Object[][] tableau, Object[] ligne )
	{
		Object[][] tableauTmp = new Object[tableau.length+1][ligne.length];

		for ( int lig = 0; lig < tableau.length; lig++ )
		{
			tableauTmp[lig] = Arrays.copyOf ( tableau[lig], tableau[lig].length );
		}
		tableauTmp[tableauTmp.length-1] = Utilitaire.copier ( ligne );


		return tableauTmp;
	}
	
	/**
	 * Copie profonde d'un tableau avec une nouveau nombre de colonnes (supprime les dernières ou en rajoute des vides)
	 */
	public static Object[][] formater ( Object[][] tableau, int nbColonnes )
	{
		return Utilitaire.formater ( tableau, 0, nbColonnes-1 );
	}

	/**
	 * Copie profonde d'un tableau avec un nouveau nombre de colonnes (conserve les colonnes de indDeb à indFin)
	 */
	public static Object[][] formater ( Object[][] tableau, int indDeb, int indFin )
	{
		int taille = indFin - indDeb + 1;
		Object[][] tableauTmp = new Object[tableau.length][taille];

		for ( int lig = 0; lig < tableau.length; lig++ )
		{
			for ( int col = 0; col < taille ; col++ )
			{
				if ( col < tableau[lig].length )
				{
					tableauTmp[lig][col] = tableau[lig][indDeb+col];
				}
				else
				{
					// Ajoutez une valeur vide pour les nouvelles colonnes
					tableauTmp[lig][col] = null;
				}
			}
		}

		return tableauTmp;
	}

	/**
	 * Copie profonde d'un tableau
	 */
	public static Object[][] copier ( Object[][] tableau )
	{
		return Utilitaire.copier ( tableau, 0 );
	}

	/**
	 * Copie profonde d'un tableau avec 1 à supprimer
	 */
	public static Object[][] copier ( Object[][] tableau, int index )
	{
		int cptLigTmp = 0;

		if ( index < 0              ) { index = 0;              }
		if ( index > tableau.length ) { index = tableau.length; }

		Object[][] tableauTmp = new Object[tableau.length - 1][tableau[0].length];

		for ( int lig = 0; lig < tableau.length; lig++ )
			if ( lig != index )
				tableauTmp[cptLigTmp++] = copier ( tableau[lig] );

		return tableauTmp;
	}

	/**
	 * Copie profonde d'un ensemble
	 */
	public static Object[] copier ( Object[] ensemble )
	{
		return Arrays.copyOf( ensemble, ensemble.length );
	}

	public static void main(String[] args) {
		Object[][] test = { {5, 5}, {5, 6}, {7, 3} };
		Object[]   lign = { 5, 6 };
		
		Object[][] formatage = formater ( test, 10 );
	}


	/*---------------------------------------*/
	/*          METHODES CONVERSIONS         */
	/*---------------------------------------*/ 

	/**
	 * Conversion d'une instance en un objet avec tous ses attributs
	 */
	public static Object[] toArray ( Object instance )
	{
		Class<?> clazz = instance.getClass ( );
		Field[] fields = clazz.getDeclaredFields ( );
		List<Object> values = new ArrayList<> ( );

		try
		{
			for (Field field : fields)
			{
				field.setAccessible(true);
				values.add ( field.get ( instance ) );
				field.setAccessible(false);
			}
		}
		catch ( IllegalAccessException e )
		{
			e.printStackTrace();
		}

		return values.toArray();
	}


	/*---------------------------------------*/
	/*             METHODES AUTRES           */
	/*---------------------------------------*/

	/**
	 * Methode permettant d'inverser deux objets d'un tableau
	 */
	public static void inverser( Object[] liste, int ind1, int ind2 )
	{
		Object tmp = liste[ind1];
		liste[ind1] = liste[ind2];
		liste[ind2] = tmp;
	}

	//TODO: sur les méthodes typer, il n'y a peut être pas de copie profonde (le retour serait inutile)

	/**
	 * Permet de typer des objets d'un tableau en fonction du tableau de type passé en paramètres
	 */
	public static Object[][] typer ( Object[][] tableau, Class<?>[] types )
	{
		for ( int cptLig = 0; cptLig < tableau.length; cptLig++ )
		{
			Utilitaire.typer ( tableau[cptLig], types );
		}

		return tableau;
	}

	/**
	 * Permet de typer des objets d'une liste en fonction du tableau de type passé en paramètres
	 */
	public static Object[] typer ( Object[] liste, Class<?>[] types )
	{
		for ( int cpt = 0; cpt < liste.length; cpt++ )
		{
			if ( liste[cpt] != null )
			{
				liste[cpt] = types[cpt].cast ( liste[cpt] );
			}
		}

		return liste;
	}

	/**
	 * Permet de supprimer une colonne d'un tableau
	 */
	public static Object[][] supprimerColonne ( Object[][] tableau, int index )
	{
		Object[][] tableauTmp = new Object[tableau.length][tableau[0].length-1];

		for ( int cptLig = 0; cptLig < tableau.length; cptLig++ )
		{
			int cptColTmp = 0;
			for ( int cptCol = 0; cptCol < tableau[cptLig].length; cptCol++ )
			{
				if ( cptCol != index )
				{
					tableauTmp[cptLig][cptColTmp++] = tableau[cptLig][cptCol];
				}
			}
		}

		return tableauTmp;
	}
}
