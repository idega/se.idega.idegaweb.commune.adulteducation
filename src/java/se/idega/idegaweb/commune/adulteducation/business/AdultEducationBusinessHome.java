/*
 * $Id: AdultEducationBusinessHome.java,v 1.21 2005/06/20 12:56:22 laddi Exp $
 * Created on Jun 20, 2005
 *
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package se.idega.idegaweb.commune.adulteducation.business;

import com.idega.business.IBOHome;


/**
 * Last modified: $Date: 2005/06/20 12:56:22 $ by $Author: laddi $
 * 
 * @author <a href="mailto:laddi@idega.com">laddi</a>
 * @version $Revision: 1.21 $
 */
public interface AdultEducationBusinessHome extends IBOHome {

	public AdultEducationBusiness create() throws javax.ejb.CreateException, java.rmi.RemoteException;
}
