<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String filePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>资源预览</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="initial-scale=1,user-scalable=no,maximum-scale=1,width=device-width" />
    <style type="text/css" media="screen">
        html, body	{ height:100%; }
        body { margin:0; padding:0; overflow:auto; }
        #flashContent { display:none; }
    </style>

    <link rel="stylesheet" type="text/css" href="<%=basePath%>FlexPaper_2.1.1/css/flexpaper.css" />
    <script type="text/javascript" src="<%=basePath%>FlexPaper_2.1.1/js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>FlexPaper_2.1.1/js/flexpaper.js"></script>
    <script type="text/javascript" src="<%=basePath%>FlexPaper_2.1.1/js/flexpaper_handlers.js"></script>
    <link href="<%=basePath%>css/layout.css" rel="stylesheet" type="text/css" />
	<link href="<%=basePath%>css/styles.css" rel="stylesheet" type="text/css" />
</head>
<body>

<div style="position:absolute;left:10px;top:10px;">

<div id="documentViewer" class="flexpaper_viewer" style="width:1030px;height:850px;"></div>

<script type="text/javascript">
    

    $('#documentViewer').FlexPaperViewer(
            { config : {

                SWFFile : '../../localResource/swfLocation/<%=request.getParameter("file")%>',

                Scale : 0.6,
                ZoomTransition : 'easeOut',
                ZoomTime : 0.5,
                ZoomInterval : 0.2,
                FitPageOnLoad : true,
                FitWidthOnLoad : false,
                FullScreenAsMaxWindow : false,
                ProgressiveLoading : false,
                MinZoomSize : 0.2,
                MaxZoomSize : 5,
                SearchMatchAll : false,
                InitViewMode : 'Portrait',
                RenderingOrder : 'flash',
                StartAtPage : '',

                ViewModeToolsVisible : true,
                ZoomToolsVisible : true,
                NavToolsVisible : true,
                CursorToolsVisible : true,
                SearchToolsVisible : true,
                WMode : 'window',
                localeChain: 'en_US'
            }}
    );
</script>
</body>
</html>