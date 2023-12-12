package astre.modele;

/** Page de gestion de la base de données
  * @author : Matéo Sa, Alizéa Lebaron, Maximilien Lesterlin et Maxime Lemoine
  * @version : 1.0 - 11/12/2023
  * @date : 06/12/2023
  */

//TODO: Penser à fermer le rs et st
//TODO: remplacer les requêtes complexes du java en un appel à une fonction définie directement dans la BD //en cours

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import astre.modele.*;
//import explicite pour pas confondre avec java.lang.Module

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

	public List<Semestre> getSemestres ( )
	{
		ArrayList<Semestre> lst = new ArrayList<Semestre> ( );
		
		try
		{
			Statement st = co.createStatement ( );
			ResultSet rs = st.executeQuery ( "SELECT * FROM Semestre" );
			while ( rs.next ( ) )
			{
				lst.add ( new Semestre ( rs.getInt ( 1 ), rs.getInt ( 2 ), rs.getInt ( 3 ),rs.getInt ( 4 ), rs.getInt ( 5 ) ) );
			}
		}
		
		catch ( SQLException e )
		
		{
			System.out.println ( "Erreur getSemestre() : " + e );
		}
		
		return lst;
	}
	
	public List<Contrat> getContrats ( )
	{
		ArrayList<Contrat> lst = new ArrayList<Contrat> ( );
		
		try
		{
			Statement st = co.createStatement ( );
			ResultSet rs = st.executeQuery ( "SELECT * FROM Contrat" );
			while ( rs.next( ) )
			{
				lst.add ( new Contrat ( rs.getInt ( 1 ), rs.getString ( 2 ), rs.getInt ( 3 ), rs.getInt ( 4 ), rs.getDouble ( 5 ) ) );
			}
		}
		catch ( SQLException e )
		
		{
			System.out.println ( "Erreur getContrats() : " + e );
		}
		
		return lst;
	}
	
	public List<Heure> getHeures ( )
	{
		ArrayList<Heure> lst = new ArrayList<Heure> ( );
		
		try 
		{
			Statement st = co.createStatement ( );
			ResultSet rs = st.executeQuery ( "select * from Heure" );
			while ( rs.next ( ) ) 
			{
				lst.add ( new Heure( rs.getString(1), rs.getDouble(2) ) );
			}
		} 
		catch ( SQLException e ) 
		{
			System.out.println ( "Erreur getHeure() : " + e );
		}
		
		return lst;
	}
	
	// public List<TypeModule> getTypeModules()
	// {
		
	// }
	
	public List<ModuleIUT> getModules ( int numeroSemestre )
	{
		//FIXME: mettre le numéro du semestre

		String REQUETE = "SELECT *"
		               + "FROM   ModuleIUT m join Semestre s on m.id_semestre = s.id_semestre join typemodule t on t.id_typemodule = m.id_typemodule "
		               + "WHERE  m.id_semestre = ?";
		
		ArrayList<ModuleIUT> ensModules = new ArrayList<> ( );
		
		try 
		{
			Statement         st = co.createStatement (         );
			PreparedStatement ps = co.prepareStatement( REQUETE );

			ps.setInt ( 1, numeroSemestre );

			ResultSet rs = ps.executeQuery ( );

			while ( rs.next ( ) ) 
			{
				int iS = 6;
				int iT = 11;
				int iM = 1;

				Semestre   semestre   = new Semestre   ( rs.getInt ( iS++ ), rs.getInt ( iS++ ), rs.getInt ( iS++ ), rs.getInt ( iS++ ), rs.getInt ( iS ) );
				TypeModule typeModule = new TypeModule ( rs.getInt ( iT++ ), rs.getString ( iT ) );

				Map<Heure, Integer> hmHeuresPn         = this.getHeures ( rs.getString ( 1 ), 'P' );
				Map<Heure, Integer> hmHeuresRepartiees = this.getHeures ( rs.getString ( 1 ), 'R' );
				
				ModuleIUT  moduleIUT =  new ModuleIUT ( semestre, typeModule, rs.getString ( iM++ ), rs.getString ( iM++ ), rs.getString ( iM ), hmHeuresPn, hmHeuresRepartiees );

				ensModules.add ( moduleIUT );
			}

			rs.close ( );
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
		//FIXME: Peut avoir une heure null 

		//TODO: Fonction SQL
		
		HashMap<Heure, Integer> hm = new HashMap<> ( );
		
		Heure heure = null;
		
		String heureS = ( typeHeure == 'P' ) ? "ho.nbHeurePn" : "ho.nbHeure";
		
		String REQUETE = "SELECT he.nomHeure, " + heureS + " "
		               + "FROM   Horaire ho JOIN Heure he    ON ho.nomHeure     = he.nomHeure "
		               + "JOIN ModuleIUT m ON ho.Code_ModuleIUT = m.Code_ModuleIUT "
		               + "WHERE ho.Code_ModuleIUT = ?";

		try
		{
			Statement         st = co.createStatement (         );
			PreparedStatement ps = co.prepareStatement( REQUETE );

			ps.setString ( 1, code   );

			ResultSet rs = ps.executeQuery ( );

			while ( rs.next ( ) )
			{
				for ( Heure h : this.getHeures ( ) )
					if ( h.getNom ( ).equals ( rs.getString ( 1 ) )  )
						heure = h;

				hm.put ( heure, rs.getInt ( 2 ) );
			}
				
			rs.close ( );
			st.close ( );
		}
		catch ( SQLException e ) 
		{
			System.out.println ( e );
		}

		return hm;
	}
	
	public List<Intervenant> getIntervenants ( )
	{
		ArrayList<Intervenant> lst = new ArrayList<> ( );
		
		try 
		{
			Statement st = co.createStatement ( );
			ResultSet rs = st.executeQuery ( "select * from Intervenant" );
			while ( rs.next( ) ) 
			{
				lst.add ( new Intervenant( rs.getInt ( 1 ), rs.getString ( 2 ), rs.getString ( 3 ), getContrat ( rs.getInt ( 6 ) ), rs.getInt ( 4 ), rs.getInt ( 5 ) ) );
			}
		} 
		catch ( SQLException e ) 
		{
			System.out.println ( "Erreur getIntervenant() : " + e );
		}
		
		return lst;
	}
	
	/*public List<Intervient> getIntervient()
	{
		
	}*/
	
	/*public List<Horaire> getHoraires()
	{
		
	}*/
	
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
				contrat = new Contrat ( rs.getInt ( 1 ), rs.getString ( 2 ), rs.getInt ( 3 ), rs.getInt ( 4 ), rs.getDouble ( 5 ) );
			}
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur getContrat(int c) : " + e );
		}
		
		return contrat;
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
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur 2 getModulesTableau() : " + e );
		}
		return modules;
	}

	public Object[][] getIntervenantsTableau ( )
	{
		int nbInervenants = 0;
		
		try
		{
			Statement st = co.createStatement ( );
			ResultSet rs = st.executeQuery( "select count(*) from Intervenant" );
			while ( rs.next ( ) )
			{
				nbInervenants = rs.getInt ( 1 );
			}
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur 1 getIntervenantsTableau() : " + e );
		}
		
		Object[][] intervenants = new Object[nbInervenants][15];

		try
		{
			Statement st = co.createStatement ( );
			ResultSet rs = st.executeQuery ( "select nomContrat, nomInter, prenom, hService, hMax from Intervenant i join Contrat c on i.Id_Contrat = c.Id_Contrat" );
			int cpt = 0;
			while ( rs.next ( ) )
			{
				intervenants[cpt][0] = rs.getString(1);//contrat
				intervenants[cpt][1] = rs.getString(2);//nom
				intervenants[cpt][2] = rs.getString(3);//prenom
				intervenants[cpt][3] = rs.getString(4);//hservice
				intervenants[cpt][4] = rs.getInt(4);//hmax
				intervenants[cpt][5] = rs.getInt(4);//hservice
				intervenants[cpt][6] = "";
				intervenants[cpt][7] = "";
				intervenants[cpt][8] = "";
				intervenants[cpt][9] = "";
				intervenants[cpt][10] = "";
				intervenants[cpt][11] = "";
				intervenants[cpt][12] = "";
				intervenants[cpt][13] = "";
				intervenants[cpt][14] = "";

				cpt++;
			}
		}
		catch ( SQLException e )
		{
			System.out.println ("Erreur 2 getIntervenantsTableau() : " +  e );
		}
		return intervenants;
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
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur insert(Contrat c) : " + e );
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
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur insert(Heure h) : " +  e );
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
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur insert(module m) : " + e );
		}
	}*/
	
	public void insert ( Intervenant i )
	{
		String req = "INSERT INTO Intervenant (nomInter, prenom, hService, hMax, Id_Contrat) VALUES(?,?,?,?,?)";
		try
		{
			ps = co.prepareStatement ( req );
			ps.setString ( 1, i.getNom          ( ) );
			ps.setString ( 2, i.getPrenom       ( ) );
			ps.setInt    ( 3, i.getheureService ( ) );
			ps.setInt    ( 4, i.getHeureMaximum ( ) );
			ps.setInt    ( 5, i.getContrat      ( ).getId ( ) );
			ps.executeUpdate ( );
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur insert(intervenant i) : " + e );
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
		}
		catch ( SQLException x )
		{
			System.out.println ( "Erreur insert(intervient e) : " + x );
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
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur insert(horaire h) : " + e );
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
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur delete(Contrat c) : " + e );
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
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur delete(Heure h) : " + e );
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
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur delete(Module m) : " +  e );
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
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur delete(Intervenant i) : " + e );
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
		}
		catch ( SQLException x )
		{
			System.out.println ( "Erreur delete(Intervient e) : " + x );
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
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur delete(horaire h) : " + e );
		}
	}

	

	/*---------------------------------------*/
	/*                UPDATE                 */
	/*---------------------------------------*/

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
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur update(Semestre s) : " + e );
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
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur update(Contrat c) : " + e );
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
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur update(Heure h) : " + e );
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
			ps.setInt    ( 3, m.getTypeModule ( ).getId         ( ) );
			ps.setInt    ( 4, m.getSemestre   ( ).getIdSemestre ( ) );
			ps.setString ( 5, m.getCode       ( ) );
			ps.executeUpdate ( );
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur update(Module m) : " + e);
		}
	}

	public void update ( Intervenant i )
	{
		String req = "UPDATE Intervenant SET nomInter = ?, prenom = ?, hService = ?, hMax = ?, Id_Contrat = ? WHERE Id_Intervenant = ?";
		try
		{
			ps = co.prepareStatement ( req );
			ps.setString ( 1, i.getNom          ( ) );
			ps.setString ( 2, i.getPrenom       ( ) );
			ps.setInt    ( 3, i.getheureService ( ) );
			ps.setInt    ( 4, i.getHeureMaximum ( ) );
			ps.setInt    ( 5, i.getContrat      ( ).getId ( ) );
			ps.executeUpdate ( );
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur update(Intervenant i) : " + e);
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
		}
		catch ( SQLException x )
		{
			System.out.println ( "Erreur update(Intervient e ) : " + x );
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
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur update(Horaire h) : " + e);
		}
	}
	
	/*---------------------------------------*/
	/*                MAIN TEST              */
	/*---------------------------------------*/
	public static void main ( String[] args ) 
	{
		BD bd = BD.getInstance ( );

		List<Intervenant> test = bd.getIntervenants();

		for(Intervenant i : test)
		{
			System.out.println(i.toString() );
			System.out.println( i.getContrat().toString() );
		}

	}
}
