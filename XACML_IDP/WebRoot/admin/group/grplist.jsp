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
	        <li class="current">修改/删除群组</li>
            <li class="current"><font color="red">***删除群组请慎重***</font></li>
        </ul>
    </div>
    <!-- 右侧主页面上部导航条结束 -->
    <div>
    	<table id="grouplist" border='0'>
    	<thead>
		<tr>
			<th style="min-width: 350px;">群组名称</th><th style="min-width: 350px;">用户数</th>
			<th style="min-width: 300px;">删除</th>
		</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
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
			if(confirm("是否删除此部门？")){
				
				return true;
			}
			 else{
                return false;
            }
		}
		function delgroup(groupname){
			if(firm() == false){
				return;
			}

			var url = getRootPath()+"/admin/groupAction.action?action=delgroup&groupname="+groupname;
			url = encodeURI(url);
			$.get(url,function(data){
					if(data == "true"){
						sAlert("","删除成功!");
					}else{
						sAlert("","删除失败!");
					}
					document.location.reload();
				}
			);
		}
		$(document).ready(function() {
			var url = getRootPath()+"/admin/groupAction.action?action=getallgroups";
			$.getJSON(url,function(data){
				if(data.length > 0){
					var tbody = $("#grouplist > tbody");
					for(var i = 0; i < data.length ; i++){
						var tr = $("<tr></tr>");
						var groupnametd = $("<td algin='center'></td>");
						var usernumtd = $("<td algin='center'></td>");
						var deltd = $("<td algin='center'></td>")
						var del = $("<a>删除</a>");
						var groupname = data[i].groupname;

						del.attr("href","#");
						groupnametd.append(data[i].groupname);
						usernumtd.append(data[i].users.length);
						deltd.append(del);
						tr.append(groupnametd);
						tr.append(usernumtd);
						tr.append(deltd);

						tbody.append(tr);
					}
					var del = tbody.find("a");
					
					del.each(function(i){
						$(this).click(function(){
							delgroup(data[i].groupname);
						}
						);
					});
					
				}

			}
			);
		});

</script>
</body>
</html>

