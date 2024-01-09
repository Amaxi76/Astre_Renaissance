package astre.modele;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import astre.Controleur;
import astre.modele.elements.*;
import astre.modele.outils.Utilitaire;

public class Astre
{
	private BD bd;

	public Astre ( )
	{
		this.bd = BD.getInstance ( );
	}

	/*---------------------------------------*/
	/*                GETTEUR                */
	/*---------------------------------------*/

	public Object[][]    getTableau            ( Class<?>  type          ) { return this.bd.getTableau            ( type         ); }
	public Object[][]    getTableauParticulier ( String nomRecherche     ) { return this.bd.getTableauParticulier ( nomRecherche ); }
	public Semestre      getSemestre           ( int    numSemestre      ) { return this.bd.getSemestre           ( numSemestre  ); }
	public Heure         getHeure              ( int    id               ) { return this.bd.getHeure              ( id           ); }
	public Intervenant   getIntervenant        ( int    id               ) { return this.bd.getIntervenant        ( id           ); }
	public Heure         getHeure              ( String nom              ) { return this.bd.getHeure              ( nom          ); }
	public Contrat       getContrat            ( String nom              ) { return this.bd.getContrat            ( nom          ); }
	public ModuleIUT     getModule             ( String nom              ) { return this.bd.getModule             ( nom          ); }


	public List<String>      getHistorique     (                 ) { return this.bd.getHistorique   (      ); }
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

	/**
	 * Met à jour la base de donnée en créant un nouvel objet avec chaque lignes de tabDonnéesBD
	 */
	public void majTableauBD ( Object[][] tabDonneeBD, Class<?> type )
	{
		for ( int i = 0; i < tabDonneeBD.length; i++ )
		{
			Object[] sousTab = Arrays.copyOfRange ( tabDonneeBD[i], 1, tabDonneeBD[i].length );
			this.majObjetBD ( sousTab, type, ( char ) tabDonneeBD[i][0] );
		}
	}

	/**
	 * Créée un objet du bon type à partir de ses attributs contenus dans tabObjet
	 */
	public void majObjetBD ( Object[] tabObjet, Class<?> type, char modification )
	{
		try
		{	
			Object objet = type.getDeclaredMethod ( "creation", Object[].class ).invoke ( null, ( Object ) tabObjet );

			switch ( modification )
			{
				case Controleur.AJOUTER   -> this.insert ( objet );
				case Controleur.MODIFIER  -> this.update ( objet );
				case Controleur.SUPPRIMER -> this.delete ( objet );
			
				default  -> { break; } // ModeleTableau.DEFAUT et ModeleTableau.IGNORER
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace ( );
		}
	}

	/**
	 * Utilisation de la méthode INSERT, UPDATE ou DELETE sur un objet dans la base de données
	 */
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