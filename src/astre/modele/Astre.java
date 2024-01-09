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

	public Object[][]    getTableau            ( Class<?>  type          ) { return this.bd.getTableau            ( type         ); }
	public Object[][]    getTableauParticulier ( String nomRecherche     ) { return this.bd.getTableauParticulier ( nomRecherche ); }
	public Semestre      getSemestre           ( int    numSemestre      ) { return this.bd.getSemestre           ( numSemestre  ); }
	public Heure         getHeure              ( int    id               ) { return this.bd.getHeure              ( id           ); }
	public Intervenant   getIntervenant        ( int    id               ) { return this.bd.getIntervenant        ( id           ); }
	public Heure         getHeure              ( String nom              ) { return this.bd.getHeure              ( nom          ); }
	public Contrat       getContrat            ( String nom              ) { return this.bd.getContrat            ( nom          ); }
	public ModuleIUT     getModule             ( String nom              ) { return this.bd.getModule             ( nom          ); }

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

	/**
	 * Met à jour la base de donnée en créant un nouvel objet avec chaque lignes de tabDonnéesBD
	 */
	public void majTableauBD ( Object[][] tabDonneeBD, Class<?> type )
	{
		for ( int i = 0; i < tabDonneeBD.length; i++ )
		{
			Object[] sousTab = Arrays.copyOfRange ( tabDonneeBD[i], 1, tabDonneeBD[i].length );
			this.majObjetBD ( sousTab, type, ( char ) tabDonneeBD[i][0] );
		}
	}

	public boolean estGenere  ( ) { return this.bd.estGenere     (                                 ) ; }
	public void    genererBDD ( ) {        this.bd.executeScript ( "./database/creation_debut.sql" ) ; }

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
	
	public String        getAnnee             ( ) { return this.bd.getAnneeAct ( ) ; }
	public List<String>  getEnsAnnee          ( ) { return this.bd.getEnsAnnee ( ) ; }


	public boolean nouvelleAnnee     ( String nom ) 
	{ 
		try 
		{
			Scanner sc;
			Scanner sc1;
			BufferedWriter ecrivain;

			try
			{
				sc       = new Scanner        ( new FileInputStream ( "./database/creation_default.sql"               ) );
				sc1      = new Scanner        ( new FileInputStream ( "./database/insertion_default.sql"              ) );
				ecrivain = new BufferedWriter ( new FileWriter      ( "./database/creation_insertion_" + nom + ".sql" ) );
			}
			catch ( Exception e )
			{
				sc       = new Scanner        ( new FileInputStream ( "../database/creation_default.sql"               ) );
				sc1      = new Scanner        ( new FileInputStream ( "../database/insertion_default.sql"              ) );
				ecrivain = new BufferedWriter ( new FileWriter      ( "../database/creation_insertion_" + nom + ".sql" ) );
			}

			String ligne = "";

			while ( sc.hasNextLine ( ) )
			{
				ligne = sc.nextLine ( );
				ecrivain.write ( ligne + "\n");
			}

			ligne = "";

			while ( sc1.hasNextLine ( ) )
			{
				ligne = sc1.nextLine ( );
				ecrivain.write ( ligne + "\n");
			}

			// écriture des données à sauvegardées

			ecrivain.write( this.sauvegarde ( nom ) );

			// fermuture des scanners

			sc .close ( );
			sc1.close ( );
			ecrivain.close ( );

			// Création de la nouvelle base de données

			try 
			{
				this.bd.executeScript("./database/creation_insertion_" + nom + ".sql");
			} 
			catch (Exception e) 
			{
				this.bd.executeScript("../database/creation_insertion_" + nom + ".sql");
			}

			this.bd.insertAnnee(nom);
			this.bd.setAnneeActuelle(nom);
			
			return true;
		} 
		catch (Exception e) 
		{
			System.out.println(e) ;

			return false;
		}
	
	}

	public boolean nouvelleAnneeZero ( String nom ) 
	{  
		try 
		{
			Scanner sc;
			Scanner sc1;
			BufferedWriter ecrivain;
			try
			{
				sc       = new Scanner        ( new FileInputStream ( "./database/creation_default.sql"               ) );
				sc1      = new Scanner        ( new FileInputStream ( "./database/insertion_default.sql"              ) );
				ecrivain = new BufferedWriter ( new FileWriter      ( "./database/creation_insertion_" + nom + ".sql" ) );
			}
			catch ( Exception e )
			{
				sc       = new Scanner        ( new FileInputStream ( "../database/creation_default.sql"               ) );
				sc1      = new Scanner        ( new FileInputStream ( "../database/insertion_default.sql"              ) );
				ecrivain = new BufferedWriter ( new FileWriter      ( "../database/creation_insertion_" + nom + ".sql" ) );
			}

			String ligne = "";

			while ( sc.hasNextLine ( ) )
			{
				ligne = sc.nextLine ( );
				ecrivain.write ( ligne + "\n");
			}

			ligne = "";

			while ( sc1.hasNextLine ( ) )
			{
				ligne = sc1.nextLine ( );
				ecrivain.write ( ligne + "\n");
			}

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

			// Création de la nouvelle base de données

			try 
			{
				this.bd.executeScript("./database/creation_insertion_" + nom + ".sql");
			} 
			catch (Exception e) 
			{
				this.bd.executeScript("../database/creation_insertion_" + nom + ".sql");
			}

			this.bd.insertAnnee(nom);
			this.bd.setAnneeActuelle(nom);

			return true;
		}
		catch (Exception e) 
		{

			System.out.println(e);

			return false;
		}

	}

	public boolean changerAnnee (String nom)
	{
		try 
		{
			Scanner sc;
			Scanner sc1;
			BufferedWriter ecrivain;

			try
			{
				sc       = new Scanner        ( new FileInputStream ( "./database/creation_default.sql"                                     ) );
				sc1      = new Scanner        ( new FileInputStream ( "./database/insertion_default.sql"                                  ) );
				ecrivain = new BufferedWriter ( new FileWriter      ( "./database/creation_insertion_" + this.bd.getAnneeAct ( ) + ".sql" ) );
			}
			catch ( Exception e )
			{
				sc       = new Scanner        ( new FileInputStream ( "../database/creation_default.sql"                                     ) );
				sc1      = new Scanner        ( new FileInputStream ( "../database/insertion_default.sql"                                  ) );
				ecrivain = new BufferedWriter ( new FileWriter      ( "../database/creation_insertion_" + this.bd.getAnneeAct ( ) + ".sql" ) );
			}

			String ligne = "";

			while ( sc.hasNextLine ( ) )
			{
				ligne = sc.nextLine ( );
				ecrivain.write ( ligne + "\n");
			}

			ligne = "";

			while ( sc1.hasNextLine ( ) )
			{
				ligne = sc1.nextLine ( );
				ecrivain.write ( ligne + "\n");
			}

			// écriture des données à sauvegardées

			ecrivain.write ( this.sauvegarde ( this.bd.getAnneeAct ( ) ) );

			// Fermeture

			ecrivain.close ( );
			sc1.close ( );
			sc .close ( );

			// Création de la nouvelle base de données

			try 
			{
				this.bd.executeScript("./database/creation_insertion_" + nom + ".sql");
			} 
			catch (Exception e) 
			{
				this.bd.executeScript("../database/creation_insertion_" + nom + ".sql");
			}

			this.bd.setAnneeActuelle(nom);

			return true;
		}
		catch (Exception e) 
		{

			System.out.println(e);

			return false;
		}
	}


	private String sauvegarde ( String nom )
	{
		String sauvegarde =  " ";

		// Gestion des semestres

		String semestre = "INSERT INTO Semestre VALUES \n";

		boolean firstIteration = true;
		for (Semestre s : this.bd.getSemestres ( ) )
		{
			if ( !firstIteration ) { semestre += ", \n"; } else { firstIteration = false; }
			semestre += "(" + s.getIdSemestre ( ) + "," + s.getNbGroupeTP ( ) + "," + s.getNbGroupeTD ( ) + "," + s.getNbEtudiant ( ) + "," + s.getNbSemaine ( ) + ")";
		}

		semestre += "; \n\n";

		sauvegarde +=  ( semestre );

		// Gestion des contrat

		if ( ! this.bd.getContrats ( ).isEmpty ( ) )
		{
			String contrat = "INSERT INTO Contrat (nomContrat, hServiceContrat, hMaxContrat, ratioTP) VALUES \n";

			firstIteration = true;
			for (Contrat c : this.bd.getContrats ( ) )
			{
				if ( !firstIteration ) { contrat += ", \n"; } else { firstIteration = false; }
				contrat += "('" + c.getNom ( ) + "'," + c.getHeureServiceContrat ( ) + "," + c.getHeureMaxContrat ( ) + ",'" + c.getRatioTP ( ) + "')";
			}

			contrat += "; \n\n";

			sauvegarde +=  ( contrat );
		}

		// Gestion des heures

		if ( !this.bd.getHeures ( ).isEmpty ( ) )
		{
			String heure = "INSERT INTO Heure ( nomHeure, coeffTD ) VALUES \n";

			firstIteration = true;
			for (Heure h : this.bd.getHeures ( ) )
			{
				if ( !firstIteration ) { heure += ", \n"; } else { firstIteration = false; }
				heure += "('" + h.getNom ( ) + "','" + h.getCoefTd ( ) + "')";
			}

			heure += "; \n\n";

			sauvegarde +=  ( heure );
		}
		

		// Gestion des modules

		if ( !this.bd.getModuleIUTs ( ).isEmpty ( ) )
		{
			String ModuleIUT = "INSERT INTO ModuleIUT VALUES \n";

			firstIteration = true;
			for (ModuleIUT m : this.bd.getModuleIUTs ( ) )
			{
				if ( !firstIteration ) { ModuleIUT += ", \n"; } else { firstIteration = false; }
				ModuleIUT += "('" + m.getCode ( )   + "','" + m.getLibLong ( ) + "','" + m.getLibCourt ( ) + "','" + m.getTypeModule ( ) + 
							"'," + m.estValide ( ) + ","   + m.getSemestre ( ).getIdSemestre ( )          + ")";
			}

			ModuleIUT += "; \n\n";

			sauvegarde +=  ( ModuleIUT );
		}
		

		// Gestion des intervenants

		if ( !this.bd.getIntervenants ( ).isEmpty ( ) )
		{
			String Intervenant = "INSERT INTO Intervenant (nom, prenom, hService, hMax, Id_Contrat) VALUES \n";

			firstIteration = true;
			for (Intervenant i : this.bd.getIntervenants ( ) )
			{
				if ( !firstIteration ) { Intervenant += ", \n"; } else { firstIteration = false; }
				Intervenant += "('" + i.getNom ( ) + "','" + i.getPrenom ( ) + "'," + i.getheureService ( ) + "," + i.getHeureMaximum ( ) + "," + i.getContrat ( ).getId ( ) +")";
			}

			Intervenant += "; \n\n";

			sauvegarde +=  ( Intervenant );
		}

		// Gestion intervients

		if ( !this.bd.getIntervients ( ).isEmpty ( ) )
		{
			String Intervient = "INSERT INTO Intervient VALUES \n";

			firstIteration = true;
			for (Intervient i : this.bd.getIntervients ( ) )
			{
				if ( !firstIteration ) { Intervient += ", \n"; } else { firstIteration = false; }
				Intervient += "("  + i.getIntervenant ( ).getId ( ) + "," + i.getHeure ( ).getId ( ) + ",'" + i.getModule ( ).getCode ( ) + 
							"'," + i.getNbSemaine ( ) + "," + i.getNbGroupe ( ) + "," + i.getNbHeure ( ) + ",'" + i.getCommentaire ( ) +"')";
			}

			Intervient += "; \n\n";

			sauvegarde +=  ( Intervient );
		}

		// Gestion horaire

		if (!this.bd.getHoraires ( ).isEmpty ( ) )
		{
			String Horaire = "INSERT INTO Horaire VALUES \n";

			firstIteration = true;
			for (Horaire h : this.bd.getHoraires ( ) )
			{
				if ( !firstIteration ) { Horaire += ", \n"; } else { firstIteration = false; }
				Horaire += "("  + h.getHeure ( ).getId ( ) + ",'" + h.getModule ( ).getCode ( ) + "'," + h.getNbHeurePN ( ) + "," + 
								h.getNbHeure         ( ) + ","  + h.getNbSemaine          ( ) + ")"  ;
			}

			Horaire += "; \n\n";

			sauvegarde +=  ( Horaire );
		}

		return sauvegarde;
	}

}

