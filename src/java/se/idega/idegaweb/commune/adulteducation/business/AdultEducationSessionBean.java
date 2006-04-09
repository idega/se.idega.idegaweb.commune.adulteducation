/*
 * $Id: AdultEducationSessionBean.java,v 1.14 2006/04/09 11:41:07 laddi Exp $
 * Created on May 24, 2005
 *
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package se.idega.idegaweb.commune.adulteducation.business;

import java.rmi.RemoteException;
import java.sql.Date;
import javax.ejb.FinderException;
import se.idega.idegaweb.commune.accounting.school.business.StudyPathBusiness;
import se.idega.idegaweb.commune.adulteducation.data.AdultEducationChoice;
import se.idega.idegaweb.commune.adulteducation.data.AdultEducationCourse;
import se.idega.idegaweb.commune.adulteducation.data.CoursePackage;
import se.idega.idegaweb.commune.adulteducation.data.SchoolCoursePackage;
import se.idega.idegaweb.commune.business.CommuneUserBusiness;
import com.idega.block.school.business.SchoolBusiness;
import com.idega.block.school.data.School;
import com.idega.block.school.data.SchoolClass;
import com.idega.block.school.data.SchoolClassMember;
import com.idega.block.school.data.SchoolSeason;
import com.idega.block.school.data.SchoolStudyPathGroup;
import com.idega.block.school.data.SchoolType;
import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.business.IBORuntimeException;
import com.idega.business.IBOSessionBean;
import com.idega.user.data.User;
import com.idega.util.IWTimestamp;


/**
 * Last modified: $Date: 2006/04/09 11:41:07 $ by $Author: laddi $
 * 
 * @author <a href="mailto:laddi@idega.com">laddi</a>
 * @version $Revision: 1.14 $
 */
public class AdultEducationSessionBean extends IBOSessionBean  implements AdultEducationSession{
	
	private Object iAdministratorPK = null;
	private School iCurrentSchool = null;
	
	private Object iSeasonPK = null;
	private SchoolSeason iSeason = null;
	
	private Object iCourseSeasonPK = null;
	private SchoolSeason iCourseSeason = null;
	
	private Object iChoicePK = null;
	private AdultEducationChoice iChoice = null;
	
	private String iUserUniqueID = null;
	private User iUser = null;
	private Object iUserPK = null;
	
	private Object iSchoolTypePK = null;
	private SchoolType iSchoolType = null;
	
	private Object iStudyPathGroupPK = null;
	private SchoolStudyPathGroup iStudyPathGroup = null;
	
	private Object iCoursePK = null;
	private AdultEducationCourse iCourse = null;
	
	private Object iSchoolPK = null;
	private School iSchool = null;
	
	private Object iCoursePackagePK = null;
	private CoursePackage iCoursePackage = null;
	
	private Object iSchoolCoursePackagePK = null;
	private SchoolCoursePackage iSchoolCoursePackage = null;
	
	private Object iSchoolClassPK = null;
	private SchoolClass iSchoolClass = null;
	
	private Object iSchoolClassMemberPK = null;
	private SchoolClassMember iSchoolClassMember = null;
	
	private Date iFromDate = null;
	private Date iToDate = null;
	
	private int iSort = -1;

	public SchoolSeason getSchoolSeason() {
		return getSchoolSeason(true);
	}
	
	public SchoolSeason getSchoolSeason(boolean useDefaultIfNotSet) {
		if (this.iSeason == null && this.iSeasonPK != null) {
			try {
				this.iSeason = getSchoolBusiness().getSchoolSeason(new Integer(this.iSeasonPK.toString()));
			}
			catch (RemoteException re) {
				this.iSeason = null;
			}
		}
		else if (this.iSeason == null && useDefaultIfNotSet) {
			try {
				this.iSeason = getSchoolBusiness().getSchoolSeasonHome().findNextSeason(getSchoolBusiness().getCategoryAdultEducation(), new IWTimestamp().getDate());
			}
			catch (FinderException fe) {
				//Nothing found...
			}
			catch (RemoteException re) {
				throw new IBORuntimeException(re);
			}
		}
		return this.iSeason;
	}

	public void setSeason(Object seasonPK) {
		if (this.iSeasonPK != null && !this.iSeasonPK.equals(seasonPK)) {
			setSchoolClass(null);
			setCourse(null);
			setCourseSeason(null);
		}
		this.iSeasonPK = seasonPK;
		this.iSeason = null;
	}
	
	public SchoolSeason getCourseSeason() {
		if (this.iCourseSeason == null && this.iCourseSeasonPK != null) {
			try {
				this.iCourseSeason = getSchoolBusiness().getSchoolSeason(new Integer(this.iCourseSeasonPK.toString()));
			}
			catch (RemoteException re) {
				this.iCourseSeason = null;
			}
		}
		return this.iCourseSeason;
	}

	public void setCourseSeason(Object seasonPK) {
		this.iCourseSeasonPK = seasonPK;
		this.iCourseSeason = null;
	}
	
	public SchoolType getSchoolType() {
		if (this.iSchoolType == null && this.iSchoolTypePK != null) {
			try {
				this.iSchoolType = getSchoolBusiness().getSchoolType(new Integer(this.iSchoolTypePK.toString()));
			}
			catch (RemoteException re) {
				this.iSchoolType = null;
			}
		}
		return this.iSchoolType;
	}

	public void setSchoolType(Object schoolTypePK) {
		this.iSchoolTypePK = schoolTypePK;
		this.iSchoolType= null;
	}
	
	public SchoolStudyPathGroup getStudyPathGroup() {
		if (this.iStudyPathGroup == null && this.iStudyPathGroupPK != null) {
			try {
				this.iStudyPathGroup = getStudyPathBusiness().findStudyPathGroup(new Integer(this.iStudyPathGroupPK.toString()));
			}
			catch (RemoteException re) {
				this.iStudyPathGroup = null;
			}
		}
		return this.iStudyPathGroup;
	}

	public void setStudyPathGroup(Object studyPathGroupPK) {
		if (this.iStudyPathGroupPK != null && !this.iStudyPathGroupPK.equals(studyPathGroupPK)) {
			setSchoolClass(null);
			setCourse(null);
		}
		this.iStudyPathGroupPK = studyPathGroupPK;
		this.iStudyPathGroup= null;
	}
	
	public AdultEducationCourse getCourse() {
		if (this.iCourse == null && this.iCoursePK != null) {
			try {
				this.iCourse = getAdultEducationBusiness().getCourse(this.iCoursePK);
			}
			catch (RemoteException re) {
				this.iCourse = null;
			}
			catch (FinderException fe) {
				fe.printStackTrace();
				this.iCourse = null;
			}
		}
		return this.iCourse;
	}

	public void setCourse(Object coursePK) {
		this.iCoursePK = coursePK;
		this.iCourse= null;
	}
	
	public SchoolClass getSchoolClass() {
		if (this.iSchoolClass == null && this.iSchoolClassPK != null) {
			try {
				this.iSchoolClass = getSchoolBusiness().getSchoolClassHome().findByPrimaryKey(new Integer(this.iSchoolClassPK.toString()));
			}
			catch (RemoteException re) {
				this.iSchoolClass = null;
			}
			catch (FinderException fe) {
				fe.printStackTrace();
				this.iSchoolClass = null;
			}
		}
		return this.iSchoolClass;
	}

	public void setSchoolClass(Object schoolClassPK) {
		this.iSchoolClassPK = schoolClassPK;
		this.iSchoolClass= null;
	}
	
	public School getChosenSchool() {
		if (this.iSchool == null && this.iSchoolPK != null) {
			try {
				this.iSchool = getSchoolBusiness().getSchool(this.iSchoolPK);
			}
			catch (RemoteException re) {
				this.iSchool = null;
			}
		}
		return this.iSchool;
	}

	public void setChosenSchool(Object schoolPK) {
		this.iSchoolPK = schoolPK;
		this.iSchool= null;
	}
	
	public CoursePackage getCoursePackage() {
		if (this.iCoursePackage == null && this.iCoursePackagePK != null) {
			try {
				this.iCoursePackage = getAdultEducationBusiness().getCoursePackage(this.iCoursePackagePK);
			}
			catch (RemoteException re) {
				this.iCoursePackage = null;
			}
			catch (FinderException fe) {
				this.iCoursePackage = null;
			}
		}
		return this.iCoursePackage;
	}

	public void setCoursePackage(Object coursePackagePK) {
		this.iCoursePackagePK = coursePackagePK;
		this.iCoursePackage= null;
	}
	
	public SchoolCoursePackage getSchoolCoursePackage() {
		if (this.iSchoolCoursePackage == null && this.iSchoolCoursePackagePK != null) {
			try {
				this.iSchoolCoursePackage = getAdultEducationBusiness().getSchoolCoursePackage(this.iSchoolCoursePackagePK);
			}
			catch (RemoteException re) {
				this.iSchoolCoursePackage = null;
			}
			catch (FinderException fe) {
				this.iSchoolCoursePackage = null;
			}
		}
		return this.iSchoolCoursePackage;
	}

	public void setSchoolCoursePackage(Object schoolCoursePackagePK) {
		this.iSchoolCoursePackagePK = schoolCoursePackagePK;
		this.iSchoolCoursePackage= null;
	}
	
	public SchoolClassMember getSchoolClassMember() {
		if (this.iSchoolClassMember == null && this.iSchoolClassMemberPK != null) {
			try {
				this.iSchoolClassMember = getSchoolBusiness().getSchoolClassMemberHome().findByPrimaryKey(new Integer(this.iSchoolClassMemberPK.toString()));
			}
			catch (RemoteException re) {
				this.iSchoolClassMember = null;
			}
			catch (FinderException fe) {
				fe.printStackTrace();
				this.iSchoolClassMember = null;
			}
		}
		return this.iSchoolClassMember;
	}

	public void setSchoolClassMember(Object schoolClassMemberPK) {
		this.iSchoolClassMemberPK = schoolClassMemberPK;
		this.iSchoolClassMember= null;
	}
	
	public Date getFromDate() {
		if (this.iFromDate == null) {
			this.iFromDate = new IWTimestamp().getDate();
		}
		return this.iFromDate;
	}
	
	public void setFromDate(Date date) {
		this.iFromDate = date;
	}
	
	public Date getToDate() {
		if (this.iToDate == null) {
			this.iToDate = new IWTimestamp().getDate();
		}
		return this.iToDate;
	}
	
	public void setToDate(Date date) {
		this.iToDate = date;
	}
	
	public int getSort() {
		return this.iSort;
	}
	
	public void setSort(int sort) {
		this.iSort = sort;
	}
	
	public AdultEducationChoice getChoice() {
		if (this.iChoice == null && this.iChoicePK != null) {
			try {
				this.iChoice = getAdultEducationBusiness().getChoice(new Integer(this.iChoicePK.toString()));
			}
			catch (FinderException fe) {
				fe.printStackTrace();
				this.iChoice = null;
			}
			catch (RemoteException re) {
				this.iChoice = null;
			}
		}
		return this.iChoice;
	}

	public void setChoice(Object choicePK) {
		this.iChoicePK = choicePK;
		this.iChoice = null;
	}

	public User getStudent() {
		if (this.iUser == null && this.iUserPK != null) {
			try {
				this.iUser = getUserBusiness().getUser(new Integer(this.iUserPK.toString()));
			}
			catch (RemoteException re) {
				this.iUser = null;
			}
		}
		return this.iUser;
	}

	public void setStudent(String userPK) {
		this.iUserPK = userPK;
		this.iUser = null;
	}

	public void setStudentUniqueID(String userUniqueID) {
		this.iUserUniqueID = userUniqueID;
		try {
			this.iUser = getUserBusiness().getUserByUniqueId(this.iUserUniqueID);
			this.iUserPK = this.iUser.getPrimaryKey();
		}
		catch (FinderException fe) {
			fe.printStackTrace();
			this.iUser = null;
			this.iUserPK = null;
		}
		catch (RemoteException re) {
			this.iUser = null;
			this.iUserPK = null;
		}
	}
	
	public School getSchool() {
		if (getUserContext().isLoggedOn()) {
			User user = getUserContext().getCurrentUser();
			Object userPK = user.getPrimaryKey();
			
			if (this.iAdministratorPK != null && this.iAdministratorPK.equals(userPK)) {
				if (this.iCurrentSchool != null) {
					return this.iCurrentSchool;
				}
				else {
					return getSchoolFromUser(user);
				}
			}
			else {
				this.iAdministratorPK = userPK;
				return getSchoolFromUser(user);
			}
		}
		else {
			return this.iCurrentSchool;	
		}
	}

	/**
	 * Returns the schoolID.
	 * @return int
	 */
	public Object getSchoolPK() {
		if (this.iCurrentSchool != null) {
			return this.iCurrentSchool.getPrimaryKey();
		}
		return null;
	}
	
	private School getSchoolFromUser(User user) {
		if (user != null) {
			try {
				School school = getAdultEducationBusiness().getSchoolForUser(user);
				if (school != null) {
					this.iCurrentSchool = school;
				}
			}
			catch (FinderException fe) {
				fe.printStackTrace();
			}
			catch (RemoteException re) {
				throw new IBORuntimeException(re);
			}
		}
		return this.iCurrentSchool;
	}

	private CommuneUserBusiness getUserBusiness() {
		try {
			return (CommuneUserBusiness) IBOLookup.getServiceInstance(this.getIWApplicationContext(), CommuneUserBusiness.class);
		}
		catch (IBOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}

	private SchoolBusiness getSchoolBusiness() {
		try {
			return (SchoolBusiness) IBOLookup.getServiceInstance(this.getIWApplicationContext(), SchoolBusiness.class);
		}
		catch (IBOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}

	private StudyPathBusiness getStudyPathBusiness() {
		try {
			return (StudyPathBusiness) IBOLookup.getServiceInstance(this.getIWApplicationContext(), StudyPathBusiness.class);
		}
		catch (IBOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}

	private AdultEducationBusiness getAdultEducationBusiness() {
		try {
			return (AdultEducationBusiness) IBOLookup.getServiceInstance(this.getIWApplicationContext(), AdultEducationBusiness.class);
		}
		catch (IBOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}

}