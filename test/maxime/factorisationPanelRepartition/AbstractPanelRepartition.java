import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class AbstractPanelRepartition extends JPanel
{
	protected KeyListener listenerModule;

	public AbstractPanelRepartition ( KeyListener listenerModule )
	{
		this.listenerModule = listenerModule;
		
		this.setLayout ( new GridBagLayout ( ) );
		this.setBorder ( BorderFactory.createLineBorder ( Color.GRAY, 1 ) );
	}

	public void ajouterTypeHeure ( String typeHeure ){}
	public void supprimerTypeHeure ( String typeHeure ){}

	public void majIHM ( ){}
}
