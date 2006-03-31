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
import com.idega.data.IDORelationshipException;
import com.idega.data.query.InCriteria;
import com.idega.data.query.MatchCriteria;
import com.idega.data.query.SelectQuery;
import com.idega.data.query.Table;
import com.idega.data.query.WildCardColumn;


/**
 * <p>
 * TODO Dainis Describe Type AdultEducationCourse
 * </p>
 *  Last modified: $Date: 2006/03/31 18:01:05 $ by $Author: dainis $
 * 
 * @author <a href="mailto:Dainis@idega.com">Dainis</a>
 * @version $Revision: 1.5 $
 */
public interface AdultEducationCourse extends IDOEntity {

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.data.AdultEducationCourseBMPBean#getStudyPath
	 */
	public SchoolStudyPath getStudyPath();

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.data.AdultEducationCourseBMPBean#getStudyPathPK
	 */
	public Object getStudyPathPK();

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.data.AdultEducationCourseBMPBean#getSchoolSeason
	 */
	public SchoolSeason getSchoolSeason();

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.data.AdultEducationCourseBMPBean#getSchoolSeasonPK
	 */
	public Object getSchoolSeasonPK();

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.data.AdultEducationCourseBMPBean#getSchool
	 */
	public School getSchool();

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.data.AdultEducationCourseBMPBean#getSchoolPK
	 */
	public Object getSchoolPK();

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.data.AdultEducationCourseBMPBean#getStartDate
	 */
	public Date getStartDate();

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.data.AdultEducationCourseBMPBean#getEndDate
	 */
	public Date getEndDate();

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.data.AdultEducationCourseBMPBean#getComment
	 */
	public String getComment();

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.data.AdultEducationCourseBMPBean#getCode
	 */
	public String getCode();

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.data.AdultEducationCourseBMPBean#getLength
	 */
	public int getLength();

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.data.AdultEducationCourseBMPBean#isInactive
	 */
	public boolean isInactive();

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.data.AdultEducationCourseBMPBean#setStudyPath
	 */
	public void setStudyPath(SchoolStudyPath path);

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.data.AdultEducationCourseBMPBean#setStudyPath
	 */
	public void setStudyPath(Object path);

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.data.AdultEducationCourseBMPBean#setSchoolSeason
	 */
	public void setSchoolSeason(SchoolSeason season);

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.data.AdultEducationCourseBMPBean#setSchoolSeason
	 */
	public void setSchoolSeason(Object season);

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.data.AdultEducationCourseBMPBean#setSchool
	 */
	public void setSchool(School school);

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.data.AdultEducationCourseBMPBean#setSchool
	 */
	public void setSchool(Object school);

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.data.AdultEducationCourseBMPBean#setStartDate
	 */
	public void setStartDate(Date date);

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.data.AdultEducationCourseBMPBean#setEndDate
	 */
	public void setEndDate(Date date);

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.data.AdultEducationCourseBMPBean#setComment
	 */
	public void setComment(String comment);

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.data.AdultEducationCourseBMPBean#setCode
	 */
	public void setCode(String code);

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.data.AdultEducationCourseBMPBean#setLength
	 */
	public void setLength(int length);

	/**
	 * @see se.idega.idegaweb.commune.adulteducation.data.AdultEducationCourseBMPBean#setNotActive
	 */
	public void setNotActive(boolean active);
}
