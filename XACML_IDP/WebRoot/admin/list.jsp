<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>用户管理系统</title>
	<link href="<%=basePath %>admin/styles/layout.css" rel="stylesheet" type="text/css" />
	<link href="<%=basePath %>admin/styles/wysiwyg.css" rel="stylesheet" type="text/css" />
	<link href="<%=basePath %>admin/styles/styles.css" rel="stylesheet" type="text/css" />

</head>

<!-- 用frame分帧将页面分成三部分 -->
<frameset rows="70px,*" border="0" frameborder="0" framespacing= "0" scrolling="no" noresize>
	<frame src="top_list.jsp">
	<frameset cols="226px,*" >
		<frame src="left_list.jsp" border="0" frameborder="0" framespacing= "0" scrolling="no" noresize name="left">
		<frame src="right_list.jsp" border="0" frameborder="0" framespacing="0"  noresize name="right">
		<!-- frame设置name属性 左边按钮设置target="right" 达到点击左边按钮后右边显示相应页面  -->
	</frameset>
</frameset>

	<!-- 防止IE6出现不兼容 -->
    <!--[if IE 6]>
    <script type='text/javascript' src='scripts/png_fix.js'></script>
    <script type='text/javascript'>
      DD_belatedPNG.fix('img, .notifycount, .selected');
    </script>
    <![endif]--> 
    
</html>
