package astre.vue.previsionnel.module;

import astre.modele.elements.ModuleIUT;
import astre.modele.elements.Semestre;

import java.awt.*;

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
	private JTextField  txtSemestre;
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
	}

	public void formatTxt ( JTextField txt )
	{
		txt.setEditable   ( false            );
		txt.setBackground ( Color.LIGHT_GRAY );
	}

	public void setValeurs ( Object[] module )
	{
		this.txtSemestre.setText ( "S" + ( ( Semestre ) module[0] ).getIdSemestre ( ) );
		this.txtType    .setText ( ( String ) module[1] );
		this.txtCode    .setText ( ( String ) module[2] );
		this.txtLibLong .setText ( ( String ) module[3] );
		this.txtLibCourt.setText ( ( String ) module[4] );
	}
	
	public int        getNbEtud   ( ) { return Integer.parseInt ( this.txtNbEtd .getText ( ) );  }
	public int        getNbGpTD   ( ) { return Integer.parseInt ( this.txtNbGpTD.getText ( ) );  }
	public int        getNbGpTP   ( ) { return Integer.parseInt ( this.txtNbGpTP.getText ( ) );  }
	public String     getCode     ( ) { return this.txtCode    .getText ( );                     }
	public String     getLibLong  ( ) { return this.txtLibLong .getText ( );                     }
	public String     getLibCourt ( ) { return this.txtLibCourt.getText ( );                     }
	public ModuleIUT  getModule   ( ) { return this.ctrl.getModule ( this.txtCode.getText ( ) ); }
	public JTextField getTxtCode  ( ) { return this.txtCode; }

	public Object[] getDonnees ( )
	{
		Object[] moduleIUT = new Object[6];

		moduleIUT[0] = this.ctrl.getSemestre ( Integer.parseInt ( this.txtSemestre.getText ( ).substring ( 1 ) ) );
		moduleIUT[1] = this.txtType    .getText ( );
		moduleIUT[2] = this.txtCode    .getText ( );
		moduleIUT[3] = this.txtLibLong .getText ( );
		moduleIUT[4] = this.txtLibCourt.getText ( );
		moduleIUT[5] = false;

		return moduleIUT;
	}
}