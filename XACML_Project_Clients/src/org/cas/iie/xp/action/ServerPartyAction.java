package org.cas.iie.xp.action;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.util.ServletContextAware;
import org.cas.iie.xp.idp.user.UserRole;
import org.cas.iie.xp.saml.SAMLrequest;
import org.cas.iie.xp.saml.SAMLresponse;

import com.opensymphony.xwork2.ActionSupport;

public class ServerPartyAction extends ActionSupport implements ServletRequestAware,ServletResponseAware{
	
	private HttpServletRequest request;

	private HttpSession session;
	
	private HttpServletResponse response;
	
	private static String ISSUER = "http://sp.example.org";
	
	private static String PROVIDE_NAME = "sp.example.org";
	
	private static String ACS_URL = "http://192.168.111.78:8080/XACML_Project_Clients/access/spAction.action";
	
	//private static String IDP_URL = "http://192.168.112.122:8080/sso";
	
	private static String IDP_URL = "http://192.168.112.122:8080/XACML_IDP/admin/ssoAction.action";

	@Override
	public void setServletResponse(HttpServletResponse response) {
		
		this.response = response;
		
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		
		this.request = request;
		
		this.session = request.getSession();
	}
	
	private boolean setSession(UserRole user){
		
		session.setAttribute("username", user.getUsername());
		
		session.setAttribute("usergroup", user.getUsergroup());
		
		return true;
	}
	
	private String makeRequest(){
		
		SAMLrequest requesthandle = new SAMLrequest(ISSUER, PROVIDE_NAME, ACS_URL);
		
		String samlrequest = requesthandle.generateAuthnRequest();
		
		return samlrequest;
		
	}
	
	public String execute(){
		
		if(session.getAttribute("username") == null){
			
			String samlresponse = request.getParameter("SAMLResponse");
			
			if(samlresponse == null){
				
				String samlrequest = makeRequest();
				
				String rediredtUrl = IDP_URL+"?SAMLrequest="+samlrequest;
				
				try {
					
					response.sendRedirect(rediredtUrl);
					
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
			
			else{
				
				SAMLresponse responseHandle = new SAMLresponse(samlresponse);
				
				UserRole user = responseHandle.readResponse();
				
				if(user != null){
					
					setSession(user);
					
				}
				String redirectUrl= "/XACML_Project_Clients/access/accessAction.action";
				
				try {
					
					response.sendRedirect(redirectUrl);
					
				} catch (IOException e) {
					
					e.printStackTrace();
				}	
			}
			
			return null;
		}
		
		else{
			
			String redirectUrl= "/XACML_Project_Clients/access/accessAction.action";
			
			try {
				
				response.sendRedirect(redirectUrl);
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}	

		}
		
		return null;
	}


}
