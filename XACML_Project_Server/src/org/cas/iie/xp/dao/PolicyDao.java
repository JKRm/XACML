package org.cas.iie.xp.dao;

import java.util.List;

import org.cas.iie.xp.model.PolicyInfo;

public interface PolicyDao {
	
	public List<PolicyInfo> getPolicyByGroupId(int groupId);
	
	public PolicyInfo addPolicy(PolicyInfo pi);
	
	public boolean deltePolicyById(int policyId);
	
	public boolean deletePolicyByGroup(int groupId);
	
	public PolicyInfo getPolicyById(int policyId);
	
	public boolean policyUpdate(PolicyInfo pi);
	
	public List<PolicyInfo> getPolicy(String resName, int GroupId);
	
	public List<PolicyInfo> getPolicy(String resName, int GroupId, String action);

}
