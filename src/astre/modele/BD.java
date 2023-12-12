package astre.modele;

/** Page de gestion de la base de données
  * @author : Matéo Sa, Alizéa Lebaron
  * @version : 1.0 - 11/12/2023
  * @date : 06/12/2023
  */

import java.sql.*;
import java.util.ArrayList;
import astre.modele.*;
import astre.modele.elements.*;
import astre.modele.elements.Module;//import explicite pour pas confondre avec java.lang.Module

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
			System.out.println ( e );
		}
		catch ( SQLException e )
		{
			System.out.println ( e );
		}
	}
	
	public static BD getInstance ( )
	{
		return dbInstance != null ? dbInstance : new BD ( );
	}
	
	
	/*---------------------------------------*/
	/*            RECUP GENERALE             */
	/*---------------------------------------*/

	public ArrayList<Semestre> getSemestres ( )
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
			System.out.println ( e );
		}
		
		return lst;
	}
	
	public ArrayList<Contrat> getContrats ( )
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
			System.out.println ( e );
		}
		
		return lst;
	}
	
	public ArrayList<Heure> getHeures ( )
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
			System.out.println ( e );
		}
		
		return lst;
	}
	
	// public ArrayList<TypeModule> getTypeModules()
	// {
		
	// }
	
	/*public ArrayList<Module> getModule()
	{
		
	}*/
	
	public ArrayList<Intervenant> getIntervenants ( )
	{
		ArrayList<Intervenant> lst = new ArrayList<Intervenant> ( );
		
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
			System.out.println ( e );
		}
		
		return lst;
	}
	
	/*public ArrayList<Enseigne> getEnseignes()
	{
		
	}*/
	
	/*public ArrayList<Horaire> getHoraires()
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
			System.out.println ( e );
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
			System.out.println ( e );
		}
		
		return contrat;
	}


	/*---------------------------------------*/
	/*              RECUP TABLO              */
	/*---------------------------------------*/

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
			System.out.println ( e );
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
			System.out.println ( e );
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
			System.out.println ( e );
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
			System.out.println ( e );
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
			System.out.println ( e );
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
			System.out.println ( e );
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
			System.out.println ( e );
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
			System.out.println ( e );
		}
	}

	public void insert ( Intervenant i, Heure h, Module m, int nbSemaine, int nbGroupe, int nbHeure, String commentaire )
	{
		String req = "INSERT INTO Enseigne VALUES(?,?,?,?,?,?,?)";
		try
		{
			ps = co.prepareStatement ( req );
			ps.setInt    ( 1, i.getId   ( ) );
			ps.setString ( 2, h.getNom  ( ) );
			ps.setString ( 3, m.getCode ( ) );
			ps.setInt    ( 4, nbSemaine     );
			ps.setInt    ( 5, nbGroupe      );
			ps.setInt    ( 6, nbHeure       );
			ps.setString ( 7, commentaire   );
			ps.executeUpdate ( );
		}
		catch ( SQLException e )
		{
			System.out.println ( e );
		}
	}

	public void insert ( Heure h, Module m, int nbHeurePN, int nbHeure, int nbSemaine )
	{
		String req = "INSERT INTO Enseigne VALUES(?,?,?,?,?,?,?)";
		try
		{
			ps = co.prepareStatement ( req );
			ps.setString ( 1, h.getNom  ( ) );
			ps.setString ( 2, m.getCode ( ) );
			ps.setInt    ( 3, nbHeurePN     );
			ps.setInt    ( 4, nbHeure       );
			ps.setInt    ( 5, nbSemaine     );
			ps.executeUpdate ( );
		}
		catch ( SQLException e )
		{
			System.out.println ( e );
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
			System.out.println ( e );
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
			System.out.println ( e );
		}
	}

	public void delete ( Module m )
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
			System.out.println ( e );
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
			System.out.println ( e );
		}
	}
	
	public void delete ( Intervenant i, Heure h, Module m )
	{
		String req = "DELETE FROM Enseigne where Id_Intervenant = ? AND nomHeure = ? AND Id_ModuleIUT = ?";
		try
		{
			ps = co.prepareStatement ( req );
			ps.setInt    ( 1, i.getId   ( ) );
			ps.setString ( 2, h.getNom  ( ) );
			ps.setString ( 3, m.getCode ( ) );
			ps.executeUpdate ( );
		}
		catch ( SQLException e )
		{
			System.out.println ( e );
		}
	}

	public void delete ( Heure h, Module m )
	{
		String req = "DELETE FROM Horaire where nomHeure = ? AND Id_ModuleIUT = ?";
		try
		{
			ps = co.prepareStatement ( req );
			ps.setString ( 1, h.getNom  ( ) );
			ps.setString ( 2, m.getCode ( ) );
			ps.executeUpdate ( );
		}
		catch ( SQLException e )
		{
			System.out.println ( e );
		}
	}

	

	/*---------------------------------------*/
	/*                UPDATE                 */
	/*---------------------------------------*/



	
	/*---------------------------------------*/
	/*                MAIN TEST              */
	/*---------------------------------------*/
	public static void main ( String[] args ) 
	{
		BD bd = BD.getInstance ( );

		ArrayList<Intervenant> test = bd.getIntervenants();

		for(Intervenant i : test)
		{
			System.out.println(i.toString() );
			System.out.println( i.getContrat().toString() );
		}

	}
}
