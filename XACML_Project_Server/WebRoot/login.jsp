<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="java.util.*,
	org.cas.iie.xp.model.*
	"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String filePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="<%=basePath%>css/layout.css" rel="stylesheet" type="text/css" />
	<link href="<%=basePath%>css/styles.css" rel="stylesheet" type="text/css" />
	<script src="<%=basePath%>js/jquery-1.3.2.min.js" type="text/javascript"></script>
	<script src="<%=basePath%>js/mygroup.js" type="text/javascript"></script>
	<link href="<%=basePath%>css/login.css" rel="stylesheet" type="text/css" />
</head>

<body>
 	<div id="logincontainer">
    <div id="loginbox">
        <div id="loginheader">
            <img src="<%=basePath%>img/cp_logo_login.png" alt="用户组及策略管理平台" />
        </div>
        <div id="innerlogin">
           	<form method = "post" action = "<%=basePath%>group/isAdmin.action" id = "loginform" name="login">
                <p>用户名:</p>
                <input type="text" class="logininput" name="username" />
                <p>密码:</p>
                <input type="password" class="logininput" name="password"/>
                <input type="submit" class="loginbtn" value="提交"/><br/>
            </form>
            <p><a href="" title="Forgoteen Password?">忘记密码?</a></p>
        </div>
    </div>
</div>
</body>
</html>