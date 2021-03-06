/*
 * $Id: AdultEducationBlock.java,v 1.9 2006/04/09 11:41:06 laddi Exp $
 * Created on 27.4.2005
 *
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package se.idega.idegaweb.commune.adulteducation.presentation;

import se.idega.idegaweb.commune.adulteducation.AdultEducationConstants;
import se.idega.idegaweb.commune.adulteducation.business.AdultEducationBusiness;
import se.idega.idegaweb.commune.adulteducation.business.AdultEducationSession;
import se.idega.idegaweb.commune.adulteducation.business.GroupFileWriter;
import se.idega.idegaweb.commune.presentation.CommuneBlock;
import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.business.IBORuntimeException;
import com.idega.idegaweb.IWApplicationContext;
import com.idega.idegaweb.IWMainApplication;
import com.idega.idegaweb.IWUserContext;
import com.idega.io.MediaWritable;
import com.idega.presentation.IWContext;
import com.idega.presentation.Image;
import com.idega.presentation.text.Link;
import com.idega.presentation.ui.Window;

/**
 * Last modified: $Date: 2006/04/09 11:41:06 $ by $Author: laddi $
 * 
 * @author <a href="mailto:laddi@idega.com">laddi</a>
 * @version $Revision: 1.9 $
 */
public abstract class AdultEducationBlock extends CommuneBlock {

	public static final String PARAMETER_CHOICE = "ae_choice";
	public static final String PARAMETER_SCHOOL_SEASON = "ae_school_season";
	public static final String PARAMETER_STUDY_PATH = "ae_study_path";
	public static final String PARAMETER_STUDENT = "ae_student";
	public static final String PARAMETER_COURSE_PACKAGE = "ae_course_package";

	private static final String PROPERTY_RED_COLOR = "red_color";
	
	protected static String RED_COLOR = "#FFE0E0";

	private AdultEducationBusiness business;
	private AdultEducationSession session;
	
	public void main(IWContext iwc) throws Exception {
		setBundle(getBundle(iwc));
		setResourceBundle(getResourceBundle(iwc));
		this.business = getBusiness(iwc);
		this.session = getSession(iwc);
		
		RED_COLOR = getBundle(iwc).getProperty(PROPERTY_RED_COLOR, "#FFE0E0");
		
		present(iwc);
	}
	
	protected Link getPDFLink(Class classToUse, Image image) {
		Link link = new Link(image);
		link.setWindow(getFileWindow(localize("pdf", "PDF")));
		link.addParameter(GroupFileWriter.prmPrintType, GroupFileWriter.PDF);
		link.addParameter(MediaWritable.PRM_WRITABLE_CLASS, IWMainApplication.getEncryptedClassName(classToUse));
		return link;
	}

	protected Link getXLSLink(Class classToUse, Image image) {
		Link link = new Link(image);
		link.setWindow(getFileWindow(localize("xls", "Excel")));
		link.addParameter(GroupFileWriter.prmPrintType, GroupFileWriter.XLS);
		link.addParameter(MediaWritable.PRM_WRITABLE_CLASS, IWMainApplication.getEncryptedClassName(classToUse));
		return link;
	}

	protected AdultEducationBusiness getBusiness() {
		return this.business;
	}
	
	private AdultEducationBusiness getBusiness(IWApplicationContext iwac) {
		try {
			return (AdultEducationBusiness) IBOLookup.getServiceInstance(iwac, AdultEducationBusiness.class);
		}
		catch (IBOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}

	protected AdultEducationSession getSession() {
		return this.session;
	}
	
	protected AdultEducationSession getSession(IWUserContext iwuc) {
		try {
			return (AdultEducationSession) IBOLookup.getSessionInstance(iwuc, AdultEducationSession.class);
		}
		catch (IBOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}

	protected Window getFileWindow(String title) {
		Window w = new Window(title, getIWApplicationContext().getIWMainApplication().getMediaServletURI());
		w.setResizable(true);
		w.setMenubar(true);
		w.setHeight(400);
		w.setWidth(500);
		return w;
	}
	
	public String getBundleIdentifier() {
		return AdultEducationConstants.IW_BUNDLE_IDENTIFIER;
	}
	
	public abstract void present(IWContext iwc);
}