package astre.modele;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;

/** Page de gestion de la base de données
  * @author : Matéo Sa, Alizéa Lebaron, Maximilien Lesterlin, Maxime Lemoine et Clémentin Ly
  * @version : 19.0 - 17/01/2024
  * @date : 06/12/2023
  */

//TODO: Penser à fermer le rs et st
//TODO: Refactoriser la métode UPDATE
//TODO: Trier les méthodes utilisées ou non
//TODO: Pour toutes les fonctions somme qui retourne un int faire une fonction générale qui a deux paramètre : la fonction et les paramètres
//TODO: Trier par ordre alphabétique les méthodes
//FIXME: Remplacer tous les e.toString() par e.getMessage()

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

import astre.modele.elements.*;
import astre.modele.outils.ModeleTableau; // pour les constantes
import astre.modele.outils.Utilitaire;
import astre.vue.FrameIdentifiant;

public class BD
{
	private String login;
	private String password;

	private String urlWoody;
	private String urlLocal;

	private static BD dbInstance;

	private Connection co;
	private PreparedStatement ps;

	private BD ( )
	{
		this.setIndentifiant ( );
		
		String erreurConnexion = "";

		try
		{
			co = DriverManager.getConnection ( this.urlWoody , this.login, this.password );
		}
		catch ( SQLException e1 )
		{
			erreurConnexion += "Erreur de connexion à la base de données " + this.urlWoody + " : " + e1 + "\n";

			try
			{
				co = DriverManager.getConnection ( this.urlLocal, this.login, this.password );
			}
			catch ( SQLException e2 )
			{
				erreurConnexion += "Erreur de connexion à la base de données " + this.urlLocal + " : " + e2 ;
				JOptionPane.showMessageDialog ( null, erreurConnexion, "Erreur de connexion", JOptionPane.ERROR_MESSAGE );
				new FrameIdentifiant ( );
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
		catch ( Exception e )
		{
			JOptionPane.showMessageDialog ( null, e.getMessage ( ), "Identifiants introuvables", JOptionPane.ERROR_MESSAGE );
		}

		this.urlWoody = "jdbc:postgresql://woody/"          + this.login + "?useUnicode=true&characterEncoding=UTF-8";
		this.urlLocal = "jdbc:postgresql://localhost:7777/" + this.login + "?useUnicode=true&characterEncoding=UTF-8";
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

			estCree = rs.getBoolean ( 1 );

			rs.close ( );
			st.close ( );

			//Utilisation de variable pour refermer correctement les statement et resultat
			return estCree;
		}
		catch ( SQLException e )
		{
			JOptionPane.showMessageDialog ( null, e.toString(), "erreur estGenere() : ", JOptionPane.ERROR_MESSAGE );
			e.getMessage ( );
		}
		
		return estCree;
	}

	/*---------------------------------------*/
	/*            RECUP GENERALE             */
	/*---------------------------------------*/

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
					if ( type.equals ( Semestre    .class ) )
						lst.add ( type.cast ( Semestre   .creation ( rs.getInt ( 1 ), rs.getInt ( 2 ), rs.getInt ( 3 ), rs.getInt ( 4 ), rs.getInt ( 5 ) ) ) );

					if ( type.equals ( Contrat    .class ) )
						lst.add ( type.cast ( Contrat    .creation ( rs.getInt ( 1 ), rs.getString ( 2 ), rs.getInt ( 3 ), rs.getInt ( 4 ), rs.getDouble ( 5 ) ) ) );

					if ( type.equals ( Heure      .class ) )
						lst.add ( type.cast ( Heure      .creation ( rs.getInt ( 1 ), rs.getString ( 2 ), rs.getDouble ( 3 ) ) ) );

					if ( type.equals ( Intervenant.class ) )
						lst.add ( type.cast ( Intervenant.creation ( rs.getInt ( 1 ), rs.getString ( 2 ), rs.getString ( 3 ), rs.getDouble ( 4 ), rs.getDouble ( 5 ), rs.getDouble ( 6 ), this.getContrat ( rs.getInt ( 7 ) ) ) ) );

					if ( type.equals ( ModuleIUT  .class ) )
						lst.add ( type.cast ( ModuleIUT  .creation ( rs.getInt ( 1 ), rs.getString ( 2 ) , rs.getString ( 3 ), rs.getString ( 4 ), rs.getString ( 5 ), rs.getBoolean ( 6 ), this.getSemestre ( rs.getInt ( 7 ) ), rs.getDouble ( 7 ), rs.getDouble ( 8 ) ) ) );

					if ( type.equals ( Horaire    .class ) )
						lst.add ( type.cast ( Horaire    .creation ( getHeure ( rs.getInt ( 1 ) ), getModule (rs.getString ( 2 ) ), rs.getInt ( 3 ), rs.getInt ( 5 ), rs.getInt ( 4 ) ) ) );

					if ( type.equals ( Intervient .class ) )
						lst.add ( type.cast ( Intervient .creation ( getIntervenant ( rs.getInt ( 1 ) ), getHeure ( rs.getInt ( 2 ) ) , getModule (rs.getString ( 3 ) ), rs.getInt ( 4 ), rs.getInt ( 5 ), rs.getInt ( 6 ), rs.getString(7) ) ) );
				}
				catch ( Exception e )
				{
					e.getMessage ( );
				}
			}

			rs.close ( );
			st.close ( );
		}
		catch ( SQLException e )
		{
			JOptionPane.showMessageDialog ( null, e.getMessage ( ), "erreur getTable ( ) : ", JOptionPane.ERROR_MESSAGE );
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
			System.out.println ( "getHoraire ( )" +  e.getMessage ( ) );
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
			System.out.println ( "getAnnee ( )" +  e.getMessage ( ) );
			nom = "Aucune année en cours";
		}

		return nom;
	}

	/*---------------------------------------*/
	/*             RECUP UNITAIRE            */
	/*---------------------------------------*/

	/*---------------------------------------*/
	/*       RECUP AVEC UN IDENTIFIANT       */
	/*---------------------------------------*/


	public List<Horaire> getHoraires ( String module )
	{
		ArrayList<Horaire> ensHoraire = new ArrayList<> ( );

		final String REQUETE = "SELECT * FROM Horaire where Code_ModuleIUT = ?";

		try
		{
			Statement         st = co.createStatement  (         );
			PreparedStatement ps = co.prepareStatement ( REQUETE );

			ps.setString ( 1, module );

			ResultSet rs = ps.executeQuery ( );

			while ( rs.next ( ) )
			{
				Horaire h = Horaire.creation ( getHeure ( rs.getInt ( 1 ) ), getModule ( rs.getString ( 2 ) ), rs.getInt ( 3 ), rs.getInt ( 4 ), rs.getInt ( 5 ) );
				ensHoraire.add ( h );
			}

			rs.close ( );
			ps.close ( );
			st.close ( );
		}
		catch ( SQLException e )
		{
			System.out.println ( "getHoraire ( )" +  e.getMessage ( ) );
		}

		return ensHoraire;
	}

	public Object getObjetAvecId ( Class<?> type, int identifiant )
	{
		/*Object objet = null;

		try
		{
			Statement st = co.createStatement ( );
			ResultSet rs = st.executeQuery ( "select * from Semestre where Id_Semestre = " + c );
			while ( rs.next ( ) )
			{
				objet = ;
			}

			rs.close ( );
			st.close ( );
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur getSemestre(int c) : " + e.getMessage ( ) );
		}

		return semestre;*/
		return null;
	}
	
	public Semestre getSemestre ( int c )
	{
		Semestre semestre = null;

		try
		{
			Statement st = co.createStatement ( );
			ResultSet rs = st.executeQuery ( "select * from Semestre where Id_Semestre = " + c );
			while ( rs.next ( ) )
			{
				semestre = Semestre.creation ( rs.getInt ( 1 ), rs.getInt ( 2 ), rs.getInt ( 3 ), rs.getInt ( 4 ), rs.getInt ( 5 ) );
			}

			rs.close ( );
			st.close ( );
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur getSemestre(int c) : " + e.getMessage ( ) );
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
				inter = Intervient.creation ( getIntervenant( rs.getInt ( 1 ) ), getHeure( rs.getInt ( 2 ) ), getModule ( rs.getString ( 3 ) ), rs.getInt ( 4 ), rs.getInt ( 5 ), rs.getInt ( 6 ), ""  );
			}

			rs.close ( );
			st.close ( );
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur getIntervient(int c) : " + e.getMessage ( ) );
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
					System.out.println ( "Erreur création de contrat : " + e.getMessage ( ) );
				}
			}

			rs.close();
			st.close();
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur getContrat(int c) : " + e.getMessage ( ) );
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
					e.getMessage ( );
				}

			}
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur getContrat(int c) : " + e.getMessage ( ) );
		}

		return contrat;
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
				intervenant = Intervenant.creation ( rs.getInt ( 1 ), rs.getString ( 2 ), rs.getString ( 3 ), rs.getDouble ( 4 ), rs.getDouble ( 5 ), rs.getDouble ( 6 ), this.getContrat ( rs.getInt ( 7 ) ) );
			}
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur getIntervenant(int c) : " + e.getMessage ( ) );
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
					e.getMessage ( );
				}

			}
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur getHeure(int h) : " + e.getMessage ( ) );
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
					e.getMessage ( );
				}

			}
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur getHeure(int h) : " + e.getMessage ( ) );
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
				module = ModuleIUT  .creation ( rs.getInt ( 1 ), rs.getString ( 2 ) , rs.getString ( 3 ), rs.getString ( 4 ), rs.getString ( 5 ), rs.getBoolean ( 6 ), this.getSemestre ( rs.getInt ( 7 ) ), rs.getDouble ( 7 ), rs.getDouble ( 8 ) );
			}
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur getModule (String m ) : " + e.getMessage ( ) );
		}

		return module;
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
			System.out.println ( "Erreur getContrat(int c) : " + e.getMessage ( ) );
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
			System.out.println ( "Erreur getContrat(int c) : " + e.getMessage ( ) );
		}

		return result;
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
		catch ( Exception e )
		{
			System.out.println ( "Erreur  getNBHeureParModule (String code, int Id_Inter, int Id_Heure) : " + e.getMessage ( ) );
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
			System.out.println ( "getNBHeureEQTD (String code, String nomHeure) : " + e.getMessage ( ) );
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
			System.out.println ( "Erreur getNBHeureParSemestre (int Id_Semestre, int Id_Intervenant) : " + e.getMessage ( ) );
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
		catch ( Exception e )
		{
			System.out.println ( "Erreur getNBHeurePNParModule (String code, int Id_Heure) : " + e.getMessage ( ) );
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
			System.out.println ( "Erreur getNBHeureRepParModule (String code, int Id_Heure) : " + e.getMessage ( ) );
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
			System.out.println ( "Erreur getHeureParSemestreImpair ( int id ) : " + e.getMessage ( ) );
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
			System.out.println ( "Erreur getHeureParSemestrePair ( int id ) : " + e.getMessage ( ) );
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
			System.out.println ( "Erreur getHeureParSemestreTotal ( int id ) : " + e.getMessage ( ) );
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
			System.out.println ( "Erreur getTotalHeureParInter ( int idInter, int idHeure ) : " + e.getMessage ( ) );
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
			System.out.println ( "Erreur getNbTuple ( String table ) : " + e.getMessage ( ) );
		}

		return nbTuple;
	}

	public Object getFonction ( String fonction )
	{
		Object o = null;

		try
		{
			Statement st = co.createStatement ( );
			ResultSet rs = st.executeQuery ( "select " + fonction );
			while ( rs.next ( ) )
			{
				o = rs.getObject ( 1 );
			}
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur getFonction ( String fonction ) : " + e.getMessage ( ) );
		}

		return o;
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
				object[lig][col+1] = tmp[col];
		}

		return object;
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
					}
					catch ( Exception e )
					{
						System.out.println ( "Ptit problème de conversion : getTableauParticulier ( ) " + e.getMessage ( ) );
					}
				}
				cpt++;
			}
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur getTableauParticulier ( String nomRecherche ) : " + e.getMessage ( ) );
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
			System.out.println ( "Erreur 1 getHistorique () : " + e.getMessage ( ) );
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
			System.out.println ( "Erreur insertAnnee ( String nom ) : " + e.getMessage ( ) );
		}
	}

	public void insert ( Contrat c )
	{
		String req = "INSERT INTO Contrat ( nomContrat, hServiceContrat, hMaxContrat, ratioTP ) VALUES(?,?,?,?)";
		try
		{
			ps = co.prepareStatement ( req );
			ps.setString ( 1, c.getNom                 ( ) );
			ps.setDouble ( 2, c.getHeureServiceContrat ( ) );
			ps.setDouble ( 3, c.getHeureMaxContrat     ( ) );
			ps.setDouble ( 4, c.getRatioTP             ( ) );
			ps.executeUpdate ( );

			ps.close ( );
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur insert ( Contrat c ) : " + e.getMessage ( ) );
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
			System.out.println ( "Erreur insert ( Heure h ) : " +  e.getMessage ( ) );
		}
	}

	//TODO: Mettre une erreur quand le module existe déha
	public void insert ( ModuleIUT m )
	{
		String req = "INSERT INTO ModuleIUT VALUES(?,?,?,?,?,?,?,?)";
		try
		{
			ps = co.prepareStatement ( req );
			ps.setString  ( 1, m.getCode               ( ) );
			ps.setString  ( 2, m.getLibLong            ( ) );
			ps.setString  ( 3, m.getLibCourt           ( ) );
			ps.setString  ( 4, m.getTypeModule         ( ) );
			ps.setBoolean ( 5, m.estValide             ( ) );
			ps.setInt     ( 6, m.getSemestre           ( ).getIdSemestre ( ) );
			ps.setDouble  ( 7, m.getTotalHeurePN       ( ) );
			ps.setDouble  ( 8, m.getTotalHeureAffectee ( ) );
			ps.executeUpdate ( );

			ps.close ( );
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur insert(moduleIUT m) : " + e.getMessage ( ) );
		}
	}

	public void insert ( Intervenant i )
	{
		String req = "INSERT INTO Intervenant (nom, prenom, hService, hMax, Id_Contrat) VALUES(?,?,?,?,?)";
		try
		{
			ps = co.prepareStatement ( req );
			ps.setString ( 1, i.getNom             ( )        );
			ps.setString ( 2, i.getPrenom          ( )        );
			ps.setDouble ( 3, i.getNbHeureService  ( )        );
			ps.setDouble ( 4, i.getMaxHeureService ( )        );
			ps.setInt    ( 5, i.getContrat      ( ).getId ( ) );
			ps.executeUpdate ( );

			ps.close ( );
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur insert ( Intervenant i ) : " + e.getMessage ( ) );
		}
	}

	public void insert ( Intervient e )
	{
		String req = "INSERT INTO Intervient VALUES(?,?,?,?,?,?,?)";
		try
		{
			ps = co.prepareStatement ( req );
			ps.setInt    ( 1, e.getIntervenant ( ).getId   ( ) );
			ps.setInt    ( 2, e.getHeure       ( ).getId   ( ) );
			ps.setString ( 3, e.getModule      ( ).getCode ( ) );
			ps.setInt    ( 4, e.getNbSemaine   ( )             );
			ps.setInt    ( 5, e.getNbGroupe    ( )             );
			ps.setDouble ( 6, e.getNbHeure     ( )             );
			ps.setString ( 7, e.getCommentaire ( )             );
			ps.executeUpdate ( );

			ps.close ( );
		}
		catch ( SQLException x )
		{
			System.out.println ( "Erreur insert ( Intervient e ) : " + x.getMessage ( ) );
		}
	}

	public void insert ( Horaire h )
	{
		String req = "INSERT INTO Horaire VALUES ( ?,?,?,?,? )";
		try
		{
			ps = co.prepareStatement ( req );
			ps.setInt    ( 1, h.getHeure          ( ).getId   ( ) );
			ps.setString ( 2, h.getModule         ( ).getCode ( ) );
			ps.setDouble ( 3, h.getNbHeurePN      ( )             );
			ps.setDouble ( 4, h.getNbHeureSemaine ( )             );
			ps.setInt    ( 5, h.getNbSemaine      ( )             );
			ps.executeUpdate ( );

			ps.close ( );
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur insert ( Horaire h ) : " + e.getMessage ( ) );
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
		String req = "DELETE FROM ModuleIUT where Code_ModuleIUT = '" + m.getCode ( ) + "'";

		try
		{
			ps = co.prepareStatement ( req );
			ps.executeUpdate ( );
			ps.close ( );
		}
		catch ( SQLException e )
		{
			
			int test = JOptionPane.showInternalConfirmDialog ( null, "Le module " + m.getCode ( ) + " est présent sur une autre table, voulez-vous supprimer toutes ses relations ?", "Suppression impossible", JOptionPane.YES_NO_OPTION );
			if ( test == 0 )
			{
				deleteAllIntervient ( m.getCode ( ), "Code_ModuleIUT" );
				deleteAllHoraire    ( m.getCode ( ), "Code_ModuleIUT" );
				delete ( m );
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
			System.out.println ( ex.getMessage ( ) );
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
			System.out.println ( e.getMessage ( ) );
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
			System.out.println ( ex.getMessage ( ) );
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
			System.out.println ( "Erreur setAnneeActuelle ( String nom ) : " + e.getMessage ( ) );
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
			System.out.println ( "Erreur update ( Semestre s ) : " + e.getMessage ( ) );
		}
	}

	public void update ( Contrat c )
	{
		String req = "UPDATE Contrat SET nomContrat = ?, hServiceContrat = ?, hMaxContrat = ?, ratioTP = ? WHERE Id_Contrat = ?";
		try
		{
			ps = co.prepareStatement ( req );
			ps.setString ( 1, c.getNom                 ( ) );
			ps.setDouble ( 2, c.getHeureServiceContrat ( ) );
			ps.setDouble ( 3, c.getHeureMaxContrat     ( ) );
			ps.setDouble ( 4, c.getRatioTP             ( ) );
			ps.setInt    ( 5, c.getId                  ( ) );
			ps.executeUpdate ( );

			ps.close ( );
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur update ( Contrat c ) : " + e.getMessage ( ) );
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
			System.out.println ( "Erreur update ( Heure h ) : " + e.getMessage ( ) );
		}
	}

	public void update ( ModuleIUT m )
	{
		String req = "UPDATE ModuleIUT SET libLong = ?, libCourt = ?, typeModule = ?, valide = ?, Id_Semestre = ?, heurePN = ?, heureAffecte = ? WHERE code_Moduleiut = ?";
		try
		{
			ps = co.prepareStatement ( req );
			ps.setString  ( 1, m.getLibLong    ( ) );
			ps.setString  ( 2, m.getLibCourt   ( ) );
			ps.setString  ( 3, m.getTypeModule ( ) );
			ps.setBoolean ( 4, m.estValide     ( ) );
			ps.setInt     ( 5, m.getSemestre   ( ).getIdSemestre ( ) );
			ps.setDouble  ( 6, m.getTotalHeurePN       ( ) );
			ps.setDouble  ( 7, m.getTotalHeureAffectee ( ) );
			ps.setString  ( 8, m.getCode       ( ) );
			ps.executeUpdate ( );

			ps.close ( );
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur update ( ModuleIUT m ) : " + e.getMessage ( ) );
		}
	}

	public void update ( Intervenant i )
	{
		String req = "UPDATE Intervenant SET nom = ?, prenom = ?, hService = ?, hMax = ?, Id_Contrat = ? WHERE Id_Intervenant = ?";
		try
		{
			ps = co.prepareStatement ( req );
			ps.setString ( 1, i.getNom             ( ) );
			ps.setString ( 2, i.getPrenom          ( ) );
			ps.setDouble ( 3, i.getNbHeureService  ( ) );
			ps.setDouble ( 4, i.getMaxHeureService ( ) );
			ps.setInt    ( 5, i.getContrat         ( ).getId ( ) );
			ps.setInt    ( 6, i.getId              ( ) );
			ps.executeUpdate ( );

			ps.close ( );
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur update ( Intervenant i ) : " + e.getMessage ( ) );
		}
	}

	public void update ( Intervient e )
	{
		String req = "UPDATE Intervient SET nbSemaine = ?, nbGroupe = ?, nbHeure = ?, commentaire = ? WHERE Id_Intervenant = ? AND id_heure = ? AND code_ModuleIUT = ?";
		try
		{
			ps = co.prepareStatement ( req );
			ps.setInt    ( 1, e.getNbSemaine   ( )             );
			ps.setInt    ( 2, e.getNbGroupe    ( )             );
			ps.setDouble ( 3, e.getNbHeure     ( )             );
			ps.setString ( 4, e.getCommentaire ( )             );
			ps.setInt    ( 5, e.getIntervenant ( ).getId   ( ) );
			ps.setInt    ( 6, e.getHeure       ( ).getId   ( ) );
			ps.setString ( 7, e.getModule      ( ).getCode ( ) );
			ps.executeUpdate ( );

			ps.close ( );
		}
		catch ( SQLException x )
		{
			System.out.println ( "Erreur update ( Intervient e ) : " + x.getMessage ( ) );
		}
	}

	public void update ( Horaire h )
	{
		String req = "UPDATE Horaire SET nbSemaine = ?, nbheurepn = ?, nbheurerepartie = ? WHERE id_Heure = ? AND code_moduleIUT = ?";
		try
		{
			ps = co.prepareStatement ( req );
			ps.setInt    ( 1, h.getNbSemaine      ( )             );
			ps.setDouble ( 2, h.getNbHeurePN      ( )             );
			ps.setDouble ( 3, h.getNbHeureSemaine ( )             );
			ps.setInt    ( 4, h.getHeure          ( ).getId   ( ) );
			ps.setString ( 5, h.getModule         ( ).getCode ( ) );
			ps.executeUpdate ( );

			ps.close ( );
		}
		catch ( SQLException e )
		{
			System.out.println ( "Erreur update ( Horaire h ) : " + e.getMessage ( ) );
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
			System.out.println ( "Erreur nouvelleAnnee ( ) : " + x.getMessage ( ) );
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
			System.out.println ( "Erreur nouvelleAnneeZero ( ) : " + x.getMessage ( ) );
			return false;
		}
	}
}