package org.cas.iie.xp.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.cas.iie.xp.dao.GroupDao;
import org.cas.iie.xp.dao.PolicyDao;
import org.cas.iie.xp.model.GroupInfo;
import org.cas.iie.xp.model.PolicyInfo;
import org.cas.iie.xp.model.UserInfo;
import org.cas.iie.xp.service.PolicyAndRequestService;
import org.cas.iie.xp.util.DoPDP;
import org.cas.iie.xp.util.DocumentPath;
import org.cas.iie.xp.util.MyPolicyBuilder;
import org.cas.iie.xp.util.MyRequestBuilder;
import org.springframework.stereotype.Component;

@Component("policyAndRequestService")
public class PolicyAndRequestServiceImpl implements PolicyAndRequestService {
	
	private PolicyDao pd;
	
	private GroupDao gd;
	
	@Resource(name="policyDao")
	public void setPd(PolicyDao pd) {
		this.pd = pd;
	}

	public PolicyDao getPd() {
		return pd;
	}
	
	public GroupDao getGd() {
		return gd;
	}
	
	@Resource(name="groupDao")
	public void setGd(GroupDao gd) {
		this.gd = gd;
	}


	@Override
	public String formRequest(UserInfo ui) {

		MyRequestBuilder rb = new MyRequestBuilder();

		String xmlName = null;

		try {
			
			xmlName = rb.doBuild(ui);

		} catch (Exception e) {

			e.printStackTrace();

			System.out.println("XACML request创建失败");

		}

		return xmlName;
	}

	@Override
	public String formPolicy(UserInfo ui, String[] actions) {

		MyPolicyBuilder pb = new MyPolicyBuilder();
		
		String xmlname = null;

		try {

			xmlname = pb.policyBuild(ui,actions);

		} catch (Exception e) {

			e.printStackTrace();

			System.out.println("XACML policy创建失败");

		}

		return xmlname;
	}

	@Override
	public String getRequestResult(String requestFile) {

		DocumentPath dp = new DocumentPath();

		File file = new File(dp.getPath().get("policy"));

		File[] files = file.listFiles();

		String[] policyFiles = new String[files.length];

		requestFile = dp.getPath().get("request") + requestFile;

		if (policyFiles.length < 1) {

			System.out.println("policy不存在于指定路径");

			System.exit(1);
		}

		for (int i = 0; i < files.length; i++) {

			policyFiles[i] = files[i].getPath();
		}

		String result = null;

		try {

			DoPDP dpdp = new DoPDP(policyFiles);

			result = dpdp.doPDP(requestFile, policyFiles);

		} catch (Exception e) {

			e.printStackTrace();

			System.out.println("policy检查部分失败");
		}
		
		String policyPath = System.getProperty("user.dir").replace("bin", "webapps")
								+ "\\documents\\request\\" + requestFile;

		File xmlFile = new File(policyPath);

		if(xmlFile.exists()){

			xmlFile.delete();

		}
		

		return result;
	}

	@Override
	public List<PolicyInfo> getPolicyByGroupId(int groupId) {
		// TODO Auto-generated method stub
		return pd.getPolicyByGroupId(groupId);
	}

	@Override
	public int addPolicy(PolicyInfo pi, String[] actions) {
		
		UserInfo ui = new UserInfo();
		
		GroupInfo gi = new GroupInfo();
		
		gi = gd.getGroupById(pi.getGroup_id());
		
		System.out.println(gi.getGroup_name());
		
		ui.setGroup(gi.getGroup_name());
		
		//ui.setResAction(pi.getPolicy_type());
		
		ui.setResGroup(pi.getResource_name());
		
		if(pi.getPolicy_type()==null){
			
			String xmlname = this.formPolicy(ui, actions);
			
			PolicyInfo newPi;
			
			int flag = 1;
			
			for(int i=0; i<actions.length; i++){
				
				newPi = new PolicyInfo();
				
				newPi.setGroup_id(pi.getGroup_id());
				
				newPi.setResource_name(pi.getResource_name());
				
				newPi.setPolicy_type(actions[i]);
				
				newPi.setPolicy_file(xmlname);
				
				flag = flag*(pd.addPolicy(newPi).getPolicy_id());
			}
			
			return flag;
		}
		
		else{
			
			String[] newActions = new String[actions.length+1];
			
			for(int i=0; i<actions.length; i++){
				
				newActions[i] = actions[i];
			}
			
			newActions[actions.length] = pi.getPolicy_type(); 
			
			String xmlname = this.formPolicy(ui, newActions);
			
			List<PolicyInfo> piList = new ArrayList<PolicyInfo>();
			
			piList = this.getPolicy(pi.getResource_name(), pi.getGroup_id());
			
			String oldName = piList.get(0).getPolicy_file();
			
			this.deleteOldFile(oldName);
			
			for(int i=0; i<piList.size(); i++){
				
				PolicyInfo npi = piList.get(i);
				
				npi.setPolicy_file(xmlname);
				
				pd.policyUpdate(npi);
			}
			
			pi.setPolicy_file(xmlname);
			
			return pd.addPolicy(pi).getPolicy_id();
		}
		
	}

	@Override
	public boolean deltePolicyById(int policyId) {
		
		PolicyInfo pi = new PolicyInfo();
		
		pi = this.getPolicyById(policyId);
		
		UserInfo ui = new UserInfo();
		
		GroupInfo gi = new GroupInfo();
		
		gi = gd.getGroupById(pi.getGroup_id());
		
		ui.setGroup(gi.getGroup_name());
		
		ui.setResGroup(pi.getResource_name());
		
		boolean flag = pd.deltePolicyById(policyId);
		
		if(flag){
			
			this.deleteOldFile(pi.getPolicy_file());
			
			List<PolicyInfo> piList = new ArrayList<PolicyInfo>();
			
			piList = this.getPolicy(pi.getResource_name(), pi.getGroup_id());
			
			PolicyInfo npi;
			
			String[] actions = new String[piList.size()];
			
			for(int i=0; i<piList.size(); i++){
				
				actions[i] = piList.get(i).getPolicy_type();
			}
			
			String xmlname = this.formPolicy(ui, actions);
			
			for(int i=0; i<piList.size(); i++){
				
				PolicyInfo nnpi = piList.get(i);
				
				nnpi.setPolicy_file(xmlname);
				
				pd.policyUpdate(nnpi);
			}
		}
		
		return flag;
	}

	@Override
	public PolicyInfo getPolicyById(int policyId) {
		// TODO Auto-generated method stub
		return pd.getPolicyById(policyId);
	}

	@Override
	public List<PolicyInfo> getPolicy(String resName, int GroupId) {
		// TODO Auto-generated method stub
		return pd.getPolicy(resName, GroupId);
	}

	@Override
	public List<PolicyInfo> getPolicy(String resName, int GroupId, String action) {
		// TODO Auto-generated method stub
		return pd.getPolicy(resName, GroupId, action);
	}
	
	private void deleteOldFile(String xmlname){
		
		String policyPath = System.getProperty("user.dir").replace("bin", "webapps")
								+ "\\documents\\policy\\" + xmlname;
		
		File xmlFile = new File(policyPath);
		
		if(xmlFile.exists()){
			
			boolean flag = xmlFile.delete();
			
		}
		
		
	}
}
