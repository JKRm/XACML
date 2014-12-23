package org.cas.iie.xp.service;

import java.util.List;

import org.cas.iie.xp.model.LocalBucket;
import org.cas.iie.xp.model.LocalObject;
public interface LocalStorageService {
	
	public int getBucketNum();
	
	public boolean addBucket(String bucketName);
	
	public boolean uploadObjects(String bucketName, String filePath);
	
	public boolean uploadText(String content, String fileName,
			String bucketName);
	
	public List<LocalBucket> getBucketsList();
	
	List<LocalObject> getObjectsList(String bucketName);
	
	public boolean deleteBucket(int BucketId);
	
	public boolean getObjectDownloaded(String bucketName, String objectKey,
			String filePath);
	
	public boolean deleteObject(int objectId);
	
	public boolean moveObject(int srcObjectId, int desBucketId);
	
	public boolean renameObject(int objectId, String newName);
	
	public boolean copyObject(int srcObjectId, int desBucketId);
	
	public LocalObject getObjectbById(int object_id); 
	
	public LocalObject getObjectByName(String name);
	
	public LocalBucket getBucketbById(int bucket_id); 
	
	public boolean uploadinfo(String bucketName, String filePath, int Size, String objectFile);
	
	public boolean isObjectExist(String oname, String obucket);
	
	public List<LocalObject> getListByBucketName(String bucketName);

}
