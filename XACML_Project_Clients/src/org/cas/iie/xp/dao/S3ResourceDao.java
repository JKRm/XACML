package org.cas.iie.xp.dao;

import java.util.List;

import org.cas.iie.xp.model.S3Bucket;
import org.cas.iie.xp.model.S3Object;



public interface S3ResourceDao {
	
	public int getBucketNum();
	
	public boolean addBucket(String bucketName);
	
	public int uploadObjects(String bucketName, String filePath);
	
	public boolean uploadText(String content, String fileName,
			String bucketName);
	
	public List<S3Bucket> getBucketsList();
	
	public List<S3Object> getObjectsList(String bucketName);
	
	public boolean deleteBucket(String bucketName);
	
	public String getObjectDetails(String bucketName, String objectName);
	
	public boolean getObjectDownloaded(String bucketName, String objectKey,
			String filePath);
	
	public boolean deleteObject(int objectId);
	
	public boolean moveObject(int ObjectId, int BucketId);
	
	public boolean renameObject(int objectId, String newName);
	
	public boolean copyObject(int ObjectId, int BucketId);
	
	public boolean CompleteObjectInfo(int objectId, int objectSize, String filePath);
	
	public S3Object getObjectbById(int object_id); 
	
	public S3Object getObjectByName(String name);
	
	public S3Bucket getBucketbById(int bucket_id); 
	
	public boolean isObjectExist(String oname, String obucket);
	
	
}
