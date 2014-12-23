package org.cas.iie.xp.maptype;

import java.util.ArrayList;
import java.util.List;


public class StringLong {
	
	public static class Entry{
		
		private String key;
		
		private Long value;
		
		public Entry(){
			
			
		}

		public Entry(String key, Long value) {
			
			this.key = key;
			
			this.value = value;
		}

		public void setValue(Long value) {
			this.value = value;
		}

		public Long getValue() {
			return value;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getKey() {
			return key;
		}
		
	}
	
	private List<Entry> entries = new ArrayList<Entry>();

	public void setEntries(List<Entry> entries) {
		this.entries = entries;
	}

	public List<Entry> getEntries() {
		return entries;
	}
	
	

}
