<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'Right_list.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="<%=basePath%>css/layout.css" rel="stylesheet" type="text/css" />
	<link href="<%=basePath%>css/styles.css" rel="stylesheet" type="text/css" />
	<link href="<%=basePath%>css/wysiwyg.css" rel="stylesheet" type="text/css" />

  </head>
  
  <body>
  <!-- Top Breadcrumb Start -->
    <div id="breadcrumb">
    	<ul>	
        	<li><img src="img/icons/icon_breadcrumb.png" alt="Location" /></li>
        	<li><strong>Location:</strong></li>
            <li>欢迎</li>
            
        </ul>
    </div>
    <!-- Top Breadcrumb End -->
    <br><br><br><br><br><br><br><br>
    <h1> <font face="微软雅黑" size="20"><center>欢迎使用<br>访问控制管理平台</center></font></h1>
    <br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
    <center> Copyright &copy; 2013 All Rights Reserved By JKR</center>
</body>
</html>
