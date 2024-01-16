package astre.modele.outils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Classe représentant une fraction mathématique
 *  @author Maxime Lemoine
 *  @version : 1.2 - 16/01/2024
 *  @date : 16/01/2024
*/
//TODO: voir pour afficher par défaut que des valeurs de fractions exactes et ajouter une méthode pour tronquer les valeurs pour un affichage de fractions simplifiées

public class Fraction
{
	private static final String FRACTION_REGEX = "-?\\d+/\\d+";
	private static final String REEL_REGEX     = "-?\\d+(\\.\\d+)?";
	private static final String SEPARATEUR = "/";

	private int numerateur;
	private int denominateur;

	/*---------------------------------------*/
	/*             CONSTRUCTEURS             */
	/*---------------------------------------*/

	public Fraction ( String objet )
	{
		if ( estReelValide ( objet ) )
		{
			this.fractionFromDouble ( Double.parseDouble ( objet ) );
		}
		else
		{
			if ( !estFractionValide ( objet ) )
				throw new IllegalArgumentException ( "La chaîne de fraction n'est pas valide : " + objet );

			String[] tab = objet.split ( SEPARATEUR );
			this.numerateur   = Integer.parseInt ( tab[0] );
			this.denominateur = Integer.parseInt ( tab[1] );
		}

		this.simplifier ( );
	}

	public Fraction ( int numerateur, int denominateur )
	{
		this.numerateur   = numerateur;
		this.denominateur = denominateur;
		this.simplifier ( );
	}

	public Fraction ( Integer nombre )
	{
		this.numerateur   = nombre.intValue ( );
		this.denominateur = 1;
	}

	public Fraction ( Double nombre )
	{
		this.fractionFromDouble ( nombre );
		simplifier();
	}


	/*---------------------------------------*/
	/*                TESTEUR                */
	/*---------------------------------------*/

	/**
	 * Teste si une chaîne de caractères est une fraction valide
	 * @author IA
	 * @param fraction
	 * @return true si la chaîne est une fraction valide, false sinon
	 */
	public static boolean estFractionValide ( String fraction )
	{
		Pattern pattern = Pattern.compile ( FRACTION_REGEX );
		Matcher matcher = pattern.matcher ( fraction );
		return matcher.matches ( );
	}

	/**
	 * Teste si une chaîne de caractères est un nombre réel valide (entier ou décimal positif ou négatif) ou non
	 * @author IA
	 * @param reel
	 * @return true si la chaîne est un nombre réel valide, false sinon
	 */
	public static boolean estReelValide ( String reel )
	{
		Pattern pattern = Pattern.compile ( REEL_REGEX );
		Matcher matcher = pattern.matcher ( reel );
		return matcher.matches ( );
	}


	/*---------------------------------------*/
	/*                OUTILS                 */
	/*---------------------------------------*/

	/**
	 * Méthode qui permet de créer une fraction à partir d'un nombre décimal
	 * @author IA
	 * @param nombre nombre décimal
	 */
	//TODO: revoir cette méthode et en particulier avec "maxDenominateur" qui par défaut était à 1000000
	private void fractionFromDouble ( Double nombre )
	{
		double epsilon = 1.0e-10;
	
		int signe = ( int ) Math.signum ( nombre );
		double reste = Math.abs ( nombre );
		int maxDenominateur = 10; // Limite pour simplifier les fractions
	
		int meilleurNumerateur = 0;
		int meilleurDenominateur = 1;
		double meilleurDifference = Double.MAX_VALUE;
	
		for( int denominateur = 1; denominateur <= maxDenominateur; denominateur++ )
		{
			int numerateur = ( int ) Math.round ( reste * denominateur );
			double difference = Math.abs ( ( double ) numerateur / denominateur - reste );
	
			if( difference < meilleurDifference )
			{
				meilleurNumerateur = numerateur;
				meilleurDenominateur = denominateur;
				meilleurDifference = difference;
			}
	
			if( difference < epsilon )
			{
				break; // On a trouvé une fraction exacte
			}
		}
	
		this.numerateur = signe * meilleurNumerateur;
		this.denominateur = meilleurDenominateur;
	}
	
	/**
	 * Utilisation de l'algorithme d'Euclide pour simplifier la fraction
	 * @author IA
	 */
	private void simplifier ( )
	{
		long pgcd = pgcd ( Math.abs ( numerateur ), denominateur );
		numerateur /= pgcd;
		denominateur /= pgcd;
	}
	
	/**
	 * Calcul du plus grand commun diviseur
	 * @param a
	 * @param b
	 * @return pgcd
	 */
	private long pgcd ( long a, long b )
	{
		while( b > 0 )
		{
			long temp = b;
			b = a % b;
			a = temp;
		}
		return a;
	}


	/*---------------------------------------*/
	/*                METHODES               */
	/*---------------------------------------*/

	@Override
	public String toString ( )
	{
		if( this.denominateur == 1 )
			return String.valueOf ( this.numerateur );
		else
			return this.numerateur + SEPARATEUR + this.denominateur;
	}

	public double toDouble ( )
	{
		return ( double ) numerateur / denominateur;
	}

	public static void main ( String[] args )
	{
		System.out.println ( "     Saisie     |       Classe      " );
		System.out.println ( " Type |  Valeur | Fraction |  Double" );

		int[] entiers = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		for ( int nombre : entiers )
		{
			Fraction f = new Fraction ( nombre );
			System.out.println ( String.format ( "%5s","int" ) + " | " + String.format ( "%7s", nombre ) + " | " + String.format ( "%8s", f ) + " | " + String.format ( "%6s", f.toDouble() ) );
		}

		double[] entiersDouble = { 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0 };
		for ( double nombre : entiersDouble )
		{
			Fraction f = new Fraction ( nombre );
			System.out.println ( String.format ( "%5s","fsimp" ) + " | " + String.format ( "%7s", nombre ) + " | " + String.format ( "%8s", f ) + " | " + String.format ( "%6s", f.toDouble() ) );
		}

		double[] reels = { 1.5, 2.5, 3.5, 4.5, 5.5 };
		for ( double nombre : reels )
		{
			Fraction f = new Fraction ( nombre );
			System.out.println ( String.format ( "%5s","reel" ) + " | " + String.format ( "%7s", nombre ) + " | " + String.format ( "%8s", f ) + " | " + String.format ( "%6s", f.toDouble() ) );
		}

		String[] fractions = { "1/2", "2/3", "3/4", "4/5", "5/6" };
		for ( String fraction : fractions )
		{
			Fraction f = new Fraction ( fraction );
			System.out.println ( String.format ( "%5s","fract" ) + " | " + String.format ( "%7s", fraction ) + " | " + String.format ( "%8s", f ) + " | " + String.format ( "%6s", f.toDouble() ) );
		}

		double particulier = 0.33;
		Fraction f = new Fraction ( particulier );
		System.out.println ( String.format ( "%5s","parti" ) + " | " + String.format ( "%7s", particulier ) + " | " + String.format ( "%8s", f ) + " | " + String.format ( "%6s", f.toDouble() ) );
	}
}
