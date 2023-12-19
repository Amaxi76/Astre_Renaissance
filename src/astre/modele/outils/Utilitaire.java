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
 */

public abstract class Utilitaire
{
	/**
	 * Classe utilitaire sans constructeur
	 */
	private Utilitaire () {}

	/*---------------------------------------*/
	/*           METHODES TABLEAUX           */
	/*---------------------------------------*/ 

	/**
	 * toString
	 */
	public static String afficherTableau ( List<?> liste )
	{
		String s="";
		
		for ( Object o : liste )
		{
			s += o.toString ( ) + "\n";
		}
		
		return s;
	}

	/**
	 * toString
	 */
	public static String afficherTableau ( Object[] liste )
	{
		String s="";
		
		for ( Object o : liste )
		{
			s += o.toString ( ) + "\n";
		}
		
		return s;
	}

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

	/*public static void main(String[] args) {
		Object[][] test = { {5, 5}, {5, 6}, {7, 3} };
		Object[]   lign = { 5, 6 };
		Object[][] copie = copier ( test, lign );
		System.out.println ( test[1].length + " = " + copie[1].length );
	}*/

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

	/*public static void main(String[] args)
	{
		Object contrat = astre.modele.elements.Contrat.creation( 1, "UnContrat", 6, 15, 1.5);
		Object[] attributs = Utilitaire.toArray ( contrat );
	
		System.out.println ( afficherTableau(attributs) );
	}*/
}