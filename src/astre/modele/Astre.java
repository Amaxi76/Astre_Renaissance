package astre.modele;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import astre.Controleur;
import astre.modele.elements.*;

public class Astre
{
	private BD bd;

	public Astre ( )
	{
		this.bd = BD.getInstance ( );
	}

	/*---------------------------------------*/
	/*                GETTEUR                */
	/*---------------------------------------*/

	public Object[][]    getTableau            ( Class<?>  type      ) { return this.bd.getTableau            ( type         ); }
	public Object[][]    getTableauParticulier ( String nomRecherche ) { return this.bd.getTableauParticulier ( nomRecherche ); }
	public Semestre      getSemestre           ( int    numSemestre  ) { return this.bd.getSemestre           ( numSemestre  ); }
	public Heure         getHeure              ( int    nom          ) { return this.bd.getHeure              ( nom          ); } //TODO: nom de variable à retravailler
	public Heure         getHeure              ( String nom          ) { return this.bd.getHeure              ( nom          ); }
	public Contrat       getContrat            ( String nom          ) { return this.bd.getContrat            ( nom          ); }
	public ModuleIUT     getModule             ( String nom          ) { return this.bd.getModule             ( nom          ); }

	public List<String>      getHistorique     (                 ) { return this.bd.getHistorique   (      ); }
	public List<Contrat>     getContrats       (                 ) { return this.bd.getContrats     (      ); }
	public List<Intervenant> getIntervenants   (                 ) { return this.bd.getIntervenants (      ); }
	public <T> List<T>       getTable          ( Class<T> type   ) { return this.bd.getTable        ( type ); }

	public int               getNBHeureEQTD    ( String code, String nomHeure ) { return this.bd.getNBHeureEQTD ( code, nomHeure ); }

	/*---------------------------------------*/
	/*                SETTEUR                */
	/*---------------------------------------*/
	
	public void update ( Object o ) { this.modification ( o, "update" ); }
	public void insert ( Object o ) { this.modification ( o, "insert" ); }
	public void delete ( Object o ) { this.modification ( o, "delete" ); }

	/*---------------------------------------*/
	/*                METHODES               */
	/*---------------------------------------*/ 

	//TODO: à supprimer si bien inutile
	/*public int sommeHeure ( String code, char typeHeure )
	{
		int somme = 0;

		for ( Heure h : this.bd.getHeures ( code, typeHeure ).keySet (  ) )
			somme += this.bd.getHeures ( code, typeHeure ).get ( h );

		return somme;
	}*/

	/**
	 * Met à jour la base de donnée en créant un nouvel objet avec chaque lignes de tabDonnéesBD
	 */
	public void majTableauBD ( Object[][] tabDonneeBD, Class<?> type )
	{
		for ( int i = 0; i < tabDonneeBD.length; i++ )
		{
			Object[] sousTab = Arrays.copyOfRange ( tabDonneeBD[i], 1, tabDonneeBD[i].length );
			this.majObjetBD ( sousTab, type, ( char ) tabDonneeBD[i][0] );
			/*try
			{
				Object[] sousTab = Arrays.copyOfRange ( tabDonneeBD[i], 1, tabDonneeBD[i].length );
				
				Object objet = type.getDeclaredMethod ( "creation", Object[].class ).invoke ( null, ( Object ) sousTab );

				switch ( ( char ) tabDonneeBD[i][0] )
				{
					case ModeleTableau.AJOUTER   -> this.insert ( objet );
					case ModeleTableau.MODIFIER  -> this.update ( objet );
					case ModeleTableau.SUPPRIMER -> this.delete ( objet );
				
					default  -> { break; } // ModeleTableau.DEFAUT et ModeleTableau.IGNORER
				}
			}
			catch ( Exception e )
			{
				e.printStackTrace ( );
			}*/
		}
	}

	/**
	 * Créée un objet du bon type à partir de ses attributs contenus dans tabObjet
	 */
	public void majObjetBD ( Object[] tabObjet, Class<?> type, char modification )
	{
		try
		{	
			Object objet = type.getDeclaredMethod ( "creation", Object[].class ).invoke ( null, ( Object ) tabObjet );

			switch ( modification )
			{
				case Controleur.AJOUTER   -> this.insert ( objet );
				case Controleur.MODIFIER  -> this.update ( objet );
				case Controleur.SUPPRIMER -> this.delete ( objet );
			
				default  -> { break; } // ModeleTableau.DEFAUT et ModeleTableau.IGNORER
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace ( );
		}
	}

	/**
	 * Utilisation de la méthode INSERT, UPDATE ou DELETE sur un objet dans la base de données
	 */
	private void modification ( Object o, String methode )
	{
		try
		{
			Method updateMethod = BD.class.getMethod ( methode , o.getClass ( ) );
			updateMethod.invoke ( this.bd, o );
		}
		catch ( NoSuchMethodException  | IllegalAccessException | InvocationTargetException e )
		{
			e.printStackTrace ( );
		}
	}
	
	
	public String  getAnnee          ( ) { return this.bd.getAnnee          ( ); }


	public boolean nouvelleAnnee     ( String nom ) 
	{ 
		try 
		{
			Scanner sc              = new Scanner        ( new FileInputStream ( "../database/creation_Astre.sql" ) );
			BufferedWriter ecrivain = new BufferedWriter ( new FileWriter      ( "../database/creation_insertion_" + nom + ".sql" ) );

			String ligne = sc.nextLine ( );

			while ( sc.hasNextLine ( ) )
			{
				ecrivain.write ( ligne + "\n");

				ligne = sc.nextLine ( );
			}

			sc = new Scanner        ( new FileInputStream ( "../database/insertion_default.sql" ) );

			
			while ( sc.hasNextLine ( ) )
			{
				ecrivain.write ( ligne + "\n");

				ligne = sc.nextLine ( );
			}

			String annee =
			"INSERT INTO Annee VALUES \n"    +
			"('" + nom + "'); \n"            ;

			ecrivain.write(annee);

			ecrivain.close ( );
			return true;
		} 
		catch (Exception e) 
		{
			return false;
		}
	
	}



	public boolean nouvelleAnneeZero ( String nom ) 
	{  
		try 
		{
			Scanner sc              = new Scanner        ( new FileInputStream ( "../database/creation_Astre.sql" ) );
			BufferedWriter ecrivain = new BufferedWriter ( new FileWriter      ( "../database/creation_insertion_" + nom + ".sql" ) );

			String ligne = sc.nextLine ( );

			while ( sc.hasNextLine ( ) )
			{
				ecrivain.write ( ligne + "\n");

				ligne = sc.nextLine ( );
			}

			sc = new Scanner        ( new FileInputStream ( "../database/insertion_default.sql" ) );

			
			while ( sc.hasNextLine ( ) )
			{
				ecrivain.write ( ligne + "\n");

				ligne = sc.nextLine ( );
			}

			String annee =
			"INSERT INTO Annee VALUES \n"    +
			"('" + nom + "'); \n"            ;

			ecrivain.write(annee);

			String semestre = 
			"INSERT INTO Semestre VALUES \n" +
			"(1, 0, 0, 0, 0), \n"            +
			"(2, 0, 0, 0, 0), \n"            +
			"(3, 0, 0, 0, 0), \n"            +
			"(4, 0, 0, 0, 0), \n"            +
			"(5, 0, 0, 0, 0), \n"            +
			"(6, 0, 0, 0, 0); \n"            ;
   
			ecrivain.write(semestre);

			ecrivain.close ( );
			return true;
		}
		catch (Exception e) 
		{
			return false;
		}

	}

}