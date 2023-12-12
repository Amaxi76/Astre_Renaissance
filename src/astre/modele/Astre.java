package astre.modele;

import astre.modele.elements.Semestre;

public class Astre
{
	private BD           bd;

	public Astre ( )
	{
		this.bd = BD.getInstance ( );
	}

	public Object[][] getTableauModule ( )
	{
		//à remplacer par une commande de BD
		/*Object[][] tableau = { {"R1.01", "Initia", "437/465", "V"}, {"R2.02", "Dev", "374/374", "V"} };
		return tableau;*/
		
		return this.bd.getModulesTableau();
	}

	public Object[][] getTableauIntervenant ( )
	{
		return this.bd.getIntervenantsTableau();
	}

		public Semestre getSemestre ( int numSemestre )
	{
		return this.bd.getSemestre ( numSemestre );
	}
	

}
