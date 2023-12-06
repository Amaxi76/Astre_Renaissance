package astre.modele;

public class Module
{
	private Semestre semestre;
	private String   code;
	private String   libLong;
	private String   libCourt;

	public Module ( Semestre semestre, String code, String libLong, String libCourt )
	{
		this.semestre = semestre;
		this.code     = code;
		this.libLong  = libLong;
		this.libCourt = libCourt;
	}

	public Semestre getSemestre ( ) { return semestre; }
	public String   getCode     ( ) { return code;     }
	public String   getLibLong  ( ) { return libLong;  }
	public String   getLibCourt ( ) { return libCourt; }

	public void setSemestre ( Semestre semestre ) { this.semestre = semestre; }
	public void setCode     ( String   code     ) { this.code     = code;     }
	public void setLibLong  ( String   libLong  ) { this.libLong  = libLong;  }
	public void setLibCourt ( String   libCourt ) { this.libCourt = libCourt; }

}
