package org.cas.iie.idp.action;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

public class SsoAction extends ActionSupport implements ServletRequestAware{

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
	
	public String islogin(){
		
		if(session.getAttribute("username")==null){
			
			return ERROR;
		}
		
		else{
			
			return SUCCESS;
		}
	}

}
