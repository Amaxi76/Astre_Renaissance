package astre.modele;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import astre.modele.elements.Intervenant;
import astre.modele.elements.Intervient;

/** Page de génération des fichiers
  * @author : Matéo Sa, Lebaron Alizéa
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
            // Ecriture de l'entête du CSV
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

	public static void GenererHTMLIntervenant (Intervenant inter)
	{
		String chemin = "./fichierGenerer/recapIntervenant" + inter.getNom() + ".html";

		try ( BufferedWriter ecrivain = new BufferedWriter ( new FileWriter ( chemin ) ) )
		{
            // Ecriture de l'entête
			String entete = 
			"<!DOCTYPE html>"                                                          + 
			"<html lang=\"fr\">"                                                       + 
			"<head>"                                                                   + 
				"<meta charset=\"UTF-8\">"                                             + 
				"<link href=\"style.css\" rel=\"stylesheet\">"                         + 
				"<title>Intervenant " + inter.getNom().toUpperCase() + " </title>"     + 
			"</head>"                                                                  + 
			"<body>"                                                                   + 
				"<img src=\"../data/images/astre.png\" alt=\"Logo de l'application\">" +
				"<table>"                                                              ;

            ecrivain.write ( entete );
			ecrivain.newLine ( );

			ecrivain.write( "<tr>" + 
			                "<th colspan=\"4\">" + inter.getPrenom() + " - " + inter.getNom().toUpperCase() + "</th>" +
							"\n</tr>"
						  );
			ecrivain.newLine ( );

			ecrivain.write("<tr>" + 
								"<td class=\"num\">Numéro de ligne</td>" +
								"<td class=\"mod\">Code de la matière et nom</td>" +
								"<td class=\"heure\">CM - nbHCM <br> TD - nbHTD</td>" +
						   "</tr>"
						  );
			ecrivain.newLine ( );
			
			// Ecriture du corps du tableau
			BD bd = BD.getInstance ( );
			ArrayList<Intervient> lstInter = (ArrayList<Intervient>) bd.getTable(Intervient.class);

			Intervient prec = null;
			int compt = 1;
			for(Intervient intervient : lstInter )
			{
				if( intervient.getIntervenant().getId() == inter.getId() )
				{
					if( prec != null && prec.getModule().equals(intervient.getModule() ) )
					{
						ecrivain.write(" <br> " + intervient.getHeure().getNom() + " - " + ( intervient.getNbGroupe() * intervient.getNbSemaine() * intervient.getNbHeure() ) );
						ecrivain.write("  ");
					}
					else
					{
						if( compt != 0 )
						{
							ecrivain.write("\t</td> " + 
							               "</tr> "
							              );
						}

						ecrivain.write( "<tr>" +
										"\t<td class=\"num\">" + compt++ + "</td>" + 
										"\t<td class=\"mod\">" + intervient.getModule().getCode() + " - " + intervient.getModule().getLibLong() + "</td>" +
										"\t<td class=\"heure\"> " +  intervient.getHeure().getNom() + " - " + ( intervient.getNbGroupe() * intervient.getNbSemaine() * intervient.getNbHeure() )
									  );
						prec = intervient;
					}
				}	
			}
			
			//Écriture fermant les balises html
			String footer = 
			"</table>" +
			"</body>"  +
			"</html>"  ;

			ecrivain.write(footer);

            System.out.println ( "Fichier CSV créé avec succès." );
        } catch ( IOException e )
		{
            e.printStackTrace ( );
        }



	}
}
