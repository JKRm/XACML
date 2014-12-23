package org.cas.iie.xp.action;

import javax.annotation.Resource;

import org.cas.iie.xp.service.InvokingService;
import org.cas.iie.xp.service.LocalStorageService;
import org.cas.iie.xp.service.S3StorageService;
import org.cas.iie.xp.service.UserInfo;

import com.opensymphony.xwork2.ActionSupport;

public class TestAction extends ActionSupport {
	
	private InvokingService is;
	
	private S3StorageService sss;
	
	private LocalStorageService lss;
	
	public TestAction(){
		
		System.out.println("hi!!!");
	}

	@Resource(name = "localStorageService")
	public void setLss(LocalStorageService lss) {
		this.lss = lss;
	}

	public LocalStorageService getLss() {
		return lss;
	}

	public void setIs(InvokingService is) {
		this.is = is;
	}

	public InvokingService getIs() {
		return is;
	}
	
	
	public void setSss(S3StorageService sss) {
		this.sss = sss;
	}

	public S3StorageService getSss() {
		return sss;
	}
	
	public String execute(){
		
		
		//System.out.println(lss.getBucketNum());
		
		//lss.addBucket("hello world");	
		
		//lss.addBucket("hello you");
		
		//List<String> bl = lss.getBucketsList();
		
		//for(int i=0;i<bl.size();i++){
			
			//System.out.println(bl.get(i));
		//}
		
		//lss.uploadObjects("hello you", "D:\\blackcat7.jpg");
		
		//StringLong om = lss.getObjectsList("hello you");
		
		//lss.deleteBucket("hello world");
		
		//System.out.println("hi!!!!!!");
		
		//lss.copyObject("hello you", "blackcat7.jpg", "hello world", "blackcat2.jpg");
		
		//System.out.println("hi!!!!!!");
		
		//lss.renameObject("hello world", "blackcat2.jpg", "ºÚÃ¨.jpg");
		
		//lss.moveObject("hello world", "blackcat7.jpg", "hello you", "xxxxx.jpg");
		
		//lss.copyObject("hello you", "xxxxx.jpg", "hello world", "blackcat8.jpg");
		
		//lss.renameObject("hello you", "xxxxx.jpg", "blackcat9.jpg");
		
		//lss.deleteObject("hello you", "blackcat9.jpg");
		
		//lss.deleteBucket("hello world");
	
		
		//lss.deleteObject("hello you", "blackcat.jpg");
		
		UserInfo ui = new UserInfo();
		
		ui.setGroup("testGroup");
		
		ui.setResAction("delete");
		
		ui.setResGroup("http://AmazonS3.com");
		
		//System.out.println(is.sayHi("JKR"));
		
//		System.out.println(is.getName());
		
		
		//System.out.println(is.formPolicy(ui));
		
		//System.out.println(is.getRequestResult(is.formRequest(ui)));
		System.out.println(is.getXacmlResult(ui));
		
		lss.getBucketNum();
		
		//System.out.println(is.getXacmlResult(ui));
		
//		System.out.println();
//		
		/**
		 
		StringCat sc = is.getAllCats();
		
		for(Entry entry : sc.getEntries()){
			
			System.out.println(entry.getKey() + entry.getValue().getName());
		}
		 
		 */
		
//		sss.uploadObjects("JKR_private", "D:/res/Apache_CXF_ÑµÁ·.pdf");
//		
//		sss.getBucketsList();
//		
//		Map<String, Long> s1 =  sss.getObjectsList("JKR_private");
//		
//		Set<String> keySet = s1.keySet();
//		
		
		//sss.getObjectDownloaded(arg0, arg1, arg2)
		
		//sss.getObjectsList("JKR_temp");
		//sss.copyObject("JKR_temp", "blackcat.jpg", "JKR_private", "ºÚÃ¨.jpg");
		
		//sss.getBucketNum();
//		
//		sss.getObjectDownloaded("JKR_private", "Apache_CXF_ÑµÁ·.pdf", "D:/oo.pdf");
//		
		//sss.getObjectDownloaded("JKR_temp", "blackcat.jpg", "D:/¿É°®.jpg");
		
		//sss.deleteObject("JKR_temp", "Apache_CXF_ÑµÁ·.pdf");
		
		//sss.shutDownService();
		
		return SUCCESS;
	}

	


	

}
