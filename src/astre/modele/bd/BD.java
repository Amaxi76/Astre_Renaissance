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
