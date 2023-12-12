package astre.modele;

import java.util.List;
import java.util.ArrayList;

import astre.modele.elements.*;

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
		
		ArrayList<ModuleIUT> ensModules = new ArrayList<> ( this.bd.getModules ( ) ) ;
		
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
