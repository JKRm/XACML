package org.cas.iie.xp.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Admin {
	
	private String username;
	
	private String password;

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	@Id
	public String getUsername() {
		return username;
	}
	
	

}
