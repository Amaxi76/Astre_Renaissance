package astre.modele.bd;

/** Page d'insertion de la base de données
  * @author : Matéo Sa
  * @version : 1.0 - 11/12/2023
  * @date : 06/12/2023
  */

import java.sql.*;

public class BD
{
	Connection co;
	PreparedStatement ps;
	private static BD dbInstance;
	
	private BD()
	{
		try {
			Class.forName("org.postgresql.Driver");
			co = DriverManager.getConnection("jdbc:postgresql://woody/sm220306",                           "sm220306", "mateo2705");
			
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
	
	public static BD getInstance()
	{
		if ( dbInstance == null ) {
			dbInstance = new BD();
		}
		return dbInstance;
	}
	
	public ArrayList<Intervenant> getIntervenants()
	{
		ArrayList<Intervenant> lst = new ArrayList<Intervenant>();
		
		try {
			Statement st = co.createStatement();
			ResultSet rs = st.executeQuery("select * from Intervenant");
			while (rs.next()) {
				lst.add(new Intervenant(rs.getString(1), rs.getString(2), rs.getString(3),rs.getDouble(4), rs.getInt(5), rs.getInt(6) ) );
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		
		return lst;
	}
	
}
