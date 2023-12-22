package astre.vue.previsionnel.module;

import astre.modele.elements.ModuleIUT;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import astre.Controleur;

/** Classe PanelModuleLabel
  * @author : Clémentin Ly, Maximilien LESTERLIN
  * @version : 2.0 - 14/12/2023
  * @date : 11/12/2023
  */

public class PanelModuleLabel  extends JPanel
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/
	private final String[] intitules = { "type module", "semestre", "code", "libellé long", "libellé court", "nbEtud", "nbGpTD", "nbGpTP" };

	private Controleur  ctrl;

	private JTextField  txtType;
	private JTextField  txtSemestre;;
	private JTextField  txtCode;
	private JTextField  txtLibLong;
	private JTextField  txtLibCourt;
	private JTextField  txtNbEtd;
	private JTextField  txtNbGpTD;
	private JTextField  txtNbGpTP;

	/* ------------------------------- */
	/*          Constructeur           */
	/* ------------------------------- */
	
	public PanelModuleLabel ( Controleur ctrl, String typeModule, int numSemestre )
	{
		this.ctrl = ctrl;
		
		this.setLayout ( new GridBagLayout ( ) );

		GridBagConstraints gbc = new GridBagConstraints ( );
		
		/* ------------------------------- */
		/*     Création des composants     */
		/* ------------------------------- */

		this.txtType     = new JTextField ( ""  + typeModule , 10 );
		this.txtSemestre = new JTextField ( "S" + numSemestre,  5 );

		this.txtCode     = new JTextField ( "",  5 );
		this.txtLibLong  = new JTextField ( "", 20 );
		this.txtLibCourt = new JTextField ( "", 10 );

		// Récupération des informations du semestre avec la BD
		this.txtNbEtd  = new JTextField ( "" + this.ctrl.getSemestre ( numSemestre ).getNbEtudiant ( ), 3 );
		this.txtNbGpTD = new JTextField ( "" + this.ctrl.getSemestre ( numSemestre ).getNbGroupeTD ( ), 3 );
		this.txtNbGpTP = new JTextField ( "" + this.ctrl.getSemestre ( numSemestre ).getNbGroupeTP ( ), 3 );

		// Formatage des JLabels pour indiquer la non-modification
		this.formatTxt ( this.txtType     );
		this.formatTxt ( this.txtSemestre );
		this.formatTxt ( this.txtNbEtd    );
		this.formatTxt ( this.txtNbGpTD   );
		this.formatTxt ( this.txtNbGpTP   );

		/* ------------------------------- */
		/*  Positionnement des composants  */
		/* ------------------------------- */
		
		gbc.gridx  = gbc.gridy = 0;
		gbc.insets = new Insets ( 5, 5, 5, 5 );
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill   = GridBagConstraints.NONE;

		// Ajout des intitulés dans les JLabels
		for ( int cpt = 0; cpt < intitules.length - 3; cpt++ )
		{
			gbc.anchor = GridBagConstraints.LINE_START;
			this.add ( new JLabel ( intitules[cpt] ), gbc );
			gbc.gridx++;
		}

		gbc.gridy = 1;
		gbc.gridx = 0;
		this.add ( this.txtType    , gbc );

		gbc.gridx = 1;
		this.add ( this.txtSemestre, gbc );

		gbc.gridx = 2;
		this.add ( this.txtCode    , gbc );

		gbc.gridx = 3;
		this.add ( this.txtLibLong , gbc );

		gbc.gridx = 4;
		this.add ( this.txtLibCourt, gbc );

		gbc.gridy = 2;
		gbc.gridx = 1;

		// Ajout des derniers intitulés dans les JLabels
		for ( int cpt = 5; cpt < intitules.length; cpt++ )
		{
			this.add ( new JLabel ( intitules[cpt] ), gbc );
			gbc.gridx++;
		}

		gbc.gridy = 3;
		gbc.gridx = 1;
		this.add ( this.txtNbEtd , gbc );

		gbc.gridx = 2;
		this.add ( this.txtNbGpTD, gbc );

		gbc.gridx = 3;
		this.add ( this.txtNbGpTP, gbc );

		/* ------------------------------- */
		/*    Activation des composants    */
		/* ------------------------------- */

		/*this.txtCode.addKeyListener ( new KeyListener ( )
		{
			public void keyTyped    ( KeyEvent e ) { majLabels ( ); }
			public void keyPressed  ( KeyEvent e ) { }
			public void keyReleased ( KeyEvent e ) { }
		} );*/
	}

	private void formatTxt ( JTextField txt )
	{
		txt.setEditable   ( false            );
		txt.setBackground ( Color.LIGHT_GRAY );
	}

	public void majIhm ( String code )
	{
		this.txtCode    .setText ( code                                         );
		this.txtLibLong .setText ( this.ctrl.getModule ( code ).getLibLong  ( ) );
		this.txtLibCourt.setText ( this.ctrl.getModule ( code ).getLibCourt ( ) );
	}

	/*private void majLabels ( )
	{
		String code = this.txtCode.getText ( ).toUpperCase ( );
		int valSemestre = -1;

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

		else if ( code.startsWith ( "PP" ) )
		{
			this.lblType.setText ( "PPP" );
		}

		if ( !lblType.getText ( ).equals ( "PPP" ) )
		{
			valSemestre = ( code.length() > 1 ) ? Character.getNumericValue ( code.charAt ( 1 ) ) : -1;
		}
		else
		{
			valSemestre = ( code.length() > 4 ) ? Character.getNumericValue ( code.charAt ( 4) ) : -1;
		}

		if ( valSemestre >= 1 && valSemestre <= 6 )
				this.lblSemestre.setText ( "S" + valSemestre );

		attributsSemestre ( valSemestre );

		this.frm.setVisiblePanels ( this.lblType.getText ( ) );
	}*/

	/*private void attributsSemestre ( int valSemestre )
	{
		Semestre sem = this.ctrl.getSemestre ( valSemestre );

		if ( sem != null )
		{
			this.lblNbEtd .setText ( String.valueOf ( sem.getNbEtudiant ( ) ) );
			this.lblNbGpTD.setText ( String.valueOf ( sem.getNbGroupeTD ( ) ) );
			this.lblNbGpTP.setText ( String.valueOf ( sem.getNbGroupeTP ( ) ) );
		}
	}*/

	/*public void setModule ( ModuleIUT module )
	{
		this.lblSemestre.setText ( "S" + module.getSemestre ( ).getIdSemestre ( ) );
		this.lblType    .setText ( module.getTypeModule ( ) );
		this.lblNbEtd   .setText ( module.getSemestre ( ).getNbEtudiant ( ) + "" );
		this.lblNbGpTD  .setText ( module.getSemestre ( ).getNbGroupeTD ( ) + "" );
		this.lblNbGpTP  .setText ( module.getSemestre ( ).getNbGroupeTP ( ) + "" );

		this.txtLibLong .setText ( module.getLibLong  ( ) );
		this.txtLibCourt.setText ( module.getLibCourt ( ) );
		this.txtCode    .setText ( module.getCode     ( ) );
	}*/
	
	public int       getNbEtud   ( ) { return Integer.parseInt ( this.txtNbEtd .getText ( ) );  }
	public int       getNbGpTD   ( ) { return Integer.parseInt ( this.txtNbGpTD.getText ( ) );  }
	public int       getNbGpTP   ( ) { return Integer.parseInt ( this.txtNbGpTP.getText ( ) );  }
	public String    getCode     ( ) { return this.txtCode    .getText ( );                     }
	public String    getLibLong  ( ) { return this.txtLibLong .getText ( );                     }
	public String    getLibCourt ( ) { return this.txtLibCourt.getText ( );                     }
	public ModuleIUT getModule   ( ) { return this.ctrl.getModule ( this.txtCode.getText ( ) ); }
}