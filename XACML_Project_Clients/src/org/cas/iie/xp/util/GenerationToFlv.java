package org.cas.iie.xp.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class GenerationToFlv {
	private String OldPath;
	private String NewPath;

	public boolean process() {
		int type = checkContentType();
		boolean status = false;
		if (type == 0) {
			status = processFLV(OldPath);// 直接将文件转为flv文件
		} 
		return status;
	}

	private int checkContentType() {
		String type = OldPath.substring(OldPath.lastIndexOf(".") + 1,
				OldPath.length()).toLowerCase();
		// ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）
		if (type.equals("avi")) {
			return 0;
		} else if (type.equals("mpg")) {
			return 0;
		} else if (type.equals("wmv")) {
			return 0;
		} else if (type.equals("3gp")) {
			return 0;
		} else if (type.equals("mov")) {
			return 0;
		} else if (type.equals("mp4")) {
			return 0;
		} else if (type.equals("asf")) {
			return 0;
		} else if (type.equals("asx")) {
			return 0;
		}else if (type.equals("flv")) {
			return 0;
		}
		return 9;
	}

	private boolean checkfile(String OldPath) {
		File file = new File(OldPath);
		if (!file.isFile()) {
			return false;
		}
		return true;
	}

	// ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）
	private boolean processFLV(String oldfilepath) {
		String ffpath = System.getProperty("user.dir").replace("bin", "webapps")
							+"\\XACML_Project_Clients\\tools\\ffmpeg\"";
		String ffme = "\"" + ffpath;
		
		if (!checkfile(OldPath)) {
			return false;
		}

		List<String> commend = new java.util.ArrayList<String>();
		commend.add(ffme);
		commend.add("-i");
		commend.add(oldfilepath);
		commend.add("-y");
		commend.add("-ab");
		commend.add("320000");
		commend.add("-ar");
		commend.add("22050");
		commend.add("-b");
		commend.add("800000");
		commend.add("-s");
		commend.add("640*480");
		commend.add(NewPath);
		try {			
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(commend);
			Process process=builder.start();
			InputStream inErr = process.getErrorStream();
			InputStream inIns = process.getInputStream();
			BufferedReader brErr = new BufferedReader(new InputStreamReader(inErr));
			BufferedReader brIns = new BufferedReader(new InputStreamReader(inIns));
			// inErr读取输出信息开始
            String strsErr = "";
            String strErr = brErr.readLine();
            while (strErr != null) {
                strsErr = strsErr + strErr + "\n";
                strErr = brErr.readLine();
            }
            // inErr读取输出信息结束

            // inIns读取输出信息开始
            String strsIns = "";
            String strIns = brIns.readLine();
            while (strIns != null) {
                strsIns = strsIns + strIns + "\n";
                strIns = brErr.readLine();
            }
            process.waitFor();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public String getOldPath() {
		return OldPath;
	}

	public void setOldPath(String oldPath) {
		OldPath = oldPath;
	}

	public String getNewPath() {
		return NewPath;
	}

	public void setNewPath(String newPath) {
		NewPath = newPath;
	}

}
