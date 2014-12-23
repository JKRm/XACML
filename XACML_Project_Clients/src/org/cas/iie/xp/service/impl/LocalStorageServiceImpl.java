package org.cas.iie.xp.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.cas.iie.xp.dao.LocalResourceDao;
import org.cas.iie.xp.model.LocalBucket;
import org.cas.iie.xp.model.LocalObject;
import org.cas.iie.xp.service.InvokingService;
import org.cas.iie.xp.service.LocalStorageService;
import org.cas.iie.xp.service.UserInfo;
import org.cas.iie.xp.util.DocConverter;
import org.cas.iie.xp.util.GenerationToFlv;
import org.springframework.stereotype.Component;

@Component("localStorageService")
public class LocalStorageServiceImpl implements LocalStorageService {

	private LocalResourceDao lrd;
	
	private InvokingService is;
	
	private UserInfo ui;

	public void setIs(InvokingService is) {
		this.is = is;
	}

	public InvokingService getIs() {
		return is;
	}

	@Resource(name = "localResourceDao")
	public void setLrd(LocalResourceDao lrd) {
		this.lrd = lrd;
	}

	public LocalResourceDao getLrd() {
		return lrd;
	}
	

	@Override
	public int getBucketNum() {

		int num = lrd.getBucketNum();

		System.out.println("Local bucket数量： " + num);

		return num;
	}
	

	@Override
	public boolean addBucket(String bucketName) {
		
		if (lrd.addBucket(bucketName)) {

			File file = new File(this.getResourceLocation() + bucketName + "\\");

			if (!file.isDirectory()) {

				file.mkdir();

			}

		}

		return true;
	}

	@Override
	public boolean uploadObjects(String bucketName, String filePath) {
		
		File inFile = new File(filePath);
		
		String fileType = filePath.substring(filePath.lastIndexOf("."));
		
		String fileName = filePath.substring(filePath.lastIndexOf("\\")+1);
		
		File outFile = new File(this.getResourceLocation() + bucketName + "\\" + fileName);
		
		boolean flag = true;
		
		int fileSize = 0;
		
		String fileloca = null;
		
		if(inFile.exists()){
			
			try {
				
				InputStream is = new FileInputStream(inFile);
				
				fileSize = is.available();
				
				OutputStream os = new FileOutputStream(outFile); 
				
				 byte[] buffer = new byte[1024];  
				 
				 int length = 0;
				 
				 while ((length = is.read(buffer))>0){
					 
					 os.write(buffer, 0, length);  
				 }
				 
				 is.close();
				 
				 os.close();
				 
				 Thread.sleep(300);
				 
				String realType = fileName.substring(fileName.lastIndexOf(".")+1);
				
				 if(realType.equals("doc")||realType.equals("docx")||
							realType.equals("ppt")||realType.equals("pptx")||
							realType.equals("pdf")||realType.equals("xls")||
							realType.equals("txt")||realType.equals("xlsx")){
					
					DocConverter dc = new DocConverter(
							this.getResourceLocation() + bucketName + "\\" + fileName);
					
					 dc.conver();
					 
					 fileloca = dc.getSwfName();
					 
					}
				 
				 else if(realType.equals("mp4")||realType.equals("avi")||
							realType.equals("3gp")||realType.equals("asf")||
							realType.equals("mpg")||realType.equals("wmv")||
							realType.equals("flv")||realType.equals("mov")||
							realType.equals("asx")){
						
						
						GenerationToFlv flv = new GenerationToFlv();
						
						flv.setOldPath(this.getResourceLocation() + bucketName + "\\" + fileName);
						
						String tempUrlflv = this.getResourceLocation()  +"\\flvLocation";
						
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
						
						fileloca = flvName;
						
					}
					
					else if(realType.equals("mp3")){
						
						fileloca = fileName;
					}
				 
				
			} catch (Exception e) {
				
				flag = false;
				
				e.printStackTrace();
			}  
			
		}
		
		else{
			
			flag = false;
		}
		
		if(flag){
			
			int newObjectId = lrd.uploadObjects(bucketName, filePath);
			
			flag = flag&&(newObjectId!=0);
			
			flag = flag&&lrd.CompleteObjectInfo(newObjectId, fileSize,fileloca);
		}
		
		
		
		return flag;
	}

	@Override
	public boolean uploadText(String content, String fileName, String bucketName) {
		return false;
	}

	@Override
	public List<LocalBucket> getBucketsList() {

		return lrd.getBucketsList();
	}

	@Override
	public List<LocalObject> getObjectsList(String bucketName) {
		return lrd.getObjectsList(bucketName);
	}

	@Override
	public boolean deleteBucket(int BucketId) {
		
		String bucketName = lrd.getBucketbById(BucketId).getBucket_name();
		
		String path = this.getResourceLocation() + bucketName;

		File file = new File(path);
		
		boolean flag = true;

		if (file.exists()) {

			this.deleteFile(file);
			
			file.delete();
			
			List<LocalObject> ol = new ArrayList<LocalObject>();
			
			ol = this.getListByBucketName(bucketName);
			
			for(int i=0; i<ol.size(); i++){
				
				if(ol.get(i).getObject_file()!=null){
					
					String swfpath = this.getResourceLocation() + "swfLocation\\"
					+ ol.get(i).getObject_file();
					
					String flvpath = this.getResourceLocation() + "flvLocation\\"
					+ ol.get(i).getObject_file();
					
					File swffile = new File(swfpath);
					
					File flvfile = new File(flvpath);
					
					if(swffile.exists()){
						
						swffile.delete();
					}
					
					if(flvfile.exists()){
						
						flvfile.delete();
					}
				}
			}
		}
		
		else{
			
			flag = false;
		}
		
		if(flag){
			
			flag = flag&&lrd.deleteBucket(BucketId);
		}

		return flag;
	}

	@Override
	public boolean getObjectDownloaded(String bucketName, String objectKey,
			String filePath) {
		return false;
	}

	@Override
	public boolean deleteObject(int ObjectId) {
		
		LocalObject lo = lrd.getObjectbById(ObjectId);

		String path = this.getResourceLocation() + lo.getBucket_name() + "\\"
				+ lo.getObject_name();
		
		if(lo.getObject_file()!=null){
		
		String swfpath = this.getResourceLocation() + "swfLocation\\"
		+ lo.getObject_file();
		
		String flvpath = this.getResourceLocation() + "flvLocation\\"
		+ lo.getObject_file();
		
		File swffile = new File(swfpath);
		
		File flvfile = new File(flvpath);
		
		if(swffile.exists()){
			
			swffile.delete();
		}
		
		if(flvfile.exists()){
			
			flvfile.delete();
		}
		}

		File file = new File(path);
		
		boolean flag = true;

		if (file.exists()) {

			file.delete();
		}
		
		else{
			
			flag = false;
		}
		
		if(flag){
			
			flag = flag&&lrd.deleteObject(ObjectId);
			
		}

		return flag;
	}

	@Override
	public boolean moveObject(int srcObjectId,int desBucketId) {
		
		LocalObject lo = new LocalObject();
		
		LocalBucket lb = new LocalBucket();
		
		lo = lrd.getObjectbById(srcObjectId);
		
		lb = lrd.getBucketbById(desBucketId);
		
		String srcBucket = lo.getBucket_name();
		
		String srcObject = lo.getObject_name();
		
		String desBucket = lb.getBucket_name();

		String path = this.getResourceLocation() + srcBucket + "\\" + srcObject;

		File oldFile = new File(path);

		String newPath = this.getResourceLocation() + desBucket + "\\";

		File fnewpath = new File(newPath);
		
		boolean flag = true;

		if (!fnewpath.exists()) {

			this.addBucket(desBucket);

		}
		
		if(!oldFile.exists()){

			flag = false;
			
			System.out.println("源文件不存在，移动失败！");
		}

		File fnew = new File(newPath + srcObject);

		flag = flag&&oldFile.renameTo(fnew);
		
		if(flag){
			
			flag = flag&&lrd.moveObject(srcObjectId,desBucketId);
		}

		return flag;
	}

	@Override
	public boolean renameObject(int ObjectId,String newName) {
		
		LocalObject lo = new LocalObject();
		
		lo = lrd.getObjectbById(ObjectId);
		
		String basePath = this.getResourceLocation() + lo.getBucket_name() + "\\";

		File oldFile = new File(basePath + lo.getObject_name());

		File newFile = new File(basePath + newName);

		boolean flag = true;

		if (oldFile.exists()&&!newFile.exists()) {

			oldFile.renameTo(newFile);
		}

		else {

			System.out.println("重命名文件时出错，请检查文件名的存在性！");

			flag = false;
		}
		
		if(flag){

		flag =   flag&&lrd.renameObject(ObjectId, newName);
		
		}
		
		return flag;
		
	}

	@Override
	public boolean copyObject(int srcObjectId, int desBucketId) {
		
		LocalObject lo = new LocalObject();
		
		LocalBucket lb = new LocalBucket();
		
		lo = lrd.getObjectbById(srcObjectId);
		
		lb = lrd.getBucketbById(desBucketId);
		
		String srcBucket = lo.getBucket_name();
		
		String srcObject = lo.getObject_name();
		
		String desBucket = lb.getBucket_name();

		String oldPath = this.getResourceLocation() + srcBucket + "\\"
				+ srcObject;

		String newPath = this.getResourceLocation() + desBucket + "\\"
				+ srcObject;

		boolean flag = true;

		try {
			int bytesum = 0;

			int byteread = 0;

			File oldfile = new File(oldPath);

			if (oldfile.exists()) {

				InputStream inStream = new FileInputStream(oldPath);

				FileOutputStream fs = new FileOutputStream(newPath);

				byte[] buffer = new byte[1444];

				while ((byteread = inStream.read(buffer)) != -1) {

					bytesum += byteread;

					fs.write(buffer, 0, byteread);

				}

				inStream.close();
				
				fs.close();
			}

		} catch (Exception e) {

			flag = false;

			e.printStackTrace();

		}
		
		if(flag){
			
			flag = flag&&lrd.copyObject(srcObjectId, desBucketId);
		}

		return flag;
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

	public String getResourceLocation() {

		String nowpath = System.getProperty("user.dir");

		String tempdir = nowpath.replace("bin", "webapps")
				+ "\\localResource\\";

		return tempdir;

	}

	@Override
	public LocalObject getObjectbById(int object_id) {
		// TODO Auto-generated method stub
		return lrd.getObjectbById(object_id);
	}

	@Override
	public LocalObject getObjectByName(String name) {
		// TODO Auto-generated method stub
		return lrd.getObjectByName(name);
	}

	@Override
	public LocalBucket getBucketbById(int bucket_id) {
		// TODO Auto-generated method stub
		return lrd.getBucketbById(bucket_id);
	}

	@Override
	public boolean uploadinfo(String bucketName, String filePath, 
			int Size, String objectFile) {
		
		boolean flag = true;
		
		int newObjectId = lrd.uploadObjects(bucketName, filePath);
		
		flag = flag&&(newObjectId!=0);
		
		flag = flag&&lrd.CompleteObjectInfo(newObjectId, Size, objectFile);
		
		return flag;
	}

	@Override
	public boolean isObjectExist(String oname, String obucket) {
		// TODO Auto-generated method stub
		return lrd.isObjectExist(oname, obucket);
	}

	@Override
	public List<LocalObject> getListByBucketName(String bucketName) {
		// TODO Auto-generated method stub
		return lrd.getListByBucketName(bucketName);
	}

}
