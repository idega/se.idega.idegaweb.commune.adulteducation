/*
 * $Id: AdultEducationChoiceBMPBean.java,v 1.14 2005/10/13 08:09:37 palli Exp $
 * Created on May 3, 2005
 *
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf. Use is subject to
 * license terms.
 */
package se.idega.idegaweb.commune.adulteducation.data;

import java.sql.Date;
import java.util.Collection;

import javax.ejb.FinderException;

import se.idega.idegaweb.commune.adulteducation.AdultEducationConstants;

import com.idega.block.process.data.AbstractCaseBMPBean;
import com.idega.block.process.data.Case;
import com.idega.block.school.data.SchoolSeason;
import com.idega.block.school.data.SchoolStudyPath;
import com.idega.block.school.data.SchoolType;
import com.idega.data.IDOAddRelationshipException;
import com.idega.data.IDOException;
import com.idega.data.IDORelationshipException;
import com.idega.data.IDORemoveRelationshipException;
import com.idega.data.query.CountColumn;
import com.idega.data.query.InCriteria;
import com.idega.data.query.JoinCriteria;
import com.idega.data.query.MatchCriteria;
import com.idega.data.query.OR;
import com.idega.data.query.SelectQuery;
import com.idega.data.query.Table;
import com.idega.data.query.WildCardColumn;
import com.idega.user.data.User;

public class AdultEducationChoiceBMPBean extends AbstractCaseBMPBean implements Case, AdultEducationChoice {

	private static final long serialVersionUID = 2000526983820143043L;

	private static final String ENTITY_NAME = "comm_vux_choice";

	private static final String USER = "ic_user_id";

	private static final String COURSE = "vux_course_id";

	private static final String CHOICE_DATE = "choice_date";

	private static final String COMMENT = "choice_comment";

	private static final String NOTES = "notes";

	private static final String CHOICE_ORDER = "choice_order";

	private static final String GRANTED_RULE_1 = "granted_rule_1";

	private static final String GRANTED_RULE_2 = "granted_rule_2";

	private static final String GRANTED_RULE_3 = "granted_rule_3";

	private static final String GRANTED_RULE_4 = "granted_rule_4";

	private static final String GRANTED_RULE_NOTES = "granted_rule_notes";

	private static final String GRANTED_DATE = "granted_date";

	private static final String ALL_GRANTED = "all_granted";

	private static final String PRIORITY = "priority";

	private static final String CONFIRMATION_MESSAGE = "confirmation_message";

	private static final String PLACEMENT_MESSAGE = "placement_message";

	private static final String REJECTION_COMMENT = "rejection_comment";

	private static final String OTHER_REASON = "other_reason";

	private static final String PACKAGE = "vux_school_package_id";

	@Override
	public String getCaseCodeKey() {
		return AdultEducationConstants.ADULT_EDUCATION_CASE_CODE;
	}

	@Override
	public String getCaseCodeDescription() {
		return AdultEducationConstants.ADULT_EDUCATION_CASE_DESCRIPTION;
	}

	@Override
	public String getEntityName() {
		return ENTITY_NAME;
	}

	@Override
	public void initializeAttributes() {
		addGeneralCaseRelation();

		addManyToOneRelationship(USER, "User", User.class);
		addManyToOneRelationship(COURSE, "Course", AdultEducationCourse.class);
		addManyToOneRelationship(PACKAGE, "Package", SchoolCoursePackage.class);

		addAttribute(CHOICE_DATE, "Choice date", Date.class);
		addAttribute(COMMENT, "Comment", String.class, 1000);
		addAttribute(CHOICE_ORDER, "Choice order", Integer.class);
		addAttribute(GRANTED_RULE_1, "Granted rule 1", Boolean.class);
		addAttribute(GRANTED_RULE_2, "Granted rule 2", Boolean.class);
		addAttribute(GRANTED_RULE_3, "Granted rule 3", Boolean.class);
		addAttribute(GRANTED_RULE_4, "Granted rule 4", Boolean.class);
		addAttribute(GRANTED_RULE_NOTES, "Granted rule notes", String.class, 1000);
		addAttribute(GRANTED_DATE, "Granted date", Date.class);
		addAttribute(ALL_GRANTED, "All granted", Boolean.class);
		addAttribute(PRIORITY, "Priority", Integer.class);
		addAttribute(CONFIRMATION_MESSAGE, "Confirmation message", Boolean.class);
		addAttribute(PLACEMENT_MESSAGE, "Placement message", Boolean.class);
		addAttribute(REJECTION_COMMENT, "Rejection comment", String.class, 1000);
		addAttribute(NOTES, "Notes", String.class, 1000);
		addAttribute(OTHER_REASON, "Other reason", String.class);

		addManyToManyRelationShip(AdultEducationChoiceReason.class);
	}

	@Override
	public void setDefaultValues() {
		setGrantedRule1(false);
		setGrantedRule2(false);
		setGrantedRule3(false);
		setAllGranted(false);
		setPlacementMessageSent(false);
		setConfirmationMessageSent(false);

		super.setDefaultValues();
	}

	// Getters
	public User getUser() {
		return (User) getColumnValue(USER);
	}

	public Object getUserPK() {
		return getIntegerColumnValue(USER);
	}

	public AdultEducationCourse getCourse() {
		return (AdultEducationCourse) getColumnValue(COURSE);
	}

	public Object getCoursePK() {
		return getIntegerColumnValue(COURSE);
	}

	public SchoolCoursePackage getPackage() {
		return (SchoolCoursePackage) getColumnValue(PACKAGE);
	}

	public Object getPackagePK() {
		return getIntegerColumnValue(PACKAGE);
	}

	public Date getChoiceDate() {
		return getDateColumnValue(CHOICE_DATE);
	}

	public String getComment() {
		return getStringColumnValue(COMMENT);
	}

	public int getChoiceOrder() {
		return getIntColumnValue(CHOICE_ORDER);
	}

	public boolean hasGrantedRule1() {
		return getBooleanColumnValue(GRANTED_RULE_1, false);
	}

	public boolean hasGrantedRule2() {
		return getBooleanColumnValue(GRANTED_RULE_2, false);
	}

	public boolean hasGrantedRule3() {
		return getBooleanColumnValue(GRANTED_RULE_3, false);
	}

	public boolean hasGrantedRule4() {
		return getBooleanColumnValue(GRANTED_RULE_4, false);
	}

	public boolean hasAllGranted() {
		return getBooleanColumnValue(ALL_GRANTED, false);
	}

	public String getGrantedRuleNotes() {
		return getStringColumnValue(GRANTED_RULE_NOTES);
	}

	public Date getGrantedDate() {
		return getDateColumnValue(GRANTED_DATE);
	}

	public int getPriority() {
		return getIntColumnValue(PRIORITY);
	}

	public boolean isPlacementMessageSent() {
		return getBooleanColumnValue(PLACEMENT_MESSAGE, false);
	}

	public boolean isConfirmationMessageSent() {
		return getBooleanColumnValue(CONFIRMATION_MESSAGE, false);
	}

	public String getRejectionComment() {
		return getStringColumnValue(REJECTION_COMMENT);
	}

	public String getNotes() {
		return getStringColumnValue(NOTES);
	}

	public Collection getReasons() throws IDORelationshipException {
		return idoGetRelatedEntities(AdultEducationChoiceReason.class);
	}

	public String getOtherReason() {
		return getStringColumnValue(OTHER_REASON);
	}

	// Setters
	public void setUser(User user) {
		setColumn(USER, user);
	}

	public void setUser(Object userPK) {
		setColumn(USER, userPK);
	}

	public void setCourse(AdultEducationCourse course) {
		setColumn(COURSE, course);
	}

	public void setCourse(Object coursePK) {
		setColumn(COURSE, coursePK);
	}

	public void setPackage(SchoolCoursePackage schoolCoursePackage) {
		setColumn(PACKAGE, schoolCoursePackage);
	}

	public void setPackage(Object schoolCoursePackagePK) {
		setColumn(PACKAGE, schoolCoursePackagePK);
	}

	public void setChoiceDate(Date date) {
		setColumn(CHOICE_DATE, date);
	}

	public void setComment(String comment) {
		setColumn(COMMENT, comment);
	}

	public void setChoiceOrder(int order) {
		setColumn(CHOICE_ORDER, order);
	}

	public void setGrantedRule1(boolean granted) {
		setColumn(GRANTED_RULE_1, granted);
	}

	public void setGrantedRule2(boolean granted) {
		setColumn(GRANTED_RULE_2, granted);
	}

	public void setGrantedRule3(boolean granted) {
		setColumn(GRANTED_RULE_3, granted);
	}

	public void setGrantedRule4(boolean granted) {
		setColumn(GRANTED_RULE_4, granted);
	}

	public void setGrantedRuleNotes(String notes) {
		setColumn(GRANTED_RULE_NOTES, notes);
	}

	public void setAllGranted(boolean granted) {
		setColumn(ALL_GRANTED, granted);
	}

	public void setGrantedDate(Date date) {
		setColumn(GRANTED_DATE, date);
	}

	public void setPriority(int priority) {
		setColumn(PRIORITY, priority);
	}

	public void setPlacementMessageSent(boolean sent) {
		setColumn(PLACEMENT_MESSAGE, sent);
	}

	public void setConfirmationMessageSent(boolean sent) {
		setColumn(CONFIRMATION_MESSAGE, sent);
	}

	public void setRejectionComment(String comment) {
		setColumn(REJECTION_COMMENT, comment);
	}

	public void setNotes(String notes) {
		setColumn(NOTES, notes);
	}

	public void addReason(AdultEducationChoiceReason reason) throws IDOAddRelationshipException {
		idoAddTo(reason);
	}

	public void addReason(Object reasonPK) throws IDOAddRelationshipException {
		idoAddTo(AdultEducationChoiceReason.class, reasonPK);
	}

	public void removeReason(AdultEducationChoiceReason reason) throws IDORemoveRelationshipException {
		idoRemoveFrom(reason);
	}

	public void removeAllReasons() throws IDORemoveRelationshipException {
		idoRemoveFrom(AdultEducationChoiceReason.class);
	}

	public void setOtherReason(String otherReason) {
		setColumn(OTHER_REASON, otherReason);
	}

	// Finders
	public Collection ejbFindAllBySeasonAndStatuses(SchoolSeason season, String[] statuses) throws FinderException {
		return ejbFindAllBySeasonAndStatuses(season, statuses, -1);
	}

	public Collection ejbFindAllBySeasonAndStatuses(SchoolSeason season, String[] statuses, int choiceOrder)
			throws FinderException {
		Table table = new Table(this);
		Table course = new Table(AdultEducationCourse.class);
		Table cases = new Table(Case.class);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(new WildCardColumn(table));
		try {
			query.addJoin(table, course);
			query.addJoin(table, cases);
		}
		catch (IDORelationshipException ire) {
			throw new FinderException(ire.getMessage());
		}
		query.addCriteria(new MatchCriteria(course, "sch_school_season_id", MatchCriteria.EQUALS, season));
		if (choiceOrder != -1) {
			query.addCriteria(new MatchCriteria(table, CHOICE_ORDER, MatchCriteria.EQUALS, choiceOrder));
		}
		query.addCriteria(new InCriteria(cases, "case_status", statuses));
		query.addOrder(cases, "case_status", false);
		query.addOrder(table, CHOICE_DATE, true);

		return idoFindPKsByQuery(query);
	}

	public Collection ejbFindAllBySeasonAndTypeAndDateAndStatuses(SchoolSeason season, SchoolType type, Date fromDate,
			Date toDate, String[] statuses, int choiceOrder) throws FinderException {
		return ejbFindAllBySeasonAndTypeAndDateAndHandlerAndStatuses(season, type, fromDate, toDate, null, statuses,
				choiceOrder);
	}

	public Collection ejbFindAllBySeasonAndTypeAndDateAndHandlerAndStatuses(SchoolSeason season, SchoolType type,
			Date fromDate, Date toDate, User handler, String[] statuses, int choiceOrder) throws FinderException {
		Table table = new Table(this);
		Table course = new Table(AdultEducationCourse.class);
		Table cases = new Table(Case.class);
		Table studyPath = new Table(SchoolStudyPath.class);
		Table user = new Table(User.class);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(new WildCardColumn(table));
		try {
			query.addJoin(table, course);
			query.addJoin(table, cases);
			query.addJoin(course, studyPath);
			query.addJoin(table, user, USER);
		}
		catch (IDORelationshipException ire) {
			throw new FinderException(ire.getMessage());
		}
		query.addCriteria(new MatchCriteria(course, "sch_school_season_id", MatchCriteria.EQUALS, season));
		if (choiceOrder != -1) {
			query.addCriteria(new MatchCriteria(table, CHOICE_ORDER, MatchCriteria.EQUALS, choiceOrder));
		}
		if (type != null) {
			query.addCriteria(new MatchCriteria(studyPath, "sch_school_type_id", MatchCriteria.EQUALS, type));
		}
		query.addCriteria(new MatchCriteria(table, CHOICE_DATE, MatchCriteria.GREATEREQUAL, fromDate));
		query.addCriteria(new MatchCriteria(table, CHOICE_DATE, MatchCriteria.LESSEQUAL, toDate));
		if (handler != null) {
			OR or1 = new OR(new MatchCriteria(cases.getColumn("HANDLER_GROUP_ID"), false), new JoinCriteria(
					table.getColumn(USER), cases.getColumn("HANDLER_GROUP_ID")));
			query.addCriteria(new OR(new MatchCriteria(cases, "HANDLER_GROUP_ID", MatchCriteria.EQUALS, handler), or1));
		}
		query.addCriteria(new InCriteria(cases, "case_status", statuses));
		query.addOrder(cases, "case_status", false);
		query.addOrder(table, CHOICE_DATE, true);
		query.addOrder(user, "personal_id", true);

		return idoFindPKsByQuery(query);
	}

	public Collection ejbFindAllByUserAndSeason(User user, SchoolSeason season) throws FinderException {
		return ejbFindAllByUserAndSeason(user, season, -1);
	}

	public Collection ejbFindAllByUserAndSeason(User user, SchoolSeason season, int choiceOrder) throws FinderException {
		Table table = new Table(this);
		Table course = new Table(AdultEducationCourse.class);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(new WildCardColumn(table));
		try {
			query.addJoin(table, course);
		}
		catch (IDORelationshipException ire) {
			throw new FinderException(ire.getMessage());
		}
		query.addCriteria(new MatchCriteria(table, USER, MatchCriteria.EQUALS, user));
		query.addCriteria(new MatchCriteria(course, "sch_school_season_id", MatchCriteria.EQUALS, season));
		if (choiceOrder != -1) {
			query.addCriteria(new MatchCriteria(table, CHOICE_ORDER, MatchCriteria.EQUALS, choiceOrder));
		}
		query.addOrder(table, CHOICE_ORDER, true);

		return idoFindPKsByQuery(query);
	}

	public Collection ejbFindAllByUserAndSeasonAndPackage(User user, SchoolSeason season,
			SchoolCoursePackage coursePackage) throws FinderException {
		return ejbFindAllByUserAndSeasonAndPackage(user, season, coursePackage, -1);
	}

	public Collection ejbFindAllByUserAndSeasonAndPackage(User user, SchoolSeason season,
			SchoolCoursePackage coursePackage, int choiceOrder) throws FinderException {
		Table table = new Table(this);
		Table course = new Table(AdultEducationCourse.class);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(new WildCardColumn(table));
		try {
			query.addJoin(table, course);
		}
		catch (IDORelationshipException ire) {
			throw new FinderException(ire.getMessage());
		}
		query.addCriteria(new MatchCriteria(table, USER, MatchCriteria.EQUALS, user));
		query.addCriteria(new MatchCriteria(course, "sch_school_season_id", MatchCriteria.EQUALS, season));
		query.addCriteria(new MatchCriteria(table, PACKAGE, MatchCriteria.EQUALS, coursePackage));
		if (choiceOrder != -1) {
			query.addCriteria(new MatchCriteria(table, CHOICE_ORDER, MatchCriteria.EQUALS, choiceOrder));
		}
		query.addOrder(table, CHOICE_ORDER, true);

		return idoFindPKsByQuery(query);
	}

	public Collection ejbFindAllByUser(User user, String[] statuses) throws FinderException {
		Table table = new Table(this);
		Table course = new Table(AdultEducationCourse.class);
		Table season = new Table(SchoolSeason.class);
		Table cases = new Table(Case.class);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(new WildCardColumn(table));
		try {
			query.addJoin(table, course);
			query.addJoin(course, season);
			query.addJoin(table, cases);
		}
		catch (IDORelationshipException ire) {
			throw new FinderException(ire.getMessage());
		}
		query.addCriteria(new MatchCriteria(table, USER, MatchCriteria.EQUALS, user));
		query.addCriteria(new InCriteria(cases, "case_status", statuses));
		query.addOrder(season, "season_start", true);

		return idoFindPKsByQuery(query);
	}

	public Collection ejbFindAllByUserAndSeasonAndStatuses(User user, SchoolSeason season, String[] statuses)
			throws FinderException {
		Table table = new Table(this);
		Table course = new Table(AdultEducationCourse.class);
		Table cases = new Table(Case.class);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(new WildCardColumn(table));
		try {
			query.addJoin(table, course);
			query.addJoin(table, cases);
		}
		catch (IDORelationshipException ire) {
			throw new FinderException(ire.getMessage());
		}
		query.addCriteria(new MatchCriteria(table, USER, MatchCriteria.EQUALS, user));
		query.addCriteria(new MatchCriteria(course, "sch_school_season_id", MatchCriteria.EQUALS, season));
		query.addCriteria(new InCriteria(cases, "case_status", statuses));
		query.addOrder(table, CHOICE_ORDER, true);

		return idoFindPKsByQuery(query);
	}

	public Collection ejbFindAllByUserAndSeasonAndStatuses(User user, SchoolSeason season,
			SchoolCoursePackage coursePackage, int choiceOrder, String[] statuses) throws FinderException {
		Table table = new Table(this);
		Table course = new Table(AdultEducationCourse.class);
		Table cases = new Table(Case.class);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(new WildCardColumn(table));
		try {
			query.addJoin(table, course);
			query.addJoin(table, cases);
		}
		catch (IDORelationshipException ire) {
			throw new FinderException(ire.getMessage());
		}
		query.addCriteria(new MatchCriteria(table, USER, MatchCriteria.EQUALS, user));
		query.addCriteria(new MatchCriteria(course, "sch_school_season_id", MatchCriteria.EQUALS, season));
		query.addCriteria(new MatchCriteria(table, PACKAGE, MatchCriteria.EQUALS, coursePackage));
		query.addCriteria(new MatchCriteria(table, CHOICE_ORDER, MatchCriteria.EQUALS, choiceOrder));
		query.addCriteria(new InCriteria(cases, "case_status", statuses));
		query.addOrder(table, CHOICE_ORDER, true);

		return idoFindPKsByQuery(query);
	}

	public Object ejbFindByUserAndCourse(Object userPK, Object coursePK) throws FinderException {
		Table table = new Table(this);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(new WildCardColumn(table));
		query.addCriteria(new MatchCriteria(table, USER, MatchCriteria.EQUALS, userPK));
		query.addCriteria(new MatchCriteria(table, COURSE, MatchCriteria.EQUALS, coursePK));

		return idoFindOnePKByQuery(query);
	}

	public Collection ejbFindAllByUserAndSeasonAndStudyPath(Object userPK, Object seasonPK, Object studyPathPK,
			String[] statuses) throws FinderException {
		Table table = new Table(this);
		Table course = new Table(AdultEducationCourse.class);
		Table cases = new Table(Case.class);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(new WildCardColumn(table));
		try {
			query.addJoin(table, course);
			query.addJoin(table, cases);
		}
		catch (IDORelationshipException ire) {
			throw new FinderException(ire.getMessage());
		}
		query.addCriteria(new MatchCriteria(table, USER, MatchCriteria.EQUALS, userPK));
		query.addCriteria(new MatchCriteria(course, "sch_study_path_id", MatchCriteria.EQUALS, studyPathPK));
		query.addCriteria(new MatchCriteria(course, "sch_school_season_id", MatchCriteria.EQUALS, seasonPK));
		query.addCriteria(new InCriteria(cases, "case_status", statuses));
		query.addOrder(table, CHOICE_ORDER, true);

		return idoFindPKsByQuery(query);
	}

	public Object ejbFindByUserAndSeasonAndStudyPathAndChoiceOrder(Object userPK, Object seasonPK, Object studyPathPK,
			int choiceOrder, String[] statuses) throws FinderException {
		Table table = new Table(this);
		Table course = new Table(AdultEducationCourse.class);
		Table cases = new Table(Case.class);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(new WildCardColumn(table));
		try {
			query.addJoin(table, course);
			query.addJoin(table, cases);
		}
		catch (IDORelationshipException ire) {
			throw new FinderException(ire.getMessage());
		}
		query.addCriteria(new MatchCriteria(table, USER, MatchCriteria.EQUALS, userPK));
		query.addCriteria(new MatchCriteria(course, "sch_school_season_id", MatchCriteria.EQUALS, seasonPK));
		query.addCriteria(new MatchCriteria(course, "sch_study_path_id", MatchCriteria.EQUALS, studyPathPK));
		query.addCriteria(new InCriteria(cases, "case_status", statuses));
		query.addCriteria(new MatchCriteria(table, CHOICE_ORDER, MatchCriteria.EQUALS, choiceOrder));

		return idoFindOnePKByQuery(query);
	}

	public Collection ejbFindAllBySeasonAndCourse(SchoolSeason season, AdultEducationCourse aeCourse, String[] statuses)
			throws FinderException {
		Table table = new Table(this);
		Table course = new Table(AdultEducationCourse.class);
		Table cases = new Table(Case.class);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(new WildCardColumn(table));
		try {
			query.addJoin(table, course);
			query.addJoin(table, cases);
		}
		catch (IDORelationshipException ire) {
			throw new FinderException(ire.getMessage());
		}
		if (aeCourse != null) {
			query.addCriteria(new MatchCriteria(table, COURSE, MatchCriteria.EQUALS, aeCourse));
		}
		query.addCriteria(new MatchCriteria(course, "sch_school_season_id", MatchCriteria.EQUALS, season));
		query.addCriteria(new InCriteria(cases, "case_status", statuses));
		query.addOrder(table, PRIORITY, true);
		query.addOrder(table, CHOICE_DATE, true);

		return idoFindPKsByQuery(query);
	}

	public int ejbHomeGetCountOfChoicesByCourse(SchoolSeason season, AdultEducationCourse course, String[] statuses)
			throws IDOException {
		Table table = new Table(this);
		Table courseTable = new Table(AdultEducationCourse.class);
		Table cases = new Table(Case.class);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(new CountColumn(table, getIDColumnName()));
		query.addJoin(table, courseTable);
		query.addJoin(table, cases);
		query.addCriteria(new MatchCriteria(courseTable, "sch_school_season_id", MatchCriteria.EQUALS, season));
		query.addCriteria(new MatchCriteria(courseTable, COURSE, MatchCriteria.EQUALS, course));
		query.addCriteria(new InCriteria(cases, "case_status", statuses));

		return idoGetNumberOfRecords(query);
	}

	public void addSubscriber(User subscriber) throws IDOAddRelationshipException {
	}

	public Collection<User> getSubscribers() {
		return null;
	}

	public void removeSubscriber(User subscriber) throws IDORemoveRelationshipException {
	}
}