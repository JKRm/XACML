package org.cas.iie.xp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class PolicyInfo implements java.io.Serializable{
	
	private int policy_id;
	
	private  String policy_type;
	
	private String resource_name;
	
	private String policy_file;
	
	private int group_id;

	@Id
	@GeneratedValue
	public int getPolicy_id() {
		return policy_id;
	}

	public void setPolicy_id(int policy_id) {
		this.policy_id = policy_id;
	}

	public String getPolicy_type() {
		return policy_type;
	}

	public void setPolicy_type(String policy_type) {
		this.policy_type = policy_type;
	}

	public String getResource_name() {
		return resource_name;
	}

	public void setResource_name(String resource_name) {
		this.resource_name = resource_name;
	}

	public String getPolicy_file() {
		return policy_file;
	}

	public void setPolicy_file(String policy_file) {
		this.policy_file = policy_file;
	}

	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}

	public int getGroup_id() {
		return group_id;
	}
	
	

}
