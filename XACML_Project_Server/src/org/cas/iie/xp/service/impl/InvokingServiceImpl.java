package org.cas.iie.xp.service.impl;

import javax.annotation.Resource;
import javax.jws.WebService;

import org.cas.iie.xp.model.UserInfo;
import org.cas.iie.xp.service.InvokingService;
import org.cas.iie.xp.service.PolicyAndRequestService;

@WebService(endpointInterface = "org.cas.iie.xp.service.InvokingService", serviceName = "InvokingServiceImpl")
public class InvokingServiceImpl implements InvokingService {
	
	private PolicyAndRequestService pars;
	
	@Resource(name="policyAndRequestService")
	public void setPars(PolicyAndRequestService pars) {
		this.pars = pars;
	}

	public PolicyAndRequestService getPars() {
		return pars;
	}

	
	@Override
	public String getXacmlResult(UserInfo ui) {
		
		String requestFile = pars.formRequest(ui);
		
		return pars.getRequestResult(requestFile);
	}

}
