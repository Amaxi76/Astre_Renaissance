public class DB
{
	//TODO: GENERALISER POUR TOUTES LES CLASSES
	public Object[][] getContratsTableau ( )
	{
		List<Contrat> lst = this.getTable ( Contrat.class );

		int nbAttributs = Contrat.class.getDeclaredFields ( ).length;
		Object[][] modules = new Object[ lst.size ( )][nbAttributs+1];

		for ( int lig = 0; lig < modules.length; lig ++ )
		{
			modules[lig][0] = 'X';
			Object[] tmp = Utilitaire.toArray ( lst.get ( lig ) );

			for ( int col = 0 ; col < nbAttributs; col ++ )
			{
				modules[lig][col+1] = tmp[col];
			}
		}

		return modules;
		
		/*int nbContrat = 0;

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
			System.out.println ( "Erreur 1 getContratsTableau() : " + e );
		}

		Object[][] contrats = new Object[nbContrat][5];

		try
		{
			Statement st = co.createStatement ( );
			ResultSet rs = st.executeQuery ( "select * from Contrat ORDER BY nomcontrat ASC" );
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
		return modules;
		*/
		
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
				heures[cpt][0] = rs.getInt ( 1 );
				heures[cpt][1] = rs.getString ( 2 );
				heures[cpt][2] = rs.getDouble ( 3 );

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

	public Object[][] getModulesTableau ( )
	{
		Object[] lst = this.getTable ( ModuleIUT.class ).toArray ( );
		
		int nbAttributs = ModuleIUT.class.getDeclaredFields ( ).length;
		Object[][] modules = new Object[lst.length][nbAttributs + 1];
		
		for ( int lig = 0; lig < modules.length; lig ++ )
		{
			modules[lig][0] = ' ';
			for ( int col = 1 ; col < nbAttributs; col ++ )
			{
				modules[lig] = ( Object[] ) modules[lig][col];

				System.out.println ( "TEST" + modules[lig][col] );
			}
		}
		
		
		/*int nbModule = this.getNbTuple ( "ModuleIUT" );

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
			System.out.println ( "Erreur 2 getModulesTableau ( ) : " + e );
		}*/

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
			ResultSet rs = st.executeQuery ( "select Id_Intervenant, nomContrat, nom, prenom, hService, hMax from Intervenant i join Contrat c on i.Id_Contrat = c.Id_Contrat order by Id_intervenant asc" );
			int cpt = 0;
			while ( rs.next ( ) )
			{
				double s1 = getInterventionIntervenant ( rs.getInt(1), 1 );
				double s2 = getInterventionIntervenant ( rs.getInt(1), 2 );
				double s3 = getInterventionIntervenant ( rs.getInt(1), 3 );
				double s4 = getInterventionIntervenant ( rs.getInt(1), 4 );
				double s5 = getInterventionIntervenant ( rs.getInt(1), 5 );
				double s6 = getInterventionIntervenant ( rs.getInt(1), 6 );
				double ttimp = s1 + s3 + s5;
				double ttpair = s2 + s4 + s6;

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
			ResultSet rs = st.executeQuery ( "select Id_Intervenant, Id_Heure, nbSemaine, nbGroupe, nbHeure, commentaire from Intervient where code_moduleIUT = '" + module + "'");
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

	 	return intervients;
	}
}
