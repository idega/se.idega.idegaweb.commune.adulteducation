/*
 * $Id: AdultEducationStudentFinder.java,v 1.1 2005/10/19 11:41:33 palli Exp $ Created on Oct
 * 14, 2005
 * 
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 * 
 * This software is the proprietary information of Idega hf. Use is subject to
 * license terms.
 */
package se.idega.idegaweb.commune.adulteducation.presentation;

import java.rmi.RemoteException;

import javax.ejb.EJBException;

import se.idega.idegaweb.commune.presentation.CommuneUserFinder;
import se.idega.idegaweb.commune.school.business.SchoolCommuneBusiness;
import se.idega.idegaweb.commune.school.business.SchoolCommuneSession;
import se.idega.idegaweb.commune.school.event.SchoolEventListener;

import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.business.IBORuntimeException;
import com.idega.presentation.IWContext;
import com.idega.user.data.User;

public class AdultEducationStudentFinder extends CommuneUserFinder {

	/*
	 * (non-Javadoc)
	 * 
	 * @see se.idega.idegaweb.commune.presentation.CommuneUserFinder#addUser(com.idega.presentation.IWContext,
	 *      com.idega.user.data.User)
	 */
	public boolean addUser(IWContext iwc, User user) {
		try {
			return getSchoolBusiness(iwc).isPlacedAtSchool(((Integer) user.getPrimaryKey()).intValue(), getSchoolSession(iwc).getSchoolID());
		}
		catch (RemoteException e) {
			return false;
		}
		catch (EJBException e) {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see se.idega.idegaweb.commune.presentation.CommuneUserFinder#getParameterName(com.idega.presentation.IWContext)
	 */
	public String getParameterName(IWContext iwc) {
		try {
			return getSchoolSession(iwc).getParameterStudentID();
		}
		catch (RemoteException e) {
			throw new IBORuntimeException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see se.idega.idegaweb.commune.presentation.CommuneUserFinder#getParameterName(com.idega.presentation.IWContext)
	 */
	public String getParameterUniqueName(IWContext iwc) {
		try {
			return getSchoolSession(iwc).getParameterStudentID();
		}
		catch (RemoteException e) {
			throw new IBORuntimeException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see se.idega.idegaweb.commune.presentation.CommuneUserFinder#getEventListener()
	 */
	public Class getEventListener() {
		return SchoolEventListener.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see se.idega.idegaweb.commune.presentation.CommuneUserFinder#getSubmitDisplay()
	 */
	public String getSubmitDisplay() {
		return localize("vux_school.show_placings", "Show placings");
	}

	/**
	 * @see se.idega.idegaweb.commune.presentation.CommuneUserFinder#getSearchSubmitDisplay()
	 */
	public String getSearchSubmitDisplay() {
		return localize("vux_school.find_student", "Find student");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see se.idega.idegaweb.commune.presentation.CommuneUserFinder#getNoUserFoundString()
	 */
	public String getNoUserFoundString() {
		return localize("vux_school.no_student_found", "No student found");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see se.idega.idegaweb.commune.presentation.CommuneUserFinder#getFoundUsersString()
	 */
	public String getFoundUsersString() {
		return localize("vux_school.found_students", "Found students");
	}

	private SchoolCommuneBusiness getSchoolBusiness(IWContext iwc) {
		try {
			return (SchoolCommuneBusiness) IBOLookup.getServiceInstance(iwc, SchoolCommuneBusiness.class);
		}
		catch (RemoteException e) {
			throw new IBORuntimeException(e.getMessage());
		}
	}

	private SchoolCommuneSession getSchoolSession(IWContext iwc) {
		try {
			return (SchoolCommuneSession) IBOLookup.getSessionInstance(iwc, SchoolCommuneSession.class);
		}
		catch (IBOLookupException e) {
			throw new IBORuntimeException(e.getMessage());
		}
	}

/*	private AdultEducationBusiness getAdultEducationBusines(IWContext iwc) {
		try {
			return (AdultEducationBusiness) IBOLookup.getServiceInstance(iwc, AdultEducationBusiness.class);
		}
		catch (RemoteException e) {
			throw new IBORuntimeException(e.getMessage());
		}
	}

	private AdultEducationSession getAdultEducationSession(IWContext iwc) {
		try {
			return (AdultEducationSession) IBOLookup.getSessionInstance(iwc, AdultEducationSession.class);
		}
		catch (IBOLookupException e) {
			throw new IBORuntimeException(e.getMessage());
		}
	}*/
}