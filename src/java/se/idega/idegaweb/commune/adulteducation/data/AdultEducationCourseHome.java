/**
 * 
 */
package se.idega.idegaweb.commune.adulteducation.data;

import java.sql.Date;
import java.util.Collection;
import javax.ejb.FinderException;
import com.idega.block.process.data.Case;
import com.idega.block.school.data.School;
import com.idega.block.school.data.SchoolBMPBean;
import com.idega.block.school.data.SchoolClass;
import com.idega.block.school.data.SchoolClassMember;
import com.idega.block.school.data.SchoolSeason;
import com.idega.block.school.data.SchoolStudyPath;
import com.idega.block.school.data.SchoolStudyPathBMPBean;
import com.idega.block.school.data.SchoolStudyPathGroup;
import com.idega.block.school.data.SchoolType;
import com.idega.data.GenericEntity;
import com.idega.data.IDOEntity;
import com.idega.data.IDOHome;
import com.idega.data.IDORelationshipException;
import com.idega.data.query.InCriteria;
import com.idega.data.query.MatchCriteria;
import com.idega.data.query.SelectQuery;
import com.idega.data.query.Table;
import com.idega.data.query.WildCardColumn;


/**
 * <p>
 * TODO Dainis Describe Type AdultEducationCourseHome
 * </p>
 *  Last modified: $Date: 2006/03/31 18:01:05 $ by $Author: dainis $
 * 
 * @author <a href="mailto:Dainis@idega.com">Dainis</a>
 * @version $Revision: 1.5 $
 */
public interface AdultEducationCourseHome extends IDOHome {

	public AdultEducationCourse create() throws javax.ejb.CreateException;

	public AdultEducationCourse findByPrimaryKey(Object pk) throws javax.ejb.FinderException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.data.AdultEducationCourseBMPBean#ejbFindBySeasonAndCode
	 */
	public AdultEducationCourse findBySeasonAndCode(SchoolSeason season, String code) throws FinderException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.data.AdultEducationCourseBMPBean#ejbFindBySeasonAndCode
	 */
	public AdultEducationCourse findBySeasonAndCode(Object season, String code) throws FinderException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.data.AdultEducationCourseBMPBean#ejbFindAllBySeasonAndSchool
	 */
	public Collection findAllBySeasonAndSchool(SchoolSeason season, School school) throws FinderException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.data.AdultEducationCourseBMPBean#ejbFindAllBySeasonAndSchool
	 */
	public Collection findAllBySeasonAndSchool(Object season, Object school) throws FinderException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.data.AdultEducationCourseBMPBean#ejbFindAllBySeasonAndTypeAndSchoolAndStudyPathGroup
	 */
	public Collection findAllBySeasonAndTypeAndSchoolAndStudyPathGroup(SchoolSeason season, SchoolType type,
			School school, SchoolStudyPathGroup group) throws FinderException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.data.AdultEducationCourseBMPBean#ejbFindAllBySeasonAndTypeAndSchoolAndStudyPathGroup
	 */
	public Collection findAllBySeasonAndTypeAndSchoolAndStudyPathGroup(Object season, Object type, Object school,
			Object group) throws FinderException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.data.AdultEducationCourseBMPBean#ejbFindAllBySchoolAndSeasonAndStudyPathGroupConnectedToChoices
	 */
	public Collection findAllBySchoolAndSeasonAndStudyPathGroupConnectedToChoices(Object school, Object season,
			Object group, Object[] statuses) throws FinderException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.data.AdultEducationCourseBMPBean#ejbFindAllBySchoolAndSeasonAndStudyPathGroupConnectedToStudents
	 */
	public Collection findAllBySchoolAndSeasonAndStudyPathGroupConnectedToStudents(Object school, Object season,
			Object group) throws FinderException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.data.AdultEducationCourseBMPBean#ejbFindAllBySeasonAndSchoolAndStudyPath
	 */
	public Collection findAllBySeasonAndSchoolAndStudyPath(SchoolSeason season, School school, SchoolStudyPath studyPath)
			throws FinderException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.data.AdultEducationCourseBMPBean#ejbFindAllBySeasonAndSchoolAndStudyPath
	 */
	public Collection findAllBySeasonAndSchoolAndStudyPath(Object season, Object school, Object studyPath)
			throws FinderException;

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.data.AdultEducationCourseBMPBean#ejbFindAllAvailableCoursesByParameters
	 */
	public Collection findAllAvailableCoursesByParameters(SchoolType schoolType, SchoolSeason schoolSeason,
			SchoolStudyPathGroup studyPathGroup, SchoolStudyPath studyPath, School school) throws FinderException;
}
