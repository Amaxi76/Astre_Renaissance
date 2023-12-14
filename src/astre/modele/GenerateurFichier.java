package astre.modele;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/** Page de génération des fichiers
  * @author : Matéo Sa
  * @version : 1.0 - 11/12/2023
  * @date : 06/12/2023
  */

public class GenerateurFichier
{
	public static void recapTtInter ( )
	{
        String chemin = "./fichierGenerer/recapTtIntervenant.csv";

        try ( BufferedWriter ecrivain = new BufferedWriter ( new FileWriter ( chemin ) ) )
		{
            // Écrivez les en-têtes du CSV
			String entete = "Catégorie, Nom, Prénom, Service dû, Maximum heure, Coefficient TP, S1, S3, S5, sTot, S2, S4, S6, sTot, Total";

            ecrivain.write ( entete );
            ecrivain.newLine ( );
			
			BD bd = BD.getInstance ( ); //A changer ??
			Object[][] elem = bd.getIntervenantsTableau ( );
			for ( int i = 0; i < elem.length; i++ )
			{
				for ( int j=1; j < elem[0].length; j++ )
				{
					ecrivain.write ( elem[i][j].toString ( ) + "," );
				}
            	ecrivain.newLine ( );
			}

            System.out.println ( "Fichier CSV créé avec succès." );
        } catch ( IOException e )
		{
            e.printStackTrace ( );
        }
	}
}
