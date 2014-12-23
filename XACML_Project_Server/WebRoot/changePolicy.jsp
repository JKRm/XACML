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
	<script src="<%=basePath%>js/changepolicy.js" type="text/javascript"></script>
	
</head>

<body>
 	<!-- 右侧主页面上部导航条开始 -->
    <div id="breadcrumb">
    	<ul>	
        	<li><img src="../img/icons/icon_breadcrumb.png" alt="Location" /></li>
        	<li><strong>Location:</strong></li>
            <li><a href="../Right_list.jsp" title="返回首页">欢迎</a></li>
            <li>/</li>
	        <li class="current">添加策略</li>
        </ul>
    </div>
    <!-- 右侧主页面上部导航条结束 --><br/> <br/> <br/> <br/>
    <div style="margin:0 auto; width:400px; height: 100px">
    <center><h2>添加策略</h2><br/><br/></center>
    <center><select id="groupId" name="groupId">
    <%List<GroupInfo> giList = (List<GroupInfo>) request.getAttribute("groupList");
  			for (int i=0; i<giList.size(); ++i) { 
  				GroupInfo gi = giList.get(i);
  			%>
  			<option value="<%=gi.getGroup_id() %>"><%=gi.getGroup_name() %></option>
  			<%} %>
    </select>
    <select id="resName" name="resName">
    	<option value="http://AmazonS3.com">Amzaon S3</option>
    	<option value="http://local.com">Local Storage</option>
    </select>
    <select id="paction" name="paction">
    	<option value="delete">delete</option>
    	<option value="read">read</option>
    	<option value="list">list</option>
    	<option value="download">download</option>
    	<option value="upload">upload</option>
    	<option value="rename">rename</option>
    </select></center>
    </div><br/>
    <div style="margin:0 auto; width:400px; height: 100px" ><center><a href="javascript:void(0)" onclick="addl();return false">添加</a></center></div>
</body>
</html>