package test;

import org.cas.iie.xp.model.UserInfo;
import org.cas.iie.xp.service.PolicyAndRequestService;
import org.cas.iie.xp.service.impl.PolicyAndRequestServiceImpl;

public class TestMain {

	
	public static void main(String[] args) {
		
		PolicyAndRequestService xxx = new PolicyAndRequestServiceImpl() ;
		
		//GroupService gs = new GroupServiceImpl();
		
		UserInfo ui = new UserInfo();
		
		String[] s = new String[2];
		
		s[0] = "read";
		
		s[1] = "commit";
		
		ui.setGroup("rootgroup");
		
		ui.setResAction("list");
		
		ui.setResGroup("http://local.com");
		
		ui.setResGroup("http://AmazonS3.com");
		
		//xxx.formPolicy(ui,s);
		
		//GroupInfo gi = new GroupInfo();
		
		//gi.setGroup_name("newGroup");
		
		//gs.addGroup(gi);
		
		System.out.println(xxx.getRequestResult(xxx.formRequest(ui)));

	}

}
