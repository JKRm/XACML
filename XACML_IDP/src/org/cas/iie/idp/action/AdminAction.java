package org.cas.iie.idp.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.cas.iie.idp.admin.groupAdmin;
import org.cas.iie.idp.admin.userAdmin;
import org.cas.iie.idp.user.GroupRole;
import org.cas.iie.idp.user.UserRole;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;

public class AdminAction extends ActionSupport implements ServletRequestAware{
	
	
	private HttpServletRequest request;

	private HttpSession session;
	
	private InputStream inputStream;
	
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
	
	public String adminLogin(){
		
		String username = request.getParameter("username");
		
		String password = request.getParameter("password");
		
		if(username == null || password == null){
			
		}
		else{
			
			if(username.equals("admin")&& password.equals("admin")){
				
				session.setAttribute("username", username);
				

			}
		}
		
		return SUCCESS;
	}
	
	public String groupModify() throws Exception{
		
		String result = null;
		
		String action = request.getParameter("action");
		
		if(action != null){
			
			if(action.equals("getallgroups")){
				
				groupAdmin groupadmin = new groupAdmin();
				
				List<GroupRole> groups = groupadmin.getAllGroups(0, 0);
				
				Gson json = new Gson();
				
				result = json.toJson(groups);
				
			}
			else if(action.equals("addgroup")){
				
				String groupname = request.getParameter("groupname");
				
				if(groupname != null){
					
					GroupRole group = new GroupRole();
					
					group.setGroupname(groupname);
					
					groupAdmin groupadmin = new groupAdmin();
					
					result = String.valueOf(groupadmin.addGroup(group));
					
				}
				
			}
			else if(action.equals("delgroup")){
				
				String groupname = request.getParameter("groupname");
				
				if(groupname != null){
					
					groupAdmin groupadmin = new groupAdmin();
					
					result = String.valueOf(groupadmin.deleteGroup(groupname));
					
				}
			}
		}
		
		inputStream = new ByteArrayInputStream(result.getBytes("UTF-8"));
		
		return SUCCESS;
		
	}
	
	public String UserMotify() throws Exception{
		
		String result = null;
		
		String action = request.getParameter("action");
		
		if(action != null){
			
			if(action.equals("getallusers")){
				
				userAdmin useradmin = new userAdmin();
				
				List<UserRole> users = useradmin.getAllUsers(0, 0);
				
				Gson json = new Gson();
				
				result = json.toJson(users);
				
				useradmin.close();
				
				
			}
			else if(action.equals("adduser")){
				
				String username = request.getParameter("username");
				
				String realname = request.getParameter("realname");
				
				String password = request.getParameter("password");
				
				if(username != null && realname != null && password != null){
					
					UserRole user = new UserRole();
					
					user.setUsername(username);
					
					user.setRealname(realname);
					
					user.setPassword(password);
					
					userAdmin useradmin = new userAdmin();
					
					result = String.valueOf(useradmin.addUser(user));
					
				}
				
			}
			else if(action.equals("deluser")){
				
				String username = request.getParameter("username");
				
				if(username != null){
					
					userAdmin useradmin = new userAdmin();
					
					result = String.valueOf(useradmin.deleteuser(username));
					
					useradmin.close();
					
				}
				
			}
			else if(action.equals("getuser")){
				
				String username = request.getParameter("username");
				
				if(username != null){
					
					userAdmin useradmin = new userAdmin();
					
					UserRole user = useradmin.getUserByName(username,true);
					
					Gson json = new Gson();
					
					result = json.toJson(user);
					
				}
				
			}
			else if(action.equals("modifyusergroup")){
				
				String username = request.getParameter("username");
				
				String[] groups = request.getParameterValues("groupname");
				
				UserRole user = new UserRole();
				
				user.setUsername(username);
				
				user.setUsergroup(Arrays.asList(groups));
				
				userAdmin useradmin = new userAdmin();
				
				useradmin.modifyUseGroup(user);
				
				result = "true";
				
			}
		}
		
		inputStream = new ByteArrayInputStream(result.getBytes("UTF-8"));
		
		return SUCCESS;
	}

}
