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
			
			System.out.println("        ����ѷS3ƽ̨���ӳɹ���  ");
			
			System.out.println("================================");
			
			
		} catch (Exception e) {
			
			System.out.println("����ѷs3����ʧ�ܣ�����֤����Ч�ԣ�");
			
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

		System.out.println("Local bucket������ " + num);

		return num;
		
	}

	@Override
	public boolean addBucket(String bucketName) {
			
			S3Bucket newBucket;
			
			try {
				
				newBucket = s3Service.createBucket(bucketName);
				
				System.out.println("S3 bucket�����ɹ�, bucket��: " + newBucket.getName());
				
				if(srd.addBucket(bucketName)){
					
					return true;
				}
				
				else{
					return false;
				}

				
			} catch (S3ServiceException e) {
				
				e.printStackTrace();
				
				System.out.println("bucket����ʧ�ܣ�bucket�������Ѵ��ڡ�");
				
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
				
				System.out.println("�����ڸ�bucket��");
			}
			
			file = new File(filePath);
			
			fileObject = new S3Object(file);
			
			s3Service.putObject(bucket, fileObject);
			
			filesize = fileObject.getContentLength();
			
			System.out.println("�ϴ��ļ��ɹ���" + file.getName() + "�Ѵ���" + 
					"bucket: " + bucket.getName());
			
			objectId = srd.uploadObjects(bucketName, filePath);
			
			//this.renameObject(objectId, fileName);
			
			
		} catch (Exception e) {
			
			System.out.println("�ϴ�object�ļ�ʧ�ܣ�");
			
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
			
			System.out.println("ɾ��bucket��" + bucketName + "�б�ɹ���");
			
		} catch (ServiceException e) {
			
			e.printStackTrace();
			
			System.out.println("ɾ��bucket��" + bucketName + "�б�ʧ�ܣ�");
			
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
			
			System.out.println("�ļ�" + objectKey + "���سɹ���");
			
			System.out.println("��������" + targetFile.getAbsolutePath());
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			System.out.println("�ļ�����ʧ�ܣ�����bucket��object�Ƿ���ڣ�");
			
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
			
			System.out.println("ɾ��object " + objectKey + "�ɹ���");
			
		} catch (ServiceException e) {
			
			e.printStackTrace();
			
			System.out.println("ɾ��object " + objectKey + "ʧ�ܣ�");
			
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
			
			System.out.println("�ѳɹ���" + srcBucket + "��object" + srcObject
					+ "����" + desBucket + "��");
			
		} catch (ServiceException e) {
			
			e.printStackTrace();
			
			System.out.println(srcObject + "�ƶ�ʧ�ܣ�");
			
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
			
			System.out.println("�ѳɹ���" + bucketName + "��object" + 
					oriName + "������Ϊ" + newName + "��");
			
		} catch (ServiceException e) {
			
			e.printStackTrace();
			
			System.out.println(oriName + "������ʧ�ܣ�");
			
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
			
			System.out.println("�ѳɹ���" + srcBucket + "��object" + srcObject
					+ "������" + desBucket + "��");
			
		} catch (ServiceException e) {
			
			e.printStackTrace();
			
			System.out.println(srcObject + "����ʧ�ܣ�");
			
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
			
			System.out.println("            �����ѳɹ��رգ�  ");
			
			System.out.println("================================");
			
			
		} catch (ServiceException e) {
			
			e.printStackTrace();
			
			System.out.println("����ر�ʧ�ܣ�");
			
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
