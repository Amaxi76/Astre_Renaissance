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

	public Object[][]    getTableauIntervenant (                 ) { return this.bd.getIntervenantsTableau ( );  }
	public Object[][]    getTableauIntervient  (                 ) { return this.bd.getIntervientsTableau  ( );  }
	public Object[][]    getTableauContrat     (                 ) { return this.bd.getContratsTableau     ( );  }
	public Object[][]    getTableauHeure       (                 ) { return this.bd.getHeureTableau        ( );  }
	public Semestre      getSemestre           ( int numSemestre ) { return this.bd.getSemestre ( numSemestre ); }
	public Heure 	     getHeure              ( String nom      ) { return this.bd.getHeure    ( nom );         }
	public List<Contrat> getContrats           (                 ) { return this.bd.getContrats ( );             }

	/*---------------------------------------*/
	/*                SETTEUR                */
	/*---------------------------------------*/

	public void majSemestre ( Semestre s ) { this.bd.update ( s ); }

	/*---------------------------------------*/
	/*                METHODES               */
	/*---------------------------------------*/ 

	public boolean nouvelleAnnee ( ) { return this.bd.nouvelleAnnee(); }
}
