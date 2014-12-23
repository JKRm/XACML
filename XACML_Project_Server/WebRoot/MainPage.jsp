<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>欢迎使用！</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="<%=basePath%>css/layout.css" rel="stylesheet" type="text/css" />
	<link href="<%=basePath%>css/styles.css" rel="stylesheet" type="text/css" />
	<link href="<%=basePath%>css/wysiwyg.css" rel="stylesheet" type="text/css" />

  </head>
  
  <frameset rows="70px,*" border="0" frameborder="0" framespacing= "0" scrolling="no" noresize>
	<frame src="Top_list.jsp">
	<frameset cols="226px,*" >
		<frame src="Left_list.jsp" border="0" frameborder="0" framespacing= "0" scrolling="no" noresize name="left">
		<frame src="Right_list.jsp" border="0" frameborder="0" framespacing="0"  noresize name="right">
	</frameset>
</frameset>
</html>
