package astre.modele;

/** Page de gestion de la base de données
  * @author : Matéo Sa, Alizéa Lebaron, Maximilien Lesterlin, Maxime Lemoine et Clémentin Ly
  * @version : 1.0 - 11/12/2023
  * @date : 06/12/2023
  */

//TODO: Penser à fermer le rs et st
//TODO: Refactoriser la métode UPDATE

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import astre.modele.elements.*;

public class BD
{
	Connection co;
	PreparedStatement ps;
	private static BD dbInstance;
	
	private BD ( )
	{
		try 
		{
			Class.forName ( "org.postgresql.Driver" );
			
			//co = DriverManager.getConnection ( "jdbc:postgresql://localhost:7777/sm220306", "sm220306", "mateo2705" ); //Pour alizéa
			co = DriverManager.getConnection ( "jdbc:postgresql://woody/sm220306", "sm220306", "mateo2705" );
		} 
		catch ( ClassNotFoundException e ) 
		{
			System.out.println ( "Erreur 1 de connexion à la base de données : " + e );
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur 2 de connexion à la base de données " +  e );
		}
	}
	
	public static BD getInstance ( )
	{
		return dbInstance != null ? dbInstance : new BD ( );
	}
	
	
	/*---------------------------------------*/
	/*            RECUP GENERALE             */
	/*---------------------------------------*/
	
	/** Méthode générique pour la récupération de table
	 * Exemple d'utilisation : ArrayList<Semestre> ensS= new ArrayList<> (getTable ( "Semestre", Semestre.class ) ) ;
	 */

	public <T> List<T> getTable ( Class<T> clazz )
	{
		ArrayList<T> lst = new ArrayList<>();

		try
		{
			Statement st = co.createStatement ( );
			ResultSet rs = st.executeQuery ( "SELECT * FROM " + clazz.getSimpleName ( ) );

			while ( rs.next ( ) )
			{
				if ( clazz == Semestre.class      )
					lst.add ( ( T ) new Semestre ( rs.getInt ( 1 ), rs.getInt ( 2 ), rs.getInt ( 3 ),rs.getInt ( 4 ), rs.getInt ( 5 ) ) );
				
				if ( clazz == Contrat.class      )
				{
					try
					{
						lst.add ( ( T ) Contrat.creation ( rs.getInt ( 1 ), rs.getString ( 2 ), rs.getInt ( 3 ), rs.getInt ( 4 ), rs.getDouble ( 5 ) ) );
					}
					catch ( Exception e )
					{
						e.printStackTrace ( );
					}
				}
				
				if ( clazz == Heure.class        )
					lst.add ( ( T ) new Heure ( rs.getInt ( 1 ), rs.getString ( 2 ), rs.getDouble ( 3 ) ) );
				
				if ( clazz == Intervenant.class )
					lst.add ( ( T ) new Intervenant( rs.getInt ( 1 ), rs.getString ( 2 ), rs.getString ( 3 ), getContrat ( rs.getInt ( 6 ) ), rs.getInt ( 4 ), rs.getInt ( 5 ) ) );
				
				// Ajouter d'autres conditions pour d'autres classes si nécessaire
			}

		}
		catch ( SQLException e )
		{
			System.out.println("Erreur getTable : " + e);
		}

		return lst;
	}

	public List<Semestre>    getSemestres    ( ) { return this.getTable ( Semestre   .class ); }
	public List<Intervenant> getIntervenants ( ) { return this.getTable ( Intervenant.class ); }
	public List<Contrat>     getContrats     ( ) { return this.getTable ( Contrat    .class ); }
	public List<Heure>       getHeures       ( ) { return this.getTable ( Heure      .class ); }
	public List<Intervient>  getIntervients  ( ) { return this.getTable ( Intervient .class ); }


	public List<ModuleIUT> getModules ( int numeroSemestre )
	{
		//TODO: refaire le compteur
		
		ArrayList<ModuleIUT> ensModules = new ArrayList<> ( );
		
		String REQUETE = "SELECT * FROM f_selectModulesIUTParSemestre(?)";

		try 
		{
			Statement         st = co.createStatement  (         );
			PreparedStatement ps = co.prepareStatement ( REQUETE );

			ps.setInt ( 1, numeroSemestre );

			ResultSet rs = ps.executeQuery ( );

			while ( rs.next ( ) ) 
			{
				int iS = 6;
				int iM = 1;

				Semestre   semestre   = new Semestre   ( rs.getInt ( iS++ ), rs.getInt ( iS++ ), rs.getInt ( iS++ ), rs.getInt ( iS++ ), rs.getInt ( iS ) );

				Map<Heure, Integer> hmHeuresPn         = this.getHeures ( rs.getString ( 1 ), 'P' );
				Map<Heure, Integer> hmHeuresRepartiees = this.getHeures ( rs.getString ( 1 ), 'R' );
				
				ModuleIUT  moduleIUT =  new ModuleIUT ( semestre, rs.getString ( 4 ), rs.getString ( iM++ ), rs.getString ( iM++ ), rs.getString ( iM ), rs.getBoolean ( 5 ), hmHeuresPn, hmHeuresRepartiees );;

				ensModules.add ( moduleIUT );
			}

			rs.close ( );
			ps.close ( );
			st.close ( );
		} 
		catch ( SQLException e )
		{
			System.out.println ( "getModules ( )" +  e );
		}
		
		return ensModules;
	}

	private Map<Heure, Integer> getHeures ( String code, char typeHeure ) //typeHeure = 'P' ou 'R'
	{
		HashMap<Heure, Integer> hm = new HashMap<> ( );
		
		Heure heure    = null;
		String REQUETE = "SELECT * FROM f_selectHoraireParModule(?)";

		try
		{
			Statement         st = co.createStatement (         );
			PreparedStatement ps = co.prepareStatement( REQUETE );

			ps.setString ( 1, code );

			ResultSet rs = ps.executeQuery ( );

			while ( rs.next ( ) )
			{
				for ( Heure h : this.getHeures ( ) )
					if ( h.getNom ( ).equals ( rs.getString ( 1 ) )  )
						heure = h;

				int heureS = ( typeHeure == 'P' ) ? 2 : 3;
	
				hm.put ( heure, rs.getInt ( heureS ) );
			}
				
			rs.close ( );
			ps.close ( );
			st.close ( );
		}
		catch ( SQLException e ) 
		{
			System.out.println ( e );
		}

		return hm;
	}
		
	
	public List<Horaire> getHoraires( String module )
	{
		ArrayList<Horaire> ensHoraire = new ArrayList<> ( );
		
		//TODO faire fonction
		String REQUETE = "SELECT * FROM Horaire where Code_ModuleIUT = ?";

		try 
		{
			Statement         st = co.createStatement  (         );
			PreparedStatement ps = co.prepareStatement ( REQUETE );

			ps.setString ( 1, module );

			ResultSet rs = ps.executeQuery ( );

			while ( rs.next ( ) ) 
			{
				Horaire h = new Horaire( getHeure( rs.getInt(1)), getModule(rs.getString(2)), rs.getInt(3), rs.getInt(4), rs.getInt(5));
				ensHoraire.add ( h );
			}

			rs.close ( );
			ps.close ( );
			st.close ( );
		} 
		catch ( SQLException e )
		{
			System.out.println ( "getHoraire ( )" +  e );
		}
		
		return ensHoraire;
	}
	
	/*---------------------------------------*/
	/*             RECUP UNITAIRE            */
	/*---------------------------------------*/

	public Semestre getSemestre ( int c )
	{
		Semestre semestre = null;
		
		try 
		{
			Statement st = co.createStatement ( );
			ResultSet rs = st.executeQuery ( "select * from Semestre where Id_Semestre = " + c );
			while ( rs.next ( ) ) 
			{
				semestre = new Semestre ( rs.getInt ( 1 ), rs.getInt ( 2 ), rs.getInt ( 3 ), rs.getInt ( 4 ), rs.getInt ( 5 )  );
			}

			rs.close ( );
			st.close ( );
		} 
		catch ( SQLException e ) 
		{
			System.out.println ( "Erreur getSemestre(int c) : " + e );
		}
		
		return semestre;
	}

	public Contrat getContrat ( int c )
	{
		Contrat contrat = null;
		
		try
		{
			Statement st = co.createStatement ( );
			ResultSet rs = st.executeQuery ( "select * from Contrat where Id_Contrat = " + c );
			while ( rs.next( ) )
			{
				try
				{
					contrat = Contrat.creation ( rs.getInt ( 1 ), rs.getString ( 2 ), rs.getInt ( 3 ), rs.getInt ( 4 ), rs.getDouble ( 5 ) );
				}
				catch (Exception e)
				{
					// TODO: handle exception
				}
			}

			rs.close();
			st.close();
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur getContrat(int c) : " + e );
		}
		
		return contrat;
	}

	public Contrat getContrat ( String c )
	{
		Contrat contrat = null;
		
		try
		{
			Statement st = co.createStatement ( );
			ResultSet rs = st.executeQuery ( "select * from Contrat where nomContrat = '" + c  +"'");
			while ( rs.next( ) )
			{
				try
				{
					contrat = Contrat.creation ( rs.getInt ( 1 ), rs.getString ( 2 ), rs.getInt ( 3 ), rs.getInt ( 4 ), rs.getDouble ( 5 ) );
				}
				catch (Exception e)
				{
					e.printStackTrace ( );
				}
				
			}
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur getContrat(int c) : " + e );
		}
		
		return contrat;
	}

	public int getInterventionIntervenant(int inter, int semes)
	{
		int result = 0;
		
		try
		{
			Statement st = co.createStatement ( );
			ResultSet rs = st.executeQuery ( "SELECT nbSemaine, nbGroupe, nbHeure " + 
					                         "FROM   Intervenant i JOIN Intervient t  ON i.Id_Intervenant  = t.Id_Intervenant " + 
					                         "                     JOIN ModuleIUT m   ON m.Code_ModuleIUT = t.Code_ModuleIUT " + 
				                             "Where  Id_Semestre      = "+ semes +" AND " + 
					                         "       i.Id_intervenant = " + inter);
			while ( rs.next( ) )
			{
				result = rs.getInt(1) * rs.getInt(2) * rs.getInt(3);
			}
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur getContrat(int c) : " + e );
		}
		
		return result;
	}

	public 
	
	public Intervenant getIntervenant ( int i )
	{
		Intervenant intervenant = null;
		
		try 
		{
			Statement st = co.createStatement ( );
			ResultSet rs = st.executeQuery ( "select * from Intervenant where Id_Intervenant = " + i );
			while ( rs.next ( ) ) 
			{
				intervenant = new Intervenant ( rs.getInt ( 1 ), rs.getString ( 2 ), rs.getString ( 3 ), getContrat ( rs.getInt ( 4 ) ), rs.getInt ( 5 ), rs.getInt ( 6 ) );
			}
		} 
		catch ( SQLException e ) 
		{
			System.out.println ( "Erreur getIntervenant(int c) : " + e );
		}
		
		return intervenant;
	}

	public Heure getHeure ( int h )
	{
		Heure heure = null;
		
		try 
		{
			Statement st = co.createStatement ( );
			ResultSet rs = st.executeQuery ( "select * from Heure where Id_Heure = " + h  );
			while ( rs.next ( ) ) 
			{
				heure = new Heure ( rs.getInt ( 1 ), rs.getString ( 2 ), rs.getDouble ( 3 ) );
			}
		} 
		catch ( SQLException e ) 
		{
			System.out.println ( "Erreur getHeure(int h) : " + e );
		}
		
		return heure;
	}

	public Heure getHeure ( String h )
	{
		Heure heure = null;
		
		try 
		{
			Statement st = co.createStatement ( );
			ResultSet rs = st.executeQuery ( "select * from Heure where nomheure = '" + h + "'"  );
			while ( rs.next ( ) ) 
			{
				heure = new Heure ( rs.getInt ( 1 ), rs.getString ( 2 ), rs.getDouble ( 3 ) );
			}
		} 
		catch ( SQLException e ) 
		{
			System.out.println ( "Erreur getHeure(int h) : " + e );
		}
		
		return heure;
	}

	public ModuleIUT getModule ( String m )
	{
		ModuleIUT module = null;
		
		try 
		{
			Statement st = co.createStatement ( );
			ResultSet rs = st.executeQuery ( "select * from ModuleIUT where Code_ModuleIUT = '" + m + "'" );
			while ( rs.next ( ) ) 
			{
				Map<Heure, Integer> hmHeuresPn         = this.getHeures ( rs.getString ( 1 ), 'P' );
				Map<Heure, Integer> hmHeuresRepartiees = this.getHeures ( rs.getString ( 1 ), 'R' );
				
				module = new ModuleIUT ( getSemestre ( rs.getInt ( 6 ) ), rs.getString ( 4 ), rs.getString ( 1 ), rs.getString ( 2 ), rs.getString ( 3 ),rs.getBoolean ( 5 ), hmHeuresPn, hmHeuresRepartiees );
			}
		} 
		catch ( SQLException e ) 
		{
			System.out.println ( "Erreur getModule(int m) : " + e );
		}
		
		return module;
	}


	/*---------------------------------------*/
	/*              RECUP TABLO              */
	/*---------------------------------------*/

	//TODO: Fonction SQL ?

	public Object[][] getModulesTableau ( )
	{
		int nbModule = 0;
		
		try
		{
			Statement st = co.createStatement ( );
			ResultSet rs = st.executeQuery ( "select count(*) from ModuleIUT" );
			while ( rs.next ( ) )
			{
				nbModule = rs.getInt ( 1 );
				System.out.println ( nbModule );
			}

			rs.close ( );
			st.close ( );
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur 1 getModulesTableau() : " + e );
		}
		
		Object[][] modules = new Object[nbModule][4];

		try
		{
			Statement st = co.createStatement ( );
			ResultSet rs = st.executeQuery ( "select Id_ModuleIUT, libLong from ModuleIUT" );
			int cpt = 0;
			while ( rs.next ( ) )
			{
				modules[cpt][0] = rs.getString ( 1 );
				modules[cpt][1] = rs.getString ( 2 );
				modules[cpt][2] = "";
				modules[cpt][3] = "";
				cpt++;
			}

			rs.close ( );
			st.close ( );
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur 2 getModulesTableau() : " + e );
		}
		return modules;
	}

	public Object[][] getIntervenantsTableau ( )
	{
		//TODO: première partie de la requete inutile ? faire la taille du resultSet ? 
		int nbInervenants = 0;
		
		try
		{
			Statement st = co.createStatement ( );
			ResultSet rs = st.executeQuery ( "select count(*) from Intervenant" );
			while ( rs.next ( ) )
				nbInervenants = rs.getInt ( 1 );

			rs.close ( );
			st.close ( );
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur 1 getIntervenantsTableau() : " + e );
		}

		Object[][] intervenants = new Object[nbInervenants][16];

		try
		{
			Statement st = co.createStatement ( );
			ResultSet rs = st.executeQuery ( "select Id_Intervenant, nomContrat, nom, prenom, hService, hMax from Intervenant i join Contrat c on i.Id_Contrat = c.Id_Contrat" );
			int cpt = 0;
			while ( rs.next ( ) )
			{
				int s1 = getInterventionIntervenant ( rs.getInt(1), 1 );
				int s2 = getInterventionIntervenant ( rs.getInt(1), 2 );
				int s3 = getInterventionIntervenant ( rs.getInt(1), 3 );
				int s4 = getInterventionIntervenant ( rs.getInt(1), 4 );
				int s5 = getInterventionIntervenant ( rs.getInt(1), 5 );
				int s6 = getInterventionIntervenant ( rs.getInt(1), 6 );
				int ttimp = s1 + s3 + s5;
				int ttpair = s2 + s4 + s6;

				intervenants[cpt][0]  = rs.getInt    (1);//Id
				intervenants[cpt][1]  = rs.getString (2);//contrat
				intervenants[cpt][2]  = rs.getString (3);//nom
				intervenants[cpt][3]  = rs.getString (4);//prenom
				intervenants[cpt][4]  = rs.getInt    (5);//hservice
				intervenants[cpt][5]  = rs.getInt    (6);//hmax
				intervenants[cpt][6]  = getContrat   (rs.getString(2)).getRatioTP();//coeff
				intervenants[cpt][7]  = s1;
				intervenants[cpt][8]  = s3;
				intervenants[cpt][9]  = s5;
				intervenants[cpt][10] = ttimp;
				intervenants[cpt][11] = s2;
				intervenants[cpt][12] = s4;
				intervenants[cpt][13] = s6;
				intervenants[cpt][14] = ttpair;
				intervenants[cpt][15] = ttimp + ttpair;

				cpt++;
			}

			rs.close ( );
			st.close ( );
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur 2 getIntervenantsTableau ( ) : " +  e );
		}

		return intervenants;
	}

	public Object[][] getIntervientsTableau( String module )
	{
		int nbIntervients = 0;
		
		try
		{
			Statement st = co.createStatement ( );
			ResultSet rs = st.executeQuery ( "select count(*) from Intervient where Code_ModuleIUT ='" + module + "'" );
			while ( rs.next ( ) )
				nbIntervients = rs.getInt ( 1 );
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur 1 getIntervientsTableau() : " + e );
		}

	// 	Object[][] intervients = new Object[nbIntervients][6];

		try
		{
			Statement st = co.createStatement ( );
			ResultSet rs = st.executeQuery ( "select Id_Intervenant, nomHeure, nbSemaine, nbGroupe, nbHeure, commentaire from Intervient where Code_ModuleIUT ='" + module + "'" );
			int cpt = 0;
			while ( rs.next ( ) )
			{
				intervients[cpt][0] = getIntervenant(rs.getInt ( 1 )).getNom();//nom
				intervients[cpt][1] = rs.getString ( 2 );//heure
				intervients[cpt][2] = rs.getInt    ( 3 );//nbsemaine
				intervients[cpt][3] = rs.getInt    ( 4 );//nbgroupe
				intervients[cpt][4] = rs.getInt    ( 5 );//nbheure
				intervients[cpt][5] = rs.getString ( 6 );//commentaire

	// 			if( intervients[cpt][5] == null )
	// 				intervients[cpt][5] = "";

				cpt++;
			}
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur 2 getIntervientsTableau ( ) : " +  e );
		}

		if( nbInbIntervients == 0 )
		{
			Object[][] test = new Object[1][6];
			for( int cpt = 0; cpt < intervients.length; cpt++)
			{
				test[0][cpt] = "";
			}
			return test;
		}

		return intervients;
	}

	public Object[][] getIntervientsTableau( )
	{
		int nbIntervients = 0;
		
		try
		{
			Statement st = co.createStatement ( );
			ResultSet rs = st.executeQuery ( "select count(*) from Intervient");
			while ( rs.next ( ) )
				nbIntervients = rs.getInt ( 1 );
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur 1 getIntervientsTableau() : " + e );
		}

		Object[][] intervients = new Object[nbIntervients][6];

		try
		{
			Statement st = co.createStatement ( );
			ResultSet rs = st.executeQuery ( "select Id_Intervenant, Id_Heure, nbSemaine, nbGroupe, nbHeure, commentaire from Intervient");
			int cpt = 0;
			while ( rs.next ( ) )
			{
				intervients[cpt][0] = getIntervenant(rs.getInt ( 1 )).getNom();//nom
				intervients[cpt][1] = getHeure(rs.getInt ( 2 )).getNom();//heure
				intervients[cpt][2] = rs.getInt    ( 3 );//nbsemaine
				intervients[cpt][3] = rs.getInt    ( 4 );//nbgroupe
				intervients[cpt][4] = rs.getInt    ( 5 );//nbheure
				intervients[cpt][5] = rs.getString ( 6 );//commentaire

				if( intervients[cpt][5] == null )
					intervients[cpt][5] = "";

				cpt++;
			}
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur 2 getIntervientsTableau ( ) : " +  e );
		}

	// 	return intervients;
	// }

	public Object[][] getContratsTableau ( )
	{
		int nbContrat = 0;
		
		try
		{
			Statement st = co.createStatement ( );
			ResultSet rs = st.executeQuery ( "select count(*) from Contrat" );
			while ( rs.next ( ) )
			{
				nbContrat = rs.getInt ( 1 );
			}

			rs.close ( );
			st.close ( );
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur 1 getModulesTableau() : " + e );
		}
		
		Object[][] contrats = new Object[nbContrat][5];

		try
		{
			Statement st = co.createStatement ( );
			ResultSet rs = st.executeQuery ( "select * from Contrat" );
			int cpt = 0;
			while ( rs.next ( ) )
			{
				contrats[cpt][0] = rs.getInt    ( 1 );
				contrats[cpt][1] = rs.getString ( 2 );
				contrats[cpt][2] = rs.getInt    ( 3 );
				contrats[cpt][3] = rs.getInt    ( 4 );
				contrats[cpt][4] = rs.getDouble ( 5 );
				cpt++;
			}

			rs.close ( );
			st.close ( );
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur 2 (ici) getContratsTableau ( ) : " + e );
		}
		return contrats;
	}

	public Object[][] getHeureTableau ( )
	{
		int nbHeure = 0;
		
		try
		{
			Statement st = co.createStatement ( );
			ResultSet rs = st.executeQuery ( "select count(*) from Heure" );
			while ( rs.next ( ) )
			{
				nbHeure = rs.getInt ( 1 );
			}

			rs.close ( );
			st.close ( );
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur 1 getHeureTableau() : " + e );
		}
		
		Object[][] heures = new Object[nbHeure][3];

		try
		{
			Statement st = co.createStatement ( );
			ResultSet rs = st.executeQuery ( "select * from Heure" );
			int cpt = 0;
			while ( rs.next ( ) )
			{
				heures[cpt][0] = rs.getString ( 1 );
				heures[cpt][1] = rs.getString ( 2 );
				heures[cpt][2] = rs.getDouble ( 3 );

				System.out.println("oui");

				cpt++;
			}

			rs.close ( );
			st.close ( );
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur 2 getHeureTableau ( ) : " + e );
		}
		return heures;
	}

	/*---------------------------------------*/
	/*                INSERT                 */
	/*---------------------------------------*/

	public void insert ( Contrat c )
	{
		String req = "INSERT INTO Contrat ( nomContrat, hServiceContrat, hMaxContrat, ratioTP ) VALUES(?,?,?,?)";
		try
		{
			ps = co.prepareStatement ( req );
			ps.setString ( 1, c.getNom                 ( ) );
			ps.setInt    ( 2, c.getHeureServiceContrat ( ) );
			ps.setInt    ( 3, c.getHeureMaxContrat     ( ) );
			ps.setDouble ( 4, c.getRatioTP             ( ) );
			ps.executeUpdate ( );

			ps.close ( );
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur insert ( Contrat c ) : " + e );
		}
	}

	public void insert ( Heure h )
	{
		String req = "INSERT INTO Heure VALUES(?,?)";
		try
		{
			ps = co.prepareStatement ( req );
			ps.setString ( 1, h.getNom    ( ) );
			ps.setDouble ( 2, h.getCoefTd ( ) );
			ps.executeUpdate ( );

			ps.close ( );
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur insert ( Heure h ) : " +  e );
		}
	}

	/*public void insert ( Module m )
	{
		String req = "INSERT INTO Module VALUES(?,?,?,?,?)";
		try
		{
			ps = co.prepareStatement ( req );
			ps.setString ( 1, m.getCode() );
			ps.setString ( 2, m.getLibLong  ( ) );
			ps.setString ( 3, m.getLibCourt ( ) );
			ps.setInt    ( 4, m.getT );
			ps.setInt    ( 5, m.getSemestre ( ).getIdSemestre ( ) );
			ps.executeUpdate ( );

			ps.close ( );
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur insert(module m) : " + e );
		}
	}*/
	
	public void insert ( Intervenant i )
	{
		String req = "INSERT INTO Intervenant (nom, prenom, hService, hMax, Id_Contrat) VALUES(?,?,?,?,?)";
		try
		{
			ps = co.prepareStatement ( req );
			ps.setString ( 1, i.getNom          ( ) );
			ps.setString ( 2, i.getPrenom       ( ) );
			ps.setInt    ( 3, i.getheureService ( ) );
			ps.setInt    ( 4, i.getHeureMaximum ( ) );
			ps.setInt    ( 5, i.getContrat      ( ).getId ( ) );
			ps.executeUpdate ( );

			ps.close ( );
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur insert ( Intervenant i ) : " + e );
		}
	}

	public void insert ( Intervient e )
	{
		String req = "INSERT INTO Intervient VALUES(?,?,?,?,?,?,?)";
		try
		{
			ps = co.prepareStatement ( req );
			ps.setInt    ( 1, e.getIntervenant ( ).getId   ( ) );
			ps.setString ( 2, e.getHeure       ( ).getNom  ( ) );
			ps.setString ( 3, e.getModule      ( ).getCode ( ) );
			ps.setInt    ( 4, e.getNbSemaine   ( )             );
			ps.setInt    ( 5, e.getNbGroupe    ( )             );
			ps.setInt    ( 6, e.getNbHeure     ( )             );
			ps.setString ( 7, e.getCommentaire ( )             );
			ps.executeUpdate ( );

			ps.close ( );
		}
		catch ( SQLException x )
		{
			System.out.println ( "Erreur insert ( Intervient e ) : " + x );
		}
	}


	public void insert ( Horaire h )
	{
		String req = "INSERT INTO Horaire VALUES(?,?,?,?,?,?,?)";
		try
		{
			ps = co.prepareStatement ( req );
			ps.setString ( 1, h.getHeure     ( ).getNom  ( ) );
			ps.setString ( 2, h.getModule    ( ).getCode ( ) );
			ps.setInt    ( 3, h.getNbHeurePN ( )             );
			ps.setInt    ( 4, h.getNbHeure   ( )             );
			ps.setInt    ( 5, h.getNbSemaine ( )             );
			ps.executeUpdate ( );

			ps.close ( );
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur insert ( Horaire h ) : " + e );
		}
	}

	/*---------------------------------------*/
	/*                DELETE                 */
	/*---------------------------------------*/

	public void delete ( Contrat c )
	{
		String req = "DELETE FROM Contrat where Id_Contrat = ?";
		try
		{
			ps = co.prepareStatement ( req );
			ps.setInt ( 1, c.getId ( ) );
			ps.executeUpdate ( );

			ps.close ( );
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur delete ( Contrat c ) : " + e );
		}
	}

	public void delete ( Heure h )
	{
		String req = "DELETE FROM Heure where nomHeure = ?";
		try
		{
			ps = co.prepareStatement ( req );
			ps.setString ( 1, h.getNom ( ) );
			ps.executeUpdate ( );

			ps.close ( );
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur delete ( Heure h ) : " + e );
		}
	}

	public void delete ( ModuleIUT m )
	{
		String req = "DELETE FROM ModuleIUT where Id_ModuleIUT = ?";
		try
		{
			ps = co.prepareStatement ( req );
			ps.setString ( 1, m.getCode ( ) );
			ps.executeUpdate ( );

			ps.close ( );
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur delete ( Module m ) : " +  e );
		}
	}

	public void delete ( Intervenant i )
	{
		String req = "DELETE FROM Intervenant where Id_Intervenant = ?";
		try
		{
			ps = co.prepareStatement ( req );
			ps.setInt ( 1, i.getId ( ) );
			ps.executeUpdate ( );

			ps.close ( );
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur delete ( Intervenant i ) : " + e );
		}
	}
	
	public void delete ( Intervient e )
	{
		String req = "DELETE FROM Intervient where Id_Intervenant = ? AND nomHeure = ? AND Id_ModuleIUT = ?";
		try
		{
			ps = co.prepareStatement ( req );
			ps.setInt    ( 1, e.getIntervenant ( ).getId   ( ) );
			ps.setString ( 2, e.getHeure       ( ).getNom  ( ) );
			ps.setString ( 3, e.getModule      ( ).getCode ( ) );
			ps.executeUpdate ( );

			ps.close ( );
		}
		catch ( SQLException x )
		{
			System.out.println ( "Erreur delete ( Intervient e ) : " + x );
		}
	}

	public boolean nouvelleAnnee (  )
	{
		
		try
		{
			String req = "SELECT f_deleteIntervient ( )";
			ps = co.prepareStatement ( req );
			ps.execute ( );

			req = "SELECT f_updateAnneeSemestre ( )";
			ps = co.prepareStatement ( req );
			ps.execute ( );

			ps.close ( );

			return true;
		}
		catch ( SQLException x )
		{
			System.out.println ( "Erreur nouvelleAnnee ( ) : " + x );
			return false;
		}
	}

	public boolean nouvelleAnneeZero (  )
	{
		
		try
		{
			String req = "SELECT f_deleteAll ( )";
			ps = co.prepareStatement ( req );
			ps.execute ( );

			req = "SELECT f_updateAnneeSemestre ( )";
			ps = co.prepareStatement ( req );
			ps.execute ( );

			ps.close ( );

			return true;
		}
		catch ( SQLException x )
		{
			System.out.println ( "Erreur nouvelleAnneeZero ( ) : " + x );
			return false;
		}
	}

	public void delete ( Horaire h )
	{
		String req = "DELETE FROM Horaire where nomHeure = ? AND Code_ModuleIUT = ?";
		try
		{
			ps = co.prepareStatement ( req );
			ps.setString ( 1, h.getHeure  ( ).getNom  ( ) );
			ps.setString ( 2, h.getModule ( ).getCode ( ) );
			ps.executeUpdate ( );

			ps.close ( );
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur delete ( horaire h ) : " + e );
		}
	}


	/*---------------------------------------*/
	/*                UPDATE                 */
	/*---------------------------------------*/

	//TODO: Faire tous les updates

	public void update ( Semestre s )
	{
		String req = "UPDATE Semestre SET nbGroupeTP = ?, nbGroupeTD = ?, nbEtud = ?, nbSemaine = ? WHERE Id_Semestre = ? ";
		try
		{
			ps = co.prepareStatement ( req );
			ps.setInt ( 1, s.getNbGroupeTP ( ) );
			ps.setInt ( 2, s.getNbGroupeTD ( ) );
			ps.setInt ( 3, s.getNbEtudiant ( ) );
			ps.setInt ( 4, s.getNbSemaine  ( ) );
			ps.setInt ( 5, s.getIdSemestre ( ) );
			ps.executeUpdate ( );

			ps.close ( );
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur update ( Semestre s ) : " + e );
		}
	}

	public void update ( Contrat c )
	{
		String req = "UPDATE Contrat SET nomContrat = ?, hServiceContrat = ?, hMaxContrat = ?, ratioTP = ? WHERE Id_Contrat = ?";
		try
		{
			ps = co.prepareStatement ( req );
			ps.setString ( 1, c.getNom                 ( ) );
			ps.setInt    ( 2, c.getHeureServiceContrat ( ) );
			ps.setInt    ( 3, c.getHeureMaxContrat     ( ) );
			ps.setDouble ( 4, c.getRatioTP             ( ) );
			ps.setInt    ( 5, c.getId                  ( ) );
			ps.executeUpdate ( );

			ps.close ( );
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur update ( Contrat c ) : " + e );
		}
	}

	public void update ( Heure h )
	{
		String req = "UPDATE Heure SET coeffTD = ? WHERE nomHeure = ?";
		try
		{
			ps = co.prepareStatement ( req );
			ps.setDouble ( 1, h.getCoefTd ( ) );
			ps.setString ( 2, h.getNom    ( ) );
			ps.executeUpdate ( );

			ps.close ( );
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur update ( Heure h ) : " + e );
		}
	}

	public void update ( ModuleIUT m )
	{
		String req = "UPDATE Module SET libLong = ?, libCourt = ?, Id_TypeModule = ?, Id_Semestre = ? WHERE Id_Module = ?";
		try
		{
			ps = co.prepareStatement ( req );
			ps.setString ( 1, m.getLibLong    ( ) );
			ps.setString ( 2, m.getLibCourt   ( ) );
			ps.setString ( 3, m.getTypeModule ( ) );
			ps.setInt    ( 4, m.getSemestre   ( ).getIdSemestre ( ) );
			ps.setString ( 5, m.getCode       ( ) );
			ps.executeUpdate ( );

			ps.close ( );
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur update ( Module m ) : " + e);
		}
	}

	public void update ( Intervenant i )
	{
		String req = "UPDATE Intervenant SET nom = ?, prenom = ?, hService = ?, hMax = ?, Id_Contrat = ? WHERE Id_Intervenant = ?";
		try
		{
			ps = co.prepareStatement ( req );
			ps.setString ( 1, i.getNom          ( ) );
			ps.setString ( 2, i.getPrenom       ( ) );
			ps.setInt    ( 3, i.getheureService ( ) );
			ps.setInt    ( 4, i.getHeureMaximum ( ) );
			ps.setInt    ( 5, i.getContrat      ( ).getId ( ) );
			ps.setInt    ( 6, i.getId           ( ) );
			ps.executeUpdate ( );

			ps.close ( );
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur update ( Intervenant i ) : " + e);
		}
	}

	public void update ( Intervient e )
	{
		String req = "UPDATE Intervient SET nbSemaine = ?, nbGroupe = ?, nbHeure = ?, commentaire = ? WHERE Id_Intervenant = ? AND nomHeure = ? AND Id_ModuleIUT = ?";
		try
		{
			ps = co.prepareStatement ( req );
			ps.setInt    ( 1, e.getNbSemaine   ( )             );
			ps.setInt    ( 2, e.getNbGroupe    ( )             );
			ps.setInt    ( 3, e.getNbHeure     ( )             );
			ps.setString ( 4, e.getCommentaire ( )             );
			ps.setInt    ( 5, e.getIntervenant ( ).getId   ( ) );
			ps.setString ( 6, e.getHeure       ( ).getNom  ( ) );
			ps.setString ( 7, e.getModule      ( ).getCode ( ) );
			ps.executeUpdate ( );

			ps.close ( );
		}
		catch ( SQLException x )
		{
			System.out.println ( "Erreur update ( Intervient e ) : " + x );
		}
	}

	public void update ( Horaire h )
	{
		String req = "UPDATE Horaire SET nbSemaine = ?, nbGroupe = ?, nbHeure = ?, commentaire = ? WHERE Id_Intervenant = ? AND nomHeure = ? AND Id_ModuleIUT = ?";
		try
		{
			ps = co.prepareStatement ( req );
			ps.setString ( 1, h.getHeure     ( ).getNom  ( ) );
			ps.setString ( 2, h.getModule    ( ).getCode ( ) );
			ps.setInt    ( 3, h.getNbHeurePN ( )             );
			ps.setInt    ( 4, h.getNbHeure   ( )             );
			ps.setInt    ( 5, h.getNbSemaine ( )             );
			ps.executeUpdate ( );

			ps.close ( );
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur update ( Horaire h ) : " + e );
		}
	}

}
