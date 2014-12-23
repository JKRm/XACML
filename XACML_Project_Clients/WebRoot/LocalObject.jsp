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
	<script src="<%=basePath%>js/Localobject.js" type="text/javascript"></script>
	
</head>

<body>
 	<!-- 右侧主页面上部导航条开始 -->
    <div id="breadcrumb">
    	<ul>	
        	<li><img src="../img/icons/icon_breadcrumb.png" alt="Location" /></li>
        	<li><strong>Location:</strong></li>
            <li><a href="../Right_list.jsp" title="返回首页">欢迎</a></li>
            <li><a href="javascript:history.go(-1);">/Local Storage/</a></li>
	        <li class="current"><%=request.getAttribute("bn") %></li>
        </ul>
    </div>
    <!-- 右侧主页面上部导航条结束 -->
    <div>
    	<table id="grouplist" border='0'>
    	<thead>
		<tr>
			<th style="min-width: 520px;">文件名</th><th style="min-width: 200px;">文件大小</th><th style="min-width: 300px;">文件操作</th>
		</tr>
		</thead>
		<tbody >
		<%List<LocalObject> loList = (List<LocalObject>) request.getAttribute("loList");
  	for (int i=0; i<loList.size(); ++i) {
  		
  		LocalObject lo = loList.get(i);
  		String ncfile = null;
  		String cbuck=java.net.URLEncoder.encode(lo.getBucket_name(), "UTF-8");
  		String ncbuck = java.net.URLEncoder.encode(cbuck,"UTF-8");
  		String realType = lo.getObject_name().substring(lo.getObject_name().lastIndexOf(".")+1);
  		if(lo.getObject_file()!=null){
  		String cfile=java.net.URLEncoder.encode(lo.getObject_file(), "UTF-8");
  		ncfile = java.net.URLEncoder.encode(cfile,"UTF-8");
  		}
  %>
		<tr id="li<%=lo.getObject_id() %>">
		<%if(realType.equals("doc")||realType.equals("docx")||
							realType.equals("ppt")||realType.equals("pptx")||
							realType.equals("pdf")||realType.equals("xls")||
							realType.equals("txt")||realType.equals("xlsx")){ %>
    <th id=<%=lo.getObject_id() %>><a href="<%=basePath %>FlexPaper_2.1.1/showResource.jsp?file=<%=lo.getObject_file() %>"><%=lo.getObject_name() %></a></th>
    <%} else if(realType.equals("mp4")||realType.equals("avi")||
							realType.equals("3gp")||realType.equals("asf")||
							realType.equals("mpg")||realType.equals("wmv")||
							realType.equals("flv")||realType.equals("mov")||
							realType.equals("asx")){ %>
     <th id=<%=lo.getObject_id() %>><a href="<%=basePath %>FlvPlayer/previewVideo.jsp?file=<%=lo.getObject_file() %>"><%=lo.getObject_name() %></a></th>
     <%} else if(realType.equals("mp3")){ %>
     <th id=<%=lo.getObject_id() %>><a href="<%=basePath %>FlvPlayer/previewMP3.jsp?bucket=<%=ncbuck %>&file=<%=ncfile %>"><%=lo.getObject_name() %></a></th>
     <%} else if(realType.equals("jpg")){ %>
     	<th id=<%=lo.getObject_id() %>><a href="<%=filePath %>localResource/<%=lo.getBucket_name() %>/<%=lo.getObject_name() %>"><%=lo.getObject_name() %></a></th>
     <%} else{%>
     	<th id=<%=lo.getObject_id() %>><a href="javascript:void(0)" onclick="sAlert('','该格式文件暂不支持预览！')"><%=lo.getObject_name() %></a></th>
     	<%} %>
		<th>size: <%=lo.getObject_size() %> bytes</th>
		<th>
		<a href="javascript:void(0)" onclick="getPath(<%=lo.getObject_id() %>);return false"  style="text-decoration: none;">下载</a>
      	  	<a href="javascript:void(0)" onclick="rename(this,<%=lo.getObject_id()%>);return false" style="text-decoration: none;" id="re<%=lo.getObject_id() %>">重命名</a>
      	  	<a id="mov<%=lo.getObject_id() %>" href="javascript:void(0)" onclick="movefile(this,<%=lo.getObject_id()%>);return false" style="text-decoration: none;">移动▼</a>
      	  	<a id="cop<%=lo.getObject_id() %>" href="javascript:void(0)" onclick="copyfile(this,<%=lo.getObject_id()%>);return false" style="text-decoration: none;">复制▼</a>
      	  <a href="javascript:void(0)" onclick="con(<%=lo.getObject_id() %>);return false"  style="text-decoration: none;">删除</a>
		</th>
		</tr>
		<tr  id="tr<%=lo.getObject_id() %>"><th id="move<%=lo.getObject_id() %>"></th><th></th><th></th></tr>
		<%}%>
		</tbody>
	</table>
    </div>
	    

	    
</body>
</html>