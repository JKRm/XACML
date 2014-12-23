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
	<script src="<%=basePath%>js/S3bucket.js" type="text/javascript"></script>
	
</head>

<body>
 	<!-- 右侧主页面上部导航条开始 -->
    <div id="breadcrumb">
    	<ul>	
        	<li><img src="../img/icons/icon_breadcrumb.png" alt="Location" /></li>
        	<li><strong>Location:</strong></li>
            <li><a href="../Right_list.jsp" title="返回首页">欢迎</a></li>
            <li>/</li>
	        <li class="current">Amazon Stroage S3</li>
        </ul>
    </div>
    <!-- 右侧主页面上部导航条结束 -->
    <div>
	<a id="add"></a><a href="javascript:void(0)" onclick="add(this)" id="addb">添加 bucket</a>
    	<table id="grouplist" border='0'>
    	<thead>
		<tr>
			<th style="min-width: 720px;">包名</th><th style="min-width: 300px;">包操作</th>
		</tr>
		</thead>
		<tbody >
		<%List<S3Bucket> sbList = (List<S3Bucket>) request.getAttribute("bucketList");
  			for (int i=0; i<sbList.size(); ++i) {
  			S3Bucket sb = sbList.get(i);
  		%>
		<tr id="li<%=sb.getBucket_id() %>">
		<th id=<%=sb.getBucket_id() %>><a href="javascript:void(0)" onclick="getList(<%=sb.getBucket_id() %>);return false"><%=sb.getBucket_name() %></a></th>
		<th>
			<a href="javascript:void(0)" onclick="upfile(this,<%=sb.getBucket_id()%>);return false" style="text-decoration: none;">上传文件▼</a>
      		<a href="javascript:void(0)" onclick="con(<%=sb.getBucket_id() %>);return false"  style="text-decoration: none;">删除</a>
		</th>
		</tr>
		<tr  id="tr<%=sb.getBucket_id() %>"><th id="upArea<%=sb.getBucket_id()%>"></th><th></th></tr>
		<%}%>
		</tbody>
	</table>
    </div>
	    

	    
</body>
</html>