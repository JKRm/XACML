<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <title>音乐播放</title>
    <link rel="stylesheet" href="<%=basePath%>css/preview.css" />
    <script src="<%=basePath%>js/jquery-1.3.2.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		window.onload = function(e) {
		if (e) return setTimeout(arguments.callee, 500);
		var url = "http://192.168.111.9:8080/localResource/3434.mp3";
		WMPlayer.play(url);
		WMPlayer.addEventListener("PositionChange", function(oldPosition, newPosition) {
			//alert(this == player.wmp);
		});
		var lrc = new LRCPlayer(
			  WMPlayer.wmp
			, document.getElementById('txt').value
			, document.getElementById('lrc')
			, 230, 300
			, 'C2D5F0', '00FF00'
		);
		//setTimeout(function() { lrc.remove(); }, 1000 * 30);
	};
	</script>
</head>
<body>
<div id="main">
  <div id="audioPlayer">
  	<div style="height: 60px;"></div>
	  <div id="audio">
	  <script src="/aesop/js/player.js" type="text/javascript"></script>
	    <script src="/aesop/js/lrc.js" type="text/javascript"></script>
		<textarea id="txt" style="display:none;">
		${lrc}
		</textarea>
		<div id="lrc" style="float:left; background:#49648A ;"></div>
	  </div>
  </div>
  
</div>
</body>
</html>