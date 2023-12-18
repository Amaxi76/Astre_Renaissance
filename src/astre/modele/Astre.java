package astre.modele;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import astre.modele.elements.*;
import astre.modele.outils.SuppressionException;

public class Astre
{
	private BD bd;

	public Astre ( )
	{
		this.bd = BD.getInstance ( );
	}

	public Object[][] getTableauModule ( int numeroSemestre )
	{
		//TODO à remplacer par une commande de BD
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

	public void majTableauBD ( Object[][] tabDonneeBD, Class<?> type )
	{
		ArrayList<Object> lstLocal = new ArrayList<> (                           );
		ArrayList<Object> lstBD    = new ArrayList<> ( this.bd.getTable ( type ) );
		
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
		ArrayList<Object> lstUpdate      = new ArrayList<> (                           );

		// Ajout
		lstAjout.removeAll ( lstBD );

		System.out.println(lstAjout.size());

		// Suppression
		lstSuppression.removeAll ( lstLocal );

		// Update
		lstBD   .removeAll(lstLocal);
		lstLocal.removeAll(lstBD);
		lstUpdate.addAll ( new ArrayList<> ( lstBD ) );
		lstUpdate.addAll ( new ArrayList<> ( lstLocal ) );
		
		for ( Object obj : lstAjout )
			this.insert ( obj );

		for ( Object obj : lstSuppression )
			this.delete ( obj );

		for ( Object obj : lstUpdate )
			this.update ( obj );
	}

	public void update ( Object o )
	{
		String test = o.getClass().toString();
		//System.out.println(test);
		String str[] = test.split("\\.");

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
		String test = o.getClass().toString();
		//System.out.println(test);
		String str[] = test.split("\\.");

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
		String test = o.getClass().toString();
		//System.out.println(test);
		String str[] = test.split("\\.");

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
