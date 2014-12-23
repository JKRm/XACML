package org.cas.iie.xp.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.cas.iie.xp.dao.LocalResourceDao;
import org.cas.iie.xp.model.LocalBucket;
import org.cas.iie.xp.model.LocalObject;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

@Component("localResourceDao")
public class LocalResourceDaoImpl implements LocalResourceDao {
	
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
		
		List<LocalBucket> bucketList = hibernateTemplate.find(
				"from LocalBucket lb");
		
		int bucketnum = bucketList.size();
		
		return bucketnum;
	}

	@Override
	public boolean addBucket(String bucketName) {
		
		LocalBucket bucket = new LocalBucket();
		
		bucket.setBucket_name(bucketName);
		
		try{
			
			List<LocalBucket> bucketList = hibernateTemplate.find(
			"from LocalBucket lb where lb.bucket_name=?",bucketName);
			
			if(bucketList.size()==0){
				
			hibernateTemplate.save(bucket);
			
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			
			System.out.println("bucket创建失败！");
			
			return false;
		}
		
		System.out.println("Local bucket创建成功, bucket名: " + bucketName);
		
		return true;
	}

	@Override
	public int uploadObjects(String bucketName, String filePath) {
		
		LocalObject lo = new LocalObject();
		
		lo.setBucket_name(bucketName);
		
		String objectName = filePath.substring(filePath.lastIndexOf("\\")+1);
		
		lo.setObject_name(objectName);
		
		int ObjectId = 0;
		
		try{
			
			hibernateTemplate.save(lo);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			System.out.println("上传object文件失败！");
			
			return ObjectId;
		}
		
		System.out.println("上传文件成功！" + objectName + "已传至" + 
				"bucket: " +bucketName);
		
		ObjectId = lo.getObject_id();
		
		return ObjectId;
	}

	@Override
	public boolean uploadText(String content, String fileName, String bucketName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<LocalBucket> getBucketsList() {
		
		List<LocalBucket> bucketList = new ArrayList<LocalBucket>();
		
		try{
			
			bucketList = 
				hibernateTemplate.find("from LocalBucket lb");
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			System.out.println("获取bucket列表失败！");
			
		}
		
		
		return bucketList;
	}

	@Override
	public List<LocalObject> getObjectsList(String bucketName) {
		
		List<LocalObject> tempList = new ArrayList<LocalObject>();
		
		try{
			
			tempList = 
				hibernateTemplate.find(
						"from LocalObject lo where lo.bucket_name=?",bucketName);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			System.out.println("获取bucket列表失败！");
			
		}
		
		
		return tempList;
	}

	@Override
	public boolean deleteBucket(int BucketId) {
		
		String bucketName = this.getBucketbById(BucketId).getBucket_name();
		
		try{
			List<LocalObject> tempList = hibernateTemplate.find(
						"from LocalObject lo where lo.bucket_name=?",bucketName);
			
			if(tempList.size()!=0){
				
				for(int i=0; i<tempList.size(); i++){
				
					hibernateTemplate.delete(tempList.get(i));
				
				}
			}
			
			LocalBucket lb = (LocalBucket) hibernateTemplate.find(
					"from LocalBucket lb where lb.bucket_id=?",BucketId).get(0);
			
			hibernateTemplate.delete(lb);
			
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
			
			LocalObject object = 
				(LocalObject) hibernateTemplate.find(
					"from LocalObject lo where lo.object_id = ?",objectId).get(0);
			
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
	public boolean moveObject(int srcObjectId,int desBucketId) {
		
		String srcObject = null;
		
		String srcBucket = null;
		
		String desBucket = null;
		
		try{
			
			LocalObject object = 
				(LocalObject) hibernateTemplate.find(
					"from LocalObject lo where lo.object_id = ?" ,srcObjectId).get(0);
			
			LocalBucket bucket = this.getBucketbById(desBucketId);
			
			desBucket = bucket.getBucket_name();
			
			
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
				+ "移至" + desBucket +  "！");
		
		return true;
	}

	@Override
	public boolean renameObject(int objectId,String newName) {
		
		String oriName = null;
		
		String bucketName = null;
		
		try{
			
			LocalObject object = 
				(LocalObject) hibernateTemplate.find(
					"from LocalObject lo where lo.object_id = ?" ,objectId).get(0);
			
			if(object!=null){
				
				bucketName = object.getBucket_name();
				
				oriName = object.getObject_name();
				
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
	public boolean copyObject(int srcObjectId, int desBucketId) {
		
		String srcObject = null;
		
		String srcBucket = null;
		
		String desBucket = null;
		
		LocalObject newObject = new LocalObject();
		
		LocalBucket bucket = new LocalBucket();
		
		bucket = this.getBucketbById(desBucketId);
		
		desBucket = bucket.getBucket_name();
		
		try{
			
			LocalObject object = 
				(LocalObject) hibernateTemplate.find(
					"from LocalObject lo where lo.object_id = ?",srcObjectId).get(0);
			
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
	public boolean CompleteObjectInfo(int ObjectId, int objectSize, String objectFile) {
		
		LocalObject object = 
			(LocalObject) hibernateTemplate.find(
				"from LocalObject lo where lo.object_id = ?",ObjectId).get(0);
		
		object.setObject_file(objectFile);
		
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
	public LocalObject getObjectbById(int object_id) {
		
		LocalObject lo = (LocalObject) hibernateTemplate.find(
				
		"from LocalObject lb where lb.object_id = ?", object_id).get(0);
		
		return lo;
	}

	@Override
	public LocalObject getObjectByName(String name) {
		
		LocalObject lo = (LocalObject) hibernateTemplate.find(
				
				"from LocalObject lb where lb.object_name = ?", name).get(0);
				
				return lo;
			}

	@Override
	public LocalBucket getBucketbById(int bucket_id) {
		
		LocalBucket bucket = (LocalBucket) hibernateTemplate.find(
		"from LocalBucket lb where lb.bucket_id=?",bucket_id).get(0);
		
		return bucket;
	}

	@Override
	public boolean isObjectExist(String oname, String obucket) {
		List<LocalObject> tempList = hibernateTemplate.find(
				"from LocalObject lo where lo.bucket_name=? and lo.object_name=?",obucket,oname);
		if(tempList.size()==0){
			
			return true;
		}
		else{
			return false;
		}
		
	}

	@Override
	public List<LocalObject> getListByBucketName(String bucketName) {
		List<LocalObject> tempList = hibernateTemplate.find(
				"from LocalObject lo where lo.bucket_name=?",bucketName);
		return tempList;
	}


}
