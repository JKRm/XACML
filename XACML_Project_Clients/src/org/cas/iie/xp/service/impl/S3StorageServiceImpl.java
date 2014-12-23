package org.cas.iie.xp.service.impl;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;

import org.cas.iie.xp.dao.S3ResourceDao;
import org.cas.iie.xp.service.S3StorageService;
import org.cas.iie.xp.util.AccessUtils;
import org.jets3t.service.S3ServiceException;
import org.jets3t.service.ServiceException;
import org.jets3t.service.impl.rest.httpclient.RestS3Service;
import org.jets3t.service.model.S3Bucket;
import org.jets3t.service.model.S3Object;
import org.jets3t.service.security.AWSCredentials;
import org.springframework.stereotype.Component;

@Component("s3StorageService")
public class S3StorageServiceImpl implements S3StorageService{
	
	private S3ResourceDao srd;
	
	private RestS3Service s3Service;
	
	public S3StorageServiceImpl(){
		
		AWSCredentials awsCredentials;
		
		try {
			
			awsCredentials = AccessUtils.loadAWSCredentials();
			
			s3Service = new RestS3Service(awsCredentials);
			
			System.out.println("================================");
			
			System.out.println("        亚马逊S3平台连接成功！  ");
			
			System.out.println("================================");
			
			
		} catch (Exception e) {
			
			System.out.println("亚马逊s3连接失败！请检查证书有效性！");
			
			e.printStackTrace();
		}
		
	}
	
	@Resource(name="s3ResourceDao")
	public void setSrd(S3ResourceDao srd) {
		this.srd = srd;
	}

	public S3ResourceDao getSrd() {
		return srd;
	}

	@Override
	public int getBucketNum() {
		
		int num = srd.getBucketNum();

		System.out.println("Local bucket数量： " + num);

		return num;
		
	}

	@Override
	public boolean addBucket(String bucketName) {
			
			S3Bucket newBucket;
			
			try {
				
				newBucket = s3Service.createBucket(bucketName);
				
				System.out.println("S3 bucket创建成功, bucket名: " + newBucket.getName());
				
				if(srd.addBucket(bucketName)){
					
					return true;
				}
				
				else{
					return false;
				}

				
			} catch (S3ServiceException e) {
				
				e.printStackTrace();
				
				System.out.println("bucket创建失败！bucket名可能已存在。");
				
				return false;
			}
			
			
	}

	@Override
	public boolean uploadObjects(String bucketName, String filePath) {
		
		boolean flag = true;
		
		S3Object fileObject;
		
		S3Bucket bucket  = new S3Bucket();
		
		File file = null;
		
		long filesize = 0;
		
		int objectId = 0;
		
		try {
			
			bucket = s3Service.getBucket(bucketName);
			
			if(bucket==null){
				
				System.out.println("不存在该bucket！");
			}
			
			file = new File(filePath);
			
			fileObject = new S3Object(file);
			
			s3Service.putObject(bucket, fileObject);
			
			filesize = fileObject.getContentLength();
			
			System.out.println("上传文件成功！" + file.getName() + "已传至" + 
					"bucket: " + bucket.getName());
			
			objectId = srd.uploadObjects(bucketName, filePath);
			
			//this.renameObject(objectId, fileName);
			
			
		} catch (Exception e) {
			
			System.out.println("上传object文件失败！");
			
			e.printStackTrace();
			
			return false;
		}
		
		
		
		flag = flag && (objectId!=0);
		
		flag = flag && srd.CompleteObjectInfo(objectId, (int)filesize, null);
		
		return flag;
	}


	@Override
	public boolean uploadText(String content, String fileName, String bucketName) {
		// TODO Auto-generated method stub
		return srd.uploadText(content, fileName, bucketName);
	}

	@Override
	public List<org.cas.iie.xp.model.S3Bucket> getBucketsList() {
		// TODO Auto-generated method stub
		return srd.getBucketsList();
	}

	@Override
	public List<org.cas.iie.xp.model.S3Object> getObjectsList(String bucketName) {
		// TODO Auto-generated method stub
		return srd.getObjectsList(bucketName);
	}

	@Override
	public boolean deleteBucket(String bucketName) {
		
		boolean flag = true;
		
		try {
			
			S3Object[] objects = s3Service.listObjects(bucketName);
			
			if(objects.length!=0){
				
				for(int i=0; i<objects.length; i++){
					
					s3Service.deleteObject(bucketName, objects[i].getKey());
				}
				
			}
			
			s3Service.deleteBucket(bucketName);
			
			System.out.println("删除bucket：" + bucketName + "列表成功！");
			
		} catch (ServiceException e) {
			
			e.printStackTrace();
			
			System.out.println("删除bucket：" + bucketName + "列表失败！");
			
			return false;
		}
		
		flag = flag&&srd.deleteBucket(bucketName);
		
		return flag;
	}
	

	@Override
	public String getObjectDetails(String bucketName, String objectName) {
		// TODO Auto-generated method stub
		return srd.getObjectDetails(bucketName, objectName);
	}

	@Override
	public boolean getObjectDownloaded(int ObjectId,String filePath) {
		
		BufferedInputStream inBuff = null;
		
        BufferedOutputStream outBuff = null;
        
        org.cas.iie.xp.model.S3Object so = new org.cas.iie.xp.model.S3Object();
        
        so = srd.getObjectbById(ObjectId);
        
        String bucketName = so.getBucket_name();
        
        String objectKey = so.getObject_name();
        
        File targetFile = new File(filePath);
		
		 try {
			 
			S3Object objectComplete = s3Service.getObject(bucketName, objectKey);
			
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
		
		org.cas.iie.xp.model.S3Object so = new org.cas.iie.xp.model.S3Object();
		
		so = srd.getObjectbById(objectId);
		
		String bucketName = so.getBucket_name();
		
		String objectKey = so.getObject_name();
		
		boolean flag = true;
		
		try {
			s3Service.deleteObject(bucketName, objectKey);
			
			System.out.println("删除object " + objectKey + "成功！");
			
		} catch (ServiceException e) {
			
			e.printStackTrace();
			
			System.out.println("删除object " + objectKey + "失败！");
			
			return false;
		}
		
		flag = flag&&srd.deleteObject(objectId);
		
		return flag;
	}
		

	@Override
	public boolean moveObject(int ObjectId, int BucketId) {
		
		org.cas.iie.xp.model.S3Object so = new org.cas.iie.xp.model.S3Object();
		
		org.cas.iie.xp.model.S3Bucket sb = new org.cas.iie.xp.model.S3Bucket();
		
		so = srd.getObjectbById(ObjectId);
		
		sb = srd.getBucketbById(BucketId);
		
		String desBucket = sb.getBucket_name();
		
		String srcBucket = so.getBucket_name();
		
		String srcObject = so.getObject_name();
		
		boolean flag = true;
		
		try {
			s3Service.moveObject(srcBucket, srcObject, 
					desBucket, new S3Object(srcObject), false);
			
			System.out.println("已成功将" + srcBucket + "的object" + srcObject
					+ "移至" + desBucket + "！");
			
		} catch (ServiceException e) {
			
			e.printStackTrace();
			
			System.out.println(srcObject + "移动失败！");
			
			return false;
		}
		
		flag = flag&&srd.moveObject(ObjectId, BucketId);
		
		return flag;
	}

	@Override
	public boolean renameObject(int objectId,String newName) {
		
		boolean flag = true;
		
		org.cas.iie.xp.model.S3Object so = new org.cas.iie.xp.model.S3Object();
		
		so = srd.getObjectbById(objectId);
		
		String bucketName = so.getBucket_name();
		
		String oriName = so.getObject_name();

		try {
			
			s3Service.renameObject(bucketName, oriName, new S3Object(newName));
			
			System.out.println("已成功将" + bucketName + "的object" + 
					oriName + "重命名为" + newName + "！");
			
		} catch (ServiceException e) {
			
			e.printStackTrace();
			
			System.out.println(oriName + "重命名失败！");
			
			return false;
		}
		
		flag = flag&&srd.renameObject(objectId, newName);
		
		return flag;
	}

	@Override
	public boolean copyObject(int ObjectId, int BucketId) {
		
		boolean flag = true;
		
		org.cas.iie.xp.model.S3Object so = new org.cas.iie.xp.model.S3Object();
		
		org.cas.iie.xp.model.S3Bucket sb = new org.cas.iie.xp.model.S3Bucket();
		
		so = srd.getObjectbById(ObjectId);
		
		sb = srd.getBucketbById(BucketId);
		
		String desBucket = sb.getBucket_name();
		
		String srcBucket = so.getBucket_name();
		
		String srcObject = so.getObject_name();

		boolean replaceMetadata = false;
		
		try {
			s3Service.copyObject(srcBucket, srcObject, desBucket,
					new S3Object(srcObject), replaceMetadata);
			
			System.out.println("已成功将" + srcBucket + "的object" + srcObject
					+ "复制至" + desBucket + "！");
			
		} catch (ServiceException e) {
			
			e.printStackTrace();
			
			System.out.println(srcObject + "复制失败！");
			
			return false;
		}
		
		flag = flag&&srd.copyObject(ObjectId, BucketId);
		
		return flag;
	}
		

	@Override
	public boolean shutDownService() {

		try {
			s3Service.shutdown();
			
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
	public org.cas.iie.xp.model.S3Bucket getBucketbById(int bucket_id) {
		// TODO Auto-generated method stub
		return srd.getBucketbById(bucket_id);
	}

	@Override
	public org.cas.iie.xp.model.S3Object getObjectbById(int object_id) {
		// TODO Auto-generated method stub
		return srd.getObjectbById(object_id);
	}

	@Override
	public boolean isObjectExist(String oname, String obucket) {
		// TODO Auto-generated method stub
		return srd.isObjectExist(oname, obucket);
	}
	

}
