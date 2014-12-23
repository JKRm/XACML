package org.cas.iie.xp.service;


import java.util.List;

import org.cas.iie.xp.model.GsBucket;
import org.cas.iie.xp.model.GsObject;
public interface GSStorageService {
	
	public int getBucketNum();
	
	public boolean addBucket(String bucketName);
	
	public boolean uploadObjects(String bucketName, String filePath);
	
	public boolean uploadText(String content, String fileName,
			String bucketName);
	
	public List<GsBucket> getBucketsList();
	
	public List<GsObject> getObjectsList(String bucketName);
	
	public boolean deleteBucket(String bucketName);
	
	public String getObjectDetails(String bucketName, String objectName);
	
	public boolean getObjectDownloaded(String bucketName, String objectKey,
			String filePath);
	
	public boolean deleteObject(int objectId);
	
	public boolean moveObject(int objectId,  int tarBucket);
	
	public boolean renameObject(int objectId, String newName);
	
	public boolean copyObject(int objectId,  int tarBucket);
	
	public boolean shutDownService();
	
	public GsBucket getBucketbById(int bucket_id); 
	
	public GsObject getObjectbById(int object_id); 
	

}
