<%@ page language="java" import="java.util.*"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="<%=basePath %>css/layout.css" rel="stylesheet" type="text/css" />
	<link href="<%=basePath %>css/styles.css" rel="stylesheet" type="text/css" />
	<script src="<%=basePath%>js/jquery-1.3.2.min.js" type="text/javascript"></script>
    <script type='text/javascript' src='<%=basePath %>js/jquery-ui.min.js'></script>
    
</head>

	<body id="homepage">
  <!-- 左部菜单开始 -->
  <div id="leftside">
    <div class="user">
        <img src="img/avatar.png" width="44" height="44" class="hoverimg" alt="Avatar" />
        <p>欢迎您使用本系统,<%=session.getAttribute("admin") %></p>
        <p class="username"></p>
        <p class="userbtn"><a href="<%=basePath%>group/adminOutAction.action" target="_parent" title="注销">注销</a></p>
    </div>
    
    <ul id="nav">
        <li>
        <a class="expanded heading" >用户组管理</a>
        <ul class="navigation">
        <li><a href="<%=basePath %>addgroup.jsp" title="添加用户组" target="right">添加用户组</a></li>
        <li><a href="<%=basePath %>group/getGroupList.action" title="删除用户组" target="right">删除用户组</a></li>
     	</ul>
        </li>
        	<li>
                <a class="expanded heading" >访问策略管理</a>
                 <ul class="navigation">
                   <li><a href="<%=basePath %>group/getGroup4p.action?callfunc=1" title="添加策略" target="right">添加策略</a></li>
                    <li><a href="<%=basePath %>group/getGroup4p.action" title="删除策略" target="right">删除策略</a></li>
                    
                </ul>
            </li>
            
                
    </ul>
       
    </div>
    <!-- 左部菜单结束 -->
    
    <!-- JS特效 -->
    <script type="text/javascript" src="<%=basePath %>js/enhance.js"></script>
    <script type='text/javascript' src='<%=basePath %>js/excanvas.js'></script>

	<script type='text/javascript' src='<%=basePath %>js/jquery.wysiwyg.js'></script>
    <script type='text/javascript' src='<%=basePath %>js/visualize.jQuery.js'></script>
    <script type="text/javascript" src='<%=basePath %>js/functions.js'></script>
    <script type="text/javascript">

function getCookie(c_name){
    if(document.cookie.length > 0){
        c_start = document.cookie.indexOf(c_name+"=");
        if(c_start != -1){
            c_start = c_start + c_name.length + 1 ;
            c_end   = document.cookie.indexOf(";",c_start);
            if(c_end == -1){
                c_end = document.cookie.length;
            }
            return unescape(document.cookie.substring(c_start,c_end));
        }
        return null;
    }
}
    
    $(document).ready(function(){
        var adminname = getCookie("adminname");
        if(adminname != null){
            $(".username").text(adminname);
        }
    });
   
    </script>
</body>
</html>
