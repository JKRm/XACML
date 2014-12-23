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
	<script src="<%=basePath %>admin/scripts/newAlert.js"></script>
</head>

<script language="javascript">

function getRootPath(){
    var strFullPath=window.document.location.href;
    var strPath=window.document.location.pathname;
    var pos=strFullPath.indexOf(strPath);
    var prePath=strFullPath.substring(0,pos);
    var postPath=strPath.substring(0,strPath.substr(1).indexOf('/')+1);
    return(prePath+postPath);
    }

		function checkadd(){
			if(useradd.username.value==""){
				
               sAlert("","用户名不能为空！");
				return false;
			}
			if(useradd.realname.value==""){
				
                sAlert("","真实姓名不能为空！");
				return false;
			}
            if(useradd.password.value==""){
				
                sAlert("","密码不能为空！");
				return false;
			}
			return true;
		}
		function adduser(){
			if(checkadd() == true){
				var data;
        		data = $('#useradd').serialize();
        		$.post(getRootPath()+"/admin/userAction.action",data,function(data){
        			if(data == "true"){
        				sAlert("","添加成功！");
        				document.location.reload();
        			}else{
        				sAlert("","添加失败！");
        			}
        		}
        		);
			}
		}
		$(document).ready(function(){
			$("input").keypress(function (e) {
				var keyCode = e.keyCode ? e.keyCode : e.which ? e.which : e.charCode;
				if (keyCode == 13){
					var i;
					for (i = 0; i < this.form.elements.length; i++)
						if (this == this.form.elements[i])
							break;

					if(i == 2){
						adduser();
					}

						i = (i + 1) % this.form.elements.length;
						this.form.elements[i].focus();
						return false;
				}
				else
					return true;
			});

		});
</script>
<body>
	<!-- 右侧主页面上部导航条开始 -->
    <div id="breadcrumb">
    	<ul>	
        	<li><img src="<%=basePath %>admin/img/icons/icon_breadcrumb.png" alt="Location" /></li>
        	<li><strong>Location:</strong></li>
            <li><a href="../right_list.jsp" title="返回首页">欢迎</a></li>
            <li>/</li>
	        <li class="current">增加用户</li>
        </ul>
    </div>
    <!-- 右侧主页面上部导航条结束 -->
    
<form method="post" action="#" name="useradd" id="useradd">

<table>
		<tr>
			<td>用户名</td>
			<td><input type="text" name="username"></td>
		</tr>
		<tr>
			<td>真实名称</td>
			<td><input type="text" name="realname"></td>
		</tr>
		<tr>
			<td>密码</td>
			<td><input type="text" name="password"></td>
		</tr>
		<tr>
			<td>
		<input type="button" value="添加" name="button" onclick="adduser();">
		<td><input type="hidden" name="action" value="adduser"></td>

			</td>
		</tr>
</table>
</form>

</body>
</html>


