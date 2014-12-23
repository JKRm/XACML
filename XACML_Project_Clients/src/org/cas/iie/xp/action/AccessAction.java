package org.cas.iie.xp.action;


import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

public class AccessAction extends ActionSupport implements ServletRequestAware{
	
	private HttpServletRequest request;

	private HttpSession session;
	

	@Override
	public void setServletRequest(HttpServletRequest request) {
		
		this.request = request;
		
		this.session = request.getSession();
	}
	

	
	public String execute(){
		
		if(session.getAttribute("username")!=null){
			
			
			return SUCCESS;
		}
		
		else{
			
			return ERROR;
		}
	}
	
	public String logOut(){
		
		session.removeAttribute("username");
		
		session.removeAttribute("usergroup");
		
		return SUCCESS;
		
		
	}



	

}
