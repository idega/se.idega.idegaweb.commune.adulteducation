/*
 * $Id: AdultEducationSessionBean.java,v 1.3 2005/05/26 07:27:46 laddi Exp $
 * Created on May 24, 2005
 *
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package se.idega.idegaweb.commune.adulteducation.business;

import java.rmi.RemoteException;
import javax.ejb.FinderException;
import se.idega.idegaweb.commune.adulteducation.data.AdultEducationChoice;
import se.idega.idegaweb.commune.business.CommuneUserBusiness;
import com.idega.block.school.business.SchoolBusiness;
import com.idega.block.school.data.SchoolSeason;
import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.business.IBORuntimeException;
import com.idega.business.IBOSessionBean;
import com.idega.user.data.User;
import com.idega.util.IWTimestamp;


/**
 * Last modified: $Date: 2005/05/26 07:27:46 $ by $Author: laddi $
 * 
 * @author <a href="mailto:laddi@idega.com">laddi</a>
 * @version $Revision: 1.3 $
 */
public class AdultEducationSessionBean extends IBOSessionBean  implements AdultEducationSession{
	
	private Object iSeasonPK = null;
	private SchoolSeason iSeason = null;
	
	private Object iChoicePK = null;
	private AdultEducationChoice iChoice = null;
	
	private String iUserUniqueID = null;
	private User iUser = null;
	private Object iUserPK = null;

	public SchoolSeason getSchoolSeason() {
		if (iSeason == null && iSeasonPK != null) {
			try {
				iSeason = getSchoolBusiness().getSchoolSeason(new Integer(iSeasonPK.toString()));
			}
			catch (RemoteException re) {
				iSeason = null;
			}
		}
		else if (iSeason == null) {
			try {
				iSeason = getSchoolBusiness().getSchoolSeasonHome().findNextSeason(getSchoolBusiness().getCategoryAdultEducation(), new IWTimestamp().getDate());
			}
			catch (FinderException fe) {
				//Nothing found...
			}
			catch (RemoteException re) {
				throw new IBORuntimeException(re);
			}
		}
		return iSeason;
	}

	public void setSeason(Object seasonPK) {
		iSeasonPK = seasonPK;
		iSeason = null;
	}
	
	public AdultEducationChoice getChoice() {
		if (iChoice == null && iChoicePK != null) {
			try {
				iChoice = getAdultEducationBusiness().getChoice(new Integer(iChoicePK.toString()));
			}
			catch (FinderException fe) {
				fe.printStackTrace();
				iChoice = null;
			}
			catch (RemoteException re) {
				iChoice = null;
			}
		}
		return iChoice;
	}

	public void setChoice(Object choicePK) {
		iChoicePK = choicePK;
		iChoice = null;
	}

	public User getStudent() {
		if (iUser == null && iUserPK != null) {
			try {
				iUser = getUserBusiness().getUser(new Integer(iUserPK.toString()));
			}
			catch (RemoteException re) {
				iUser = null;
			}
		}
		return iUser;
	}

	public void setStudent(String userPK) {
		iUserPK = userPK;
		iUser = null;
	}

	public void setStudentUniqueID(String userUniqueID) {
		iUserUniqueID = userUniqueID;
		try {
			iUser = getUserBusiness().getUserByUniqueId(iUserUniqueID);
			iUserPK = iUser.getPrimaryKey();
		}
		catch (FinderException fe) {
			fe.printStackTrace();
			iUser = null;
			iUserPK = null;
		}
		catch (RemoteException re) {
			iUser = null;
			iUserPK = null;
		}
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

	private AdultEducationBusiness getAdultEducationBusiness() {
		try {
			return (AdultEducationBusiness) IBOLookup.getServiceInstance(this.getIWApplicationContext(), AdultEducationBusiness.class);
		}
		catch (IBOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}

}