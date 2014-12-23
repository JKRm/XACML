package org.cas.iie.xp.action;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.util.ServletContextAware;
import org.cas.iie.xp.model.GsBucket;
import org.cas.iie.xp.model.LocalBucket;
import org.cas.iie.xp.model.LocalObject;
import org.cas.iie.xp.model.S3Bucket;
import org.cas.iie.xp.model.S3Object;
import org.cas.iie.xp.service.GSStorageService;
import org.cas.iie.xp.service.InvokingService;
import org.cas.iie.xp.service.LocalStorageService;
import org.cas.iie.xp.service.S3StorageService;
import org.cas.iie.xp.service.UserInfo;

import com.opensymphony.xwork2.ActionSupport;

public class BucketAction extends ActionSupport implements ServletContextAware,
ServletRequestAware{
	
	private InvokingService is;
	
	private UserInfo ui ;
	
	private String sort;
	
	private String platform;
	
	private int bucketId;
	
	private int objectId;
	
	private LocalStorageService lss;
	
	private S3StorageService sss;
	
	private GSStorageService gss;
	
	private InputStream inputStream;
	
	private HttpServletRequest request;

	private HttpSession session;

	private ServletContext context;
	
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
	
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public LocalStorageService getLss() {
		return lss;
	}
	@Resource(name ="localStorageService")
	public void setLss(LocalStorageService lss) {
		this.lss = lss;
	}

	public S3StorageService getSss() {
		return sss;
	}
	@Resource(name ="s3StorageService")
	public void setSss(S3StorageService sss) {
		this.sss = sss;
	}


	public GSStorageService getGss() {
		return gss;
	}
	
	@Resource(name ="gsStorageService")
	public void setGss(GSStorageService gss) {
		this.gss = gss;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getPlatform() {
		return platform;
	}

	
	public int getBucketId() {
		return bucketId;
	}

	public void setBucketId(int bucketId) {
		this.bucketId = bucketId;
	}

	public int getObjectId() {
		return objectId;
	}

	public void setObjectId(int objectId) {
		this.objectId = objectId;
	}
	
	public BucketAction(){
		
		super();
		
		ui = new UserInfo();
	}

	public String execute()throws Exception{
		
		String result = "[";
		
		
		if(sort.equals("local")){
			
			List<LocalBucket> bucketList = new ArrayList<LocalBucket>();
			
			bucketList = lss.getBucketsList();
			
			for (int i = 0; i < bucketList.size(); i++) {
				LocalBucket lb = bucketList.get(i);
				if (i != (bucketList.size() - 1)) {
					result += "{\"text\":\"" + lb.getBucket_id()
							+ "\",\"value\":\""
							+ lb.getBucket_name() + "\"},";
				} else {
					result += "{\"text\":\"" + lb.getBucket_id()
							+ "\",\"value\":\"" + lb.getBucket_name() + "\"}]";
				}
			}
			
		}
		
		else if(sort.equals("amazon")){
			
			List<S3Bucket> bucketList = new ArrayList<S3Bucket>();
			
			bucketList = sss.getBucketsList();
			
			for (int i = 0; i < bucketList.size(); i++) {
				S3Bucket sb = bucketList.get(i);
				if (i != (bucketList.size() - 1)) {
					result += "{\"text\":\"" + sb.getBucket_id()
							+ "\",\"value\":\""
							+ sb.getBucket_name() + "\"},";
				} else {
					result += "{\"text\":\"" + sb.getBucket_id()
							+ "\",\"value\":\"" + sb.getBucket_name() + "\"}]";
				}
			}
			
		}
		
		else if(sort.equals("google")){
			
			List<GsBucket> bucketList = new ArrayList<GsBucket>();
			
			bucketList =  gss.getBucketsList();
			
			if(bucketList!=null){
				for (int i = 0; i < bucketList.size(); i++) {
					GsBucket gb = bucketList.get(i);
					if (i != (bucketList.size() - 1)) {
						result += "{\"text\":\"" + gb.getBucket_id()
								+ "\",\"value\":\""
								+ gb.getBucket_name() + "\"},";
					} else {
						result += "{\"text\":\"" + gb.getBucket_id()
								+ "\",\"value\":\"" + gb.getBucket_name() + "\"}]";
					}
				}
			}
		}
		
		else if(sort.equals("0")||sort.equals(0)){
			
			inputStream = new ByteArrayInputStream(result.getBytes("UTF-8"));
			
		}
		
		inputStream = new ByteArrayInputStream(result.getBytes("UTF-8"));
		
		return SUCCESS;
		
	}
	
	public String getBucketList(){
		
		if(platform.equals("local")){
			
			List<LocalBucket> lbList = new ArrayList<LocalBucket>();
			
			lbList = lss.getBucketsList();
			
			request.setAttribute("bucketList", lbList);
			
			return "local";
			
		}
		
		else if(platform.equals("s3")){
			
			List<S3Bucket> sbList = new ArrayList<S3Bucket>();
			
			sbList = sss.getBucketsList();
			
			request.setAttribute("bucketList", sbList);
			
			return "amazon";
		}
		
		else if(platform.equals("gs")){
			
			List<GsBucket> gbList = new ArrayList<GsBucket>();
			
			gbList = gss.getBucketsList();
			
			request.setAttribute("bucketList", gbList);
			
			return "google";
		}
		
		else{
			
			System.out.println("platform´«µÝ´íÎó");
			
			return ERROR;
		}
		
	}
	
	public String mvS32Local() throws UnsupportedEncodingException{
		
		boolean flag = true;
		
		String result = null;
		
		S3Object so = new S3Object();
		
		LocalBucket sb = new LocalBucket();
		
		sb = lss.getBucketbById(bucketId);
		
		so = sss.getObjectbById(objectId);
		
		if(lss.isObjectExist(so.getObject_name(), sb.getBucket_name())){
		
		if(this.getResult("delete",1)&&this.getResult("upload",0)){
		
		//DateFormat format = new SimpleDateFormat("ddhhmmssSSS");

		//String fileName = format.format(new Date());
		
		//String fileType = so.getObject_name().substring(so.getObject_name().lastIndexOf("."));
		
		String newPath = System.getProperty("user.dir").replace("bin", "webapps")
							+ "\\fileTemp\\" + sb.getBucket_name();
		
		File file = new File(newPath);
		
		if(!file.exists()){
			
			file.mkdir();
		}
		
		String filePath = newPath + "\\" + so.getObject_name();
		
		flag = sss.getObjectDownloaded(objectId, filePath);
		
		if(flag){
		
		flag = flag && lss.uploadObjects(sb.getBucket_name(), filePath);
		
		}
		
		if(flag){
			
			flag = flag && sss.deleteObject(objectId);
		}
		
		if (file.exists()) {

			this.deleteFile(file);
			
			file.delete();
		}
		
		if(flag){
			
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
	
	public String mvLocal2S3() throws UnsupportedEncodingException{
		
		boolean flag = true;
		
		String result = null;
		
		LocalObject lo = new LocalObject();
		
		S3Bucket sb = new S3Bucket();
		
		sb = sss.getBucketbById(bucketId);
		
		lo = lss.getObjectbById(objectId);
		
		if(sss.isObjectExist(lo.getObject_name(), sb.getBucket_name())){
		
		if(this.getResult("delete",0)&&this.getResult("upload",1)){
		
		//DateFormat format = new SimpleDateFormat("ddhhmmssSSS");

		//String fileName = format.format(new Date());
		
		//String fileType = so.getObject_name().substring(so.getObject_name().lastIndexOf("."));
		
		String newPath = System.getProperty("user.dir").replace("bin", "webapps")
							+ "\\localResource\\" + lo.getBucket_name() + "\\"
							+ lo.getObject_name();
		
		File file = new File(newPath);
		
		flag = sss.uploadObjects(sb.getBucket_name(), newPath);
		
		if(flag){
			
			flag = flag && lss.deleteObject(objectId);
		}
		
		if(flag){
			
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
	
	public String cpS32Local() throws UnsupportedEncodingException{
		
		boolean flag = true;
		
		String result = null;
		
		S3Object so = new S3Object();
		
		LocalBucket sb = new LocalBucket();
		
		sb = lss.getBucketbById(bucketId);
		
		so = sss.getObjectbById(objectId);
		
		if(lss.isObjectExist(so.getObject_name(), sb.getBucket_name())){
		
		if(this.getResult("upload",0)){
		
		//DateFormat format = new SimpleDateFormat("ddhhmmssSSS");

		//String fileName = format.format(new Date());
		
		//String fileType = so.getObject_name().substring(so.getObject_name().lastIndexOf("."));
		
		String newPath = System.getProperty("user.dir").replace("bin", "webapps")
							+ "\\fileTemp\\" + sb.getBucket_name();
		
		File file = new File(newPath);
		
		if(!file.exists()){
			
			file.mkdir();
		}
		
		String filePath = newPath + "\\" + so.getObject_name();
		
		flag = sss.getObjectDownloaded(objectId, filePath);
		
		if(flag){
		
		flag = flag && lss.uploadObjects(sb.getBucket_name(), filePath);
		
		}
		
		
		if (file.exists()) {

			this.deleteFile(file);
			
			file.delete();
		}
		
		if(flag){
			
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
	
public String cpLocal2S3() throws UnsupportedEncodingException{
		
		boolean flag = true;
		
		String result = null;
		
		LocalObject lo = new LocalObject();
		
		S3Bucket sb = new S3Bucket();
		
		sb = sss.getBucketbById(bucketId);
		
		lo = lss.getObjectbById(objectId);
		
		if(sss.isObjectExist(lo.getObject_name(), sb.getBucket_name())){
		
		if(this.getResult("upload",1)){
		
		//DateFormat format = new SimpleDateFormat("ddhhmmssSSS");

		//String fileName = format.format(new Date());
		
		//String fileType = so.getObject_name().substring(so.getObject_name().lastIndexOf("."));
		
		String newPath = System.getProperty("user.dir").replace("bin", "webapps")
							+ "\\localResource\\" + lo.getBucket_name() + "\\"
							+ lo.getObject_name();
		
		File file = new File(newPath);
		
		flag = sss.uploadObjects(sb.getBucket_name(), newPath);
		
		if(flag){
			
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
	
private boolean getResult(String action, int resGroup){
		
		List<String> userGroup = new ArrayList<String>();
		
		String res = null;
		
		if(resGroup==0){
			
			res = "http://local.com";
		}
		
		else if(resGroup==1){
			
			res = "http://AmazonS3.com";
		}
		
		if(session.getAttribute("username")!=null){
			
			userGroup = (List<String>) session.getAttribute("usergroup");
			
			ui.setGroup(userGroup.get(0));
		}
		
		ui.setResAction(action);
		
		ui.setResGroup(res);
		
		boolean flag = is.getXacmlResult(ui).equals("Permit");
		
		return flag;
		
		
	}
}
