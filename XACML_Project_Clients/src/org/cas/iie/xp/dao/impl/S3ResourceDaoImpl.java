package org.cas.iie.xp.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.cas.iie.xp.dao.S3ResourceDao;
import org.cas.iie.xp.model.LocalBucket;
import org.cas.iie.xp.model.LocalObject;
import org.cas.iie.xp.model.S3Bucket;
import org.cas.iie.xp.model.S3Object;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

@Component("s3ResourceDao")
public class S3ResourceDaoImpl implements S3ResourceDao {
	
	private HibernateTemplate hibernateTemplate;
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	
	@Resource(name="hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
/*
 * @Override
	public String doTest() {
		// TODO Auto-generated method stub
		List<Test> list = hibernateTemplate.find(
				"from Test t where t.id=?", "1");
		return list.get(0).getName();
		
	}

 * 
 */
	
	@Override
	public int getBucketNum() {
		
		List<S3Bucket> bucketList = hibernateTemplate.find(
				"from S3Bucket sb");
		
		int bucketnum = bucketList.size();
		
		return bucketnum;
	}

	@Override
	public boolean addBucket(String bucketName) {
		
		S3Bucket bucket = new S3Bucket();
		
		bucket.setBucket_name(bucketName);
		
		try{
			
			List<S3Bucket> bucketList = hibernateTemplate.find(
			"from S3Bucket sb where sb.bucket_name=?",bucketName);
			
			if(bucketList.size()==0){
				
			hibernateTemplate.save(bucket);
			
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			
			System.out.println("bucket创建失败！");
			
			return false;
		}
		
		System.out.println("S3 bucket创建成功, bucket名: " + bucketName);
		
		return true;
	}

	@Override
	public int uploadObjects(String bucketName, String filePath) {
		
		S3Object so = new S3Object();
		
		so.setBucket_name(bucketName);
		
		String objectName = filePath.substring(filePath.lastIndexOf("\\")+1);
		
		so.setObject_name(objectName);
		
		try{
			
			hibernateTemplate.save(so);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			System.out.println("上传object文件失败！");
			
			return 0;
		}
		
		System.out.println("上传文件成功！" + objectName + "已传至" + 
				"bucket: " +bucketName);
		
		return so.getObject_id();
	}

	@Override
	public boolean uploadText(String content, String fileName, String bucketName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<S3Bucket> getBucketsList() {
		
		List<S3Bucket> bucketList = new ArrayList<S3Bucket>();
		
		try{
			
			bucketList = 
				hibernateTemplate.find("from S3Bucket sb");
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			System.out.println("获取bucket列表失败！");
			
		}
		
		
		return bucketList;
	}

	@Override
	public List<S3Object> getObjectsList(String bucketName) {
		
		List<S3Object> tempList = new ArrayList<S3Object>();
		
		try{
			
			tempList = 
				hibernateTemplate.find(
						"from S3Object so where so.bucket_name=?",bucketName);
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			System.out.println("获取bucket列表失败！");
			
		}
		
		
		return tempList;
	}

	@Override
	public boolean deleteBucket(String bucketName) {
		
		try{
			List<S3Object> tempList = hibernateTemplate.find(
						"from S3Object so where so.bucket_name=?",bucketName);
			
			if(tempList.size()!=0){
				
				for(int i=0; i<tempList.size(); i++){
				
					hibernateTemplate.delete(tempList.get(i));
				
				}
			}
			
			S3Bucket sb = (S3Bucket) hibernateTemplate.find(
					"from S3Bucket sb where sb.bucket_name=?",bucketName).get(0);
			
			hibernateTemplate.delete(sb);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			System.out.println("删除bucket：" + bucketName + "列表失败！");
			
			return false;
			
		}
		
		System.out.println("删除bucket：" + bucketName + "列表成功！");
		
		return true;
	}

	@Override
	public boolean getObjectDownloaded(String bucketName, String objectKey,
			String filePath) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteObject(int objectId) {
		
		String objectKey = null;
		
		try{
			
			S3Object object = 
				(S3Object) hibernateTemplate.find(
					"from S3Object so where so.object_id = ?" ,objectId).get(0);
			
			if(object!=null){
				
				objectKey = object.getObject_name();
				
				hibernateTemplate.delete(object);
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			System.out.println("删除object " + objectKey +"失败！");
			
			return false;
			
		}
		System.out.println("删除object " + objectKey + "成功！");
		
		return true;
	}

	@Override
	public boolean moveObject(int ObjectId, int BucketId) {
		
		String  srcObject = null;
		
		String srcBucket = null;
		
		S3Bucket sb = this.getBucketbById(BucketId);
		
		String desBucket = sb.getBucket_name();
		
		try{
			
			S3Object object = 
				(S3Object) hibernateTemplate.find(
					"from S3Object so where so.object_id = ?" ,ObjectId).get(0);
			
			if(object!=null){
				
				srcObject = object.getObject_name();
				
				srcBucket = object.getBucket_name();
				
				object.setBucket_name(desBucket);
				
				hibernateTemplate.update(object);
				
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			System.out.println(srcObject + "移动失败！");
			
			return false;
			
		}
		System.out.println("已成功将" + srcBucket + "的object " + srcObject
				+ "移至" + desBucket  + "！");
		
		return true;
	}

	@Override
	public boolean renameObject(int objectId,String newName) {
		
		String oriName = null;
		
		String bucketName = null;
		
		try{
			
			S3Object object = 
				(S3Object) hibernateTemplate.find(
					"from S3Object so where so.object_id = ?" ,objectId).get(0);
			
			if(object!=null){
				
				oriName = object.getObject_name();
				
				bucketName = object.getBucket_name();
				
				object.setObject_name(newName);
				
				hibernateTemplate.update(object);
				
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			System.out.println(oriName + "重命名失败！");
			
			return false;
			
		}
		
		System.out.println("已成功将" + bucketName + "的object " + 
				oriName + "重命名为" + newName + "！");
		
		return true;
	}

	@Override
	public boolean copyObject(int ObjectId, int BucketId) {
		
		S3Object newObject = new S3Object();
		
		S3Bucket sb = this.getBucketbById(BucketId);
		
		String desBucket = sb.getBucket_name();
		
		String srcObject = null;
		
		String srcBucket = null;
		
		try{
			
			S3Object object = 
				(S3Object) hibernateTemplate.find(
					"from S3Object so where so.object_id = ?",ObjectId).get(0);
			
			if(object!=null){
				
				srcObject = object.getObject_name();
				
				srcBucket = object.getBucket_name();
				
				newObject.setBucket_name(desBucket);
				
				newObject.setObject_name(srcObject);
				
				newObject.setObject_file(object.getObject_file());
				
				newObject.setObject_size(object.getObject_size());
				
				hibernateTemplate.save(newObject);
				
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			System.out.println(srcObject + "复制失败！该文件可能已不存在！");
			
			return false;
			
		}
		System.out.println("已成功将" + srcBucket + "的object " + srcObject
				+ "复制至" + desBucket  + "！");
		
		return true;
	}

	@Override
	public boolean CompleteObjectInfo(int objectId, int objectSize, String filePath) {
		
		S3Object object = 
			(S3Object) hibernateTemplate.find(
				"from S3Object so where so.object_id = ?",objectId).get(0);
		
		object.setObject_file(filePath);
		
		object.setObject_size(objectSize);
		
		try {
			
			hibernateTemplate.update(object);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			return false;
			
		}
		
		return true;
		
	}

	@Override
	public String getObjectDetails(String bucketName, String objectName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public S3Object getObjectbById(int object_id) {
		
		S3Object so = (S3Object) hibernateTemplate.find(
				
			"from S3Object sb where sb.object_id = ?", object_id).get(0);
				
		return so;
	}


	@Override
	public S3Object getObjectByName(String name) {
		
		S3Object so = (S3Object) hibernateTemplate.find(
				
			"from S3Object sb where sb.object_name = ?", name).get(0);
					
		return so;
	}

	@Override
	public S3Bucket getBucketbById(int bucket_id) {
		S3Bucket bucket = (S3Bucket) hibernateTemplate.find(
				"from S3Bucket sb where sb.bucket_id=?",bucket_id).get(0);
				
				return bucket;
	}

	@Override
	public boolean isObjectExist(String oname, String obucket) {
		List<S3Object> tempList = hibernateTemplate.find(
				"from S3Object so where so.bucket_name=? and so.object_name=?",obucket,oname);
		if(tempList.size()==0){
			
			return true;
		}
		
		else{
			
			return false;
		}
		
	}

	

}
