package org.cas.iie.xp.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.cas.iie.xp.dao.GroupDao;
import org.cas.iie.xp.dao.PolicyDao;
import org.cas.iie.xp.model.GroupInfo;
import org.cas.iie.xp.model.PolicyInfo;
import org.cas.iie.xp.service.GroupService;
import org.springframework.stereotype.Component;


@Component("groupService")
public class GroupServiceImpl implements GroupService {

	private GroupDao gd;
	
	private PolicyDao pd;
	
	public GroupDao getGd() {
		return gd;
	}
	
	@Resource(name="groupDao")
	public void setGd(GroupDao gd) {
		this.gd = gd;
	}
	
	@Resource(name="policyDao")
	public void setPd(PolicyDao pd) {
		this.pd = pd;
	}

	public PolicyDao getPd() {
		return pd;
	}

	
	@Override
	public List<GroupInfo> getGroupList() {
		// TODO Auto-generated method stub
		return gd.getGroupList();
	}

	@Override
	public boolean deleteGroupById(int groupId) {
		
		List<PolicyInfo> pl = new ArrayList<PolicyInfo>();
		
		pl = pd.getPolicyByGroupId(groupId);
		
		List<PolicyInfo> pll = pd.getPolicy("http://local.com", groupId);
		
		List<PolicyInfo> pal = pd.getPolicy("http://AmazonS3.com", groupId);
		
		if(gd.deleteGroupById(groupId)){
				
			pd.deletePolicyByGroup(groupId);
			
			if(pll.size()!=0){
				
				this.deleteOldFile(pll.get(0).getPolicy_file());
			}
			
			if(pal.size()!=0){
				
				this.deleteOldFile(pal.get(0).getPolicy_file());
			}
			
			return true;
		}
		
		return false;
	}

	@Override
	public int addGroup(GroupInfo gi) {
		// TODO Auto-generated method stub
		return gd.addGroup(gi);
	}

	@Override
	public GroupInfo getGroupById(int groupId) {
		// TODO Auto-generated method stub
		return gd.getGroupById(groupId);
	}

	@Override
	public boolean isAdmin(String username, String password) {
		// TODO Auto-generated method stub
		return gd.isAdmin(username, password);
	}
	
	private void deleteOldFile(String xmlname){
		
		String policyPath = System.getProperty("user.dir").replace("bin", "webapps")
								+ "\\documents\\policy\\" + xmlname;
		
		File xmlFile = new File(policyPath);
		
		if(xmlFile.exists()){
			
			xmlFile.delete();
		}
	}

}
