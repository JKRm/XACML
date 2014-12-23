package org.cas.iie.xp.auth;

import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class AuthInterceptor extends AbstractPhaseInterceptor<SoapMessage>{
	
	public AuthInterceptor(){
		
		super(Phase.PRE_INVOKE);
	}
	
	@Override
	public void handleMessage(SoapMessage msg) throws Fault {
		
		List<Header> headers = new ArrayList<Header>();
		
		headers = msg.getHeaders();
		
		if(headers == null || headers.size()<1){
			
			throw new Fault(new IllegalArgumentException("SOAP消息header不存在"));
		}
		Header first = headers.get(0);
		
		Element ele = (Element)first.getObject();
		
		NodeList userIds = ele.getElementsByTagName("userId");
		
		NodeList userPasses = ele.getElementsByTagName("userPass");
		
		if(userIds.getLength()!=1){
			
			throw new Fault(new IllegalArgumentException("用户名格式不对！"));
			
		}
		
		if(userPasses.getLength()!=1){
			
			throw new Fault(new IllegalArgumentException("密码格式不对！"));
			
		}
		
		String userId = userIds.item(0).getTextContent();
		
		String userPass = userPasses.item(0).getTextContent();
		
		if(!userId.equals("root")||!userPass.equals("root")){
			
			throw new Fault(new IllegalArgumentException("用户名密码不正确！"));
		}
	}

}
