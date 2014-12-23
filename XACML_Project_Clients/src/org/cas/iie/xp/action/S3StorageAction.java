package org.cas.iie.xp.action;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.util.ServletContextAware;
import org.cas.iie.xp.model.LocalBucket;
import org.cas.iie.xp.model.S3Bucket;
import org.cas.iie.xp.model.S3Object;
import org.cas.iie.xp.service.InvokingService;
import org.cas.iie.xp.service.S3StorageService;
import org.cas.iie.xp.service.UserInfo;

import com.opensymphony.xwork2.ActionSupport;

public class S3StorageAction extends ActionSupport implements ServletContextAware,
ServletRequestAware{
	
	private InvokingService is;
	
	private S3StorageService sss;
	
	private int bucketId;
	
	private int objectId;
	
	private int tarBucket;
	
	private String newName;
	
	private InputStream inputStream;
	
	private File myFile;  
	
	private String myFileContentType;  
	
	private String myFileFileName;  
	
	private HttpServletRequest request;

	private HttpSession session;

	private ServletContext context;
	
	private UserInfo ui;
	
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
	
	public S3StorageService getSss() {
		return sss;
	}
	
	@Resource(name ="s3StorageService")
	public void setSss(S3StorageService sss) {
		this.sss = sss;
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
	
	public S3StorageAction(){
		
		super();
		
		ui = new UserInfo();
		
		ui.setResGroup("http://AmazonS3.com");
	}

	public String getObjectList(){
		
		
		if(this.getBucketId()!=0){
			
			session.setAttribute("bucketId", this.getBucketId());
		}
		
		else{
			
			bucketId = (Integer) session.getAttribute("bucketId");
		}
		
		List<S3Object> soList = new ArrayList<S3Object>();
		
		String bucketName = sss.getBucketbById(bucketId).getBucket_name();
		
		soList = sss.getObjectsList(bucketName);
		
		request.setAttribute("soList", soList);
		
		request.setAttribute("bn", bucketName);
		
		return SUCCESS;
	}
	
	public String deleteObject() throws Exception{
		
		String result = null;
		
		if(this.getResult("delete")){
		
		S3Object so = sss.getObjectbById(objectId);
		
		String oldname = so.getObject_name();
		
		if(sss.deleteObject(objectId)){
			
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
		
		S3Object so = sss.getObjectbById(objectId);
		
		String oldpart = so.getObject_name().substring(so.getObject_name().lastIndexOf("."));
		
		String newpart = newName.substring(0,newName.lastIndexOf("."));
		
		newName = newpart + oldpart;
		
		if(sss.isObjectExist(newName, so.getBucket_name())){
		
		if(this.getResult("rename")){
		
		String oldname = so.getObject_name();
		
		if(sss.renameObject(objectId, newName)){
			
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
	
	public String addBucket() throws Exception{
		
		String result = null;
		
		if(this.getResult("upload")){
		
		if(sss.addBucket(newName)){
			
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
		
		S3Bucket lb = sss.getBucketbById(bucketId);
		
		String oldname = lb.getBucket_name();
		
		if(sss.deleteBucket(lb.getBucket_name())){
			
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
	
	public String moveObject() throws Exception{
		
		String result = null;
		
		S3Object so = new S3Object();
		
		S3Bucket sb = new S3Bucket();
		
		so = sss.getObjectbById(objectId);
		
		sb = sss.getBucketbById(tarBucket);
		
		if(sss.isObjectExist(so.getObject_name(), sb.getBucket_name())){
		
		if(this.getResult("delete")&&this.getResult("upload")){
		
		if(sss.moveObject(objectId, tarBucket)){
			
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
		
		S3Object so = new S3Object();
		
		S3Bucket sb = new S3Bucket();
		
		so = sss.getObjectbById(objectId);
		
		sb = sss.getBucketbById(tarBucket);
		
		if(sss.isObjectExist(so.getObject_name(), sb.getBucket_name())){
		
		if(this.getResult("upload")){
		
		if(sss.copyObject(objectId, tarBucket)){
			
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
	
	public String getS3file() throws UnsupportedEncodingException{
		
		String result = null;
		
		if(this.getResult("download")){
		
		org.cas.iie.xp.model.S3Object so = new org.cas.iie.xp.model.S3Object();
		
		so = sss.getObjectbById(objectId);
		
		String filePath  = System.getProperty("user.dir").replace("bin", "webapps")
								+ "\\downloadTemp\\" + so.getBucket_name();
		
		File file = new File(filePath);
		
		if(!file.exists()){
			
			file.mkdir();
		}
		
		if(sss.getObjectDownloaded(objectId, filePath +"\\" + so.getObject_name())){
			
			String path = so.getBucket_name()  +"/" + so.getObject_name();
				
			session.setAttribute("path", path);
			
			session.setAttribute("filename", so.getObject_name());
			
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
	
public String uploadObject() throws Exception{
	
	session.setAttribute("bucketId", bucketId);
	
	S3Bucket sb = new S3Bucket();
	
	sb = sss.getBucketbById(bucketId);
	
	if(sss.isObjectExist(this.getMyFileFileName(), sb.getBucket_name())){
	
	if(this.getResult("upload")){
		
		InputStream is = new FileInputStream(myFile);
		
		String uploadPath = System.getProperty("user.dir").replace("bin", "webapps")
								+ "\\fileTemp\\" + sb.getBucket_name();
		
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
		
		sss.uploadObjects(sb.getBucket_name(), real);
		
		this.deleteFile(indat);
		
		indat.delete();
		
			
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
	
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
		this.session = request.getSession();
	}

	@Override
	public void setServletContext(ServletContext context) {
		this.context = context;
	}
	
	public void deleteFile(File oldPath) {

		if (oldPath.isDirectory()) {

			File[] files = oldPath.listFiles();

			for (File file : files) {

				deleteFile(file);
			}

		} else {

			oldPath.delete();

		}

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

}
