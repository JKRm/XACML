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
	        <li class="current">删除用户组</li>
        </ul>
    </div>
    <!-- 右侧主页面上部导航条结束 -->
    <div><br />
    <center><h2>用户组列表</h2></center><br />
    	<table id="grouplist" border='0'>
    	<thead>
		<tr>
			<th style="min-width: 720px;">用户组名</th><th style="min-width: 300px;">用户组操作</th>
		</tr>
		</thead>
		<tbody >
		<%List<GroupInfo> giList = (List<GroupInfo>) request.getAttribute("groupList");
  			for (int i=0; i<giList.size(); ++i) {
  			GroupInfo gi = giList.get(i);
  		%>
		<tr id="li<%=gi.getGroup_id() %>">
		<th id=<%=gi.getGroup_id() %>><a><%=gi.getGroup_name() %></a></th>
		<th>
      	  <a href="javascript:void(0)" onclick="con(<%=gi.getGroup_id() %>);return false"  style="text-decoration: none;">删除</a>
		</th>
		</tr>
		<%}%>
		</tbody>
	</table>
    </div>
</body>
</html>