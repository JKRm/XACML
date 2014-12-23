<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="<%=basePath %>admin/styles/layout.css" rel="stylesheet" type="text/css" />
</head>
<body id="homepage">
	<div id="header">
    	<a href="" title=""><img src="<%=basePath %>admin/img/cp_logo.png" alt="Control Panel" class="logo" /></a>
    </div>
</body>
</html>
