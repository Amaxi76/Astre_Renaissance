package astre.modele;

import java.util.ArrayList;
import java.util.List;

import astre.modele.elements.*;
import astre.modele.outils.TableauUtilitaire;

public class Astre
{
	private BD bd;

	public Astre ( )
	{
		this.bd = BD.getInstance ( );
	}

	public Object[][] getTableauModule ( int numeroSemestre )
	{
		//à remplacer par une commande de BD
		/*Object[][] tableau = { {"R1.01", "Initia", "437/465", "V"}, {"R2.02", "Dev", "374/374", "V"} };
		return tableau;*/
		
		ArrayList<ModuleIUT> ensModules = new ArrayList<> ( this.bd.getModules ( numeroSemestre ) ) ;

		Object[][] tabObjet = new Object[ensModules.size ( ) ][4];

		for ( int cpt = 0; cpt < ensModules.size ( ); cpt++ )
		{
			ModuleIUT module = ensModules.get ( cpt );
			
			tabObjet[cpt][0] = module.getCode    ( );
			tabObjet[cpt][1] = module.getLibLong ( );
			tabObjet[cpt][2] = "" + module.getHeureReparties ( ) + "/" + module.getHeurePn ( );
			tabObjet[cpt][3] = module.estValide  ( );
		}
		
		return tabObjet;
	}

	/*---------------------------------------*/
	/*                GETTEUR                */
	/*---------------------------------------*/

	public Object[][]    getTableauIntervenant (                 ) { return this.bd.getIntervenantsTableau  ( ); }
	public Object[][]    getTableauIntervient  (                 ) { return this.bd.getIntervientsTableau   ( ); }
	public Object[][]    getTableauContrat     (                 ) { return this.bd.getContratsTableau      ( ); }
	public Object[][]    getTableauHeure       (                 ) { return this.bd.getHeureTableau         ( ); }
	public Semestre      getSemestre           ( int numSemestre ) { return this.bd.getSemestre ( numSemestre ); }
	public Heure 	     getHeure              ( int nom      ) { return this.bd.getHeure            ( nom ); }
	public Heure 	     getHeure              ( String nom      ) { return this.bd.getHeure            ( nom ); }

	public Contrat       getContrat            ( String nom      ) { return this.bd.getContrat          ( nom ); }
	public ModuleIUT     getModule             ( String nom      ) { return this.bd.getModule           ( nom ); }

	public List<Contrat>     getContrats       (                 ) { return this.bd.getContrats     (      ); }
	public List<Intervenant> getIntervenants   (                 ) { return this.bd.getIntervenants (      ); }
	public <T> List<T>       getTable          ( Class<T> type   ) { return this.bd.getTable        ( type ); }

	/*---------------------------------------*/
	/*                SETTEUR                */
	/*---------------------------------------*/

	public void majSemestre ( Semestre s ) { this.bd.update ( s ); }

	/*---------------------------------------*/
	/*                METHODES               */
	/*---------------------------------------*/ 

	public void majTableauBD_ancien ( Object[][] tabDonneeBD, Class<?> type )
	{
		ArrayList<Object> lstLocal = new ArrayList<> (                           );
		ArrayList<Object> lstBD    = new ArrayList<> ( this.bd.getTable ( type ) );

		System.out.println ( TableauUtilitaire.afficherTableau ( lstBD    ) );

		
		//Pour tout contrat dans le nouveau tab, si ID existe dans BD alors update la ligne sinon insert la ligne
		Object objet = null;
		for ( int i = 0; i < tabDonneeBD.length; i++ )
		{
			try
			{
				objet = type.getDeclaredMethod ( "creation", Object[].class ).invoke ( null, ( Object ) tabDonneeBD[i] );
				
				lstLocal.add ( objet );
			}
			catch ( Exception e )
			{
				e.printStackTrace ( );
			}

		}

		ArrayList<Object> lstAjout       = new ArrayList<> ( lstLocal                  );
		ArrayList<Object> lstSuppression = new ArrayList<> ( this.bd.getTable ( type ) );
		ArrayList<Object> lstModification      = new ArrayList<> (                           );

		// Ajout

		System.out.println ( lstAjout.get ( 0 ).equals ( lstBD.get ( 0 ) ) );
		System.out.println ( lstAjout.get ( 0 ).getClass ( ) );
		System.out.println ( lstBD   .get ( 0 ).getClass ( ) );

		lstAjout.removeAll ( lstBD );

		//System.out.println ( TableauUtilitaire.afficherTableau ( lstAjout ) );
		

		// Suppression
		lstSuppression.removeAll ( lstLocal );
		//System.out.println ( TableauUtilitaire.afficherTableau ( lstSuppression ) );


		// Update
		/*lstBD   .removeAll ( lstLocal );
		lstLocal.removeAll ( lstBD    );

		System.out.println ( TableauUtilitaire.afficherTableau ( lstBD    ) );
		//System.out.println ( TableauUtilitaire.afficherTableau ( lstLocal ) );
		lstUpdate.addAll ( new ArrayList<> ( lstBD )    );
		lstUpdate.addAll ( new ArrayList<> ( lstLocal ) );*/

		/*ArrayList<Object> intersection = new ArrayList<>(lstLocal);
		intersection.retainAll(lstBD);

		lstModification.addAll(lstBD);
		lstModification.removeAll(intersection);
		System.out.println ( TableauUtilitaire.afficherTableau ( lstModification ) );*/

		
		for ( Object obj : lstAjout )
			this.insert ( obj );

		for ( Object obj : lstSuppression )
			this.delete ( obj );

		for ( Object obj : lstModification )
			this.update ( obj );
	}

	public void majTableauBD ( Object[][] tabDonneeBD, Class<?> type )
	{
		ArrayList<Object> lstLocal = new ArrayList<> (                           );
		ArrayList<Object> lstBD    = new ArrayList<> ( this.bd.getTable ( type ) );
		
		Object objet = null;
		for ( int i = 0; i < tabDonneeBD.length; i++ )
		{
			try
			{
				objet = type.getDeclaredMethod ( "creation", Object[].class ).invoke ( null, ( Object ) tabDonneeBD[i] );
				
				lstLocal.add ( objet );
			}
			catch ( Exception e )
			{
				e.printStackTrace ( );
			}
		}

		System.out.println ( "lstLocal : \n"  + TableauUtilitaire.afficherTableau ( lstLocal ) );
		System.out.println ( "lstBD    : \n"  + TableauUtilitaire.afficherTableau ( lstBD ) );

		ArrayList<Object> lstAjout        = new ArrayList<> ( lstLocal                  );
		ArrayList<Object> lstSuppression  = new ArrayList<> ( this.bd.getTable ( type ) );
		ArrayList<Object> lstModification = new ArrayList<> (                           );
		
		lstAjout      .removeAll ( lstBD    );
		lstSuppression.removeAll ( lstLocal );

		for ( Object eltLocal : lstLocal )
		{
			for ( Object eltBD : lstBD )
			{
				System.out.println("Condition 1 :" + !lstLocal.contains ( eltBD ));
				System.out.println("Condition 2 :" + !lstBD.contains ( eltLocal ));
				System.out.println("Condition 3 :" + !lstModification.contains ( eltLocal ));
				System.out.println("Condition 4 :" + ( lstAjout.contains(eltLocal) && lstSuppression.contains ( eltBD ) ));

				if ( !lstLocal.contains ( eltBD ) && !lstBD.contains ( eltLocal ) && !lstModification.contains ( eltLocal ) && ( lstAjout.contains(eltLocal) && lstSuppression.contains ( eltBD ) ) )
				{
					System.out.println("eltLocal : " + eltLocal);
					lstModification.add    ( eltLocal );
					lstAjout       .remove ( eltLocal );
					//lstAjout       .remove ( eltBD    );
					lstSuppression .remove ( eltBD    );
					//lstSuppression .remove ( eltLocal );
				}
			}
		}

		System.out.println ( "lstAjout        : \n" + TableauUtilitaire.afficherTableau ( lstAjout        ) );
		System.out.println ( "lstSuppr        : \n" + TableauUtilitaire.afficherTableau ( lstSuppression  ) );
		System.out.println ( "lstModification : \n" + TableauUtilitaire.afficherTableau ( lstModification ) );
		
		for ( Object obj : lstAjout )
			this.insert ( obj );

		for ( Object obj : lstSuppression )
			this.delete ( obj );

		for ( Object obj : lstModification )
			this.update ( obj );
	}

	public void update ( Object o )
	{
		String str[] = o.getClass ( ).toString ( ).split ( "\\." );

		switch(str[str.length - 1])
		{
			case "Contrat"     : this.bd.update ( ( Contrat     ) o ); break;
			case "Heure"       : this.bd.update ( ( Heure       ) o ); break;
			case "Horaire"     : this.bd.update ( ( Horaire     ) o ); break;
			case "Intervenant" : this.bd.update ( ( Intervenant ) o ); break;
			case "Intervient"  : this.bd.update ( ( Intervient  ) o ); break;
			case "ModuleIUT"   : this.bd.update ( ( ModuleIUT   ) o ); break;
			case "Semestre"    : this.bd.update ( ( Semestre    ) o ); break;

			default : System.out.println("def");
		}
	}

	public void insert ( Object o )
	{
		String str[] = o.getClass().toString().split("\\.");

		switch(str[str.length - 1])
		{
			case "Contrat"     : this.bd.insert ( ( Contrat     ) o ); break;
			case "Heure"       : this.bd.insert ( ( Heure       ) o ); break;
			case "Horaire"     : this.bd.insert ( ( Horaire     ) o ); break;
			case "Intervenant" : this.bd.insert ( ( Intervenant ) o ); break;
			case "Intervient"  : this.bd.insert ( ( Intervient  ) o ); break;
			//case "ModuleIUT"   : this.bd.insert ( ( ModuleIUT   ) o ); break;
			//case "Semestre"    : this.bd.insert ( ( Semestre    ) o ); break;

			default : System.out.println("def");
		}
	}

	public void delete ( Object o )
	{
		String str[] = o.getClass().toString().split("\\.");

		switch(str[str.length - 1])
		{
			case "Contrat"     : this.bd.delete ( ( Contrat     ) o ); break;
			case "Heure"       : this.bd.delete ( ( Heure       ) o ); break;
			case "Horaire"     : this.bd.delete ( ( Horaire     ) o ); break;
			case "Intervenant" : this.bd.delete ( ( Intervenant ) o ); break;
			case "Intervient"  : this.bd.delete ( ( Intervient  ) o ); break;
			case "ModuleIUT"   : this.bd.delete ( ( ModuleIUT   ) o ); break;
			//case "Semestre"    : this.bd.delete ( ( Semestre    ) o ); break;

			default : System.out.println("def");
		}
	}
	public boolean nouvelleAnnee     ( ) { return this.bd.nouvelleAnnee     ( ); }
	public boolean nouvelleAnneeZero ( ) { return this.bd.nouvelleAnneeZero ( ); }
}
