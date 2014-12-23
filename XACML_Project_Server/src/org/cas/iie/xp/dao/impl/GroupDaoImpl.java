package org.cas.iie.xp.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.cas.iie.xp.dao.GroupDao;
import org.cas.iie.xp.model.Admin;
import org.cas.iie.xp.model.GroupInfo;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

@Component("groupDao")
public class GroupDaoImpl implements GroupDao {
	
	private HibernateTemplate hibernateTemplate;
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	
	@Resource(name="hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@Override
	public List<GroupInfo> getGroupList() {
		
		List<GroupInfo> groupList = new ArrayList<GroupInfo>();
		
		try{
			
			groupList = 
				hibernateTemplate.find("from GroupInfo gi");
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			System.out.println("获取group列表失败！");
			
		}
		
		return groupList;
	}

	@Override
	public boolean deleteGroupById(int groupId) {
		
		List<GroupInfo> groupList = new ArrayList<GroupInfo>();
		
		try{
			
			groupList = 
				hibernateTemplate.find("from GroupInfo gi where gi.group_id=?",groupId);
			
			if(groupList.size()!=0){
				
				hibernateTemplate.delete(groupList.get(0));
				
				return true;
			}
			
			else{
				
				System.out.println("不存在此group！删除失败！");
				
				return false;
			}
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			System.out.println("获取group列表失败！");
			
			return false;
			
		}
		
	}


	@Override
	public int addGroup(GroupInfo gi) {
		
		if(!this.getGroupByName(gi.getGroup_name())){
		
		try {
			
			hibernateTemplate.save(gi);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			return 0;
			
		}
		
		return gi.getGroup_id();
		
		}
		
		return -1;
		
	}

	@Override
	public GroupInfo getGroupById(int groupId) {
		
		List<GroupInfo> groupList = new ArrayList<GroupInfo>();
		
		try{
			
			groupList = 
				hibernateTemplate.find("from GroupInfo gi where gi.group_id=?",groupId);
			
			if(groupList.size()!=0){
				
				return groupList.get(0);
			}
			
			else{
				
				System.out.println("不存在此group！获取失败！");
				
				return null;
			}
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			System.out.println("获取group列表失败！");
			
			return null;
			
		}
		
	}
	
	private boolean getGroupByName(String gname){
		
		List<GroupInfo> groupList = new ArrayList<GroupInfo>();
		
		groupList = 
			hibernateTemplate.find("from GroupInfo gi where gi.group_name=?",gname);
		
		if(groupList.size()!=0){
			
			return true;
		}
		
		else{
			
			return false;
		}
		
	}

	@Override
	public boolean isAdmin(String username, String password) {
		List<Admin> adminList = new ArrayList<Admin>();
		adminList = 
			hibernateTemplate.find("from Admin ad where ad.username=? and ad.password=?",username,password);
		if(adminList.size()!=0){
			
			return true;
		}
		else{
			return false;
		}
		
	}

}
