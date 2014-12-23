package org.cas.iie.xp.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;

import org.cas.iie.xp.dao.GSResourceDao;
import org.cas.iie.xp.model.GsBucket;
import org.cas.iie.xp.model.GsObject;
import org.cas.iie.xp.service.GSStorageService;
import org.cas.iie.xp.util.AccessUtils;
import org.jets3t.service.ServiceException;
import org.jets3t.service.impl.rest.httpclient.GoogleStorageService;
import org.jets3t.service.model.GSBucket;
import org.jets3t.service.model.GSObject;
import org.jets3t.service.security.GSCredentials;
import org.springframework.stereotype.Component;

@Component("gsStorageService")
public class GSStorageServiceImpl implements GSStorageService{
	
	private GSResourceDao grd;
	
	private GoogleStorageService gsService;

	public GSStorageServiceImpl() {

		GSCredentials gsCredentials;

		try {

			gsCredentials = AccessUtils.loadGSCredentials();

			gsService = new GoogleStorageService(gsCredentials);

			System.out.println("================================");

			System.out.println("       google storage平台连接成功！  ");

			System.out.println("================================");

		} catch (Exception e) {

			System.out.println("google storage连接失败！请检查证书有效性！");

			e.printStackTrace();
		}

	}
	
	@Resource(name="gsResourceDao")
	public void setGrd(GSResourceDao grd) {
		this.grd = grd;
	}

	public GSResourceDao getGrd() {
		return grd;
	}

	@Override
	public int getBucketNum() {
		// TODO Auto-generated method stub
		return grd.getBucketNum();
	}

	@Override
	public boolean addBucket(String bucketName) {
		
		if (grd.addBucket(bucketName)) {
			
			GSBucket newBucket;
			
			try {
				
				newBucket = gsService.createBucket(bucketName);
				
				System.out.println("GS bucket创建成功, bucket名: " + newBucket.getName());

				
			} catch (Exception e) {
				
				e.printStackTrace();
				
				System.out.println("bucket创建失败！bucket名可能已存在。");
				
				return false;
			}
			
			return true;
		}

		return false;
	}

	@Override
	public boolean uploadObjects(String bucketName, String filePath) {
		
		boolean flag = true;
		
		GSObject fileObject;
		
		GSBucket bucket  = new GSBucket();
		
		File file = new File(filePath);
		
		try {
			
			fileObject = new GSObject(file);
			
			gsService.putObject(bucketName, fileObject);
			
			System.out.println("上传文件成功！" + file.getName() + "已传至" + 
					"bucket: " + bucket.getName());
			
		} catch (Exception e) {
			
			System.out.println("上传object文件失败！");
			
			e.printStackTrace();
			
			return false;
		}
		
		flag = flag&&grd.uploadObjects(bucketName, filePath);
		
		return flag;
	}
	

	@Override
	public boolean uploadText(String content, String fileName, String bucketName) {
		// TODO Auto-generated method stub
		return grd.uploadText(content, fileName, bucketName);
	}

	@Override
	public List<GsBucket> getBucketsList() {
		// TODO Auto-generated method stub
		return grd.getBucketsList();
	}

	@Override
	public List<GsObject> getObjectsList(String bucketName) {
		// TODO Auto-generated method stub
		return grd.getObjectsList(bucketName);
	}

	@Override
	public boolean deleteBucket(String bucketName) {
		
		boolean flag = true;
		
		try {
			
			GSObject[] objects = gsService.listObjects(bucketName);
			
			if(objects.length!=0){
				
				for(int i=0; i<objects.length; i++){
					
					gsService.deleteObject(bucketName, objects[i].getKey());
				}
				
			}
			
			gsService.deleteBucket(bucketName);
			
			System.out.println("删除bucket：" + bucketName + "列表成功！");
			
		} catch (ServiceException e) {
			
			e.printStackTrace();
			
			System.out.println("删除bucket：" + bucketName + "列表失败！");
			
			return false;
		}
		
		flag = flag&&grd.deleteBucket(bucketName);
		
		return flag;
	}

	
	@Override
	public String getObjectDetails(String bucketName, String objectName) {
		// TODO Auto-generated method stub
		return grd.getObjectDetails(bucketName, objectName);
	}

	@Override
	public boolean getObjectDownloaded(String bucketName, String objectKey,
			String filePath) {
		
		BufferedInputStream inBuff = null;
		
        BufferedOutputStream outBuff = null;
        
        File targetFile = new File(filePath);
		
		 try {
			 
			GSObject objectComplete = gsService.getObject(bucketName, objectKey);
			
			InputStream sourceFile =  objectComplete.getDataInputStream();
			
			inBuff = new BufferedInputStream(sourceFile);
			
			outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));
			
			byte[] b = new byte[1024 * 5];
			
            int len;
            
            while ((len = inBuff.read(b)) != -1) {
            	
                outBuff.write(b, 0, len);
                
            }
            outBuff.flush();
			
			System.out.println("文件" + objectKey + "下载成功！");
			
			System.out.println("下载至：" + targetFile.getAbsolutePath());
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			System.out.println("文件下载失败！请检查bucket或object是否存在！");
			
			return false;
		}
		finally {
			
            if (inBuff != null)
				try {
					inBuff.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
            if (outBuff != null)
				try {
					outBuff.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        } 		 
		 
		return true;
	}


	@Override
	public boolean deleteObject(int objectId) {
		
		GsObject go = new GsObject();
		
		go = grd.getObjectbById(objectId);
		
		String bucketName = go.getBucket_name();
		
		String objectKey = go.getObject_name();
		
		boolean flag = true;

		try {
			gsService.deleteObject(bucketName, objectKey);
			
			System.out.println("删除object " + objectKey + "成功！");
			
		} catch (ServiceException e) {
			
			e.printStackTrace();
			
			System.out.println("删除object " + objectKey + "失败！");
			
			return false;
		}
		
		flag = flag&&grd.deleteObject(objectId);
		
		return flag;
	}


	@Override
	public boolean moveObject(int objectId,  int tarBucket) {
		
		boolean flag = true;
		
		GsBucket gb = new GsBucket();
		
		GsObject go = new GsObject();
		
		gb = grd.getBucketbById(tarBucket);
		
		go = grd.getObjectbById(objectId);
		
		String srcBucket = go.getBucket_name();
		
		String desBucket = gb.getBucket_name();
		
		String srcObject = go.getObject_name();
		
		try {
			gsService.moveObject(srcBucket, srcObject, 
					desBucket, new GSObject(srcObject), false);
			
			System.out.println("已成功将" + srcBucket + "的object" + srcObject
					+ "移至" + desBucket  + "！");
			
		} catch (ServiceException e) {
			
			e.printStackTrace();
			
			System.out.println(srcObject + "移动失败！");
			
			return false;
		}
		
		flag = flag&& grd.moveObject(objectId, tarBucket);
		
		return flag;
	}


	@Override
	public boolean renameObject(int objectId, String newName) {
		
		boolean flag = true;
		
		GsObject go = new GsObject();
		
		go = grd.getObjectbById(objectId);
		
		String bucketName = go.getBucket_name();
		
		String oriName = go.getObject_name();

		try {
			
			gsService.renameObject(bucketName, oriName, new GSObject(newName));
			
			System.out.println("已成功将" + bucketName + "的object" + 
					oriName + "重命名为" + newName + "！");
			
		} catch (ServiceException e) {
			
			e.printStackTrace();
			
			System.out.println(oriName + "重命名失败！");
			
			return false;
		}
		
		flag = flag && grd.renameObject(objectId, newName);
		
		return flag;
	}

	@Override
	public boolean copyObject(int objectId,  int tarBucket) {
		
		boolean replaceMetadata = false;
		
		boolean flag = true;
		
		GsBucket gb = new GsBucket();
		
		GsObject go = new GsObject();
		
		gb = grd.getBucketbById(tarBucket);
		
		go = grd.getObjectbById(objectId);
		
		String srcBucket = go.getBucket_name();
		
		String desBucket = gb.getBucket_name();
		
		String srcObject = go.getObject_name();
		
		try {
			gsService.copyObject(srcBucket, srcObject, desBucket,
					new GSObject(srcObject), replaceMetadata);
			
			System.out.println("已成功将" + srcBucket + "的object" + srcObject
					+ "复制至" + desBucket + "！");
			
		} catch (ServiceException e) {
			
			e.printStackTrace();
			
			System.out.println(srcObject + "复制失败！");
			
			return false;
		}
		
		flag = flag && grd.copyObject(objectId, tarBucket);
		
		return flag;
	}
		
	
	@Override
	public boolean shutDownService() {
		
		try {
			gsService.shutdown();
			
			System.out.println("================================");
			
			System.out.println("            服务已成功关闭！  ");
			
			System.out.println("================================");
			
			
		} catch (ServiceException e) {
			
			e.printStackTrace();
			
			System.out.println("服务关闭失败！");
			
			return false;
		}
	
		return true;
	}

	@Override
	public GsBucket getBucketbById(int bucket_id) {
		// TODO Auto-generated method stub
		return grd.getBucketbById(bucket_id);
	}

	@Override
	public GsObject getObjectbById(int object_id) {
		// TODO Auto-generated method stub
		return grd.getObjectbById(object_id);
	}
	

}
