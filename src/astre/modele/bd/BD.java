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
		try 
		{
			Class.forName("org.postgresql.Driver");
			co = DriverManager.getConnection("jdbc:postgresql://woody/sm220306",                           "sm220306", "mateo2705");
			
		} 
		catch (ClassNotFoundException e) 
		{
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
	
	
	//trucs qui renvoie les listes
	public ArrayList<Semestre> getSemestres()
	{
		
	}
	
	public ArrayList<Contrat> getContrats()
	{
		
	}
	
	public ArrayList<Heure> getHeures()
	{
		
	}
	
	public ArrayList<TypeModule> getTypeModules()
	{
		
	}
	
	public ArrayList<ModuleIUT> getModuleIUT()
	{
		
	}
	
	public ArrayList<Intervenant> getIntervenants()
	{
		ArrayList<Intervenant> lst = new ArrayList<Intervenant>();
		
		try {
			Statement st = co.createStatement();
			ResultSet rs = st.executeQuery("select * from Intervenant");
			while (rs.next()) {
				lst.add(new Intervenant(rs.getString(1), rs.getString(2), getContrat(rs.getInt(7)),rs.getDouble(4), rs.getInt(5), rs.getInt(6) ) );
			}//mettre un contrat genre wesh
		} catch (SQLException e) {
			System.out.println(e);
		}
		
		return lst;
	}
	
	public ArrayList<Enseigne> getEnseignes()
	{
		
	}
	
	public ArrayList<Horaire> getHoraires()
	{
		
	}
	
	//trucs qui recup 1 machins
	public Contrat getContrat(int c)
	{
		Contrat contrat;
		
		try {
			Statement st = co.createStatement();
			ResultSet rs = st.executeQuery("select * from Contrat where Id_Contrat = " + c );
			while (rs.next()) {
				contrat = new Contrat( rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getDouble(4) );
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		
		return contrat;
	}
}
