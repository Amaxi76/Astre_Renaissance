package astre.vue.previsionnel.module;

import astre.modele.elements.Semestre;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import astre.Controleur;

public class PanelModuleLabel  extends JPanel
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	private Controleur ctrl;
	private FrameModule frm;

	private JLabel     lblType;
	private JLabel     lblSemestre;
	private JTextField txtCode;
	private JTextField txtLibLong;
	private JTextField txtLibCourt;

	private JLabel lblNbEtd;
	public  JLabel lblNbGpTD;
	public  JLabel lblNbGpTP;


	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/
	
	public PanelModuleLabel ( Controleur ctrl, FrameModule frm )
	{
		this.ctrl = ctrl;
		this.frm  = frm;

		/* ------------------------- */
		/* Création des composants   */
		/* ------------------------- */

		this.setLayout ( new GridBagLayout() );

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets ( 5, 5, 5, 5 );

		this.lblType	 = new JLabel ( );
		this.lblSemestre = new JLabel ( );
		this.txtCode	 = new JTextField ("", 5);
		this.txtLibLong	 = new JTextField ("", 20);
		this.txtLibCourt = new JTextField ("", 10);

		this.lblNbEtd  = new JLabel( );
		this.lblNbGpTD = new JLabel( "0" );
		this.lblNbGpTP = new JLabel( "0" );


		gbc.gridy = 0;
		gbc.gridx = 0;
		this.add ( new JLabel ( "type module" ), gbc );

		gbc.gridx = 1;
		this.add ( new JLabel ( "semestre" ), gbc );

		gbc.gridx = 2;
		this.add ( new JLabel ( "code"  ), gbc );

		gbc.gridx = 3;
		this.add ( new JLabel ( "libellé long" ), gbc );

		gbc.gridx = 4;
		this.add ( new JLabel ( "libellé court" ), gbc );

		
		gbc.gridy = 1;
		gbc.gridx = 0;
		this.add ( this.lblType, gbc     );

		gbc.gridx = 1;
		this.add ( this.lblSemestre, gbc );

		gbc.gridx = 2;
		this.add ( this.txtCode, gbc     );

		gbc.gridx = 3;
		this.add ( this.txtLibLong, gbc  );

		gbc.gridx = 4;
		this.add ( this.txtLibCourt, gbc );


		gbc.gridy = 2;
		gbc.gridx = 1;
		this.add ( new JLabel ( "nb Etd : " ), gbc );

		gbc.gridx = 2;
		this.add ( new JLabel ( "nb gp TD : " ), gbc );

		gbc.gridx = 3;
		this.add ( new JLabel ( "nb gp TP : " ), gbc );


		gbc.gridy = 3;
		gbc.gridx = 1;
		this.add ( this.lblNbEtd, gbc  );

		gbc.gridx = 2;
		this.add ( this.lblNbGpTD, gbc );

		gbc.gridx = 3;
		this.add ( this.lblNbGpTP, gbc );

		/* ------------------------- */
		/* Activation des composants */
		/* ------------------------- */

		this.lblType    .setBackground ( Color.LIGHT_GRAY );
		this.lblType    .setPreferredSize ( new Dimension ( 100, 15) );
		this.lblType    .setOpaque ( true );

		this.lblSemestre.setBackground ( Color.LIGHT_GRAY );
		this.lblSemestre.setPreferredSize ( new Dimension ( 50, 15 ) );
		this.lblSemestre.setOpaque ( true );

		this.txtCode.addKeyListener(new KeyListener()
		{
			public void keyTyped    ( KeyEvent e ) { majLabels(); }
			public void keyPressed  ( KeyEvent e ) {}
			public void keyReleased ( KeyEvent e ) {}
		} );


		this.lblNbEtd .setBackground ( Color.LIGHT_GRAY );
		this.lblNbEtd .setPreferredSize ( new Dimension ( 25, 15) );
		this.lblNbEtd .setOpaque ( true );

		this.lblNbGpTD.setBackground ( Color.LIGHT_GRAY );
		this.lblNbGpTD.setPreferredSize ( new Dimension ( 25, 15) );
		this.lblNbGpTD.setOpaque ( true );

		this.lblNbGpTP.setBackground ( Color.LIGHT_GRAY );
		this.lblNbGpTP.setPreferredSize ( new Dimension ( 25, 15) );
		this.lblNbGpTP.setOpaque ( true );
	}

	private void majLabels()
	{
		String code = this.txtCode.getText();

		if ( code.contains ( "ST" ) )
		{
			if ( !code.startsWith ( "ST" ) )
			{
				this.lblType.setText ( "Stage" );
			}
		}

		else if ( code.startsWith ( "R" ) )
		{
			this.lblType.setText ( "Ressource" );
		}

		else if ( code.startsWith ( "S" ) )
		{
			this.lblType.setText ( "SAE" );
		}

		int valSemestre = (code.length() > 1) ? Character.getNumericValue(code.charAt(1)) : -1;

		if (valSemestre >= 1 && valSemestre <= 6)
			this.lblSemestre.setText("S" + valSemestre);

		attributsSemestre(valSemestre);

		this.frm.setVisiblePanels ( this.lblType.getText() );
	}

	private void attributsSemestre( int valSemestre )
	{
		Semestre sem = this.ctrl.getSemestre ( valSemestre );

		if ( sem != null )
		{
			this.lblNbEtd .setText ( String.valueOf ( sem.getNbEtudiant() ) );
			this.lblNbGpTD.setText ( String.valueOf ( sem.getNbGroupeTD() ) );
			this.lblNbGpTP.setText ( String.valueOf ( sem.getNbGroupeTP() ) );
		}
	}

	public String getLblType() { return this.lblType.getText(); }
}