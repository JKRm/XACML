package org.cas.iie.xp.action;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.util.ServletContextAware;
import org.cas.iie.xp.model.LocalBucket;
import org.cas.iie.xp.model.LocalObject;
import org.cas.iie.xp.service.InvokingService;
import org.cas.iie.xp.service.LocalStorageService;
import org.cas.iie.xp.service.UserInfo;
import org.cas.iie.xp.util.DocConverter;
import org.cas.iie.xp.util.GenerationToFlv;

import com.opensymphony.xwork2.ActionSupport;

public class LocalStorageAction extends ActionSupport implements ServletContextAware,
ServletRequestAware{
	
	private InvokingService is;
	
	private LocalStorageService lss;
	
	private int bucketId;
	
	private int objectId;
	
	private int tarBucket;
	
	private String newName;
	
	private InputStream inputStream;
	
	private File myFile;  
	
	private String myFileContentType;  
	
	private String myFileFileName; 
	
	private UserInfo ui ;
	
	private HttpServletRequest request;

	private HttpSession session;

	private ServletContext context;
	
	public LocalStorageAction(){
		
		super();
		
		ui = new UserInfo();
		
		ui.setResGroup("http://local.com");
	}
	
	public void setIs(InvokingService is) {
		this.is = is;
	}

	public InvokingService getIs() {
		return is;
	}
	
	public void setUi(UserInfo ui) {
		this.ui = ui;
	}

	public UserInfo getUi() {
		return ui;
	}

	public File getMyFile() {
		return myFile;
	}

	public void setMyFile(File myFile) {
		this.myFile = myFile;
	}

	public String getMyFileContentType() {
		return myFileContentType;
	}

	public void setMyFileContentType(String myFileContentType) {
		this.myFileContentType = myFileContentType;
	}

	public String getMyFileFileName() {
		return myFileFileName;
	}

	public void setMyFileFileName(String myFileFileName) {
		this.myFileFileName = myFileFileName;
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

	@Resource(name ="localStorageService")
	public void setLss(LocalStorageService lss) {
		this.lss = lss;
	}

	public LocalStorageService getLss() {
		return lss;
	}
	
	public String getObjectList(){
		
		if(this.getBucketId()!=0){
			
			session.setAttribute("bucketId", this.getBucketId());
		}
		
		else{
			
			bucketId = (Integer) session.getAttribute("bucketId");
		}
		
		List<LocalObject> loList = new ArrayList<LocalObject>();
		
		String bucketName = lss.getBucketbById(bucketId).getBucket_name();
		
		loList = lss.getObjectsList(bucketName);
		
		request.setAttribute("loList", loList);
		
		request.setAttribute("bn", bucketName);
		
		return SUCCESS;
	}
	
	public String deleteObject() throws Exception{
		
		String result = null;
		
		if(this.getResult("delete")){
		
		LocalObject lo = lss.getObjectbById(objectId);
		
		String oldname = lo.getObject_name();
		
		if(lss.deleteObject(objectId)){
			
			result = "{\"result\":\"success\"," +
			"\"oldname\":\""+oldname +"\"}";
		}
		
		else{
			
			result = "{\"result\":\"error\"}";
			
		}
		
		}
		
		else{
			
			result = "{\"result\":\"deny\"}";
		}
		
		inputStream = new ByteArrayInputStream(result.getBytes("UTF-8"));
		
		return SUCCESS;
	}
	
	
	public String renameObject() throws Exception{
		
		String result = null;
		
		LocalObject lo = lss.getObjectbById(objectId);
		
		String oldpart = lo.getObject_name().substring(lo.getObject_name().lastIndexOf("."));
		
		String newpart = newName.substring(0,newName.lastIndexOf("."));
		
		newName = newpart + oldpart;
		
		if(lss.isObjectExist(newName, lo.getBucket_name())){
		
		if(this.getResult("rename")){
		
		String oldname = lo.getObject_name();
		
		System.out.println("--------------------------------");
		
		if(lss.renameObject(objectId, newName)){
			
			result = "{\"result\":\"success\"," +
					"\"oldname\":\""+oldname +"\"}";
			
		}
		
		else{
			
			result = "{\"result\":\"error\"}";
			
		}
		
		}
		
		else{
			
			result = "{\"result\":\"deny\"}";
		}
		}
		else{
			result = "{\"result\":\"exist\"}";
		}
		
		inputStream = new ByteArrayInputStream(result.getBytes("UTF-8"));
		
		return SUCCESS;
		
	}
	
	
	public String moveObject() throws Exception{
		
		String result = null;
		
		LocalObject lo = new LocalObject();
		
		LocalBucket lb = new LocalBucket();
		
		lo = lss.getObjectbById(objectId);
		
		lb = lss.getBucketbById(tarBucket);
		
		if(lss.isObjectExist(lo.getObject_name(), lb.getBucket_name())){
		
		if(this.getResult("upload")&&this.getResult("delete")){
		
		if(lss.moveObject(objectId, tarBucket)){
			
			result = "{\"result\":\"success\"}";
		}
		
		else{
			
			result = "{\"result\":\"error\"}";
		}
		
		}
		
		else{
			
			result = "{\"result\":\"deny\"}";
		}
		}
		else{
			
			result = "{\"result\":\"exist\"}";
		}
		
		inputStream = new ByteArrayInputStream(result.getBytes("UTF-8"));
		
		return SUCCESS;
		
		
	}
	
	public String copyObject() throws Exception{
		
		String result = null;
		
		LocalObject lo = new LocalObject();
		
		LocalBucket lb = new LocalBucket();
		
		lo = lss.getObjectbById(objectId);
		
		lb = lss.getBucketbById(tarBucket);
		
		if(lss.isObjectExist(lo.getObject_name(), lb.getBucket_name())){
		
		if(this.getResult("upload")){
		
		if(lss.copyObject(objectId, tarBucket)){
			
			result = "{\"result\":\"success\"}";
		}
		
		else{
			
			result = "{\"result\":\"error\"}";
		}
		
		}
		
		else{
			
			result = "{\"result\":\"deny\"}";
		}
		}
		else{
			result = "{\"result\":\"exist\"}";
		}
		
		inputStream = new ByteArrayInputStream(result.getBytes("UTF-8"));
		
		return SUCCESS;
		
		
	}
	
	public String addBucket() throws Exception{
		
		String result = null;
		
		if(this.getResult("upload")){
		
		if(lss.addBucket(newName)){
			
			result = "{\"result\":\"success\"}";
		}
		
		else{
			
			result = "{\"result\":\"error\"}";
		}
		}
		else{
			
			result = "{\"result\":\"deny\"}";
		}
		
		inputStream = new ByteArrayInputStream(result.getBytes("UTF-8"));
		
		return SUCCESS;
		
	}
	
	public String deleteBucket() throws Exception{
		
		String result = null;
		
		if(this.getResult("delete")){
		
		LocalBucket lb = lss.getBucketbById(bucketId);
		
		String oldname = lb.getBucket_name();
		
		if(lss.deleteBucket(bucketId)){
			
			result = "{\"result\":\"success\"," +
			"\"oldname\":\""+oldname +"\"}";
		}
		
		else{
			
			result = "{\"result\":\"error\"}";
			
		}
		}
		else{
			
			result = "{\"result\":\"deny\"}";
		}
		
		inputStream = new ByteArrayInputStream(result.getBytes("UTF-8"));
		
		return SUCCESS;
	}
	
	public String uploadObject() throws Exception{
		
		session.setAttribute("bucketId", bucketId);
		
		LocalBucket lb = new LocalBucket();
		
		lb = lss.getBucketbById(bucketId);
		
		if(lss.isObjectExist(myFileFileName, lb.getBucket_name())){
		
		if(this.getResult("upload")){
		
		InputStream is = new FileInputStream(myFile);
		
		String uploadPath = System.getProperty("user.dir").replace("bin", "webapps")
								+ "\\localResource\\" + lb.getBucket_name();
		
		File indat = new File(uploadPath);
		
		if(!indat.exists()){
			
			indat.mkdir();
		}
		
		File toFile = new File(uploadPath, this.getMyFileFileName());
		
		OutputStream os = new FileOutputStream(toFile);

		byte[] buffer = new byte[1024];

		int length = 0;

		while ((length = is.read(buffer)) > 0) {
			
			os.write(buffer, 0, length);
		}
		
		is.close();
		
		os.close();
		
		String real = uploadPath + "\\"+ myFileFileName;
		
		File newFile = new File(real);
		
		InputStream ais = new FileInputStream(newFile);
		
		int fileSize = ais.available();
		
		ais.close();
		
		Thread.sleep(300);
		
		String realType = myFileFileName.substring(myFileFileName.lastIndexOf(".")+1);
		
		String fileloc = null;
		
		System.out.println(realType);
		
		if(realType.equals("doc")||realType.equals("docx")||
				realType.equals("ppt")||realType.equals("pptx")||
				realType.equals("pdf")||realType.equals("xls")||
				realType.equals("txt")||realType.equals("xlsx")){
		
		DocConverter dc = new DocConverter(real);
		
		 dc.conver();
		 
		 fileloc = dc.getSwfName();
		 
		}
		
		
		else if(realType.equals("mp4")||realType.equals("avi")||
				realType.equals("3gp")||realType.equals("asf")||
				realType.equals("mpg")||realType.equals("wmv")||
				realType.equals("flv")||realType.equals("mov")||
				realType.equals("asx")){
			
			
			GenerationToFlv flv = new GenerationToFlv();
			
			flv.setOldPath(real);
			
			String tempUrlflv = System.getProperty("user.dir").replace("bin", "webapps")
			+ "\\localResource" +"\\flvLocation";
			
			File flvFile = new File(tempUrlflv);
			
			if(!flvFile.exists()){
				
				flvFile.mkdir();
			}
			
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddhhmmss");
	        String sd = sf.format(new Date());
	        
	        String flvName = sd + ".flv";
			
			String newurlflv = tempUrlflv +"\\" + flvName;
			
			flv.setNewPath(newurlflv);
			
			flv.process();
			
			fileloc = flvName;
			
		}
		
		else if(realType.equals("mp3")){
			
			fileloc = myFileFileName;
		}
		
		lss.uploadinfo(lb.getBucket_name(), real, fileSize, fileloc);
		
		
			return SUCCESS;
		
		}
		
		else{
			
			return "deny";
		}
		}
		else{
			
			return "exist";
		}
		
		
	}
	
	public String preview(){
		
		LocalObject lo = new LocalObject();
		
		lo = lss.getObjectbById(objectId);
		
		String name = lo.getObject_name();
		
		String realType = name.substring(name.lastIndexOf(".")+1);
		
		System.out.println( lo.getBucket_name());
		System.out.println(lo.getObject_file());
		System.out.println(objectId);
		
		 if(realType.equals("mp4")||realType.equals("avi")||
				realType.equals("3gp")||realType.equals("asf")||
				realType.equals("mpg")||realType.equals("wmv")||
				realType.equals("flv")||realType.equals("mov")||
				realType.equals("asx")){
		
			 String flvPlayer = System.getProperty("user.dir").replace("bin", "webapps")
				+"\\XACML_Project_Clients\\tools\\Flvplayer.swf";
			 
			 String flvUrl = System.getProperty("user.dir").replace("bin", "webapps")
			 			+ "\\localResource\\flvLocation\\" + lo.getObject_file();
			 
		request.setAttribute("flvPlayer", flvPlayer);
		
		request.setAttribute("flvUrl", flvUrl);
		
		return SUCCESS;
		
		}
		
		return ERROR;
		
	}
	
	public String getListPerm() throws Exception{
		
		String result = null;
		
		if(this.getResult("list")){
		
			result = "{\"result\":\"success\"}";
			
		}
		
		else{
			
			result = "{\"result\":\"deny\"}";
		}
		
		inputStream = new ByteArrayInputStream(result.getBytes("UTF-8"));
		
		return SUCCESS;
	}
	
	public String getDownloadPerm() throws Exception{
		
		String result = null;
		
		LocalObject lo = new LocalObject();
		
		lo = lss.getObjectbById(objectId);
		
		if(this.getResult("download")){
		
			result = "{\"result\":\"success\"}";
			
			session.setAttribute("pathl", lo.getBucket_name()+"/"+lo.getObject_name());
			
		}
		
		else{
			
			result = "{\"result\":\"deny\"}";
		}
		
		inputStream = new ByteArrayInputStream(result.getBytes("UTF-8"));
		
		return SUCCESS;
		
	}
	
	
	private boolean getResult(String action){
		
		List<String> userGroup = new ArrayList<String>();
		
		if(session.getAttribute("username")!=null){
			
			userGroup = (List<String>) session.getAttribute("usergroup");
			
			ui.setGroup(userGroup.get(0));
		}
		
		ui.setResAction(action);
		
		return (is.getXacmlResult(ui).equals("Permit"));
		
		
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
