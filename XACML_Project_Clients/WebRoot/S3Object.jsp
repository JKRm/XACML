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
	<script src="<%=basePath%>js/S3object.js" type="text/javascript"></script>
	
</head>

<body>
 	<!-- 右侧主页面上部导航条开始 -->
    <div id="breadcrumb">
    	<ul>	
        	<li><img src="../img/icons/icon_breadcrumb.png" alt="Location" /></li>
        	<li><strong>Location:</strong></li>
            <li><a href="../Right_list.jsp" title="返回首页">欢迎</a></li>
            <li><a href="javascript:history.go(-1);">/Amazaon Simple Storage/</a></li>
	        <li class="current"><%=request.getAttribute("bn") %></li>
        </ul>
    </div>
    <!-- 右侧主页面上部导航条结束 -->
    <div>
    	<table id="grouplist" border='0'>
    	<thead>
		<tr>
			<th style="min-width: 520px;">文件名</th><th style="min-width: 200px;">文件大小</th><th style="min-width: 300px;">文件操作</th>
		</tr>
		</thead>
		<tbody >
		<%List<S3Object> soList = (List<S3Object>) request.getAttribute("soList");
  			for (int i=0; i<soList.size(); ++i) {
  			S3Object so = soList.get(i);
		 %>
		<tr id="li<%=so.getObject_id() %>">
		<th id=<%=so.getObject_id() %>><a><%=so.getObject_name() %></a></th>
		<th>size: <%=so.getObject_size() %> bytes</th>
		<th>
		<a href="javascript:void(0)" onclick="getPath(<%=so.getObject_id() %>);return false"  style="text-decoration: none;">下载</a>
      		<a href="javascript:void(0)" onclick="rename(this,<%=so.getObject_id()%>);return false" style="text-decoration: none;" id="re<%=so.getObject_id() %>">重命名</a>
      		<a id="mov<%=so.getObject_id() %>" href="javascript:void(0)" onclick="movefile(this,<%=so.getObject_id()%>);return false" style="text-decoration: none;">移动▼</a>
      		<a id="cop<%=so.getObject_id() %>" href="javascript:void(0)" onclick="copyfile(this,<%=so.getObject_id()%>);return false" style="text-decoration: none;">复制▼</a>
      		<a href="javascript:void(0)" onclick="con(<%=so.getObject_id() %>);return false"  style="text-decoration: none;">删除</a>
		</th>
		</tr>
		<tr id="tr<%=so.getObject_id() %>"><th id="move<%=so.getObject_id() %>"></th><th></th><th></th></tr>
		<%}%>
		</tbody>
	</table>
    </div>
</body>
</html>