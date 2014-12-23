package org.cas.iie.idp.authenticate;

import org.cas.iie.idp.user.UserRole;

public interface IGetUser {
	
	public UserRole getUserByName(String username,boolean flag);
	
	public UserRole getUserByID(int userID,boolean flag);
	
}
