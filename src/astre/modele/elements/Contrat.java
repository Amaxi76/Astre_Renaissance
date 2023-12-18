package astre.modele.elements;

/** Classe Contrat 
  * @author : Maximilien Lesterlin, Alizéa Lebaron
  * @version : 1.0 - 12/12/2023
  * @date : 06/12/2023
  */

public class Contrat
{
	private int    id;
	private String nom;
	private int    heureServiceContrat;
	private int    heureMaxContrat;
	private double ratioTP;

	/**
	 * @param id
	 * @param nom
	 * @param heureServiceContrat
	 * @param heureMaxContrat
	 * @param ratioTP
	 */
	private Contrat ( int id, String nom, int heureServiceContrat, int heureMaxContrat, double ratioTP )
	{
		this.id                  = id;
		this.nom                 = nom;
		this.heureServiceContrat = heureServiceContrat;
		this.heureMaxContrat     = heureMaxContrat;
		this.ratioTP             = ratioTP;
	}

	/*---------------------------------------*/
	/*                GETTEUR                */
	/*---------------------------------------*/

	/**
	 * @return
	 */
	public int    getId                  ( ) { return this.id;                  }
	/**
	 * @return
	 */
	public String getNom                 ( ) { return this.nom;                 }
	/**
	 * @return
	 */
	public int    getHeureServiceContrat ( ) { return this.heureServiceContrat; }
	/**
	 * @return
	 */
	public int    getHeureMaxContrat     ( ) { return this.heureMaxContrat;     }
	/**
	 * @return
	 */
	public double getRatioTP             ( ) { return this.ratioTP;             }

	/*---------------------------------------*/
	/*                SETTEUR                */
	/*---------------------------------------*/

	/**
	 * @param heureServiceContrat
	 */
	public void setHeureServiceContrat ( int    heureServiceContrat ) { this.heureServiceContrat = heureServiceContrat; }
	/**
	 * @param nom
	 */
	public void setNom                 ( String nom                 ) { this.nom                 = nom;                 }
	/**
	 * @param heureMaxContrat
	 */
	public void setHeureMaxContrat     ( int    heureMaxContrat     ) { this.heureMaxContrat     = heureMaxContrat;     }
	/**
	 * @param ratioTP
	 */
	public void setRatioTP             ( double ratioTP             ) { this.ratioTP             = ratioTP;             }
	/**
	 * @param id
	 */
	public void setId                  ( int    id                  ) { this.id                  = id;                  }

	/*---------------------------------------*/
	/*                METHODES               */
	/*---------------------------------------*/

	public static Contrat creation ( Object[] contrat )
	{
		Object i   = contrat[0];
		Object n   = contrat[1];
		Object hsc = contrat[2];
		Object hmc = contrat[3];
		Object rt  = contrat[4];

		if ( ( i != null && !( i instanceof Integer ) ) ||  !( n instanceof String ) || !( hsc instanceof Integer ) || !( hmc instanceof Integer ) || !( rt instanceof Number ) )
			throw new IllegalArgumentException ( "Les données du contrat ne sont pas du bon type" );
		
		int    id                  = ( i == null ) ? 0 : Integer.parseInt ( i.toString ( ) );
		int    heureServiceContrat = Integer.parseInt ( hsc.toString ( ) );
		int    heureMaxContrat     = Integer.parseInt ( hmc.toString ( ) );
		String nom                 = n.toString ( );
		double ratioTP             = Double.parseDouble ( rt.toString ( ) );

		return Contrat.creation ( id, nom, heureServiceContrat, heureMaxContrat, ratioTP );
	}

	public static Contrat creation ( int id, String nom, int heureServiceContrat, int heureMaxContrat, double ratioTP )
	{
		if ( nom.equals ( "" ) )
			throw new IllegalArgumentException ( "Une des données est vide" );

		//hserv < 0 ou hmax < 0
		if ( heureServiceContrat < 0 || heureMaxContrat < 0 )
			throw new IllegalArgumentException ( "Les heures de services et heures max doivent etre touts 2 supérieur à 0" );

		//hserv > hmax
		if ( heureServiceContrat > heureMaxContrat )
			throw new IllegalArgumentException ( "Les heures de services sont supérieur à ses heures max" );

		// Coef TD
		if ( ratioTP <= 0 )
			throw new IllegalArgumentException ( "Le coef TD ne peut pas être de 0" );
		
		return new Contrat ( id, nom, heureServiceContrat, heureMaxContrat, ratioTP );
	}

	@Override
	public boolean equals ( Object o )
	{
		if ( o == null ) return false;
		if ( o == this ) return true;

		if ( !( o instanceof Contrat ) ) return false;

		Contrat c = ( Contrat ) o;

		return this.id == c.id && this.nom.equals ( c.nom ) && this.heureServiceContrat == c.heureServiceContrat && this.heureMaxContrat == c.heureMaxContrat && this.ratioTP == c.ratioTP;
	}

	/**
	 * @return
	 */
	@Override
	public String toString ( )
	{
		return String.format ( "Nom : %-20s - "                 , this.nom                 ) +
		       String.format ( "Heure Service Contrat : %02d - ", this.heureServiceContrat ) +
		       String.format ( "Heure Max Contrat : %02d - "    , this.heureMaxContrat     ) +
		       String.format ( "Ratio TP : %,.2f"               , this.ratioTP             );

	}
	
}