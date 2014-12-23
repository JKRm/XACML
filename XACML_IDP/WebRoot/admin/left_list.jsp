<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="<%=basePath %>admin/styles/layout.css" rel="stylesheet" type="text/css" />
	<link href="<%=basePath %>admin/styles/styles.css" rel="stylesheet" type="text/css" />
    <script src="<%=basePath %>admin/scripts/jquery-1.3.2.min.js"></script>
    <script type='text/javascript' src='<%=basePath %>admin/scripts/jquery-ui.min.js'></script>
    
</head>

<body id="homepage">
  <!-- 左部菜单开始 -->
  <div id="leftside">
    <div class="user">
        <img src="<%=basePath %>admin/img/avatar.png" width="44" height="44" class="hoverimg" alt="Avatar" />
        <p>Logged in as:</p>
        <p class="username"></p>
        <!-- 通过session获取用户名 并显示 -->
        <p class="userbtn"><a href="admin/adminpass.php" title="修改密码" target="right">修改密码</a></p>
        <p class="userbtn"><a href="off.php" target="_parent" title="注销">Log out</a></p>
    </div>
    
    <ul id="nav">
        <li>
        <a class="collapsed heading" >群组管理</a>
        <ul class="navigation">
        <li><a href="<%=basePath %>admin/group/grpadd.jsp" title="添加群组" target="right">添加群组</a></li>
        <li><a href="<%=basePath %>admin/group/grplist.jsp" title="查看群组" target="right">查看群组</a></li>
     	</ul>
        </li>
            
            <li><a class="expanded heading">用户管理</a>
                <ul class="navigation">
                    <li><a href="<%=basePath %>admin/user/useradd.jsp" title="增加用户" class="likelogin" target="right">增加用户</a></li>
                    <li><a href="<%=basePath %>admin/user/userlist.jsp" title="查看用户" target="right">查看用户</a></li>
                </ul>
            </li>            
    </ul>
       
    </div>
    <!-- 左部菜单结束 -->
    
    <!-- JS特效 -->
    <script type="text/javascript" src="scripts/enhance.js"></script>
    <script type='text/javascript' src='scripts/excanvas.js'></script>

	<script type='text/javascript' src='scripts/jquery.wysiwyg.js'></script>
    <script type='text/javascript' src='scripts/visualize.jQuery.js'></script>
    <script type="text/javascript" src='scripts/functions.js'></script>
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