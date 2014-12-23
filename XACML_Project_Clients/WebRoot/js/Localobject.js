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
			$.post(getRootPath()+"/local/deleteObjectAction.action",	
				"objectId="+nid,		
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
	var load;
	function rename(obj,oid) {
			var area2 = document.getElementById("move"+oid);
			area2.innerHTML= "";
			var copys = document.getElementById("cop"+oid);
			copys.innerHTML="复制▼";
			var moves = document.getElementById("mov"+oid);
			moves.innerHTML="移动▼";
			var area = document.getElementById(oid);
			if(obj.innerHTML=="重命名"){
			load = area.innerHTML;
			area.innerHTML =  "<input type=\"text\"  id = \"newName\" name=\"newName\" value=\""+area.childNodes[0].innerHTML+"\" onblur=\"saveName()\">";
			obj.innerHTML="取消重命名";
			}
			else{
			area.innerHTML=load;
			obj.innerHTML="重命名";
			}
		}
		
		function movefile(obj,oid) {
			var ren = document.getElementById("re"+oid);
			if(ren.innerHTML =="取消重命名"){
			ren.innerHTML = "重命名";
			var area2 = document.getElementById(oid);
			area2.innerHTML=load;
			}
			var area = document.getElementById("move"+oid);
			area.innerHTML= "";
			var copys = document.getElementById("cop"+oid);
			copys.innerHTML="复制▼";
			if(obj.innerHTML=="移动▼"){
			//area.innerHTML =  "<input type=\"text\"  id = \"newName\" name=\"newName\" onblur=\"saveName()\">";
			var select = document.createElement("select");
			var select4bucket = document.createElement("select");
			var moo = document.createElement("option");
			var amas = document.createElement("option");
			var goos = document.createElement("option");
			var locs = document.createElement("option");
			var moren = document.createElement("option");
			var ma = document.createElement("a");
			ma.innerHTML = "移  动";
			ma.href = "javascript:void(0)";
			ma.setAttribute("onclick", "affirm("+oid+");return false");
			select.id = "platform";
			select4bucket.id = "bucket";
			moo.value = "0";
			moo.innerHTML = "请选择存储平台...";
			amas.value = "amazon";
			amas.innerHTML = "Amazon S3";
			goos.value = "google";
			goos.innerHTML = "google storage";
			locs.value = "local";
			locs.innerHTML = "local storage";
			moren.innerHTML="请先选择相应平台...";
			select.appendChild(moo);
			select.appendChild(locs);
			select.appendChild(amas);
			select.appendChild(goos);
			select.setAttribute("onchange", "motifyBucket()");
			select4bucket.appendChild(moren);
			area.innerHTML = "将文件移至 ";
			area.appendChild(select);
			area.appendChild(select4bucket);
			area.appendChild(ma);
			document.getElementById("bucket").disabled=true;
			obj.innerHTML="返回▲";
			}
			else{
			area.innerHTML= "";
			obj.innerHTML="移动▼";
			}
		}
		
		function copyfile(obj,oid) {
			var ren = document.getElementById("re"+oid);
			if(ren.innerHTML =="取消重命名"){
				ren.innerHTML = "重命名";
				var area2 = document.getElementById(oid);
				area2.innerHTML=load;
				}
			var area = document.getElementById("move"+oid);
			area.innerHTML= "";
			var moves = document.getElementById("mov"+oid);
			moves.innerHTML="移动▼";
			if(obj.innerHTML=="复制▼"){
			var select = document.createElement("select");
			var select4bucket = document.createElement("select");
			var moo = document.createElement("option");
			var amas = document.createElement("option");
			var goos = document.createElement("option");
			var locs = document.createElement("option");
			var moren = document.createElement("option");
			var ma = document.createElement("a");
			ma.innerHTML = "复  制";
			ma.href = "javascript:void(0)";
			ma.setAttribute("onclick", "affirm4copy("+oid+");return false");
			select.id = "platform";
			select4bucket.id = "bucket";
			moo.value = "0";
			moo.innerHTML = "请选择存储平台...";
			amas.value = "amazon";
			amas.innerHTML = "Amazon S3";
			goos.value = "google";
			goos.innerHTML = "google storage";
			locs.value = "local";
			locs.innerHTML = "local storage";
			moren.innerHTML="请先选择相应平台...";
			select.appendChild(moo);
			select.appendChild(locs);
			select.appendChild(amas);
			select.appendChild(goos);
			select.setAttribute("onchange", "motifyBucket()");
			select4bucket.appendChild(moren);
			area.innerHTML = "将文件复制至 ";
			area.appendChild(select);
			area.appendChild(select4bucket);
			area.appendChild(ma);
			document.getElementById("bucket").disabled=true;
			obj.innerHTML="返回▲";
			}
			else{
			area.innerHTML= "";
			obj.innerHTML="复制▼";
			}
		}
		
		function motifyBucket(){
			$("#bucket option").remove();
			var bucket = document.getElementById("bucket");
			document.getElementById("bucket").disabled = false;
			var sort = $("#platform").val();
			$.post(getRootPath()+"/bucket/bucketListAction.action",
			"sort="+sort,
			function(data, statusText) {
				var list = data;
				if(list == '['){
					$("#bucket option").remove();
					$("#bucket").append("<option value='0'>请先选择相应平台...</option>");
					document.getElementById("bucket").disabled = true;
					sAlert("","请正确选择存储平台!！");
				}
				else{
				var jsonStr = eval('('+list+')');
				var length = jsonStr.length;
				for (var i = 0; i < length; ++i) {
					var addItem = "<option value='"+jsonStr[i]["text"]+"'>"+jsonStr[i]["value"]+"</option>";
					$("#bucket").append(addItem);
				}
				}
				
			},
			"html");
		
		
		}
		
		function affirm(oid){
			var area = document.getElementById("move"+oid);
			var movs = document.getElementById("mov"+oid);
			var hi = document.getElementById("li"+oid);
			var hi2 = document.getElementById("tr"+oid);
			if($("#bucket").val()=='0'){
				sAlert("","请先正确选择移动位置");
			}
			else{
			
			if (confirm("确定移动？")) {
				if($("#platform").val()=='local'){
			$.post(getRootPath()+"/local/moveObjectAction.action",	
				"objectId="+oid+"&tarBucket="+$("#bucket").val(),	
				function(data, statusText) {
					var realobj=eval('('+data+')');
					if(realobj.result=='success'){
						sAlert("","已成功移动 。");
						$(hi).remove();
						$(hi2).remove();
					}
					else if(realobj.result=='error'){
						sAlert("","移动失败！内部错误！请咨询开发人员！");
						
					}
					else if(realobj.result=='deny'){
						sAlert("","移动失败！您所在的用户组未获得该操作权限！");
					}
					else if(realobj.result=='exist'){
						sAlert("","移动失败！您所复制的文件在目标路径已存在！");
					}
					
				},
			"html");
			}
				else if($("#platform").val()=='amazon'){
					$.post(getRootPath()+"/bucket/movLS.action",	
							"objectId="+oid+"&bucketId="+$("#bucket").val(),	
							function(data, statusText) {
								var realobj=eval('('+data+')');
								if(realobj.result=='success'){
									sAlert("","已成功移动 。");
									$(hi).remove();
								}
								else if(realobj.result=='error'){
									sAlert("","移动失败！内部错误！请咨询开发人员！");
								}
								else if(realobj.result=='deny'){
									sAlert("","移动失败！您所在的用户组未获得该操作权限！");
								}
								else if(realobj.result=='exist'){
									sAlert("","移动失败！您所复制的文件在目标路径已存在！");
								}
							},
						"html");
					
					
				}
			}
			
			}
			area.innerHTML = "";
			movs.innerHTML="移动▼";
		}
		
		function affirm4copy(oid){
			var area = document.getElementById("move"+oid);
			var cops = document.getElementById("cop"+oid);
			if($("#bucket").val()=='0'){
				sAlert("","请先正确选择复制位置");
			}
			else{
			
			if (confirm("确定复制？")) {
				if($("#platform").val()=='local'){
			$.post(getRootPath()+"/local/copyObjectAction.action",	
				"objectId="+oid+"&tarBucket="+$("#bucket").val(),	
				function(data, statusText) {
					var realobj=eval('('+data+')');
					if(realobj.result=='success'){
						sAlert("","已成功复制。");
					}
					else if(realobj.result=='error'){
						sAlert("","复制失败！内部错误！请咨询开发人员！");
					}
					else if(realobj.result=='deny'){
						sAlert("","复制失败！您所在的用户组未获得该操作权限！");
					}
					else if(realobj.result=='exist'){
						sAlert("","复制失败！您所复制的文件在目标路径已存在！");
					}
				},
			"html");
			} 
				else if($("#platform").val()=='amazon'){
					$.post(getRootPath()+"/bucket/cpLS.action",	
							"objectId="+oid+"&bucketId="+$("#bucket").val(),	
							function(data, statusText) {
								var realobj=eval('('+data+')');
								if(realobj.result=='success'){
									sAlert("","已成功复制 。");
								}
								else if(realobj.result=='error'){
									sAlert("","复制失败！内部错误！请咨询开发人员！");
								}
								else if(realobj.result=='deny'){
									sAlert("","复制失败！您所在的用户组未获得该操作权限！");
								}
								else if(realobj.result=='exist'){
									sAlert("","复制失败！您所复制的文件在目标路径已存在！");
								}
							},
						"html");
					
					
				}	
			}
			
			}
			area.innerHTML = "";
			cops.innerHTML="复制▼";
		}
		
	
	function saveName(){
		var oid = document.getElementById("newName").parentNode.id;
		var area = document.getElementById(oid);
		var obj = document.getElementById("re"+oid);
		var newName = document.getElementById("newName").value;
		if (confirm("确定重命名？")) {
			$.post(getRootPath()+"/local/renameObjectAction.action",	
				"newName="+newName+"&objectId="+oid,		
				function(data, statusText) {
					var realobj=eval('('+data+')');
					if(realobj.result=='success'){
						area.innerHTML=load;
						area.childNodes[0].innerHTML = newName;
						obj.innerHTML="重命名";
						location.reload() ;
					}
					else if(realobj.result=='error'){
						sAlert("","修改失败！内部错误！请咨询开发人员！");
						area.innerHTML=load;
						obj.innerHTML="重命名";
					}
					else if(realobj.result=='deny'){
						sAlert("","修改失败！您所在的用户组未获得该操作权限！");
						area.innerHTML=load;
						obj.innerHTML="重命名";
					}
					else if(realobj.result=='exist'){
					sAlert("","修改失败！当前bucket内已存在此名称文件！");
					area.innerHTML=load;
					obj.innerHTML="重命名";
				}
				},
			"html");
		} else {
		}
	
	}
	
	function getPath(oid){
		$.post(getRootPath()+"/local/GetLo.action",	
				"objectId="+oid,		
				function(data, statusText) {
					var realobj=eval('('+data+')');
					if(realobj.result=='success'){
						window.open('../showDownloadl.jsp','下载地址', 'height=100, width=400, top=400, left=400, toolbar=no, menubar=no, scrollbars=no,resizable=no,location=no, status=no');
					}
					else if(realobj.result=='error'){
						sAlert("","下载失败！内部错误！请咨询开发人员！");
					}
					else if(realobj.result=='deny'){
						sAlert("","下载失败！您所在的用户组未获得该操作权限！");
					}
					//var list = data;	
					//alert(statusText);
				},
			"html");
	}
