package astre.modele.outils;

import java.sql.SQLException;

public class SuppressionException extends SQLException
{
    public SuppressionException ( String message )
	{
        super ( message );
    }
}