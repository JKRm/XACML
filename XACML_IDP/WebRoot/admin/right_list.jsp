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
<link href="<%=basePath %>admin/styles/wysiwyg.css" rel="stylesheet" type="text/css" />
<!-- Theme Start -->
<link href="<%=basePath %>admin/styles/styles.css" rel="stylesheet" type="text/css" />
<!-- Theme End -->
</head>
<body>
  <!-- Top Breadcrumb Start -->
    <div id="breadcrumb">
    	<ul>	
        	<li><img src="<%=basePath %>admin/img/icons/icon_breadcrumb.png" alt="Location" /></li>
        	<li><strong>Location:</strong></li>
            <li>欢迎</li>
            
        </ul>
    </div>
    <!-- Top Breadcrumb End -->
    <br><br><br><br><br><br><br><br>
    <h1> <font face="微软雅黑" size="20"><center>欢迎使用<br>访问控制平台用户管理系统</center></font></h1>
    <br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
    <center> Copyright &copy; 2013 All Rights Reserved By JKR</center>
</body>
</html>
