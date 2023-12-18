package astre.modele.outils;

import java.util.Arrays;

/**
 * Classe des données et d'affichage du tableau.
 * @author Maxime Lemoine
 */

public abstract class TableauUtilitaire
{
	/**
	 * Classe utilitaire sans constructeur
	 */
	private TableauUtilitaire () {}

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
		tableauTmp[tableauTmp.length-1] = TableauUtilitaire.copier ( ligne );


		return tableauTmp;
	}

	/**
	 * Copie profonde d'un tableau
	 */
	public static Object[][] copier ( Object[][] tableau )
	{
		return TableauUtilitaire.copier ( tableau, 0 );
	}

	/**
	 * Copie profonde d'un tableau avec nbLignesASupprimer à supprimer
	 */
	public static Object[][] copier ( Object[][] tableau, int nbLignesASupprimer )
	{
		if ( nbLignesASupprimer < 0              ) { nbLignesASupprimer = 0;              }
		if ( nbLignesASupprimer > tableau.length ) { nbLignesASupprimer = tableau.length; }

		Object[][] tableauTmp = new Object[tableau.length - nbLignesASupprimer][0];

		for ( int lig = 0; lig < (tableau.length - nbLignesASupprimer); lig++ )
		{
			tableauTmp[lig] = copier ( tableau[lig] );
		}

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
}