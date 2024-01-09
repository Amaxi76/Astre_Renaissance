package astre.modele;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;

/** Page de gestion de la base de données
  * @author : Matéo Sa, Alizéa Lebaron, Maximilien Lesterlin, Maxime Lemoine et Clémentin Ly
  * @version : 1.0 - 18/12/2023
  * @date : 06/12/2023
  */

//TODO: Penser à fermer le rs et st
//TODO: Refactoriser la métode UPDATE
//TODO: Trier les méthodes utilisées ou non
//TODO: Pour toutes les fonctions somme qui retourne un int faire une fonction générale qui a deux paramètre : la fonction et les paramètres

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JOptionPane;

import astre.modele.elements.*;
import astre.modele.outils.ModeleTableau; // pour les constantes
import astre.modele.outils.Utilitaire;

public class BD
{
	private static final String JDBC      = "org.postgresql.Driver";
	//private static final String LOGIN     = "sm220306";
	//private static final String PASSWORD  = /* mot de passe ---> */																																					"mateo2705";
	//private static final String URL_WOODY = "jdbc:postgresql://woody/" + LOGIN;
	//private static final String URL_LOCAL = "jdbc:postgresql://localhost:7777/" + LOGIN;

	private String login;
	private String password;

	private String urlWoody;
	private String urlLocal;

	private static BD dbInstance;

	Connection co;
	PreparedStatement ps;

	private BD ( )
	{
		setIndentifiant ( );
		
		String erreurConnexion = "";

		try
		{
			Class.forName ( JDBC );
			co = DriverManager.getConnection ( this.urlWoody , this.login, this.password );
		}
		catch ( ClassNotFoundException | SQLException e1 )
		{
			erreurConnexion += "Erreur de connexion à la base de données " + this.urlWoody + " : " + e1 + "\n";

			try
			{
				Class.forName ( "org.postgresql.Driver" );
				co = DriverManager.getConnection( this.urlLocal, this.login, this.password );
			}
			catch ( ClassNotFoundException | SQLException e2 )
			{
				erreurConnexion += "Erreur de connexion à la base de données " + this.urlLocal + " : " + e2 ;
				JOptionPane.showMessageDialog ( null, erreurConnexion, "Erreur de connexion", JOptionPane.ERROR_MESSAGE ); //de l'ihm glissé ici déso
			}
		}
	}

	public static BD getInstance ( )
	{
		return dbInstance != null ? dbInstance : new BD ( );
	}

	/* Méthode permettant de recupérer les identifiants
	 */
	private void setIndentifiant ( )
	{
		try
		{
			Scanner sc;
			try
			{
				sc = new Scanner ( new FileInputStream ( "./data/identifiant/identifiant.txt" ) );
			}
			catch ( Exception e )
			{
				sc = new Scanner ( new FileInputStream ( "../data/identifiant/identifiant.txt" ) );
			}

			this.login    = sc.nextLine ( );
			this.password = sc.nextLine ( );

			sc.close ( );
		}
		catch ( Exception e ){ e.printStackTrace ( ); }

		this.urlWoody = "jdbc:postgresql://woody/"          + this.login;
		this.urlLocal = "jdbc:postgresql://localhost:7777/" + this.login;
	}

	public boolean estGenere ( )
	{
		String REQUETE = "SELECT EXISTS ( SELECT 1 FROM information_schema.tables WHERE table_name = 'annee' )";
		boolean estCree = true;

		try
		{
			Statement st = co.createStatement ( );
			ResultSet rs = st.executeQuery    ( REQUETE );

			rs.next ( );

			estCree = rs.getBoolean(1);

			rs.close ( );
			st.close ( );

			//Utilisation de variable pour refermer correctement les statement et resultat
			return estCree;
		}
		catch ( SQLException e )
		{
			System.out.println ( e );
		}
		
		return estCree;
	}

	/* Script de création */

	public void executeScript (String cheminScript) 
	{
		String requete = "";

        try ( BufferedReader reader = new BufferedReader ( new FileReader ( cheminScript ) ) ) 
		{
			Statement st = co.createStatement();
			//String requete = "";
			String ligne;

			// Lire le script SQL ligne par ligne
			while ((ligne = reader.readLine()) != null) 
			{
				requete += ligne + "\n";

				// Si la ligne se termine par un point-virgule, exécuter la requête
				if (ligne.trim().endsWith(";")) 
				{
					// Affichez la requête avant de l'exécuter
					// System.out.println(requete); //debug

					// Exécuter la requête
					st.executeUpdate(requete);



					// Réinitialiser la variable requete
					requete = "";
				}
			}


            // Exécuter le script SQL
            //st.executeUpdate ( script.toString ( ) );
			co.commit();

            System.out.println ( "Script SQL exécuté avec succès." );
        } 
		catch ( Exception e ) 
		{
			//System.out.println(requete); // Debug
            e.printStackTrace ( );
        }
    }

	/*---------------------------------------*/
	/*            RECUP GENERALE             */
	/*---------------------------------------*/

	//TODO: mettre les factories pour tous les types
	/** Méthode générique pour la récupération de table
	 * Exemple d'utilisation : ArrayList<Semestre> ensS= new ArrayList<> (getTable ( "Semestre", Semestre.class ) ) ;
	 */

	public <T> List<T> getTable ( Class<T> type )
	{
		ArrayList<T> lst = new ArrayList<>();

		try
		{
			Statement st = co.createStatement ( );
			ResultSet rs = st.executeQuery ( "SELECT * FROM " + type.getSimpleName ( ) );

			while ( rs.next ( ) )
			{
				try
				{
					if ( type.equals ( Semestre.class )     )
						lst.add ( type.cast ( new Semestre ( rs.getInt ( 1 ), rs.getInt ( 2 ), rs.getInt ( 3 ),rs.getInt ( 4 ), rs.getInt ( 5 ) ) ) );

					if ( type.equals ( Contrat.class )      )
						lst.add ( type.cast ( Contrat.creation ( rs.getInt ( 1 ), rs.getString ( 2 ), rs.getInt ( 3 ), rs.getInt ( 4 ), rs.getDouble ( 5 ) ) ) );

					if ( type.equals ( Heure.class )        )
						lst.add ( type.cast ( Heure.creation ( rs.getInt ( 1 ), rs.getString ( 2 ), rs.getDouble ( 3 ) ) ) );

					if ( type.equals ( Intervenant.class )  )
						lst.add ( type.cast ( Intervenant.creation ( rs.getInt ( 1 ), rs.getString ( 2 ), rs.getString ( 3 ), getContrat ( rs.getInt ( 6 ) ), rs.getInt ( 4 ), rs.getInt ( 5 ) ) ) );

					if ( type.equals ( ModuleIUT.class )  )
						lst.add ( type.cast ( ModuleIUT.creation ( getSemestre ( rs.getInt ( 6 ) ), rs.getString ( 4 ) , rs.getString ( 1 ), rs.getString ( 2 ), rs.getString ( 3 ), rs.getBoolean ( 5 ) ) ) );

					if ( type.equals ( Horaire.class )  )
						lst.add ( type.cast ( new Horaire ( getHeure ( rs.getInt ( 1 ) ), getModule (rs.getString ( 2 ) ), rs.getInt ( 3 ), rs.getInt ( 5 ), rs.getInt ( 4 ) ) ) );

					if ( type.equals ( Intervient.class )  )
						lst.add ( type.cast ( new Intervient ( getIntervenant ( rs.getInt ( 1 ) ), getHeure ( rs.getInt ( 2 ) ) , getModule (rs.getString ( 3 ) ), rs.getInt ( 4 ), rs.getInt ( 5 ), rs.getInt ( 6 ), rs.getString(7) ) ) );
						// Intervenant intervenant, Heure heure, ModuleIUT module, int nbSemaine, int nbGroupe, int nbHeure, String commentaire

				// Ajouter d'autres conditions pour d'autres classes si nécessaire
				}
				catch ( Exception e )
				{
					e.printStackTrace ( );
				}

			}

		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur getTable : " + e );
		}

		return lst;
	}

	public List<Semestre>    getSemestres    ( ) { return this.getTable ( Semestre   .class ); }
	public List<Intervenant> getIntervenants ( ) { return this.getTable ( Intervenant.class ); }
	public List<Contrat>     getContrats     ( ) { return this.getTable ( Contrat    .class ); }
	public List<Heure>       getHeures       ( ) { return this.getTable ( Heure      .class ); }
	public List<Intervient>  getIntervients  ( ) { return this.getTable ( Intervient .class ); }
	public List<ModuleIUT>   getModuleIUTs   ( ) { return this.getTable ( ModuleIUT  .class ); }
	public List<Horaire>     getHoraires     ( ) { return this.getTable ( Horaire    .class ); }


	public List<ModuleIUT> getModules ( int numeroSemestre )
	{

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

				Semestre  semestre  = new Semestre   ( rs.getInt ( iS++ ), rs.getInt ( iS++ ), rs.getInt ( iS++ ), rs.getInt ( iS++ ), rs.getInt ( iS ) );
				ModuleIUT moduleIUT = ModuleIUT.creation ( semestre, rs.getString ( 4 ), rs.getString ( iM++ ), rs.getString ( iM++ ), rs.getString ( iM ), rs.getBoolean ( 5 ) );

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

	public Map<Heure, Integer> getHeures ( String code, char typeHeure ) //typeHeure = 'P' ou 'R'
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
					if ( h.getNom ( ).equals ( rs.getString ( 1 ) ) )
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

	public List<Horaire> getHoraires ( String module )
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
				Horaire h = new Horaire ( getHeure ( rs.getInt ( 1 ) ), getModule ( rs.getString ( 2 ) ), rs.getInt ( 3 ), rs.getInt ( 4 ), rs.getInt ( 5 ) );
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

	public List<String> getEnsAnnee ( )
	{
		ArrayList<String> ensAnnee = new ArrayList <String> ( );

		String REQUETE = "SELECT nom FROM Annee";

		try
		{
			Statement         st = co.createStatement  (         );
			PreparedStatement ps = co.prepareStatement ( REQUETE );

			ResultSet rs = ps.executeQuery ( );

			while ( rs.next ( ) )
			{
				ensAnnee.add ( rs.getString ( 1 ) );
			} 

			rs.close ( );
			ps.close ( );
			st.close ( );
		}
		catch ( SQLException e )
		{
			System.out.println ( "getHoraire ( )" +  e );
		}

		return ensAnnee;
	}

	public String getAnneeAct ( )
	{
		String REQUETE = "SELECT NOM FROM Annee WHERE actuelle = true";
		String nom = "";

		try
		{
			Statement st = co.createStatement ( );
			ResultSet rs = st.executeQuery    ( REQUETE );

			rs.next ( );

			nom = rs.getString(1);
		}
		catch ( SQLException e )
		{
			System.out.println ( "getAnnee ( )" +  e );
			nom = "Aucune année en cours";
		}

		return nom;
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

	//TODO: Supprimer car inutile ?

	public Intervient getIntervient ( int c )
	{
		Intervient inter = null;

		try
		{
			Statement st = co.createStatement ( );
			ResultSet rs = st.executeQuery ( "select * from Intervient where Id_intervenant = " + c );
			while ( rs.next ( ) )
			{
				inter = new Intervient ( getIntervenant( rs.getInt(1)), getHeure( rs.getInt(2) ), getModule ( rs.getString(3) ), rs.getInt(4), rs.getInt(5), rs.getInt(6), ""  );
			}

			rs.close ( );
			st.close ( );
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur getIntervient(int c) : " + e );
		}

		return inter;
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
					System.out.println ( "Erreur création de contrat : " + e );
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

	public double getInterventionIntervenant ( int inter, int semes )
	{
		double result = 0;
		double ligne;

		try
		{
			Statement st = co.createStatement ( );
			ResultSet rs = st.executeQuery ( "SELECT nbSemaine, nbGroupe, nbHeure, i.id_intervenant, Id_Heure " +
					                         "FROM   Intervenant i JOIN Intervient t  ON i.Id_Intervenant  = t.Id_Intervenant " +
					                         "                     JOIN ModuleIUT m   ON m.Code_ModuleIUT = t.Code_ModuleIUT " +
				                             "Where  Id_Semestre      = "+ semes +" AND " +
					                         "       i.Id_intervenant = " + inter );
			while ( rs.next( ) )
			{
				ligne = 0;
				ligne += rs.getInt ( 1 ) * rs.getInt ( 2 ) * rs.getInt(3) * getHeure(rs.getInt(5)).getCoefTd();

				if ( getHeure ( rs.getInt ( 5 ) ).getNom ( ).equals ( "TP" ) )
				{
					ligne *= getIntervenant ( rs.getInt (4 ) ).getContrat ( ).getRatioTP ( );
				}

				result += ligne;
			}
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur getContrat(int c) : " + e );
		}

		return result;
	}

	//meme méthode qu'au dessus mais sans prendre en compte les coeff tp
	public double getInterventionIntervenantTheo ( int inter, int semes )
	{
		double result = 0;
		double ligne;

		try
		{
			Statement st = co.createStatement ( );
			ResultSet rs = st.executeQuery ( "SELECT nbSemaine, nbGroupe, nbHeure, i.id_intervenant, Id_Heure " +
					                         "FROM   Intervenant i JOIN Intervient t  ON i.Id_Intervenant  = t.Id_Intervenant " +
					                         "                     JOIN ModuleIUT m   ON m.Code_ModuleIUT = t.Code_ModuleIUT " +
				                             "Where  Id_Semestre      = "+ semes +" AND " +
					                         "       i.Id_intervenant = " + inter );
			while ( rs.next ( ) )
			{
				ligne = 0;
				ligne += rs.getInt ( 1 ) * rs.getInt ( 2 ) * rs.getInt ( 3 ) * getHeure ( rs.getInt ( 5 ) ).getCoefTd ( );

				result += ligne;
			}
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur getContrat(int c) : " + e );
		}

		return result;
	}

	public Intervenant getIntervenant ( int i )
	{
		Intervenant intervenant = null;

		try
		{
			Statement st = co.createStatement ( );
			ResultSet rs = st.executeQuery ( "select * from Intervenant where Id_Intervenant = " + i );
			while ( rs.next ( ) )
			{
				intervenant = Intervenant.creation ( rs.getInt ( 1 ), rs.getString ( 2 ), rs.getString ( 3 ), getContrat ( rs.getInt ( 6 ) ), rs.getInt ( 4 ), rs.getInt ( 5 ) );
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
				try
				{
					heure = Heure.creation ( rs.getInt ( 1 ), rs.getString ( 2 ), rs.getDouble ( 3 ) );
				}
				catch ( Exception e )
				{
					e.printStackTrace ( );
				}

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
				try
				{
					heure = Heure.creation ( rs.getInt ( 1 ), rs.getString ( 2 ), rs.getDouble ( 3 ) );
				}
				catch ( Exception e )
				{
					e.printStackTrace ( );
				}

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
				module = ModuleIUT.creation ( getSemestre ( rs.getInt ( 6 ) ), rs.getString ( 4 ), rs.getString ( 1 ), rs.getString ( 2 ), rs.getString ( 3 ),rs.getBoolean ( 5 ) );
			}
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur getModule(int m) : " + e );
		}

		return module;
	}

	public int getNBHeureParModule ( String code, int Id_Inter, int Id_Heure )
	{
		int somme = 0;

		try
		{
			Statement st = co.createStatement ( );
			ResultSet rs = st.executeQuery ( "SELECT * FROM f_selectNBHeureParModule('" + code + "'," + Id_Inter + "," + Id_Heure + ")" );

			rs.next ( );

			somme = rs.getInt ( 1 );
		}
		catch (Exception e)
		{
			System.out.println ( "Erreur  getNBHeureParModule (String code, int Id_Inter, int Id_Heure) : " + e );
		}

		return somme;
	}

	// Utilisée dans panelRepartition.java
	public int getNBHeureEQTD ( String code, String nomHeure )
	{
		int somme = 0;

		try
		{
			Statement st = co.createStatement ( );
			ResultSet rs = st.executeQuery ( "SELECT * FROM f_selectNBHeureEQTD('" + code + "','" + nomHeure + "')" );

			rs.next ( );

			somme = rs.getInt ( 1 );
		}
		catch (Exception e)
		{
			System.out.println ( "getNBHeureEQTD (String code, String nomHeure) : " + e );
		}

		return somme;
	}

	// Utilisée dans générateur.java
	public int getNBHeureParSemestre ( int Id_Semestre, int Id_Intervenant )
	{
		int somme = 0;

		try
		{
			Statement st = co.createStatement ( );
			ResultSet rs = st.executeQuery ( "SELECT * FROM f_selectNBHeureParSemestre(" + Id_Semestre + "," + Id_Intervenant + ")" );

			rs.next ( );

			somme = rs.getInt ( 1 );
		}
		catch (Exception e)
		{
			System.out.println ( "Erreur getNBHeureParSemestre (int Id_Semestre, int Id_Intervenant) : " + e );
		}

		return somme;
	}

	// Utilisée dans générateur.java
	public int getNBHeurePNParModule ( String code, int Id_Heure )
	{
		int somme = 0;

		try
		{
			Statement st = co.createStatement ( );
			ResultSet rs = st.executeQuery ( "SELECT * FROM f_selectNBHeurePNParModule('" + code + "'," + Id_Heure + ")" );

			rs.next ( );

			somme = rs.getInt ( 1 );
		}
		catch (Exception e)
		{
			System.out.println ( "Erreur getNBHeurePNParModule (String code, int Id_Heure) : " + e );
		}

		return somme;
	}

	// Utilisée dans générateur.java
	public int getNBHeureRepParModule ( String code, int Id_Heure )
	{
		int somme = 0;

		try
		{
			Statement st = co.createStatement ( );
			ResultSet rs = st.executeQuery ( "SELECT * FROM f_selectNBHeureRepParModule('" + code + "'," + Id_Heure + ")" );

			rs.next ( );

			somme = rs.getInt ( 1 );
		}
		catch (Exception e)
		{
			System.out.println ( "Erreur getNBHeureRepParModule (String code, int Id_Heure) : " + e );
		}

		return somme;
	}

	//Utilisée dans générateur.java
	public int getHeureParSemestreImpair ( int id )
	{
		int somme = 0;

		try
		{
			Statement st = co.createStatement ( );
			ResultSet rs = st.executeQuery ( "SELECT * FROM f_selectNBHeureParSemestreImpair(" + id + ")" );

			rs.next ( );

			somme = rs.getInt ( 1 );
		}
		catch (Exception e)
		{
			System.out.println ( "Erreur getHeureParSemestreImpair ( int id ) : " + e );
		}

		return somme;
	}

	//Utilisée dans générateur.java
	public int getHeureParSemestrePair ( int id )
	{
		int somme = 0;

		try
		{
			Statement st = co.createStatement ( );
			ResultSet rs = st.executeQuery ( "SELECT * FROM f_selectNBHeureParSemestrePair(" + id + ")" );

			rs.next ( );

			somme = rs.getInt ( 1 );
		}
		catch (Exception e)
		{
			System.out.println ( "Erreur getHeureParSemestrePair ( int id ) : " + e );
		}

		return somme;
	}

	//Utilisée dans générateur.java
	public int getHeureParSemestreTotal ( int id )
	{
		int somme = 0;

		try
		{
			Statement st = co.createStatement ( );
			ResultSet rs = st.executeQuery ( "SELECT * FROM f_selectNBHeureParSemestreTot(" + id + ")" );

			rs.next ( );

			somme = rs.getInt ( 1 );
		}
		catch (Exception e)
		{
			System.out.println ( "Erreur getHeureParSemestreTotal ( int id ) : " + e );
		}

		return somme;
	}

	//Utilisée dans générateur.java
	public int getTotalHeureParInter ( int idInter, int idHeure )
	{
		int somme = 0;

		try
		{
			Statement st = co.createStatement ( );
			ResultSet rs = st.executeQuery ( "SELECT * FROM f_selectTotHeureInter(" + idInter + "," + idHeure + ")" );

			rs.next ( );

			somme = rs.getInt ( 1 );
		}
		catch (Exception e)
		{
			System.out.println ( "Erreur getTotalHeureParInter ( int idInter, int idHeure ) : " + e );
		}

		return somme;
	}

	public int getNbTuple ( String table )
	{
		int nbTuple = 0;

		try
		{
			Statement st = co.createStatement ( );
			ResultSet rs = st.executeQuery ( "select count(*) from " + table );
			while ( rs.next ( ) )
			{
				nbTuple = rs.getInt ( 1 );
			}
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur getNbTuple ( String table ) : " + e );
		}

		return nbTuple;
	}

	/*---------------------------------------*/
	/*            RECUP TABLEAU              */
	/*---------------------------------------*/

	public Object[][] getTableau ( Class<?> type )
	{
		List<?> lst = this.getTable ( type );

		int nbAttributs = type.getDeclaredFields ( ).length;
		Object[][] object = new Object[ lst.size ( )][nbAttributs+1];

		for ( int lig = 0; lig < object.length; lig ++ )
		{
			object[lig][0] = ModeleTableau.DEFAUT;
			Object[] tmp = Utilitaire.toArray ( lst.get ( lig ) );

			for ( int col = 0 ; col < nbAttributs; col ++ )
			{
				object[lig][col+1] = tmp[col];
			}
		}

		return object;
	}

	// Utilisé dans src\astre\vue\previsionnel\module\PanelAffectation.java
	//TODO: regarder pour l'enlever
	//TODO: Utiliser la fonction getTableauParticulier 
	public Object[][] getIntervientsTableau ( String module )
	{
		int nbIntervients = 0;

		try
		{
			Statement st = co.createStatement ( );
			ResultSet rs = st.executeQuery ( "select count(*) from Intervient where code_moduleIUT = '" + module + "'");
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
			ResultSet rs = st.executeQuery ( "select Id_Intervenant, Id_Heure, nbSemaine, nbGroupe, nbHeure, commentaire from Intervient where code_moduleIUT = '" + module + "'" );
			int cpt = 0;
			while ( rs.next ( ) )
			{
				intervients[cpt][0] = getIntervenant ( rs.getInt ( 1 ) ).getNom ( );//nom
				intervients[cpt][1] = getHeure       ( rs.getInt ( 2 ) ).getNom ( );//heure
				intervients[cpt][2] = rs.getInt      ( 3 );//nbsemaine
				intervients[cpt][3] = rs.getInt      ( 4 );//nbgroupe
				intervients[cpt][4] = rs.getInt      ( 5 );//nbheure
				intervients[cpt][5] = rs.getString   ( 6 );//commentaire

				if ( intervients[cpt][5] == null )
					intervients[cpt][5] = "";

				cpt++;
			}
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur 2 getIntervientsTableau ( ) : " +  e );
		}

	 	return intervients;
	}

	public Object[][] getTableauParticulier ( String nomRecherche )
	{
		//TODO: Mettre des '' sur les string et les enlever là où c'est appeler
		Object[][] tabObjet = null;

		try
		{
			Statement st = co.createStatement ( );
			ResultSet rs = st.executeQuery ( "select * from " + nomRecherche );
			ResultSetMetaData rsmd = rs.getMetaData ( );

			int nbAttributs = rsmd.getColumnCount ( );
			tabObjet = new Object[this.getNbTuple ( nomRecherche )][nbAttributs + 1];

			int cpt = 0;
			while ( rs.next ( ) )
			{
				tabObjet[cpt][0] = ModeleTableau.DEFAUT;
				for ( int i = 1; i < tabObjet[cpt].length; i++ )
				{
					try
					{
						Object valeur = rs.getObject ( i );

						tabObjet[cpt][i] = valeur;
						// CA MARCHE SANS FAIRE CA
						/*switch ( rsmd.getColumnType ( i ) )
						{
							case Types.INTEGER -> ( Integer ) ( valeur );
							case Types.VARCHAR -> ( String  ) ( valeur );
							case Types.DOUBLE  -> ( Double  ) ( valeur );
							case Types.BOOLEAN -> ( Boolean ) ( valeur );
							default-> valeur.toString ( );
						};*/
					}
					catch ( Exception e )
					{
						System.out.println ( "Ptit problème de conversion : getTableauParticulier ( +)" );
					}
				}
				cpt++;
			}
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur getTableauParticulier ( String nomRecherche ) : " + e );
		}

		return tabObjet;
	}

	public ArrayList<String> getHistorique ( )
	{
		ArrayList<String> lst = new ArrayList<>();

		try
		{
			Statement st = co.createStatement ( );
			ResultSet rs = st.executeQuery ( "select * from Historique" );
			while ( rs.next ( ) )
			{
				String date[] = rs.getString ( 2 ).split ( ":" );
				lst.add ( date[0] + ":" + date[1] + "  " + rs.getString ( 3 ) );
			}

			rs.close ( );
			st.close ( );
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur 1 getHistorique () : " + e );
		}

		return lst;
	}



	/*---------------------------------------*/
	/*                INSERT                 */
	/*---------------------------------------*/

	public void insertAnnee ( String nom )
	{
		String req = "INSERT INTO Annee VALUES (?,false)";
		try
		{
			ps = co.prepareStatement ( req );
			ps.setString ( 1, nom );
			ps.executeUpdate ( );

			ps.close ( );
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur insertAnnee ( String nom ) : " + e );
		}
	}

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
		String req = "INSERT INTO Heure (nomHeure, coeffTD) VALUES(?,?)";
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

	public void insert ( ModuleIUT m )
	{
		String req = "INSERT INTO ModuleIUT VALUES(?,?,?,?,?)";
		try
		{
			ps = co.prepareStatement ( req );
			ps.setString  ( 1, m.getCode       ( ) );
			ps.setString  ( 2, m.getLibLong    ( ) );
			ps.setString  ( 3, m.getLibCourt   ( ) );
			ps.setString  ( 4, m.getTypeModule ( ) );
			ps.setBoolean ( 5, m.estValide     ( ) );
			ps.setInt     ( 6, m.getSemestre   ( ).getIdSemestre ( ) );
			ps.executeUpdate ( );

			ps.close ( );
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur insert(moduleIUT m) : " + e );
		}
	}

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
			JOptionPane.showConfirmDialog ( null, "Le contrat " + c.getNom ( ) + " est présent sur une autre table, supprimer toutes ses relations avant de le supprimer", "Suppression impossible", JOptionPane.WARNING_MESSAGE );
		}
	}

	public void delete ( Heure h )
	{
		String req = "DELETE FROM Heure where Id_Heure = ?";

		try
		{
			ps = co.prepareStatement ( req );
			ps.setInt ( 1, h.getId ( ) );
			ps.executeUpdate ( );

			ps.close ( );
		}
		catch ( SQLException e )
		{
			JOptionPane.showConfirmDialog ( null, "L'Heure " + h.getNom ( ) + " est présent sur une autre table, supprimer toutes ses relations avant de le supprimer", "Suppression impossible", JOptionPane.WARNING_MESSAGE );
		}
	}

	public void delete ( ModuleIUT m )
	{
		//System.out.println("paramètre : " + m.getCode());
		String req = "DELETE FROM ModuleIUT where Code_ModuleIUT = '" + m.getCode ( ) + "'";

		try
		{
			System.out.println("try 1");
            ps = co.prepareStatement ( req );
            //ps.setString ( 1, m.getCode ( ) );
			System.out.println("try 2");
            ps.executeUpdate ( );
			System.out.println("try 3");
			ps.close ( );
        }
		catch ( SQLException e )
		{
			System.out.println("catch 1");
			System.out.println(e);
			int test = JOptionPane.showInternalConfirmDialog ( null, "Le module " + m.getCode ( ) + " est présent sur une autre table, voulez-vous supprimer toutes ses relations ?", "Suppression impossible", JOptionPane.YES_NO_OPTION );
			System.out.println("catch 2");
			if(test == 0)
			{
				System.out.println("catch 3");
				deleteAllIntervient ( m.getCode ( ), "Code_ModuleIUT" );
				System.out.println("catch 4");
				deleteAllHoraire    ( m.getCode ( ), "Code_ModuleIUT" );
				System.out.println("catch 5");
				delete ( m );
				System.out.println("catch 6");
			}
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
			int test = JOptionPane.showInternalConfirmDialog ( null, "L'intervenant " + i.getNom ( ) + " est présent sur une autre table, voulez-vous supprimer toutes ses relations ?", "Suppression impossible", JOptionPane.YES_NO_OPTION );

			if(test == 0)
			{
				deleteAllIntervient ( i.getId ( ) + "", "Id_Intervenant" );
				delete ( i );
			}
        }
	}

	public void delete ( Intervient e )
	{
		String req = "DELETE FROM Intervient where Id_Intervenant = ? AND ID_Heure = ? AND code_ModuleIUT = ?";

		try
		{
            ps = co.prepareStatement ( req );
			ps.setInt    ( 1, e.getIntervenant ( ).getId   ( ) );
			ps.setInt    ( 2, e.getHeure       ( ).getId   ( ) );
			ps.setString ( 3, e.getModule      ( ).getCode ( ) );
			ps.executeUpdate ( );

			ps.close ( );
        }
		catch ( SQLException ex )
		{
			System.out.println ( ex );
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
			System.out.println ( e );
        }
	}

	public void deleteAllIntervient ( String nb, String id )
	{
		nb = id.equals ( "Code_ModuleIUT" ) ? "'" + nb + "'" : nb;

		System.out.println("Intervient nb : " + nb);

		String req = "DELETE FROM Intervient where " +  id + " = " + nb;

		try
		{
            ps = co.prepareStatement ( req );
			ps.executeUpdate ( );

			ps.close ( );
        }
		catch ( SQLException ex )
		{
			System.out.println ( ex );
        }
	}

	public void deleteAllHoraire ( String nb, String id )
	{
		nb = id.equals ( "Code_ModuleIUT" ) ? "'" + nb + "'" : nb;

		System.out.println("Intervient nb : " + nb);

		String req = "DELETE FROM Horaire where " +  id + " = " + nb;

		try
		{
            ps = co.prepareStatement ( req );
			ps.executeUpdate ( );

			ps.close ( );
        }
		catch ( SQLException ex )
		{
			System.out.println ( ex );
        }
	}

	//Tentative de généralisation de la méthode delete
	public void delete ( String table, Object[] parametres )
	{
		final String  REQ = "DELETE FROM " + table + " WHERE ";
	}

	/*---------------------------------------*/
	/*                UPDATE                 */
	/*---------------------------------------*/

	// Utilisé dans Astre.java
	public void setAnneeActuelle ( String nom )
	{
		String req  = "UPDATE Annee SET actuelle = true WHERE nom = ?";
		String req1 = "UPDATE Annee SET actuelle = false";

		try
		{
			//Mettre tous à false
			ps = co.prepareStatement ( req1 );
			ps.executeUpdate ( );

			//Mettre le bon à vrai
			ps = co.prepareStatement ( req );
			ps.setString ( 1, nom );

			ps.executeUpdate ( );

			ps.close ( );
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur setAnneeActuelle ( String nom ) : " + e );
		}
	}
	
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
		String req = "UPDATE ModuleIUT SET libLong = ?, libCourt = ?, typeModule = ?, valide = ?, Id_Semestre = ? WHERE code_Moduleiut = ?";
		try
		{
			ps = co.prepareStatement ( req );
			ps.setString  ( 1, m.getLibLong    ( ) );
			ps.setString  ( 2, m.getLibCourt   ( ) );
			ps.setString  ( 3, m.getTypeModule ( ) );
			ps.setBoolean ( 4, m.estValide     ( ) );
			ps.setInt     ( 5, m.getSemestre   ( ).getIdSemestre ( ) );
			ps.setString  ( 6, m.getCode       ( ) );
			ps.executeUpdate ( );

			ps.close ( );
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur update ( ModuleIUT m ) : " + e);
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


	/*---------------------------------------*/
	/*                Autres                 */
	/*---------------------------------------*/

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
}