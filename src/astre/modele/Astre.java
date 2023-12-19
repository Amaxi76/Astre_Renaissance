package astre.modele;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import astre.modele.elements.*;
import astre.modele.outils.ModeleTableau;

public class Astre
{
	private BD bd;

	public Astre ( )
	{
		this.bd = BD.getInstance ( );
	}

	public Object[][] getTableauModule ( int numeroSemestre )
	{
		ArrayList<ModuleIUT> ensModules = new ArrayList<> ( this.bd.getModules ( numeroSemestre ) ) ;

		Object[][] tabObjet = new Object[ensModules.size ( ) ][4];

		for ( int cpt = 0; cpt < ensModules.size ( ); cpt++ )
		{
			ModuleIUT module = ensModules.get ( cpt );
			
			tabObjet[cpt][0] = module.getCode    ( );
			tabObjet[cpt][1] = module.getLibLong ( );
			tabObjet[cpt][2] = "" + this.sommeHeure ( module.getCode ( ), 'R' ) + "/" + this.sommeHeure ( module.getCode ( ), 'P' );
			tabObjet[cpt][3] = module.estValide  ( );
		}
		
		return tabObjet;
	}

	/*---------------------------------------*/
	/*                GETTEUR                */
	/*---------------------------------------*/

	public Object[][]    getTableau  ( Class<?>  type     ) { return this.bd.getTableau  ( type        ); }
	public Semestre      getSemestre ( int    numSemestre ) { return this.bd.getSemestre ( numSemestre ); }
	public Heure 	     getHeure    ( int    nom         ) { return this.bd.getHeure    ( nom         ); } //TODO: nom de variable à retravailler
	public Heure 	     getHeure    ( String nom         ) { return this.bd.getHeure    ( nom         ); }
	public Contrat       getContrat  ( String nom         ) { return this.bd.getContrat  ( nom         ); }
	public ModuleIUT     getModule   ( String nom         ) { return this.bd.getModule   ( nom         ); }

	public ArrayList<String> getHistorique     (                 ) { return this.bd.getHistorique   (      ); }
	public List<Contrat>     getContrats       (                 ) { return this.bd.getContrats     (      ); }
	public List<Intervenant> getIntervenants   (                 ) { return this.bd.getIntervenants (      ); }
	public <T> List<T>       getTable          ( Class<T> type   ) { return this.bd.getTable        ( type ); }

	public int               getNBHeureEQTD    ( String code, String nomHeure ) { return this.bd.getNBHeureEQTD ( code, nomHeure ); }

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


	public void majTableauBD ( Object[][] tabDonneeBD, Class<?> type )
	{
		for ( int i = 0; i < tabDonneeBD.length; i++ )
		{
			try
			{
				Object[] sousTab = Arrays.copyOfRange ( tabDonneeBD[i], 1, tabDonneeBD[i].length );
				
				Object objet = type.getDeclaredMethod ( "creation", Object[].class ).invoke ( null, ( Object ) sousTab );

				switch ( ( char ) tabDonneeBD[i][0] )
				{
					case ModeleTableau.AJOUTER   -> this.insert ( objet );
					case ModeleTableau.MODIFIER  -> this.update ( objet );
					case ModeleTableau.SUPPRIMER -> this.delete ( objet );
				
					default  -> { break; } // ModeleTableau.DEFAUT et ModeleTableau.IGNORER
				}
			}
			catch ( Exception e )
			{
				e.printStackTrace ( );
			}
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
			e.printStackTrace ( );
		}
	}
	
	public boolean nouvelleAnnee     ( ) { return this.bd.nouvelleAnnee     ( ); }
	public boolean nouvelleAnneeZero ( ) { return this.bd.nouvelleAnneeZero ( ); }
}