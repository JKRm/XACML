package org.cas.iie.xp.service;

import java.util.List;

import org.cas.iie.xp.model.PolicyInfo;
import org.cas.iie.xp.model.UserInfo;

public interface PolicyAndRequestService {
	
	public String formRequest(UserInfo ui);
	
	public String formPolicy(UserInfo ui, String[] actions);
	
	public String getRequestResult(String requestFile);
	
	public List<PolicyInfo> getPolicyByGroupId(int groupId);
	
	public int addPolicy(PolicyInfo pi, String[] actions);
	
	public boolean deltePolicyById(int policyId);
	
	public PolicyInfo getPolicyById(int policyId);
	
	public List<PolicyInfo> getPolicy(String resName, int GroupId);
	
	public List<PolicyInfo> getPolicy(String resName, int GroupId, String action);

}
