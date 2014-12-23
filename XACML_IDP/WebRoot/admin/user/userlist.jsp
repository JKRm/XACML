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

<body>
	<!-- 右侧主页面上部导航条开始 -->
    <div id="breadcrumb">
    	<ul>	
        	<li><img src="<%=basePath %>admin/img/icons/icon_breadcrumb.png" alt="Location" /></li>
        	<li><strong>Location:</strong></li>
            <li><a href="../right_list.jsp" title="返回首页">欢迎</a></li>
            <li>/</li>
	        <li class="current">用户列表</li>
        </ul>
    </div>
    <!-- 右侧主页面上部导航条结束 -->
    
	<div>
    	<table id="userlist" border='0'>
    	<thead>
		<tr>
			<th style="min-width: 280px;">用户名</th><th style="min-width: 280px;">真实名称</th>
			<th style="min-width: 220px;">删除</th><th style="min-width: 220px;">分配群组</th>
		</tr>

		</thead>
		<tbody>

		</tbody>
		</table>

    	<table id="usergrp" border='0' style='display:none'>
		<tr>
			<td>用户名</td>
		</tr>
        <tr>
            <td>群组</td>
        </tr>
        <tr>
        	<td>
        	<input type="button" value="修改" name="button" onclick="modifyusergroup();">
        	<input type="hidden" class="username" name="username">
			<input type="hidden" name="action" value="modifyusergroup">
        	</td>
        </tr>
		</table>
		<p id="modifyalert" style='display:none'>正在修改，请稍后……</p>
    </div>
<script language="javascript">

function getRootPath(){
    var strFullPath=window.document.location.href;
    var strPath=window.document.location.pathname;
    var pos=strFullPath.indexOf(strPath);
    var prePath=strFullPath.substring(0,pos);
    var postPath=strPath.substring(0,strPath.substr(1).indexOf('/')+1);
    return(prePath+postPath);
    }

		function firm(){
			if(confirm("是否删除此用户？")){
				
				return true;
			}
			 else{
                return false;
            }
		}

		function modifyusergroup(){
			var form = $("<form action=\"#\"></form>");
			form.append($("#usergrp"));
			var url = getRootPath()+"/admin/userAction.action";

        	data = form.serialize();
        	$("#modifyalert").show();
        	$.post(getRootPath()+"/admin/userAction.action",data,function(data){
        		if(data == "true"){
        			sAlert("","添加成功！");
        			document.location.reload();
        		}else{
        			sAlert("","添加失败");
        		}
        	});


		}

		function deluser(username){
			if(firm() == false){
				return;
			}

			var url = getRootPath()+"/admin/userAction.action?action=deluser&username="+username;
			url = encodeURI(url);

			$.get(url,function(data){
					if(data == "true"){
						sAlert("","删除成功！");
					}else{
						sAlert("","删除失败！");
					}
					document.location.reload();
				}
			);
		}
		function getuserbyname (username) {

			$(".username").val(username);

			var url = getRootPath()+"/admin/groupAction.action?action=getallgroups";
			$.getJSON(url,function(data){
				if(data.length > 0){
					var tr = $("#usergrp  tr:eq(1)");
					var td = $("<td></td>");
					for (var i = 0; i < data.length; i++) {
						var selectgroup = $("<input type=\"checkbox\" />");
						var groupnamelabel = $("<label></label>");
						selectgroup.attr("name","groupname");
						selectgroup.attr("id",data[i].groupname);
						selectgroup.attr("value",data[i].groupname);
						td.append(selectgroup);
						groupnamelabel.append(data[i].groupname);
						groupnamelabel.attr("for",data[i].groupname);
						td.append(groupnamelabel);
						td.append("<br/>");
					};
					tr.append(td);		
					}

			});



			var url = getRootPath()+"/admin/userAction.action?action=getuser&username="+encodeURI(username);
			$.getJSON(url,function(data){
				$("#userlist").hide();
				$("#usergrp").show();
				var usernametd = $("<td algin='center'></td>");
				var usergrptd  = $("<td algin='center'></td>");

				usernametd.append(data.username);

				for (var i = 0; i < data.usergroup.length; i++) {
					var usergroup = $("#usergrp  tr:eq(1)").find("input");
					usergroup.each(function(){
						if($(this).attr("value") == data.usergroup[i]){
							$(this).attr("checked","checked");
						}
					});

				};
				$("#usergrp  tr:first").append(usernametd);
				$("#usergrp  tr:eq(1)")(1).append(usergrptd);
			});
		}

		function getallusers(){
			var url = getRootPath()+"/admin/userAction.action?action=getallusers";
			$.getJSON(url,function(data){
				if(data.length > 0){
					var tbody = $("#userlist > tbody");
					for(var i = 0; i < data.length ; i++){
						var tr = $("<tr></tr>");
						var usernametd  = $("<td algin='center'></td>");
						var realnametd  = $("<td algin='center'></td>");
						var deltd       = $("<td class='del' algin='center'></td>")
						var del         = $("<a>删除</a>");

						var modifytd    = $("<td class = 'modify' algin='center'></td>")
						var modify      = $("<a>分配群组</a>");


						del.attr("href","#");
						modify.attr("href","#");


						usernametd.append(data[i].username);
						realnametd.append(data[i].realname);
						deltd.append(del);
						modifytd.append(modify);
						tr.append(usernametd);
						tr.append(realnametd);
						tr.append(deltd);
    					tr.append(modifytd);
						tbody.append(tr);
					}
					var del = tbody.find(".del").find("a");
					
					del.each(function(i){
						$(this).click(function(){
							deluser(data[i].username);
						});							
					});
					
					var modify = tbody.find(".modify").find("a");

					modify.each(function(i){
						$(this).click(function(){
							getuserbyname(data[i].username);
						});							

					});
				}

			}
			);

		}
    		$(document).ready(function() {
    			getallusers();
		});
</script>
</body>
</html>
