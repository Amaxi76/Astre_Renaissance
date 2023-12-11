package astre.modele.bd;

/** Page d'insertion de la base de données
  * @author : Matéo Sa, Alizéa Lebaron
  * @version : 1.0 - 11/12/2023
  * @date : 06/12/2023
  */

import java.sql.*;
import java.util.ArrayList;
import astre.modele.*;

public class BD
{
	Connection co;
	PreparedStatement ps;
	private static BD dbInstance;
	
	private BD ( )
	{
		try 
		{
			Class.forName("org.postgresql.Driver");
			co = DriverManager.getConnection ( "jdbc:postgresql://woody/sm220306", "sm220306", "mateo2705" );
			
		} 
		catch ( ClassNotFoundException e ) 
		{
			System.out.println(e);
		}
		catch ( SQLException e )
		{
			System.out.println ( e );
		}
	}
	
	public static BD getInstance( )
	{
		if ( dbInstance == null ) 
		{
			dbInstance = new BD( );
		}
		return dbInstance;
	}
	
	
	//trucs qui renvoie les listes
	public ArrayList<Semestre> getSemestres ( )
	{
		ArrayList<Semestre> lst = new ArrayList<Semestre> ( );
		
		try
		{
			Statement st = co.createStatement ( );
			ResultSet rs = st.executeQuery ( "SELECT * FROM Semestre" );
			while ( rs.next ( ) )
			{
				lst.add ( new Semestre ( rs.getInt(1), rs.getInt(2), rs.getInt(3),rs.getInt(4), rs.getInt(5) ) );
			}
		} catch ( SQLException e ) {
			System.out.println ( e );
		}
		
		return lst;
	}
	
	public ArrayList<Contrat> getContrats( )
	{
		ArrayList<Contrat> lst = new ArrayList<Contrat>( );
		
		try
		{
			Statement st = co.createStatement( );
			ResultSet rs = st.executeQuery ( "SELECT * FROM Contrat" );
			while ( rs.next( ) )
			{
				lst.add( new Contrat( rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getDouble(5) ) );
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		
		return lst;
	}
	
	public ArrayList<Heure> getHeures()
	{
		ArrayList<Heure> lst = new ArrayList<Heure>();
		
		try {
			Statement st = co.createStatement();
			ResultSet rs = st.executeQuery("select * from Heure");
			while (rs.next()) {
				lst.add( new Heure( rs.getString(1), rs.getDouble(2) ) );
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		
		return lst;
	}
	
	// public ArrayList<TypeModule> getTypeModules()
	// {
		
	// }
	
	/*public ArrayList<Module> getModule()
	{
		
	}*/
	
	public ArrayList<Intervenant> getIntervenants()
	{
		ArrayList<Intervenant> lst = new ArrayList<Intervenant>();
		
		try {
			Statement st = co.createStatement();
			ResultSet rs = st.executeQuery("select * from Intervenant");
			while (rs.next()) {
				lst.add(new Intervenant(rs.getString(2), rs.getString(3), getContrat(rs.getInt(6)), rs.getInt(4), rs.getInt(5) ) );
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		
		return lst;
	}
	
	/*public ArrayList<Enseigne> getEnseignes()
	{
		
	}*/
	
	/*public ArrayList<Horaire> getHoraires()
	{
		
	}*/
	
	//trucs qui recup 1 machins
	public Semestre getSemestre ( int c )
	{
		Semestre semestre = null;
		
		try {
			Statement st = co.createStatement();
			ResultSet rs = st.executeQuery("select * from Semestre where Id_Semestre = " + c );
			while (rs.next()) {
				semestre = new Semestre(  rs.getInt(1), rs.getInt(2), rs.getInt(3),rs.getInt(4), rs.getInt(5)  );
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		
		return semestre;
	}

	public Contrat getContrat(int c)
	{
		Contrat contrat = null;
		
		try
		{
			Statement st = co.createStatement();
			ResultSet rs = st.executeQuery("select * from Contrat where Id_Contrat = " + c );
			while (rs.next())
			{
				contrat = new Contrat( rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getDouble(5) );
			}
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		
		return contrat;
	}



	public static void main ( String[] args ) 
	{
		BD bd = BD.getInstance ( );

		ArrayList<Intervenant> test = bd.getIntervenants();

		for(Intervenant i : test)
		{
			System.out.println(i.toString() );
			System.out.println( i.getStatut().toString() );
		}

	}
}
