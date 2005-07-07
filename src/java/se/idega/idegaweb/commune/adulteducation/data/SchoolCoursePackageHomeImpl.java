/*
 * $Id: SchoolCoursePackageHomeImpl.java,v 1.1 2005/07/07 08:41:42 laddi Exp $
 * Created on Jul 6, 2005
 *
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package se.idega.idegaweb.commune.adulteducation.data;

import java.util.Collection;
import javax.ejb.FinderException;
import com.idega.block.school.data.School;
import com.idega.block.school.data.SchoolSeason;
import com.idega.data.IDOException;
import com.idega.data.IDOFactory;


/**
 * Last modified: $Date: 2005/07/07 08:41:42 $ by $Author: laddi $
 * 
 * @author <a href="mailto:laddi@idega.com">laddi</a>
 * @version $Revision: 1.1 $
 */
public class SchoolCoursePackageHomeImpl extends IDOFactory implements SchoolCoursePackageHome {

	protected Class getEntityInterfaceClass() {
		return SchoolCoursePackage.class;
	}

	public SchoolCoursePackage create() throws javax.ejb.CreateException {
		return (SchoolCoursePackage) super.createIDO();
	}

	public SchoolCoursePackage findByPrimaryKey(Object pk) throws javax.ejb.FinderException {
		return (SchoolCoursePackage) super.findByPrimaryKeyIDO(pk);
	}

	public Collection findAllBySchool(School school) throws FinderException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		java.util.Collection ids = ((SchoolCoursePackageBMPBean) entity).ejbFindAllBySchool(school);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public SchoolCoursePackage findBySchoolAndSeasonAndPackage(School school, SchoolSeason season,
			CoursePackage coursePackage) throws FinderException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		Object pk = ((SchoolCoursePackageBMPBean) entity).ejbFindBySchoolAndSeasonAndPackage(school, season, coursePackage);
		this.idoCheckInPooledEntity(entity);
		return this.findByPrimaryKey(pk);
	}

	public int getNumberOfSchoolPackages(CoursePackage coursePackage) throws IDOException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		int theReturn = ((SchoolCoursePackageBMPBean) entity).ejbHomeGetNumberOfSchoolPackages(coursePackage);
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}
}
