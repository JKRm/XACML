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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>google storage bucket 列表</title>
<link type="text/css" rel="stylesheet"
			href="<%=basePath%>css/admin.css" />
<script src="<%=basePath%>js/jquery-1.3.2.min.js" type="text/javascript"></script>
<script src="<%=basePath%>js/Gsbucket.js" type="text/javascript"></script>

<style>
	.top {
	 	margin-top: 23px;
		margin-left: 9px;
		padding-left: 6px;
		padding-bottom: 2px;
		border-bottom: 1px solid rgb(230, 230, 230);
		font-size: 12px;
	}
	.top span {
		font-weight: bold;
	}
</style>
</head>
<body>
<div id="include-div">
<h3>google storage bucket 列表</h3>
<div id="noticeList">
<a id="add"></a><a href="javascript:void(0)" onclick="add(this)">添加 bucket</a>
  <ul  style="list-style: none;">
  <%List<GsBucket> gbList = (List<GsBucket>) request.getAttribute("bucketList");
  	for (int i=0; i<gbList.size(); ++i) {
  		GsBucket gb = gbList.get(i);
  %>
  <li  style="line-height: 20px; height: 20px; border-bottom: 1px dashed gray;">
    <b id=<%=gb.getBucket_id() %>><a href="<%=basePath %>gs/getObjectListAction.action?bucketId=<%=gb.getBucket_id() %>"><%=gb.getBucket_name() %></a></b>
    <p class="time"  style="text-align: right; margin-top: -20px; ">
    	<span class="actions" >
      	  <a style="cursor:pointer" onclick="con(<%=gb.getBucket_id() %>)"  style="text-decoration: none;">删除</a>
    	</span>
    </p>
  </li>
  <%}%>
  </ul>
</div>
</div>
</body>
</html>