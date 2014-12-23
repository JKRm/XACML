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
	<script src="<%=basePath%>js/mypolicy.js" type="text/javascript"></script>
	
</head>

<body>
 	<!-- 右侧主页面上部导航条开始 -->
    <div id="breadcrumb">
    	<ul>	
        	<li><img src="../img/icons/icon_breadcrumb.png" alt="Location" /></li>
        	<li><strong>Location:</strong></li>
            <li><a href="../Right_list.jsp" title="返回首页">欢迎</a></li>
            <li>/</li>
	        <li class="current">删除策略</li>
        </ul>
    </div>
    <!-- 右侧主页面上部导航条结束 -->
    <div>
    <center><h2>策略列表</h2></center><br/>
    
   
   <center><form method="post" action="<%=basePath%>group/getPolicies.action">
    <select id="groupId" name="groupId">
    <%List<GroupInfo> giList = (List<GroupInfo>) request.getAttribute("groupList");
  			for (int i=0; i<giList.size(); ++i) { 
  				GroupInfo gi = giList.get(i);
  			%>
  			<option value="<%=gi.getGroup_id() %>"><%=gi.getGroup_name() %></option>
  			<%} %>
    </select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <select id="resName" name="resName">
    	<option value="http://AmazonS3.com">Amzaon S3</option>
    	<option value="http://local.com">Local Storage</option>
    </select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <input type="submit" value="查看"/>
   </form></center><br/>
    	<table id="grouplist" border='0'>
    	<thead>
		<tr>
			<th style="min-width: 60px;">策略id</th><th style="min-width: 150px;">策略类型</th><th style="min-width: 200px;">所属用户组</th>
			<th style="min-width: 200px;">资源名</th><th style="min-width: 250px;">策略文件</th><th style="min-width: 100px;">策略操作</th>
		</tr>
		</thead>
		<tbody >
		<% 
			if(session.getAttribute("piList")!=null){
			List<PolicyInfo> piList = (List<PolicyInfo>) session.getAttribute("piList");
  			for (int i=0; i<piList.size(); ++i) {
  			PolicyInfo pi = piList.get(i);
  		%>
		<tr id="li<%=pi.getPolicy_id() %>">
		<th><a><%=pi.getPolicy_id() %></a></th>
		<th><a><%=pi.getPolicy_type()%></a></th>
		<th><a><%=session.getAttribute("groupName")%></a></th>
		<th><a><%=pi.getResource_name()%></a></th>
		<th><a><%=pi.getPolicy_file()%></a></th>
		<th>
      	  <a href="javascript:void(0)" onclick="con(<%=pi.getPolicy_id() %>);return false"  style="text-decoration: none;">删除</a>
		</th>
		</tr>
		<%}}%>
		</tbody>
	</table>
    </div>
</body>
</html>