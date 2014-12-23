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
	<link href="<%=basePath %>admin/styles/wysiwyg.css" rel="stylesheet" type="text/css" />
	<link href="<%=basePath %>admin/styles/styles.css" rel="stylesheet" type="text/css" />
 	<script src="<%=basePath %>admin/scripts/jquery-1.3.2.min.js"></script>
 	<script src="<%=basePath %>admin/scripts/newAlert.js"></script>
</head>

<!-- JS部门名不为空验证 -->
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
			if(groupadd.groupname.value==""){
				sAlert("","群组名不能为空！");
				return false;
			}
			return true;
		}
		function addgroup(){
			if(checkadd() == true){
				var data;
        		data = $('#groupadd').serialize();
        		$.post(getRootPath()+"/admin/groupAction.action",data,function(data){
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
					{
						if (this == this.form.elements[i])
							break;

					}
					if(i == 0){
						addgroup();
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
	        <li class="current">添加群组</li>
        </ul>
    </div>
    <!-- 右侧主页面上部导航条结束 --><br/> <br/> <br/> <br/>
<div style="margin:0 auto; width:400px; height: 100px">
<form name="groupadd" id="groupadd" method="post" action="">
	<table>
		<tr>
			<td>群组名</td>
			<td><input type="text" name="groupname"></td>
			<td><input type="hidden" name="action" value="addgroup"></td>
		</tr>
		<tr>
			<td>
		<center><input type="button" value="添加" name="button" onclick="addgroup();"></center>
		
</td>
</tr>
	</table>
</form>
</div>
</body>
</html>