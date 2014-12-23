package org.cas.iie.xp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class S3Object {
	
	private int object_id;
	
	private String bucket_name;
	
	private String object_name;
	
	private String object_file;
	
	private int object_size;

	@Id
	@GeneratedValue
	public int getObject_id() {
		return object_id;
	}

	public void setObject_id(int object_id) {
		this.object_id = object_id;
	}

	
	public void setBucket_name(String bucket_name) {
		this.bucket_name = bucket_name;
	}

	public String getBucket_name() {
		return bucket_name;
	}

	public String getObject_name() {
		return object_name;
	}

	public void setObject_name(String object_name) {
		this.object_name = object_name;
	}

	public String getObject_file() {
		return object_file;
	}

	public void setObject_file(String object_file) {
		this.object_file = object_file;
	}

	public void setObject_size(int object_size) {
		this.object_size = object_size;
	}

	public int getObject_size() {
		return object_size;
	}
	
	

}
