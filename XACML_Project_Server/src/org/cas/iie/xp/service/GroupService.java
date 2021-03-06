package org.cas.iie.xp.service;

import java.util.List;

import org.cas.iie.xp.model.GroupInfo;

public interface GroupService {
	
	public List<GroupInfo> getGroupList();
	
	public boolean deleteGroupById(int groupId);
	
	public int addGroup(GroupInfo gi);
	
	public GroupInfo getGroupById(int groupId);
	
	public boolean isAdmin(String username, String password) ;

}
