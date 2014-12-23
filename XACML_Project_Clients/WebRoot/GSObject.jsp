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
<title>GS资源列表</title>
<link type="text/css" rel="stylesheet"
			href="<%=basePath%>css/admin.css" />
<script src="<%=basePath%>js/jquery-1.3.2.min.js" type="text/javascript"></script>
<script src="<%=basePath%>js/GSobject.js" type="text/javascript"></script>

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
<h3>GS资源列表</h3>
<div id="noticeList">
  <ul  style="list-style: none;">
  <%List<GsObject> goList = (List<GsObject>) request.getAttribute("soList");
  	for (int i=0; i<goList.size(); ++i) {
  		GsObject go = goList.get(i);
  		
  %>
  <li  style="line-height: 20px; height: 20px; border-bottom: 1px dashed gray;">
    <b id=<%=go.getObject_id() %>><a href="<%=filePath %>localResource/<%=go.getBucket_name() %>/<%=go.getObject_file() %>"><%=go.getObject_name() %></a></b>
     <span class="datetime">size: <%=go.getObject_size() %> bytes</span>
    <p class="time"  style="text-align: right; margin-top: -20px; ">
    	<span class="actions" >
      	  <a href="javascript:void(0)" onclick="rename(this,<%=go.getObject_id()%>);return false" style="text-decoration: none;">重命名</a>
      	  <a id="move" href="javascript:void(0)" onclick="movefile(this,<%=go.getObject_id()%>);return false" style="text-decoration: none;">移动▼</a>
      	   <a id="copy" href="javascript:void(0)" onclick="copyfile(this,<%=go.getObject_id()%>);return false" style="text-decoration: none;">复制▼</a>
      	  <a style="cursor:pointer" onclick="con(<%=go.getObject_id() %>)"  style="text-decoration: none;">删除</a>
    	</span>
    </p>
  </li>
  <li id="move<%=go.getObject_id() %>"></li>
  <%}%>
  </ul>
</div>
</div>
</body>
</html>