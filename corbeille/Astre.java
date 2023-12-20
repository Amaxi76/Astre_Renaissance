package astre.modele;

import java.lang.reflect.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import astre.modele.elements.*;
import astre.modele.outils.TableauUtilitaire;

public class Astre
{
	private BD bd;
	private static final char AJOUTER   = 'a';
	private static final char MODIFIER  = 'm';
	private static final char SUPPRIMER = 's';

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
			tabObjet[cpt][2] = "" + this.sommeHeure ( module.getCode ( ), 'R' ) + "/" + this.sommeHeure ( module.getCode ( ), 'P');
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
	public Heure 	     getHeure              ( int nom         ) { return this.bd.getHeure            ( nom ); }
	public Heure 	     getHeure              ( String nom      ) { return this.bd.getHeure            ( nom ); }

	public Contrat       getContrat            ( String nom      ) { return this.bd.getContrat          ( nom ); }
	public ModuleIUT     getModule             ( String nom      ) { return this.bd.getModule           ( nom ); }

	public List<Contrat>     getContrats       (                 ) { return this.bd.getContrats     (      ); }
	public List<Intervenant> getIntervenants   (                 ) { return this.bd.getIntervenants (      ); }
	public <T> List<T>       getTable          ( Class<T> type   ) { return this.bd.getTable        ( type ); }

	/*---------------------------------------*/
	/*                SETTEUR                */
	/*---------------------------------------*/

	public void update ( Object o ) { this.modification ( o, "update" ); }
	public void insert ( Object o ) { this.modification ( o, "insert" ); }
	public void delete ( Object o ) { this.modification ( o, "delete" ); }

	/*---------------------------------------*/
	/*                METHODES               */
	/*---------------------------------------*/

	public int sommeHeure ( String code, char typeHeure )
	{
		
		int somme = 0;
		
		for ( Heure h : this.bd.getHeures ( code, typeHeure ).keySet (  ) )
			somme += this.bd.getHeures ( code, typeHeure ).get ( h );

		return somme;
	}

	public void majTableauBD ( Object[] tuple, Class<?> type, char action )
	{
		try
		{
			Object objet = type.getDeclaredMethod ( "creation", Object[].class ).invoke ( null,  tuple );
			
			switch ( action )
			{
				case AJOUTER   -> this.insert ( objet );
				case SUPPRIMER -> this.delete ( objet );
				case MODIFIER  -> this.update ( objet );
				default -> throw new Exception ( "Action non reconnue" );
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace ( );
		}
	}

	private void modification ( Object o, String methode )
	{
		try
		{
			Method updateMethod = BD.class.getMethod ( methode , o.getClass ( ) );
			updateMethod.invoke ( this.bd, o );
		}
		catch ( NoSuchMethodException  | IllegalAccessException | InvocationTargetException e )
		{
			e.printStackTrace();
		}
	}

	public boolean nouvelleAnnee     ( ) { return this.bd.nouvelleAnnee     ( ); }
	public boolean nouvelleAnneeZero ( ) { return this.bd.nouvelleAnneeZero ( ); }
}
