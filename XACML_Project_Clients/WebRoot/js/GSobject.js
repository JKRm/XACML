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
			$.post(getRootPath()+"/gs/deleteObjectAction.action",	
				"objectId="+nid,		
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
	var load;
	function rename(obj,oid) {
			var area = document.getElementById(oid);
			if(obj.innerHTML=="重命名"){
			load = area.innerHTML;
			area.innerHTML =  "<input type=\"text\"  id = \"newName\" name=\"newName\" onblur=\"saveName()\">";
			obj.innerHTML="取消重命名";
			}
			else{
			area.innerHTML=load;
			obj.innerHTML="重命名";
			}
		}
		
		function movefile(obj,oid) {
			var area = document.getElementById("move"+oid);
			area.innerHTML= "";
			var copys = document.getElementById("copy");
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
			area.innerText = "将文件移至 ";
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
			var area = document.getElementById("move"+oid);
			area.innerHTML= "";
			var moves = document.getElementById("move");
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
			ma.innerText = "复  制";
			ma.href = "javascript:void(0)";
			ma.setAttribute("onclick", "affirm4copy("+oid+");return false");
			select.id = "platform";
			select4bucket.id = "bucket";
			moo.value = "0";
			moo.innerText = "请选择存储平台...";
			amas.value = "amazon";
			amas.innerText = "Amazon S3";
			goos.value = "google";
			goos.innerText = "google storage";
			locs.value = "local";
			locs.innerText = "local storage";
			moren.innerText="请先选择相应平台...";
			select.appendChild(moo);
			select.appendChild(locs);
			select.appendChild(amas);
			select.appendChild(goos);
			select.setAttribute("onchange", "motifyBucket()");
			select4bucket.appendChild(moren);
			area.innerText = "将文件复制至 ";
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
					alert("请正确选择存储平台！");
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
			if($("#bucket").val()=='0'){
				alert("请先正确选择移动位置");
			}
			else{
			
			if (confirm("确定移动？")) {
			
			$.post(getRootPath()+"/gs/moveObjectAction.action",	
				"objectId="+oid+"&tarBucket="+$("#bucket").val(),	
				function(data, statusText) {
					var realobj=eval('('+data+')');
					if(realobj.result=='success'){
						alert("已成功移动 。");
						location.reload() ;
					}
					else if(realobj.result=='error'){
					alert("移动失败！内部错误！请咨询开发人员！");
					location.reload() ;
					}
					//var list = data;	
					//alert(statusText);
				},
			"html");
			} else {
			}
			
			}
			//alert($("#bucket").val());
			//alert($("#platform").val());
			//alert(oid);
		}
		
		function affirm4copy(oid){
			if($("#bucket").val()=='0'){
				alert("请先正确选择复制位置");
			}
			else{
			
			if (confirm("确定复制？")) {
			
			$.post(getRootPath()+"/gs/copyObjectAction.action",	
				"objectId="+oid+"&tarBucket="+$("#bucket").val(),	
				function(data, statusText) {
					var realobj=eval('('+data+')');
					if(realobj.result=='success'){
						alert("已成功复制。");
						location.reload() ;
					}
					else if(realobj.result=='error'){
					alert("复制失败！内部错误！请咨询开发人员！");
					location.reload() ;
					}
					//var list = data;	
					//alert(statusText);
				},
			"html");
			} else {
			}
			
			}
			//alert($("#bucket").val());
			//alert($("#platform").val());
			//alert(oid);
		}
		
	
	function saveName(){
		var oid = document.getElementById("newName").parentNode.id;
		var newName = document.getElementById("newName").value;
		if (confirm("确定重命名？")) {
			$.post(getRootPath()+"/gs/renameObjectAction.action",	
				"newName="+newName+"&objectId="+oid,		
				function(data, statusText) {
					var realobj=eval('('+data+')');
					if(realobj.result=='success'){
						alert("已成功将 "+realobj.oldname+" 重命名为 "+newName+" 。");
						location.reload() ;
					}
					else if(realobj.result=='error'){
					alert("修改失败！内部错误！请咨询开发人员！");
					location.reload() ;
					}
					//var list = data;	
					//alert(statusText);
				},
			"html");
		} else {
		}
	
	}