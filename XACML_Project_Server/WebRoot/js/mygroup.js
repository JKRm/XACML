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
		if (confirm("确定删除？")) {
			$.post(getRootPath()+"/group/delAction.action",	
				"groupId="+nid,		
				function(data, statusText) {
					var realobj=eval('('+data+')');
					if(realobj.result=='success'){
						sAlert("","已成功删除该用户组 。");
						$(hi).remove();
					}
					else if(realobj.result=='error'){
						sAlert("","删除失败！内部错误！请咨询开发人员！");
					}
					
				},
			"html");
		} 
	}
	
function add(){
	alert("hi");
	var groupId = document.getElementById("groupId");
	var resName = document.getElementById("resName");
	var paction = document.getElementById("paction");
	$.post(getRootPath()+"/group/addPolicy.action",	
			"groupId="+groupId+"&resName="+resName+"&paction="+paction,		
			function(data, statusText) {
				var realobj=eval('('+data+')');
				if(realobj.result=='success'){
					sAlert("","已成功添加该策略。");
				}
				else if(realobj.result=='error'){
					sAlert("","添加失败！内部错误！请咨询开发人员！");
				}
				else if(realobj.result=='error'){
					sAlert("","删除失败！该策略已存在");
				}
				
			},
		"html");
}	
