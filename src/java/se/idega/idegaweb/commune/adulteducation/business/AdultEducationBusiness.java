/*
 * $Id: AdultEducationBusiness.java,v 1.20 2005/06/07 12:49:03 laddi Exp $
 * Created on Jun 7, 2005
 *
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package se.idega.idegaweb.commune.adulteducation.business;

import java.sql.Date;
import java.util.Collection;
import java.util.Locale;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;
import se.idega.idegaweb.commune.accounting.school.business.StudyPathBusiness;
import se.idega.idegaweb.commune.adulteducation.data.AdultEducationChoice;
import se.idega.idegaweb.commune.adulteducation.data.AdultEducationCourse;
import se.idega.idegaweb.commune.adulteducation.data.AdultEducationPersonalInfo;
import se.idega.idegaweb.commune.adulteducation.data.AdultEducationPersonalInfoHome;
import se.idega.idegaweb.commune.business.CommuneUserBusiness;
import com.idega.block.process.business.CaseBusiness;
import com.idega.block.process.data.Case;
import com.idega.block.school.business.SchoolBusiness;
import com.idega.block.school.data.School;
import com.idega.block.school.data.SchoolCategory;
import com.idega.block.school.data.SchoolClass;
import com.idega.block.school.data.SchoolClassMember;
import com.idega.block.school.data.SchoolSeason;
import com.idega.block.school.data.SchoolStudyPath;
import com.idega.block.school.data.SchoolStudyPathGroup;
import com.idega.block.school.data.SchoolType;
import com.idega.business.IBOService;
import com.idega.data.IDOCreateException;
import com.idega.user.data.User;


/**
 * Last modified: $Date: 2005/06/07 12:49:03 $ by $Author: laddi $
 * 
 * @author <a href="mailto:laddi@idega.com">laddi</a>
 * @version $Revision: 1.20 $
 */
public interface AdultEducationBusiness extends IBOService, CaseBusiness {

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.business.AdultEducationBusinessBean#getBundleIdentifier
	 */
	public String getBundleIdentifier() throws java.rmi.RemoteException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.business.AdultEducationBusinessBean#getLocalizedCaseDescription
	 */
	public String getLocalizedCaseDescription(Case theCase, Locale locale) throws java.rmi.RemoteException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.business.AdultEducationBusinessBean#getSchoolForUser
	 */
	public School getSchoolForUser(User user) throws FinderException, java.rmi.RemoteException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.business.AdultEducationBusinessBean#getSchoolBusiness
	 */
	public SchoolBusiness getSchoolBusiness() throws java.rmi.RemoteException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.business.AdultEducationBusinessBean#getUserBusiness
	 */
	public CommuneUserBusiness getUserBusiness() throws java.rmi.RemoteException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.business.AdultEducationBusinessBean#getStudyPathBusiness
	 */
	public StudyPathBusiness getStudyPathBusiness() throws java.rmi.RemoteException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.business.AdultEducationBusinessBean#getCourse
	 */
	public AdultEducationCourse getCourse(Object season, String code) throws FinderException, java.rmi.RemoteException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.business.AdultEducationBusinessBean#getCourse
	 */
	public AdultEducationCourse getCourse(Object coursePK) throws FinderException, java.rmi.RemoteException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.business.AdultEducationBusinessBean#getCourses
	 */
	public Collection getCourses(Object season, Object school, Object group) throws java.rmi.RemoteException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.business.AdultEducationBusinessBean#getCourses
	 */
	public Collection getCourses(Object season, Object type, Object school, Object group) throws java.rmi.RemoteException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.business.AdultEducationBusinessBean#getGroups
	 */
	public Collection getGroups(School school, SchoolSeason season, String code) throws java.rmi.RemoteException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.business.AdultEducationBusinessBean#getGroups
	 */
	public Collection getGroups(School school, SchoolSeason season, SchoolStudyPathGroup group)
			throws java.rmi.RemoteException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.business.AdultEducationBusinessBean#storeGroup
	 */
	public void storeGroup(String name, School school, SchoolSeason season, SchoolSeason oldSeason, SchoolType type,
			String code, String oldCode, User teacher, boolean update) throws CreateException, DuplicateValueException,
			java.rmi.RemoteException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.business.AdultEducationBusinessBean#hasActiveChoices
	 */
	public boolean hasActiveChoices(SchoolSeason season, AdultEducationCourse course) throws java.rmi.RemoteException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.business.AdultEducationBusinessBean#getNumberOfActiveChoices
	 */
	public int getNumberOfActiveChoices(SchoolSeason season, AdultEducationCourse course) throws java.rmi.RemoteException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.business.AdultEducationBusinessBean#getChoices
	 */
	public Collection getChoices(SchoolSeason season, AdultEducationCourse course) throws java.rmi.RemoteException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.business.AdultEducationBusinessBean#createDefaultGroup
	 */
	public SchoolClass createDefaultGroup(SchoolSeason season, AdultEducationCourse course)
			throws java.rmi.RemoteException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.business.AdultEducationBusinessBean#getChoice
	 */
	public AdultEducationChoice getChoice(User user, AdultEducationCourse course) throws FinderException,
			java.rmi.RemoteException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.business.AdultEducationBusinessBean#getChoice
	 */
	public AdultEducationChoice getChoice(Object choicePK) throws FinderException, java.rmi.RemoteException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.business.AdultEducationBusinessBean#getChoice
	 */
	public AdultEducationChoice getChoice(User user, Object seasonPK, Object studyPathPK, int choiceOrder)
			throws FinderException, java.rmi.RemoteException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.business.AdultEducationBusinessBean#getChoices
	 */
	public Collection getChoices(User user, SchoolSeason season) throws java.rmi.RemoteException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.business.AdultEducationBusinessBean#getChoices
	 */
	public Collection getChoices(User user, SchoolSeason season, SchoolStudyPath path) throws java.rmi.RemoteException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.business.AdultEducationBusinessBean#getChoices
	 */
	public Collection getChoices(User user) throws java.rmi.RemoteException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.business.AdultEducationBusinessBean#getChoices
	 */
	public Collection getChoices(SchoolSeason season, SchoolType type, Date fromDate, Date toDate, User handler)
			throws java.rmi.RemoteException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.business.AdultEducationBusinessBean#getUnhandledChoices
	 */
	public Collection getUnhandledChoices(SchoolSeason season, SchoolType type, Date fromDate, Date toDate, User handler)
			throws java.rmi.RemoteException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.business.AdultEducationBusinessBean#getHandledChoices
	 */
	public Collection getHandledChoices(SchoolSeason season, SchoolType type, Date fromDate, Date toDate, User handler)
			throws java.rmi.RemoteException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.business.AdultEducationBusinessBean#getSelectedStudyPaths
	 */
	public Collection getSelectedStudyPaths(User user, SchoolSeason season) throws java.rmi.RemoteException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.business.AdultEducationBusinessBean#getAvailableSchools
	 */
	public Collection getAvailableSchools(Object studyPathPK, Object seasonPK) throws java.rmi.RemoteException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.business.AdultEducationBusinessBean#getAvailableCourses
	 */
	public Collection getAvailableCourses(Object seasonPK, Object schoolPK, Object studyPathPK)
			throws java.rmi.RemoteException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.business.AdultEducationBusinessBean#getPendingSeasons
	 */
	public Collection getPendingSeasons() throws java.rmi.RemoteException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.business.AdultEducationBusinessBean#getActiveReasons
	 */
	public Collection getActiveReasons() throws java.rmi.RemoteException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.business.AdultEducationBusinessBean#getCategory
	 */
	public SchoolCategory getCategory() throws FinderException, java.rmi.RemoteException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.business.AdultEducationBusinessBean#getSchoolTypes
	 */
	public Collection getSchoolTypes() throws java.rmi.RemoteException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.business.AdultEducationBusinessBean#getSeasons
	 */
	public Collection getSeasons() throws java.rmi.RemoteException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.business.AdultEducationBusinessBean#getSchools
	 */
	public Collection getSchools(SchoolType type) throws java.rmi.RemoteException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.business.AdultEducationBusinessBean#getStudyPathsGroups
	 */
	public Collection getStudyPathsGroups() throws java.rmi.RemoteException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.business.AdultEducationBusinessBean#getStudyPaths
	 */
	public Collection getStudyPaths(SchoolType type, SchoolStudyPathGroup group) throws java.rmi.RemoteException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.business.AdultEducationBusinessBean#storeCourse
	 */
	public AdultEducationCourse storeCourse(Object season, Object oldSeason, String code, String oldCode, Object school,
			Object studyPath, Date startDate, Date endDate, String comment, int length, boolean notActive, boolean update)
			throws CreateException, DuplicateValueException, java.rmi.RemoteException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.business.AdultEducationBusinessBean#storeChoices
	 */
	public void storeChoices(User user, Collection courses, Object[] oldCourses, String comment, Object[] reasons,
			String otherReason) throws IDOCreateException, java.rmi.RemoteException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.business.AdultEducationBusinessBean#grantChoice
	 */
	public void grantChoice(AdultEducationChoice choice, boolean rule1, boolean rule2, boolean rule3, boolean rule4,
			String ruleNotes, String notes, int priority, User performer) throws java.rmi.RemoteException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.business.AdultEducationBusinessBean#denyChoice
	 */
	public void denyChoice(AdultEducationChoice choice, String rejectionMessage, User performer)
			throws java.rmi.RemoteException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.business.AdultEducationBusinessBean#saveChoiceChanges
	 */
	public void saveChoiceChanges(AdultEducationChoice choice, boolean rule1, boolean rule2, boolean rule3,
			boolean rule4, String ruleNotes, String notes, int priority, User performer) throws java.rmi.RemoteException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.business.AdultEducationBusinessBean#removeCourse
	 */
	public void removeCourse(Object coursePK) throws RemoveException, java.rmi.RemoteException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.business.AdultEducationBusinessBean#removeChoices
	 */
	public void removeChoices(Object studyPathPK, Object seasonPK, User performer) throws java.rmi.RemoteException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.business.AdultEducationBusinessBean#removeChoices
	 */
	public void removeChoices(Object studyPathPK, Object seasonPK, Object userPK, User performer)
			throws java.rmi.RemoteException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.business.AdultEducationBusinessBean#placeChoices
	 */
	public void placeChoices(Object[] choicePKs, SchoolClass group, AdultEducationCourse course, Date date, User performer)
			throws java.rmi.RemoteException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.business.AdultEducationBusinessBean#rejectChoices
	 */
	public void rejectChoices(Object[] choicePKs, User performer) throws java.rmi.RemoteException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.business.AdultEducationBusinessBean#removeStudent
	 */
	public void removeStudent(Object schoolClassMemberPK, Object choicePK, User performer)
			throws java.rmi.RemoteException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.business.AdultEducationBusinessBean#sendPlacementMessage
	 */
	public void sendPlacementMessage(SchoolClass group, AdultEducationCourse course) throws java.rmi.RemoteException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.business.AdultEducationBusinessBean#changeCourse
	 */
	public void changeCourse(AdultEducationChoice choice, Object coursePK) throws java.rmi.RemoteException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.business.AdultEducationBusinessBean#changeGroup
	 */
	public void changeGroup(SchoolClassMember member, Object groupPK) throws java.rmi.RemoteException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.business.AdultEducationBusinessBean#removeGroup
	 */
	public void removeGroup(SchoolClass group) throws java.rmi.RemoteException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.business.AdultEducationBusinessBean#storePersonalInfo
	 */
	public AdultEducationPersonalInfo storePersonalInfo(int icUserID, int nativecountryId, int languageID,
			int educationCountryID, boolean nativeThisCountry, boolean citizenThisCountry, boolean educationA,
			boolean educationB, boolean educationC, boolean educationD, boolean educationE, String educationF,
			String educationG, int eduGCountryID, int eduYears, boolean eduHA, boolean eduHB, boolean eduHC,
			String eduHCommune, boolean fulltime, boolean langSfi, boolean langSas, boolean langOther, boolean studySupport,
			boolean workUnEmpl, boolean workEmpl, boolean workKicked, String workOther) throws java.rmi.RemoteException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.business.AdultEducationBusinessBean#createOverviewPDF
	 */
	public void createOverviewPDF(User user, SchoolSeason season, String path, String fileName, Locale locale)
			throws java.rmi.RemoteException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.business.AdultEducationBusinessBean#getAdultEducationPersonalHome
	 */
	public AdultEducationPersonalInfoHome getAdultEducationPersonalHome() throws java.rmi.RemoteException;
}
