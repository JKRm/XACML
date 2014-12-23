function sAlert(strTitle,strContent){ 
    var msgw,msgh,bordercolor; 
    msgw=400;//提示窗口的宽度 
    msgh=100;//提示窗口的高度 
    titleheight=25; //提示窗口标题高度 
    bordercolor="#336699";//提示窗口的边框颜色 
    titlecolor="#a4be39";//提示窗口的标题颜色
    var sWidth,sHeight; 
    sWidth=document.body.offsetWidth; 
    sHeight=screen.height; 
    var bgObj=document.createElement("div"); 
    bgObj.setAttribute('id','bgDiv'); 
    bgObj.style.position="absolute"; 
    bgObj.style.top="0"; 
    bgObj.style.background="#777"; 
    bgObj.style.filter="progid:DXImageTransform.Microsoft.Alpha(style=3,opacity=25,finishOpacity=75"; 
    bgObj.style.opacity="0.6"; 
    bgObj.style.left="0"; 
    bgObj.style.width=sWidth + "px"; 
    bgObj.style.height=sHeight + "px"; 
    bgObj.style.zIndex = "10000"; 
    document.body.appendChild(bgObj);
    var msgObj=document.createElement("div"); 
    msgObj.setAttribute("id","msgDiv"); 
    msgObj.setAttribute("align","center"); 
    msgObj.style.background="white"; 
    msgObj.style.border="1px solid " + bordercolor; 
    msgObj.style.position = "absolute"; 
    msgObj.style.left = "50%"; 
    msgObj.style.top = "50%"; 
    msgObj.style.font="12px/1.6em Verdana, Geneva, Arial, Helvetica, sans-serif"; 
    msgObj.style.marginLeft = "-225px" ; 
    msgObj.style.marginTop = -75+document.documentElement.scrollTop+"px"; 
    msgObj.style.width = msgw + "px"; 
    msgObj.style.height =msgh + "px"; 
    msgObj.style.textAlign = "center"; 
    msgObj.style.lineHeight ="25px"; 
    msgObj.style.zIndex = "10001";
    var title=document.createElement("h4"); 
    title.setAttribute("id","msgTitle"); 
    title.setAttribute("align","right"); 
    title.style.margin="0"; 
    title.style.padding="3px"; 
    title.style.background=bordercolor; 
    title.style.filter="progid:DXImageTransform.Microsoft.Alpha(startX=20, startY=20, finishX=100, finishY=100,style=1,opacity=75,finishOpacity=100);"; 
    title.style.opacity="0.75"; 
    title.style.border="1px solid " + bordercolor; 
    title.style.height="18px"; 
    title.style.font="12px Verdana, Geneva, Arial, Helvetica, sans-serif"; 
    title.style.color="white"; 
    title.style.cursor="pointer"; 
    title.title = "点击关闭"; 
    title.innerHTML="<table border='0′ width='100%'><tr><td align='left'><b>"+ strTitle +"</b></td><td>关闭</td></tr></table></div>"; 
    title.onclick=function(){ 
    document.body.removeChild(bgObj); 
    document.getElementById("msgDiv").removeChild(title); 
    document.body.removeChild(msgObj); 
    } ;
    document.body.appendChild(msgObj); 
    document.getElementById("msgDiv").appendChild(title); 
    var txt=document.createElement("p"); 
    txt.style.margin="1em 0" ;
    txt.setAttribute("id","msgTxt"); 
    txt.innerHTML=strContent; 
    document.getElementById("msgDiv").appendChild(txt); 
} 

function getRootPath(){
        var strFullPath=window.document.location.href;
        var strPath=window.document.location.pathname;
        var pos=strFullPath.indexOf(strPath);
        var prePath=strFullPath.substring(0,pos);
        var postPath=strPath.substring(0,strPath.substr(1).indexOf('/')+1);
        return(prePath+postPath);
        }



function con(nid) {
	var hi = document.getElementById("li"+nid);
	var hi2 = document.getElementById("tr"+nid);
		if (confirm("确定删除？")) {
			$.post(getRootPath()+"/local/delBucketAction.action",	
				"bucketId="+nid,		
				function(data, statusText) {
					var realobj=eval('('+data+')');
					if(realobj.result=='success'){
						sAlert("","已成功删除 "+realobj.oldname+" 。");
						$(hi).remove();
						$(hi2).remove();
					}
					else if(realobj.result=='error'){
						sAlert("","删除失败！内部错误！请咨询开发人员！");
					}
					else if(realobj.result=='deny'){
						sAlert("","删除失败！您所在的用户组未获得该操作权限！");
					}
					//var list = data;	
					//alert(statusText);
				},
			"html");
		} else {
		}
	}
	
function add(obj){
	var area = document.getElementById("add");
	if(obj.innerHTML=="添加 bucket"){
	area.innerHTML =  "<input type=\"text\"  id = \"newName\" name=\"newName\" onblur=\"saveBucket()\">";
	obj.innerHTML="取消添加";
	}
	else{
	area.innerHTML="";
	obj.innerHTML="添加 bucket";
	}
}

function saveBucket(){
	var newName = document.getElementById("newName").value;
	var hi = document.getElementById("newName");
	var hi2 = document.getElementById("addb");
	if (confirm("确定添加？")) {
		$.post(getRootPath()+"/local/addBucketAction.action",	
			"newName="+newName,		
			function(data, statusText) {
				var realobj=eval('('+data+')');
				if(realobj.result=='success'){
					location.reload() ;
				}
				else if(realobj.result=='error'){
					sAlert("","添加失败！内部错误！请咨询开发人员！");
					$(hi).remove();
					hi2.innerHTML = "添加 bucket";
				}
				else if(realobj.result=='deny'){
					sAlert("","添加失败！您所在的用户组未获得该操作权限！");
					$(hi).remove();
					hi2.innerHTML = "添加 bucket";
				}
				//var list = data;	
				//alert(statusText);
			},
		"html");
	} else {
	}
}

function upfile(obj,oid){
	var area = document.getElementById("upArea"+oid);
	if(obj.innerHTML=='上传文件▼'){
		obj.innerHTML='取消上传▲';
		var form1 = document.createElement("form");
		var upfile = document.createElement("input");
		var hide = document.createElement("input");
		var button = document.createElement("input");
		button.type = 'submit';
		button.value = '上  传';
		hide.type = 'hidden';
		hide.id = 'bucketId';
		hide.name = 'bucketId';
		hide.value = oid;
		form1.id = 'form1';
		form1.method = 'post';
		form1.action = getRootPath() + '/local/uploadObjectAction.action';
		form1.enctype = 'multipart/form-data';
		upfile.type = 'file';
		upfile.name = 'myFile';
		form1.appendChild(upfile);
		form1.appendChild(hide);
		form1.appendChild(button);
		area.appendChild(form1);
	}
	else{
		area.innerHTML = "";
		obj.innerHTML = '上传文件▼';
		
	}
		
	}
	
	
	function getList(bid){
		$.post(getRootPath()+"/local/getPremAction.action",	
				"bucketId="+bid,		
				function(data, statusText) {
					var realobj=eval('('+data+')');
					if(realobj.result=='success'){
						window.location.href=getRootPath()+
						"/local/getObjectListAction.action?bucketId="+bid;
					}
					else if(realobj.result=='deny'){
						sAlert("","获取列表失败！您所在的用户组未获得该操作权限！");
					}
					//var list = data;	
					//alert(statusText);
				},
			"html");
	}
	
