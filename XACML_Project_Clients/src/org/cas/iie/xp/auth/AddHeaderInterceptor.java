package org.cas.iie.xp.auth;

import java.util.List;

import javax.xml.namespace.QName;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class AddHeaderInterceptor extends AbstractPhaseInterceptor<SoapMessage> {
	
	private String userId;
	
	private String userPass;

	public AddHeaderInterceptor(String userId, String userPass) {
		
		super(Phase.PREPARE_SEND);
		
		this.userId = userId;
		
		this.userPass = userPass;
	}

	@Override
	public void handleMessage(SoapMessage msg) throws Fault {
		
		// TODO Auto-generated method stub
		List<Header> headers = msg.getHeaders();
		
		Document doc = DOMUtils.createDocument();
		
		Element ele = doc.createElement("authHeader");
		
		//�˴�������Ԫ��Ӧ���շ������Ǳߵ�Ҫ��
		Element idEle = doc.createElement("userId");
		
		idEle.setTextContent(userId);
		
		Element passEle = doc.createElement("userPass");
		
		passEle.setTextContent(userPass);
		
		ele.appendChild(idEle);
		
		ele.appendChild(passEle);
		
		//��eleԪ�ذ�װ��header����ӵ�soap��Ϣ��
		headers.add(new Header(new QName("java"),ele));
		
	}

}
