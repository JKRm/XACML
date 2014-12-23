package org.cas.iie.idp.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.cas.iie.idp.authenticate.IGetUser;
import org.cas.iie.idp.authenticate.Impl.GetUserByLdap;
import org.cas.iie.idp.authenticate.Impl.UserAuthenticate;
import org.cas.iie.idp.log.Logger;
import org.cas.iie.idp.saml.SAMLrequest;
import org.cas.iie.idp.saml.SAMLresponse;
import org.cas.iie.idp.user.UserRole;
import org.opensaml.common.SAMLException;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport implements ServletRequestAware{

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
	
	public String execute(){
		
		String result = null;
		
		boolean continueLogin = false;
		
		boolean needToReturn = false;
		
		String[] samlResponseAttributes = null;
		
		String username = request.getParameter("username");
		
		String password = request.getParameter("password");
		
		String samlRequest = request.getParameter("SAMLrequest");
		
		if(request.getHeader("Referer") == null){
			
			continueLogin = false;
		}

		if(session != null && session.getAttribute("username") != null){
			
			continueLogin = true;
			
		}
		else{
			
			if(username == null ||password == null){
				
				continueLogin = false;
				
			}
			else{
				
				continueLogin = authenticateUser(username,password, request);
			}
		}
		
		if(continueLogin == true){
			
			IGetUser getuserhandle = new GetUserByLdap();
			
			UserRole user = getuserhandle.getUserByName(username, false);
			

			if(samlRequest == null || samlRequest.equals("null") || samlRequest.equals("undefined")){
				
				needToReturn = false;
				
			}
			else{
				
				needToReturn = true;
				
				try {
					samlResponseAttributes = makeAssertion(samlRequest, user);
					
					if(samlResponseAttributes == null){
						
						throw new SAMLException("samlrequest is invalid!");
					}
					
				} catch (SAMLException e) {
					
					needToReturn = false;
					
					Logger.writelog(e);
					
					e.printStackTrace();
				}

			}
			
			if(needToReturn == true){
				
				String samlresponse = samlResponseAttributes[0];
				
				String acsUrl = samlResponseAttributes[1];
				
				String[] keys   = {"islogin","action","acsUrl","username","samlResponse"};
				
				String[] values = {"true","submitresponse",acsUrl,username,samlresponse};
				
				result = generateJson(keys, values);
				
				try {
					inputStream = new ByteArrayInputStream(result.getBytes("UTF-8"));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			else{
				
				String[] keys   = {"islogin","action","username"};
				
				String[] values = {"true","welcome",username};
				
				result = generateJson(keys, values);
				
				try {
					inputStream = new ByteArrayInputStream(result.getBytes("UTF-8"));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
		return SUCCESS;
	}
	
	private String[] makeAssertion(String samlreuest,UserRole user){
		
		SAMLrequest requestHandle = new SAMLrequest(samlreuest);
		
		if(requestHandle.readFromRequest() == false){
			
			return null;
			
		}
		String issuer = requestHandle.getIssuerURL();
		
		String acsUrl = requestHandle.getAcsURL();
		
		String requestID = requestHandle.getRequestID();
		
		String providerName = requestHandle.getprovideName();
				
		SAMLresponse responseHandle = new SAMLresponse(user, issuer, requestID,acsUrl);
		
		responseHandle.generateAuthnResponse();
		
		String samlresponse = responseHandle.getSamlResponse();
		
		String[] samlResponseAttributes = new String[]{samlresponse,acsUrl};
		
		return samlResponseAttributes;
	}
	
	private String generateJson(String[] keys,String[] values){
		
		Gson gson = new Gson();
		
		Map<String,String> userObj = new HashMap<String, String>();
		
		if(keys.length != values.length){
			
			return null;
			
		}
		
		for(int i = 0 ; i < keys.length ;i++ ){
			
			userObj.put(keys[i],values[i]);
			
		}
		
		return gson.toJson(userObj);
	}
	
	private boolean authenticateUser
	(String username,String password,HttpServletRequest request){
		
		boolean result = false;
		
		if(username == null ||password == null){
			
			return false;
		}

		UserAuthenticate uAuth = new UserAuthenticate(username, password);
		
		result = uAuth.doAuthenticate();
		
		return result;
	}

}


