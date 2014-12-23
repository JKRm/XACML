package org.cas.iie.xp.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.cas.iie.xp.dao.PolicyDao;
import org.cas.iie.xp.model.PolicyInfo;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

@Component("policyDao")
public class PolicyDaoImpl implements PolicyDao {
	
	private HibernateTemplate hibernateTemplate;
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	
	@Resource(name="hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@Override
	public List<PolicyInfo> getPolicyByGroupId(int groupId) {
		
		List<PolicyInfo> policyList = new ArrayList<PolicyInfo>();
		
		try{
			
			policyList = 
				hibernateTemplate.find("from PolicyInfo pi where pi.group_id=?",groupId);
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			System.out.println("获取policy列表失败！");
			
		}
		
		return policyList;
	}


	@Override
	public PolicyInfo addPolicy(PolicyInfo pi) {
		
		try {
			
			hibernateTemplate.save(pi);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			return null;
			
		}
		
		return pi;
		
	}

	@Override
	public boolean deltePolicyById(int policyId) {
		
		List<PolicyInfo> policyList = new ArrayList<PolicyInfo>();
		
		try{
			
			policyList = 
				hibernateTemplate.find("from PolicyInfo pi where pi.policy_id=?",policyId);
			
			if(policyList.size()!=0){
				
				hibernateTemplate.delete(policyList.get(0));
				
				return true;
			}
			
			else{
				
				System.out.println("不存在此policy！删除失败！");
				
				return false;
			}
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			System.out.println("获取policy列表失败！");
			
			return false;
			
		}
		
	}


	@Override
	public PolicyInfo getPolicyById(int policyId) {
		
		List<PolicyInfo> policyList = new ArrayList<PolicyInfo>();
		
		try{
			
			policyList = 
				hibernateTemplate.find("from PolicyInfo pi where pi.policy_id=?",policyId);
			
			if(policyList.size()!=0){
				
				return policyList.get(0);
			}
			
			else{
				
				System.out.println("不存在此Policy！获取失败！");
				
				return null;
			}
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			System.out.println("获取Policy列表失败！");
			
			return null;
			
		}
		
	}

	@Override
	public boolean policyUpdate(PolicyInfo pi) {
		
		try {
			
			hibernateTemplate.update(pi);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			return false;
			
		}
		
		return true;
		
	}

	@Override
	public List<PolicyInfo> getPolicy(String resName, int GroupId) {
		
		List<PolicyInfo> policyList = new ArrayList<PolicyInfo>();
		
		policyList = 
			hibernateTemplate.find("from PolicyInfo pi" +
					" where pi.resource_name=? and pi.group_id=?",resName,GroupId);
	
		
		return policyList;
	}

	@Override
	public List<PolicyInfo> getPolicy(String resName, int GroupId, String action) {
		
		List<PolicyInfo> policyList = new ArrayList<PolicyInfo>();
		
		policyList = 
			hibernateTemplate.find("from PolicyInfo pi" +
					" where pi.resource_name=? and pi.group_id=? and pi.policy_type=?"
					,resName,GroupId,action);
	
		
		return policyList;
	}

	@Override
	public boolean deletePolicyByGroup(int groupId) {
		
		List<PolicyInfo> policyList = new ArrayList<PolicyInfo>();
		
		try{
			
			policyList = 
				hibernateTemplate.find("from PolicyInfo pi where pi.group_id=?",groupId);
			
			if(policyList.size()!=0){
				
				for(int i=0; i<policyList.size(); i++){
					
					hibernateTemplate.delete(policyList.get(i));
				}
				
				return true;
			}
			
			else{
				
				System.out.println("不存在此policy！删除失败！");
				
				return false;
			}
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			System.out.println("获取policy列表失败！");
			
			return false;
			
		}
		
	}


}
