package astre.modele;

import java.util.ArrayList;
import java.util.List;

import astre.modele.elements.*;

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
	public Heure 	     getHeure              ( String nom      ) { return this.bd.getHeure            ( nom ); }
	public Contrat       getContrat            ( String nom      ) { return this.bd.getContrat          ( nom ); }

	public List<Contrat>     getContrats       (                 ) { return this.bd.getContrats             ( ); }
	public List<Intervenant> getIntervenants   (                 ) { return this.bd.getIntervenants         ( ); }

	/*---------------------------------------*/
	/*                SETTEUR                */
	/*---------------------------------------*/

	public void majSemestre ( Semestre s ) { this.bd.update ( s ); }

	/*---------------------------------------*/
	/*                METHODES               */
	/*---------------------------------------*/ 

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
