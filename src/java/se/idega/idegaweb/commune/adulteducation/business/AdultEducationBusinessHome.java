/**
 * 
 */
package se.idega.idegaweb.commune.adulteducation.business;

import com.idega.business.IBOHome;


/**
 * <p>
 * TODO Dainis Describe Type AdultEducationBusinessHome
 * </p>
 *  Last modified: $Date: 2006/04/05 12:00:29 $ by $Author: dainis $
 * 
 * @author <a href="mailto:Dainis@idega.com">Dainis</a>
 * @version $Revision: 1.34 $
 */
public interface AdultEducationBusinessHome extends IBOHome {

	public AdultEducationBusiness create() throws javax.ejb.CreateException, java.rmi.RemoteException;
}
