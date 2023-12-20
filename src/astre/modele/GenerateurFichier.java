package astre.modele;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import astre.modele.elements.*;

/** Page de génération des fichiers
  * @author : Matéo Sa, Lebaron Alizéa
  * @version : 1.0 - 18/12/2023
  * @date : 06/12/2023
  */

public class GenerateurFichier
{
	public static void recapTtInter ( )
	{
        String chemin = "./fichierGenerer/recapTtIntervenant.csv";

		//veille version
		try ( BufferedWriter ecrivain = new BufferedWriter ( new FileWriter ( chemin ) ) )
		{
            // Ecriture de l'entête du CSV
			String entete = "Catégorie, Nom, Prénom, Service dû, Maximum heure, Coefficient TP, S1 Theo, S1 Reel, S3 Theo, S3 Reel, S5 Theo, S5 Reel, sTot Theo, stot Reel, S2 Theo, S2 Reel, S4 Theo, S4 Reel, S6 Theo, S6 Reel, sTot Theo, sTot Reel, Total Theo, Total Reel";

            ecrivain.write ( entete );
            ecrivain.newLine ( );

			BD bd = BD.getInstance ( ); //A changer ??
			Object[][] elem = bd.getTableau ( Intervenant.class );
			for ( int i = 0; i < elem.length; i++ )
			{
				String[] s = new String[24];

				for ( int j=0; j < 6; j++ )
				{
					s[j] = elem[i][j + 1].toString ( );
				}

				//TODO faire avec la requete d'Alizéa
				s[ 6] = bd.getInterventionIntervenantTheo ( Integer.parseInt ( elem[i][0].toString ( ) ), 1 ) + "";
				s[ 7] = bd.getInterventionIntervenant     ( Integer.parseInt ( elem[i][0].toString ( ) ), 1 ) + "";
				s[ 8] = bd.getInterventionIntervenantTheo ( Integer.parseInt ( elem[i][0].toString ( ) ), 3 ) + "";
				s[ 9] = bd.getInterventionIntervenant     ( Integer.parseInt ( elem[i][0].toString ( ) ), 3 ) + "";
				s[10] = bd.getInterventionIntervenantTheo ( Integer.parseInt ( elem[i][0].toString ( ) ), 5 ) + "";
				s[11] = bd.getInterventionIntervenant     ( Integer.parseInt ( elem[i][0].toString ( ) ), 5 ) + "";

				s[12] = Double.parseDouble ( s[6] ) + Double.parseDouble ( s[8] ) + Double.parseDouble ( s[10] ) + "";
				s[13] = Double.parseDouble ( s[7] ) + Double.parseDouble ( s[9] ) + Double.parseDouble ( s[11] ) + "";

				s[14] = bd.getInterventionIntervenantTheo ( Integer.parseInt ( elem[i][0].toString ( ) ), 1 ) + "";
				s[15] = bd.getInterventionIntervenant     ( Integer.parseInt ( elem[i][0].toString ( ) ), 1 ) + "";
				s[16] = bd.getInterventionIntervenantTheo ( Integer.parseInt ( elem[i][0].toString ( ) ), 3 ) + "";
				s[17] = bd.getInterventionIntervenant     ( Integer.parseInt ( elem[i][0].toString ( ) ), 3 ) + "";
				s[18] = bd.getInterventionIntervenantTheo ( Integer.parseInt ( elem[i][0].toString ( ) ), 5 ) + "";
				s[19] = bd.getInterventionIntervenant     ( Integer.parseInt ( elem[i][0].toString ( ) ), 5 ) + "";

				s[20] = Double.parseDouble ( s[14] ) + Double.parseDouble ( s[16] ) + Double.parseDouble ( s[18] ) + "";
				s[21] = Double.parseDouble ( s[15] ) + Double.parseDouble ( s[17] ) + Double.parseDouble ( s[19] ) + "";

				s[22] = Double.parseDouble ( s[12] ) + Double.parseDouble ( s[20] ) + "";
				s[23] = Double.parseDouble ( s[13] ) + Double.parseDouble ( s[21] ) + "";

				/*double ttsemT = 0;
				double ttsemR = 0;
				int testT = 1;
				int testR = 2;
				for( int j = 6; j < s.length; j++)
				{
					if( j == 14 || j == 22)
					{
						s[j] = ttsemT + "";
						s[j+1] = ttsemR + "";

						ttsemT= 0;
						ttsemR=0;
					}
					else
					{
						if(j%2==0)
						{
							s[j] = bd.getInterventionIntervenantTheo(Integer.parseInt( elem[i][0].toString()), testT) + "";
							ttsemT+=bd.getInterventionIntervenantTheo(Integer.parseInt( elem[i][0].toString()), testT);
							testT += 2;
						}
						else
						{
							s[j] = bd.getInterventionIntervenant(Integer.parseInt( elem[i][0].toString()), testR) + "";
							ttsemR+=bd.getInterventionIntervenant(Integer.parseInt( elem[i][0].toString()), testR);
							testR += 2;
						}

					}
				}*/


				for( int cpt = 0; cpt < s.length; cpt++)
				{
					//System.out.println( cpt + " " + s[cpt] + " ");
					ecrivain.write ( s[cpt] + "," );
				}


            	ecrivain.newLine ( );
			}

            System.out.println ( "Fichier CSV créé avec succès." );
        } catch ( IOException e )
		{
            e.printStackTrace ( );
        }
	}

	public static void GenererHTMLIntervenant ( Intervenant inter )
	{
		String chemin = "./fichierGenerer/recapIntervenant" + inter.getNom ( ) + ".html";

		try ( BufferedWriter ecrivain = new BufferedWriter ( new FileWriter ( chemin ) ) )
		{
			// Accès à la base de données
			BD bd = BD.getInstance ( );

            // Ecriture de l'entête
			String entete =
			"<!DOCTYPE html>\n"                                                           +
			"<html lang=\"fr>\n"                                                          +
			"<head>\n"                                                                    +
				"\t<meta charset=\"UTF-8\">\n"                                            +
				"\t<link href=\"./css/style.css\" rel=\"stylesheet\">\n"                  +
				"\t<title>Intervenant " + inter.getNom().toUpperCase() + " </title>\n"    +
			"</head>\n"                                                                   +
			"<body>\n"                                                                    ;

			// Ecriture du head et des paramètres
            ecrivain.write ( entete );
			ecrivain.newLine ( );

			String header = "<header>\r\n" +
								"\t\t<h1>" + inter.getPrenom() + " " + inter.getNom().toUpperCase() + "</h1>\r\n" +
							"</header>";

			// Ecriture du header de la page
			ecrivain.write ( header );
			ecrivain.newLine ( );

			//Ecriture du récapitulatif

			String recap = 	"<table id=\"recap\">"  + "<tr> <th colspan=\"6\"> Récapitulatif </th> </tr>";

			recap += "<tr>" +
						"<td>s1</td>" +
						"<td>s3</td>" +
						"<td>s5</td>" +
						"<td>s2</td>" +
						"<td>s4</td>" +
						"<td>s6</td>" +
					"</tr>" ;

			recap += "<tr>" +
						"<td>" + bd.getNBHeureParSemestre ( 1, inter.getId ( ) ) + "</td>" +
						"<td>" + bd.getNBHeureParSemestre ( 3, inter.getId ( ) ) + "</td>" +
						"<td>" + bd.getNBHeureParSemestre ( 5, inter.getId ( ) ) + "</td>" +
						"<td>" + bd.getNBHeureParSemestre ( 2, inter.getId ( ) ) + "</td>" +
						"<td>" + bd.getNBHeureParSemestre ( 4, inter.getId ( ) ) + "</td>" +
						"<td>" + bd.getNBHeureParSemestre ( 6, inter.getId ( ) ) + "</td>" +
					"</tr>" ;

			int sommePaire   = bd.getNBHeureParSemestre ( 2, inter.getId ( ) ) + bd.getNBHeureParSemestre ( 4, inter.getId ( ) ) + bd.getNBHeureParSemestre ( 6, inter.getId ( ) );
			int sommeImpaire = bd.getNBHeureParSemestre ( 1, inter.getId ( ) ) + bd.getNBHeureParSemestre ( 3, inter.getId ( ) ) + bd.getNBHeureParSemestre ( 5, inter.getId ( ) );

			recap += "<tr>" +
						"<td colspan='3'> Total semestre impaires : " + sommeImpaire + " heures</td>" +
						"<td colspan='3'> Total semestre paires : "   + sommePaire   + " heures</td>" +
					 "</tr> " ;

			recap += "<tr>" +
						"<td colspan='6' class='total'> Total des heures réparties : " + (sommeImpaire + sommePaire) + " heures</td>" +
					"</tr>";

			recap += "</table>";
			ecrivain.write(recap);

			// Ecriture de la table des données principales

			String table = "<table>";

			int nombreHeure = bd.getTable ( Heure.class ).size ( );

			// Ecriture de la première ligne
			table += "<tr>" +
						"<th rowspan='2'>Numéro de ligne</th>"           +
						"<th rowspan='2'>Code de la matière et nom</th>" +
						"<th colspan=" + (nombreHeure + 1) + ">Heure dans la matière</th>"     +
		   			 "</tr>";

			//Ecriture de la seconde entete de tableau

			table += "<tr>" ;

			for (Heure h : bd.getTable ( Heure.class ))
			{
				table += "<th>" + h.getNom() + "</th>";
			}

			table += "<th> Total </th> </tr>";

			//Ecriture des données de l'enseignants

			ArrayList<String> ensDejaTraite = new ArrayList<String>();
			int somme = 0;
			int cpt = 1;
			int nbHeure = 0;

			for ( Intervient intervient : bd.getTable ( Intervient.class ) )
			{
				if (intervient.getIntervenant ( ).getId ( ) == inter.getId ( ) && !ensDejaTraite.contains ( intervient.getModule ( ).getCode ( ) ) )
				{

					table += "<tr>";
					table += "<td class='num'>" + cpt++ + "</td>";
					table += "<td class='mod'>" + intervient.getModule ( ).getCode ( ) + " - " + intervient.getModule ( ).getLibLong ( ) + "</td>";

					for (Heure h : bd.getTable ( Heure.class ) )
					{
						nbHeure = bd.getNBHeureParModule ( intervient.getModule( ).getCode( ), intervient.getIntervenant ( ).getId ( ), h.getId ( ) );

						table += "<td class = 'heure'>" + nbHeure + "</td>";

						somme += nbHeure;
					}

					table += "<td class = 'heure'>" + somme + " heures </td>";
					table += "</tr>";

					somme = 0;

					ensDejaTraite.add ( intervient.getModule ( ).getCode ( ) );

				}
			}

			ecrivain.write(table);

			//Écriture fermant les balises html
			String footer =
			"\t</table>" +
			"</body>"  +
			"</html>"  ;

			ecrivain.write ( footer );

            System.out.println ( "Fichier HTML créé avec succès." );
        }
		catch ( IOException e )
		{
            e.printStackTrace ( );
        }
	}

	public static void GenererHTMLModule ( ModuleIUT module )
	{
		String chemin = "./fichierGenerer/recapModule" + module.getCode ( ) + ".html";

		try ( BufferedWriter ecrivain = new BufferedWriter ( new FileWriter ( chemin ) ) )
		{
            // Ecriture de l'entête
			String entete =
			"<!DOCTYPE html>\n"                                                                   +
			"<html lang=\"fr>\n"                                                                  +
			"<head>\n"                                                                            +
				"\t<meta charset=\"UTF-8\">\n"                                                    +
				"\t<link href=\"./css/styleModule.css\" rel=\"stylesheet\">\n"                    +
				"\t<title>" + module.getCode ( ) + " - " + module.getLibCourt ( ) + " </title>\n" +
			"</head>\n"                                                                           +
			"<body>\n"                                                                            +
				"\t<header>\n"                                                                    +
					"\t\t<h1>" + module.getCode ( ) + " - " + module.getLibLong ( ) + "</h1>\n"   +
				"\t</header>\n";

            ecrivain.write ( entete );
			ecrivain.newLine ( );


			ecrivain.write("\t<table class=\"centre\">\n" +
			"\t\t<tr>") ;

			// Initialisation des variables à écrires
			String nomHeure = ""                                 ;
			String heureAct = "\t\t\t<td>Heures Actuelles</td>\n";
			String heurePN  = "\t\t\t<td>Heures PN</td>\n"       ;

			int sommePN  = 0;
			int sommeAct = 0;

			// Récupération de la base de données
			BD bd = BD.getInstance();

			for ( Heure he : bd.getTable ( Heure.class ) )
			{
				int heureRecPN  = bd.getNBHeurePNParModule  ( module.getCode ( ), he.getId ( ) );
				int heureRecRep = bd.getNBHeureRepParModule ( module.getCode ( ), he.getId ( ) );

				sommePN  += heureRecPN;
				sommeAct += heureRecRep;

				nomHeure += "\t\t\t<th> " + he.getNom ( ) + " </th>\n";
				heurePN  += "\t\t\t<td> " + heureRecPN    + " </td>\n";
				heureAct += "\t\t\t<td> " + heureRecRep   + " </td>\n";
			}

			// Ecriture du tableau récapitulatif
			nomHeure += "\t\t\t<th>Total</th>\n";
			heureAct += "\t\t\t<td> " + sommeAct + "</td>\n";
			heurePN  += "\t\t\t<td> " + sommePN  + "</td>\n";

			ecrivain.write ( "\t\t\t<th>Récapitulatif</th>\n" + nomHeure + "</tr>" );
			ecrivain.write ( "<tr>"                           + heureAct + "</tr>" );
			ecrivain.write ( "<tr>"                           + heurePN  + "</tr>" );

			// On fait de l'économie de variables
			heureAct = "";
			sommeAct =  0;
			int nbHeure;
			ArrayList<Integer> ensDejaTraite = new ArrayList<Integer>();

			ecrivain.write( "\t<table class=\"intervenant\">\n"+
					        "\t\t<tr>\n"+
						    "\t\t\t<th colspan=2>\n &nbsp;</th>" + nomHeure + "\t\t</tr>\n");

			for ( Intervient inter : bd.getTable ( Intervient.class ) )
			{
				System.out.println(inter.getIntervenant ( ).getId ( ) + " " + inter.getIntervenant ( ).getPrenom());

				if (inter.getModule ( ).getCode ( ).equals ( module.getCode( ) ) && !ensDejaTraite.contains(inter.getIntervenant ( ).getId ( ) ) )
				{
					System.out.println(inter.getIntervenant ( ).getId ( ) + " " + inter.getIntervenant ( ).getPrenom());
					ecrivain.write("<tr>");
					ecrivain.write("<td colspan=2>" + inter.getIntervenant().getPrenom() + " " + inter.getIntervenant().getNom().toUpperCase() + "</td>");

					for (Heure h : bd.getTable ( Heure.class ) )
					{
						nbHeure = bd.getNBHeureParModule ( inter.getModule( ).getCode( ), inter.getIntervenant ( ).getId ( ), h.getId ( ) );

						ecrivain.write("<td>" + nbHeure + "</td>");

						sommeAct += nbHeure;
					}

					ecrivain.write("<td>" + sommeAct + "</td>");
					ecrivain.write("</tr>");

					sommeAct = 0;

					ensDejaTraite.add(inter.getIntervenant().getId());
				}

			}

			//Écriture fermant les balises html
			String footer =
			"\t</table>" +
			"</body>"  +
			"</html>"  ;

			ecrivain.write ( footer );

            System.out.println ( "Fichier HTML créé avec succès." );
        } catch ( IOException e )
		{
            e.printStackTrace ( );
        }
	}
}
