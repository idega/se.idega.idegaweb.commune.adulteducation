/*
 * Created on 2005-maj-04
 * 
 * To change the template for this generated file go to Window - Preferences -
 * Java - Code Generation - Code and Comments
 */
package se.idega.idegaweb.commune.adulteducation.presentation;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.ejb.FinderException;
import javax.faces.component.UIComponent;
import se.idega.idegaweb.commune.adulteducation.business.AdultEducationBusiness;
import se.idega.idegaweb.commune.adulteducation.data.AdultEducationPersonalInfo;
import se.idega.idegaweb.commune.business.CommuneUserBusiness;
import se.idega.idegaweb.commune.presentation.CitizenChildren;
import com.idega.business.IBOLookup;
import com.idega.business.IBORuntimeException;
import com.idega.core.localisation.data.ICLanguage;
import com.idega.core.localisation.data.ICLanguageHome;
import com.idega.core.location.data.Address;
import com.idega.core.location.data.Commune;
import com.idega.core.location.data.CommuneHome;
import com.idega.core.location.data.Country;
import com.idega.core.location.data.CountryHome;
import com.idega.data.IDOLookup;
import com.idega.data.IDOLookupException;
import com.idega.idegaweb.IWBundle;
import com.idega.idegaweb.IWResourceBundle;
import com.idega.presentation.IWContext;
import com.idega.presentation.PresentationObject;
import com.idega.presentation.Table;
import com.idega.presentation.text.HorizontalRule;
import com.idega.presentation.text.Text;
import com.idega.presentation.ui.CheckBox;
import com.idega.presentation.ui.DropdownMenu;
import com.idega.presentation.ui.Form;
import com.idega.presentation.ui.HiddenInput;
import com.idega.presentation.ui.RadioButton;
import com.idega.presentation.ui.SubmitButton;
import com.idega.presentation.ui.TextInput;
import com.idega.user.business.UserBusiness;
import com.idega.user.data.User;
import com.idega.util.PersonalIDFormatter;
import com.idega.util.text.Name;

/**
 * @author Malin
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class PersonalInfo extends AdultEducationBlock {

	IWBundle iwb;

	IWResourceBundle iwrb;

	UserBusiness userbuiz;

	Collection schoolTypes = null;

	List[] schools = null;

	int preTypeId = -1;

	int preAreaId = -1;

	public static final String PARAMETER_UNIQUE_ID = "prm_unique_id";

	private String prefix = "sch_app_";

	private String prmYearReload = this.prefix + "yearReload";

	private String prmRealSubmit = this.prefix + "real_submit";

	private String prmAction = this.prefix + "snd_frm";

	private String prmChildId = CitizenChildren.getChildIDParameterName();

	private String prmForm = this.prefix + "the_frm";

	// //
	private boolean inWindow = false;

	private int studentId = -1;

	User student = null;

	private static final String PARAMETER_ACTION = "pi_action";

	private static final int ACTION_NONE = 1;

	private static final int ACTION_STORE = 2;

	private int iAction = ACTION_NONE;

	private final static String IC_COUNTRY = "country";

	private final static String IC_LANGUAGE = "language";

	private final static String EDUCATION_A = "educationA";

	private final static String EDUCATION_B = "educationB";

	private final static String EDUCATION_C = "educationC";

	private final static String EDUCATION_D = "educationD";

	private final static String EDUCATION_E = "educationE";

	private final static String EDUCATION_F = "educationF";

	private final static String EDUCATION_F_INPUT = "educationFInput";

	private final static String EDUCATION_G_INPUT = "educationGInput";

	private final static String EDUCATION_HA = "educationHA";

	private final static String EDUCATION_HB = "educationHB";

	private final static String EDUCATION_HC = "educationHC";

	private final static String EDUCATION_HCOMMUNE = "educationHCommune";

	private final static String EDU_COUNTRY = "educationCountry";

	private final static String EDU_YEARS = "educationYears";

	private final static String STUDY_SUPPORT = "studySupport";

	private final static String LANGUAGE_SFI = "languageSFI";

	private final static String LANGUAGE_SAS = "languageSAS";

	private final static String LANGUAGE_OTHER = "languageOther";

	private final static String WORK_UNEMP = "workUnemp";

	private final static String WORK_EMP = "workEmp";

	private final static String WORK_KICKED = "workKicked";

	private final static String WORK_OTHER = "workOther";

	private final static String WORK_OTHER_INPUT = "workOtherInput";

	private final static String GROUPNAME_NAT_KEY = "nationality";

	private final static String GROUPNAME_CIT_KEY = "citizen";

	private final static String GROUPNAME_FULLTIME_KEY = "fulltime";

	private Form myForm;

	boolean languageSFI = false;

	boolean languageSAS = false;

	boolean languageOTHER = false;

	boolean workUnemp = false;

	boolean workEmp = false;

	boolean workKicked = false;

	boolean studySupport = false;

	String workOther = null;

	int nativeCountryID = -1;

	int icLanguageID = -1;

	boolean isNativeCountry = false;

	boolean isNativeOtherCountry = false;

	boolean isCitizenCountry = false;

	boolean isCitizenOther = false;

	boolean educationHA = false;

	boolean educationHB = false;

	boolean educationHC = false;

	String educationCommune = null;

	boolean educationA = false;

	boolean educationB = false;

	boolean educationC = false;

	boolean educationD = false;

	boolean educationE = false;

	int eduCountryID = -1;

	int eduYears = -1;

	String educationF = null;

	String educationG = null;

	boolean fulltime = false;

	boolean savedBefore = false;

	// /prm
	boolean blanguageSFI = false;

	boolean blanguageSAS = false;

	boolean blanguageOTHER = false;

	boolean bworkUnemp = false;

	boolean bworkEmp = false;

	boolean bworkKicked = false;

	boolean bstudySupport = false;

	String sworkOther = null;

	int inativeCountryID = -1;

	int iicLanguageID = -1;

	boolean bisNativeCountry = false;

	boolean bisNativeOtherCountry = false;

	boolean bisCitizenCountry = false;

	boolean bisCitizenOther = false;

	boolean beducationHA = false;

	boolean beducationHB = false;

	boolean beducationHC = false;

	String seducationCommune = null;

	boolean beducationA = false;

	boolean beducationB = false;

	boolean beducationC = false;

	boolean beducationD = false;

	boolean beducationE = false;

	int ieduCountryID = -1;

	int ieduYears = -1;

	String ieducationF = null;

	//
	public void present(IWContext iwc) {
		try {
			switch (parseAction(iwc)) {
				case ACTION_NONE:
					break;
				case ACTION_STORE:
					savePersonalInfo(iwc);
					break;
			}
			this.iwb = getBundle(iwc);
			this.iwrb = getResourceBundle(iwc);
			try {
				add(getPersonalInfoForm(iwc, this.student));
			}
			catch (Exception e) {
				log(e);
			}
		}
		catch (Exception re) {
			throw new IBORuntimeException(re);
		}
	}

	private void savePersonalInfo(IWContext iwc) throws RemoteException {
		boolean blanguageSFI = iwc.isParameterSet(LANGUAGE_SFI);
		boolean blanguageSAS = iwc.isParameterSet(LANGUAGE_SAS);
		boolean blanguageOTHER = iwc.isParameterSet(LANGUAGE_OTHER);
		boolean bworkUnemp = iwc.isParameterSet(WORK_UNEMP);
		boolean bworkEmp = iwc.isParameterSet(WORK_EMP);
		boolean bworkKicked = iwc.isParameterSet(WORK_KICKED);
		boolean bstudySupport = iwc.isParameterSet(STUDY_SUPPORT);
		String sworkOther = iwc.isParameterSet(WORK_OTHER_INPUT) ? iwc.getParameter(WORK_OTHER_INPUT) : null;
		String inativeCountryID = iwc.isParameterSet(IC_COUNTRY) ? iwc.getParameter(IC_COUNTRY) : null;
		String iicLanguageID = iwc.isParameterSet(IC_LANGUAGE) ? iwc.getParameter(IC_LANGUAGE) : null;
		boolean beducationHA = iwc.isParameterSet(EDUCATION_HA);
		boolean beducationHB = iwc.isParameterSet(EDUCATION_HB);
		boolean beducationHC = iwc.isParameterSet(EDUCATION_HC);
		String seducationCommune = iwc.isParameterSet(EDUCATION_HCOMMUNE) ? iwc.getParameter(EDUCATION_HCOMMUNE) : null;
		boolean beducationA = iwc.isParameterSet(EDUCATION_A);
		boolean beducationB = iwc.isParameterSet(EDUCATION_B);
		boolean beducationC = iwc.isParameterSet(EDUCATION_C);
		boolean beducationD = iwc.isParameterSet(EDUCATION_D);
		boolean beducationE = iwc.isParameterSet(EDUCATION_E);
		String seducationF = iwc.isParameterSet(EDUCATION_F_INPUT) ? iwc.getParameter(EDUCATION_F_INPUT) : null;
		String seducationG = iwc.isParameterSet(EDUCATION_G_INPUT) ? iwc.getParameter(EDUCATION_G_INPUT) : null;
		String ieduCountryID = iwc.isParameterSet(EDU_COUNTRY) ? iwc.getParameter(EDU_COUNTRY) : null;
		String ieduYears = iwc.isParameterSet(EDU_YEARS) ? iwc.getParameter(EDU_YEARS) : null;
		int intNativeCountryID = Integer.parseInt(inativeCountryID);
		int intIcLanguageID = Integer.parseInt(iicLanguageID);
		int inteduCountryID = Integer.parseInt(ieduCountryID);
		int intEduYears = Integer.parseInt(ieduYears);
		boolean bfullTime = false;
		if (iwc.isParameterSet(GROUPNAME_FULLTIME_KEY)) {
			bfullTime = iwc.getParameter(GROUPNAME_FULLTIME_KEY).equalsIgnoreCase(Boolean.TRUE.toString());
		}
		boolean bisNativeCountry = false;
		if (iwc.isParameterSet(GROUPNAME_NAT_KEY)) {
			bisNativeCountry = iwc.getParameter(GROUPNAME_NAT_KEY).equalsIgnoreCase(Boolean.TRUE.toString());
		}
		boolean bisCitizenCountry = false;
		if (iwc.isParameterSet(GROUPNAME_CIT_KEY)) {
			bisCitizenCountry = iwc.getParameter(GROUPNAME_CIT_KEY).equalsIgnoreCase(Boolean.TRUE.toString());
		}
		try {
			getBusiness().storePersonalInfo(this.studentId, intNativeCountryID, intIcLanguageID, inteduCountryID,
					bisNativeCountry, bisCitizenCountry, beducationA, beducationB, beducationC, beducationD, beducationE,
					seducationF, seducationG, inteduCountryID, intEduYears, beducationHA, beducationHB, beducationHC,
					seducationCommune, bfullTime, blanguageSFI, blanguageSAS, blanguageOTHER, bstudySupport, bworkUnemp,
					bworkEmp, bworkKicked, sworkOther);
			if (isInWindow()) {
				getParentPage().setParentToReload();
				getParentPage().close();
			}
			else {
				if (getResponsePage() != null) {
					iwc.forwardToIBPage(getParentPage(), getResponsePage());
				}
				else {
					add(getSmallHeader(localize("choice_stored", "Choice stored.")));
				}
			}
		}
		catch (RemoteException re) {
			re.printStackTrace();
			add(getSmallErrorText(localize("personal_info_failed", "Personal info store failed.")));
			add(getPersonalInfoForm(iwc, this.student));
		}
	}

	private int parseAction(IWContext iwc) {
		try {
			if (iwc.isLoggedOn()) {
				this.userbuiz = (UserBusiness) IBOLookup.getServiceInstance(iwc, UserBusiness.class);
				if (isInWindow()) {
					this.student = getSession().getStudent();
					this.studentId = ((Integer) this.student.getPrimaryKey()).intValue();
				}
				else {
					this.studentId = iwc.getCurrentUserId();
					this.student = this.userbuiz.getUser(this.studentId);
				}
				this.iAction = ACTION_NONE;
				if (iwc.isParameterSet(PARAMETER_ACTION)) {
					this.iAction = Integer.parseInt(iwc.getParameter(PARAMETER_ACTION));
				}
				AdultEducationPersonalInfo aePI = null;
				try {
					AdultEducationBusiness aeBusiness = getBusiness();
					aePI = aeBusiness.getAdultEducationPersonalHome().findByUserId(((Integer) this.student.getPrimaryKey()));
				}
				catch (FinderException fe) {
					log(fe);
				}
				catch (RemoteException re) {
					log(re);
				}
				if (aePI != null) {
					this.savedBefore = true; // user has info stored already
					this.studySupport = aePI.getStudySupport();
					this.languageSFI = aePI.getLangSFI();
					this.languageSAS = aePI.getLangSAS();
					this.languageOTHER = aePI.getLangOTHER();
					this.workUnemp = aePI.getWorkUnEmploy();
					this.workEmp = aePI.getWorkEmploy();
					this.workKicked = aePI.getWorkKicked();
					this.workOther = aePI.getWorkOther();
					this.isNativeCountry = aePI.getNativeThisCountry();
					if (!this.isNativeCountry) {
						this.isNativeOtherCountry = true;
					}
					this.icLanguageID = aePI.getIcLanguageID();
					this.isCitizenCountry = aePI.getCitizenThisCountry();
					if (!this.isCitizenCountry) {
						this.isCitizenOther = true;
					}
					this.nativeCountryID = aePI.getNativeCountryID();
					this.educationHA = aePI.getEduHA();
					this.educationHB = aePI.getEduHB();
					this.educationHC = aePI.getEduHC();
					this.educationCommune = aePI.getEduHCommune();
					this.educationA = aePI.getEduA();
					this.educationB = aePI.getEduB();
					this.educationC = aePI.getEduC();
					this.educationD = aePI.getEduD();
					this.educationE = aePI.getEduE();
					this.educationF = aePI.getEduF();
					this.educationG = aePI.getEduG();
					this.eduCountryID = aePI.getEducationCountryID();
					this.eduYears = aePI.getEduGYears();
					this.fulltime = aePI.getFulltime();
					this.sworkOther = aePI.getWorkOther();
				}
				// /
				if (iwc.isParameterSet(LANGUAGE_SFI)) {
					this.languageSFI = iwc.isParameterSet(LANGUAGE_SFI);
				}
				if (iwc.isParameterSet(LANGUAGE_SAS)) {
					this.languageSAS = iwc.isParameterSet(LANGUAGE_SAS);
				}
				if (iwc.isParameterSet(LANGUAGE_OTHER)) {
					this.languageOTHER = iwc.isParameterSet(LANGUAGE_OTHER);
				}
				if (iwc.isParameterSet(WORK_UNEMP)) {
					this.workUnemp = iwc.isParameterSet(WORK_UNEMP);
				}
				if (iwc.isParameterSet(WORK_EMP)) {
					this.workEmp = iwc.isParameterSet(WORK_EMP);
				}
				if (iwc.isParameterSet(WORK_KICKED)) {
					this.bworkKicked = iwc.isParameterSet(WORK_KICKED);
				}
				if (iwc.isParameterSet(STUDY_SUPPORT)) {
					this.studySupport = iwc.isParameterSet(STUDY_SUPPORT);
				}
				if (iwc.isParameterSet(WORK_OTHER_INPUT)) {
					this.workOther = iwc.getParameter(WORK_OTHER_INPUT);
				}
				if (iwc.isParameterSet(IC_COUNTRY)) {
					String nativeCountryID = iwc.getParameter(IC_COUNTRY);
					this.inativeCountryID = Integer.parseInt(nativeCountryID);
				}
				if (iwc.isParameterSet(IC_COUNTRY)) {
					String cLanguageID = iwc.getParameter(IC_LANGUAGE);
					this.iicLanguageID = Integer.parseInt(cLanguageID);
				}
				if (iwc.isParameterSet(GROUPNAME_NAT_KEY)) {
					this.isNativeCountry = iwc.getParameter(GROUPNAME_NAT_KEY).equalsIgnoreCase(Boolean.TRUE.toString());
				}
				if (iwc.isParameterSet(GROUPNAME_CIT_KEY)) {
					this.isCitizenCountry = iwc.getParameter(GROUPNAME_CIT_KEY).equalsIgnoreCase(Boolean.TRUE.toString());
				}
				if (iwc.isParameterSet(EDUCATION_HA)) {
					this.beducationHA = iwc.isParameterSet(EDUCATION_HA);
				}
				if (iwc.isParameterSet(EDUCATION_HB)) {
					this.beducationHB = iwc.isParameterSet(EDUCATION_HB);
				}
				if (iwc.isParameterSet(EDUCATION_HC)) {
					this.beducationHC = iwc.isParameterSet(EDUCATION_HC);
				}
				if (iwc.isParameterSet(EDUCATION_HCOMMUNE)) {
					this.seducationCommune = iwc.getParameter(EDUCATION_HCOMMUNE);
				}
				if (iwc.isParameterSet(EDUCATION_A)) {
					this.beducationA = iwc.isParameterSet(EDUCATION_A);
				}
				if (iwc.isParameterSet(EDUCATION_B)) {
					this.beducationB = iwc.isParameterSet(EDUCATION_B);
				}
				if (iwc.isParameterSet(EDUCATION_C)) {
					this.beducationC = iwc.isParameterSet(EDUCATION_C);
				}
				if (iwc.isParameterSet(EDUCATION_D)) {
					this.beducationD = iwc.isParameterSet(EDUCATION_D);
				}
				if (iwc.isParameterSet(EDUCATION_E)) {
					this.beducationE = iwc.isParameterSet(EDUCATION_E);
				}
				if (iwc.isParameterSet(EDUCATION_F_INPUT)) {
					this.educationF = iwc.getParameter(EDUCATION_F_INPUT);
				}
				if (iwc.isParameterSet(EDUCATION_G_INPUT)) {
					this.educationG = iwc.getParameter(EDUCATION_G_INPUT);
				}
				if (iwc.isParameterSet(EDU_COUNTRY)) {
					String ieduCountryID = iwc.getParameter(EDU_COUNTRY);
					this.eduCountryID = Integer.parseInt(ieduCountryID);
				}
				if (iwc.isParameterSet(EDU_YEARS)) {
					String seduYears = iwc.getParameter(EDU_YEARS);
					this.eduYears = Integer.parseInt(seduYears);
				}
				if (iwc.isParameterSet(GROUPNAME_FULLTIME_KEY)) {
					this.fulltime = iwc.getParameter(GROUPNAME_FULLTIME_KEY).equalsIgnoreCase(Boolean.TRUE.toString());
				}
				// /
			}
			else if (!iwc.isLoggedOn()) {
				add(getLocalizedHeader("persInfo.need_to_be_logged_on", "You need to log in"));
			}
			return this.iAction;
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
	}

	public UIComponent getPersonalInfoForm(IWContext iwc, User student) throws java.rmi.RemoteException {
		this.myForm = new Form();
		this.myForm.setName(this.prmForm);
		this.myForm.maintainParameter(PARAMETER_UNIQUE_ID);
		Table T = new Table();
		T.setCellpadding(0);
		T.setCellspacing(0);
		T.setBorder(0);
		T.setWidth(520);
		this.myForm.add(T);
		int row = 1;
		T.add(getStudentPersonalInfo(iwc, student), 1, row++);
		T.add(getNationalityTable(), 1, row++);
		HorizontalRule hr = new HorizontalRule();
		hr.setHeight(1);
		T.add(hr, 1, row++);
		T.setHeight(row++, 12);
		// T.add(getChoiceSchool(), 1, row++);
		T.add(getCountryEducationTable(), 1, row++);
		T.setHeight(row, 5);
		T.add(hr, 1, row++);
		// add table with messagePart and submit button
		T.setAlignment(1, row, Table.HORIZONTAL_ALIGN_RIGHT);
		SubmitButton button = (SubmitButton) getButton(new SubmitButton(localize("pInfo.store", "Store"), PARAMETER_ACTION,
				String.valueOf(ACTION_STORE)));
		T.add(button, 1, row);
		button.setOnClick("this.form.sch_app_real_submit.value==1;");
		button.setOnSubmitFunction("checkApplication", getPersonalInfoCheckScript());
		this.myForm.setToDisableOnSubmit(button, true);
		// //
		T.add(new HiddenInput(this.prmAction, "true"), 1, 1);
		T.add(new HiddenInput(this.prmYearReload, "-1"), 1, 1);
		T.add(new HiddenInput(this.prmRealSubmit, "-1"), 1, 1);
		T.add(new HiddenInput(this.prmChildId, student.getPrimaryKey().toString()), 1, 1);
		/*
		 * Page p = this.getParentPage(); if (p != null) { Script S =
		 * p.getAssociatedScript(); }
		 */
		return this.myForm;
	}

	private PresentationObject getStudentPersonalInfo(IWContext iwc, User student) throws java.rmi.RemoteException {
		Table table = new Table();
		table.setColumns(3);
		table.setWidth("100%");
		table.setWidth(2, "8");
		table.setWidth(4, "16");
		// table.setWidth(5, 3, "120");
		table.setCellpadding(2);
		table.setCellspacing(0);
		table.setCellpaddingLeft(1, 1, 6);
		table.setCellpaddingLeft(1, 2, 6);
		table.setCellpaddingLeft(1, 3, 6);
		table.setCellpaddingLeft(1, 4, 6);
		table.setCellpaddingLeft(1, 5, 6);
		table.setCellpaddingRight(5, 1, 6);
		table.setCellpaddingTop(1, 1, 6);
		table.setCellpaddingTop(3, 1, 6);
		// table.setCellpaddingTop(1, 4, 6);
		// table.setCellpaddingTop(4, 4, 6);
		table.setVerticalAlignment(5, 1, Table.VERTICAL_ALIGN_TOP);
		table.setStyleAttribute("background:#f4f4f4;");
		table.setStyleAttribute("border:1px solid #cccccc;");
		table.setBorder(0);
		int row = 1;
		table.add(getSmallHeader(this.iwrb.getLocalizedString("persInfo.name", "Name") + ":"), 1, row++);
		table.add(getSmallHeader(this.iwrb.getLocalizedString("persInfo.personal_id", "Personal ID") + ":"), 1, row++);
		table.add(getSmallHeader(this.iwrb.getLocalizedString("persInfo.address", "Address") + ":"), 1, row);
		table.add(getSmallHeader(this.iwrb.getLocalizedString("persInfo.commune", "Commune") + ":"), 5, row);
		row = 1;
		Name name = new Name(student.getFirstName(), student.getMiddleName(), student.getLastName());
		table.add(getSmallText(name.getName(iwc.getApplicationSettings().getDefaultLocale(), true)), 3, row++);
		String personalID = PersonalIDFormatter.format(student.getPersonalID(),
				iwc.getIWMainApplication().getSettings().getApplicationLocale());
		table.add(getSmallText(personalID), 3, row++);
		Address studentAddress = this.userbuiz.getUsersMainAddress(student);
		if (studentAddress != null) {
			table.add(getSmallText(studentAddress.getStreetAddress()), 3, row);
			if (studentAddress.getPostalAddress() != null) {
				table.add(getSmallText(", " + studentAddress.getPostalAddress()), 3, row);
			}
		}
		Commune homeCommune;
		try {
			CommuneHome home = (CommuneHome) IDOLookup.getHome(Commune.class);
			Integer homeCommuneID = new Integer((studentAddress.getCommuneID()));
			homeCommune = home.findByPrimaryKey(homeCommuneID);
		}
		catch (Exception fe) {
			homeCommune = null;
		}
		if (homeCommune != null) {
			table.add(Text.NON_BREAKING_SPACE, 5, row);
			table.add(getSmallText(homeCommune.getCommuneName()), 5, row);
		}
		/*
		 * Address coAddress = userbuiz.getUsersCoAddress(student); if (coAddress !=
		 * null ) { //a check to see if the c/o address is active is needed if
		 * (!coAddress.getStreetAddress().equals("")){
		 * table.add(getSmallHeader(iwrb.getLocalizedString("persInfo.coaddress",
		 * "C/O address") + ":"), 1, row);
		 * table.add(getSmallText(coAddress.getStreetAddress()), 3, row); }
		 * 
		 * if (coAddress.getPostalAddress() != null &&
		 * !coAddress.getPostalAddress().equals("")) { table.add(getSmallText(", " +
		 * coAddress.getPostalAddress()), 3, row++); } }
		 */
		return table;
	}

	private PresentationObject getNationalityTable() {
		Table table = new Table();
		table.setColumns(3);
		table.setCellpadding(2);
		table.setCellspacing(0);
		table.setBorder(0);
		table.setCellpaddingTop(1, 1, 6);
		// table.setWidth(1, 2, "100");
		table.setWidth(2, 2, "5");
		final RadioButton rbNative1 = getRadioButton(GROUPNAME_NAT_KEY, Boolean.TRUE.toString());
		final RadioButton rbNative2 = getRadioButton(GROUPNAME_NAT_KEY, Boolean.FALSE.toString());
		rbNative1.setMustBeSelected(this.iwrb.getLocalizedString("persInfo.must_select_nationality",
				"You have to set nationality"));
		if (this.savedBefore && this.isNativeCountry) {
			rbNative1.setSelected();
		}
		else if (this.savedBefore && !this.isNativeCountry) {
			rbNative2.setSelected();
		}
		else {
			rbNative1.setSelected(false);
			rbNative2.setSelected(false);
		}
		final RadioButton rbCitizen1 = getRadioButton(GROUPNAME_CIT_KEY, Boolean.TRUE.toString());
		final RadioButton rbCitizen2 = getRadioButton(GROUPNAME_CIT_KEY, Boolean.FALSE.toString());
		rbCitizen1.setMustBeSelected(this.iwrb.getLocalizedString("persInfo.must_select_citizen",
				"You have to set your citizenship"));
		if (this.savedBefore && this.isCitizenCountry) {
			rbCitizen1.setSelected();
		}
		else if (this.savedBefore && !this.isCitizenCountry) {
			rbCitizen2.setSelected();
		}
		else {
			rbCitizen1.setSelected(false);
			rbCitizen2.setSelected(false);
		}
		int row = 1;
		table.mergeCells(1, 1, table.getColumns(), row);
		table.add(Text.BREAK, 1, row);
		table.add(getLocalizedSmallHeader("persInfo.nationality", "Nationality"), 1, row++);
		table.add(rbNative1, 1, row);
		table.add(getLocalizedSmallText("persInfo.nationality_this", "Nationality the country"), 1, row++);
		table.mergeCells(1, row, table.getColumns(), row);
		table.add(rbNative2, 1, row);
		table.add(getLocalizedSmallText("persInfo.nationality_other", "Nationality other country"), 1, row);
		table.add(Text.NON_BREAKING_SPACE, 1, row);
		table.add(Text.NON_BREAKING_SPACE, 1, row);
		table.add(getCountryDropdown(IC_COUNTRY, this.nativeCountryID), 1, row++);
		row++;
		table.add(getLocalizedSmallHeader("persInfo.mother_tongue", "Mother tongue"), 1, row);
		table.add(getLocalizedSmallText("persInfo.mother_tongue_info", "(if not swedish)"), 1, row++);
		table.add(getNativeLanguagesDropdown(this.icLanguageID), 1, row);
		table.mergeCells(1, row, 4, row);
		row++;
		row++;
		table.add(getLocalizedSmallHeader("persInfo.citizenship", "Citizenship"), 1, row++);
		table.add(rbCitizen1, 1, row);
		table.add(getLocalizedSmallText("persInfo.citizen_this", "Citizen the country"), 1, row++);
		table.add(rbCitizen2, 1, row);
		table.add(getLocalizedSmallText("persInfo.citizen_other", "Citizen other country"), 1, row);
		return table;
	}

	private PresentationObject getCountryDropdown(String name, int countryID) {
		CountryHome cHome = null;
		try {
			cHome = (CountryHome) IDOLookup.getHome(Country.class);
		}
		catch (IDOLookupException idoLE) {
			log(idoLE);
		}
		DropdownMenu drpCountry = null;
		try {
			if (cHome != null) {
				Collection countries = cHome.findAll();
				drpCountry = (DropdownMenu) getStyledInterface(new DropdownMenu(countries, name));
				drpCountry.addMenuElementFirst("-1", localize("persInfo.drp_choose_country", "- Choose country -"));
				drpCountry.setSelectedElement(countryID);
			}
		}
		catch (FinderException fe) {
			log(fe);
		}
		return drpCountry;
	}

	private PresentationObject getCountryEducationTable() {
		Table table = new Table();
		table.setColumns(3);
		table.setCellpadding(2);
		table.setCellspacing(0);
		table.setBorder(0);
		table.setHeight("10");
		table.mergeCells(3, 1, 3, 10);
		table.setCellpaddingTop(1, 1, 6);
		table.setWidth(1, 2, "100");
		table.setWidth(2, 2, "5");
		final CheckBox cbEducationA = getCheckBox(EDUCATION_A, Boolean.TRUE.toString());
		cbEducationA.setChecked(this.educationA);
		final CheckBox cbEducationB = getCheckBox(EDUCATION_B, Boolean.TRUE.toString());
		cbEducationB.setChecked(this.educationB);
		final CheckBox cbEducationC = getCheckBox(EDUCATION_C, Boolean.TRUE.toString());
		cbEducationC.setChecked(this.educationC);
		final CheckBox cbEducationD = getCheckBox(EDUCATION_D, Boolean.TRUE.toString());
		cbEducationD.setChecked(this.educationD);
		final CheckBox cbEducationE = getCheckBox(EDUCATION_E, Boolean.TRUE.toString());
		cbEducationE.setChecked(this.educationE);
		final CheckBox cbEducationF = getCheckBox(EDUCATION_F, Boolean.TRUE.toString());
		if (this.educationF != null && !this.educationF.equals("")) {
			cbEducationF.setChecked(true);
		}
		TextInput inputEducationF = (TextInput) getStyledInterface(new TextInput(EDUCATION_F_INPUT));
		if (this.educationF != null && !this.educationF.equals("")) {
			inputEducationF.setContent(this.educationF);
		}
		TextInput inputEducationG = (TextInput) getStyledInterface(new TextInput(EDUCATION_G_INPUT));
		if (this.educationG != null && !this.educationG.equals("")) {
			inputEducationG.setContent(this.educationG);
		}
		if (!cbEducationF.isEmpty()) {
			inputEducationF.setAsNotEmpty(this.iwrb.getLocalizedString("persInfo.what_educationF",
					"You have to fill in what education"));
		}
		int row = 1;
		table.add(getLocalizedSmallHeader("persInfo.thisCountryEducation", "Education in this country"), 1, row++);
		table.add(cbEducationA, 1, row);
		table.add(getLocalizedSmallText("persInfo.education_A", "Education A"), 1, row++);
		table.add(cbEducationB, 1, row);
		table.add(getLocalizedSmallText("persInfo.education_B", "Education B"), 1, row++);
		table.add(cbEducationC, 1, row);
		table.add(getLocalizedSmallText("persInfo.education_C", "Education C"), 1, row++);
		table.add(cbEducationD, 1, row);
		table.add(getLocalizedSmallText("persInfo.education_D", "Education D"), 1, row++);
		table.add(cbEducationE, 1, row);
		table.add(getLocalizedSmallText("persInfo.education_E", "Education E"), 1, row++);
		table.add(cbEducationF, 1, row);
		table.add(getLocalizedSmallText("persInfo.education_F", "Education F"), 1, row);
		table.add(Text.NON_BREAKING_SPACE, 1, row);
		table.add(inputEducationF, 1, row++);
		table.add(getLocalizedSmallHeader("persInfo.otherCountryEducation", "Education other country"), 1, row++);
		table.add(getLocalizedSmallText("persInfo.education_G", "Education G"), 1, row);
		table.add(Text.NON_BREAKING_SPACE, 1, row);
		table.add(inputEducationG, 1, row++);
		Table tableDropdwns = new Table();
		tableDropdwns.setColumns(3);
		tableDropdwns.setCellpadding(2);
		tableDropdwns.setCellspacing(0);
		tableDropdwns.setWidth(1, 2, "5");
		int rowT = 1;
		int i = 1;
		tableDropdwns.add(getLocalizedSmallText("persInfo.education_G_Country", "Other country"), 1, rowT);
		tableDropdwns.add(getLocalizedSmallText("persInfo.education_G_Years", "Total years"), 3, rowT++);
		tableDropdwns.add(getCountryDropdown(EDU_COUNTRY, this.eduCountryID), 1, rowT);
		DropdownMenu menuYears = (DropdownMenu) getStyledInterface(new DropdownMenu(EDU_YEARS));
		menuYears.addMenuElementFirst("-1", "--");
		for (i = 1; i <= 20; i++) {
			Integer j = new Integer(i);
			menuYears.addMenuElement(i, j.toString());
		}
		menuYears.setSelectedElement(this.eduYears);
		tableDropdwns.add(menuYears, 3, rowT);
		table.add(getRightTable(), 3, 1);
		table.setVerticalAlignment(3, row, Table.VERTICAL_ALIGN_TOP);
		table.add(tableDropdwns, 1, row++);
		table.add(getEarlierStudiesTable(), 1, row);
		return table;
	}

	private PresentationObject getRightTable() {
		Table table = new Table();
		table.setColumns(1);
		table.setCellpadding(2);
		table.setCellspacing(0);
		table.setBorder(0);
		int row = 1;
		final CheckBox cbLanguageSFI = getCheckBox(LANGUAGE_SFI, Boolean.TRUE.toString());
		cbLanguageSFI.setChecked(this.languageSFI);
		final CheckBox cbLanguageSAS = getCheckBox(LANGUAGE_SAS, Boolean.TRUE.toString());
		cbLanguageSAS.setChecked(this.languageSAS);
		final CheckBox cbLanguageOther = getCheckBox(LANGUAGE_OTHER, Boolean.TRUE.toString());
		cbLanguageOther.setChecked(this.languageOTHER);
		final CheckBox cbWorkUnmep = getCheckBox(WORK_UNEMP, Boolean.TRUE.toString());
		cbWorkUnmep.setChecked(this.workUnemp);
		final CheckBox cbWorkEmp = getCheckBox(WORK_EMP, Boolean.TRUE.toString());
		cbWorkEmp.setChecked(this.workEmp);
		final CheckBox cbWorkKick = getCheckBox(WORK_KICKED, Boolean.TRUE.toString());
		cbWorkKick.setChecked(this.workKicked);
		final CheckBox cbWorkOther = getCheckBox(WORK_OTHER, Boolean.TRUE.toString());
		if (this.workOther != null) {
			cbWorkOther.setChecked(true);
		}
		final CheckBox cbStudySupport = getCheckBox(STUDY_SUPPORT, Boolean.TRUE.toString());
		cbStudySupport.setChecked(this.studySupport);
		TextInput inputWorkOther = (TextInput) getStyledInterface(new TextInput(WORK_OTHER_INPUT));
		if (this.sworkOther != null && !this.sworkOther.equals("")) {
			inputWorkOther.setContent(this.sworkOther);
		}
		table.add(getLocalizedSmallHeader("persInfo.otherMotherTongueClasses", "Other mother tongue, which classes?"), 1,
				row++);
		table.add(cbLanguageSFI, 1, row);
		table.add(getLocalizedSmallText("persInfo.langSFI", "Language SFI"), 1, row++);
		table.add(cbLanguageSAS, 1, row);
		table.add(getLocalizedSmallText("persInfo.langSAS", "Language SAS"), 1, row++);
		table.add(cbLanguageOther, 1, row);
		table.add(getLocalizedSmallText("persInfo.langOther", "Language Other"), 1, row++);
		table.setHeight(1, row, "2");
		row++;
		table.add(getLocalizedSmallHeader("persInfo.study_support", "Study support"), 1, row++);
		table.add(cbStudySupport, 1, row);
		table.add(getLocalizedSmallText("persInfo.want_study_support", "I want study support"), 1, row++);
		table.setHeight(1, row, "2");
		row++;
		table.add(getLocalizedSmallHeader("persInfo.work_situation", "Work situation"), 1, row++);
		table.add(cbWorkUnmep, 1, row);
		table.add(getLocalizedSmallText("persInfo.work_unemployed", "Unemployed"), 1, row++);
		table.add(cbWorkEmp, 1, row);
		table.add(getLocalizedSmallText("persInfo.work_employed", "Employed"), 1, row++);
		table.add(cbWorkKick, 1, row);
		table.add(getLocalizedSmallText("persInfo.work_kicked", "Fired"), 1, row++);
		table.add(cbWorkOther, 1, row);
		table.add(getLocalizedSmallText("persInfo.work_other", "Work other"), 1, row);
		table.setVerticalAlignment(1, row, Table.VERTICAL_ALIGN_TOP);
		table.add(Text.NON_BREAKING_SPACE, 1, row);
		table.add(inputWorkOther, 1, row);
		return table;
	}

	private PresentationObject getEarlierStudiesTable() {
		Table table = new Table();
		table.setColumns(3);
		table.setCellpadding(2);
		table.setCellspacing(0);
		table.setBorder(0);
		table.setCellpaddingTop(1, 1, 6);
		// table.setWidth(1, 2, "100");
		table.setWidth(2, 2, "5");
		int row = 1;
		final CheckBox cbEducationHA = getCheckBox(EDUCATION_HA, Boolean.TRUE.toString());
		cbEducationHA.setChecked(this.educationHA);
		final CheckBox cbEducationHB = getCheckBox(EDUCATION_HB, Boolean.TRUE.toString());
		cbEducationHB.setChecked(this.educationHB);
		final CheckBox cbEducationHC = getCheckBox(EDUCATION_HC, Boolean.TRUE.toString());
		cbEducationHC.setChecked(this.educationHC);
		TextInput inputStudiedCommune = (TextInput) getStyledInterface(new TextInput(EDUCATION_HCOMMUNE));
		if (this.educationCommune != null) {
			inputStudiedCommune.setContent(this.educationCommune);
		}
		final RadioButton rbFulltime1 = getRadioButton(GROUPNAME_FULLTIME_KEY, Boolean.TRUE.toString());
		final RadioButton rbFulltime2 = getRadioButton(GROUPNAME_FULLTIME_KEY, Boolean.FALSE.toString());
		if (this.savedBefore && this.fulltime) {
			rbFulltime1.setSelected();
		}
		else if (this.savedBefore && !this.fulltime) {
			rbFulltime2.setSelected();
		}
		else {
			rbFulltime1.setSelected(false);
			rbFulltime2.setSelected(false);
		}
		rbFulltime1.setMustBeSelected(this.iwrb.getLocalizedString("persInfo.must_select_full_time",
				"You have to select if you want to study full time or part time"));
		table.add(getLocalizedSmallHeader("persInfo.earlier_studies", "Earlier studies"), 1, row);
		table.mergeCells(1, row, 3, row);
		row++;
		table.add(cbEducationHA, 1, row);
		table.add(getLocalizedSmallText("persInfo.education_HA", "No"), 1, row);
		table.add(cbEducationHB, 3, row);
		table.setNoWrap(3, row);
		table.add(getLocalizedSmallText("persInfo.education_HB", "Yes in this commune"), 3, row++);
		table.add(cbEducationHC, 1, row);
		table.add(getLocalizedSmallText("persInfo.education_HC", "Yes, in other commune"), 1, row);
		table.setNoWrap(1, row);
		table.add(inputStudiedCommune, 3, row++);
		table.setHeight(1, row, "2");
		row++;
		table.add(getLocalizedSmallHeader("persInfo.study_full_part_time", "I will study"), 1, row);
		table.mergeCells(1, row, 3, row);
		row++;
		table.add(rbFulltime1, 1, row);
		table.add(getLocalizedSmallText("persInfo.fulltime", "Fulltime"), 1, row);
		table.add(Text.NON_BREAKING_SPACE, 1, row);
		table.add(rbFulltime2, 1, row);
		table.add(getLocalizedSmallText("persInfo.parttime", "Parttime"), 1, row);
		row++;
		return table;
	}

	protected CheckBox getCheckBox(String name, String value) {
		return (CheckBox) setStyle(new CheckBox(name, value), STYLENAME_CHECKBOX);
	}

	private DropdownMenu getNativeLanguagesDropdown(int icLanguageID) {
		DropdownMenu drop = (DropdownMenu) getStyledInterface(new DropdownMenu(IC_LANGUAGE));
		drop.addMenuElementFirst("-1", localize("persInfo.drp_chose_native_lang", "- Choose languge -"));
		try {
			Collection langs = getICLanguageHome().findAll();
			if (langs != null) {
				for (Iterator iter = langs.iterator(); iter.hasNext();) {
					ICLanguage aLang = (ICLanguage) iter.next();
					int langPK = ((Integer) aLang.getPrimaryKey()).intValue();
					drop.addMenuElement(langPK, aLang.getName());
					drop.setSelectedElement(icLanguageID);
				}
			}
		}
		catch (RemoteException re) {
			re.printStackTrace();
		}
		catch (FinderException fe) {
		}
		return drop;
	}

	public String getPersonalInfoCheckScript() {
		StringBuffer s = new StringBuffer();
		s.append("\nfunction checkApplication(){\n\t");
		s.append("\n\t var dropNativeLang = ").append("findObj('").append(IC_COUNTRY).append("');");
		s.append("\n\t var checkNativeLang = ").append("findObj('").append(GROUPNAME_NAT_KEY).append("');");
		s.append("\n\t var checkEduA = ").append("findObj('").append(EDUCATION_A).append("');");
		s.append("\n\t var checkEduB = ").append("findObj('").append(EDUCATION_B).append("');");
		s.append("\n\t var checkEduC = ").append("findObj('").append(EDUCATION_C).append("');");
		s.append("\n\t var checkEduD = ").append("findObj('").append(EDUCATION_D).append("');");
		s.append("\n\t var checkEduE = ").append("findObj('").append(EDUCATION_E).append("');");
		s.append("\n\t var checkEduF = ").append("findObj('").append(EDUCATION_F).append("');");
		s.append("\n\t var inputEduF = ").append("findObj('").append(EDUCATION_F_INPUT).append("');");
		s.append("\n\t var checkEduHA = ").append("findObj('").append(EDUCATION_HA).append("');");
		s.append("\n\t var checkEduHB = ").append("findObj('").append(EDUCATION_HB).append("');");
		s.append("\n\t var checkEduHC = ").append("findObj('").append(EDUCATION_HC).append("');");
		s.append("\n\t var inputEduHCommune = ").append("findObj('").append(EDUCATION_HCOMMUNE).append("');");
		s.append("\n\t var inputEduG = ").append("findObj('").append(EDUCATION_G_INPUT).append("');");
		s.append("\n\t var dropEduGCountry = ").append("findObj('").append(EDU_COUNTRY).append("');");
		s.append("\n\t var dropEduGYears = ").append("findObj('").append(EDU_YEARS).append("');");
		s.append("\n\t var checkUnempl = ").append("findObj('").append(WORK_UNEMP).append("');");
		s.append("\n\t var checkEmpl = ").append("findObj('").append(WORK_EMP).append("');");
		s.append("\n\t var checkFired = ").append("findObj('").append(WORK_KICKED).append("');");
		s.append("\n\t var checkWorkOther = ").append("findObj('").append(WORK_OTHER).append("');");
		s.append("\n\t var inputWork = ").append("findObj('").append(WORK_OTHER_INPUT).append("');");
		// education
		s.append("\n\n\t if (checkEduA.checked == false && checkEduB.checked == false && checkEduC.checked == false && checkEduD.checked == false && checkEduE.checked == false && checkEduF.checked == false && inputEduG.value == ''){");
		s.append("\n\t\t alert('" + localize("persInfo.must_fill_education", "You must fill previous education") + "');");
		s.append("\n\t\t return false; \n}");
		s.append("\n\n\t else \n{");
		s.append("\n\n\t if (checkEduF.checked == true && inputEduF.value == ''){");
		s.append("\n\t\t alert('" + localize("persInfo.must_fill_education_f", "You must set what education") + "');");
		s.append("\n\t\t return false; \n}");
		s.append("\n\n\t if (inputEduG.value == '' && (dropEduGCountry.options[dropEduGCountry.selectedIndex].value > 0 || dropEduGYears.options[dropEduGYears.selectedIndex].value > 0)) {");
		s.append("\n\t\t alert('"
				+ localize("persInfo.must_fill_education_g_dropdown", "You must set what kind of education for studies abroad")
				+ "');");
		s.append("\n\t\t return false; \n}");
		s.append("\n\n\t else if (inputEduG.value != '' && (dropEduGCountry.options[dropEduGCountry.selectedIndex].value < 0 && dropEduGYears.options[dropEduGYears.selectedIndex].value < 0) ||  (dropEduGYears.options[dropEduGYears.selectedIndex].value > 0 && dropEduGCountry.options[dropEduGCountry.selectedIndex].value < 0 ) || (dropEduGYears.options[dropEduGYears.selectedIndex].value < 0 && dropEduGCountry.options[dropEduGCountry.selectedIndex].value > 0 )){");
		s.append("\n\t\t alert('"
				+ localize("persInfo.must_fill_education_g_dropdown", "You must set country and years for studies abroad")
				+ "');");
		s.append("\n\t\t return false; \n}");
		s.append("\n\n\t else if (inputEduF.value != '' && checkEduF.checked == false){");
		s.append("\n\t\t alert('" + localize("persInfo.must_fill_education_f_checkbox", "You must set education checkbox")
				+ "');");
		s.append("\n\t\t return false; \n}");
		s.append("\n\n\t  \n}");
		// Work situation
		s.append("\n\n\t if (inputWork.value != '' && checkWorkOther.checked == false){");
		s.append("\n\t\t alert('"
				+ localize("persInfo.must_fill_work_other_checkbox", "You must check work other checkbox") + "');");
		s.append("\n\t\t return false; \n}");
		s.append("\n\n\t if (checkWorkOther.checked == true && inputWork.value == ''){");
		s.append("\n\t\t alert('" + localize("persInfo.must_fill_work_other", "You must set work other") + "');");
		s.append("\n\t\t return false; \n}");
		s.append("\n\n\t else if (checkUnempl.checked == false && checkEmpl.checked == false && checkFired.checked == false && checkWorkOther.checked == false){");
		s.append("\n\t\t alert('" + localize("persInfo.must_fill_work_situation", "You must set work situation") + "');");
		s.append("\n\t\t return false; \n}");
		// earlier studies
		s.append("\n\n\t if (checkEduHC.checked == true && inputEduHCommune.value == ''){");
		s.append("\n\t\t alert('" + localize("persInfo.must_fill_other_commune", "You must set what commune") + "');");
		s.append("\n\t\t return false; \n}");
		s.append("\n\n\t else if (inputEduHCommune.value != '' && checkEduHC.checked == false){");
		s.append("\n\t\t alert('"
				+ localize("persInfo.must_fill_other_commune_checkbox", "You must check the chekbox for other commune") + "');");
		s.append("\n\t\t return false; \n}");
		s.append("\n\n\t if (checkEduHA.checked == false && checkEduHB.checked == false && checkEduHC.checked == false){");
		s.append("\n\t\t alert('" + localize("persInfo.must_fill_H_ABC", "You have to set earlier adult studies") + "');");
		s.append("\n\t\t return false; \n}");
		s.append("\n\n\t if (checkEduHA.checked == true && checkEduHB.checked == true){");
		s.append("\n\t\t alert('"
				+ localize("persInfo.cannot_fill_HA_plus", "You cannot choose yes and no in earlier studies") + "');");
		s.append("\n\t\t return false; \n}");
		s.append("\n\n\t else if (checkEduHA.checked == true && checkEduHC.checked == true){");
		s.append("\n\t\t alert('"
				+ localize("persInfo.cannot_fill_HA_plus", "You cannot choose yes and no in earlier studies") + "');");
		s.append("\n\t\t return false; \n}");
		// citizen
		s.append("\n\n\t if (dropNativeLang.options[dropNativeLang.selectedIndex].value > 0 && checkNativeLang[1].checked == false){");
		s.append("\n\t\t alert('"
				+ localize("persInfo.must_fill_native_language",
						"You must check the radiobutton for native language if you want it") + "');");
		s.append("\n\t\t return false; \n}");
		s.append("\n\n\t else if (dropNativeLang.options[dropNativeLang.selectedIndex].value < 0 && checkNativeLang[1].checked == true ){");
		s.append("\n\t\t alert('"
				+ localize("persInfo.must_fill_native_language_drp", "Select a native language in the dropdown ") + "');");
		s.append("\n\t\t return false; \n}");
		s.append("\n\t\t}");
		return s.toString();
	}

	protected RadioButton getRadioButton(String name, String value) {
		return (RadioButton) setStyle(new RadioButton(name, value), STYLENAME_CHECKBOX);
	}

	private ICLanguageHome getICLanguageHome() throws RemoteException {
		return (ICLanguageHome) IDOLookup.getHome(ICLanguage.class);
	}

	public CommuneUserBusiness getCommuneUserBusiness(IWContext iwc) throws RemoteException {
		return (CommuneUserBusiness) IBOLookup.getServiceInstance(iwc, CommuneUserBusiness.class);
	}

	public boolean isInWindow() {
		return this.inWindow;
	}

	public void setInWindow(boolean inWindow) {
		this.inWindow = inWindow;
	}
}
