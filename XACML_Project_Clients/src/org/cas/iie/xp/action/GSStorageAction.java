package org.cas.iie.xp.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.util.ServletContextAware;
import org.cas.iie.xp.model.GsBucket;
import org.cas.iie.xp.model.GsObject;
import org.cas.iie.xp.service.GSStorageService;

import com.opensymphony.xwork2.ActionSupport;

public class GSStorageAction extends ActionSupport implements ServletContextAware,
ServletRequestAware{
	
	private GSStorageService gss;
	
	private int bucketId;
	
	private int objectId;
	
	private int tarBucket;
	
	private String newName;
	
	private InputStream inputStream;	
	
	@Resource(name ="gsStorageService")
	public void setGss(GSStorageService gss) {
		this.gss = gss;
	}

	public GSStorageService getGss() {
		return gss;
	}

	public void setTarBucket(int tarBucket) {
		this.tarBucket = tarBucket;
	}

	public int getTarBucket() {
		return tarBucket;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	private HttpServletRequest request;

	private HttpSession session;

	private ServletContext context;
	
	public int getObjectId() {
		return objectId;
	}

	public void setObjectId(int objectId) {
		this.objectId = objectId;
	}

	public int getBucketId() {
		return bucketId;
	}

	public void setBucketId(int bucketId) {
		this.bucketId = bucketId;
	}

	
	public String getObjectList(){
		
		
		if(this.getBucketId()!=0){
			
			session.setAttribute("bucketId", this.getBucketId());
		}
		
		else{
			
			bucketId = (Integer) session.getAttribute("bucketId");
		}
		
		List<GsObject> goList = new ArrayList<GsObject>();
		
		String bucketName = gss.getBucketbById(bucketId).getBucket_name();
		
		goList = gss.getObjectsList(bucketName);
		
		request.setAttribute("goList", goList);
		

		return SUCCESS;
	}
	
	public String deleteObject() throws Exception{
		
		String result = null;
		
		GsObject lo = gss.getObjectbById(objectId);
		
		String oldname = lo.getObject_name();
		
		if(gss.deleteObject(objectId)){
			
			result = "{\"result\":\"success\"," +
			"\"oldname\":\""+oldname +"\"}";
		}
		
		else{
			
			result = "{\"result\":\"error\"}";
			
		}
		
		inputStream = new ByteArrayInputStream(result.getBytes("UTF-8"));
		
		return SUCCESS;
	}
	
	
	public String renameObject() throws Exception{
		
		String result = null;
		
		GsObject lo = gss.getObjectbById(objectId);
		
		String oldname = lo.getObject_name();
		
		System.out.println("--------------------------------");
		
		if(gss.renameObject(objectId, newName)){
			
			result = "{\"result\":\"success\"," +
					"\"oldname\":\""+oldname +"\"}";
			
		}
		
		else{
			
			result = "{\"result\":\"error\"}";
			
		}
		
		inputStream = new ByteArrayInputStream(result.getBytes("UTF-8"));
		
		return SUCCESS;
		
	}
	
	public String moveObject() throws Exception{
		
		String result = null;
		
		System.out.println();
		
		if(gss.moveObject(objectId, tarBucket)){
			
			result = "{\"result\":\"success\"}";
		}
		
		else{
			
			result = "{\"result\":\"error\"}";
		}
		
		inputStream = new ByteArrayInputStream(result.getBytes("UTF-8"));
		
		return SUCCESS;
		
		
	}
	
	public String copyObject() throws Exception{
		
		String result = null;
		
		System.out.println();
		
		if(gss.copyObject(objectId, tarBucket)){
			
			result = "{\"result\":\"success\"}";
		}
		
		else{
			
			result = "{\"result\":\"error\"}";
		}
		
		inputStream = new ByteArrayInputStream(result.getBytes("UTF-8"));
		
		return SUCCESS;
		
		
	}
	

	public String addBucket() throws Exception{
		
		String result = null;
		
		if(gss.addBucket(newName)){
			
			result = "{\"result\":\"success\"}";
		}
		
		else{
			
			result = "{\"result\":\"error\"}";
		}
		
		inputStream = new ByteArrayInputStream(result.getBytes("UTF-8"));
		
		return SUCCESS;
		
	}
	
	public String deleteBucket() throws Exception{
		
		String result = null;
		
		GsBucket lb = gss.getBucketbById(bucketId);
		
		String oldname = lb.getBucket_name();
		
		if(gss.deleteBucket(lb.getBucket_name())){
			
			result = "{\"result\":\"success\"," +
			"\"oldname\":\""+oldname +"\"}";
		}
		
		else{
			
			result = "{\"result\":\"error\"}";
			
		}
		
		inputStream = new ByteArrayInputStream(result.getBytes("UTF-8"));
		
		return SUCCESS;
	}
	
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
		this.session = request.getSession();
	}

	@Override
	public void setServletContext(ServletContext context) {
		this.context = context;
	}

}
