package org.cas.iie.xp.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.cas.iie.xp.dao.GSResourceDao;
import org.cas.iie.xp.model.GsBucket;
import org.cas.iie.xp.model.GsObject;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;


@Component("gsResourceDao")
public class GSResourceDaoImpl implements GSResourceDao {
	
	private HibernateTemplate hibernateTemplate;
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	
	@Resource(name="hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	
	@Override
	public int getBucketNum() {
		
		List<GsBucket> bucketList = hibernateTemplate.find(
				"from GsBucket gb");
		
		int bucketnum = bucketList.size();
		
		return bucketnum;
	}

	@Override
	public boolean addBucket(String bucketName) {
		
		GsBucket bucket = new GsBucket();
		
		bucket.setBucket_name(bucketName);
		
		try{
			
			List<GsBucket> bucketList = hibernateTemplate.find(
			"from GsBucket gb where gb.bucket_name=?",bucketName);
			
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
	public boolean uploadObjects(String bucketName, String filePath) {
		
		GsObject go = new GsObject();
		
		go.setBucket_name(bucketName);
		
		String objectName = filePath.substring(filePath.lastIndexOf("\\")+1);
		
		go.setObject_name(objectName);
		
		try{
			
			hibernateTemplate.save(go);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			System.out.println("上传object文件失败！");
			
			return false;
		}
		
		System.out.println("上传文件成功！" + objectName + "已传至" + 
				"bucket: " +bucketName);
		
		return true;
	}

	@Override
	public boolean uploadText(String content, String fileName, String bucketName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<GsBucket> getBucketsList() {
		
		List<GsBucket> bucketList = new ArrayList<GsBucket>();
		
		try{
			
			bucketList = 
				hibernateTemplate.find("from GsBucket gb");
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			System.out.println("获取bucket列表失败！");
			
		}
		
		return bucketList;
	}

	@Override
	public List<GsObject> getObjectsList(String bucketName) {
		
		List<GsObject> objectList = new ArrayList<GsObject>();
		
		try{
			
			objectList = 
				hibernateTemplate.find(
						"from GsObject go where go.bucket_name=?",bucketName);
			
		
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			System.out.println("获取bucket列表失败！");
			
		}
		
		
		return objectList;
	}

	@Override
	public boolean deleteBucket(String bucketName) {
		
		try{
			List<GsObject> tempList = hibernateTemplate.find(
						"from GsObject go where go.bucket_name=?",bucketName);
			
			if(tempList.size()!=0){
				
				for(int i=0; i<tempList.size(); i++){
				
					hibernateTemplate.delete(tempList.get(i));
				
				}
			}
			
			GsBucket gb = (GsBucket) hibernateTemplate.find(
					"from GsBucket gb where gb.bucket_name=?",bucketName).get(0);
			
			hibernateTemplate.delete(gb);
			
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
			
			GsObject object = 
				(GsObject) hibernateTemplate.find(
					"from GsObject go where go.object_id = ?" ,objectId).get(0);
			
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
	public boolean moveObject(int objectId,  int tarBucket) {
		
		GsBucket gb = new GsBucket();
		
		gb = this.getBucketbById(tarBucket);
		
		String desBucket = gb.getBucket_name();
		
		String srcObject = null;
		
		String srcBucket = null;
		
		try{
			
			GsObject object = 
				(GsObject) hibernateTemplate.find(
					"from GsObject go where go.object_id = ?",objectId).get(0);
			
			if(object!=null){
				
				srcObject = object.getObject_name();
				
				srcBucket = object.getBucket_name();
				
				object.setBucket_name(desBucket);
				
				object.setObject_name(srcObject);
				
				hibernateTemplate.update(object);
				
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			System.out.println(srcObject + "移动失败！");
			
			return false;
			
		}
		System.out.println("已成功将" + srcBucket + "的object " + srcObject
				+ "移至" + desBucket + "并重命名为" + srcObject + "！");
		
		return true;
	}

	@Override
	public boolean renameObject(int objectId, String newName) {
		
		String oriName = null;
		
		String bucketName = null;
		
		try{
			
			GsObject object = 
				(GsObject) hibernateTemplate.find(
					"from GsObject go where go.object_id = ?" ,objectId).get(0);
			
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
	public boolean copyObject(int objectId,  int tarBucket) {
		
		GsObject newObject = new GsObject();
		
		GsBucket gb = new GsBucket();
		
		gb = this.getBucketbById(tarBucket);
		
		String desBucket = gb.getBucket_name();
		
		String srcObject = null;
		
		String srcBucket = null;
		
		try{
			
			GsObject object = 
				(GsObject) hibernateTemplate.find(
					"from GsObject go where go.object_id = ?",objectId).get(0);
			
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
				+ "复制至" + desBucket + "！");
		
		return true;
	}

	@Override
	public boolean CompleteObjectInfo(String objectName, int objectSize, String filePath) {
		
		GsObject object = 
			(GsObject) hibernateTemplate.find(
				"from GsObject go where go.object_name = ?",objectName).get(0);
		
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
	public GsObject getObjectbById(int object_id) {
		
		GsObject go = (GsObject) hibernateTemplate.find(
				
			"from GsObject gb where gb.object_id = ?", object_id).get(0);
				
		return go;
	}


	@Override
	public GsObject getObjectByName(String name) {
		
		GsObject go = (GsObject) hibernateTemplate.find(
				
			"from GsObject gb where gb.object_name = ?", name).get(0);
					
		return go;
	}

	@Override
	public GsBucket getBucketbById(int bucket_id) {
		
		GsBucket bucket = (GsBucket) hibernateTemplate.find(
				"from GsBucket gb where gb.bucket_id=?",bucket_id).get(0);
				
		return bucket;
	}
	



}
