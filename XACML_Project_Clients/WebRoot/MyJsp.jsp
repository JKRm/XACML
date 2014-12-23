<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'previewVideo.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="<%=basePath%>css/preview.css">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
	<div id="main">
		<div id="player">
			<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0" width="500" height="400">
		    <param name="movie" value="D:/apache-tomcat-6.0.32-windows-x86/apache-tomcat-6.0.32/webapps/XACML_Project_Clients/tools/Flvplayer.swf" />
		    <param name="quality" value="high" />
		    <param name="allowFullScreen" value="true" />
		    <param name="FlashVars" value="vcastr_file=D:/apache-tomcat-6.0.32-windows-x86/apache-tomcat-6.0.32/webapps/localResource/helloyou2/flvLocation/20130414092242.flv&LogoText=aesop&BufferTime=3" />
		    <embed src="D:/apache-tomcat-6.0.32-windows-x86/apache-tomcat-6.0.32/webapps/XACML_Project_Clients/tools/Flvplayer.swf" allowfullscreen="true" flashvars="vcastr_file=${flvUrl}&LogoText=aesop" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="500" height="375"></embed>
			</object>
		  </div>
		
	</div>
	</body>
</html>
