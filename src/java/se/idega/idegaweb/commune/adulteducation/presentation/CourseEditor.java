/*
 * $Id: CourseEditor.java,v 1.12 2006/05/08 13:50:51 laddi Exp $ Created on
 * 27.4.2005
 * 
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 * 
 * This software is the proprietary information of Idega hf. Use is subject to
 * license terms.
 */
package se.idega.idegaweb.commune.adulteducation.presentation;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;
import se.idega.idegaweb.commune.adulteducation.business.DuplicateValueException;
import se.idega.idegaweb.commune.adulteducation.data.AdultEducationCourse;
import com.idega.block.school.business.SchoolBusiness;
import com.idega.block.school.business.SchoolUserBusiness;
import com.idega.block.school.data.School;
import com.idega.block.school.data.SchoolSeason;
import com.idega.block.school.data.SchoolStudyPath;
import com.idega.block.school.data.SchoolStudyPathGroup;
import com.idega.block.school.data.SchoolType;
import com.idega.business.IBOLookup;
import com.idega.business.IBORuntimeException;
import com.idega.presentation.IWContext;
import com.idega.presentation.Table;
import com.idega.presentation.text.Break;
import com.idega.presentation.text.Link;
import com.idega.presentation.text.Text;
import com.idega.presentation.ui.CheckBox;
import com.idega.presentation.ui.DateInput;
import com.idega.presentation.ui.DropdownMenu;
import com.idega.presentation.ui.Form;
import com.idega.presentation.ui.HiddenInput;
import com.idega.presentation.ui.SubmitButton;
import com.idega.presentation.ui.TextInput;
import com.idega.presentation.ui.util.SelectorUtility;
import com.idega.user.data.User;
import com.idega.util.IWTimestamp;

/**
 * Last modified: $Date: 2006/05/08 13:50:51 $ by $Author: laddi $
 * 
 * @author <a href="mailto:laddi@idega.com">laddi</a>
 * @version $Revision: 1.12 $
 */
public class CourseEditor extends AdultEducationBlock {

	private static final String PARAMETER_ACTION = "ce_action";

	private static final String PARAMETER_FROM_ACTION = "ce_from_action";

	private static final String PARAMETER_SCHOOL_TYPE = "ce_school_type";

	private static final String PARAMETER_SCHOOL = "ce_school";

	private static final String PARAMETER_SCHOOL_SEASON_PK = "ce_school_season";

	private static final String PARAMETER_OLD_SCHOOL_SEASON = "ce_old_school_season";

	private static final String PARAMETER_STUDY_PATH_GROUP = "ce_study_path_group";

	private static final String PARAMETER_STUDY_PATH_PK = "ce_study_path";

	private static final String PARAMETER_CODE = "ce_code";

	private static final String PARAMETER_OLD_CODE = "ce_old_code";

	private static final String PARAMETER_START_DATE = "ce_start_date";

	private static final String PARAMETER_END_DATE = "ce_end_date";

	private static final String PARAMETER_COMMENT = "ce_course_comment";

	private static final String PARAMETER_LENGTH = "ce_length";

	private static final String PARAMETER_NOT_ACTIVE = "ce_not_active";

	private static final String PARAMETER_UPDATE = "ce_update";

	private static final String PARAMETER_COURSE_PK = "ce_course_pk";

	private static final int ACTION_DELETE = 1;

	private static final int ACTION_EDIT = 2;

	private static final int ACTION_COPY = 3;

	private static final int ACTION_STORE = 4;

	private int iAction = ACTION_EDIT;

	private AdultEducationCourse iCourse;

	private Object iSchoolTypePK;

	private SchoolType iSchoolType;

	private Object iSchoolPK;

	// private School iSchool;
	private Object iSchoolSeasonPK;

	private SchoolSeason iSchoolSeason;

	private Object iStudyPathGroupPK;

	private SchoolStudyPathGroup iStudyPathGroup;

	public void present(IWContext iwc) {
		try {
			switch (parseAction(iwc)) {
				case ACTION_EDIT:
					break;
				case ACTION_COPY:
					break;
				case ACTION_DELETE:
					remove(iwc);
					break;
				case ACTION_STORE:
					store(iwc);
					break;
			}

			add(getEditor(iwc));
			add(new Break(2));
			add(getCourses(iwc));
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
	}

	private Form getEditor(IWContext iwc) throws RemoteException {
		Form form = new Form();
		form.addParameter(PARAMETER_FROM_ACTION, String.valueOf(this.iAction));
		form.maintainParameter(PARAMETER_OLD_SCHOOL_SEASON);
		form.maintainParameter(PARAMETER_OLD_CODE);

		form.add(getNavigationTable(iwc));
		form.add(new Break(2));
		form.add(getEditorTable());

		return form;
	}

	private Table getNavigationTable(IWContext iwc) throws RemoteException {
		Table table = new Table(4, 3);
		table.setCellpadding(3);
		table.setCellspacing(0);
		// table.setWidth(Table.HUNDRED_PERCENT);

		SelectorUtility util = new SelectorUtility();

		DropdownMenu types = (DropdownMenu) getStyledInterface(util.getSelectorFromIDOEntities(new DropdownMenu(
				PARAMETER_SCHOOL_TYPE), getBusiness().getSchoolTypes(), "getLocalizationKey", getResourceBundle()));
		types.addMenuElementFirst("", localize("select_type", "Select type"));
		if (this.iSchoolTypePK != null) {
			types.setSelectedElement(this.iSchoolTypePK.toString());
		}
		types.setToSubmit();

		School schoolForLoggedInUser = getSchoolByLoggedInUser(iwc);
		DropdownMenu schools = null;
		if (schoolForLoggedInUser != null) {
			Collection schoolList = new ArrayList();
			schoolList.add(schoolForLoggedInUser);
			schools = (DropdownMenu) getStyledInterface(util.getSelectorFromIDOEntities(new DropdownMenu(
					PARAMETER_SCHOOL), schoolList, "getSchoolName"));
			//schools.addMenuElementFirst("", localize("select_school", "Select school"));
			schools.setSelectedElement(((Integer)schoolForLoggedInUser.getPrimaryKey()).toString());
			schools.setToSubmit();
		}
		else {
			schools = (DropdownMenu) getStyledInterface(util.getSelectorFromIDOEntities(new DropdownMenu(
					PARAMETER_SCHOOL), getBusiness().getSchools(this.iSchoolType), "getSchoolName"));
			schools.addMenuElementFirst("", localize("select_school", "Select school"));
			if (this.iSchoolPK != null) {
				schools.setSelectedElement(this.iSchoolPK.toString());
			}
			schools.setToSubmit();
		}

		DropdownMenu groups = (DropdownMenu) getStyledInterface(util.getSelectorFromIDOEntities(new DropdownMenu(
				PARAMETER_STUDY_PATH_GROUP), getBusiness().getStudyPathsGroups(), "getLocalizationKey",
				getResourceBundle()));
		groups.addMenuElementFirst("", localize("select_study_path_group", "Select group"));
		if (this.iStudyPathGroupPK != null) {
			groups.setSelectedElement(this.iStudyPathGroupPK.toString());
		}
		groups.setToSubmit();

		DropdownMenu seasons = (DropdownMenu) getStyledInterface(util.getSelectorFromIDOEntities(new DropdownMenu(
				PARAMETER_SCHOOL_SEASON_PK), getBusiness().getSeasons(), "getSeasonName"));
		seasons.addMenuElementFirst("", localize("select_season", "Select season"));
		if (this.iSchoolSeasonPK != null) {
			seasons.setSelectedElement(this.iSchoolSeasonPK.toString());
		}
		seasons.setToSubmit();

		table.add(getSmallHeader(localize("type", "Type") + ":"), 1, 1);
		table.add(types, 2, 1);
		table.add(getSmallHeader(localize("school", "School") + ":"), 1, 2);
		table.add(schools, 2, 2);
		table.add(getSmallHeader(localize("study_path_group", "Study path group") + ":"), 3, 2);
		table.add(groups, 4, 2);
		table.add(getSmallHeader(localize("season", "Season") + ":"), 1, 3);
		table.add(seasons, 2, 3);

		return table;
	}

	private Table getEditorTable() throws RemoteException {
		Table table = new Table(4, 3);
		table.setCellpadding(3);
		table.setCellspacing(0);
		table.setWidth(Table.HUNDRED_PERCENT);

		SelectorUtility util = new SelectorUtility();

		DropdownMenu studyPaths = (DropdownMenu) getStyledInterface(util.getSelectorFromIDOEntities(new DropdownMenu(
				PARAMETER_STUDY_PATH_PK), getBusiness().getStudyPaths(this.iSchoolType, this.iStudyPathGroup), "getDescription"));
		studyPaths.addMenuElementFirst("", localize("select_study_path", "Select study path"));
		if (this.iCourse != null) {
			studyPaths.setSelectedElement(this.iCourse.getStudyPathPK().toString());
		}
		if (!(this.iAction == ACTION_DELETE || this.iAction == ACTION_STORE)) {
			studyPaths.keepStatusOnAction(true);
		}

		TextInput code = (TextInput) getStyledInterface(new TextInput(PARAMETER_CODE));
		if (this.iCourse != null) {
			code.setContent(this.iCourse.getCode());
			if (hasCoursePlacements(this.iCourse.getCode())) {
				code.setDisabled(true);
			}
		}
		if (this.iAction == ACTION_EDIT && this.iCourse != null) {
			table.add(new HiddenInput(PARAMETER_UPDATE, Boolean.TRUE.toString()));
		}
		if (!(this.iAction == ACTION_DELETE || this.iAction == ACTION_STORE)) {
			code.keepStatusOnAction(true);
		}
		code.setMaxlength(20);

		DateInput startDate = (DateInput) getStyledInterface(new DateInput(PARAMETER_START_DATE));
		IWTimestamp startDateValue = new IWTimestamp();
		if (this.iSchoolSeason != null) {
			startDate.setDate(this.iSchoolSeason.getSchoolSeasonStart());
			startDateValue = new IWTimestamp(this.iSchoolSeason.getSchoolSeasonStart());
		}
		if (this.iCourse != null) {
			startDate.setDate(this.iCourse.getStartDate());
			startDateValue = new IWTimestamp(this.iCourse.getStartDate());
		}
		if (!(this.iAction == ACTION_DELETE || this.iAction == ACTION_STORE)) {
			startDate.keepStatusOnAction(true);
		}
		startDate.setYearRange(startDateValue.getYear() - 2, startDateValue.getYear() + 5);

		DateInput endDate = (DateInput) getStyledInterface(new DateInput(PARAMETER_END_DATE));
		IWTimestamp endDateValue = new IWTimestamp();
		if (this.iSchoolSeason != null) {
			endDate.setDate(this.iSchoolSeason.getSchoolSeasonEnd());
			endDateValue = new IWTimestamp(this.iSchoolSeason.getSchoolSeasonEnd());
		}
		if (this.iCourse != null) {
			endDate.setDate(this.iCourse.getEndDate());
			endDateValue = new IWTimestamp(this.iCourse.getEndDate());
		}
		if (!(this.iAction == ACTION_DELETE || this.iAction == ACTION_STORE)) {
			endDate.keepStatusOnAction(true);
		}
		endDate.setYearRange(endDateValue.getYear() - 2, endDateValue.getYear() + 5);
		

		TextInput comment = (TextInput) getStyledInterface(new TextInput(PARAMETER_COMMENT));
		if (this.iCourse != null) {
			comment.setContent(this.iCourse.getComment());
		}
		if (!(this.iAction == ACTION_DELETE || this.iAction == ACTION_STORE)) {
			comment.keepStatusOnAction(true);
		}
		comment.setWidth(Table.HUNDRED_PERCENT);

		TextInput length = (TextInput) getStyledInterface(new TextInput(PARAMETER_LENGTH));
		if (this.iCourse != null && this.iCourse.getLength() != -1) {
			length.setContent(String.valueOf(this.iCourse.getLength()));
		}
		if (!(this.iAction == ACTION_DELETE || this.iAction == ACTION_STORE)) {
			length.keepStatusOnAction(true);
		}
		length.setLength(3);
		length.setMaxlength(3);

		CheckBox box = getCheckBox(PARAMETER_NOT_ACTIVE, Boolean.TRUE.toString());
		if (this.iCourse != null) {
			box.setChecked(this.iCourse.isInactive());
		}
		if (!(this.iAction == ACTION_DELETE || this.iAction == ACTION_STORE)) {
			box.keepStatusOnAction(true);
		}

		SubmitButton store = (SubmitButton) getButton(new SubmitButton(localize("store", "Store"), PARAMETER_ACTION,
				String.valueOf(ACTION_STORE)));

		table.add(getSmallHeader(localize("study_path", "Study path")), 1, 1);
		table.add(new Break(), 1, 1);
		table.add(studyPaths, 1, 1);

		table.add(getSmallHeader(localize("code", "Code")), 2, 1);
		table.add(new Break(), 2, 1);
		table.add(code, 2, 1);

		table.add(getSmallHeader(localize("start_date", "Start date")), 3, 1);
		table.add(new Break(), 3, 1);
		table.add(startDate, 3, 1);

		table.add(getSmallHeader(localize("course_comment", "Comment")), 1, 2);
		table.add(new Break(), 1, 2);
		table.mergeCells(1, 2, 2, 2);
		table.add(comment, 1, 2);

		table.add(getSmallHeader(localize("end_date", "End date")), 3, 2);
		table.add(new Break(), 3, 2);
		table.add(endDate, 3, 2);

		table.add(getSmallHeader(localize("length", "Length")), 4, 2);
		table.add(new Break(), 4, 2);
		table.add(length, 4, 2);

		table.mergeCells(1, 3, 2, 3);
		table.add(box, 1, 3);
		table.add(Text.getNonBrakingSpace(), 1, 3);
		table.add(getSmallText(localize("course_not_active", "Course is not active")), 1, 3);

		table.mergeCells(3, 3, 4, 3);
		table.setAlignment(3, 3, Table.HORIZONTAL_ALIGN_RIGHT);
		table.add(store, 3, 3);

		return table;
	}
	
	private boolean hasCoursePlacements(String courseCode) {
		try {
			return getBusiness().hasPlacement(courseCode);
		}
		catch (RemoteException e) {
		}
		
		return false;
	}

	private Table getCourses(IWContext iwc) {
		Table table = new Table();
		table.setWidth(Table.HUNDRED_PERCENT);
		table.setCellpadding(getCellpadding());
		table.setCellspacing(getCellspacing());
		table.setColumns(7);
		table.setRowColor(1, getHeaderColor());
		int row = 1;
		int column = 1;

		table.add(getLocalizedSmallHeader("study_path", "Study path"), column++, row);
		table.add(getLocalizedSmallHeader("code", "Code"), column++, row);
		table.add(getLocalizedSmallHeader("period", "Period"), column++, row);
		table.add(getLocalizedSmallHeader("length", "Length"), column++, row++);

		if (this.iSchoolSeasonPK != null && this.iSchoolTypePK != null && this.iSchoolPK != null && this.iStudyPathGroupPK != null) {
			try {
				Collection courses = getBusiness().getCourses(this.iSchoolSeasonPK, this.iSchoolTypePK, this.iSchoolPK,
						this.iStudyPathGroupPK);
				Iterator iter = courses.iterator();
				while (iter.hasNext()) {
					AdultEducationCourse course = (AdultEducationCourse) iter.next();
					SchoolStudyPath path = course.getStudyPath();
					IWTimestamp start = new IWTimestamp(course.getStartDate());
					IWTimestamp end = new IWTimestamp(course.getEndDate());

					Link edit = new Link(getEditIcon(localize("edit", "Edit")));
					edit.addParameter(PARAMETER_COURSE_PK, course.getPrimaryKey().toString());
					edit.addParameter(PARAMETER_ACTION, String.valueOf(ACTION_EDIT));
					edit.addParameter(PARAMETER_OLD_SCHOOL_SEASON, this.iSchoolSeasonPK.toString());
					edit.addParameter(PARAMETER_OLD_CODE, course.getCode());
					edit.maintainParameter(PARAMETER_SCHOOL_TYPE, iwc);
					edit.maintainParameter(PARAMETER_STUDY_PATH_GROUP, iwc);
					edit.maintainParameter(PARAMETER_SCHOOL, iwc);
					edit.maintainParameter(PARAMETER_SCHOOL_SEASON_PK, iwc);

					Link copy = new Link(getCopyIcon(localize("copy", "Copy")));
					copy.addParameter(PARAMETER_COURSE_PK, course.getPrimaryKey().toString());
					copy.addParameter(PARAMETER_ACTION, String.valueOf(ACTION_COPY));
					copy.maintainParameter(PARAMETER_SCHOOL_TYPE, iwc);
					copy.maintainParameter(PARAMETER_STUDY_PATH_GROUP, iwc);
					copy.maintainParameter(PARAMETER_SCHOOL, iwc);
					copy.maintainParameter(PARAMETER_SCHOOL_SEASON_PK, iwc);

					Link delete = new Link(getDeleteIcon(localize("delete", "Delete")));
					delete.addParameter(PARAMETER_COURSE_PK, course.getPrimaryKey().toString());
					delete.addParameter(PARAMETER_ACTION, String.valueOf(ACTION_DELETE));
					delete.maintainParameter(PARAMETER_SCHOOL_TYPE, iwc);
					delete.maintainParameter(PARAMETER_STUDY_PATH_GROUP, iwc);
					delete.maintainParameter(PARAMETER_SCHOOL, iwc);
					delete.maintainParameter(PARAMETER_SCHOOL_SEASON_PK, iwc);

					if (row % 2 == 0) {
						table.setRowColor(row, getZebraColor1());
					}
					else {
						table.setRowColor(row, getZebraColor2());
					}
					if (course.isInactive()) {
						table.setRowColor(row, RED_COLOR);
					}

					table.add(getSmallText(path.getDescription()), 1, row);
					table.add(getSmallText(course.getCode()), 2, row);
					table.add(getSmallText(start.getLocaleDate(iwc.getCurrentLocale(), IWTimestamp.SHORT) + " - "
							+ end.getLocaleDate(iwc.getCurrentLocale(), IWTimestamp.SHORT)), 3, row);
					table.add(getSmallText(String.valueOf(course.getLength())), 4, row);
					table.add(edit, 5, row);
					table.add(copy, 6, row);
					if (hasCoursePlacements(course.getCode())) {
						row++;
					}
					else {
						table.add(delete, 7, row++);
					}
				}
			}
			catch (RemoteException re) {
				throw new IBORuntimeException(re);
			}
		}

		return table;
	}

	private void store(IWContext iwc) {
		Object oldSeason = iwc.isParameterSet(PARAMETER_OLD_SCHOOL_SEASON) ? iwc.getParameter(PARAMETER_OLD_SCHOOL_SEASON)
				: null;
		String studyPath = iwc.isParameterSet(PARAMETER_STUDY_PATH_PK) ? iwc.getParameter(PARAMETER_STUDY_PATH_PK) : null;
		String code = iwc.isParameterSet(PARAMETER_CODE) ? iwc.getParameter(PARAMETER_CODE) : null;
		String oldCode = iwc.isParameterSet(PARAMETER_OLD_CODE) ? iwc.getParameter(PARAMETER_OLD_CODE) : null;
		String startDate = iwc.isParameterSet(PARAMETER_START_DATE) ? iwc.getParameter(PARAMETER_START_DATE) : null;
		String endDate = iwc.isParameterSet(PARAMETER_END_DATE) ? iwc.getParameter(PARAMETER_END_DATE) : null;
		String comment = iwc.getParameter(PARAMETER_COMMENT);
		String lengthString = iwc.isParameterSet(PARAMETER_LENGTH) ? iwc.getParameter(PARAMETER_LENGTH) : null;
		boolean notActive = iwc.isParameterSet(PARAMETER_NOT_ACTIVE);
		boolean update = iwc.isParameterSet(PARAMETER_UPDATE);
		int length = 0;

		boolean validated = true;
		StringBuffer alert = new StringBuffer();
		alert.append(localize("validation_alert", "The following inputs must be filled: "));
		if (this.iSchoolPK == null) {
			alert.append(localize("school_input", "School"));
			validated = false;
		}
		if (this.iSchoolSeasonPK == null) {
			if (!validated) {
				alert.append(", ");
			}
			alert.append(localize("school_season_input", "Season"));
			validated = false;
		}
		if (studyPath == null) {
			if (!validated) {
				alert.append(", ");
			}
			alert.append(localize("study_path_input", "Study path"));
			validated = false;
		}
		if (code == null) {
			if (!validated) {
				alert.append(", ");
			}
			alert.append(localize("code_input", "Code"));
			validated = false;
		}
		if (startDate == null) {
			if (!validated) {
				alert.append(", ");
			}
			alert.append(localize("start_date_input", "Start date"));
			validated = false;
		}
		else {
			if (this.iSchoolSeason != null) {
				IWTimestamp start = new IWTimestamp(startDate);
				IWTimestamp seasonStart = new IWTimestamp(this.iSchoolSeason.getSchoolSeasonStart());
				if (seasonStart.isLaterThan(start)) {
					if (!validated) {
						alert.append(", ");
					}
					alert.append(localize("start_date_input_early", "It is not possible to store a start date earlier than the period start date"));
					validated = false;					
				}
			}
		}
		if (endDate == null) {
			if (!validated) {
				alert.append(", ");
			}
			alert.append(localize("end_date_input", "End date"));
			validated = false;
		}
		if (lengthString == null) {
			if (!validated) {
				alert.append(", ");
			}
			alert.append(localize("length_input", "Length"));
			validated = false;
		}
		if (lengthString != null) {
			try {
				length = Integer.parseInt(lengthString);
			}
			catch (NumberFormatException nfe) {
				getParentPage().setAlertOnLoad(localize("length_must_be_integer", "Length must be a number"));
				return;
			}
		}

		if (validated) {
			try {
				getBusiness().storeCourse(this.iSchoolSeasonPK, oldSeason, code, oldCode, this.iSchoolPK, studyPath,
						new IWTimestamp(startDate).getDate(), new IWTimestamp(endDate).getDate(), comment, length,
						notActive, update);
				if (update) {
					getParentPage().setAlertOnLoad(localize("course_updated", "Course updated"));
				}
				else {
					getParentPage().setAlertOnLoad(localize("course_stored", "Course stored"));
				}
			}
			catch (CreateException ce) {
				ce.printStackTrace();
			}
			catch (DuplicateValueException dpe) {
				this.iAction = Integer.parseInt(iwc.getParameter(PARAMETER_FROM_ACTION));
				getParentPage().setAlertOnLoad(
						localize("duplicate_values_detected", "Trying to store duplicate values, ignoring..."));
			}
			catch (RemoteException re) {
				throw new IBORuntimeException(re);
			}
		}
		else {
			this.iAction = Integer.parseInt(iwc.getParameter(PARAMETER_FROM_ACTION));
			getParentPage().setAlertOnLoad(alert.toString());
		}
	}

	private void remove(IWContext iwc) {
		try {
			getBusiness().removeCourse(iwc.getParameter(PARAMETER_COURSE_PK));
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
		catch (RemoveException re) {
			getParentPage().setAlertOnLoad(
					localize("course_could_not_be_deleted",
							"Course could not be deleted.  It probably has some choices attached to it"));
		}
	}

	private int parseAction(IWContext iwc) {
		try {
			this.iAction = ACTION_EDIT;
			if (iwc.isParameterSet(PARAMETER_FROM_ACTION)) {
				this.iAction = Integer.parseInt(iwc.getParameter(PARAMETER_FROM_ACTION));
			}
			if (iwc.isParameterSet(PARAMETER_ACTION)) {
				this.iAction = Integer.parseInt(iwc.getParameter(PARAMETER_ACTION));
			}

			if (iwc.isParameterSet(PARAMETER_SCHOOL_TYPE)) {
				this.iSchoolTypePK = iwc.getParameter(PARAMETER_SCHOOL_TYPE);
				this.iSchoolType = getBusiness().getSchoolBusiness().getSchoolType(this.iSchoolTypePK);
			}
			if (iwc.isParameterSet(PARAMETER_SCHOOL)) {
				this.iSchoolPK = iwc.getParameter(PARAMETER_SCHOOL);
				// iSchool =
				// getBusiness().getSchoolBusiness().getSchool(iSchoolPK);
			}
			if (iwc.isParameterSet(PARAMETER_SCHOOL_SEASON_PK)) {
				this.iSchoolSeasonPK = iwc.getParameter(PARAMETER_SCHOOL_SEASON_PK);
				this.iSchoolSeason = getBusiness().getSchoolBusiness().getSchoolSeason(this.iSchoolSeasonPK);
			}
			if (iwc.isParameterSet(PARAMETER_STUDY_PATH_GROUP)) {
				this.iStudyPathGroupPK = iwc.getParameter(PARAMETER_STUDY_PATH_GROUP);
				this.iStudyPathGroup = getBusiness().getStudyPathBusiness().findStudyPathGroup(this.iStudyPathGroupPK);
			}
			if (iwc.isParameterSet(PARAMETER_COURSE_PK) && (this.iAction == ACTION_EDIT || this.iAction == ACTION_COPY)) {
				try {
					this.iCourse = getBusiness().getCourse(iwc.getParameter(PARAMETER_COURSE_PK));
				}
				catch (FinderException fe) {
					fe.printStackTrace();
				}
			}

			return this.iAction;
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
	}

	private School getSchoolByLoggedInUser(final IWContext context) throws RemoteException {
		final User user = context.getCurrentUser();
		School school = null;
		if (null != user) {
			final SchoolUserBusiness business = getSchoolUserBusiness();
			try {
				final Collection schoolIds = business.getSchools(user);
				if (!schoolIds.isEmpty()) {
					final Object schoolId = schoolIds.iterator().next();
					school = getSchoolBusiness().getSchool(schoolId);
				}
			}
			catch (FinderException e) {
				// no problem, no school found
			}
		}
		return school;
	}

	private SchoolUserBusiness getSchoolUserBusiness() throws RemoteException {
		return (SchoolUserBusiness) IBOLookup.getServiceInstance(getIWApplicationContext(), SchoolUserBusiness.class);
	}

	private SchoolBusiness getSchoolBusiness() throws RemoteException {
		return (SchoolBusiness) IBOLookup.getServiceInstance(getIWApplicationContext(), SchoolBusiness.class);
	}
}