<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-cn">
<head>
  <meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>MP3播放</title>
<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="<%=basePath%>css/preview.css">
	<link href="<%=basePath%>css/layout.css" rel="stylesheet" type="text/css" />
	<link href="<%=basePath%>css/styles.css" rel="stylesheet" type="text/css" />

</head>
<body>
<% String str = java.net.URLDecoder.decode(request.getParameter("file"), "UTF-8");
	String nstr = java.net.URLDecoder.decode(str, "UTF-8");
	String buv = java.net.URLDecoder.decode(request.getParameter("bucket"), "UTF-8");
	String nbuv = java.net.URLDecoder.decode(buv, "UTF-8");
%>
<p><%=nstr%></p>
<div id="main"><div id="player"  ></div></div>
<script type="text/javascript" src="<%=basePath%>js/swfobject.js"></script>
<script type="text/javascript">
	var s5 = new SWFObject("FlvPlayer201002.swf","playlist","500","375","7");
	s5.addParam("allowfullscreen","true");
	s5.addVariable("autostart","true");
	//s5.addVariable("image","flashM-cebbank.jpg");
	s5.addVariable("file","../../localResource/<%=nbuv%>/<%=nstr%>");
	s5.addVariable("width","500");
	s5.addVariable("height","375");
	s5.write("player");
</script>
</body>
</html>