function getRootPath(){
        var strFullPath=window.document.location.href;
        var strPath=window.document.location.pathname;
        var pos=strFullPath.indexOf(strPath);
        var prePath=strFullPath.substring(0,pos);
        var postPath=strPath.substring(0,strPath.substr(1).indexOf('/')+1);
        return(prePath+postPath);
        }



function con(nid) {
		if (confirm("确定删除？")) {
			$.post(getRootPath()+"/gs/delBucketAction.action",	
				"bucketId="+nid,		
				function(data, statusText) {
					var realobj=eval('('+data+')');
					if(realobj.result=='success'){
						alert("已成功删除 "+realobj.oldname+" 。");
						location.reload() ;
					}
					else if(realobj.result=='error'){
					alert("删除失败！内部错误！请咨询开发人员！");
					location.reload() ;
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
	if (confirm("确定添加？")) {
		$.post(getRootPath()+"/gs/addBucketAction.action",	
			"newName="+newName,		
			function(data, statusText) {
				var realobj=eval('('+data+')');
				if(realobj.result=='success'){
					alert("已成功添加 bucket " +newName+" 。");
					location.reload() ;
				}
				else if(realobj.result=='error'){
				alert("添加失败！内部错误！请咨询开发人员！");
				location.reload() ;
				}
				//var list = data;	
				//alert(statusText);
			},
		"html");
	} else {
	}
}