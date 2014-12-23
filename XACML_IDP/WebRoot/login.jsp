<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
 <head>
	<title>欢迎使用权限管理系统！</title>
	<!-- 加载CSS -->
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="<%=basePath %>admin/styles/layout.css" rel="stylesheet" type="text/css" />
	<link href="<%=basePath %>admin/styles/login.css" rel="stylesheet" type="text/css" />
	<link href="<%=basePath %>admin/styles/styles.css" rel="stylesheet" type="text/css" />
	 <script src="<%=basePath %>admin/scripts/jquery-1.3.2.min.js"></script>
    <script src="<%=basePath %>admin/scripts/idp.js"></script>
    <script language = "javascript">
     $(document).ready(function(){
        var args = getArgs();
        login.SAMLrequest.value = args['SAMLrequest'];
    });
    </script>

</head>

<body>	

<div id="logincontainer">
    <div id="loginbox">
        <div id="loginheader">
            <img src="<%=basePath %>admin/img/cp_logo_login.png" alt="Control Panel Login" />
        </div>
        <div id="innerlogin">
           	<form method = "post" action = "<%=basePath %>admin/loginAction.action" id = "loginform" name="login" onsubmit = "return checkform();">
           		<!-- 表单提交给logincheck.php -->
                <p>用户名:</p>
                <input type="text" class="logininput" name="username" >
                <p>密码:</p>
                <input type="password" class="logininput" name="password">
                <input type="hidden" class="logininput" name="SAMLrequest">
            </form>
            <input type="submit" class="loginbtn" value="提交" onclick = "checklogin();"><br>
            <p><a href="" title="Forgoteen Password?">Forgotten Password?</a></p>
        </div>
    </div>
</div>
<div id="welcome" style="display: none">
    <p id = "welcomeword"></p>
</div>
<div id="returndiv" style="display: none">
    <form name="acsForm" id="acsForm"  method="post" >
        <textarea rows=10 cols=80 name="SAMLResponse"></textarea>
    </form>
</div>


</body>
</html>