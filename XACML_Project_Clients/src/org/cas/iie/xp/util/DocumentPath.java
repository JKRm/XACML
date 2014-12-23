package org.cas.iie.xp.util;

import java.util.HashMap;
import java.util.Map;

public class DocumentPath {
	
	public Map<String,String> getPath(){
		
		Map<String,String> docPath = new HashMap<String,String>();
		
		String nowpath;
		
		String tempdir;
		   
		nowpath=System.getProperty("user.dir");
		
		tempdir=nowpath.replace("bin", "webapps") + "\\documents";
		
		docPath.put("policy", tempdir + "\\policy\\");
		
		docPath.put("request", tempdir + "\\request\\");
		   
		return docPath;

	}
	
}
