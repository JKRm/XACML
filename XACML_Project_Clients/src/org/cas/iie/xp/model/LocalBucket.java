package org.cas.iie.xp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class LocalBucket {
	
	private int bucket_id;
	
	private String bucket_name;
	
	@Id
	@GeneratedValue
	public int getBucket_id() {
		return bucket_id;
	}

	public void setBucket_id(int bucket_id) {
		this.bucket_id = bucket_id;
	}

	public String getBucket_name() {
		return bucket_name;
	}

	public void setBucket_name(String bucket_name) {
		this.bucket_name = bucket_name;
	}
	
}
