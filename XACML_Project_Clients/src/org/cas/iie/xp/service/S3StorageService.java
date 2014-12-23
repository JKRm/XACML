package org.cas.iie.xp.service;


import java.util.List;

import org.cas.iie.xp.model.S3Bucket;
import org.cas.iie.xp.model.S3Object;

public interface S3StorageService {
	
	public int getBucketNum();
	
	public boolean addBucket(String bucketName);
	
	public boolean uploadObjects(String bucketName, String filePath);
	
	public boolean uploadText(String content, String fileName,
			String bucketName);
	
	public List<S3Bucket> getBucketsList();
	
	List<S3Object> getObjectsList(String bucketName);
	
	public boolean deleteBucket(String bucketName);
	
	public String getObjectDetails(String bucketName, String objectName);
	
	public boolean getObjectDownloaded(int ObjectId,String filePath);
	
	public boolean deleteObject(int objectId);
	
	public boolean moveObject(int ObjectId, int BucketId);
	
	public boolean renameObject(int objectId,String newName);
	
	public boolean copyObject(int ObjectId, int BucketId);
	
	public boolean shutDownService();
	
	public S3Bucket getBucketbById(int bucket_id);
	
	public S3Object getObjectbById(int object_id); 
	
	public boolean isObjectExist(String oname, String obucket);
}
