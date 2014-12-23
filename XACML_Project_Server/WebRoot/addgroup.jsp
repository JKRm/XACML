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
	
</head>

<body>
 	<!-- 右侧主页面上部导航条开始 -->
    <div id="breadcrumb">
    	<ul>	
        	<li><img src="../img/icons/icon_breadcrumb.png" alt="Location" /></li>
        	<li><strong>Location:</strong></li>
            <li><a href="../Right_list.jsp" title="返回首页">欢迎</a></li>
            <li>/</li>
	        <li class="current">添加用户组</li>
        </ul>
    </div>
    <!-- 右侧主页面上部导航条结束 --> <br/> <br/> <br/> <br/>
    <div style="margin:0 auto; width:400px; height: 100px">
    <center><h2>添加用户组</h2><br/></center>
    <form action="<%=basePath%>group/addAction.action" method="post">
    	<center><p><b>用户组名：</b><input type="text"  name="groupname"/></p></center><br/>
    	<b>策略分配：</b><br/>
    	<center>Amazon S3:</center><br/>
    	<p>
    		<input type="checkbox" name="amazon" value="delete"/>delete&nbsp;&nbsp;
    		<input type="checkbox" name="amazon" value="read"/>read&nbsp;&nbsp;
    		<input type="checkbox" name="amazon" value="list"/>list&nbsp;&nbsp;
    		<input type="checkbox" name="amazon" value="download"/>download&nbsp;&nbsp;
    		<input type="checkbox" name="amazon" value="upload"/>upload&nbsp;&nbsp;
    		<input type="checkbox" name="amazon" value="rename"/>rename&nbsp;&nbsp;
    	</p><br/>
    	<center>local storage:</center><br/>
    	<p>
    		<input type="checkbox" name="local" value="delete"/>delete&nbsp;&nbsp;
    		<input type="checkbox" name="local" value="read"/>read&nbsp;&nbsp;
    		<input type="checkbox" name="local" value="list"/>list&nbsp;&nbsp;
    		<input type="checkbox" name="local" value="download"/>download&nbsp;&nbsp;
    		<input type="checkbox" name="local" value="upload"/>upload&nbsp;&nbsp;
    		<input type="checkbox" name="local" value="rename"/>rename&nbsp;&nbsp;
    	</p><br/>
    	<center><input type="submit" value="添加"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    	<input type="reset" value="清空"/></center>
    </form>
    	
    </div>
</body>
</html>