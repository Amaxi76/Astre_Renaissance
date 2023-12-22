package astre.modele;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

			BD bd = BD.getInstance ( );

			ArrayList<Intervenant> lst = ( ArrayList<Intervenant> ) bd.getTable ( Intervenant.class );

			for ( int i = 0; i < lst.size ( ); i++ )
			{
				Intervenant inter = lst.get ( i );

				double totalTheo = 0;
				double totalReel = 0;
				
				ecrivain.write ( inter.getContrat      ( ).getNom     ( ) + "\t" );
				ecrivain.write ( inter.getNom                         ( ) + "\t" );
				ecrivain.write ( inter.getPrenom                      ( ) + "\t" );
				ecrivain.write ( inter.getheureService                ( ) + "\t" );
				ecrivain.write ( inter.getHeureMaximum                ( ) + "\t" );
				ecrivain.write ( inter.getContrat      ( ).getRatioTP ( ) + "\t" );

				int tour = 1;
				while ( tour < 3 )
				{
					double sommeTheo = 0;
					double sommeReel = 0;

					for ( int j = tour; j < 5 + tour; j+=2 )
					{
						double theo = bd.getInterventionIntervenantTheo ( inter.getId ( ), j );
						double reel = bd.getInterventionIntervenant     ( inter.getId ( ), j );

						ecrivain.write ( theo + "\t" );
						ecrivain.write ( reel + "\t" );

						sommeTheo += theo;
						sommeReel += reel;
					}

					ecrivain.write ( sommeTheo + "\t" );
					ecrivain.write ( sommeReel + "\t" );

					totalTheo += sommeTheo;
					totalReel += sommeReel;

					tour++;
				}
				
				ecrivain.write ( totalTheo + "\t" );
				ecrivain.write ( totalReel + "\t" );

				ecrivain.newLine ( );
			}

            System.out.println ( "Fichier CSV créé avec succès." );
        } 
		catch ( IOException e )
		{
            e.printStackTrace ( );
        }
	}

	public static void GenererHTMLToutIntervenant ( List<Intervenant> ensInt, String theme )
	{
		try 
		{
			for ( Intervenant i : ensInt )
			{
				GenererHTMLIntervenant ( i, theme );
			}

			System.out.println ( "Tous les intervenants on été exportés sous format HTML" );
		} 
		catch ( Exception e ) 
		{
			System.out.println ( "Erreur lors de la création d'un fichier HTML" );
		}
	}

	public static void GenererHTMLToutModule ( List<ModuleIUT> ensMod, String theme )
	{
		try 
		{
			for ( ModuleIUT m : ensMod )
			{
				GenererHTMLModule ( m, theme );
			}

			System.out.println ( "Tous les modules on été exportés sous format HTML" );
		} 
		catch ( Exception e ) 
		{
			System.out.println ( "Erreur lors de la création d'un fichier HTML" );
		}
	}

	public static void GenererHTMLIntervenant ( Intervenant inter, String theme )
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
				"\t<link href=\"./css/styleIntervenant" + theme +".css\" rel=\"stylesheet\">\n"       +
				"\t<title>Intervenant " + inter.getNom ( ).toUpperCase ( ) + " </title>\n"    +
			"</head>\n"                                                                   +
			"<body>\n"                                                                    ;

			// Ecriture du head et des paramètres
            ecrivain.write ( entete );
			ecrivain.newLine ( );

			String header = "<header>\r\n" +
								"\t\t<h1>" + inter.getPrenom ( ) + " " + inter.getNom ( ).toUpperCase ( ) + "</h1>\r\n" +
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

			int sommePaire   = bd.getHeureParSemestrePair   ( inter.getId ( ) );
			int sommeImpaire = bd.getHeureParSemestreImpair ( inter.getId ( ) );
			int sommeTot     = bd.getHeureParSemestreTotal  ( inter.getId ( ) );

			recap += "<tr>" +
						"<td colspan='3'> Total semestre impaires : " + sommeImpaire + " heures</td>" +
						"<td colspan='3'> Total semestre paires : "   + sommePaire   + " heures</td>" +
					 "</tr> " ;

			recap += "<tr>" +
						"<td colspan='6' class='total'> Total des heures réparties : " + sommeTot + " heures</td>" +
					"</tr>";

			recap += "</table>";
			ecrivain.write ( recap );

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

			for (Heure h : bd.getTable ( Heure.class ) )
			{
				table += "<th>" + h.getNom ( ) + "</th>";
			}

			table += "<th> Total </th> </tr>";

			//Ecriture des données de l'enseignants

			ArrayList<String> ensDejaTraite = new ArrayList<String> ( );
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

					for ( Heure h : bd.getTable ( Heure.class ) )
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

			table += "<tr class = 'total'>" +
						"<td class='num'>Total</td>" +
						"<td class='mod'>&nbsp;</td>" ;
			
			nbHeure = 0;
			somme = 0;
			for ( Heure h : bd.getTable ( Heure.class ) )
			{
				nbHeure = bd.getTotalHeureParInter ( inter.getId ( ), h.getId ( ) );

				table += "<td class = 'heure'>" + nbHeure + "</td>";

				somme += nbHeure;
			}			
			
						
			table += "<td class='heure'>" + somme + " heures</td>"    +
					 "</tr>" ;

			ecrivain.write ( table );

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

	public static void GenererHTMLModule ( ModuleIUT module, String theme)
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
				"\t<link href=\"./css/styleModule"+ theme +".css\" rel=\"stylesheet\">\n"                    +
				"\t<title>" + module.getCode ( ) + " - " + module.getLibCourt ( ) + " </title>\n" +
			"</head>\n"                                                                           +
			"<body>\n"                                                                            +
				"\t<header>\n"                                                                    +
					"\t\t<h1>" + module.getCode ( ) + " - " + module.getLibLong ( ) + "</h1>\n"   +
				"\t</header>\n";

            ecrivain.write ( entete );
			ecrivain.newLine ( );


			ecrivain.write ( "\t<table class=\"centre\">\n" + "\t\t<tr>" ) ;

			// Initialisation des variables à écrires
			String nomHeure = ""                                 ;
			String heureAct = "\t\t\t<td>Heures Actuelles</td>\n";
			String heurePN  = "\t\t\t<td>Heures PN</td>\n"       ;

			int sommePN  = 0;
			int sommeAct = 0;

			// Récupération de la base de données
			BD bd = BD.getInstance ( );

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
			ArrayList<Integer> ensDejaTraite = new ArrayList<Integer> ( );

			ecrivain.write( "\t<table class=\"intervenant\">\n"+
					        "\t\t<tr>\n"+
						    "\t\t\t<th colspan=2>\n &nbsp;</th>" + nomHeure + "\t\t</tr>\n");

			for ( Intervient inter : bd.getTable ( Intervient.class ) )
			{
				//System.out.println(inter.getIntervenant ( ).getId ( ) + " " + inter.getIntervenant ( ).getPrenom()); //debug

				if (inter.getModule ( ).getCode ( ).equals ( module.getCode ( ) ) && !ensDejaTraite.contains(inter.getIntervenant ( ).getId ( ) ) )
				{
					System.out.println(inter.getIntervenant ( ).getId ( ) + " " + inter.getIntervenant ( ).getPrenom());
					ecrivain.write("<tr>");
					ecrivain.write("<td colspan=2>" + inter.getIntervenant ( ).getPrenom ( ) + " " + inter.getIntervenant ( ).getNom ( ).toUpperCase ( ) + "</td>");

					for (Heure h : bd.getTable ( Heure.class ) )
					{
						nbHeure = bd.getNBHeureParModule ( inter.getModule( ).getCode( ), inter.getIntervenant ( ).getId ( ), h.getId ( ) );

						ecrivain.write ( "<td>" + nbHeure + "</td>" );

						sommeAct += nbHeure;
					}

					ecrivain.write ( "<td>" + sommeAct + "</td>" );
					ecrivain.write ( "</tr>" );

					sommeAct = 0;

					ensDejaTraite.add ( inter.getIntervenant ( ).getId ( ) );
				}

			}

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
}
