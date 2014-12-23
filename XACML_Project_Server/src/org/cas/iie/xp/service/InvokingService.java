package org.cas.iie.xp.service;

import javax.jws.WebService;

import org.cas.iie.xp.model.UserInfo;


@WebService
public interface InvokingService {
	
	public String getXacmlResult(UserInfo ui);


}
