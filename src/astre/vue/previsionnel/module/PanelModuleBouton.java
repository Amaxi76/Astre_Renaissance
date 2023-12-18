package astre.vue.previsionnel.module;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;

import astre.Controleur;
import astre.modele.elements.Heure;
import astre.modele.elements.Horaire;
import astre.modele.elements.ModuleIUT;
import astre.modele.elements.Semestre;

/** Classe PanelModuleBouton
  * @author : Clémentin Ly
  * @version : 2.0 - 14/12/2023
  * @date : 11/12/2023
  */

public class PanelModuleBouton  extends JPanel implements ActionListener
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	private Controleur ctrl;
	private FrameModule frm;

	private JButton btnEnregistrer;
	private JButton btnAnnuler;

	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/
	
	public PanelModuleBouton ( Controleur ctrl, FrameModule frm )
	{
		this.ctrl = ctrl;
		this.frm  = frm;

		/* ------------------------- */
		/* Création des composants   */
		/* ------------------------- */

		this.btnEnregistrer = new JButton ( "Enregistrer" );
		this.btnAnnuler     = new JButton ( "Annuler"     );

		this.add ( this.btnEnregistrer );
		this.add ( this.btnAnnuler     );

		/* ------------------------- */
		/* Activation des composants */
		/* ------------------------- */

		this.btnEnregistrer.addActionListener ( this );
		this.btnAnnuler    .addActionListener ( this );
	}

	/* ActionListener */
	public void actionPerformed ( ActionEvent e )
	{

		if ( e.getSource ( ) == this.btnEnregistrer )
		{
			enregistrer();
		}

		if ( e.getSource ( ) == this.btnAnnuler )
		{
			( (JFrame)(this.getParent().getParent().getParent().getParent()) ).dispose(); //TODO: fonctionne mais peut être mettre qqch de plus propre via une valeur passée en paramètre de contructeur ?
		}
	}

	private void enregistrer()
	{
		String typeModule = this.frm.getPanelModuleLabel().getType();

		String   sem      = this.frm.getPanelModuleLabel().getSemestre();
		int      nbSem    = Character.getNumericValue ( sem.charAt ( 1 ) );
		Semestre semestre = this.ctrl.getSemestre ( nbSem );

		String  code     = this.frm.getPanelModuleLabel().getCode();
		String  libLong  = this.frm.getPanelModuleLabel().getLibLong();
		String  libCourt = this.frm.getPanelModuleLabel().getLibCourt();
		boolean cbOk     = this.frm.getCbValidation();

		ModuleIUT module = new ModuleIUT( semestre, typeModule, code, libLong, libCourt, cbOk);
		
		if ( typeModule.equals ( "Ressource" ) )
		{
			Heure CM      = this.ctrl.getHeure ( "CM" );
			int   CMPN    = this.frm.getPanelPNLocal().getCM();
			int   nbSemCM = this.frm.getPanelRepartition().getNbSemCM();
			int   nbHCM   = this.frm.getPanelRepartition().getNbHCM();

			new Horaire ( CM, module, CMPN, nbSemCM, nbHCM );


			Heure TD      = this.ctrl.getHeure ( "TD" );
			int   TDPN    = this.frm.getPanelPNLocal().getTD();
			int   nbSemTD = this.frm.getPanelRepartition().getNbSemTD();
			int   nbHTD   = this.frm.getPanelRepartition().getNbHTD();

			new Horaire ( TD, module, TDPN, nbSemTD, nbHTD );

			Heure TP      = this.ctrl.getHeure ( "TP" );
			int   TPPN    = this.frm.getPanelPNLocal().getTP();
			int   nbSemTP = this.frm.getPanelRepartition().getNbSemTP();
			int   nbHTP   = this.frm.getPanelRepartition().getNbHTP();

			new Horaire ( TP, module, TPPN, nbSemTP, nbHTP );
		}

		else if ( typeModule.equals ( "SAE" ) || typeModule.equals ( "Stage" ) )
		{
			Heure SAE   = this.ctrl.getHeure ( "SAE" );
			int   SAEPN = this.frm.getPanelPNLocal().getCM();
			int   HSAE  = this.frm.getPanelRepartitionBis().getSaeRepatition();

			new Horaire ( SAE, module, SAEPN, 1, HSAE );

			Heure TUT   = this.ctrl.getHeure ( "Tut" );
			int   TUTPN = this.frm.getPanelPNLocal().getTD();
			int   HTUT  = this.frm.getPanelRepartitionBis().getTutRepatition();

			new Horaire ( TUT, module, TUTPN, 1, HTUT );
		}

		else if ( typeModule.equals ( "PPP" ) )
		{
			Heure CM   = this.ctrl.getHeure ( "CM" );
			int   CMPN = this.frm.getPanelPNLocalPPP().getCM();
			int   HCM  = this.frm.getPanelRepartitionPPP().getCMRepartition();

			new Horaire ( CM, module, CMPN, 1, HCM );


			Heure TD   = this.ctrl.getHeure ( "TD" );
			int   TDPN = this.frm.getPanelPNLocalPPP().getTD();
			int   HTD  = this.frm.getPanelRepartitionPPP().getTDRepartition();

			new Horaire ( TD, module, TDPN, 1, HTD );


			Heure TP   = this.ctrl.getHeure ( "TP" );
			int   TPPN = this.frm.getPanelPNLocalPPP().getTP();
			int   HTP  = this.frm.getPanelRepartitionPPP().getTPRepartition();

			new Horaire ( TP, module, TPPN, 1, HTP );


			Heure TUT   = this.ctrl.getHeure ( "Tut" );
			int   TUTPN = this.frm.getPanelPNLocalPPP().getTut();
			int   HTUT  = this.frm.getPanelRepartitionPPP().getHTutRepartition();

			new Horaire ( TUT, module, TUTPN, 1, HTUT );

			Heure Ponct   = this.ctrl.getHeure ( "PONCT" );
			int   PonctPN = this.frm.getPanelPNLocalPPP().getPonct();
			int   HPonct  = this.frm.getPanelRepartitionPPP().getHPRepartition();

			new Horaire ( Ponct, module, PonctPN, 1, HPonct );
		}
	}
}