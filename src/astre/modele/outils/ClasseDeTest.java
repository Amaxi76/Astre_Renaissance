package astre.modele.outils;

import astre.modele.elements.Contrat;
import astre.modele.elements.Heure;

public class ClasseDeTest
{
	public static void main(String[] args)
	{
		
		Object rt = 2.0;
		double ratioTP             = Double.parseDouble ( rt.toString ( ) );

		double ratioTP2 = (double) rt;

		System.out.println("ratioTP = " + ratioTP);
		System.out.println("ratioTP2 = " + ratioTP2);


		System.out.println("Test du toString de Contrat");
		Contrat c = Contrat.creation(0, "Vacataire", 20, 30, 2.0);

		System.out.println(c);

		Object[] heure = {0, "TP", 1.5};
		Object[] heure2 = {1, "TD", 1.5};

		Heure h = Heure.creation(heure);
		Heure h2 = Heure.creation(heure2);

		System.out.println(h);
		System.out.println(h2);

	}
}
