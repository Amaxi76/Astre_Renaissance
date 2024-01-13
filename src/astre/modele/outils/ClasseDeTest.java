package astre.modele.outils;

import astre.modele.elements.Contrat;

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

	}
}
