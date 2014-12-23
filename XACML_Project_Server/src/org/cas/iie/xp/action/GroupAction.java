package org.cas.iie.xp.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.cas.iie.xp.model.GroupInfo;
import org.cas.iie.xp.model.PolicyInfo;
import org.cas.iie.xp.service.GroupService;
import org.cas.iie.xp.service.PolicyAndRequestService;

import com.opensymphony.xwork2.ActionSupport;

public class GroupAction extends ActionSupport implements ServletRequestAware{
	
	private HttpServletRequest request;

	private HttpSession session;
	
	private GroupService gs;
	
	private PolicyAndRequestService pars;
	
	private InputStream inputStream;
	
	private int groupId;
	
	private String resName;
	
	private String[] amazon;
	
	private String[] local;
	
	private String groupname;
	
	private int policyId;
	
	private int callfunc;
	
	private String paction;
	
	private String username;
	
	private String password;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setPaction(String paction) {
		this.paction = paction;
	}
	public String getPaction() {
		return paction;
	}
	public void setCallfunc(int callfunc) {
		this.callfunc = callfunc;
	}
	public int getCallfunc() {
		return callfunc;
	}
	public int getPolicyId() {
		return policyId;
	}
	public void setPolicyId(int policyId) {
		this.policyId = policyId;
	}
	public void setResName(String resName) {
		this.resName = resName;
	}
	public String getResName() {
		return resName;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	public String getGroupname() {
		return groupname;
	}
	public String[] getAmazon() {
		return amazon;
	}
	public void setAmazon(String[] amazon) {
		this.amazon = amazon;
	}
	public String[] getLocal() {
		return local;
	}
	public void setLocal(String[] local) {
		this.local = local;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public int getGroupId() {
		return groupId;
	}
	@Resource(name="policyAndRequestService")
	public void setPars(PolicyAndRequestService pars) {
		this.pars = pars;
	}
	public PolicyAndRequestService getPars() {
		return pars;
	}
	public GroupService getGs() {
		return gs;
	}
	@Resource(name="groupService")
	public void setGs(GroupService gs) {
		this.gs = gs;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		
		this.request = request;
		
		this.session = request.getSession();
	}
	
	public String getGroupList(){
		
		List<GroupInfo> giList = new ArrayList<GroupInfo>();
		
		giList = gs.getGroupList();
		
		request.setAttribute("groupList", giList);
		
		return SUCCESS;
	}
	
	public String addGroup(){
		
		GroupInfo gi = new GroupInfo();
		
		gi.setGroup_name(groupname);
		
		int group_id = gs.addGroup(gi);
		
		if(group_id==-1){
			
			return ERROR;
		}
		
		if(amazon!=null){
			
			PolicyInfo pi = new PolicyInfo();
			
			pi.setGroup_id(group_id);
			
			pi.setResource_name("http://AmazonS3.com");
			
			pars.addPolicy(pi, amazon);
			
		}
		
		if(local!=null){
			
			PolicyInfo pi = new PolicyInfo();
			
			pi.setGroup_id(group_id);
			
			pi.setResource_name("http://local.com");
				
			pars.addPolicy(pi,local);
			
		}
		
		return SUCCESS;
	}
	
	public String deleteGroup() throws Exception{
		
		String result = null;
		
		boolean flag = gs.deleteGroupById(groupId);
		
		if(flag){
			
			result = "{\"result\":\"success\"}";
		}
		
		else{
			
			result = "{\"result\":\"error\"}";
		}
		
		inputStream = new ByteArrayInputStream(result.getBytes("UTF-8"));
		
		return SUCCESS;
		
	}
	
	public String getPolicies(){
		
		List<PolicyInfo> piList = new ArrayList<PolicyInfo>();
		
		GroupInfo gi = new GroupInfo();
		
		gi = gs.getGroupById(groupId);
		
		piList = pars.getPolicy(resName, groupId);
		
		session.setAttribute("piList", piList);
		
		session.setAttribute("groupName", gi.getGroup_name());
		
		return SUCCESS;
	}
	
	
	public String getGroupList4p(){
		
		List<GroupInfo> giList = new ArrayList<GroupInfo>();
		
		giList = gs.getGroupList();
		
		request.setAttribute("groupList", giList);
		
		if(callfunc==1){
			
			return "change";
		}
		
		return SUCCESS;
	}
	
	public String deletePolicy() throws Exception{
		
		String result = null;
		
		boolean flag = pars.deltePolicyById(policyId);
		
		
		if(flag){
			
			result = "{\"result\":\"success\"}";
			
		}
		
		else{
			
			result = "{\"result\":\"error\"}";
		}
		
		inputStream = new ByteArrayInputStream(result.getBytes("UTF-8"));
		
		return SUCCESS;
	}
	
	public String changePolicy() throws UnsupportedEncodingException{
		
		String result = null;
		
		String[] oldAction = null;
		
		List<PolicyInfo> piList = new ArrayList<PolicyInfo>();
		
		List<PolicyInfo> oldList = new ArrayList<PolicyInfo>();
		
		piList = pars.getPolicy(resName, groupId, paction);
		
		oldList = pars.getPolicy(resName, groupId);
		
		if(piList.size()==0){
			
			PolicyInfo pi = new PolicyInfo();
			
			pi.setGroup_id(groupId);
			
			pi.setPolicy_type(paction);
			
			pi.setResource_name(resName);
			
			if(oldList.size()!=0){
				
				oldAction = new String[oldList.size()];
				
				for(int i=0; i<oldList.size(); i++){
					
					oldAction[i] = oldList.get(i).getPolicy_type();
				}
				
			}
			
			else{
				
				oldAction = new String[1];
				
				oldAction[0] = paction;
				
				pi.setPolicy_type(null);
			}
			
			System.out.println(pi.getGroup_id()+"!!!!!!!!!!");
			
			int flag = pars.addPolicy(pi, oldAction);
			
			if(flag!=0){
				
				result = "{\"result\":\"success\"}";
			}
			
			else{
				
				result = "{\"result\":\"error\"}";
			}
		}
		
		else{
			
			result = "{\"result\":\"exist\"}";
		}
		
		inputStream = new ByteArrayInputStream(result.getBytes("UTF-8"));
		
		return SUCCESS;
		
	}
	
	public String userLogin(){
		
		boolean flag =true;
		
		if(session.getAttribute("admin")==null){
			
			flag = gs.isAdmin(username, password);
			
			if(flag){
			
				session.setAttribute("admin", username);
				
				return SUCCESS;
			}
			
			else{
				return ERROR;
			}
		
		}
		
		return SUCCESS;
	}
	
	public String adminOut(){
		
		if(session.getAttribute("admin")!=null){
			
			session.removeAttribute("admin");
		}
		
		return SUCCESS;
	}

}
